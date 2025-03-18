package com.ontherocks.cocktail.service;

import com.ontherocks.cocktail.dao.InvenDao;
import com.ontherocks.cocktail.dto.InvenDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InvenService {
    private static final Logger logger = LoggerFactory.getLogger(InvenService.class);
    private final InvenDao invenDao;

    public InvenService(InvenDao invenDao) {
        this.invenDao = invenDao;
    }

    public List<InvenDto> getInvenList(String category, int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        return invenDao.getInvenList(category, pageSize, offset);
    }

    public int getTotalInventoriesCount(String category) {
        return invenDao.getTotalInventoriesCount(category);
    }

    public InvenDto getInvenDetail(Integer ingredientId) {
        return invenDao.getInvenDetail(ingredientId);
    }

    public void addInven(InvenDto invenDto) {
        invenDao.insertInven(invenDto);
    }

    public boolean updateInven(InvenDto invenDto) {
        int updatedRows = invenDao.updateInven(invenDto);
        boolean isSuccess = updatedRows > 0;

        if (isSuccess) {
            logger.info("✅ [수정 완료] 재고명: {}, 새로운 수량: {}, 새로운 상태: {}",
                    invenDto.getName(), invenDto.getQuantity(), invenDto.getStatus());
        } else {
            logger.warn("❌ [수정 실패] 재고명: {}", invenDto.getName());
        }

        return isSuccess;
    }

    public void deleteInven(Integer ingredientId) {
        invenDao.deleteInven(ingredientId);
    }
}
