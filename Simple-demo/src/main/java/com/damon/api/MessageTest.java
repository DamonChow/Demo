package com.damon.api;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 功能：飞信API 利用WAP实现程序控制发送飞信消息，此接口通过浏览"全恒壮"php接口实现开发。
 *
 * @author Damon
 * @since 2016/1/26 17:19
 */
public class MessageTest {

    /** Cookie匹配器 */
    private static final Pattern pattern = Pattern.compile("Set-Cookie: (.*?);");

    /** UID匹配器 */
    private static final Pattern uidPattern = Pattern.compile("touserid=(\\d+)&");

    /** 发送成功匹配器 */
    private static final Pattern successPattern = Pattern.compile("title=\"发送成功\"");

    /** csrfToken匹配器 */
    private static final Pattern csrfTokenPattern = Pattern.compile("name=\"csrfToken\".*?value=\"(.*?)\"");


    /** 消息编码 */
    private static final String CHARACTER = "utf-8";

    /** 缓存UID集合 */
    private static final Map<String, String> uids = new HashMap<String, String>();

    /** 手机号码 */
    private String mobile;

    /** 飞信密码 */
    private String password;

    /** cookie */
    private String cookie;

    private String csrfToten;


    @SuppressWarnings("unused")
    private MessageTest(){}


    /**
     * 初始化飞信对象
     * @param mobile 飞信手机号码
     * @param password 飞信密码
     */
    public MessageTest(String mobile, String password) {
        this.mobile = mobile;
        this.password = password;
        try {
            this.login();// 登录WAP飞信服务器
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 登录WAP飞信
     * @return
     * @throws IOException
     */
    private String login() throws IOException {
        String uri = "/huc/user/space/login.do?m=submit&fr=space";
        String data = "mobilenum=" + this.mobile + "&password="
                + URLEncoder.encode(this.password, CHARACTER);
        String result = this._request(uri, data);

        // 解析Cookie
        Matcher m = pattern.matcher(result);
        this.cookie = "";
        while (m.find()) {
            String content = m.group();
            int index = content.indexOf(":") + 1;
            this.cookie += content.substring(index, content.length());
        }
        result = this._request("/im/login/cklogin.action", "");
        return result;
    }


    /**
     * 请求信息
     * @param uri
     * @param data
     * @return
     * @throws IOException
     */
    private String _request(String uri, String data) throws IOException {
        Socket so = new Socket("f.10086.cn", 80);
        OutputStream os = so.getOutputStream();
        InputStream is = so.getInputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        osw.write("POST " + uri + " HTTP/1.1\r\n");
        osw.write("Host: f.10086.cn\r\n");
        osw.write("Cookie: " + this.cookie + " \r\n");
        osw.write("Content-Type: application/x-www-form-urlencoded\r\n");
        osw.write("User-Agent: Mozilla/5.0 (Windows NT 5.1; rv:14.0) Gecko/20100101 Firefox/14.0.1\r\n");
        osw.write("Content-Length: " + data.length() + "\r\n");
        osw.write("Connection: close\r\n\r\n");
        osw.write(data);
        osw.flush();
        String temp;
        String result = "";
        while ((temp = br.readLine()) != null) {
            result += temp + "\n";
        }

        // 关闭各种链接
        osw.close();
        os.close();
        br.close();
        isr.close();
        is.close();
        so.close();
        return result;
    }


    /**
     * 发送短信
     *
     * @param toMobile 对方手机号码
     * @param message 消息内容
     * @return int 0: 发送成功 1: 发送失败 2：发送异常
     * @throws IOException
     */
    public int send(String toMobile, String message) {
        String result;
        try {
            if (this.mobile.equals(toMobile)) {// 发送给自己
                result = this.sendMy(message);
            } else {
                String uid = getUid(toMobile);// 获取UID
                result = this._toUid(uid, message);
            }
            Matcher m = successPattern.matcher(result);
            if (m.find())
                return 0;
        } catch (Exception e) {
            return 2;
        }
        return 1;
    }


    /**
     * 获取手机号码的飞信UID
     *
     * @param mobile
     *            好友手机号码
     * @return UID
     * */
    private String getUid(String mobile) throws IOException {
        if (!uids.containsKey(mobile)) {// 如果不包含
            String uri = "/im/index/searchOtherInfoList.action";
            String data = "searchText=" + mobile;
            String result = this._request(uri, data);
            Matcher m = uidPattern.matcher(result);
            if (m.find()) {
                String str = m.group();
                int index = str.indexOf("=") + 1;
                str = str.substring(index, str.length() - 1);
                uids.put(mobile, str);// 缓存UID
            }
        }
        return uids.get(mobile);
    }


    /**
     * 发送消息给UID
     * @param uid 好友UID
     * @param message 消息内容
     * @return
     * @throws IOException
     */
    private String _toUid(String uid, String message) throws IOException {
        String uri = "/im/chat/sendMsg.action?touserid=" + uid;
        String csrfToken = this._getCsrfToken(uid);
        String data = "msg=" + URLEncoder.encode(message, CHARACTER)
                + "&csrfToken=" + csrfToken;
        String result = this._request(uri, data);
        return result;
    }


    private String _getCsrfToken(String uid) throws IOException {
        if (this.csrfToten == null) {
            String uri = "/im/chat/toinputMsg.action?touserid=" + uid;
            String result = this._request(uri, "");

            Matcher m = csrfTokenPattern.matcher(result);
            if (m.find()) {
                String str = m.group();
                int index = str.indexOf("value=\"") + 7;
                this.csrfToten = str.substring(index, str.length() - 1);
            }
        }
        return this.csrfToten;
    }


    /**
     * 发送消息给自己
     * @param message 消息内容
     * @return
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    private String sendMy(String message) throws UnsupportedEncodingException,
            IOException {
        String uri = "/im/user/sendMsgToMyselfs.action";
        return this._request(uri, "msg="
                + URLEncoder.encode(message, CHARACTER));
    }

}
