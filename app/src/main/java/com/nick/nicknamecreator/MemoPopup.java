package com.nick.nicknamecreator;

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


public class MemoPopup extends Activity {
    private Button save_btn;
    private Button close_btn;
    private TextView text_nick;
    private SQLHelper helper;
    private Context context = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_memo);
        save_btn = (Button) findViewById(R.id.btn);
        close_btn = (Button)findViewById(R.id.close_btn);
        text_nick = (EditText)findViewById(R.id.infoText);

        helper = helper.getInst(context);

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MemoPopup.this, "저장했습니다!", Toast.LENGTH_SHORT).show();
                String value = text_nick.getText().toString();
                helper.insertMemo(value);
                finish();
            }
        });
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}