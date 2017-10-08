package com.example.dilun.testik;

//region IMPORTS

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//endregion
/**
 * Created by dilun on 07.10.17.
 */

class RequestTask extends AsyncTask<Void, Void, Void> {

    //private BufferedOutputStream bos;
    JSONObject jsonObj;
    protected String resultImput = "";


    // получить результат класса
    public String getResultJson() {
        return this.resultImput;
    }


    @Override
    protected Void doInBackground(Void... params) {

        Log.d("myLogs","doInBackground");
        //region skrit
      /*  // Формируем jsonObject из файла
        try {

            File yourFile = new File(Environment.getExternalStorageDirectory(), "/MyFiles/fileSD1.txt");
            FileInputStream stream = new FileInputStream(yourFile);
            String jsonStr = null;
            try {
                FileChannel fc = stream.getChannel();
                MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());

                jsonStr = Charset.defaultCharset().decode(bb).toString();
            }
            finally {
                stream.close();
            }

            jsonObj = new JSONObject(jsonStr);

            Log.d("myLogs",jsonObj.toString());

            // Отправка json, отправку делаем в AsyncTask


        }
        catch (Exception e) {
            e.printStackTrace();
        }
*/
      //endregion
        // Отправка данных
        try {

            String MY_SITE = "http://ip.jsontest.com/";
            URL url =new URL(MY_SITE);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();

            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestMethod("POST");
            urlConnection.setConnectTimeout(5000);
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);

            //region skrit
         /*  Log.d("myLogs", "___" + jsonObj.toString());
            BufferedOutputStream bos = new BufferedOutputStream(urlConnection.getOutputStream());
            Log.d("myLogs", "qqq");
            bos.write(jsonObj.toString().getBytes());
            bos.flush();
            bos.close();
*/
         //endregion

            Log.d("myLogs", "отправили данные");
            if (urlConnection.getResponseCode()==HttpURLConnection.HTTP_OK) {
                String result = urlConnection.getResponseMessage();
                Log.d("myLogs", "server response: " + result); //проверить, что вернет сервер, сервер должен вернуть тестовую строку

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(urlConnection.getInputStream()));

                StringBuffer sb = new StringBuffer();
                String tmp="";
                while((tmp=reader.readLine())!=null)
                    sb.append(tmp).append("\n");
                reader.close();

                Log.d("myLogs",sb.toString());
                resultImput=sb.toString();
            }
            else {
                Log.d("myLogs","no");
            }

        } catch (Exception e) {
            //System.out.println("Exp=" + e);
            e.printStackTrace();

        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {

        super.onPostExecute(result);
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();
    }
}