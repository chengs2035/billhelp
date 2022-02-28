package com.djc8.tools.bill;

public class BillModel {
    /**
     * 发票号码
     */
    String billNo;

    public BillModel() {
    }

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

    public String getBillNo() {
        return this.billNo;
    }

    public String getBillDate() {
        return this.billDate;
    }

    public String getBuyName() {
        return this.buyName;
    }

    public String getBuyNo() {
        return this.buyNo;
    }

    public String getLessAmot() {
        return this.lessAmot;
    }

    public String getUpAmot() {
        return this.upAmot;
    }

    public String getItemName() {
        return this.itemName;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public void setBuyName(String buyName) {
        this.buyName = buyName;
    }

    public void setBuyNo(String buyNo) {
        this.buyNo = buyNo;
    }

    public void setUpAmot(String upAmot) {
        this.upAmot = upAmot;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof BillModel)) return false;
        final BillModel other = (BillModel) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$billNo = this.getBillNo();
        final Object other$billNo = other.getBillNo();
        if (this$billNo == null ? other$billNo != null : !this$billNo.equals(other$billNo)) return false;
        final Object this$billDate = this.getBillDate();
        final Object other$billDate = other.getBillDate();
        if (this$billDate == null ? other$billDate != null : !this$billDate.equals(other$billDate)) return false;
        final Object this$buyName = this.getBuyName();
        final Object other$buyName = other.getBuyName();
        if (this$buyName == null ? other$buyName != null : !this$buyName.equals(other$buyName)) return false;
        final Object this$buyNo = this.getBuyNo();
        final Object other$buyNo = other.getBuyNo();
        if (this$buyNo == null ? other$buyNo != null : !this$buyNo.equals(other$buyNo)) return false;
        final Object this$lessAmot = this.getLessAmot();
        final Object other$lessAmot = other.getLessAmot();
        if (this$lessAmot == null ? other$lessAmot != null : !this$lessAmot.equals(other$lessAmot)) return false;
        final Object this$upAmot = this.getUpAmot();
        final Object other$upAmot = other.getUpAmot();
        if (this$upAmot == null ? other$upAmot != null : !this$upAmot.equals(other$upAmot)) return false;
        final Object this$itemName = this.getItemName();
        final Object other$itemName = other.getItemName();
        if (this$itemName == null ? other$itemName != null : !this$itemName.equals(other$itemName)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof BillModel;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $billNo = this.getBillNo();
        result = result * PRIME + ($billNo == null ? 43 : $billNo.hashCode());
        final Object $billDate = this.getBillDate();
        result = result * PRIME + ($billDate == null ? 43 : $billDate.hashCode());
        final Object $buyName = this.getBuyName();
        result = result * PRIME + ($buyName == null ? 43 : $buyName.hashCode());
        final Object $buyNo = this.getBuyNo();
        result = result * PRIME + ($buyNo == null ? 43 : $buyNo.hashCode());
        final Object $lessAmot = this.getLessAmot();
        result = result * PRIME + ($lessAmot == null ? 43 : $lessAmot.hashCode());
        final Object $upAmot = this.getUpAmot();
        result = result * PRIME + ($upAmot == null ? 43 : $upAmot.hashCode());
        final Object $itemName = this.getItemName();
        result = result * PRIME + ($itemName == null ? 43 : $itemName.hashCode());
        return result;
    }

    public String toString() {
        return "BillModel(billNo=" + this.getBillNo() + ", billDate=" + this.getBillDate() + ", buyName=" + this.getBuyName() + ", buyNo=" + this.getBuyNo() + ", lessAmot=" + this.getLessAmot() + ", upAmot=" + this.getUpAmot() + ", itemName=" + this.getItemName() + ")";
    }
}
