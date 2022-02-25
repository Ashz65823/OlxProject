package com.zensar.olx.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zensar.olx.DB.CategoryDAO;
import com.zensar.olx.bean.Category;



@Service
public class CategoryService {

	@Autowired
	CategoryDAO dao;

	public Category addCategory(Category category)
	{
		return this.dao.save(category);//insert category
	}
	public List<Category> getAllCategories()
	{
		return this.dao.findAll();//return all the category list
	}
	public Category findCategory(int id)
	{
		Optional< Category> optional;
		optional=this.dao.findById(id);
		if(optional.isPresent())
			return optional.get();
		else
			return null;
	}
}
