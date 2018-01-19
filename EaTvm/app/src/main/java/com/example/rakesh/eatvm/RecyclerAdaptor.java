package com.example.rakesh.eatvm;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by rakesh on 10/1/18.
 */

public class RecyclerAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<Hotel> hotels;

    public RecyclerAdaptor(List<Hotel> hotels,Context context){
        this.hotels = hotels;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflate = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflate.inflate(R.layout.listitem,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Hotel hotel = hotels.get(position);
        ViewHolder hold = (ViewHolder)holder;

        hold.hotelname.setText(hotel.getHotelName());
        hold.setHotelImage(context,hotel.getImageUrl());
        hold.descroption.setText(hotel.getDescription());



    }

    @Override
    public int getItemCount() {

        return hotels.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView hotelname,descroption;
        ImageView hotelImnage;

        public ViewHolder(View itemView) {
            super(itemView);
            hotelname = (TextView)itemView.findViewById(R.id.htname);
            hotelImnage = (ImageView)itemView.findViewById(R.id.dhotelimage);
            descroption = (TextView)itemView.findViewById(R.id.speciality);

        }
        public  void setHotelImage(Context ctx,String image){
            ImageView htimg = (ImageView)itemView.findViewById(R.id.dhotelimage);
            Picasso.with(ctx).load(image).into(htimg);


        }
    }





}
