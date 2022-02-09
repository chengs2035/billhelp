package com.djc8.tools.bill;

import com.alibaba.fastjson.JSON;

import java.io.*;

public class BillJson {
    public static ConfigModel readConfig(String fileName){
        String str=readJsonFile(fileName);
        return JSON.parseObject(str,ConfigModel.class);
    }
    public static String readJsonFile(String fileName){
        String jsonStr="";
        try{
            File jsonFile=new File(fileName);
            FileReader fileReader=new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
            int ch=0;
            StringBuffer sb=new StringBuffer();
            while((ch=reader.read())!=-1){
                sb.append((char)ch);
            }
            fileReader.close();
            reader.close();
            jsonStr=sb.toString();
            return jsonStr;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
