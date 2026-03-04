package org.zerock.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.zerock.dto.ProductDTO;
import org.zerock.dto.ProductImageDTO;
import org.zerock.dto.ProductListDTO;

public interface ProductMapper {
	
	// 상품 등록
	int insert(ProductDTO dto);
	// 이미지 등록
	int insertImage(ProductDTO dto);
	// 상품 조회
	ProductDTO selectOne(@Param("pNo") Long pno);
	// 상품 삭제(sale = false)
	int deleteOne(@Param("pNo") Long pno);
	// 사진 삭제
	int deleteImage(@Param("pNo") Long pno);
	// 상품 수정
	int updateOne(ProductDTO dto);
	// 리스트 화면 조회
	List<ProductListDTO> selectList(@Param("limit") int limit,
									@Param("offset") int offset);
	// 리스트 전체 개수 조회
	int selectListCount();
}
