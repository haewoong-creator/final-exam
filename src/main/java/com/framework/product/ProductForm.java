package com.framework.product;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 검색 및 페이징용 폼 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductForm {
    /** 요청할 페이지 번호 */
    private int pageNum;
    /** 상품명 검색 키워드 */
    private String searchProductName;
    /** 상품 유형 검색 키워드 */
    private String searchProductType;
	public Object getKeyword() {
		// TODO Auto-generated method stub
		return null;
	}
}

