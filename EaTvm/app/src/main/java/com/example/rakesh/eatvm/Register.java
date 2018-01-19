package com.example.rakesh.eatvm;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    private EditText emailid,pass,pass2;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private Button signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        emailid = (EditText)findViewById(R.id.signupemail);
        pass = (EditText)findViewById(R.id.signuppass);
        pass2 = (EditText)findViewById(R.id.confirmpass);
        signup = (Button)findViewById(R.id.signup);
        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email_text = emailid.getText().toString().trim();
                String pass_text = pass.getText().toString().trim();
                String confim_pass = pass2.getText().toString().trim();

                if(!TextUtils.isEmpty(email_text) && !TextUtils.isEmpty(pass_text) && !TextUtils.isEmpty(confim_pass)){

                    if(pass_text.equals(confim_pass)){

                        mAuth.createUserWithEmailAndPassword(email_text,pass_text).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                String user_id = mAuth.getCurrentUser().getUid();
                                DatabaseReference current_user = mDatabase.child(user_id);
                                current_user.child("Name").setValue(email_text);
                            }
                        });
                    }
                }

            }
        });

    }


}
