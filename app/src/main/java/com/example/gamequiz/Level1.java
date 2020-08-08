package com.example.gamequiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Level1 extends AppCompatActivity {

    ClassImagesLevel1 ImagesLevel1 = new ClassImagesLevel1();
    private Random RandValue = new Random();
    private int ValueLeftImage = -1;
    private int ValueRightImage = -1;

    private void SetDataToImage(ImageView aImageView, TextView aTextView, int aNumber){
        final String[] StringNumbers = getResources().getStringArray(R.array.ValueNumbers);
        aImageView.setImageResource(ImagesLevel1.ListImages[aNumber]);
        aTextView.setText(StringNumbers[aNumber]);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.universal);

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent intent = getIntent();
        String NumberSelectLevel = intent.getStringExtra("ValuseNumberLevel");

        TextView NameLevel = findViewById(R.id.tvNumberLevel);
        NumberSelectLevel = NameLevel.getText() + " " + NumberSelectLevel;
        NameLevel.setText(NumberSelectLevel);

        Button ButtonBack = findViewById(R.id.btnBack);
        ButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Level1.this, GameLevel.class);
                startActivity(intent);
                finish();
            }
        });

        // Обработка левой картинки
        ImageView LeftImage = (ImageView)findViewById(R.id.img_level_left);
        TextView TextLeft = (TextView)findViewById(R.id.tv_text_left);
        ValueLeftImage = RandValue.nextInt(10);
        SetDataToImage(LeftImage, TextLeft, ValueLeftImage);

        LeftImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ValueLeftImage > ValueRightImage){
                    Toast MyToast = Toast.makeText(getBaseContext(), "Левая картинка больше правой", Toast.LENGTH_SHORT);
                    MyToast.show();
                }
                else{
                    Toast MyToast = Toast.makeText(getBaseContext(), "ОШИБКА!!! ", Toast.LENGTH_SHORT);
                    MyToast.show();
                }
            }
        });

        // Обработка правой картинки
        ValueRightImage = RandValue.nextInt(10);
        while (ValueRightImage == ValueLeftImage) {
            ValueRightImage = RandValue.nextInt(10);
        }

        ImageView RightImage = findViewById(R.id.img_level_right);
        TextView TextRight = findViewById(R.id.tv_text_right);
        SetDataToImage(RightImage, TextRight, ValueRightImage);

        RightImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ValueRightImage > ValueLeftImage){
                    Toast MyToast = Toast.makeText(getBaseContext(), "Правая картинка больше левой", Toast.LENGTH_SHORT);
                    MyToast.show();
                }
                else{
                    Toast MyToast = Toast.makeText(getBaseContext(), "ОШИБКА!!!!", Toast.LENGTH_SHORT);
                    MyToast.show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Level1.this, GameLevel.class);
        startActivity(intent);
        finish();
    }
}