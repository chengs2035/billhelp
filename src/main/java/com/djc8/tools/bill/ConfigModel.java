package com.djc8.tools.bill;

@lombok.Data
public class ConfigModel {
    /**
     *
     */
    String secretId;
    /**
     *
     */
    String secretKey;
    /**
     * pdf路径
     */
    String pdfpath;
    /**
     * 日志路径,如果没有填写,则默认为本地
     */
    String logpath=System.getProperty("user.dir");
    /**
     * 增加输出文件名称带日期的,可方便整理,格式:日期_发票编号_金额
     */
    String addOutFileDate;


}
