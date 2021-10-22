package com.digitalBook.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.digitalBook.Entity.Material;

@Mapper
public interface MaterialMapper {
	
	//농자재 검색
	List<Material> SearchMaterial(@Param("search_type") String search_type, @Param("keyword") String keyword,
			@Param("offset") int offset, @Param("limit") int limit, @Param("user_group") int user_group);
	
	//농자재 갯수 검색
	int SearchMaterialCount(@Param("search_type") String search_type, @Param("keyword") String keyword, @Param("user_group") int user_group);
	
	//농자재 등록
	int insertMaterial(Material material);
	
	//최근 농자재 ID 조회
	String selectLastMeterialCode(int user_group);
	
	//농자재 상세 조회
	Material selectMaterialDetail(int material_id);
	
	//농자재 수정
	int updateMaterial(Material material);
	
	//농자재 삭제
	int deleteMaterial(int material_id);
	
}
