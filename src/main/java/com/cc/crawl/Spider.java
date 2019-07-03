package com.cc.crawl;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.sql.*;
import java.io.IOException;
import java.sql.Connection;

public class Spider {
    public static void main(String[] args) {
        Long start=System.currentTimeMillis();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String sql1="select * from gene_literature";
        String sql2="UPDATE gene_literature SET title=? WHERE id =?";
        try (
                Connection c = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test?characterEncoding=UTF-8&useSSL=false&serverTimezone=Hongkong",
                        "root", "root");
                PreparedStatement ps1 = c.prepareStatement(sql1);
                PreparedStatement ps2 = c.prepareStatement(sql2);
        )
        {
            ResultSet rs=ps1.executeQuery();
            while (rs.next()){
                int id=rs.getInt("id");
                if (id<0) {
                    continue;
                }
                Thread.sleep(2000);
                String url=rs.getString("url");
                String title=getTitle(url);
                ps2.setString(1,title);
                ps2.setInt(2,id);
//                ps2.execute();
                System.out.println(id+":"+title);
            }
            Long end=System.currentTimeMillis();
            System.out.println("总耗时："+(end-start)/1000+"s");


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public static String getTitle(String url){
        Document doc=null;

        try {
            doc= Jsoup.connect(url).userAgent("Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)").timeout(20000).get();
            Elements h1=doc.select("h1");
            for (Element h:h1){
                if (!h.text().equals("PubMed")&&!h.text().equals("PMC")){
                    return h.text();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
