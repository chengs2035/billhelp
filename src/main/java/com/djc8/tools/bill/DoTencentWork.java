package com.djc8.tools.bill;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.ocr.v20181119.OcrClient;
import com.tencentcloudapi.ocr.v20181119.models.TextVatInvoice;
import com.tencentcloudapi.ocr.v20181119.models.VatInvoiceOCRRequest;
import com.tencentcloudapi.ocr.v20181119.models.VatInvoiceOCRResponse;

import java.util.Arrays;
import java.util.function.Predicate;

public class DoTencentWork {

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
    /**
     * ocr票据识别,只需要传入一个pdf的base64位编码就行了
     * @param pdfbase64
     * @return
     */
    public static VatInvoiceOCRResponse getResp(ConfigModel config,String pdfbase64){
        try {
            // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
            // 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取

            Credential cred = new Credential(config.getSecretId(), config.getSecretKey());

            // 实例化一个http选项，可选的，没有特殊需求可以跳过
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("ocr.tencentcloudapi.com");

            // 实例化一个client选项，可选的，没有特殊需求可以跳过
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            // 实例化要请求产品的client对象,clientProfile是可选的
            OcrClient client = new OcrClient(cred, "ap-guangzhou", clientProfile);
            // 实例化一个请求对象,每个接口都会对应一个request对象

            VatInvoiceOCRRequest req = new VatInvoiceOCRRequest();
            //是否pdf,后续版本去掉了这参数.
            req.setIsPdf(true);
            //pdf页数,后续版本去掉了这参数
            req.setPdfPageNumber(1L);

            req.setImageBase64(pdfbase64);
            //慢速模式,强制休眠1秒
            Thread.sleep(1000);

            return  client.VatInvoiceOCR(req);

        } catch (TencentCloudSDKException | InterruptedException e) {
            System.out.println(e.toString());
        }
        return null;
    }
}
