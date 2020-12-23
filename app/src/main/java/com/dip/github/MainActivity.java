package com.dip.github;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dip.rest.APIClient;
import com.dip.rest.GitHubUserEndPoint;

public class MainActivity extends AppCompatActivity {
EditText tvname;
Button login;
String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final SharedPreferences sharedPreferences = getSharedPreferences("MYPREFERENCE", MODE_PRIVATE);
          tvname=(EditText)findViewById(R.id.textboxname);
          login=(Button)findViewById(R.id.button);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=tvname.getText().toString();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("name", name);
                Toast.makeText(getApplicationContext(),name,Toast.LENGTH_LONG).show();
                editor.apply();
                Intent i=new Intent(MainActivity.this,UserActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(i);
            }
        });
    }
}