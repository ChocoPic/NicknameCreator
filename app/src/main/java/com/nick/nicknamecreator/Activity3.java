package com.nick.nicknamecreator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;
/*외국닉 랜덤*/
public class Activity3 extends AppCompatActivity {

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

    private final String[] LAST_WORD = {"나","나","나","나","나","나","노","니","다","든","든","든","디","디","디",
            "라","라","라","라","라","라","라","라","라","라","라","라","런","로","로","로","롯","류","르",
            "리","리","리","리","리","리","리","리","리","리","리","린","린","린","마","마","미","밋",
            "바","바","바","반","버","버","브","븐","비","셔","스","스","스","스","스","슨","슨","슨","슨",
            "시","아","아","아","아","아","아","아","아","아","아","아","아","아","아","안","암","야","야",
            "어","어","어","어","에","오","오","이","이","이","저","즈","테","튜","틴","퍼","퍼","핀"};
    private final String[] MID_WORD = {"그","그","그","나","나","노","노","노","노","녹","단","델",
            "도","듀","드","드","드","디","라","라","라","러","레","레","레","레","레","레","레","레",
            "로","로","로","로","루","루","루","루","루","리","리","리","리","리","리","리","리","리",
            "리","리","리","리","리","리","리","리","리","리","린","릴","마","마","매","메","미","미",
            "밀","밀","바","벨","벨","벨","벨","보","브","브","브","블","비","비","비","비","빅","사",
            "사","새","샐","샬","소","소","스","스","스","스","스","시","아","아","아","아","아","아",
            "아","아","아","아","아","아","안","안","알","알","알","애","앤","어","에","에","에","에",
            "엘","엘","엘","엘","엘","엘","엠","엠","오","오","오","오","오","올","올","올","올","이",
            "이","이","이","이","이","이","이","이","이","이","이","이","일","일","일","일","일","일",
            "일","임","자","재","잭","저","제","제","제","젤","젤","조","조","줄","카","카","카","캘",
            "케","케","클","클","클","탈","테","텔","토","틀","티","파","패","피","피","필","허","헨"};

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
                    Toast.makeText(Activity3.this, "숫자를 입력하세요", Toast.LENGTH_LONG).show();
                } else {
                    try {
                        Integer len = Integer.parseInt("" + et_length.getText());

                        if (len < 1 || len > 8)
                            Toast.makeText(Activity3.this, "1~8글자만 가능합니다", Toast.LENGTH_LONG).show();
                        else {
                            Toast.makeText(Activity3.this, "얍!", Toast.LENGTH_SHORT).show();
                            //입력받은 개수에 맞게 랜덤으로 조합합니다
                            int i;
                            for (i = 0; i < TVS; i++) {
                                tvs[i].setText(createName_ver3(len));
                            }
                        }
                    } catch (Exception e) {
                        Toast.makeText(Activity3.this, "숫자를 읽을 수가 없어요ㅠㅠ", Toast.LENGTH_LONG).show();
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
            if(i==len-1){
                if(AorB > 0.1){
                    //리스트에서 마지막글자 고르기
                    int a = rand.nextInt((LAST_WORD.length));
                    newName += LAST_WORD[a];
                }else{
                    //랜덤으로 마지막글자 만들기
                    newName += createC();
                }
            }
            else{
                if(AorB > 0.2){
                    //리스트에서 중간글자 고르기
                    int a = rand.nextInt((MID_WORD.length));
                    newName += MID_WORD[a];
                }else {
                    //랜덤으로 중간글자 만들기
                    newName += createC();
                }
            }
            Log.i("단어생성중",i+newName);
        }
        Log.i("단어생성완료",newName);
        return newName;
    }



    public String createC(){
        char chr;
        Random rand = new Random();
        //초성 60
        int cho_list[] = {0,0,0,0,1,2,2,2,2,3,3,3,3,4,5,5,5,5,6,6,6,6,7,7,7,7,8,9,9,9
                ,9,10,11,11,11,12,12,12,12,13,14,14,14,14,15,15,15,15,16,16,16,16,17,17,17,17,18,18,18,18};
        //중성 70
        int jun_list[] = {0,0,0,0,0,0,0,0,1,1,1,2,2,2,3,4,4,4,4,4,4,4,5,5,5,6,6,6,7,8
                ,8,8,8,8,9,10,11,12,13,13,13,14,14,14,15,16,17,17,17,18,18,18,18,18,18,18,19,20,20,20
                ,20,20,20,20,20,20,20,20,20,18};
        //종성 60
        int jon_list[] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,4,4
                ,4,4,4,4,4,4,7,8,8,8,8,8,8,8,8,8,8,10,0,0
                ,16,16,16,16,16,16,16,17,17,27,17,0,19,19,21,21,21,21,21,26, 27};

         int cho = rand.nextInt(50);
         int jun = rand.nextInt(50);
         int jon = rand.nextInt(50);

         int a = cho_list[cho]; int b = jun_list[jun]; int c = jon_list[jon];
         chr = (char)makeCh(a,b,c);

        return String.valueOf(chr);
    }

    public int makeCh(int a, int b, int c){
        return (0xAC00 + 21*28*a +28*b + c);
    }
}