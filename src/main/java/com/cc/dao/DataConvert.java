package com.cc.dao;


import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataConvert {
    String url = "jdbc:mysql://127.0.0.1:3306/test?characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai";
    String driver = "com.mysql.cj.jdbc.Driver";
    String user = "root";
    String password = "root";
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet resultSet = null;

    public void connectionDB() {

        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Successfully connected");
        } catch (ClassNotFoundException e) {

            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void WriteContextIntoDB() {
        //1.将大文本插入到数据库，只需要读出 文本插入即可
        String insertsql = "UPDATE tb_report_config SET lab_examination=? WHERE id =?";
        String findsql = "select * from tb_report_config";
        try {
            ps = conn.prepareStatement(insertsql);

            System.out.println("成功向数据库插入文本 ");
        } catch (SQLException e) {
            System.out.println("SQLException");
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws SQLException {

        DataConvert t = new DataConvert();
        t.connectionDB();
        t.WriteContextIntoDB();
//        System.out.println("读取成功，请到E盘查看");


    }
}