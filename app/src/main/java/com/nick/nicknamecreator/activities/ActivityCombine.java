package com.nick.nicknamecreator.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.nick.nicknamecreator.R;
import com.nick.nicknamecreator.service.CombineFunc;
import com.nick.nicknamecreator.service.MyData;
import com.nick.nicknamecreator.service.SQLHelper;
import com.nick.nicknamecreator.ui.MemoPopup;

import java.util.Random;

public class ActivityCombine extends AppCompatActivity {
    private static final String CLEAR_OUTPUT_TEXT = "";
    private final int [] TV_ids = {R.id.textView6, R.id.textView7, R.id.textView8, R.id.textView9, R.id.textView10};
    private final static int TVS = 5;
    private final TextView[] tvs = new TextView[TVS];
    private final Context context = this;
    private SQLHelper helper;
    static Toast toast;
    int popup = 0;
    final int MAX_LEN = 8;
    private final CombineFunc func = new CombineFunc();
    private static final MyData data = new MyData();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combinate);

        Button b_create = findViewById(R.id.button_create);
        Button btn_memo = findViewById(R.id.memo_btn);
        helper = SQLHelper.getInst(context);


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
                showToast("얍!");
                //랜덤으로 조합합니다
                for(int i=0; i<TVS; i++){
                    tvs[i].setText(combine_word());
                }
            }
        });
    }
    public String combine_word(){
        Random rand = new Random();
        String a = data.A[rand.nextInt(data.A.length)];
        String b = data.B[rand.nextInt(data.B.length)];
        return (a+" "+b);
    }

    // Toast 겹치지 않게 띄우는 함수
    public void showToast(String text){
        if(toast != null){
            toast.cancel();
        }
        Toast toast_new = Toast.makeText(ActivityCombine.this, text, Toast.LENGTH_SHORT);
        toast_new.show();
        toast = toast_new;
    }
}