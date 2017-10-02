package com.ankrish.firebaseauthentication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.*;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity {

    private TextView email;
    private EditText name,phno,address;
    private Button logout,submit_info;

    private FirebaseAuth auth;
    DatabaseReference dr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name=(EditText)findViewById(R.id.name);
        phno=(EditText) findViewById(R.id.phno1);
        address=(EditText)findViewById(R.id.address);
        submit_info=(Button)findViewById(R.id.submit_info);


        email=(TextView)findViewById(R.id.profile_email);
        logout=(Button)findViewById(R.id.logout);

        auth=FirebaseAuth.getInstance();
        dr= FirebaseDatabase.getInstance().getReference();

        if(auth.getCurrentUser()==null)
        {
            finish();
            startActivity(new Intent(ProfileActivity.this, MainActivity.class));
        }

        FirebaseUser user= auth.getCurrentUser();

        email.setText("Welcome "+user.getEmail());

        submit_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String profile_name=name.getText().toString().trim();
                String profile_phno=phno.getText().toString().trim();
                String profile_address=address.getText().toString().trim();

                if(TextUtils.isEmpty(profile_name))
                {
                    Toast.makeText(ProfileActivity.this,"Please Enter Your Name",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(profile_phno))
                {
                    Toast.makeText(ProfileActivity.this,"Please Enter Your Phone No",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(profile_address))
                {
                    Toast.makeText(ProfileActivity.this,"Please Enter Your Address",Toast.LENGTH_SHORT).show();
                    return;
                }
                UserInfo info=new UserInfo(profile_name,profile_phno,profile_address);

                FirebaseUser user= auth.getCurrentUser();


                dr.child(user.getUid()).setValue(info);

                Toast.makeText(ProfileActivity.this,"Insesrting Values",Toast.LENGTH_SHORT).show();


            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();

                startActivity(new Intent(ProfileActivity.this,LoginActivity.class));
                finish();
                overridePendingTransition(R.anim.mainfadein, R.anim.splashfadeout);
            }
        });


    }
}
