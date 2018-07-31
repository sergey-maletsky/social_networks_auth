package com.snm.snauth.controller.handler;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class FacebookRequestHandler {

    private HttpServletRequest request;
    private String appUrl;
    String appId;
    String appSecret;

    public FacebookRequestHandler(HttpServletRequest request, String appUrl, String appId, String appSecret) {
        this.request = request;
        this.appUrl = appUrl;
        this.appId = appId;
        this.appSecret = appSecret;
    }

    private String getAuthCode() throws IOException {
        String error = request.getParameter("error_reason");
        if (error != null && !error.isEmpty()) {

            return "";
        }

        return request.getParameter("code");
    }

    public String getFacebookAccessToken(String redirectFunction) throws IOException {
        String authCode = getAuthCode();
        String token;
        if (authCode != null && !authCode.equals("")) {
            String newUrl = new String("https://graph.facebook.com/oauth/access_token?" +
                    "client_id=" + appId +
                    "&redirect_uri=" + appUrl + redirectFunction +
                    "&client_secret=" + appSecret +
                    "&code=" + authCode);

            try {
                CloseableHttpClient httpClient = HttpClients.createDefault();
                HttpGet httpGet = new HttpGet(newUrl);
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                String responseBody = httpClient.execute(httpGet, responseHandler);
                token = responseBody.substring(responseBody.indexOf("=") + 1, responseBody.indexOf("&"));
            }
            catch (Exception e) {

                  return null;
            }
        }
        else {

            return null;
        }

        return token;
    }
}
