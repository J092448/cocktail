package com.ontherocks.cocktail.service;

import com.ontherocks.cocktail.dto.MenuDto;
import com.ontherocks.cocktail.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    @Autowired
    private MenuMapper menuMapper;

    public List<MenuDto> getAllMenu() {
        return menuMapper.findAll();  // 메서드 호출을 findAll()로 변경
    }

    public MenuDto getMenuById(int menuId) {
        return menuMapper.findById((long) menuId);
    }
}
