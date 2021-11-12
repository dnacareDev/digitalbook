package com.digitalBook.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalBook.Entity.Division;
import com.digitalBook.Entity.Eaches;
import com.digitalBook.Entity.Genetic;
import com.digitalBook.Entity.Record;
import com.digitalBook.Entity.Report;
import com.digitalBook.Entity.Seed;
import com.digitalBook.Entity.User;
import com.digitalBook.Entity.Warehouse;
import com.digitalBook.Mapper.SeedMapper;
import com.digitalBook.Service.SeedService;

@Service
public class SeedServiceImpl implements SeedService
{
	@Autowired
	private SeedMapper mapper;
	
	//시료 개수 검색
	@Override
	public int SearchSeedCount(String search_type, String keyword, int user_group)
	{
		return mapper.SearchSeedCount(search_type, keyword, user_group);
	}
	
	//시료 검색
	@Override
	public List<Seed> SearchSeed(String search_type, String keyword, int user_group, int offset, int limit)
	{
		return mapper.SearchSeed(search_type, keyword, user_group, offset, limit);
	}
	
	//과제 전체 조회
	@Override
	public List<Report> SelectReportList(String user_name)
	{
		return mapper.SelectReportList(user_name);
	}
	
	//사용자 전체 조회
	@Override
	public List<User> SelectUserList(int user_group)
	{
		return mapper.SelectUserList(user_group);
	}
	
	//단위 전체 조회
	@Override
	public List<Eaches> SelectEachesList()
	{
		return mapper.SelectEachesList();
	}
	
	//저장 장소 전체 조회
	@Override
	public List<Warehouse> SelectWarehouseList()
	{
		return mapper.SelectWarehouseList();
	}
	
	//조사 항목(작목) 전체 조회
	@Override
	public List<Division> SelectDivisionList()
	{
		return mapper.SelectDivisionList();
	}
	
	//품종, 유전정보 전체 조회
	@Override
	public List<Genetic> SelectGeneticList(int division_id, int genetic_type)
	{
		return mapper.SelectGeneticList(division_id, genetic_type);
	}
	
	//시료 등록
	@Override
	public int InsertSeed(Seed seed)
	{
		return mapper.InsertSeed(seed);
	}
	
	//최근 시료 ID
	@Override
	public String SelectLastSeedCode(int user_group)
	{
		return mapper.SelectLastSeedCode(user_group);
	}
	
	//시료 detail list
	@Override
	public List<Seed> SelectSeedDetailList(int report_id)
	{
		return mapper.SelectSeedDetailList(report_id);
	}
	
	//시료 수정
	@Override
	public int UpdateSeed(Seed seed)
	{
		return mapper.UpdateSeed(seed);
	}
	
	//시료 삭제
	@Override
	public int DeleteSeed(int seed_id)
	{
		return mapper.DeleteSeed(seed_id);
	}
	
	//시료 detail
	@Override
	public Seed SelectSeedDetail(int seed_id)
	{
		return mapper.SelectSeedDetail(seed_id);
	}
	
	//과제 비연계 등록
	@Override
	public int InsertNonReport(Report report) {
		
		return mapper.InsertNonReport(report);
	}
	
	//최근 비연계 과제 ID 조회
	@Override
	public String selectLastNonReportCode(int user_group) {
		
		return mapper.selectLastNonReportCode(user_group);
	}
	
	//변경 이력 등록
	@Override
	public int insertRecord(Record record) {
		
		return mapper.insertRecord(record);
	}
	
	//시료 변경 이력 조회
	@Override
	public List<Record> selectRecordList(String report_code) {
		
		return mapper.selectRecordList(report_code);
	}
	
	//시료 승인
	@Override
	public int updateSeedStatus(int seed_id) {
		
		return mapper.updateSeedStatus(seed_id);
	}
	
	//genetic 기타 등록
	@Override
	public int insertGeneticEtc(Genetic genetic) {
		
		return mapper.insertGeneticEtc(genetic);
	}
}