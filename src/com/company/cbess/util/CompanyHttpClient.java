package com.company.cbess.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Represents the company http client.
 */
public class CompanyHttpClient {

    public static void sendPostRequest(String url, String urlParameters, ResponseHandler responseHandler) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // add request header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", "Mozilla/5.0 BoxDemoApp");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        // get the response body content
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        // construct the response string
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // send to handler
        if (responseHandler != null) {
            responseHandler.onResponseString(url, con.getResponseCode(), response.toString());
        }
    }

    public interface ResponseHandler {
        void onResponseString(String url, int responseCode, String responseContent);
    }
}
