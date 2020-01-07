package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class personalCenterPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_center);

        /**
         * goto home page
         */
        Button btn_home = (Button)findViewById(R.id.home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(personalCenterPage.this,homePage.class);
                startActivity(intent);
            }
        });

        /**
         * goto game interface
         */
        Button btn_game = (Button)findViewById(R.id.game);
        btn_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(personalCenterPage.this,gamePage.class);
                startActivity(intent);
            }
        });

        /**
         * goto setting interface
         */
        Button btn_setting = (Button)findViewById(R.id.setting);
        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(personalCenterPage.this,settingPage.class);
                startActivity(intent);
            }
        });

        /**
         * goto favourite interface
         */
        Button btn_favourite = (Button)findViewById(R.id.favourite);
        btn_favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(personalCenterPage.this,favouritePage.class);
                startActivity(intent);
            }
        });

        /**
         * goto download interface
         */
        Button btn_download = (Button)findViewById(R.id.download);
        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(personalCenterPage.this,downloadPage.class);
                startActivity(intent);
            }
        });


    }




}
