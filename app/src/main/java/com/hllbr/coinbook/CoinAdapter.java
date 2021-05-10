package com.hllbr.coinbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CoinAdapter extends ArrayAdapter<String> {
    private ArrayList<String> coinName;
    private  ArrayList<Integer> image;
    private Context context;
    private TextView coinnn;
    private ImageView coinresim;
    public CoinAdapter(ArrayList<String> coinName, ArrayList<Integer> image, Context context){
        super(context,R.layout.coin_item,coinName);
        this.coinName = coinName;
        this.image = image;
        this.context = context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.coin_item,null);
        if(view != null){
            coinnn = (TextView) view.findViewById(R.id.coin_item_textView);
            coinresim =view.findViewById(R.id.coin_item_imageViewResim);

            coinnn.setText(coinName.get(position));
            coinresim.setBackgroundResource(image.get(position));
        }
        return view;
    }
}
