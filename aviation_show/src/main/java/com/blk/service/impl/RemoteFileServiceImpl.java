
package com.blk.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.blk.common.MeasureConstant;
import com.blk.model.vo.Record;
import com.blk.service.MeasureDataLatestService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.cert.X509Certificate;
import javax.annotation.Resource;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class RemoteFileServiceImpl {


    @Resource
    private MeasureDataLatestService measureDataLatestService;

    @Autowired
    private RestTemplate restTemplate;

    public void getRemoteFile(String fileName) {
        log.info("开始获取文件：{}", fileName);
        try {
            restTemplate = createRestTemplateWithNoSSLVerification();
        } catch (Exception e) {
            log.error("配置RestTemplate时发生错误", e);
            e.printStackTrace();
        }

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(MeasureConstant.FILE_MEASURE_IP + MeasureConstant.FILE_MEASURE_URL)
                .queryParam("fileName", fileName)
                .queryParam("fileFolder", MeasureConstant.FILE_FOLDER);
        String url = builder.toUriString();
        log.debug("构建的URL: {}", url);
        // 创建请求头
        HttpHeaders headers = new HttpHeaders();
        headers.set(MeasureConstant.REQUEST_HEADER_KEY_ID, MeasureConstant.REQUEST_HEADER_VALUE_ID);
        headers.set(MeasureConstant.REQUEST_HEADER_KEY_APPKEY, MeasureConstant.REQUEST_HEADER_VALUE_APPKEY);

        // 创建HttpEntity对象
        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        // 发送请求并获取响应
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
        );

        try {
            if (response.getBody() == null || response.getBody().isEmpty()) {
                log.warn("响应体为空或无效");
                return;
            }

            JSONObject responseData = JSONObject.parseObject(response.getBody());
            log.info("解析的响应数据: {}", responseData.toJSONString());


            Date eventTime = responseData.getDate("sendDateTime");
            String substationId = responseData.getString("substationId");
            List<Record> records = responseData.getJSONArray("body").toJavaList(Record.class);

            if (records != null && !records.isEmpty()) {
                measureDataLatestService.add(records, eventTime, substationId);
            } else {
                log.warn("记录列表为空");
            }

        } catch (Exception e) {
            log.error("处理文件时发生错误，文件名: {}, URL: {}, 响应: {}", fileName, url, response, e);
            e.printStackTrace();
        }
    }


    private RestTemplate createRestTemplateWithNoSSLVerification() throws NoSuchAlgorithmException, KeyManagementException {
        // 创建一个忽略SSL证书验证的SSLContext
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {}

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {}

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        }}, new java.security.SecureRandom());

        // 创建一个忽略主机名验证的SSLConnectionSocketFactory
        SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);

        // 创建一个使用自定义SSLContext的HttpClient
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(sslSocketFactory)
                .build();

        // 创建一个使用自定义HttpClient的RequestFactory
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);

        // 返回一个新的RestTemplate实例
        return new RestTemplate(requestFactory);
    }


}