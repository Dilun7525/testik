package com.example.dilun.testik;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
    }
    // Переход на другую активити
    public void goOneActivity (View view) {

        // Создаем объект Intent для вызова новой Activity
        Intent intent = new Intent(this, MainActivity.class);

        // запуск activity
        startActivity(intent);
    }

}





