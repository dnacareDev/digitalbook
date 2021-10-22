package com.digitalBook.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalBook.Entity.Machine;
import com.digitalBook.Mapper.MachineMapper;
import com.digitalBook.Service.MachineService;

@Service
public class MachineServiceImpl implements MachineService
{
	@Autowired
	private MachineMapper mapper;

	// 장비 상세 조회
	@Override
	public Machine SelectMachineDetail(int machine_id)
	{
		return mapper.SelectMachineDetail(machine_id);
	}

	// 최신 장비 조회
	@Override
	public Machine SelectLastMachine(int user_group)
	{
		return mapper.SelectLastMachine(user_group);
	}

	// 장비 갯수 검색
	@Override
	public int SearchMachineCount(int search_type, String keyword, int user_group)
	{
		return mapper.SearchMachineCount(search_type, keyword, user_group);
	}

	// 장비 검색
	@Override
	public List<Machine> SearchMachine(int search_type, String keyword, int offset, int limit, int user_group)
	{
		return mapper.SearchMachine(search_type, keyword, offset, limit, user_group);
	}

	// 장비 등록
	@Override
	public int InsertMachine(Machine machine)
	{
		return mapper.InsertMachine(machine);
	}

	// 장비 수정
	@Override
	public int UpdateMachine(Machine machine)
	{
		return mapper.UpdateMachine(machine);
	}

	// 장비 삭제
	@Override
	public int DeleteMachine(int machine_id)
	{
		return mapper.DeleteMachine(machine_id);
	}
}