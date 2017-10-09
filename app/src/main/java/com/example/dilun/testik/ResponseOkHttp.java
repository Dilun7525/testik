package com.example.dilun.testik;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Класс для получения JSON объекта
 * (пока не получается связаться с сервером по https)
 * Тестовый класс
 */

public class ResponseOkHttp extends AsyncTask<Void, Void, String> {
    static final String LOGS = "LOG_ResponseOkHttp:";
    private Exception exception;
    protected String urlJson = "";
    protected String resultJson = "";

    public ResponseOkHttp(String urlJson) {
        this.urlJson = urlJson;
        Log.d(LOGS, " в конструкторе");
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
        Log.d(LOGS, " в потоке");
        OkHttpClient client = new OkHttpClient();
        //MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        try {
            Log.d(LOGS, " в 1");
            Request request = new Request.Builder()
                    .url(urlJson)
                    .build();
            Log.d(LOGS, " в 2");
            Response response = client.newCall(request).execute();
            Log.d(LOGS, " в 3");
            response.body().string();
            Log.d(LOGS, " в 4");
            resultJson = response.body().string();
            Log.d(LOGS, " в resultJson="+resultJson);
            return resultJson;
        } catch (Exception e) {
            this.exception = e;
            return null;
        }
    }

    @Override
    protected void onPostExecute(String strJson) {
        super.onPostExecute(strJson);
    }
}
