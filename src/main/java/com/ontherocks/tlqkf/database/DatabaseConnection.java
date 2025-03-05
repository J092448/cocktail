package com.ontherocks.tlqkf.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static void main(String[] args) {
        String url = "jdbc:mysql://192.168.0.24:3306/cocktail_bar_erp?serverTimezone=UTC";
        String user = "ontherocks"; // 실제 사용자명
        String password = "1111"; // 실제 비밀번호

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            if (conn != null) {
                System.out.println("✅ 데이터베이스 연결 성공!");
            }
        } catch (SQLException e) {
            System.out.println("❌ 데이터베이스 연결 실패!");
            e.printStackTrace();
        }
    }
}
