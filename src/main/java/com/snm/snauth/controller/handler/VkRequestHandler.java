package com.snm.snauth.controller.handler;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class VkRequestHandler {
    private HttpServletRequest request;
    private String appUrl;
    String appId;
    String appSecret;

    public VkRequestHandler(HttpServletRequest request, String appUrl, String appId, String appSecret) {
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

    public JSONObject getVkAccessJson(String redirectFunction) throws Exception {
        String authCode = getAuthCode();
        JSONObject accessJson = null;

        if (authCode != null && !authCode.equals("")) {
            String newUrl = new String("https://oauth.vk.com/access_token?" +
                    "client_id=" + appId +
                    "&client_secret=" + appSecret +
                    "&code=" + authCode +
                    "&redirect_uri=" + appUrl + redirectFunction);

            accessJson = getJsonByRequest(newUrl);
        }
        else {

            return null;
        }

        return accessJson;
    }

    public JSONObject getVkUserData(String accessToken, String userId) throws Exception {
        String newUrl = new String("https://api.vk.com/method/users.get?" +
                "user_id=" + userId +
                "&fields=id,first_name,last_name" +
                "&v=5.27" +
                "&access_token=" + accessToken);

        JSONArray jsonArray = null;
        JSONObject responseJson = getJsonByRequest(newUrl);

        if (responseJson != null && !responseJson.has("error")) {
            jsonArray = responseJson.getJSONArray("response");
        }
        else {
            return null;
        }

        return jsonArray.getJSONObject(0);
    }

    private JSONObject getJsonByRequest(String url) {
        JSONObject responseJson = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        try {
            String responseBody = httpClient.execute(httpGet, responseHandler);
            responseJson = new JSONObject(responseBody);
        }
        catch (Exception e) {

            return null;
        }

        return responseJson;
    }
}
