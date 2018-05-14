package com.ayamovie.movieapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {
EditText email,password;
Button login;
SharedPreferences sharedPreferences;
SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences=getSharedPreferences("pref_file",MODE_PRIVATE);

        if(sharedPreferences.contains("emailKey")){

            Intent intent=new Intent(Login.this,MainActivity.class);
            startActivity(intent);
        }
        setContentView(R.layout.activity_login);


        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        login=findViewById(R.id.login);

        editor=sharedPreferences.edit();


   login.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           editor.putString("emailKey",email.getText().toString());
           editor.putString("passwordKey",password.getText().toString());
           editor.commit();
           editor.apply();


           Intent intent=new Intent(Login.this,MainActivity.class);
           startActivity(intent);
       }
   });
    }


}
