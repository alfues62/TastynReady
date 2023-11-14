package com.example.afusesc.tastynready;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdaptadorPedidos extends RecyclerView.Adapter<AdaptadorPedidos.ViewHolderPedidos> {

    private List<AdaptadorPedidos.MyItem> itemList;
    private Context context;

    public AdaptadorPedidos(Context context, List<AdaptadorPedidos.MyItem> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public AdaptadorPedidos.ViewHolderPedidos onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_layout, parent, false);
        return new AdaptadorPedidos.ViewHolderPedidos(view);
    }

    @Override
    public void onBindViewHolder(AdaptadorPedidos.ViewHolderPedidos holder, int position) {
        AdaptadorPedidos.MyItem item = itemList.get(position);
        holder.textView.setText(item.getText());
        holder.imageView.setImageResource(item.getImageResId());
        holder.textView2.setText(item.getText2());

        final int minValue = 0;
        final int maxValue = 12;

        holder.incrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentValue = Integer.parseInt(holder.valueEditText.getText().toString());
                if (currentValue < maxValue) {
                    currentValue++;
                }
                holder.valueEditText.setText(String.valueOf(currentValue));
            }
        });

        holder.decrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentValue = Integer.parseInt(holder.valueEditText.getText().toString());
                if (currentValue > minValue) {
                    currentValue--;
                }
                holder.valueEditText.setText(String.valueOf(currentValue));
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolderPedidos extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public TextView textView2;
        public Button incrementButton;
        public Button decrementButton;
        public EditText valueEditText;

        public ViewHolderPedidos(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_entrante);
            textView = itemView.findViewById(R.id.textView);
            textView2 = itemView.findViewById(R.id.textView2);
            incrementButton = itemView.findViewById(R.id.incrementButton);
            decrementButton = itemView.findViewById(R.id.decrementButton);
            valueEditText = itemView.findViewById(R.id.valueEditText);
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
