package com.snm.snauth.controller.handler;

import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GoogleRequestHandler {
    private HttpServletRequest request;
    private String appUrl;
    String appId;
    String appSecret;

    public GoogleRequestHandler(HttpServletRequest request, String appUrl, String appId, String appSecret) {
        this.request = request;
        this.appUrl = appUrl;
        this.appId = appId;
        this.appSecret = appSecret;
    }

    private String getAuthCode() throws IOException {
        String error = request.getParameter("error");
        if (error != null && !error.isEmpty()) {
            return "";
        }

        return request.getParameter("code");
    }

    public String getGoogleAccessToken(String redirectFunction) throws Exception {
        String authCode = getAuthCode();
        String accessToken;

        if (authCode != null && !authCode.equals("")) {
            accessToken = getTokenByRequest(authCode, redirectFunction);
        }
        else {
            return null;
        }

        return accessToken;
    }

    public JSONObject getGoogleUserData(String accessToken) throws Exception {
        String newUrl = new String("https://www.googleapis.com/oauth2/v1/userinfo?" +
                "&access_token=" + accessToken);

        JSONObject responseJson = getJsonByRequest(newUrl);

        if (responseJson != null) {
            return responseJson;
        }
        else {
            return null;
        }
    }

    private String getTokenByRequest(String authCode, String redirectFunction) {
        String accessToken;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://accounts.google.com/o/oauth2/token");
        //HttpPost httpPost = new HttpPost("https://www.googleapis.com/oauth2/v4/token");
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        try {
            List<NameValuePair> nameValuePairList = new ArrayList<>();
            nameValuePairList.add(new BasicNameValuePair("code", authCode));
            nameValuePairList.add(new BasicNameValuePair("client_id", appId));
            nameValuePairList.add(new BasicNameValuePair("client_secret", appSecret));
            nameValuePairList.add(new BasicNameValuePair("redirect_uri", appUrl + redirectFunction));
            nameValuePairList.add(new BasicNameValuePair("grant_type", "authorization_code"));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairList));
            String responseBody = httpClient.execute(httpPost, responseHandler);
            JSONObject responseJson = new JSONObject(responseBody);

            if (responseJson != null && !responseJson.has("error")) {
                accessToken = responseJson.getString("access_token");
            }
            else {
                return null;
            }
        }
        catch (Exception e) {
            return null;
        }

        return accessToken;
    }

    private JSONObject getJsonByRequest(String url) {
        JSONObject responseJson;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        try {
            String responseBody = httpClient.execute(httpGet, responseHandler);
            responseJson = new JSONObject(responseBody);

            if (responseJson.has("error")) {
                return null;
            }
        }
        catch (Exception e) {
            return null;
        }

        return responseJson;
    }
}
