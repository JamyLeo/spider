package com.cc.json.bigJson;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONReader;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class JSONUtil {

    public static void main(String[] args) {
        String jsonPath="C:\\Users\\Bainuo\\Desktop\\新建文件夹 (2)\\体检报告.json";
        List<Json> jsons=getOrderObjectsByClass(null,jsonPath,Json.class);

        for (int i=0;i<jsons.size();i++){
            String outputPath="C:\\Users\\Bainuo\\Desktop\\新建文件夹 (2)\\";

            outputPath+=jsons.get(i).getScode()+".txt";

            JSONObject report_data= (JSONObject) JSONObject.parse(jsons.get(i).getReport_data());
            if (report_data.containsKey("normal")){
                report_data.remove("normal");
            }
            String result=report_data.toJSONString();
            System.out.println(report_data);
            FileUtil.writeString(result,outputPath,"UTF-8");
        }
    }

    public static <T> List<T> getOrderObjectsByClass(String jsonName, String jsonPath, Class<T> type) {
        LinkedList<T> returnList = new LinkedList<T>();
        File file = new File(jsonPath);
        InputStreamReader isr = null;
        BufferedReader bufferedReader = null;
        try {
            isr = new InputStreamReader(new FileInputStream(file), "utf-8");
            bufferedReader = new BufferedReader(isr);

            JSONReader reader = new JSONReader(bufferedReader);
            reader.startArray();
            while (reader.hasNext()) {
                T readObject = reader.readObject(type);
                returnList.add(readObject);
            }
            reader.endArray();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != isr) {
                    isr.close();
                }
                if (null != bufferedReader) {
                    bufferedReader.close();
                }
            } catch (Exception e2) {
            }
        }
        return returnList;

    }
}
