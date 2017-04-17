package com.ugc.micropayment.util org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.helpers.DefaultHandler;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by fanjl on 2016/11/30.
 */
public class HttpClientUtil extends DefaultHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class);

    private static String postUrl = "http://cf.51welink.com/submitdata/Service.asmx/g_Submit";
    private static String sname = "DL-shanghq1";
    private static String spwd = "shang800527";
    private static String sprdid = "1012818";


    public static String SMS(String telephone, String num) {
        try {
            String postData = "sname=" + sname + "&spwd=" + spwd
                    + "&scorpid=&sprdid=" + sprdid
                    + "&sdst=" + telephone + "&smsg="
                    + java.net.URLEncoder.encode("您的验证码是" + num + "，请于1分钟内进行验证【51区块链】", "utf-8");
            //发送POST请求
            URL url = new URL(postUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setUseCaches(false);
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Length", "" + postData.length());
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            out.write(postData);
            out.flush();
            out.close();

            //获取响应状态
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                LOGGER.error("connect failed!");
                return "";
            }
            //获取响应内容体
            String line, result = "";
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            while ((line = in.readLine()) != null) {
                result += line + "\n";
            }
            in.close();
            return result;
        } catch (IOException e) {
            LOGGER.error("SMS", e);
        }
        return "";
    }
    public static String getResult(String telephone,String verification){
        return parseXml(SMS(telephone,verification));
    }


    public static String parseXml(String source){
        String[] state = source.split("State");
        String stateStr = state[2].replace(">","").replace("</","");
        return stateStr;
    }
}
