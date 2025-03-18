package com.ontherocks.cocktail.model;

// Lombok 라이브러리 import: @Getter와 @Setter 애노테이션을 통해 getter와 setter 메서드 자동 생성
import lombok.Getter;
import lombok.Setter;

// Lombok 애노테이션: getter와 setter 메서드를 자동 생성하여 코드 간결화
@Getter
@Setter
// 사용자 정보를 관리하기 위한 클래스 선언
public class User {

    // 고유 ID 필드: 각 사용자를 고유하게 식별하기 위한 필드 (주로 데이터베이스 기본 키로 사용)
    private Long id;

    // 사용자 이름 필드: 사용자가 로그인하거나 식별될 때 사용하는 이름
    private String username;

    // 사용자 비밀번호 필드: 인증을 위해 사용 (보안상 암호화를 권장)
    private String password;

    // 사용자 역할 필드: 사용자의 권한이나 역할 (예: "ADMIN", "USER")을 정의
    private String role;

    // Lombok이 자동으로 생성하지만 명시적으로 추가된 getter와 setter 메서드들

    // 사용자 ID를 반환하는 getter 메서드
    public Long getId() {
        return id;
    }

    // 사용자 ID를 설정하는 setter 메서드
    public void setId(Long id) {
        this.id = id;
    }

    // 사용자 이름을 반환하는 getter 메서드
    public String getUsername() {
        return username;
    }

    // 사용자 이름을 설정하는 setter 메서드
    public void setUsername(String username) {
        this.username = username;
    }

    // 비밀번호를 반환하는 getter 메서드
    public String getPassword() {
        return password;
    }

    // 비밀번호를 설정하는 setter 메서드
    public void setPassword(String password) {
        this.password = password;
    }

    // 역할 정보를 반환하는 getter 메서드
    public String getRole() {
        return role;
    }

    // 역할 정보를 설정하는 setter 메서드
    public void setRole(String role) {
        this.role = role;
    }
}
