package com.digitalBook.Service;

import java.util.List;

import com.digitalBook.Entity.Material;

public interface MaterialService {
	
	//농자재 검색
	List<Material> SearchMaterial(String search_type, String keyword, int offset, int limit);
		
	//농자재 갯수 검색
	int SearchMaterialCount(String search_type, String keyword);
	
	//농자재 등록
	int insertMaterial(Material material);
	
	//최근 농자재 ID 조회
	String selectLastMeterialCode();
	
	//농자재 상세 조회
	Material selectMaterialDetail(int material_id);
	
	//농자재 수정
	int updateMaterial(Material material);
	
	//농자재 삭제
	int deleteMaterial(int material_id);
	
}
