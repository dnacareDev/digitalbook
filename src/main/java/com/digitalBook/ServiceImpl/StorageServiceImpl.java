package com.digitalBook.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalBook.Entity.Department;
import com.digitalBook.Mapper.StorageMapper;
import com.digitalBook.Service.StorageService;

@Service
public class StorageServiceImpl implements StorageService
{
	@Autowired
	private StorageMapper mapper;
	
	@Override
	public List<Department> SelectDepartment()
	{
		return mapper.SelectDepartment();
	}
}