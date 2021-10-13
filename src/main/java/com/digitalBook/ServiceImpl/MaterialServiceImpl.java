package com.digitalBook.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalBook.Entity.Material;
import com.digitalBook.Mapper.MaterialMapper;
import com.digitalBook.Service.MaterialService;

@Service
public class MaterialServiceImpl implements MaterialService {
	
	@Autowired
	private MaterialMapper mapper;
	
	//농자재 검색
	@Override
	public List<Material> SearchMaterial(String search_type, String keyword, int offset, int limit) {
		
		return mapper.SearchMaterial(search_type, keyword, offset, limit);
	}
	
	//농자재 검색 개수
	@Override
	public int SearchMaterialCount(String search_type, String keyword) {
		
		return mapper.SearchMaterialCount(search_type, keyword);
	}
	
	//농자재 등록
	@Override
	public int insertMaterial(Material material) {
		
		return mapper.insertMaterial(material);
	}
	
	//최근 농자재 ID 조회
	@Override
	public String selectLastMeterialCode() {
		
		return mapper.selectLastMeterialCode();
	}
	
	//농자재 상세 조회
	@Override
	public Material selectMaterialDetail(int material_id) {
		
		return mapper.selectMaterialDetail(material_id);
	}
	
	//농자재 수정
	@Override
	public int updateMaterial(Material material) {
		
		return mapper.updateMaterial(material);
	}
	
	//농자재 삭제
	@Override
	public int deleteMaterial(int material_id) {
		
		return mapper.deleteMaterial(material_id);
	}

}
