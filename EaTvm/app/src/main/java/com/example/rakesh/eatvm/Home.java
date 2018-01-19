package com.example.rakesh.eatvm;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Home extends AppCompatActivity {

    private Button image,menu,upload;
    private EditText name,longitude,lattitude,description;
    private static final int GALLERQ =1;
    private static final int GALLERQ1 =1;
    private Uri uriImage = null,uriMenu = null;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private StorageReference storageReference = null;
    String hname,hlongi,hlati,hdesc;
    private DatabaseReference mRef;
    private FirebaseDatabase firebaseDatabase;
    private Uri downloadImageurl,downloadMenuurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        image = (Button)findViewById(R.id.imgbtn);
       // menu = (Button)findViewById(R.id.menuimage);
        upload = (Button)findViewById(R.id.upload);

        name = (EditText)findViewById(R.id.hotelname);
        longitude = (EditText) findViewById(R.id.longi);
        lattitude = (EditText)findViewById(R.id.lati);
        description = (EditText)findViewById(R.id.dec_text);

        storageReference = FirebaseStorage.getInstance().getReference();
        mRef = FirebaseDatabase.getInstance().getReference("Item");

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
              //  galleryIntent.setType("Image/*");
                startActivityForResult(galleryIntent,GALLERQ);
            }
        });

       /* menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
               // galleryIntent.setType("Image/*");
                startActivityForResult(galleryIntent,GALLERQ1);
            }
        });*/

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                hname = name.getText().toString();
                hlongi = longitude.getText().toString();
                hlati = lattitude.getText().toString();
                hdesc = description.getText().toString();

                if(!TextUtils.isEmpty(hname) && !TextUtils.isEmpty(hlongi) && !TextUtils.isEmpty(hlati) && !TextUtils.isEmpty(hdesc)){
                    StorageReference filepathImage = storageReference.child(uriImage.getLastPathSegment());
                    filepathImage.putFile(uriImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            downloadImageurl = taskSnapshot.getDownloadUrl();
                            final DatabaseReference newPost = mRef.push();
                            newPost.child("HotelName").setValue(hname);
                            newPost.child("Longitude").setValue(hlongi);
                            newPost.child("Latitude").setValue(hlati);
                            newPost.child("Description").setValue(hdesc);
                            newPost.child("ImageUrl").setValue(downloadImageurl.toString());
                           // newPost.child("MenuUrl").setValue(downloadMenuurl.toString());

                        }
                    });

                 /*   StorageReference filepathMenu = storageReference.child(uriMenu.getLastPathSegment());
                    filepathMenu.putFile(uriMenu).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            downloadMenuurl = taskSnapshot.getDownloadUrl();

                        }
                    });*/




                }

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERQ && resultCode==RESULT_OK){
            uriImage = data.getData();
        }/*else if(requestCode == GALLERQ1 && resultCode==RESULT_OK){
            uriMenu = data.getData();
        }*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id==R.id.logout){
            mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();
            Intent intent = new Intent(Home.this,MainActivity.class);
            startActivity(intent);
            finish();
        }else if (id ==R.id.hotellist){
            Intent intent = new Intent(Home.this,HotelList.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
