package com.example.afusesc.tastynready;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdaptadorComidas extends RecyclerView.Adapter<AdaptadorComidas.ViewHolderComidas> {

    private List<MyItem> itemList;
    private Context context;

    public AdaptadorComidas(Context context, List<MyItem> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public ViewHolderComidas onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolderComidas(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderComidas holder, int position) {
        MyItem item = itemList.get(position);
        holder.textView.setText(item.getText());
        holder.imageView.setImageResource(item.getImageResId());
        holder.textView2.setText(item.getText2());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolderComidas extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public TextView textView2;

        public ViewHolderComidas(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_entrante);
            textView = itemView.findViewById(R.id.textView);
            textView2 = itemView.findViewById(R.id.textView2);
        }
    }

    public static class MyItem {
        private String text;
        private int imageResId;
        private String text2;

        public MyItem(String text, int imageResId, String text2) {
            this.text = text;
            this.imageResId = imageResId;
            this.text2 = text2;
        }

        public String getText() {
            return text;
        }

        public int getImageResId() {
            return imageResId;
        }

        public String getText2(){ return  text2; }
    }
}