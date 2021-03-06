package com.example.gamequiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class GameLevel extends AppCompatActivity {

    private String NumberSelectLevel = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_level);

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Button ButtonBack = findViewById(R.id.btnBack);
        ButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameLevel.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        final TextView NumberLevel1 = findViewById(R.id.tvNumber1);
        NumberLevel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NumberSelectLevel = NumberLevel1.getText().toString();
                Intent intent = new Intent(GameLevel.this, Level1.class);
                // Передача параметра - номер уровня
                intent.putExtra("ValuseNumberLevel", NumberSelectLevel);

                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(GameLevel.this, MainActivity.class);
        startActivity(intent);
        finish();
        //super.onBackPressed();
    }
}