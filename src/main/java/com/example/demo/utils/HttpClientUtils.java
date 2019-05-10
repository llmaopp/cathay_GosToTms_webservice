package com.example.demo.utils;

import com.example.demo.model.InterfaceMonitor;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @ClassName: HttpsUtils
 * @Description: TODO(https post忽略证书请求)
 */
@Configuration
public class HttpClientUtils {
    private static Logger log = LoggerFactory.getLogger(RemoteIpUtil.class);
    private static final String CHARSET = "utf-8";
    private static final String HTTP = "http";
    private static final String HTTPS = "https";
    private static SSLConnectionSocketFactory sslsf = null;
    private static PoolingHttpClientConnectionManager cm = null;
    private static SSLContextBuilder builder = null;

    static {
        try {
            builder = new SSLContextBuilder();
            // 全部信任 不做身份鉴定
            builder.loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    return true;
                }
            });
            sslsf = new SSLConnectionSocketFactory(builder.build(),
                    new String[]{"SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.2"}, null, NoopHostnameVerifier.INSTANCE);
            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register(HTTP, new PlainConnectionSocketFactory()).register(HTTPS, sslsf).build();
            cm = new PoolingHttpClientConnectionManager(registry);
            cm.setMaxTotal(200);// max connection
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * httpClient post请求
     *
     * @param url    请求url
     * @param entity 请求实体 json/xml提交适用
     * @return 可能为空 需要处理
     * @throws Exception
     */
    public static String post(String url, StringEntity entity)
            throws Exception {
        String result = "";
        CloseableHttpClient httpClient = null;
        try {
            httpClient = getHttpClient();

            HttpPost httpPost = new HttpPost(url);//Post请求
            // 设置实体 优先级高
            if (entity != null) {
                httpPost.setHeader("User-agent", "WEB");
                httpPost.setHeader("x-tymh-client-ip", RemoteIpUtil.getRemoteIp());
            }
            HttpResponse httpResponse = httpClient.execute(httpPost);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            System.out.println("状态码：" + statusCode);
            if (statusCode >= 200 && statusCode < 300) {
                HttpEntity resEntity = httpResponse.getEntity();
                result = EntityUtils.toString(resEntity);
            } else {
                readHttpResponse(httpResponse);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (httpClient != null) {
                httpClient.close();
            }
        }
        return result;
    }

    public static String postXML(String url,String xml){
        CloseableHttpClient client = null;
        CloseableHttpResponse resp = null;
        try{
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-Type", "text/xml; charset=UTF-8");
            client = HttpClients.createDefault();
            StringEntity entityParams = new StringEntity(xml,"utf-8");
            httpPost.setEntity(entityParams);
            client = HttpClients.createDefault();
            resp = client.execute(httpPost);
            String resultMsg = EntityUtils.toString(resp.getEntity(),"utf-8");
            return resultMsg;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(client!=null){
                    client.close();
                }
                if(resp != null){
                    resp.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static CloseableHttpClient getHttpClient() throws Exception {
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).setConnectionManager(cm)
                .setConnectionManagerShared(true).build();
        return httpClient;
    }

    public static String readHttpResponse(HttpResponse httpResponse) throws ParseException, IOException {
        StringBuilder builder = new StringBuilder();
        // 获取响应消息实体
        HttpEntity entity = httpResponse.getEntity();
        // 响应状态
        builder.append("status:" + httpResponse.getStatusLine());
        builder.append("headers:");
        HeaderIterator iterator = httpResponse.headerIterator();
        while (iterator.hasNext()) {
            builder.append("\t" + iterator.next());
        }
        // 判断响应实体是否为空
        if (entity != null) {
            String responseString = EntityUtils.toString(entity);
            builder.append("response length:" + responseString.length());
            builder.append("response content:" + responseString.replace("\r\n", ""));
        }
        return builder.toString();
    }

public static String post(String url, String str, ContentType contentType) throws Throwable {
    //时钟
    long startTime = System.currentTimeMillis();
    url = addReqSeq(url);
    String ret = null;
    //创建封装接口的model
    InterfaceMonitor interfaceMonitor  =new InterfaceMonitor();

    try (CloseableHttpClient httpClient = CloseableHttpClientPool.getHttpclient()) {
        final HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("User-agent", "WEB");
        httpPost.setHeader("x-tymh-client-ip", RemoteIpUtil.getRemoteIp());
        StringEntity entity = new StringEntity(str, contentType);
        httpPost.setEntity(entity);
        final ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
            @Override
            public String handleResponse(HttpResponse response) throws IOException {
                int status = response.getStatusLine().getStatusCode();
                interfaceMonitor.setResCode(String.valueOf(status));

                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity, CHARSET) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            }
        };
        ret = httpClient.execute(httpPost, responseHandler);

        //封装接口信息
        Date date=new Date();
        SimpleDateFormat edf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        interfaceMonitor.setCreateTime(edf.format(date));
        interfaceMonitor.setInterfaceUrl(url);
        interfaceMonitor.setInterfaceRunTime(Long.toString(System.currentTimeMillis() - startTime));
        interfaceMonitor.setReqJson(str);
        interfaceMonitor.setResJson(ret);
        interfaceMonitor.setReqType("post");

//        //发送消息
//        InterfaceMonitorSender.sendMessage(interfaceMonitor);


        return ret;
    } finally {
        log.info("running time {} ms, post url: {} ,param : {}, return : {}",
                System.currentTimeMillis() - startTime, url, str, ret);
    }
}

    /**
     * 每个请求后面增加 reqSeq时间戳
     *
     * @param url URL 地址
     * @return 加上时间戳的URL
     */
    private static String addReqSeq(String url) {
        String reqSeq = String.valueOf((new Date()).getTime());
        if (url.indexOf("?") > 0) {
            url = url + "&reqSeq=" + reqSeq;
        } else {
            url = url + "?reqSeq=" + reqSeq;
        }
        return url;
    }

}

