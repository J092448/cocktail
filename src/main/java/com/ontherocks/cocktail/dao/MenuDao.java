package com.ontherocks.cocktail.dao;

import com.ontherocks.cocktail.dto.MenuDto;
import com.ontherocks.cocktail.exception.MenuException;
import com.ontherocks.cocktail.mapper.MenuMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository  // ✅ DAO 클래스임을 나타내는 애너테이션 (Spring이 자동으로 Bean으로 등록)
public class MenuDao {
    private final MenuMapper menuMapper;  // ✅ MyBatis의 Mapper 인터페이스 의존성 주입

    public MenuDao(MenuMapper menuMapper) {
        this.menuMapper = menuMapper;  // ✅ 생성자를 통해 MenuMapper 객체를 주입받음
    }

    // ✅ 모든 메뉴 목록을 조회하는 메서드
    public List<MenuDto> getAllMenus() {
        try {
            return menuMapper.getAllMenus(0, Integer.MAX_VALUE);  // ✅ 전체 데이터 가져오기 위해 offset=0, size=최대값
        } catch (Exception e) {
            throw new MenuException("메뉴 목록을 불러오는 중 데이터베이스 오류 발생", e);
        }
    }


    // ✅ 특정 메뉴를 업데이트하는 메서드
    public void updateMenu(MenuDto MenuDto) {
        try {
            menuMapper.updateMenu(MenuDto);  // ✅ 데이터베이스에서 해당 메뉴 업데이트 실행
        } catch (Exception e) {
            throw new MenuException("메뉴 수정 중 데이터베이스 오류 발생", e);
            // ✅ 예외 발생 시 `MenuException`을 던짐
        }
    }


}
