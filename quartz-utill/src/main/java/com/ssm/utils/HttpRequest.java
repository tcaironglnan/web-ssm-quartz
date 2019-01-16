package com.ssm.utils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author Lenovo
 * @Created 2018-08-02 15:26
 **/
public class HttpRequest {

    //region http get请求

    /**
     * http请求
     *
     * @param requestUrl 请求地址
     * @return 接收到的结果
     */
    public static String httpGet(String requestUrl) {

        if (ToolStr.isBlank(requestUrl)) return "";

        HttpURLConnection httpURLConnection = getHttpURLConnection(requestUrl);
        if (httpURLConnection == null) return "";

        try (
                InputStream inputStream = httpURLConnection.getInputStream();
                //封装输入流,并且制定字符集
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
        ) {
            //通过connection链接读取输入流
            if (httpURLConnection.getResponseCode() == 200) {
                return appendData(bufferedReader);
            }
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        } finally {
            httpURLConnection.disconnect();
        }
    }

    /**
     * 拼接HttpURLConnection对象
     *
     * @param requestUrl 请求路径
     * @return ""
     */
    private static HttpURLConnection getHttpURLConnection(String requestUrl) {

        //创建远程连接对象
        try {
            URL url = new URL(requestUrl);
            //通过远程连接对象打开一个连接,强转成httpURLConnection
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            //设置连接方式
            httpURLConnection.setRequestMethod("GET");
            //设置连接主机连接服务器的超时时间
            httpURLConnection.setConnectTimeout(15000);
            //设置远程读取返回的数据时间
            httpURLConnection.setReadTimeout(60000);
            //发送请求
            httpURLConnection.connect();
            return httpURLConnection;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    //endregion

    //region http post请求

    /**
     * http的post请求
     *
     * @param requestUrl 请求路径
     * @param param      请求参数 :name1=value1&name2=value2....namen=valuen;
     * @return "
     */
    public static String httpPost(String requestUrl, String param) {

        if (ToolStr.isBlank(requestUrl) || ToolStr.isBlank(param)) return "";

        HttpURLConnection httpURLConnection = getHttpURLConnectionPost(requestUrl);
        if (httpURLConnection == null) return "";
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;

        try (
                //通过连接对象获取输出流
                OutputStream outputStream = httpURLConnection.getOutputStream()
        ) {
            //通过输出对象,把数据传输出去/写出去
            outputStream.write(param.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();

            //通过连接对象获取输入流,用于向远程读取数据
            inputStream = httpURLConnection.getInputStream();
            //对输入流对象进行封装,编码格式需要根据需求来设定
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            if (httpURLConnection.getResponseCode() == 200) {
                return appendData(bufferedReader);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpURLConnection.disconnect();
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * 拼接数据
     *
     * @param bufferedReader 字符流
     * @return 拼接结果
     * @throws IOException "
     */
    private static String appendData(BufferedReader bufferedReader) throws IOException {
        //存放数据
        StringBuilder stringBuffer = new StringBuilder();
        String temp;
        while ((temp = bufferedReader.readLine()) != null) {
            stringBuffer.append(temp).append("\r\n");
        }
        return stringBuffer.toString();
    }

    /**
     * 获取Http post请求的HttpURLConnection对象
     *
     * @param requestUrl 请求路径
     * @return HttpURLConnection
     */
    private static HttpURLConnection getHttpURLConnectionPost(String requestUrl) {

        try {
            URL url = new URL(requestUrl);
            //通过远程url打开连接
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            //设置连接请求方式
            httpURLConnection.setRequestMethod("POST");
            //设置连接主机服务器连接超时时间
            httpURLConnection.setConnectTimeout(15000);
            //设置读取服务器返回数据超时时间
            httpURLConnection.setReadTimeout(60000);

            //默认值为false 当向远程服务器传送/写数据时,需要设置成true
            httpURLConnection.setDoOutput(true);
            //默认值为true,当向远程服务器读取数据时,设置成true,该参数可有可无
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            //设置传入参数的格式,请求参数应该是:name1=value1&name2=value2的形式
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //设置鉴权信息 Authorization: Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0
            httpURLConnection.setRequestProperty("Authorization", "Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0");

            return httpURLConnection;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    //endregion

    //region HttpClient方式创建请求

    //region httpGet请求

    /**
     * httpClientGet 请求
     *
     * @param requestUrl 请求路径
     * @return ""
     */
    public static String httpClientGet(String requestUrl) {

        if (ToolStr.isBlank(requestUrl)) return "";
        HttpGet httpGet = getHttpGet(requestUrl);
        try (
                CloseableHttpClient httpClient = HttpClients.createDefault();
                //执行get请求得到返回对象
                CloseableHttpResponse response = httpClient.execute(httpGet)
        ) {
            //通过返回对象获取返回数据
            HttpEntity entity = response.getEntity();
            //通过EntityUtils中的toString方法将结果转换成字符串
            return EntityUtils.toString(entity, "UTF-8");
        } catch (IOException e) {
            return "";
        }
    }

    /**
     * 获取HttpGet
     *
     * @param requestUrl 请求路径
     * @return HttpGet
     */
    private static HttpGet getHttpGet(String requestUrl) {

        //创建一个httpGet远程连接实例
        HttpGet httpGet = new HttpGet(requestUrl);
        //设置请求头信息,鉴权
        httpGet.setHeader("Authorization", "Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0");
        //设置请求的报文头部的编码
        httpGet.setHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"));
        //设置期望服务端返回的编码
        httpGet.setHeader(new BasicHeader("Accept", "text/plain;charset=utf-8"));
        //配置连接主机服务超时时间
        RequestConfig build = RequestConfig.custom().setConnectTimeout(35000)
                //设置请求超时时间
                .setConnectionRequestTimeout(35000)
                //设置读取超时时间
                .setSocketTimeout(60000).build();
        //为httpGet实例设置配置
        httpGet.setConfig(build);

        return httpGet;
    }
    //endregion

    //region httpPost请求
    public static String httpClientPost(String sendUrl, String param) {
        CloseableHttpResponse httpResponse = null;
        try (
                CloseableHttpClient httpClient = HttpClients.custom().build()
        ) {
            HttpPost httppost = new HttpPost(sendUrl);
            //处理json格式
            StringEntity stringentity = new StringEntity(param, ContentType.create("application/json", "utf-8"));
            //处理数据流格式
            //StringEntity stringentity = new StringEntity(param, ContentType.create("application/x-www-form-urlencoded", "utf-8"));
            httppost.setEntity(stringentity);
            httpResponse = httpClient.execute(httppost);
            return EntityUtils.toString(httpResponse.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (httpResponse != null) {
                    httpResponse.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String httpClientPost(String requestUrl, Map<String, Object> paramMap) {

        if (ToolStr.isBlank(requestUrl) || ToolStr.isEmpty(paramMap)) return "";

        HttpPost httpPost = getHttpPost(requestUrl, paramMap);
        try (
                //创建HttpClient实例
                CloseableHttpClient httpClient = HttpClients.createDefault();
                //httpClient对象执行post请求,并返回响应参数对象
                CloseableHttpResponse response = httpClient.execute(httpPost)
        ) {
            //从响应对象中获取响应内容
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取HttpPost实例
     *
     * @param requestUrl 请求路径
     * @return HttpPost
     */
    private static HttpPost getHttpPost(String requestUrl, Map<String, Object> paramMap) {

        //创建HttpPost远程实例
        HttpPost httpPost = new HttpPost(requestUrl);
        //配置请求参数实例
        RequestConfig requestConfig = RequestConfig.custom()
                //设置连接主机超时时间
                .setConnectTimeout(35000)
                //设置请求连接超时时间
                .setConnectionRequestTimeout(35000)
                //设置读取远程数据超时时间
                .setSocketTimeout(60000).build();
        //为HttpPost实例设置配置
        httpPost.setConfig(requestConfig);

        //设置请求头
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        httpPost.setHeader("Accept", "text/plain;charset=utf-8");
        //封装post请求参数
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        //通过map集成entrySet方法获取entry
        paramMap.forEach((key, value) -> {
            nameValuePairs.add(new BasicNameValuePair(key, value.toString()));
        });
        //为httpPost设置封装好的请求参数
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return httpPost;
    }
    //endregion

    //endregion
}