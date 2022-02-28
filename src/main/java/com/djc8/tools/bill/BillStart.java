package com.djc8.tools.bill;

import com.tencentcloudapi.ocr.v20181119.models.VatInvoiceOCRResponse;

import java.io.*;
import java.util.*;

public class BillStart {


    public static void main(String[] args) {
        preStart();
    }

    /**
     * start前置
     */
    public static void preStart(){
        String userDir=System.getProperty("user.dir");
        //配置文件
        String fileStr=userDir+File.separator+"config.json";

        if(new File(fileStr).exists()){
            ConfigModel configModel=BillJson.readConfig(fileStr);
            PrintStream ps ;
            try {
                ps=new PrintStream(new File(configModel.logpath+File.separator+"process.log"));
                System.setOut(ps);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            System.out.println(configModel);

            start(configModel);

        }else{
            System.out.println("config.json配置文件不存在,程序退出");
        }
    }

    /**
     * start
     * @param config
     */
    public static void start(ConfigModel config){
        File f=new File(config.getPdfpath());
        if(!f.exists()) {
            System.out.println(config.getPdfpath()+"路径不存在,程序退出");
            return;
        }

        String fileBak=config.getPdfpath()+File.separator+"bak-"+new Date().getTime()+File.separator;
        File bakFile=new File(fileBak);

        if(!bakFile.exists()){
            bakFile.mkdirs();
        }
        //从指定目录下取所有的pdf出来并且遍历
        List<File> fs= BillFile.getFiles(config.getPdfpath());
        if(fs!=null && fs.size()>0){
            System.out.println("获取到"+fs.size()+"个pdf文件");
        }else {
            System.out.println("没有获取到任何pdf文件,请确认路径是否选择错误!");
            return;
        }
        //成功的文件数
        int successFileNumber=0;
        for(int i=0;i<fs.size();i++){
            File file=fs.get(i);
            String pdfBase64=BillFile.PdfToBase64(file);
            //传入base64编码,用于获取识别到的pdf信息.
            VatInvoiceOCRResponse resp = DoTencentWork.getResp(config,pdfBase64);

            if(resp==null){
                //说明没有识别到发票,直接打印出失败的这个文件名称
                System.out.println("失败文件:"+file.getAbsolutePath());
                continue;
            }
            try {
                String respString=VatInvoiceOCRResponse.toJsonString(resp);

                //输出返回的报文为文件.
                BillFile.outResponseByString(respString,fileBak+File.separator+file.getName()+".json");

            } catch (IOException e) {
                e.printStackTrace();
            }

            //转换腾讯返回的pdf信息为我们可以用的实体
            BillModel bm=DoTencentWork.transBillModel(resp);
            if(bm.getBillNo()==null || "".equals(bm.getBillNo()) ||
                    bm.getLessAmot() == null || "".equals(bm.getLessAmot())){
                //说明没有识别到发票,直接打印出失败的这个文件名称
                System.out.println("失败文件:"+file.getAbsolutePath());
                continue;
            }
            successFileNumber = getSuccessFileNumber(config, fileBak, successFileNumber, file, bm);
            //

        }
        System.out.println("==================任务结束===================");
        System.out.println("获取到"+fs.size()+"个pdf文件,成功处理"+successFileNumber+"个文件");
    }


    private static int getSuccessFileNumber(ConfigModel config, String fileBak, int successFileNumber, File infile, BillModel bm) {

        String outFileName=fileBak + bm.getBillNo()+"_"+ bm.getLessAmot()+".pdf";
        String outFileName_date=fileBak + bm.getBillDate().replaceAll("年","").replaceAll("月","").replaceAll("日","")+"_"+ bm.getBillNo()+"_"+ bm.getLessAmot()+".pdf";
        String outSignName=fileBak+bm.getBillNo()+"_"+bm.getLessAmot()+"_sign.pdf";
        boolean flag=false;
        flag=BillFile.copyByChannel(infile.getAbsolutePath(),outFileName);

        if("add_date".equals(config.addOutFileDate)){
            flag=flag&&BillFile.copyByChannel(infile.getAbsolutePath(),outFileName_date);
        }
        if(flag){
            successFileNumber++;
        }
        if(config.getCopy_sign_file()!=null && config.getCopy_sign_file().length>0){
            //随机获取一个签名
            Random r=new Random();
            int i=r.nextInt(config.getCopy_sign_file().length);
            flag=BillFile.copyByChannel(config.getCopy_sign_file()[i],outSignName);
        }


        return successFileNumber;
    }


}
