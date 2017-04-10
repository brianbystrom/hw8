package com.example.brianbystrom.hw8;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
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
import java.util.Date;

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
    public void onBindViewHolder(MyFiveDayAdapter.ViewHolder holder, final int position) {
        //holder.iv_five.setImageResource();
        String icon;
        if(mDataset.get(position).getIconDay() < 10) {
            icon = "0" + mDataset.get(position).getIconDay();
        } else {
            icon = mDataset.get(position).getIconDay() + "";
        }

        Picasso.with(mContext).load("http://developer.accuweather.com/sites/default/files/"+icon+"-s.png").into(holder.iv_five);

        holder.iv_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creates alert dialog for set city with 2 ET and adds to LinearLayout
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Weather details for: " + mDataset.get(position).getDate());

                AlertDialog dialog = builder.create();

                TextView dateTV = new TextView(mContext);
                TextView minTV = new TextView(mContext);
                TextView maxTV = new TextView(mContext);
                TextView cityTV = new TextView(mContext);

                minTV.setText("Min Temp: " + mDataset.get(position).getMin() + " F");
                maxTV.setText("Max Temp: " + mDataset.get(position).getMax() + " F");
                cityTV.setText("City: " + mDataset.get(position).getCity() + ", " + mDataset.get(position).getCountry());





                //setCityET.setHint("Enter your city");
                //setCountryET.setHint("Set your country");

                LinearLayout ll = new LinearLayout(mContext);
                ll.setOrientation(LinearLayout.VERTICAL);
                ll.removeAllViews();
                ll.addView(cityTV);
                ll.addView(maxTV);
                ll.addView(minTV);
                builder.setView(ll);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //setCity.setValue(setCityET.getText().toString());
                        //setCountry.setValue(setCountryET.getText().toString());

                    }
                });



                dialog = builder.create();
                dialog.show();

            }
        });
        //Picasso.with(mContext).load("http://developer.accuweather.com/sites/default/files/"+position+"-s.png").into(holder.iv_five);
       // Log.d("dates",(mDataset.get(8).getDate()));

        //String dateTimeAsString = new Date( mDataset.get(position).getDate()).toString();
        holder.tv_five.setText(mDataset.get(position).getDate());



    }

    @Override
    public int getItemCount() {
        Log.d("size of fiveday",mDataset.size()+"");
        return mDataset.size();
    }
}
