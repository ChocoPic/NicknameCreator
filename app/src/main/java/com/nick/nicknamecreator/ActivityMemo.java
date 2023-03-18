package com.nick.nicknamecreator;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ActivityMemo extends AppCompatActivity {
    private Context context = this;
    private TextView blankText;
    private Button clear_btn;
    private SQLHelper helper;
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private ArrayList<String> items;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        recyclerView = findViewById(R.id.memo_list);
        blankText = (TextView)findViewById(R.id.text_blank);
        clear_btn = (Button)findViewById(R.id.button_clear);

        helper = new SQLHelper(this);
        items = new ArrayList<>();
        items = loadDataList();

        LinearLayoutManager layout = new LinearLayoutManager(context);
        layout.setReverseLayout(true);  //역순
        layout.setStackFromEnd(true);   //역순
        recyclerView.setLayoutManager(layout);

        adapter = new RecyclerAdapter(items, context);
        recyclerView.setAdapter(adapter);

        clear_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.clearMemo(context);
                Toast.makeText(ActivityMemo.this, "모두 삭제되었습니다", Toast.LENGTH_SHORT).show();
                adapter.clear();
                adapter.notifyDataSetChanged();
                blankText.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void onDestroy() {
        helper.close();
        super.onDestroy();
    }

    public ArrayList<String> loadDataList() {
        ArrayList<String> memos = new ArrayList<>();
        int cnt = 0;
        try {
            helper = new SQLHelper(ActivityMemo.this);
            Cursor c = helper.getAllData();
            if (c.moveToFirst()) {
                do {
                    String data = c.getString(1);
                    memos.add(data);
                    cnt++;
                } while (c.moveToNext());
            }
            c.close();
            if(cnt!=0)
                blankText.setVisibility(View.GONE);
            else
                blankText.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return memos;
    }
}
