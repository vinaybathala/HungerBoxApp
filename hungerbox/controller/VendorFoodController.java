package com.hungerbox.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hungerbox.dto.FoodMenuDto;
import com.hungerbox.service.VendorFoodService;

@RestController
@RequestMapping("/foodmenu")
public class VendorFoodController {

	@Autowired
	VendorFoodService vendorFoodService;
	
	private final static Logger logger = LoggerFactory.getLogger(VendorFoodController.class);
	
	@GetMapping("/searchbyfoodname")
	public List<FoodMenuDto> getFoodNameWithVendor(@RequestParam String foodName, @RequestParam int pageNumber, @RequestParam int pageSize){
		logger.info("Before calling food name with vendor ");
		return vendorFoodService.findByfoodNameContains(foodName,pageNumber,pageSize);
	}
	
}

