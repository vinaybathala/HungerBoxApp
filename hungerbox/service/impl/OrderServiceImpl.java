package com.hungerbox.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.hungerbox.dto.FoodItemRequestDto;
import com.hungerbox.dto.FoodOrdersDto;
import com.hungerbox.dto.UserOrderRequestDto;
import com.hungerbox.dto.UserTransactionRequestDto;
import com.hungerbox.entity.FoodMenu;
import com.hungerbox.entity.FoodOrders;
import com.hungerbox.entity.OrderItems;
import com.hungerbox.entity.User;
import com.hungerbox.entity.Vendor;
import com.hungerbox.exception.FoodItemNotFoundException;
import com.hungerbox.exception.UserNotFoundException;
import com.hungerbox.exception.VendorNotFoundException;
import com.hungerbox.feignclients.DBSBankClient;
import com.hungerbox.repository.FoodMenuRepository;
import com.hungerbox.repository.OrderItemsRepository;
import com.hungerbox.repository.OrderRepository;
import com.hungerbox.repository.UserRepository;
import com.hungerbox.repository.VendorRepository;
import com.hungerbox.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	FoodMenuRepository foodmenuRepository;

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	VendorRepository vendorRepository;

	@Autowired
	OrderItemsRepository orderItemsRepository;

	@Autowired
	DBSBankClient dBSBankClient;

	@Override
	public void saveOrder(UserOrderRequestDto userOrderRequestDto) {
		FoodOrders foodOrders = new FoodOrders();
		Optional<User> optionalUser = userRepository.findById(userOrderRequestDto.getUserId());
		long totalprice = 0;

		// exception for user
		if (!optionalUser.isPresent()) {
			throw new UserNotFoundException("Not a valid user");

		}

		User user = optionalUser.get();
		foodOrders.setUserId(user.getUserId());
		for (FoodItemRequestDto foodItemRequestDto : userOrderRequestDto.getFoodItems()) {
			Optional<FoodMenu> optionalfoodmenu = foodmenuRepository.findById(foodItemRequestDto.getFoodId());

			// exception for food
			if (!optionalfoodmenu.isPresent()) {
				throw new FoodItemNotFoundException("selected food item is not available");

			}

			FoodMenu foodmenu = optionalfoodmenu.get();

			long selecteditemprice = foodmenu.getFoodPrice();
			long selecteditemtotal = (selecteditemprice * foodItemRequestDto.getQuantity());
			totalprice = (totalprice + selecteditemtotal);
			foodOrders.setTotalPrice(totalprice);
			foodOrders.setFoodId(foodmenu.getFoodId());
			foodOrders.setFoodName(foodmenu.getFoodName());
			foodOrders.setVendorId(foodmenu.getVendorId());
			foodOrders.setOrderStatus("pending");
			foodOrders = orderRepository.save(foodOrders);

			OrderItems orderItems = new OrderItems();
			orderItems.setFoodId(foodmenu.getFoodId());
			orderItems.setOrderId(foodOrders.getOrderId());
			orderItems.setQuantity(foodItemRequestDto.getQuantity());
			orderItems = orderItemsRepository.save(orderItems);

			Optional<Vendor> optionalVendor = vendorRepository.findById(foodmenu.getVendorId());

			// exception for vendor
			if (!optionalVendor.isPresent()) {
				throw new VendorNotFoundException("Not a valid vendor");
			}

			if (optionalVendor.isPresent()) {
				Vendor vendor = optionalVendor.get();
				UserTransactionRequestDto userTransactionRequestDto = new UserTransactionRequestDto();
				userTransactionRequestDto.setToAccount(vendor.getAccountNumber());
				userTransactionRequestDto.setFromAccounNumber(userOrderRequestDto.getFromAccountNumber());
				userTransactionRequestDto.setAmount(totalprice);
				userTransactionRequestDto.setRemarks("foodapp");

				dBSBankClient.fundTransfer(userTransactionRequestDto);

			}

		}

		foodOrders.setOrderStatus("success");
		orderRepository.save(foodOrders);

	}

	@Override
	public List<FoodOrdersDto> getOrderList(int userId, int pageNumber, int pageSize) {

		List<FoodOrders> foodOrdersList = new ArrayList<FoodOrders>();
		List<FoodOrdersDto> foodOrderDtoList = new ArrayList<FoodOrdersDto>();

		Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Direction.ASC, "foodName"));
		foodOrdersList = orderRepository.findByuserId(userId, pageable);

		for (FoodOrders foodOrders : foodOrdersList) {
			FoodOrdersDto foodOrdersDto = new FoodOrdersDto();
			BeanUtils.copyProperties(foodOrders, foodOrdersDto);
			foodOrderDtoList.add(foodOrdersDto);
		}
		return foodOrderDtoList;
	}

	@Override
	public String getInfo() {
		return dBSBankClient.getInfo();
	}
}
