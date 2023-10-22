package com.nick.nicknamecreator.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.Random;

/*외국닉 랜덤*/
public class ActivityFor extends AppCompatActivity {

    private final int[] TV_ids = {R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4, R.id.tv5, R.id.tv6, R.id.tv7, R.id.tv8, R.id.tv9, R.id.tv10, R.id.tv11, R.id.tv12, R.id.tv13, R.id.tv14, R.id.tv15, R.id.tv16};
    private EditText et_length;
    private final TextView[] tvs = new TextView[TVS];
    private static final int TVS = 16;
    private final Context context = this;
    private SQLHelper helper;

    int popup = 0;
    final int MAX_LEN = 8;
    static Toast toast;
    private final CombineFunc func = new CombineFunc();
    private static final MyData data = new MyData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_length);

        et_length = findViewById(R.id.editText_length);
        Button b_create = findViewById(R.id.button_create);
        Button btn_memo = findViewById(R.id.memo_btn);
        helper = SQLHelper.getInst(context);


        for (int i = 0; i < TVS; i++) {
            tvs[i] = (TextView) findViewById(TV_ids[i]);
            String CLEAR_OUTPUT_TEXT = "";
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
            public void onClick(View v) {
                popup = getIntent().getIntExtra("popup", -1);

                if (et_length.getText().toString().equals("")) {
                    showToast("숫자를 입력하세요");
                } else {
                    try {
                        int len = Integer.parseInt("" + et_length.getText());

                        if (len < 1 || len > 8)
                            showToast("1~8글자만 가능합니다");
                        else {
                            showToast("얍!");
                            //입력받은 개수에 맞게 랜덤으로 조합합니다
                            int i;
                            for (i = 0; i < TVS; i++) {
                                tvs[i].setText(createName_ver4(len));
                            }
                        }
                    } catch (Exception e) {
                        showToast("숫자를 읽을 수가 없어요ㅠㅠ");
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public String createName_ver4(int len) {
        Random rand = new Random();
        StringBuilder newName = new StringBuilder();
        for(int i=0; i<len; i++){
            double AorB = Math.random();
            if(AorB > 0.1){ //한글자 배열에서 고름
                if(i==0){
                   newName.append(func.pickOne(data.FOR_ONE_FIRST_CNT, data.FOR_ONE_FIRST_TEXT));
                }else if(i==len-1){
                    newName.append(func.pickOne(data.FOR_ONE_LAST_CNT, data.FOR_ONE_LAST_TEXT));
                }else{
                   newName.append(func.pickOne(data.FOR_ONE_CNT, data.FOR_ONE_TEXT));
                }
            }else{  //랜덤으로 한글자 조합
                if(i==0){
                    newName.append(func.createC(data.CHO_FIRST, data.JUN_FIRST, data.JON_FIRST));
                }else if(i==len-1){
                    newName.append(func.createC(data.CHO_LAST, data.JUN_LAST, data.JON_LAST));
                }else{
                    newName.append(func.createC(data.CHO, data.JUN, data.JON));
                }
            }
//            Log.i("단어생성중",i+newName);
        }
//        Log.i("단어생성완료",newName);
        return newName.toString();
    }

    // Toast 겹치지 않게 띄우는 함수
    public void showToast(String text){
        if(toast != null){
            toast.cancel();
        }
        Toast toast_new = Toast.makeText(ActivityFor.this, text, Toast.LENGTH_SHORT);
        toast_new.show();
        toast = toast_new;
    }
}