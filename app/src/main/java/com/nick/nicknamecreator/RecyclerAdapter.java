package com.nick.nicknamecreator;

import android.app.AlertDialog;
import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.mViewHolder> {

    private ArrayList<String> items;
    private SQLHelper helper;
    private Context context;

    public class mViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView memoText;

        public mViewHolder(@NonNull View itemView) {
            super(itemView);
            memoText = itemView.findViewById(R.id.memoText);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem delete = menu.add(Menu.NONE, 1, 1, "삭제");
            delete.setOnMenuItemClickListener(onMenu);
        }

        private MenuItem.OnMenuItemClickListener onMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final MenuItem item) {
                switch (item.getItemId()) {
                    case 1:
                        int pos = getAdapterPosition();
                        String memo = items.get(pos);
                        items.remove(pos);
                        helper.deleteMemo(memo);
                        notifyItemRemoved(pos);
                        notifyItemRangeChanged(pos, items.size());
                        break;
                }
                return true;
            }
        };
    }

    public RecyclerAdapter(ArrayList<String> items, Context context) {
        this.items = items;
        this.context = context;
        this.helper = helper.getInst(context);
    }

    @NonNull
    @Override
    public mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        RecyclerAdapter.mViewHolder holder = new RecyclerAdapter.mViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull mViewHolder holder, int position) {
        String memo = items.get(position);
        holder.memoText.setText(memo);
    }

    @Override
    public int getItemCount() {
        return (items == null) ? 0 : items.size();
    }

    public void clear(){ items.clear();}
}
