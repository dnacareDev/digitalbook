package com.digitalBook.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.digitalBook.Entity.Department;

@Mapper
public interface StorageMapper
{
	// 이용과 조회
	List<Department> SelectDepartment();
}