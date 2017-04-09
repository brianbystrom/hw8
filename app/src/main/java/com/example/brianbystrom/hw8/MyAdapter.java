/*
Assignment #: Homework 07
File Name: MyAdapter.java
Group Members: Brian Bystrom, Mohamed Salad
*/

package com.example.brianbystrom.hw8;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;
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
 * Created by brianbystrom on 3/9/17.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<Weather> mDataset;
    private Context mContext;
    private Weather clickedRadio;
    //public static PlayPodcastAsync aTask;

    int counter = 0;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public LinearLayout mLinearLayout;
        public TextView tv_citycountry, tv_lastupdate, tv_temp;
        public ImageButton mPodcastIb;
        public ImageView fav;
        public ViewHolder(View v) {
            super(v);
            tv_citycountry = (TextView) v.findViewById(R.id.tv_citycountry);
            tv_lastupdate = (TextView) v.findViewById(R.id.tv_lastupdate);
            tv_temp = (TextView) v.findViewById(R.id.tv_temp);
            fav = (ImageView) v.findViewById(R.id.tv_fav);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<Weather> myDataset, Context context) {
        mDataset = myDataset;
        mContext = context;
    }

    private int convertMonthToNumber(String s){
        if(s.equals("Jan")){
            return 1;
        }
        else  if(s.equals("Feb")){
            return 2;
        }
        else  if(s.equals("Mar")){
            return 3;
        }
        else  if(s.equals("Apr")){
            return 4;
        }
        else  if(s.equals("May")){
            return 5;
        }
        else  if(s.equals("Jun")){
            return 6;
        }
        else  if(s.equals("July")){
            return 7;
        }
        else  if(s.equals("Aug")){
            return 8;
        }
        else  if(s.equals("Sep")){
            return 9;
        }
        else  if(s.equals("Oct")){
            return 10;
        }
        else  if(s.equals("Nov")){
            return 11;
        }
        else  if(s.equals("Dec")){
            return 12;
        }
        return 99;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view

        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //TextView tv = (TextView) holder.mLinearLayout.findViewById(R.id.podcast_tv);
        holder.tv_citycountry.setText(mDataset.get(position).getCity()+","+mDataset.get(position).getCountry());

        //Log.d("POOP",mDataset.get(position).getPublished_date().substring(5,7));
        //int day = Integer.parseInt(mDataset.get(position).getPublished_date().substring(5,7));
        //int month = convertMonthToNumber(mDataset.get(position).getPublished_date().substring(8,11));
        //int year = Integer.parseInt(mDataset.get(position).getPublished_date().substring(12,16));
        holder.tv_temp.setText(mDataset.get(position).getTempF() + " F");

        holder.tv_lastupdate.setText(mDataset.get(position).getTime());

        if(mDataset.get(position).isFav()){
            holder.fav.setImageResource(R.mipmap.star_gold);
        }
        else
        {
            holder.fav.setImageResource(R.mipmap.star_gray);
        }






    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}

