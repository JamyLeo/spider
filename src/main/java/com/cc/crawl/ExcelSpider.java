package com.cc.crawl;

import javafx.scene.control.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import sun.awt.SunHints;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelSpider {

    public static void spider() {
        try {
            String filePath = "C:\\Users\\Bainuo\\Desktop\\新建文件夹\\文献(1)(1).xlsx";

            InputStream is = new FileInputStream(filePath);
            // 构造 XSSFWorkbook 对象，strPath 传入文件路径
            XSSFWorkbook xwb = new XSSFWorkbook(is);
            // 读取第一章表格内容
            XSSFSheet sheet = xwb.getSheetAt(1);


            // 定义 row、cell,
            XSSFRow row;
            XSSFCell value;
            for (int i = sheet.getFirstRowNum() + 1; i < sheet.getLastRowNum() + 1; i++) {
                row = sheet.getRow(i);
                value = row.getCell(1);
                String url = row.getCell(2).toString().trim();
                String title = getTitle(url);
//                value.setCellValue(title);
                System.out.println(title);
/*                for (int j = 0; j < row.getLastCellNum(); j++) {
                    // 通过 row.getCell(j).toString().trim() 获取单元格内容，
                    cell = row.getCell(j).toString().trim();
                    System.out.print(cell + "\t");
                }*/
            }
//            OutputStream out = new FileOutputStream(filePath);
//            xwb.write(out);
        } catch (Exception e) {
            System.out.println("已运行xlRead() : " + e);
        }
    }

    public static String getTitle(String url) {
        Document doc = null;

        try {
            doc = Jsoup.connect(url).userAgent("Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)").timeout(20000).get();
            Elements h1 = doc.select("h1");
            Elements timeTemp = doc.select("div.cit");

            String time = "";
            String text = "";
            for (Element e : timeTemp) {
                try {
                    text = e.text().replaceAll(e.select("a").text(), "");
                    time = text.substring(0, text.indexOf(".")).trim();
                } catch (Exception e1) {
                    time += "没获取到时间";
                    System.out.println("没获取到时间");
                }
            }

            for (Element h : h1) {
                if (!h.text().equals("PubMed") && !h.text().equals("PMC")) {
                    return h.text() + time;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        spider();
    }
}
