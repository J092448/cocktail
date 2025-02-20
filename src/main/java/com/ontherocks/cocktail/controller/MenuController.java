package com.ontherocks.cocktail.controller;

import com.ontherocks.cocktail.dto.MenuDto;  // MenuDto로 임포트
import com.ontherocks.cocktail.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MenuController {

    @Autowired
    private MenuService menuService;

    // 메뉴 리스트 조회
    @GetMapping("/menu/list")
    public ResponseEntity<List<MenuDto>> getMenuList() {
        List<MenuDto> menuList = menuService.getAllMenu();
        return ResponseEntity.ok(menuList);
    }

    // 메뉴 상세 조회 (추가할 수 있는 기능)
    @GetMapping("/menu/{menuId}")
    public ResponseEntity<MenuDto> getMenuDetail(@PathVariable int menuId) {
        MenuDto menu = menuService.getMenuById(menuId);
        return ResponseEntity.ok(menu);
    }
}
