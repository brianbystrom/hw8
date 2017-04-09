package com.example.brianbystrom.hw8;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by msalad on 4/9/2017.
 */

public class MyFiveDayAdapter extends RecyclerView.Adapter<MyFiveDayAdapter.ViewHolder> {
    private ArrayList<FiveDay> mDataset;
    private Context mContext;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        public TextView tv_five;
        public ImageView iv_five;

        public ViewHolder(View v) {
            super(v);
            tv_five = (TextView) v.findViewById(R.id.tv_fiveday_tv);
            iv_five = (ImageView) v.findViewById(R.id.iv_fiveday_iv);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyFiveDayAdapter(ArrayList<FiveDay> myDataset, Context context) {
        mDataset = myDataset;
        mContext = context;
    }



    // Create new views (invoked by the layout manager)
    @Override
    public MyFiveDayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view

        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_fiveday_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyFiveDayAdapter.ViewHolder holder, int position) {
        //holder.iv_five.setImageResource();
        //Picasso.with(mContext).load("http://developer.accuweather.com/sites/default/files/"+position+"-s.png").into(holder.iv_five);
        holder.tv_five.setText(mDataset.get(position).getDate());
       // Log.d("dates",(mDataset.get(8).getDate()));




    }

    @Override
    public int getItemCount() {
        Log.d("size of fiveday",mDataset.size()+"");
        return mDataset.size();
    }
}
