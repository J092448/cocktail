package com.ontherocks.cocktail.dao;

import com.ontherocks.cocktail.dto.InvenDto;
import com.ontherocks.cocktail.mapper.InvenMapper;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@Repository
public class InvenDao {
    private static final Logger logger = LoggerFactory.getLogger(InvenDao.class);
    private final InvenMapper invenMapper;

    public InvenDao(InvenMapper invenMapper) {
        this.invenMapper = invenMapper;
    }

    public List<InvenDto> getInvenList(String category, int pageSize, int offset) {
        if ("all".equals(category)) {
            category = null;
        }
        logger.debug("📊 [DAO 요청] 재고 목록 조회 - 카테고리: '{}'", (category == null ? "전체" : category));
        return invenMapper.getInvenList(category, pageSize, offset);
    }

    public int getTotalInventoriesCount(String category) {
        return invenMapper.getTotalInventoriesCount(category);
    }

    public InvenDto getInvenDetail(Integer ingredientId) {
        logger.debug("🔍 [DAO 요청] 재고 상세 조회 | Ingredient ID: {}", ingredientId);
        return invenMapper.getInvenDetail(ingredientId);
    }

    public void insertInven(InvenDto invenDto) {
        logger.debug("🆕 [DAO 요청] 재고 추가 | Ingredient ID: {}", invenDto.getIngredientId());
        invenMapper.insertInven(invenDto);
    }

    public int updateInven(InvenDto invenDto) {
        logger.debug("🔄 [DAO 요청] 재고 정보 수정 | Ingredient ID: {}", invenDto.getIngredientId());
        int updatedRows = invenMapper.updateInven(invenDto);

        if (updatedRows > 0) {
            logger.info("✅ [업데이트 성공] Ingredient ID: {}, 새로운 수량: {}, 새로운 상태: {}",
                    invenDto.getIngredientId(), invenDto.getQuantity(), invenDto.getStatus());
        } else {
            logger.warn("❌ [업데이트 실패] Ingredient ID: {}", invenDto.getIngredientId());
        }

        return updatedRows;
    }

    public void deleteInven(Integer ingredientId) {
        logger.debug("🗑 [DAO 요청] 재고 삭제 | Ingredient ID: {}", ingredientId);
        invenMapper.deleteInven(ingredientId);
    }
}
