package com.digitalBook.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalBook.Entity.Department;
import com.digitalBook.Entity.Storage;
import com.digitalBook.Mapper.StorageMapper;
import com.digitalBook.Service.StorageService;

@Service
public class StorageServiceImpl implements StorageService
{
	@Autowired
	private StorageMapper mapper;
	
	// 이용과 조회
	@Override
	public List<Department> SelectDepartment()
	{
		return mapper.SelectDepartment();
	}

	// 장소 갯수 조회
	@Override
	public int SelectStorageCount()
	{
		return mapper.SelectStorageCount();
	}

	// 장소 검색
	@Override
	public List<Storage> SearchStorage(int offset, int limit)
	{
		return mapper.SearchStorage(offset, limit);
	}

	// 최신 장소 코드 조회
	@Override
	public Storage SelectLastStorage()
	{
		return mapper.SelectLastStorage();
	}

	// 장소 등록
	@Override
	public int InsertStorage(Storage storage)
	{
		return mapper.InsertStorage(storage);
	}
	// 장소 삭제
	@Override
	public int deleteStorage(Storage storage)
	{
		return mapper.deleteStorage(storage);
	}
}