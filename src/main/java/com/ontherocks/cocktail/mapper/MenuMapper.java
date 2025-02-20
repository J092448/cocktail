package com.ontherocks.cocktail.mapper;

import com.ontherocks.cocktail.dto.MenuDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MenuMapper {

    @Select("SELECT * FROM menu")
    List<MenuDto> findAll();  // 기존의 selectAllMenus()를 findAll()로 변경

    @Select("SELECT * FROM menu WHERE menu_id = #{menuId}")
    MenuDto findById(Long menuId);
}
