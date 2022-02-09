package com.djc8.tools.bill;

import lombok.Data;
@lombok.Data
public class BillModel {
    /**
     * 发票号码
     */
    String billNo;

    public void setBillNo(String billNo) {
        this.billNo = billNo.replaceAll("No","");
    }

    /**
     * 开票日期
     */
    String billDate;
    /**
     * 购方名称
     */
    String buyName;
    /**
     * 购方识别号
     */
    String buyNo;
    /**
     * 小计(小写)
     */
    String lessAmot;

    public void setLessAmot(String lessAmot) {
        this.lessAmot = lessAmot.replaceAll("¥","");;

    }

    /**
     * 小计(大写)
     */
    String upAmot;
    /**
     * 货物或者服务名称
     */
    String itemName;
}
