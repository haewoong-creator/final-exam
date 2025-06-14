package com.framework.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.framework.product.mapper.ProductMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * 상품 검색 및 페이징 처리 서비스
 */
@Slf4j
@Service
public class ProductService {

    /** 한 페이지당 보여줄 상품 수 */
    private static final int PAGE_SIZE = 10;
    /** 페이지 블록 당 노출할 페이지 번호 개수 */
    public static final int PAGE_BLOCK_LIST = 5;

    private final ProductMapper productMapper;

    // 생성자 주입
    public ProductService(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    /**
     * 검색어 및 페이징에 따른 상품 목록 조회
     *
     * @param form 검색어, 페이지 번호 정보가 담긴 Form
     * @return totalCount, list, currentPage, totalPage, pageList, pageBlockList 등을 담은 Map
     */
    @Transactional(readOnly = true)
    public Map<String, Object> searchProducts(ProductForm form) {
        String name = form.getSearchProductName();
        String type = form.getSearchProductType();
        int requestedPage = form.getPageNum() <= 0 ? 1 : form.getPageNum();
        log.info("searchProducts 호출 - name={}, type={}, page={}", name, type, requestedPage);

        // 1) 전체 건수 조회
        int totalCount = productMapper.selectProductTotalCount(name, type);
        log.info("총 상품 건수 = {}", totalCount);

        // 2) 전체 페이지 계산
        int totalPage = (int) Math.ceil((double) totalCount / PAGE_SIZE);
        if (totalPage == 0) {
            totalPage = 1;
        }
        if (requestedPage > totalPage) {
            requestedPage = totalPage;
        }

        // 3) 조회 offset 계산
        int offset = (requestedPage - 1) * PAGE_SIZE;

        // 4) 리스트 조회
        List<Product> list = productMapper.selectProductList(name, type, offset, PAGE_SIZE);
        log.info("조회된 상품 개수 = {}", list.size());

        // 5) 페이지 블록 계산
        int block = PAGE_BLOCK_LIST;
        int startPage = ((requestedPage - 1) / block) * block + 1;
        int endPage = startPage + block - 1;
        if (endPage > totalPage) {
            endPage = totalPage;
        }
        List<Integer> pageList = new ArrayList<>();
        for (int p = startPage; p <= endPage; p++) {
            pageList.add(p);
        }

        // 6) 결과 조립
        Map<String, Object> result = new HashMap<>();
        result.put("totalCount", totalCount);
        result.put("list", list);
        result.put("currentPage", requestedPage);
        result.put("totalPage", totalPage);
        result.put("pageList", pageList);
        result.put("pageBlockList", PAGE_BLOCK_LIST);

        return result;
    }

    /**
     * 단일 상품 상세 조회
     */
    @Transactional(readOnly = true)
    public Map<String, Object> detail(int seq) {
        Map<String, Object> row = productMapper.selectProductDetail(seq);
        Map<String, Object> result = new HashMap<>();
        result.put("PRODUCT_DETAIL", row);
        return result;
    }
}