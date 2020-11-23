package user;

import com.alibaba.fastjson.JSON;
import com.jale.weblog.user.api.dataobject.User;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;

public class TestCase {

    private final static String ONLINE_URL = "http://127.0.0.1:8081/api/";
    //private final static String ONLINE_URL = "http://8.129.114.212:8081/api/";

    private final static CloseableHttpClient HTTP_CLIENT = HttpClientBuilder.create().build();

    public String post(String url, Object obj){
        HttpPost httpPost = new HttpPost(ONLINE_URL + url);
        httpPost.setHeader("Content-Type", "application/json;charset=utf8");
        //httpPost.setHeader("token", getToken());
        StringEntity reqEntity = new StringEntity(JSON.toJSONString(obj),"utf-8");
        httpPost.setEntity(reqEntity);

        String result = "";
        try {
            HttpResponse response = HTTP_CLIENT.execute(httpPost);

            HttpEntity respEntity = response.getEntity();
            result = EntityUtils.toString(respEntity, Consts.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public String get(String url){
        HttpGet httpGet = new HttpGet(ONLINE_URL + url);
        httpGet.setHeader("Content-Type", "application/json;charset=utf8");
        //httpGet.setHeader("token", getToken());

        String result = "";
        try {
            HttpResponse response = HTTP_CLIENT.execute(httpGet);

            HttpEntity respEntity = response.getEntity();
            result = EntityUtils.toString(respEntity, Consts.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public String getToken(){
        HttpPost httpPost = new HttpPost(ONLINE_URL + "/user/login");
        httpPost.setHeader("Content-Type", "application/json;charset=utf8");

        User user = new User().setUserName("jale").setPassword("123456");
        StringEntity reqEntity = new StringEntity(JSON.toJSONString(user),"utf-8");
        httpPost.setEntity(reqEntity);

        String result = "";
        try {
            HttpResponse response = HTTP_CLIENT.execute(httpPost);

            HttpEntity respEntity = response.getEntity();
            result = EntityUtils.toString(respEntity, Consts.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return JSON.parseObject(result, Map.class).get("content").toString();
    }

}
