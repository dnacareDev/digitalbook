package com.digitalBook.Service;

import java.util.List;

import com.digitalBook.Entity.Department;
import com.digitalBook.Entity.Storage;

public interface StorageService
{
	// 이용과 조회
	List<Department> SelectDepartment();

	// 최신 장소 코드 조회
	Storage SelectLastStorage();

	// 장소 갯수 조회
	int SelectStorageCount();
	
	// 장소 검색
	List<Storage> SearchStorage(int offset, int limit);

	// 장소 등록
	int InsertStorage(Storage storage);
	int deleteStorage(Storage storage);
}