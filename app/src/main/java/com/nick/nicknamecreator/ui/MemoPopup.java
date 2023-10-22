package com.nick.nicknamecreator.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.nick.nicknamecreator.R;
import com.nick.nicknamecreator.service.SQLHelper;


public class MemoPopup extends Activity {
    private TextView text_nick;
    private SQLHelper helper;
    private final Context context = this;
    static Toast toast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_memo);
        Button save_btn = (Button) findViewById(R.id.btn);
        Button close_btn = (Button) findViewById(R.id.close_btn);
        text_nick = (EditText)findViewById(R.id.infoText);

        helper = SQLHelper.getInst(context);

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = text_nick.getText().toString();
                if(value.equals("") || value.replace(" ", "").length()==0){
                    showToast("내용을 입력해주세요");
                }else{
                    helper.insertMemo(value);
                    text_nick.setText("");
                    showToast("저장 완료!");
                }
            }
        });
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void showToast(String text){
        if(toast==null){
            toast = Toast.makeText(MemoPopup.this, text, Toast.LENGTH_SHORT);
        }else{
            toast.setText(text);
        }
        toast.show();
    }
}