package com.nick.nicknamecreator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Random;

public class Activity1 extends AppCompatActivity {

    private final String CLEAR_OUTPUT_TEXT = "";
    private final int[] TV_ids = {R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4, R.id.tv5, R.id.tv6, R.id.tv7, R.id.tv8, R.id.tv9, R.id.tv10, R.id.tv11, R.id.tv12, R.id.tv13, R.id.tv14, R.id.tv15, R.id.tv16};
    private EditText et_length;
    private Button b_create;
    private TextView[] tvs = new TextView[TVS];
    private Button btn_memo;
    private static final int TVS = 16;
    private Context context = this;
    private SQLHelper helper;

    int popup = 0;
    final int MAX_LEN = 8;

    private final String[] ONE_WORD = {"가","가","가","가","가","가","가","가","가","가","가","가","가","가","가","가","가","가","가","강","강","강","개","개","개",
            "고","고","고","고","고","고","고","구","구","구","구","구","구","구","구","그","그","그","그","그","그","그","그","금","금","금","금","금","금","기","기",
            "기","기","기","기","기","기","기","기","기","기","기","기","기","기","기","기","길","길","길","길","길","길","까","까","까","꼬","꼬","꼬","꼬","꽃","꽃",
            "꽃","꽃","나","나","나","나","나","나","나","나","나","나","나","나","나","나","나","나","나","나","나","남","남","남","남","남","내","내","내","내","내",
            "내","내","너","너","너","너","노","노","노","노","노","노","노","누","누","누","누","눈","눈","눈","눈","눈","늘","늘","늘","늬","늬","늬","늬","니","니",
            "니","니","니","니","니","니","니","니","니","니","니","니","니","니","니","니","니","니","다","다","다","다","다","다","다","다","다","다","다","다","다",
            "다","다","달","달","달","달","달","달","대","대","대","대","더","더","더","도","도","도","도","도","도","도","도","도","도","도","도","돌","돌","돌","돌",
            "돌","돌","돌","동","동","동","두","두","두","두","두","두","두","드","드","드","드","드","드","드","드","드","드","들","들","들","들","라","라","라","라",
            "라","라","라","라","라","라","라","라","라","라","라","라","라","라","람","람","람","람","람","람","람","람","람","람","람","람","람","람","람","람","람",
            "람","람","랑","랑","랑","랑","랑","랑","랑","랑","랑","랑","랑","랑","래","래","래","래","래","래","래","래","래","래","래","래","래","레","레","레","레",
            "레","레","로","로","로","로","로","로","로","로","루","루","루","루","루","루","루","르","르","르","르","르","르","르","름","름","름","리","리","리","리",
            "리","리","리","리","리","리","리","리","리","리","리","리","리","리","리","리","리","리","리","리","리","리","리","리","리","리","리","리","리","리","리",
            "리","리","리","리","리","리","리","리","리","리","리","리","리","리","리","리","리","리","리","리","리","리","리","리","리","리","리","리","리","리","리",
            "리","리","리","리","리","리","리","리","리","린","린","린","린","린","림","림","림","림","림","림","마","마","마","마","마","마","마","마","마","마","마",
            "마","마","마","마","마","마","망","망","망","매","매","매","매","머","머","머","메","메","메","모","모","모","모","모","모","모","모","모","모","모","몽",
            "몽","몽","무","무","무","무","무","무","무","무","무","무","무","무","무","물","물","물","물","물","물","물","물","미","미","미","미","미","미","미","미",
            "미","미","미","미","미","미","미","미","미","미","미","미","바","바","바","바","바","바","바","바","바","바","바","바","바","바","바","바","바","바","반",
            "반","반","방","방","방","방","방","버","버","버","버","버","별","별","별","별","별","별","부","부","부","부","부","비","비","비","비","비","비","비","비",
            "비","비","비","비","비","비","비","비","비","비","비","비","사","사","사","사","사","사","사","사","사","사","사","사","사","사","사","사","사","사","사",
            "산","산","산","산","살","살","살","살","새","새","새","새","새","새","새","새","새","새","새","새","샘","샘","샘","생","생","생","생","선","선","선","선",
            "세","세","세","소","소","소","소","소","소","소","소","소","소","소","소","소","소","소","소","솔","솔","솔","수","수","수","수","수","수","수","수","수",
            "수","수","수","수","수","수","수","스","스","스","시","시","시","시","시","시","시","시","시","시","시","시","실","실","실","심","심","심","심","아","아",
            "아","아","아","아","아","아","아","아","아","아","아","아","아","아","아","아","아","아","아","아","아","알","알","알","야","야","야","여","여","여","여",
            "여","여","여","오","오","오","온","온","온","온","온","온","올","올","올","우","우","우","우","우","우","우","우","우","우","우","우","울","울","울","위",
            "위","위","위","은","은","은","은","은","이","이","이","이","이","이","이","이","이","이","이","이","이","이","이","이","이","이","이","이","이","이","이",
            "이","자","자","자","자","자","자","자","자","잠","잠","잠","잠","장","장","장","조","조","조","주","주","주","주","지","지","지","지","지","지","지","지",
            "지","지","지","지","지","지","지","지","지","지","지","지","짜","짜","짜","짜","짜","짜","천","천","천","치","치","치","치","치","치","치","타","타","타",
            "타","파","파","파","파","파","파","파","푸","푸","푸","풀","풀","풀","피","피","피","하","하","하","하","하","하","하","하","하","하","하","하","한","한",
            "한","한","한","한","한","해","해","해","해","해","희","희","희"};
    private final String[] TWO_WORD = {"미르","푸르","미리","리내","온새","미로","한울","아라","마루","가람","가온","가온","누리","가시","버시",
            "나리","솔길","윤슬","비늘","해리","헤윰","나린","아리","리아","피아","푸실","단미","아토","타니","까미","람이","희나","라온","하제","힐조","다미","미로","에멜",
            "지로","꽃잠","사나","나래","르샤","나르","베리","벼리","흐노","노니","노고","고지","지리","아미","이든","이내","너울","너비","누리","누리","아사","하제","아스",
            "라이","슈룹","가라","사니","라사","초아","나린","호드","두리","바람","까부","누리","까비","매지","리내","모라","라기","삿갓","달비","리나","비나","그린","나래",
            "하나","하야","로비","꼬리","별찌","그루","바오","살비","다흰","다원","가람","가비","파니","퍼르","해랑","타래","서리","도담","올리","사랑","리사","도래","래솔",
            "한울","여우","우비","하람","가론","조이","아름","드리","수리","우리","다라","아띠","새라","다솜","소니","다소","소니","난이","늦마","바리","마소","두래","산돌",
            "우물","여우","매지","구름","아람","느루","꼬지","르로","로이","바래","라지","그미","즈믄","마닐","다라","하슬","아라","가야","아리","리수","꽃샘","바람","소소",
            "소리","바람","돌개","바람","산돌","시랑","랑이","앙짜","옴니","암니","모꼬","나비","알이","머리","하늬","바람","북새","바람","마파","파람","파랑","자귀","다님",
            "소마","고수","머리","하마","움길","희치","소마","사달","사그","랑이","자리","바라","라기","지망","드레","모람","둔치","가라","라사","사니","무리","바치","거레",
            "수련","곰비","임비","구듭","구유","도리","그루","금새","기를","길마","길미","깜부","꾀로","꽃샘","꿰미","끄나","나래","남새","남우","내남","너널","더리","가리",
            "노루","노적","가리","놀금","높새","비음","눈미","느루","는개","다따","다랑","달랑","달포","도리","두리","바람","껑이","펄이","두리","저리","거리","린결","도사",
            "도섭","파니","동그","마니","아리","동티","되모","모시","두멍","뒤란","뒤웅","드난","드레","들마","들메","들피","쟁이","마고","코지","매개","드리","메지","부리",
            "가비","모래","모르","모주","구리","몽니","몽짜","꾸리","녀리","서리","텅이","미립","민패","절미","반기","방자","방짜","배내","버금","벼리","보드","북새","비나",
            "나리","비말","물이","지시","뿌다","구니","시랑","랑이","산돌","소매","사미","삼태","새경","새물","물내","새룽","바람","선술","소댕","소두","소래","소리","바람",
            "대기","사래","수지","지니","수채","스스","시게","시나","브로","시래","시세","시앗","랑이","실터","마니","니리","아람","아름","드리","아주","버니","안날","알섬",
            "알심","머리","라지","부루","너리","여남","남은","여리","여우","우비","여줄","가리","영판","옥셈","올무","아리","마니","수리","웃비","의초","리끼","도리","수리",
            "가리","여우","주니","돌이","진솔","짐짓","짜발","량이","짜장","마리","정이","찌그","트레","바리","푸네","풀무","하냥","하늬","하릅","사리","한무","한풀","구리",
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);

        et_length = findViewById(R.id.editText_length);
        b_create = findViewById(R.id.button_create);
        btn_memo = findViewById(R.id.memo_btn);
        helper = helper.getInst(context);


        for (int i = 0; i < TVS; i++) {
            tvs[i] = (TextView) findViewById(TV_ids[i]);
            tvs[i].setText(CLEAR_OUTPUT_TEXT);
            registerForContextMenu(tvs[i]);
            tvs[i].setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    TextView tv = (TextView) v;
                    String value = tv.getText().toString();
                    helper.insertMemo(value);
                    Toast.makeText(Activity1.this, "저장했습니다!", Toast.LENGTH_LONG).show();
                    return true;
                }
            });
        }

        btn_memo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MemoPopup.class);
                startActivity(intent);
            }
        });

        b_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup = getIntent().getIntExtra("popup", -1);

                if (et_length.getText().toString().equals("")) {
                    Toast.makeText(Activity1.this, "숫자를 입력하세요", Toast.LENGTH_LONG).show();
                } else {
                    try {
                        Integer len = Integer.parseInt("" + et_length.getText());

                        if (len < 1 || len > 8)
                            Toast.makeText(Activity1.this, "1~8글자만 가능합니다", Toast.LENGTH_LONG).show();
                        else {
                            Toast.makeText(Activity1.this, "얍!", Toast.LENGTH_SHORT).show();
                            //TODO: ONE과 TWO를 조합하여 입력받은 개수에 맞게 랜덤으로 조합합니다
                            int i;
                            for (i = 0; i < TVS; i++) {
                                tvs[i].setText(createName_ver3(len));
                            }
                            /*
                            int i;
                            for(i=0; i<TVS; i++){
                                tvs[i].setText(createName(len));
                            }*/

                        }
                    } catch (Exception e) {
                        Toast.makeText(Activity1.this, "숫자를 읽을 수가 없어요ㅠㅠ", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public String createName_ver3(int len) {
        Random rand = new Random();
        String newName = "";
        for(int i=0; i<len; i++){
            double AorB = Math.random();
            if(AorB > 0.3 && i < len-1){
                //TODO: 두글자 배열에서 고름
                int a = rand.nextInt(TWO_WORD.length);
                newName += TWO_WORD[a];
                i++;
            }else {
                //TODO: 한글자 배열에서 고름
                int a = rand.nextInt(ONE_WORD.length);
                newName += ONE_WORD[a];
            }
            Log.i("단어생성중",i+newName);
        }
        Log.i("단어생성완료",newName);
        return newName;
    }
}
/*
    public String createName(int len){             //마지막 글자 확률 다르게
        Random rand = new Random();
//초성 60
        int cho_first_n_last[] = {0,0,0,0,1,2,2,2,2,3
                ,3,3,3,4,5,5,5,5,6,6
                ,6,6,7,7,7,7,8,9,9,9
                ,9,10,11,11,11,12,12,12,12,13
                ,14,14,14,14,15,15,15,15,16,16
                ,16,16,17,17,17,17,18,18,18,18};
        int cho_even[] = {0,0,1,2,2,2,2,2,2,2
                ,2,2,2,3,3,4,5,5,5,5
                ,5,5,5,5,5,5,5,5,6,6
                ,6,6,6,6,6,6,7,7,8,9
                ,9,9,10,11,11,11,11,11,11,11
                ,11,12,12,13,14,15,16,16,17,18};
        int cho_odd[] = {0,1,2,2,2,2,2,2,2,2
                ,3,4,5,5,5,5,5,5,5,6
                ,6,6,6,6,7,7,7,7,7,8
                ,9,9,9,10,11,11,11,11,11,11
                ,11,11,11,11,12,12,12,13,14,14
                ,14,15,15,15,1,17,17,18,18,18};
//중성 70
        int jun_even[] = {0,0,0,0,0,0,0,0,1,1
                ,1,2,2,2,3,4,4,4,4,4
                ,4,4,5,5,5,6,6,6,7,8
                ,8,8,8,8,9,10,11,12,13,13
                ,13,14,14,14,15,16,17,17,17,18
                ,18,18,18,18,18,18,19,20,20,20
                ,20,20,20,20,20,20,20,20,20,18};
        int jun_odd[] = {0,0,0,0,0,0,0,0,0,0
                ,1,1,1,1,2,3,4,4,4,4
                ,5,5,5,5,6,7,8,8,8,9
                ,9,9,10,11,12,13,13,13,14,14
                ,14,15,15,15,16,16,17,17,17,17
                ,17,18,18,18,18,18,18,18,18,19
                ,19,19,20,20,20,20,20,20,20,20};
//종성 60
        int jon_first[] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,4,4
                ,4,4,4,4,4,4,7,8,8,8,8,8,8,8,8,8,8,10,0,0
                ,16,16,16,16,16,16,16,17,17,27,17,0,19,19,21,21,21,21,21,26};
        int jon_even[] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
                         ,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
                         ,0,0,0,0,0,0,0,0,0,0,4,4,8,8,16,16,17,17,21,21};
        int jon_odd[] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
                ,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,4,4
                ,4,4,16,16,16,16,17,17,17,19
                ,19,19,21,21,21,21,8,8,8,8};
        int jon_last[] = {0,0,0,0,0,0,0,0,0,0
                ,0,0,0,0,0,0,0,0,0,0
                ,0,0,1,4,4,4,4,4,4,4
                ,4,16,16,16,17,17,17,19,19,19
                ,21,21,21,21,21,21,21,24,8,8
                ,8,8,8,8,8,8,21,0,0,27};

        char nameArr[] = new char[MAX_LEN];

        int j;
        for(j=0 ; j<len ; j++) {
             int cho = rand.nextInt(50);
             int jun = rand.nextInt(50);
             int jon = rand.nextInt(50);

             if(j==0){  //첫글자
                 int a = cho_first_n_last[cho]; int b = jun_odd[jun]; int c = jon_first[jon];
                 nameArr[j] = (char)makeCh(a,b,c);
             }
             else if(j==len-1 && len>3){   //3글자이상, 마지막글자
                 int a = cho_first_n_last[cho]; int b = jun_odd[jun]; int c = jon_last[jon];
                 nameArr[j] = (char)makeCh(a,b,c);
             }
             else if(j%2==0){  //짝수
                 int a = cho_even[cho]; int b = jun_even[jun]; int c = jon_even[jon];
                 nameArr[j] = (char)makeCh(a,b,c);
             }
             else{  //홀수
                 int a = cho_odd[cho]; int b = jun_odd[jun]; int c = jon_odd[jon];
                 nameArr[j] = (char)makeCh(a,b,c);
             }
        }
        String namesArr = String.valueOf(nameArr);

        return namesArr;
    }

    public int makeCh(int a, int b, int c){
        return (0xAC00 + 21*28*a +28*b + c);
    }
}

*/