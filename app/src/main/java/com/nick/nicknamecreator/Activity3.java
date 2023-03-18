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

    private final String[] ONE_WORD_LAST = {"나","나","나","나","나","나","노","니","다","든","든","든","디","디","디","라","라","라","라","라","라",
            "라","라","라","라","라","라","런","로","로","로","롯","류","르","리","리","리","리","리","리","리","리","리","리","리","린","린","린",
            "마","마","미","밋","바","바","바","반","버","버","브","븐","비","셔","스","스","스","스","스","슨","슨","슨","슨","시","아","아","아",
            "아","아","아","아","아","아","아","아","아","아","아","안","암","야","야","어","어","어","어","에","오","오","이","이","이","저","즈","테","튜","틴","퍼","퍼","핀"};
    private final String[] ONE_WORD_MID = {"그","그","그","나","나","노","노","노","노","녹","단","델","도","듀","드","드","드","디","라","라","라","러","레",
            "레","레","레","레","레","레","레","로","로","로","로","루","루","루","루","루","리","리","리","리","리","리","리","리","리","리","리","리","리","리",
            "리","리","리","리","리","린","릴","마","마","매","메","미","미","밀","밀","바","벨","벨","벨","벨","보","브","브","브","블","비","비","비","비","빅",
            "사","사","새","샐","샬","소","소","스","스","스","스","스","시","아","아","아","아","아","아","아","아","아","아","아","아","안","안","알","알","알",
            "애","앤","어","에","에","에","에","엘","엘","엘","엘","엘","엘","엠","엠","오","오","오","오","오","올","올","올","올","이","이","이","이","이","이",
            "이","이","이","이","이","이","이","일","일","일","일","일","일","일","임","자","재","잭","저","제","제","제","젤","젤","조","조","줄","카","카","카",
            "캘","케","케","클","클","클","탈","테","텔","토","틀","티","파","패","피","피","필","허","헨","리","미","나","디","드","드","안"};
    //초성 60
    private final int cho_first_n_last[] = {0,0,0,0,1,2,2,2,2,3,3,3,3,4,5,5,5,5,6,6,6,6,7,7,7,7,8,9,9,9
            ,9,10,11,11,11,12,12,12,12,13,14,14,14,14,15,15,15,15,16,16,16,16,17,17,17,17,18,18,18,18};
    private final int cho_even[] = {0,0,1,2,2,2,2,2,2,2,2,2,2,3,3,4,5,5,5,5,5,5,5,5,5,5,5,5,6,6
            ,6,6,6,6,6,6,7,7,8,9,9,9,10,11,11,11,11,11,11,11,11,12,12,13,14,15,16,16,17,18};
    private final int cho_odd[] = {0,1,2,2,2,2,2,2,2,2,3,4,5,5,5,5,5,5,5,6,6,6,6,6,7,7,7,7,7,8
            ,9,9,9,10,11,11,11,11,11,11,11,11,11,11,12,12,12,13,14,14,14,15,15,15,1,17,17,18,18,18};
    //중성 70
    private final int jun_even[] = {0,0,0,0,0,0,0,0,1,1,1,2,2,2,3,4,4,4,4,4,4,4,5,5,5,6,6,6,7,8
            ,8,8,8,8,9,10,11,12,13,13,13,14,14,14,15,16,17,17,17,18,18,18,18,18,18,18,19,20,20,20
            ,20,20,20,20,20,20,20,20,20,18};
    private final int jun_odd[] = {0,0,0,0,0,0,0,0,0,0,1,1,1,1,2,3,4,4,4,4,5,5,5,5,6,7,8,8,8,9
            ,9,9,10,11,12,13,13,13,14,14,14,15,15,15,16,16,17,17,17,17,17,18,18,18,18,18,18,18,18,19
            ,19,19,20,20,20,20,20,20,20,20};
    //종성 60
    private final int jon_first[] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,4,4,4,4,4,4,4,4,7,8,8,8,
            8,8,8,8,8,8,8,10,0,0,16,16,16,16,16,16,16,17,17,27,17,0,19,19,21,21,21,21,21,26};
    private final int jon_even[] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4,4,8,8,16,16,17,17,21,21};
    private final int jon_odd[] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,1,4,4,4,4,16,16,16,16,17,17,17,19,19,19,21,21,21,21,8,8,8,8};
    private final int jon_last[] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,4,4,4,4,4,4,4
            ,4,16,16,16,17,17,17,19,19,19,21,21,21,21,21,21,21,24,8,8,8,8,8,8,8,8,21,0,0,27};

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
                    //Toast.makeText(Activity3.this, "저장했습니다!", Toast.LENGTH_LONG).show();
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
                            //ONE과 TWO를 조합하여 입력받은 개수에 맞게 랜덤으로 조합합니다
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

        double AorB = Math.random();
        if(AorB > 0.2){ //약 80퍼 확률로 마지막글자를 배열에서 골라서 사용
            int a = rand.nextInt(ONE_WORD_LAST.length);
            newName += createNameMid(len-1);
            newName += ONE_WORD_LAST[a];

        }else{ //약 20퍼 확률로 마지막글자를 조합해서 사용
            newName += createNameMid(len);
        }
        Log.i("단어생성완료",newName);
        return newName;
    }

    public String createNameMid(int len){
        Random rand = new Random();

        String nameArr = "";
        int j;

        for(j=0 ; j<len ; j++) {
            double aORb = Math.random();
            if (aORb > 0.2) { //약 80퍼 확률로 가져다 씀
                int n = rand.nextInt(ONE_WORD_MID.length);
                nameArr += ONE_WORD_MID[n];
            } else {  //약 20퍼 확룔로 랜덤
                int cho = rand.nextInt(50);
                int jun = rand.nextInt(50);
                int jon = rand.nextInt(50);

                if (j == 0) {  //첫글자
                    int a = cho_first_n_last[cho];
                    int b = jun_odd[jun];
                    int c = jon_first[jon];
                    nameArr += ((char) makeCh(a, b, c));
                } else if (j == len - 1 && len > 3) {   //3글자이상, 마지막글자
                    int a = cho_first_n_last[cho];
                    int b = jun_odd[jun];
                    int c = jon_last[jon];
                    nameArr += (char) makeCh(a, b, c);
                } else if (j % 2 == 0) {  //짝수
                    int a = cho_even[cho];
                    int b = jun_even[jun];
                    int c = jon_even[jon];
                    nameArr += (char) makeCh(a, b, c);
                } else {  //홀수
                    int a = cho_odd[cho];
                    int b = jun_odd[jun];
                    int c = jon_odd[jon];
                    nameArr += (char) makeCh(a, b, c);
                }
            }
        }
        return nameArr;
    }

    public int makeCh(int a, int b, int c){
        return (0xAC00 + 21*28*a +28*b + c);
    }
}
