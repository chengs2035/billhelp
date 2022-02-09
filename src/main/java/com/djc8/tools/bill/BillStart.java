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

        String fileBack=config.getPdfpath()+File.separator+"back"+new Date().getTime()+File.separator;
        File backFile=new File(fileBack);

        if(!backFile.exists()){
            backFile.mkdirs();
        }
        //从指定目录下取所有的pdf出来并且遍历
        List<File> fs= getFiles(config.getPdfpath());
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
            String pdfBase64=PdfToBase64(file);
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
                outResponseByString(respString,fileBack+File.separator+file.getName()+".json");
            } catch (IOException e) {
                e.printStackTrace();
            }

            //转换腾讯返回的pdf信息为我们可以用的实体
            BillModel bm=transBillModel(resp);
            if(bm.getBillNo()==null || "".equals(bm.getBillNo()) ||
                    bm.getLessAmot() == null || "".equals(bm.getLessAmot())){
                //说明没有识别到发票,直接打印出失败的这个文件名称
                System.out.println("失败文件:"+file.getAbsolutePath());
                continue;
            }
            //构建输出的文件
            File outF=new File(fileBack+bm.getBillNo()+"_"+bm.getLessAmot()+".pdf");
            File outF_date=new File(fileBack+bm.getBillDate().replaceAll("年","").replaceAll("月","").replaceAll("日","")+"_"+bm.getBillNo()+"_"+bm.getLessAmot()+".pdf");
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
        }
        System.out.println("==================任务结束===================");
        System.out.println("获取到"+fs.size()+"个pdf文件,成功处理"+successFileNumber+"个文件");

    }

    /**
     * 将报文输出为文件
     *
     * @param filename
     */
    public static void outResponseByString(String writeString,String filename) throws IOException {
        FileWriter fw=new FileWriter(new File(filename));
        BufferedWriter bw=new BufferedWriter(fw);
        bw.write(writeString);
        bw.close();
        fw.close();
    }
    /**
     * 将resp抓换为billmodel
     * @param resp
     * @return
     */
    public static BillModel transBillModel(VatInvoiceOCRResponse resp){
        BillModel bm=new BillModel();

        Arrays.stream(resp.getVatInvoiceInfos()).filter(new Predicate<TextVatInvoice>() {
            @Override
            public boolean test(TextVatInvoice textVatInvoice) {
                if(textVatInvoice.getName().equals("发票号码") ||
                        textVatInvoice.getName().equals("开票日期") ||
                        textVatInvoice.getName().equals("购买方名称")||
                        textVatInvoice.getName().equals("购买方识别号")||
                        textVatInvoice.getName().equals("小写金额")||
                        textVatInvoice.getName().equals("价税合计(大写)")||
                        textVatInvoice.getName().equals("货物或应税劳务、服务名称"))
                    return true;
                return false;
            }
        }).forEach(e->{

            if(e.getName().equals("发票号码")){
                bm.setBillNo(e.getValue());
            }else if(e.getName().equals("开票日期")){
                bm.setBillDate((e.getValue()));
            }else if(e.getName().equals("购买方名称")){
                bm.setBuyName(e.getValue());
            }else if(e.getName().equals("购买方识别号")){
                bm.setBuyNo(e.getValue());
            }else if(e.getName().equals("小写金额")){
                bm.setLessAmot(e.getValue());
            }else if(e.getName().equals("价税合计(大写)")){
                bm.setUpAmot(e.getValue());
            }else if(e.getName().equals("货物或应税劳务、服务名称")){
                bm.setItemName(e.getValue());
            }

        });
        return bm;
    }
//    public static void main(String[] args) {
//
//
//        start("/home/ya/IdeaProjects/billhelp/bill/202104/");
//
//    }

    /**
     * 取某个路径下的所有pdf文件
     * @param direpath
     * @return
     */
    public static List<File> getFiles(String direpath){
        List<File>  flist=new ArrayList<>();

        File fi=new File(direpath);
        if(fi.isDirectory()){

            FilenameFilter fname=new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return !new File(dir,name).isDirectory()||name.endsWith(".pdf");
                }
            };
            File[] outfiles=fi.listFiles(fname);

            for (File w : outfiles) {
                flist.add(w);
            }

        }
        return flist;
    }
    /**
     * 将pdf文件转换为Base64编码
     * @param file pdf文件
     * @return
     */
    public static String PdfToBase64(File file) {
        Base64.Encoder encoder = Base64.getEncoder();
        FileInputStream fin =null;
        BufferedInputStream bin =null;
        ByteArrayOutputStream baos = null;
        BufferedOutputStream bout =null;
        try {
            fin = new FileInputStream(file);
            bin = new BufferedInputStream(fin);
            baos = new ByteArrayOutputStream();
            bout = new BufferedOutputStream(baos);
            byte[] buffer = new byte[1024];
            int len = bin.read(buffer);
            while(len != -1){
                bout.write(buffer, 0, len);
                len = bin.read(buffer);
            }
            //刷新此输出流并强制写出所有缓冲的输出字节
            bout.flush();
            byte[] bytes = baos.toByteArray();
            return encoder.encodeToString(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                fin.close();
                bin.close();
                bout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
