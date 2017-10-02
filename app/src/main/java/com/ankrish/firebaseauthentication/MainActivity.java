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


public class MainActivity extends AppCompatActivity {

    private EditText user,pass;
    private Button submit;
    private TextView sign;
    private ProgressDialog pd;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user= (EditText) findViewById(R.id.user);
        pass= (EditText) findViewById(R.id.pass);
        submit= (Button) findViewById(R.id.submit);
        sign= (TextView) findViewById(R.id.signin);

        pd=new ProgressDialog(this);
        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser()!=null)
        {
            finish();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));

        }


        sign.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email =user.getText().toString().trim();
                String password=pass.getText().toString().trim();

                if(TextUtils.isEmpty(email))
                {
                    Toast.makeText(MainActivity.this,"Please Enter Email Id",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(password))
                {
                    Toast.makeText(MainActivity.this,"Please Enter Password",Toast.LENGTH_SHORT).show();
                    return;
                }
                pd.setMessage("Registering");
                pd.show();

                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(MainActivity.this,
                        new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        pd.dismiss();
                        if (task.isSuccessful()){
                            finish();
                            startActivity(new Intent(MainActivity.this,ProfileActivity.class));

                        }
                        else {
                            pd.dismiss();
                            Toast.makeText(MainActivity.this, "Could not register. please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}
