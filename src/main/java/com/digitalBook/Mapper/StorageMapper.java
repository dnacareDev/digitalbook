package com.digitalBook.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.digitalBook.Entity.Department;
import com.digitalBook.Entity.Storage;

@Mapper
public interface StorageMapper
{
	// 이용과 조회
	List<Department> SelectDepartment();

	// 장소 갯수 조회
	int SelectStorageCount();
	
	// 장소 검색
	List<Storage> SearchStorage(@Param("offset") int offset, @Param("limit") int limit);

	// 최신 장소 코드 조회
	Storage SelectLastStorage();

	// 장소 등록
	int InsertStorage(Storage storage);
	
	int deleteStorage(Storage storage);
}