package com.example.rakesh.eatvm;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HotelList extends AppCompatActivity {

    private RecyclerView mHotelList;
    private DatabaseReference mDatabase;

    RecyclerView.Adapter raptor;
    ProgressDialog progressDialog;

    List<Hotel> obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_list);

        progressDialog = new ProgressDialog(HotelList.this);
        mHotelList = (RecyclerView)findViewById(R.id.hotellist);

        mHotelList.setHasFixedSize(true);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);

        mHotelList.setLayoutManager(manager);

        obj = new ArrayList<>();

      //  mHotelList.setLayoutManager(new LinearLayoutManager(this));
        raptor = new RecyclerAdaptor(obj,HotelList.this);
        progressDialog.setMessage("Loading. . .");
        progressDialog.show();
        mDatabase = FirebaseDatabase.getInstance().getReference("Item");

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Hotel hotel = new Hotel();
                hotel = dataSnapshot.getValue(Hotel.class);
                obj.add(hotel);
               // Hotel newHotel = new Hotel();
                String name = hotel.getDescription();
                Log.i("Values",String.valueOf(obj));
                Log.i("Values",name);
              //  newHotel.setHotelName(hotel.getHotelName());
                raptor = new RecyclerAdaptor(obj,HotelList.this);
                mHotelList.setAdapter(raptor);
                progressDialog.dismiss();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });





    }
    


}


