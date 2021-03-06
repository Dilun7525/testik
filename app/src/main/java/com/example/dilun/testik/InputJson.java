package com.example.dilun.testik;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by dilun on 07.10.17.
 * Класс для получения JSON обьекта
 */

public class InputJson extends AsyncTask<Void, Void, String> {

    //region variable
    static final String LOGS = "LOG_______";
    protected HttpURLConnection urlConnection = null;
    protected BufferedReader reader = null;
    protected String resultJson = "";
    protected String urlJson = "";
    protected String urlMethod = "POST";
    //endregion

    //Конструкторы: 1 для работы по умолчанию с POST
    //              2 для работы с введеным методом
    public InputJson(String urlJson) {
        this.urlJson = urlJson;
    }
    public InputJson(String urlJson, String urlMetod) {
        this.urlJson = urlJson;
        this.urlMethod = urlMetod.toUpperCase();
    }

    // получить результат класса
    public JSONObject getResultJson() {

        JSONObject jsonObj =null;
        try {
            jsonObj  =new JSONObject(resultJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObj;
    }

    // Асинхронный метод для закачки ресурсов
    @Override
    protected String doInBackground(Void... params) {

        // получаем данные с внешнего ресурса
        try {
            URL url = new URL(urlJson);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(urlMethod);
            urlConnection.connect();

            //При получении ответа от сервера
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                //открываем поток для чтения
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






