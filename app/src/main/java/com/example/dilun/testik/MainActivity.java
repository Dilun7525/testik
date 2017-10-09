package com.example.dilun.testik;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    //region variable
    String strRequestBuffer = "";
    static final int SLEEPTIME = 1000;
    static final int COUNT_COMMENTS = 500;
    static final int NUMBER_IMG = 3;
    static final String ERROR_NO_VALUE = "Нет значения для отправки";
    static final String ERROR_FALSE_VALUE = "Значение не входит в допустимые пределы (0-" + COUNT_COMMENTS + ")";
    static final String LOGS = "LOG_______";
    //endregion

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
        strRequestBuffer = "http://ip.jsontest.com/";
        View viewIp = findViewById(R.id.ipText);
        workJson(strRequestBuffer, viewIp, Var.IP);

        //Date & Time
        strRequestBuffer = "http://date.jsontest.com/";
        View viewDate = findViewById(R.id.dateText);
        workJson(strRequestBuffer, viewDate, Var.DATA);

        //вставка изображения с интернет ресурса
        imgUrl(NUMBER_IMG,"https://jsonplaceholder.typicode.com/photos/");
    }

    //region Receiving data

    //Отправка, получение и вывод JSON
    public void workJson(String strRequest, View view, Var var) {
        InputJson inputJson = new InputJson(strRequest);
        inputJson.execute();

        //Задержка основного потока на время для отработки
        //действий по получению JSON с сервера
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
                submitOrder(view, "Парсинг начат(ЗАГЛУШКА)");
                break;
        }
    }

    //Отправка формы JSON из формы key-value
    public void btnEchoJson(View view) {
        EditText viewText1 = (EditText) findViewById(R.id.keyEchoJsonText);
        EditText viewText2 = (EditText) findViewById(R.id.valueEchoJsonText);
        View viewTextResult = findViewById(R.id.echoJsonTextResult);

        StringBuilder strRequest = new StringBuilder();
        String strViewText1 = String.valueOf(viewText1.getText());
        String strViewText2 = String.valueOf(viewText2.getText());

        strRequest.append("http://echo.jsontest.com/");
        strRequest.append(strViewText1);
        strRequest.append("/");
        strRequest.append(strViewText2);

        workJson(strRequest.toString(), viewTextResult, Var.ECHO);
    }

    //Получение JSON комментария с введенным id
    //(пока не риализовано соединение с сервером через HTTPS)
    public void btnComments(View view) {
        EditText commentsEditText = (EditText) findViewById(R.id.commentsEditText);
        View commentsResult = findViewById(R.id.commentsResult);

        StringBuilder strRequest = new StringBuilder();
        String strBuffer = commentsEditText.getText().toString();
        
        //Если есть значение`
        if (!strBuffer.isEmpty()) {

            int intBuffer = Integer.parseInt(strBuffer);

            //Если значение в рамках 0 <> COUNT_COMMENTS(500)
            if (intBuffer > 0 && intBuffer <= COUNT_COMMENTS) {
                strRequest.append("https://jsonplaceholder.typicode.com/posts/");
                strRequest.append(intBuffer);
                
                //Log.d(LOGS, "btnComments: перед отправкой");
                HttpsJson httpsJson = new HttpsJson(strRequest.toString());
                httpsJson.execute();
                //Задержка основного потока на время
                try {
                    Thread.sleep(SLEEPTIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //Получаем обьект типа JSON
                JSONObject jsonObject = httpsJson.getResultJson();

                //Парсим обьект JSON

                submitOrder(view, "Парсинг начат(ЗАГЛУШКА)");
                
                //workJson(strRequest.toString(), commentsResult, Var.COMMENTS);
            } else {
                submitOrder(commentsResult, ERROR_FALSE_VALUE);
            }
        } else {
            submitOrder(commentsResult, ERROR_NO_VALUE);
        }

    }

    //Получение JSON image с  id=NUMBER_IMG=3
    //(пока не риализовано соединение с сервером через HTTPS)
    //изображение берется библиотекой Picasso
    public void imgUrl(int numberInt, String urlHttpsJson) {
        String urlImage = "";


        String urlBuffer = "";
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        StringBuilder strRequest = new StringBuilder();
        strRequest.append(urlHttpsJson);
        strRequest.append(numberInt);
        urlBuffer = strRequest.toString();

        //Раскоментировать в итоговой программе
/*
        ResponseOkHttp responseOkHttp = new ResponseOkHttp(urlBuffer);
        responseOkHttp.execute();
        //Задержка основного потока на время
        try {
            Thread.sleep(SLEEPTIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Получаем обьект типа JSON
        JSONObject jsonObject = responseOkHttp.getResultJson();

        //Парсинг JSON объкта

        try {
            urlImage = jsonObject.getString("url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
*/

        urlImage ="http://placehold.it/600/24f355"; //Удалить в итоговой версии программы
        Log.d(LOGS, "imgeUrl: " + urlImage);       //Удалить в итоговой версии программы

        Picasso.with(this)
                .load(urlImage)
                .into(imageView);

    }

    //endregion

    //region Parses JSON
    // Парсеры выдают разобранный JSON объкт
    // в виде строки

    //Парсер IP
    public String parseJsonIp(JSONObject jsonObject) {
        StringBuilder buffer = new StringBuilder();
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
                buffer.append(key).append("\n");
                buffer.append("Значение: ");
                buffer.append(jsonObject.getString(key)).append("\n");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return buffer.toString();
    }

    //endregion

    //Вывод информации в view
    public void submitOrder(View view, String strInputJson) {
        TextView Textview = (TextView) view;
        Textview.setText(strInputJson);
    }

    // Переход на другую активити
    public void goMyActivity(View view) {

        // Создаем объект Intent для вызова новой Activity
        Intent intent = new Intent(this, MyActivity.class);

        // запуск activity
        startActivity(intent);
    }


}

