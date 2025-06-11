package com.framework.product.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.framework.product.Product;

/**
 * MyBatis Mapper for Products
 */
@Mapper
public interface ProductMapper {

    /**
     * 전체 상품 건수를 조회한다.
     * 검색어가 있으면 상품명에 대해 LIKE 조건을 적용한다.
     */
    @Select({"<script>",
            "SELECT COUNT(*)",
            "FROM tb_product",
            "<where>",
            "  <if test=\"searchProductName != null and searchProductName != ''\">",
            "    product_name LIKE CONCAT('%', #{searchProductName}, '%')",
            "  </if>",
            "  <if test=\"searchProductType != null and searchProductType != ''\">",
            "    AND product_type = #{searchProductType}",
            "  </if>",
            "</where>",
            "</script>"})
    int selectProductTotalCount(
            @Param("searchProductName") String searchProductName,
            @Param("searchProductType") String searchProductType
    );

    /**
     * 상품 목록을 조회한다.
     * 검색어가 있으면 상품명 LIKE, 유형이 지정되면 유형 조건을 적용하며,
     * 페이징을 위해 offset과 limit을 사용한다.
     */
    @Select({"<script>",
            "SELECT",
            "  product_seq AS productSeq,",
            "  product_type AS productType,",
            "  product_name AS productName,",
            "  product_price AS productPrice,",
            "  reg_dt AS regDt,",
            "  mod_dt AS modDt",
            "FROM tb_product",
            "<where>",
            "  <if test=\"searchProductName != null and searchProductName != ''\">",
            "    product_name LIKE CONCAT('%', #{searchProductName}, '%')",
            "  </if>",
            "  <if test=\"searchProductType != null and searchProductType != ''\">",
            "    AND product_type = #{searchProductType}",
            "  </if>",
            "</where>",
            "ORDER BY product_seq",
            "LIMIT #{offset}, #{limit}",
            "</script>"})
    List<Product> selectProductList(
            @Param("searchProductName") String searchProductName,
            @Param("searchProductType") String searchProductType,
            @Param("offset") int offset,
            @Param("limit") int limit
    );

    /**
     * 단일 상품 상세 조회
     */
    @Select("SELECT product_seq AS productSeq, product_type AS productType, product_name AS productName, product_price AS productPrice, reg_dt AS regDt, mod_dt AS modDt FROM tb_product WHERE product_seq = #{seq}")
    Map<String, Object> selectProductDetail(@Param("seq") int seq);
}
