package com.example.rakesh.eatvm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference mDatabase;
    private FirebaseDatabase firebaseDatabase;

   ProgressDialog prg;
    private EditText email;
    private EditText pass;
    private Button loginbtn,registerbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginbtn = (Button)findViewById(R.id.login);
        email = (EditText) findViewById(R.id.useremail);
        pass = (EditText)findViewById(R.id.userpass);
        mAuth = FirebaseAuth.getInstance();
        registerbtn = (Button)findViewById(R.id.register);
        prg = new ProgressDialog(MainActivity.this);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");


       /*authStateListener = new FirebaseAuth.AuthStateListener() {

          @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

              FirebaseUser user = firebaseAuth.getCurrentUser();
              if( user != null){
                  Intent intent = new Intent(MainActivity.this,Home.class);
                  startActivity(intent);
                  finish();
                  Log.i("USER",String.valueOf(user));
                 // Toast.makeText(MainActivity.this,String.valueOf(user),Toast.LENGTH_LONG).show();
              }
               //
           }
        };*/

       registerbtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(MainActivity.this,Register.class);
               startActivity(intent);
           }
       });

    }

  /*  @Override
    protected void onStart() {
        super.onStart();
        //  FirebaseUser currentUser = mAuth.getCurrentUser();
        mAuth.addAuthStateListener(authStateListener);

    }*/

    public void userLoginClicked(View view){
        prg.setMessage("Logging In. . . ");
        prg.show();
        String emailtext = email.getText().toString();
        String password = pass.getText().toString();

        if(TextUtils.isEmpty(emailtext)||TextUtils.isEmpty(password)){

            Toast.makeText(MainActivity.this,"Enter both values!!1",Toast.LENGTH_LONG).show();

        }else{

            mAuth.signInWithEmailAndPassword(emailtext,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(!task.isSuccessful()){
                        Toast.makeText(MainActivity.this,"Invalid Username/Password",Toast.LENGTH_LONG).show();

                    }else if(task.isSuccessful()){
                        checkUserExists();
                    }

                    prg.dismiss();
                }
            });
        }
    }


    public void checkUserExists(){
       final String userid = mAuth.getCurrentUser().getUid();
       mDatabase.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(userid)){
                    Intent homeIntent = new Intent(MainActivity.this,Home.class);
                    startActivity(homeIntent);
                    finish();
                }
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });
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


}
