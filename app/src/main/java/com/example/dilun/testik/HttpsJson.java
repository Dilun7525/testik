package com.example.dilun.testik;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

/**
 * Класс для получения JSON объекта
 * (пока не получается связаться с сервером по https)
 * Тестовый класс
 */

public class HttpsJson extends AsyncTask<Void, Void, String> {

    static final String LOGS = "LOG_______";
    protected BufferedReader reader = null;
    protected String resultJson = "";
    protected String urlJson = "";


    public HttpsJson(String urlJson) {
        this.urlJson = urlJson;
    }

    // получить результат класса
    public JSONObject getResultJson() {

        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(resultJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObj;
    }

    @Override
    protected String doInBackground(Void... params) {
/*
* Далее создаём экземпляр класса HttpsURLConnection и
* устанавливаем созданный нами объект HostnameVerifier
* в качестве проверяющего для данного конкретного соединения.*/

        HostnameVerifier hostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                HostnameVerifier hv = HttpsURLConnection.getDefaultHostnameVerifier();
                return hv.verify("example.com", session);
            }
        };

        // получаем данные с внешнего ресурса
        Log.d(LOGS, "HttpsJson: potok");
        try {
            Log.d(LOGS, "HttpsJson: potok_inside");

            URL url = new URL(urlJson);

            Log.d(LOGS, "HttpsJson: url: " + url.toString());

            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setHostnameVerifier(hostnameVerifier);

            urlConnection.connect();

            Log.d(LOGS, "HttpsJson: url: коннект");
            //При получении ответа от сервера
            if (urlConnection.getResponseCode() == HttpsURLConnection.HTTP_OK)

            {
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                //построчно считываем данные из потока
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                resultJson = buffer.toString();

            }else{
                resultJson ="Сервер не отвечает";
            }
            urlConnection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultJson;
    }


    @Override
    protected void onPostExecute(String strJson) {
        super.onPostExecute(strJson);
    }

}