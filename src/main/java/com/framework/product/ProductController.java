package com.framework.product;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    // 생성자 방식으로 주입
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 기존 list 메서드 생략…

    /**
     * GET /product/detail/{seq}
     * - Restful URL 방식으로 seq를 받아서 상세 정보 조회
     * - Service에서 리턴한 Map을 "result"라는 이름으로 뷰에 올림
     */
    @GetMapping("/detail/{seq}")
    public String detail(
            @PathVariable("seq") int seq,
            Model model
    ) {
        Map<String, Object> result = productService.detail(seq);
        model.addAttribute("result", result);
        return "product/detail";
    }
}
