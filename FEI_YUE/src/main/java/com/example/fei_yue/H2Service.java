package com.example.fei_yue;

import java.sql.*;

public class H2Service {
    private static final String DRIVER = "org.h2.Driver";
    private static final String URL = "jdbc:h2:" + AppConstants.DB_PATH;
    private static final String USER = "sa_user";
    private static final String PASSWORD = "Feifei@1227";
    private static Connection conn = null;
    private static final H2Service instance;

    static {
        instance = new H2Service();
    }

    private H2Service() {
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            if (null != conn) {
                System.out.println("DB is ready!");
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("DB is not ready!");
        }
    }

    public static H2Service getInstance() {
        return instance;
    }

    public static Connection getConn() {
        return conn;
    }

    public Integer insertHuiYuan(HuiYuan huiYuan) {
        String sql = "Insert into hui_yuan(phone, name, amount, charge_date, left_time, score) values (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, huiYuan.getPhone());
            statement.setString(2, huiYuan.getName());
            statement.setDouble(3, huiYuan.getAmount());
            statement.setString(4, huiYuan.getChargeDate());
            statement.setString(5, huiYuan.getLeftTime());
            statement.setString(6, huiYuan.getScore());
            statement.executeUpdate();
            return AppConstants.SQL_RESULT_0;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return AppConstants.SQL_RESULT_1;
        }
    }

    public HuiYuan searchByPhone(String phone) throws SQLException {
        HuiYuan huiYuan = new HuiYuan();
        String sql = "Select * from hui_yuan where phone = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, phone);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            huiYuan.setPhone(rs.getString("phone"));
            huiYuan.setName(rs.getString("name"));
            huiYuan.setAmount(rs.getDouble("amount"));
            huiYuan.setChargeDate(rs.getString("charge_date"));
            huiYuan.setLeftTime(rs.getString("left_time"));
            huiYuan.setScore(rs.getString("score"));
        }
        return huiYuan;
    }

    public Integer updateHuiYuan(HuiYuan curhuiYuan) throws SQLException {
        String phone = curhuiYuan.getPhone();
        HuiYuan existing = searchByPhone(phone);
        Integer res;
        if (existing.getPhone() == null) {
            return AppConstants.SQL_RESULT_1;
        }
        String sql = "Update hui_yuan set amount=?, charge_date=?, left_time=?, score=? where phone=?";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setDouble(1, curhuiYuan.getAmount());
            statement.setString(2, curhuiYuan.getChargeDate());
            statement.setString(3, curhuiYuan.getLeftTime());
            statement.setString(4, curhuiYuan.getScore());
            statement.setString(5, curhuiYuan.getPhone());
            statement.executeUpdate();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return AppConstants.SQL_RESULT_1;
        }
        res = insertAuditLog(curhuiYuan);
        return res;
    }

    public Integer insertAuditLog(HuiYuan curhuiYuan) {
        String sql = "Insert into audit_log(phone, name, amount, charge_date, left_time, score, audit_date) values (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, curhuiYuan.getPhone());
            statement.setString(2, curhuiYuan.getName());
            statement.setDouble(3, curhuiYuan.getAmount());
            statement.setString(4, curhuiYuan.getChargeDate());
            statement.setString(5, curhuiYuan.getLeftTime());
            statement.setString(6, curhuiYuan.getScore());
            statement.setString(7, JsonUtils.getAuditDateStr());
            statement.executeUpdate();
            return AppConstants.SQL_RESULT_0;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return AppConstants.SQL_RESULT_1;
        }
    }


}
