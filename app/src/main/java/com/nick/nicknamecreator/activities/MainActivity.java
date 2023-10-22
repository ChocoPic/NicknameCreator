package com.nick.nicknamecreator.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.nick.nicknamecreator.BuildConfig;
import com.nick.nicknamecreator.service.PrefManager;
import com.nick.nicknamecreator.R;
import com.nick.nicknamecreator.service.SQLHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private PrefManager prefManager = new PrefManager();
    private InterstitialAd mAd;
    public SharedPreferences pref;
    public SQLHelper helper;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //첫 화면
        setContentView(R.layout.activity_main);

        //광고 초기화
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        helper = helper.getInst(this);
        pref = getSharedPreferences("Pref", MODE_PRIVATE);
        //pref.edit().putBoolean("isFirstRun", true).apply();
        int saved_pref_ver = pref.getInt("app_version", 0);
        if(saved_pref_ver < BuildConfig.VERSION_CODE){  //저장된 버전과 현재 버전이 다르면
            checkFirstRun();    //첫 실행인지 체크
            pref.edit().putInt("app_version", BuildConfig.VERSION_CODE).apply();    //현재 버전 저장
        }

        Button btn1 = (Button) findViewById(R.id.button_1);
        Button btn2 = (Button) findViewById(R.id.button_2);
        Button btn3 = (Button) findViewById(R.id.button_3);
        Button btn4 = (Button) findViewById(R.id.button_4);
        Button btn_memoPage = (Button) findViewById(R.id.memo_btn);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityKor.class);
                showInterstitial(intent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityBasic.class);
                showInterstitial(intent);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityFor.class);
                showInterstitial(intent);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityCombine.class);
                showInterstitial(intent);
            }
        });
        btn_memoPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityMemo.class);
                showInterstitial(intent);
            }
        });
    }

    // 광고 불러오는 함수
    private void loadAd(){
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this,
                //getString(R.string.admob_ad_unit_id),
                getString(R.string.admob_test),//테스트 광고 로드해보기
                adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        Log.i("광고로드실패", loadAdError.getMessage());
                        mAd = null;
                    }
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        Log.i("광고로드성공", "onAdLoaded");
                        mAd = interstitialAd;
                    }
                });
    }
    // 광고 보여주는 함수
    private void showInterstitial(Intent intent) {
        // Show the ad if it's ready. Otherwise toast and restart the game.
        if (mAd != null) {
            mAd.show(this);
            mAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    Log.d("광고", "광고 dismissed");
                    startActivity(intent);
                    loadAd();
                }

                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    Log.d("광고", "광고실패");
                    startActivity(intent);
                }

                @Override
                public void onAdShowedFullScreenContent() {
                    mAd = null;
                    Log.d("광고", "광고성공");
                }
            });
        } else {
            startActivity(intent);
            loadAd();
        }
    }

    // 첫 실행이면 도움말 보여주는 함수
    public void checkFirstRun(){
        boolean isFirstRun = pref.getBoolean("isFirstRun", true);
        if(isFirstRun){
            backUpPref();
            ImageView image = new ImageView(this);
            image.setImageResource(R.drawable.guide_image);
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                    .setNegativeButton("다시 보지 않기", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).
                            setView(image);
            builder.create().show();
            pref.edit().putBoolean("isFirstRun", false).apply();
        }
    }
    // 첫 실행인지 아닌지 판단하는 함수
    public void backUpPref(){
        ArrayList<String> nickName = new ArrayList<>();
        prefManager.getPreferences(context);
        try{
            for(int i=0; i<20; i++){
                String text = prefManager.getString(this, String.valueOf(i));
                if(!text.equals("")) {
                    nickName.add(text);
                    helper.insertMemo(nickName.get(i));
                }
            }
            prefManager.clear(context);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}