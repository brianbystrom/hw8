/*
Assignment #: Homework 07
File Name: MyAdapter.java
Group Members: Brian Bystrom, Mohamed Salad
*/

package com.example.brianbystrom.hw8;

import android.content.Context;
import android.content.SharedPreferences;
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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by brianbystrom on 3/9/17.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<City> mDataset;
    private Context mContext;
    private Weather clickedRadio;
    SharedPreferences sharedpreferences;
    private DatabaseReference mDatabase;
    DatabaseReference myRef;


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
        public View v;
        public ViewHolder(View v) {
            super(v);
            this.v = v;
            tv_citycountry = (TextView) v.findViewById(R.id.tv_citycountry);
            tv_lastupdate = (TextView) v.findViewById(R.id.tv_lastupdate);
            tv_temp = (TextView) v.findViewById(R.id.tv_temp);
            fav = (ImageView) v.findViewById(R.id.tv_fav);



        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<City> myDataset, Context context) {
        mDataset = myDataset;
        mContext = context;
        sharedpreferences = mContext.getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);

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

        holder.v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d("CLICK",mDataset.get(position).getKey());
                mDatabase = FirebaseDatabase.getInstance().getReference().child("city");
                myRef = mDatabase;
                myRef.child(mDataset.get(position).getKey()).removeValue();
                notifyDataSetChanged();
                return true;
            }
        });

        holder.tv_citycountry.setText(mDataset.get(position).getName()+","+mDataset.get(position).getCountry());

        //Log.d("POOP",mDataset.get(position).getPublished_date().substring(5,7));
        //int day = Integer.parseInt(mDataset.get(position).getPublished_date().substring(5,7));
        //int month = convertMonthToNumber(mDataset.get(position).getPublished_date().substring(8,11));
        //int year = Integer.parseInt(mDataset.get(position).getPublished_date().substring(12,16));
        holder.tv_temp.setText("Temperature: " + mDataset.get(position).getTempF() + " F");

        PrettyTime p = new PrettyTime();

        Log.d("DATE", p.format(new Date(mDataset.get(position).getUpdated())));

        holder.tv_lastupdate.setText("Last Updated: " + p.format(new Date(mDataset.get(position).getUpdated())));


        String keyString = sharedpreferences.getString("CURRENT CITY", "");
        String[] key = keyString.split("\\|");
        Log.d("DEMO", key[0]);
        String thisKey = mDataset.get(position).getKey();
        if(mDataset.get(position).getKey().equals(key[0])){
            holder.fav.setImageResource(R.mipmap.star_gold);
        }
        else
        {
            holder.fav.setImageResource(R.mipmap.star_gray);
        }


        holder.fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = sharedpreferences.edit();

                String cityString;
                cityString = mDataset.get(position).getKey() + "|" + mDataset.get(position).getName() + "|" + mDataset.get(position).getCountry();
                Log.d("CITYSTRING", cityString);
                editor.putString("CURRENT CITY", cityString);
                editor.commit();
                notifyDataSetChanged();

            }
        });






    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}

