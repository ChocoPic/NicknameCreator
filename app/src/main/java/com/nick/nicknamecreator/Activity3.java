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

    private final String[] ONE_WORD = {};
    private final String[] TWO_WORD = {};

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
                    Toast.makeText(Activity3.this, "저장했습니다!", Toast.LENGTH_LONG).show();
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