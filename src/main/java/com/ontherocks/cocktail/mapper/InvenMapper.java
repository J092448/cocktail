package com.ontherocks.cocktail.mapper;

import com.ontherocks.cocktail.dto.InvenDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface InvenMapper {
    List<InvenDto> getInvenList(@Param("category") String category, @Param("pageSize") int pageSize, @Param("offset") int offset);

    int getTotalInventoriesCount(@Param("category") String category);

    InvenDto getInvenDetail(@Param("ingredientId") Integer ingredientId);

    void insertInven(InvenDto invenDto);

    int updateInven(InvenDto invenDto);

    void deleteInven(@Param("ingredientId") Integer ingredientId);
}
