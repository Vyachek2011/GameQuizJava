package com.example.gamequiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    long TimeBackPress;
    Toast MyToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Button ButtonStart = findViewById(R.id.btn_Start);
        ButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GameLevel.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (TimeBackPress + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
            MyToast.cancel();
            return;
        }
        else{
            MyToast = Toast.makeText(getBaseContext(), "Нажмите еще раз, чтобы выйти", Toast.LENGTH_SHORT);
            MyToast.show();
        }

        TimeBackPress = System.currentTimeMillis();
    }

    /*
    public void OnClick(){
        Intent intent = new  Intent(this, GameLevel.class);
        startActivity(intent);
    }*/
}
