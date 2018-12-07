package com.huada.demo.http;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: HttpClient使用接口，通过spring容器注入
 **/
@Service
public class HttpAPIService {

    @Autowired
    private CloseableHttpClient httpClient;

    @Autowired
    private RequestConfig config;


    /**
     * get请求,不带参数，不带token,默认utf-8字符集
     *
     * @param url
     * @return
     * @throws Exception
     */
    public String doGet(String url) throws Exception {
        // 声明 http get 请求
        HttpGet httpGet = new HttpGet(url);

        // 装载配置信息
        httpGet.setConfig(config);

        // 发起请求
        CloseableHttpResponse response = this.httpClient.execute(httpGet);

        // 返回响应体的内容
        return EntityUtils.toString(response.getEntity(), "UTF-8");
    }

    /**
     * @Description: get请求，不带参数,带token，默认字符集utf-8
     * @Param: [url, token]
     * @return: java.lang.String
     */
    public String doGet(String url, String token) throws Exception {
        // 声明 http get 请求
        HttpGet httpGet = new HttpGet(url);

        // 装载配置信息
        httpGet.setConfig(config);
        // 浏览器表示
        httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.7.6)");
        // 传输的类型
        httpGet.addHeader("Content-Type", "application/x-www-form-urlencoded");
        httpGet.addHeader("Authorization", "Bearer "+token);
        // 发起请求
        CloseableHttpResponse response = this.httpClient.execute(httpGet);

        // 返回响应体的内容
        return EntityUtils.toString(response.getEntity(), "utf-8");
    }


    /**
     * get请求，带参数，不带token,默认字符集utf-8
     *
     * @param url
     * @return
     * @throws Exception
     */
    public String doGet(String url, Map<String, Object> map) throws Exception {
        URIBuilder uriBuilder = new URIBuilder(url);

        if (map != null) {
            // 遍历map,拼接请求参数
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
            }
        }

        // 调用不带参数的get请求
        return this.doGet(uriBuilder.build().toString());
    }


    /**
     * @Description: get请求，带参数，带token，默认字符集utf-8
     * @Param: [url, map, token]
     * @return: java.lang.String
     */
    public String doGet(String url, Map<String, Object> map, String token) throws Exception {
        URIBuilder uriBuilder = new URIBuilder(url);

        if (map != null) {
            // 遍历map,拼接请求参数
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
            }
        }

        // 调用不带参数的get请求
        return this.doGet(uriBuilder.build().toString(), token);
    }

    /**
     * 带参数的post请求
     *
     * @param url
     * @param map
     * @return
     * @throws Exception
     */
    public HttpResult doPost(String url, Map<String, Object> map) throws Exception {
        // 声明httpPost请求
        HttpPost httpPost = new HttpPost(url);
        // 加入配置信息
        httpPost.setConfig(config);
        // 判断map是否为空，不为空则进行遍历，封装from表单对象
        if (map != null) {
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                list.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }
            // 构造from表单对象
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list, "UTF-8");

            // 把表单放到post里
            httpPost.setEntity(urlEncodedFormEntity);
        }

        // 发起请求
        CloseableHttpResponse response = this.httpClient.execute(httpPost);
        return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(
                response.getEntity(), "UTF-8"));
    }

    /**
     * @Description: post请求，带参数，带token，默认字符集utf-8
     * @Param: [url, map, token]
     * @return: com.css.openapi.propcloud.http.HttpResult
     */
    public HttpResult doPost(String url, Map<String, Object> map, String token) throws Exception {
        // 声明httpPost请求
        HttpPost httpPost = new HttpPost(url);
        // 加入配置信息
        httpPost.setConfig(config);

        // 判断map是否为空，不为空则进行遍历，封装from表单对象
        if (map != null && !map.isEmpty()) {
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                list.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }
            // 构造from表单对象
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list, "UTF-8");

            // 把表单放到post里
            httpPost.setEntity(urlEncodedFormEntity);
        }
        httpPost.addHeader("Authorization", " Bearer "+token);

        // 发起请求
        CloseableHttpResponse response = this.httpClient.execute(httpPost);
        return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(
                response.getEntity(), "UTF-8"));
    }

    /**
     * 不带参数post请求
     *
     * @param url
     * @return
     * @throws Exception
     */
    public HttpResult doPost(String url) throws Exception {
        return this.doPost(url, new HashMap<>());
    }

    /**
     * @Description: post请求，不带参数,带token，默认字符集utf-8
     * @Param: [url, token]
     * @return: java.lang.String
     */
    public HttpResult doPost(String url, String token) throws Exception {
        return this.doPost(url, new HashMap<>(), token);
    }

    /**
     * @Description: Get，设置参数，默认字符集utf-8，
     * @Param: [url, token,map]
     * @return: java.lang.String
     */
    public String doGet1(String url,String token,String uid) throws Exception {
        // 声明 http get 请求
        HttpGet httpGet = new HttpGet(url);

        // 装载配置信息
        httpGet.setConfig(config);
        // 浏览器表示
        httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.7.6)");
        // 传输的类型
        httpGet.addHeader("Content-Type", "application/x-www-form-urlencoded");
        //Header中参数设置
        httpGet.addHeader("version","0116010101");
        httpGet.addHeader("uid",uid);
        httpGet.addHeader("token",token);

        // 发起请求
        CloseableHttpResponse response = this.httpClient.execute(httpGet);

        // 返回响应体的内容
        return EntityUtils.toString(response.getEntity(), "utf-8");
    }

    public String doDelete(String url,String token) throws IOException {
        CloseableHttpResponse response = null;
        HttpDeleteWithBody httpDelete = null;
        String result = null;
        httpDelete = new HttpDeleteWithBody(url);

        httpDelete.addHeader("Content-type", "application/json; charset=utf-8");
        httpDelete.setHeader("Accept", "application/json; charset=utf-8");
        httpDelete.addHeader("Authorization", "Bearer "+token);

//            if (map != null && !map.isEmpty()) {
//                for (Map.Entry<String, String> entry : map.entrySet()) {
//                    httpDelete.addHeader(entry.getKey(),entry.getValue());
//                }
//            }
        //httpDelete.setEntity(new StringEntity(data));

        response = this.httpClient.execute(httpDelete);
//            HttpEntity entity = response.getEntity();
//
//
//            result = EntityUtils.toString(response.getEntity(), "utf-8");

        if (200 == response.getStatusLine().getStatusCode()) {
            System.out.println("*********************************1111111");
//                LOGGER.info("远程调用成功.msg={}", result);
        }
        return EntityUtils.toString(response.getEntity(), "utf-8");
    }

    public class HttpDeleteWithBody extends HttpEntityEnclosingRequestBase {
        public static final String METHOD_NAME = "DELETE";

        /**
         * 获取方法（必须重载）
         *
         * @return
         */
        @Override
        public String getMethod() {
            return METHOD_NAME;
        }

        public HttpDeleteWithBody(final String uri) {
            super();
            setURI(URI.create(uri));
        }

        public HttpDeleteWithBody(final URI uri) {
            super();
            setURI(uri);
        }

        public HttpDeleteWithBody() {
            super();
        }


    }
    /**
     * put请求，不带参数，,默认字符集utf-8
     * @param url
     * @return
     * @throws Exception
     */
    public String doPut(String url,String token,String uid) throws Exception {
        HttpPut httpPut=new HttpPut(url);
        httpPut.setConfig(config);
        httpPut.addHeader("Content-Type", "application/x-www-form-urlencoded");
        //Header中参数设置
        httpPut.addHeader("version","0116010101");
        httpPut.addHeader("uid",uid);
        httpPut.addHeader("token",token);

        CloseableHttpResponse response = this.httpClient.execute(httpPut);
        // 调用不带参数的get请求
        return EntityUtils.toString(response.getEntity(), "utf-8");
    }


}
