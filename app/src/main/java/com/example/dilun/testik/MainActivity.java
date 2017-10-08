package com.example.dilun.testik;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;


public class MainActivity extends AppCompatActivity {

    String strRequestBoofer = "";
    int SLEEPTIME = 1000;
    String ERROR_NO_VALUE = "Нет значения для отправки";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        * Получение обьекта JSON,
        * места, вывода информации(view),
        * представление данных в удобочитаемом виде
         */
        //IP
        strRequestBoofer = "http://ip.jsontest.com/";
        View viewIp = findViewById(R.id.ipText);
        workJson(strRequestBoofer, viewIp, Var.IP);

        //Date & Time
        strRequestBoofer = "http://date.jsontest.com/";
        View viewDate = findViewById(R.id.dateText);
        workJson(strRequestBoofer, viewDate, Var.DATA);


    }

    //region Receiving data

    //Отправка, получение и вывод Echo JSON
    public void workJson(String strRequest, View view, Var var) {
        InputJson inputJson = new InputJson(strRequest);
        inputJson.execute();
        //Задержка основного потока на время
        try {
            Thread.sleep(SLEEPTIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Получаем обьект типа JSON
        JSONObject jsonObject = inputJson.getResultJson();

        //Парсим обьект JSON
        switch (var) {
            case IP:
                submitOrder(view, parseJsonIp(jsonObject));
                break;
            case DATA:
                submitOrder(view, parseJsonDateTime(jsonObject));
                break;
            case ECHO:
                submitOrder(view, parseJsonEcho(jsonObject));
                break;
            case COMMENTS:
                submitOrder(view, parseJsonDateTime(jsonObject));
                break;
        }
    }

    public void btnEchoJson(View view) {
        EditText viewText1 = (EditText) findViewById(R.id.keyEchoJsonText);
        EditText viewText2 = (EditText) findViewById(R.id.valueEchoJsonText);
        View viewTextResult = findViewById(R.id.echoJsonTextResult);

        StringBuffer strRequest = new StringBuffer();
        String strViewText1 = String.valueOf(viewText1.getText());
        String strViewText2 = String.valueOf(viewText2.getText());

        strRequest.append("http://echo.jsontest.com/");
        strRequest.append(strViewText1);
        strRequest.append("/");
        strRequest.append(strViewText2);

        workJson(strRequest.toString(), viewTextResult, Var.ECHO);
    }


    public void btnComments(View view) {
        EditText commentsEditText = (EditText) findViewById(R.id.commentsEditText);
        View commentsResult = findViewById(R.id.commentsResult);

        StringBuffer strRequest = new StringBuffer();
        String strBuffer = commentsEditText.getText().toString();
        submitOrder(commentsResult, "Вошли в ////");
        //Если есть значение`
        if (!strBuffer.isEmpty()){
            submitOrder(commentsResult, "Вошли в if");
            int strIntBoofer = Integer.parseInt(strBuffer) ;
            strRequest.append("https://jsonplaceholder.typicode.com/posts/");
            strRequest.append(strIntBoofer);
            workJson(strRequest.toString(), commentsResult, Var.COMMENTS);
        }else{
            submitOrder(commentsResult, ERROR_NO_VALUE);
        }

    }
    //endregion


    //region Parse JSON
    //Парсер IP
    public String parseJsonIp(JSONObject jsonObject) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("ip: ");
        try {
            buffer.append(jsonObject.getString("ip"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    //Парсер Date & Time
    public String parseJsonDateTime(JSONObject jsonObject) {
        StringBuffer buffer = new StringBuffer();

        try {
            buffer.append("Дата и время по гринвичу \n");
            buffer.append("Дата: ");
            buffer.append(jsonObject.getString("date") + "\n");
            buffer.append("Время: ");
            buffer.append(jsonObject.getString("time"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    //Парсер Echo JSON
    public String parseJsonEcho(JSONObject jsonObject) {
        StringBuffer buffer = new StringBuffer();

        try {
            for (Iterator<String> keys = jsonObject.keys(); keys.hasNext(); ) {
                String key = keys.next();

                buffer.append("Ключ: ");
                buffer.append(key + "\n");
                buffer.append("Значение: ");
                buffer.append(jsonObject.getString(key) + "\n");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return buffer.toString();
    }
    //endregion


    /*Вывод информации в view*/

    public void submitOrder(View view, String strInputJson) {
        TextView Textview = (TextView) view;
        Textview.setText(strInputJson);
    }
}



    /*//Получение и вывод IP
    public void outputIp(String strRequest) {

        InputJson inputJson = new InputJson(strRequest);
        inputJson.execute();

        try {
            Thread.sleep(SLEEPTIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        *//*
        * Получение обьекта JSON,
        * места, вывода информации(view),
        * представление данных в удобочитаемом виде
         *//*
        JSONObject jsonObject = inputJson.getResultJson();
        View view = findViewById(R.id.ipText);
        submitOrder(view, parseJsonIp(jsonObject));
    }

    //Получение и вывод Date & Time
    public void outputDateTime(String strRequest) {

        InputJson inputJson = new InputJson(strRequest);
        inputJson.execute();

        try {
            Thread.sleep(SLEEPTIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        *//*
        * Получение обьекта JSON,
        * места, вывода информации(view),
        * представление данных в удобочитаемом виде
         *//*
        JSONObject jsonObject = inputJson.getResultJson();
        View view = findViewById(R.id.dateText);
        submitOrder(view, parseJsonDateTime(jsonObject));
    }*/
