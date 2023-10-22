package com.nick.nicknamecreator.activities;

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

import com.nick.nicknamecreator.service.CombineFunc;
import com.nick.nicknamecreator.service.MyData;
import com.nick.nicknamecreator.ui.MemoPopup;
import com.nick.nicknamecreator.R;
import com.nick.nicknamecreator.service.SQLHelper;

/*기본 랜덤*/
public class ActivityBasic extends AppCompatActivity
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
    char[] userInput = new char[LEN+1];
    String userInputString;
    static int clicked = 0;
    static Toast toast;
    private static final MyData data = new MyData();
    private final CombineFunc func = new CombineFunc();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_length_text);

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
                    showToast("저장 완료!");
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
                userInput[5] = '\n';
                userInputString = String.valueOf(userInput);
                Log.d("유저입력", userInputString);

                try{
                    if(et_length.getText().toString().equals("")){
                        showToast("숫자를 입력하세요");
                    }
                    else {
                        try {
                            int len = Integer.parseInt("" + et_length.getText());
                            if ( len < 1 || len > 5)
                                showToast("1~5글자까지 가능합니다");
                            else {
                               showToast("얍!");
                                int i;
                                for(i=0; i<TVS; i++){
                                    tvs[i].setText(createName(len, userInputString));
                                }
                            }
                        } catch (Exception e) {
                            showToast("숫자를 읽을 수가 없어요ㅠㅠ");
                        }
                    }
                }catch (Exception e){
                    showToast("제대로 입력해주세요!");
                    e.printStackTrace();
                }
            }
        });
    }
    public StringBuilder createName(int len, String user){             //마지막 글자 확률 다르게
        StringBuilder name = new StringBuilder();
        int j=0;
        int a, b, c;
        int cho;
        for(j=0 ; j<len ; j++) {
            switch (user.charAt(j)){
                case ' ' : cho=20; break;  //입력안한경우
                case 'ㄱ': cho=0; break;    //초성입력
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
                default: cho=-1;    //완성된 글자 입력
            };

            if(cho!=(-1)){//입력이 없거나 초성인 경우
                if(j==0){//첫글자
                    if(cho != 20){
                        name.append(func.createC(new int[]{cho}, data.JUN_FIRST, data.JON_FIRST));
                    }else{
                        name.append(func.createC(data.CHO_FIRST, data.JUN_FIRST, data.JON_FIRST));
                    }
                }
                else if(j==len-1 && len>3){//마지막글자
                    if(cho != 20){
                        name.append(func.createC(new int[]{cho}, data.JUN_LAST, data.JON_LAST));
                    }else{
                        name.append(func.createC(data.CHO_LAST, data.JUN_LAST, data.JON_LAST));
                    }
                }
                else{//나머지글자
                    if(cho != 20){
                        name.append(func.createC(new int[]{cho}, data.JUN, data.JON));
                    }else{
                        name.append(func.createC(data.CHO, data.JUN, data.JON));
                    }
                }
            }
            else {// 완성된 글자를 입력한 경우
                name.append(user.charAt(j));
            }
            //nameArr[LEN] = '\n';
        }
        return name;
    }

    public char saveInput(String s){
        char c;
        if(s.isEmpty()){
            c = ' ';
        }
        else{
            c = s.charAt(0);
        }
        return c;
    }

    // Toast 겹치지 않게 띄우는 함수
    public void showToast(String text){
        if(toast != null){
            toast.cancel();
        }
        Toast toast_new = Toast.makeText(ActivityBasic.this, text, Toast.LENGTH_SHORT);
        toast_new.show();
        toast = toast_new;
    }
}

