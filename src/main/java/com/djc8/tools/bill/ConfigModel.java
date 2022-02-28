package com.djc8.tools.bill;

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
    private String[] copy_sign_file;


    public ConfigModel() {
    }

    public String getSecretId() {
        return this.secretId;
    }

    public String getSecretKey() {
        return this.secretKey;
    }

    public String getPdfpath() {
        return this.pdfpath;
    }

    public String getLogpath() {
        return this.logpath;
    }

    public String getAddOutFileDate() {
        return this.addOutFileDate;
    }


    public void setSecretId(String secretId) {
        this.secretId = secretId;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public void setPdfpath(String pdfpath) {
        this.pdfpath = pdfpath;
    }

    public void setLogpath(String logpath) {
        this.logpath = logpath;
    }

    public void setAddOutFileDate(String addOutFileDate) {
        this.addOutFileDate = addOutFileDate;
    }


    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ConfigModel)) return false;
        final ConfigModel other = (ConfigModel) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$secretId = this.getSecretId();
        final Object other$secretId = other.getSecretId();
        if (this$secretId == null ? other$secretId != null : !this$secretId.equals(other$secretId)) return false;
        final Object this$secretKey = this.getSecretKey();
        final Object other$secretKey = other.getSecretKey();
        if (this$secretKey == null ? other$secretKey != null : !this$secretKey.equals(other$secretKey)) return false;
        final Object this$pdfpath = this.getPdfpath();
        final Object other$pdfpath = other.getPdfpath();
        if (this$pdfpath == null ? other$pdfpath != null : !this$pdfpath.equals(other$pdfpath)) return false;
        final Object this$logpath = this.getLogpath();
        final Object other$logpath = other.getLogpath();
        if (this$logpath == null ? other$logpath != null : !this$logpath.equals(other$logpath)) return false;
        final Object this$addOutFileDate = this.getAddOutFileDate();
        final Object other$addOutFileDate = other.getAddOutFileDate();
        if (this$addOutFileDate == null ? other$addOutFileDate != null : !this$addOutFileDate.equals(other$addOutFileDate))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ConfigModel;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $secretId = this.getSecretId();
        result = result * PRIME + ($secretId == null ? 43 : $secretId.hashCode());
        final Object $secretKey = this.getSecretKey();
        result = result * PRIME + ($secretKey == null ? 43 : $secretKey.hashCode());
        final Object $pdfpath = this.getPdfpath();
        result = result * PRIME + ($pdfpath == null ? 43 : $pdfpath.hashCode());
        final Object $logpath = this.getLogpath();
        result = result * PRIME + ($logpath == null ? 43 : $logpath.hashCode());
        final Object $addOutFileDate = this.getAddOutFileDate();
        result = result * PRIME + ($addOutFileDate == null ? 43 : $addOutFileDate.hashCode());

        return result;
    }

    public String toString() {
        return "ConfigModel(secretId=" + this.getSecretId() + ", secretKey=" + this.getSecretKey() + ", pdfpath=" + this.getPdfpath() + ", logpath=" + this.getLogpath() + ", addOutFileDate=" + this.getAddOutFileDate() + ")";
    }

    /**
     * 同步输出签名页
     */
    public String[] getCopy_sign_file() {
        return copy_sign_file;
    }

    public void setCopy_sign_file(String[] copy_sign_file) {
        this.copy_sign_file = copy_sign_file;
    }
}
