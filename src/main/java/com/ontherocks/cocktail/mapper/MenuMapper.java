package com.ontherocks.cocktail.mapper;

import com.ontherocks.cocktail.dto.MenuDto;
import com.ontherocks.cocktail.enums.MenuStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

@Mapper  // ✅ MyBatis XML과 연결
public interface MenuMapper {

    // ✅ 특정 메뉴 조회 (MyBatis XML에서 구현됨)
    MenuDto getMenuById(@Param("menuId") int menuId);

    // ✅ 전체 메뉴 개수 조회
    int getTotalMenusCount();

    // ✅ 모든 메뉴 조회 (페이징 적용)
    List<MenuDto> getAllMenus(@Param("size") int size, @Param("offset") int offset);

    // ✅ 메뉴 정보 업데이트
    void updateMenu(MenuDto MenuDto);

    // ✅ 메뉴 상태 업데이트

    void updateMenuStatus(@Param("menuId") int menuId, @Param("status") String status, @Param("cocktailId") int cocktailId);

    void insertMenu(MenuDto MenuDto);

    int getMaxCocktailId();

    Integer getMenuListCount(int menuId);

    void insertMenuList(@Param("menuId") int menuId, @Param("cocktailId") int cocktailId, @Param("status") MenuStatus status);

    Integer findCocktailIdByMenuID(@Param("menuId") int menuId);

    Integer getCocktailIdByMenuId(int menuId);

    void insertCocktail(int newCocktailId, String name, double price);

    void insertCocktail(Map<String, Object> params);



        // menu_list 테이블에서 menuId에 해당하는 행 삭제
        void deleteMenuFromMenuList(@Param("menuId") int menuId);

        // menu 테이블에서 menuId에 해당하는 행 삭제
        void deleteMenu(@Param("menuId") int menuId);

        // cocktails 테이블에서 cocktail_id 삭제
        void deleteCocktail(@Param("cocktailId") int cocktailId);

}
