package com.digitalBook.Service;

import java.util.List;

import com.digitalBook.Entity.Department;

public interface StorageService
{
	// 이용과 조회
	List<Department> SelectDepartment();
}