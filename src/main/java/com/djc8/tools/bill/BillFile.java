package com.djc8.tools.bill;

import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class BillFile {
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
