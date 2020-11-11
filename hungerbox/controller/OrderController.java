package com.hungerbox.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hungerbox.dto.FoodOrdersDto;
import com.hungerbox.dto.UserOrderRequestDto;
import com.hungerbox.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	OrderService orderService;

	private final static Logger logger = LoggerFactory.getLogger(OrderController.class);

	@GetMapping("/port")
	public String getPortNumber() {
		return orderService.getInfo();
	}

	@PostMapping("/orderfood")
	public String placeOrder(@RequestBody UserOrderRequestDto userOrderRequestDto) {
		orderService.saveOrder(userOrderRequestDto);
		logger.info("Before calling place order  ");
		return "OrderPlaced-Succssfully";

	}

	@GetMapping("/vieworders")
	public List<FoodOrdersDto> getOrderList(@RequestParam int userId, @RequestParam int pageNumber,
			@RequestParam int pageSize) {

		return orderService.getOrderList(userId, pageNumber, pageSize);
	}

}
