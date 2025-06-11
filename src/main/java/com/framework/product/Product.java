package com.framework.product;

import java.time.LocalDateTime;
import lombok.Data;

/**
 * Product 목록/상세에 사용하는 DTO
 */
@Data
public class Product {
    private int    productSeq;
    private String productType;
    private String productName;
    private int    productPrice;
    private LocalDateTime regDt;
    private LocalDateTime modDt;
}
