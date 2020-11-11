package com.hungerbox.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.hungerbox.dto.FoodMenuDto;
import com.hungerbox.entity.FoodMenu;
import com.hungerbox.exception.FoodItemNotFoundException;
import com.hungerbox.repository.FoodMenuRepository;
import com.hungerbox.service.VendorFoodService;

@Service
public class VendorFoodServiceImpl implements VendorFoodService{

	@Autowired
	FoodMenuRepository foodmenuRepository;

	@Override
	public List<FoodMenuDto> findByfoodNameContains(String foodName, int pageNumber, int pageSize) {

		List<FoodMenu> foodMenuList = new ArrayList();
		List<FoodMenuDto> foodMenuDtoList = new ArrayList();

		Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Direction.ASC, "foodName"));
		foodMenuList = foodmenuRepository.findByfoodNameContains(foodName, pageable);

		
		// exception for food
				if (foodMenuList.isEmpty()) {
					throw new FoodItemNotFoundException("food item not found");
				}
					
					
		for (FoodMenu foodMenu : foodMenuList) {
			FoodMenuDto foodMenuDto = new FoodMenuDto();
			BeanUtils.copyProperties(foodMenu, foodMenuDto);
			foodMenuDtoList.add(foodMenuDto);
		}
		return foodMenuDtoList;

	}

}