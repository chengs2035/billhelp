package com.djc8.tools.bill;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.ocr.v20181119.OcrClient;
import com.tencentcloudapi.ocr.v20181119.models.VatInvoiceOCRRequest;
import com.tencentcloudapi.ocr.v20181119.models.VatInvoiceOCRResponse;

public class DoTencentWork {

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
            req.setIsPdf(true);
            req.setPdfPageNumber(1L);
            req.setImageBase64(pdfbase64);
            Thread.sleep(1000);
            return  client.VatInvoiceOCR(req);

        } catch (TencentCloudSDKException | InterruptedException e) {
            System.out.println(e.toString());
        }
        return null;
    }
}
