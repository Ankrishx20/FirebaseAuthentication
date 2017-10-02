package com.ankrish.firebaseauthentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText login_email,login_password;
    private Button login;
    private TextView signup;
    private ProgressDialog pd;

    private FirebaseAuth auth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_email= (EditText) findViewById(R.id.login_email);
        login_password= (EditText) findViewById(R.id.login_password);
        login= (Button) findViewById(R.id.login);
        signup= (TextView) findViewById(R.id.signup);

        pd=new ProgressDialog(this);
        auth = FirebaseAuth.getInstance();


        if(auth.getCurrentUser()!=null)
        {

            startActivity(new Intent(LoginActivity.this,ProfileActivity.class));
            finish();
        }


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=login_email.getText().toString().trim();
                String password=login_password.getText().toString().trim();

                if(TextUtils.isEmpty(email))
                {
                    Toast.makeText(LoginActivity.this,"Please Enter Email Id",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(password))
                {
                    Toast.makeText(LoginActivity.this,"Please Enter Password",Toast.LENGTH_SHORT).show();
                    return;
                }



                pd.setMessage("Log In");
                pd.show();

                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(LoginActivity.this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        pd.dismiss();
                    if(task.isSuccessful())
                    {
                        finish();
                        startActivity(new Intent(LoginActivity.this,ProfileActivity.class));
                    }
                    else
                    {
                        pd.dismiss();

                        Toast.makeText(LoginActivity.this,"Wrong Email/ Password",Toast.LENGTH_SHORT).show();

                    }
                    }
                });

            }
        });
    }
}
