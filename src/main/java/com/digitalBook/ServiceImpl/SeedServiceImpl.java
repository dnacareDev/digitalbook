package com.digitalBook.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalBook.Entity.Division;
import com.digitalBook.Entity.Eaches;
import com.digitalBook.Entity.Genetic;
import com.digitalBook.Entity.Report;
import com.digitalBook.Entity.Seed;
import com.digitalBook.Entity.User;
import com.digitalBook.Entity.Warehouse;
import com.digitalBook.Mapper.SeedMapper;
import com.digitalBook.Service.SeedService;

@Service
public class SeedServiceImpl implements SeedService {
	
	@Autowired
	private SeedMapper mapper;
	
	//시료 검색
	@Override
	public List<Seed> SearchSeed(String search_type, String keyword, int offset, int limit) {
		
		return mapper.SearchSeed(search_type, keyword, offset, limit);
	}
	
	//시료 개수 검색
	@Override
	public int SearchSeedCount(String search_type, String keyword) {
		
		return mapper.SearchSeedCount(search_type, keyword);
	}
	
	//과제 전체 조회
	@Override
	public List<Report> selectReportList() {
		
		return mapper.selectReportList();
	}
	
	//사용자 전체 조회
	@Override
	public List<User> selectUserList() {
		
		return mapper.selectUserList();
	}
	
	//단위 전체 조회
	@Override
	public List<Eaches> selectEachesList() {
		
		return mapper.selectEachesList();
	}
	
	//저장 장소 전체 조회
	@Override
	public List<Warehouse> selectWarehouseList() {
		
		return mapper.selectWarehouseList();
	}
	
	//조사 항목(작목) 전체 조회
	@Override
	public List<Division> selectDivisionList() {
		
		return mapper.selectDivisionList();
	}
	
	//품종, 유전정보 전체 조회
	@Override
	public List<Genetic> selectGeneticList(int division_id, int genetic_type) {
		
		return mapper.selectGeneticList(division_id, genetic_type);
	}
	
	//시료 등록
	@Override
	public int insertSeed(Seed seed) {
		
		return mapper.insertSeed(seed);
	}
	
	//최근 시료 ID
	@Override
	public String selectLastSeedCode() {
		
		return mapper.selectLastSeedCode();
	}
	
	

}
