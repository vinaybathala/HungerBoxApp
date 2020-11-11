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

import com.hungerbox.dto.VendorDto;
import com.hungerbox.entity.Vendor;
import com.hungerbox.exception.VendorNotFoundException;
import com.hungerbox.repository.VendorRepository;
import com.hungerbox.service.VendorService;

@Service
public class VendorServiceImpl implements VendorService {
	
	
	@Autowired
	VendorRepository vendorRepository;
	
	@Override
	public List<VendorDto> findByvendorNameContains(String vendorName, int pageNumber, int pageSize) {
		List<Vendor> vendorList = new ArrayList();
		List<VendorDto> vendorDtoList = new ArrayList();
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize,Sort.by(Direction.ASC, "vendorName"));
		vendorList = vendorRepository.findByvendorNameContains(vendorName,pageable); 
		
		// exception for vendor
		if (vendorList.isEmpty()) {
			throw new VendorNotFoundException("Not a valid vendor");
		}
		for (Vendor vendor : vendorList) {
			VendorDto vendorDto = new VendorDto();
			BeanUtils.copyProperties(vendor, vendorDto);
			vendorDtoList.add(vendorDto);
		}
		return vendorDtoList;
	}
	
}
