package com.hungerbox.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hungerbox.entity.FoodMenu;

@Repository
public interface FoodMenuRepository extends JpaRepository<FoodMenu,Integer>{

	public List<FoodMenu> findByfoodNameContains(@Param("foodName") String foodName, Pageable pageRequest);

}
