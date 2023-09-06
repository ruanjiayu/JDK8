package com.xian.jdk.obs;

import com.obs.services.ObsClient;
import com.obs.services.model.HttpMethodEnum;
import com.obs.services.model.TemporarySignatureRequest;
import com.obs.services.model.TemporarySignatureResponse;
import okhttp3.Request;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Description:
 * @Author: summer
 * @CreateDate: 2023/6/2 09:17
 * @Version: 1.0.0
 */
public class ObsInit {

    private static String endPoint = "obs.ap-southeast-1.myhuaweicloud.com";
    private static String ak = "UFEFAVBOBLCCZRWAGRHH";
    private static String sk = "LVj9LfJseL6eu4QJG63cy0ia72ToVQwYwfczFrBn";

    private static String BUCKET_NAME = "dspa-api";



    public static void main(String[] args) throws IOException {
        ObsClient obsClient = new ObsClient(ak, sk, endPoint);
        obsClient.putObject("dspa-api", "ruanjiayu", new ByteArrayInputStream("Hello OBS".getBytes()));


        // URL有效期，3600秒
        long expireSeconds = 3600L;

        Map<String, String> headers = new HashMap<String, String>();
        String contentType = "text/plain";
        headers.put("Content-Type", contentType);

        TemporarySignatureRequest request = new TemporarySignatureRequest(HttpMethodEnum.PUT, expireSeconds);
        request.setBucketName(BUCKET_NAME);
        request.setObjectKey(UUID.randomUUID().toString().replace("-", ""));
        request.setHeaders(headers);

        TemporarySignatureResponse response = obsClient.createTemporarySignature(request);

        System.out.println("Creating object using temporary signature url:");
        System.out.println("\t" + response.getSignedUrl());
        Request.Builder builder = new Request.Builder();
        for (Map.Entry<String, String> entry : response.getActualSignedRequestHeaders().entrySet()) {
            builder.header(entry.getKey(), entry.getValue());
        }


        obsClient.close();
    }

}
