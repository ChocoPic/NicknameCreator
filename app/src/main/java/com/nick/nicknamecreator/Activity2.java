package com.nick.nicknamecreator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.security.PrivateKey;
import java.util.Random;

public class Activity2 extends AppCompatActivity
{
    final String CLEAR_OUTPUT_TEXT = "";
    private final int [] TV_ids = {R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4, R.id.tv5, R.id.tv6, R.id.tv7, R.id.tv8, R.id.tv9, R.id.tv10, R.id.tv11, R.id.tv12, R.id.tv13, R.id.tv14, R.id.tv15, R.id.tv16};
    private static final int TVS = 16;
    private TextView [] tvs = new TextView[TVS];
    EditText n1, n2, n3, n4, n5;
    EditText et_length;
    Button b_create;
    Button btn_memo;
    TextView info_text;
    Button info_btn;
    private Context context = this;
    private SQLHelper helper;

    static final int LEN = 5;
    char userInput[] = new char[LEN];
    String userInputString;
    static int clicked = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        n1 = findViewById(R.id.editText);
        n2 = findViewById(R.id.editText2);
        n3 = findViewById(R.id.editText3);
        n4 = findViewById(R.id.editText4);
        n5 = findViewById(R.id.editText5);

        et_length = findViewById(R.id.editText_length);
        b_create = findViewById(R.id.button_create);

        btn_memo = findViewById(R.id.memo_btn);
        info_text = (TextView) findViewById(R.id.info_textView);
        info_btn = (Button) findViewById(R.id.info_button);
        helper = helper.getInst(context);

        info_text.setVisibility(View.GONE);
        info_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clicked==0){
                    info_text.setVisibility(View.VISIBLE);
                    clicked = 1;
                }else{
                    info_text.setVisibility(View.GONE);
                    clicked = 0;
                }
            }
        });

        for(int i=0; i<TVS; i++){
            tvs[i] = (TextView)findViewById(TV_ids[i]);
            tvs[i].setText(CLEAR_OUTPUT_TEXT);
            registerForContextMenu(tvs[i]);
            tvs[i].setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    TextView tv = (TextView) v;
                    String value = tv.getText().toString();
                    helper.insertMemo(value);
                    //Toast.makeText(Activity2.this, "저장했습니다!", Toast.LENGTH_LONG).show();
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
            public void onClick(View view) {
                userInput[0] = saveInput(n1.getText().toString());
                userInput[1] = saveInput(n2.getText().toString());
                userInput[2] = saveInput(n3.getText().toString());
                userInput[3] = saveInput(n4.getText().toString());
                userInput[4] = saveInput(n5.getText().toString());
                userInputString = String.valueOf(userInput);

                try{
                    if(et_length.getText().toString().equals("")){
                        Toast.makeText(Activity2.this, "숫자를 입력하세요", Toast.LENGTH_LONG).show();
                    }
                    else {
                        try {
                            Integer len = Integer.parseInt("" + et_length.getText());
                            if ( len < 1 || len > 5)
                                Toast.makeText(Activity2.this, "1~5글자까지 가능합니다", Toast.LENGTH_LONG).show();
                            else {
                                Toast.makeText(Activity2.this, "얍!", Toast.LENGTH_SHORT).show();
                                int i;
                                for(i=0; i<TVS; i++){
                                    tvs[i].setText(createName(len, userInputString));
                                }
                            }
                        } catch (Exception e) {
                            Toast.makeText(Activity2.this, "숫자를 읽을 수가 없어요ㅠㅠ", Toast.LENGTH_LONG).show();
                        }
                    }
                }catch (Exception e){
                    Toast.makeText(Activity2.this, "제대로 입력해주세요!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
    }
    public String createName(int len, String user){             //마지막 글자 확률 다르게
        Random rand = new Random();
    //ㄱ초성(50)
        //int cho_range_last[] = {0, 1, 2, 2, 2, 2, 2, 2, 3, 3, 4, 3, 3, 3, 3, 4, 5, 6, 7, 8, 9, 9, 9, 9, 9, 9, 9, 9, 10, 11, 11, 11, 11, 12, 12, 13, 14, 14, 14, 15, 15, 15, 16, 16, 16, 17, 18, 18, 18, 5};
        int cho_range_first[] = {0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10, 11, 11, 12, 12, 13, 13, 14, 14, 15, 15, 16, 16, 17, 17, 18, 18, 2, 3, 8, 11, 18, 12, 16, 15, 5, 9, 18, 11};
       // int cho_range[] = {0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 5, 5, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 7, 9, 9, 11, 11, 11, 11, 11, 11, 11, 12, 14, 14, 15, 16, 17, 18, 2, 2, 2, 2, 2, 5, 5, 5, 5, 5};
    //ㅏ중성(50)
        int jun_range_e[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 18, 18, 18, 18, 18, 18, 18, 18, 20, 20, 20, 20, 20, 20, 20, 20, 1, 4, 5, 6, 7, 9, 10, 11, 14, 5, 19, 0, 19, 17, 17};
        int jun_range_o[] = {20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 8, 8, 8, 18, 18, 18, 18, 18, 18, 21, 21, 8, 8, 1, 1, 2, 4, 5, 5, 6, 14, 20, 20, 19, 19, 20, 8, 4};
    //ㅇ종성(50)
        int jon_range_last[] = {4, 4, 4, 4, 4, 4, 4, 4, 16, 16, 16, 16, 16, 16, 16, 16, 19, 19, 19, 19, 19, 19, 19, 21, 21, 21, 21, 21, 21, 21, 21, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 21, 16, 8, 8, 4, 4, 4, 4, 4};
        int jon_range[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 4, 4, 4, 8, 8, 8, 8, 16, 16, 16, 16, 17, 19, 21, 21, 0, 0, 0, 0, 0, 0, 0, 0};
        int jon_range_first[] = {4, 4, 4, 4, 4, 4, 4, 4, 16, 16, 16, 16, 16, 16, 16, 16, 8, 8, 8, 8, 8, 19, 19, 21, 21, 21, 21, 1, 21, 21, 16, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 21, 16, 8, 8, 4, 4, 4, 4, 4};

        char nameArr[] = new char[LEN+1];

        int j=0;
        int a, b, c;
        int cho;
        for(j=0 ; j<len ; j++) {
            switch (user.charAt(j)){
                case ' ' : cho=19; break;  //EX) " "
                case 'ㄱ': cho=0; break;    //EX) ㄱ
                case 'ㄲ': cho=1; break;
                case 'ㄴ': cho=2; break;
                case 'ㄷ': cho=3; break;
                case 'ㄸ': cho=4; break;
                case 'ㄹ': cho=5; break;
                case 'ㅁ': cho=6; break;
                case 'ㅂ': cho=7; break;
                case 'ㅃ': cho=8; break;
                case 'ㅅ': cho=9; break;
                case 'ㅆ': cho=10; break;
                case 'ㅇ': cho=11; break;
                case 'ㅈ': cho=12; break;
                case 'ㅉ': cho=13; break;
                case 'ㅊ': cho=14; break;
                case 'ㅋ': cho=15; break;
                case 'ㅌ': cho=16; break;
                case 'ㅍ': cho=17; break;
                case 'ㅎ': cho=18; break;
                default: cho=-1;    //EX) 각
            };
            int jun = rand.nextInt(50);
            int jon = rand.nextInt(50);
            if(cho!=(-1)){//EX) ㄱ, " "
                if(j==0){               //첫글자
                    b = jun_range_e[jun]; c = jon_range_first[jon];
                    if(cho==19) a = cho_range_first[rand.nextInt(50)];
                    else a = cho;
                }
                else if(j==len-1 && len>3){      //마지막글자
                    b = jun_range_e[jun]; c = jon_range_last[jon];
                    if(cho==19) a = cho_range_first[rand.nextInt(50)];
                    else a = cho;
                }
                else{                   //나머지글자
                    b = jun_range_o[jun]; c = jon_range[jon];
                    if(cho==19) a = cho_range_first[rand.nextInt(50)];
                    else a = cho;
                }
                nameArr[j] = (char)(0xAC00 + 21*28*(a) + 28*(b) + (c));
            }
            else {//EX) 각
                nameArr[j]=user.charAt(j);
            }
            //nameArr[LEN] = '\n';
        }
        String namesArr = String.valueOf(nameArr);

        return namesArr;
    }

    public char saveInput(String s){
        char c;
        if(s.isEmpty()){c = ' ';}
        else{c = s.charAt(0);}
        return c;
    }

}

