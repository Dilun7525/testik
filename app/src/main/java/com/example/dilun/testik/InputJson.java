package com.example.dilun.testik;

//region IMPORTS

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
//endregion

/**
 * Created by dilun on 07.10.17.
 * Класс для получения JSON обьекта
 */

public class InputJson extends AsyncTask<Void, Void, String> {


    protected HttpURLConnection urlConnection = null;
    protected BufferedReader reader = null;
    protected String resultJson = "";
    protected String urlJson = "";


    public InputJson(String urlJson) {
        this.urlJson = urlJson;
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

    @Override
    protected String doInBackground(Void... params) {

        // получаем данные с внешнего ресурса
        try {
            URL url = new URL(urlJson);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.connect();

            //При получении ответа от сервера
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {

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
                Log.d("myLogs","Сервер не отвечает");
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return resultJson;

    }

    //Получение рузультирующей строки из запроса в закрытую переменную класса
    @Override
    protected void onPostExecute(String strJson) {
        super.onPostExecute(strJson);
    }

   /* protected void startJson(Void... params) {
        // получаем данные с внешнего ресурса
        Log.d(LOGWORK, "Вошли в поток");
        System.out.println("Вошли в поток");
        try {
            URL url = new URL(urlJson);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            System.out.println("buffer:" + buffer.toString());
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
                System.out.println("line:" + line);
            }

            resultJson = buffer.toString();

            reader.close();

        } catch (Exception e) {
            e.printStackTrace();

        }

        System.out.println("результат:" + resultJson);
    }*/


    // в дополнительном потоке получение из запроса результата

}






