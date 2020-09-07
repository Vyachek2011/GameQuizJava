package com.example.gamequiz;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Level1 extends AppCompatActivity {

    //Dialog MainDialog;

    ClassImagesLevel1 ImagesLevel1 = new ClassImagesLevel1();
    private Random RandValue = new Random();
    private int ValueLeftImage = -1;
    private int ValueRightImage = -1;
    private int CountDoTaskInLevel = -1;
    private long mLastClickTime;
    private static final long MIN_CLICK_INTERVAL=600;
    long TimeBackPress;

    private void SetDataToImage(ImageView aImageView, TextView aTextView, int aNumber){
        final String[] StringNumbers = getResources().getStringArray(R.array.ValueNumbers);
        aImageView.setImageResource(ImagesLevel1.ListImages[aNumber]);
        aTextView.setText(StringNumbers[aNumber]);
    }

    private void SetNewImageLeftAndRight(ImageView aLeftImage, ImageView aRightImage){
        TextView TextLeft = (TextView)findViewById(R.id.tv_text_left);
        ValueLeftImage = RandValue.nextInt(10);
        SetDataToImage(aLeftImage, TextLeft, ValueLeftImage);

        ValueRightImage = RandValue.nextInt(10);
        while (ValueRightImage == ValueLeftImage) {
            ValueRightImage = RandValue.nextInt(10);
        }

        TextView TextRight = findViewById(R.id.tv_text_right);
        SetDataToImage(aRightImage, TextRight, ValueRightImage);

        CountDoTaskInLevel++;
    }

    private void MySleep(long aTime){
        long endTime = System.currentTimeMillis() + aTime * 500;
        while (System.currentTimeMillis() < endTime) {
            synchronized (this) {
                try {
                    wait(endTime - System.currentTimeMillis());
                } catch (Exception e) {
                }
            }
        }

        /*
        try {
            TimeUnit.SECONDS.sleep(1);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
         */
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.universal);

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Вызов и обработка диалогового окна
        final Dialog MainDialog = new Dialog(this);
        MainDialog.setContentView(R.layout.start_task_level);
        //MainDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        MainDialog.setCancelable(true);
        MainDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        MainDialog.show();

        TextView btnCloseMainDialog = MainDialog.findViewById(R.id.tvCloseStartTskLevel);
        btnCloseMainDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent IntentBackLevelGames = new Intent(Level1.this, GameLevel.class);
                    startActivity(IntentBackLevelGames);
                    finish();
                } catch (Exception e) {
                }
                MainDialog.dismiss();
            }
        });

        Button btnContinueDialog = MainDialog.findViewById(R.id.btnContinueDialog);
        btnContinueDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainDialog.dismiss();
            }
        });
        // Конец обработки диалогового окна

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
        final ImageView LeftImage = (ImageView) findViewById(R.id.img_level_left);
        LeftImage.setClipToOutline(true);
        final ImageView RightImage = findViewById(R.id.img_level_right);
        RightImage.setClipToOutline(true);

        //TextView TextLeft = (TextView)findViewById(R.id.tv_text_left);
        //ValueLeftImage = RandValue.nextInt(10);
        //SetDataToImage(LeftImage, TextLeft, ValueLeftImage);

        SetNewImageLeftAndRight(LeftImage, RightImage);

        LeftImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (CountDoTaskInLevel < 20) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        RightImage.setEnabled(false);
                        if (ValueLeftImage > ValueRightImage) { // Правильный ответ
                            LeftImage.setImageResource(R.drawable.answer_true);
                        } else {
                            LeftImage.setImageResource(R.drawable.answer_false);
                        }
                    } else {
                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            RightImage.setEnabled(false);
                            LeftImage.setEnabled(false);

                            TextView tvPointAnswer = findViewById(ImagesLevel1.ProgressAnswer[CountDoTaskInLevel]);
                            if (ValueLeftImage > ValueRightImage){
                                tvPointAnswer.setBackgroundResource(R.drawable.style_points_green);
                            }
                            else{
                                tvPointAnswer.setBackgroundResource(R.drawable.style_points_red);
                            }

                            //MySleep(1);
                            //SetNewImageLeftAndRight(LeftImage, RightImage);

                            view.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    LeftImage.setEnabled(true);
                                    RightImage.setEnabled(true);
                                    SetNewImageLeftAndRight(LeftImage, RightImage);
                                }
                            },150); //150 is in milliseconds

                            MySleep(1);

                            //RightImage.setEnabled(true);
                            //LeftImage.setEnabled(true);
                        }
                    }
                }
                else{

                    Dialog DialogAllRight = new Dialog(Level1.this);
                    DialogAllRight.setContentView(R.layout.start_task_level);
                    DialogAllRight.show();


                    //Dialog DialogErrors = new Dialog(Level1.this);
                }

                return true;
            }
        });

        RightImage.setOnTouchListener(new View.OnTouchListener() {
            /*long currentClickTime=SystemClock.uptimeMillis();
            long elapsedTime=currentClickTime-mLastClickTime;

            mLastClickTime=currentClickTime;

            if(elapsedTime>MIN_CLICK_INTERVAL)
                return;*/

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {


                    if (CountDoTaskInLevel <= 20) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                            LeftImage.setEnabled(false);
                            if (ValueLeftImage < ValueRightImage) {
                                RightImage.setImageResource(R.drawable.answer_true);
                            } else {
                                RightImage.setImageResource(R.drawable.answer_false);
                            }
                        } else {
                            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                                RightImage.setEnabled(false);
                                LeftImage.setEnabled(false);

                                TextView tvPointAnswer = findViewById(ImagesLevel1.ProgressAnswer[CountDoTaskInLevel]);
                                if (ValueLeftImage < ValueRightImage) {
                                    tvPointAnswer.setBackgroundResource(R.drawable.style_points_green);
                                } else {
                                    tvPointAnswer.setBackgroundResource(R.drawable.style_points_red);
                                }

                                //SetNewImageLeftAndRight(LeftImage, RightImage);
                                //MySleep(1);

                                view.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        RightImage.setEnabled(true);
                                        LeftImage.setEnabled(true);
                                        SetNewImageLeftAndRight(LeftImage, RightImage);
                                    }
                                },150); //150 is in milliseconds

                                MySleep(1);

                                //RightImage.setEnabled(true);
                                //LeftImage.setEnabled(true);
                            }
                        }
                    } else {

                        Dialog DialogAllRight = new Dialog(Level1.this);
                        DialogAllRight.setContentView(R.layout.start_task_level);
                        DialogAllRight.show();


                        //Dialog DialogErrors = new Dialog(Level1.this);
                    }
                //}

                return true;
            }
        });

        // Обработка правой картинки
        /*ValueRightImage = RandValue.nextInt(10);
        while (ValueRightImage == ValueLeftImage) {
            ValueRightImage = RandValue.nextInt(10);
        }

        TextView TextRight = findViewById(R.id.tv_text_right);
        SetDataToImage(RightImage, TextRight, ValueRightImage);*/




        /*RightImage.setOnClickListener(new View.OnClickListener() {
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
        });*/
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Level1.this, GameLevel.class);
        startActivity(intent);
        finish();
    }
}