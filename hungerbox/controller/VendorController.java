package com.hungerbox.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hungerbox.dto.VendorDto;
import com.hungerbox.service.VendorService;

@RestController
@RequestMapping("/vendors")
public class VendorController {
	
	@Autowired
	VendorService vendorService;
	
	private final static Logger logger = LoggerFactory.getLogger(VendorController.class);
	
	@GetMapping("/searchbyvendorname")
	public List<VendorDto> getVendorWithVendorName(@RequestParam String vendorName, @RequestParam int pageNumber, @RequestParam int pageSize){
		logger.info("Before calling vendor name ");
		return vendorService.findByvendorNameContains(vendorName,pageNumber,pageSize);
	}
}

 
