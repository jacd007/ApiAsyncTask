package com.zippyttech.libasynctask;

/**
 * Created by Darwin C on 5/6/19.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by zippyttech on 01/12/16.
 */

public class ApiCall {
    private static final String TAG = "ApiCall";
    HttpEntity<String> requestEntity;
    HttpHeaders requestHeaders;
    public static final String SHARED_PREFERENCES_KEY = "ActivitySharedPreferences_data";
    Context context;

    public ApiCall(Context context) {
        this.context = context;
        SharedPreferences settings;
        settings = context.getSharedPreferences(context.getString(R.string.shared_key), 0);

        String accessToken = settings.getString("accessToken", "");


        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.getDefault());
        Date currentLocalTime = calendar.getTime();
        DateFormat date = new SimpleDateFormat("Z");
        String localTime = date.format(currentLocalTime);

        try {
            SSLContext sslc = SSLContext.getInstance("TLS");
            TrustManager[] trustManagerArray = { new NullX509TrustManager() };
            sslc.init(null, trustManagerArray, null);
            HttpsURLConnection.setDefaultSSLSocketFactory(sslc.getSocketFactory());

        } catch(Exception e) {
            e.printStackTrace();
        }

        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {

                if(session.isValid())
                    return true;
                else
                    return false;
            }
        });

        requestHeaders = new HttpHeaders();
        requestHeaders.add("Content-Type", "application/json;charset=UTF-8");
        requestHeaders.set("Authorization",accessToken);
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        requestHeaders.set("x-TimeZone", localTime);
        requestHeaders.set("Accept-Language", Locale.getDefault().getLanguage());
        requestHeaders.set("User-Agent", System.getProperty("http.agent"));


    }

    private static class NullX509TrustManager implements X509TrustManager {
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            System.out.println();
        }
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            System.out.println();
        }
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }


    public String callGet(String url) {
        Log.i("GET REQUEST", url);
        String resp = null;
        try {
            requestEntity = new HttpEntity<String>(requestHeaders);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
            resp = responseEntity.getBody();

            Log.i("GETAlgo", resp+"");

        } catch (RestClientException e) {

            if(e instanceof HttpStatusCodeException) {
                resp = "error "+ ((HttpStatusCodeException) e).getStatusCode()+" " +((HttpStatusCodeException) e).getResponseBodyAsString();

                Log.i("GET", "error:" +  ((HttpStatusCodeException) e).getStatusCode()+" " + ((HttpStatusCodeException) e).getResponseBodyAsString());


                HttpStatus statusCode = ((HttpStatusCodeException) e).getStatusCode();
                if( statusCode == HttpStatus.UNAUTHORIZED){
                    Log.i("StatusCode get", statusCode.toString());
                    logout();
                }
            }
        }
        return resp;
    }

    public String callPost(String url, String data) {
        Log.i("POST REQUEST", "url:" + url);
        Log.i("POST REQUEST", "data"+data);
        String resp = null;
        try {
            HttpEntity<String> entity = new HttpEntity<String>(data, requestHeaders);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

            return response.getBody();
//            int status = response.getStatusCode().value();
//            if (status == 200 || status == 201 )
//                resp = response.getBody();
//            else if(status == 422)
//                resp = response.getBody();
//            else
//                resp = "{\"error\":" + status+"}";
//
//            Log.i("POST", "resp "+resp);

        } catch(RestClientException e){
            //process exception
            if(e instanceof HttpStatusCodeException) {
                resp =  ((HttpStatusCodeException) e).getResponseBodyAsString();

                Log.i("POST", "error:" + resp);

                HttpStatus statusCode = ((HttpStatusCodeException) e).getStatusCode();

                Log.i("StatusCode", statusCode.toString());
                if( statusCode == HttpStatus.UNAUTHORIZED){
                    Log.i("StatusCode", statusCode.toString());
                }
            }
        }
        return resp;
    }


    public String callPut(String url, String data) {
        Log.i("PUT REQUEST", url);
        String resp = null;
        try {
            HttpEntity<String> entity = new HttpEntity<String>(data, requestHeaders);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
            resp = response.getBody();

            Log.i("PUT", resp);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resp;
    }

    public String callDelete(String url) {
        Log.i("DELETE REQUEST", url);
        String resp = null;
        try {
            HttpEntity<String> entity = new HttpEntity<String>(requestHeaders);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);
            resp = response.getBody();

            Log.i("DELETE", "Algo" +resp);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resp;
    }

    /**
     * logout session active
     */
    public void logout() {

        SharedPreferences settings = context.getSharedPreferences(context.getString(R.string.shared_key), 0);



        String providerId = settings.getString("providerId", "");
        Log.d("logout", "provider:" + providerId);



        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.commit();


//        Intent intent = new Intent(context, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent);

    }

}
