package com.ontherocks.cocktail.controller;

import com.ontherocks.cocktail.dto.InvenDto;
import com.ontherocks.cocktail.service.InvenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inven")
public class InvenApi {

    private static final Logger logger = LoggerFactory.getLogger(InvenApi.class);
    private final InvenService invenService;

    public InvenApi(InvenService invenService) {
        this.invenService = invenService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<InvenDto>> getInvenList(
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize) {

        logger.info("📊 [API 요청] 재고 목록 조회 - 카테고리: '{}', 페이지: {}, 페이지 크기: {}", category, page, pageSize);
        List<InvenDto> invenList = invenService.getInvenList(category, page, pageSize);
        return ResponseEntity.ok(invenList);
    }

    @GetMapping("/{ingredientId}")
    public ResponseEntity<InvenDto> getInvenDetail(@PathVariable Integer ingredientId) {
        logger.info("🔍 [API 요청] 재고 상세 조회 | Ingredient ID: {}", ingredientId);
        InvenDto invenDto = invenService.getInvenDetail(ingredientId);

        if (invenDto == null) {
            logger.warn("⚠️ [API 실패] Ingredient ID {} 없음", ingredientId);
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(invenDto);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addInven(@RequestBody InvenDto invenDto) {
        logger.info("🆕 [API 요청] 재고 추가: {}", invenDto.getName());

        try {
            invenService.addInven(invenDto);
            return ResponseEntity.ok("재고가 성공적으로 추가되었습니다.");
        } catch (Exception e) {
            logger.error("❌ [API 실패] 재고 추가 중 오류 발생", e);
            return ResponseEntity.badRequest().body("재고 추가 실패: " + e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateInven(@RequestBody InvenDto invenDto) {
        logger.info("🔄 [API 요청] 재고 수정: Ingredient ID {}", invenDto.getIngredientId());

        try {
            invenService.updateInven(invenDto);
            return ResponseEntity.ok("재고가 성공적으로 업데이트되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("업데이트 실패: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{ingredientId}")
    public ResponseEntity<String> deleteInven(@PathVariable Integer ingredientId) {
        logger.info("🗑 [API 요청] 재고 삭제: Ingredient ID {}", ingredientId);

        try {
            invenService.deleteInven(ingredientId);
            return ResponseEntity.ok("재고가 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            logger.error("❌ [API 실패] 재고 삭제 중 오류 발생", e);
            return ResponseEntity.badRequest().body("재고 삭제 실패: " + e.getMessage());
        }
    }
}
