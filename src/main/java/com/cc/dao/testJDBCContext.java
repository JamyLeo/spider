package com.cc.dao;


import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class testJDBCContext {
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
        try {
            ps = conn.prepareStatement(insertsql);
            File file = new File("C:\\Users\\Bainuo\\Desktop\\基因综合建议数据0514\\1.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader br=new BufferedReader(fileReader);
            String str=null;
            int id=2;
            while (id<=66){
                str=br.readLine();
                if (str!=null){
                    String result="[";
                    for (String s:str.split(",")){
                        result+=s;
                    }
                    result+=("]");
//                    System.out.println(result);
                    ps.setString(1,result );
                    ps.setInt(2,id );
                    ps.executeUpdate();
                }
                id++;
            }
            System.out.println("成功向数据库插入文本 ");
        } catch (SQLException e) {
            System.out.println("SQLException");
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException ");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws SQLException {

        testJDBCContext t = new testJDBCContext();
        t.connectionDB();
        t.WriteContextIntoDB();
//        System.out.println("读取成功，请到E盘查看");


    }
}