package com.djc8.tools.bill;

import com.tencentcloudapi.ocr.v20181119.models.TextVatInvoice;
import com.tencentcloudapi.ocr.v20181119.models.VatInvoiceOCRResponse;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.*;

import java.util.function.Predicate;

public class BillStart {


    public static void main(String[] args) {


        String userdir=System.getProperty("user.dir");
        String fileStr=userdir+File.separator+"config.json";

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


        //获取配置文件

    }

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
              //  System.out.println(respString);
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
        }
        System.out.println("==================任务结束===================");
        System.out.println("获取到"+fs.size()+"个pdf文件,成功处理"+successFileNumber+"个文件");

    }

    private static int getSuccessFileNumber(ConfigModel config, String fileBak, int successFileNumber, File file, BillModel bm) {
        //构建输出的文件
        File outF=new File(fileBak + bm.getBillNo()+"_"+ bm.getLessAmot()+".pdf");
        File outF_date=new File(fileBak + bm.getBillDate().replaceAll("年","").replaceAll("月","").replaceAll("日","")+"_"+ bm.getBillNo()+"_"+ bm.getLessAmot()+".pdf");
        //重命名文件到back目录
        FileChannel inputChannel=null;
        FileChannel outChannel=null;

        try{
            inputChannel = new FileInputStream(file).getChannel();
            outChannel=new FileOutputStream(outF).getChannel();

            outChannel.transferFrom(inputChannel,0,inputChannel.size());

            if("add_date".equals(config.addOutFileDate)){
                outChannel=new FileOutputStream(outF_date).getChannel();
                inputChannel = new FileInputStream(file).getChannel();
                outChannel.transferFrom(inputChannel,0,inputChannel.size());
            }

            successFileNumber++;
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{

        }
        return successFileNumber;
    }


}
