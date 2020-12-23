package com.dip.github;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dip.model.GitHunUser;
import com.dip.rest.APIClient;
import com.dip.rest.GitHubUserEndPoint;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity {
ImageView avtarImg;
TextView usernameTV,followrsTV,followingTV,login,email;
Button ownedrepos;
String userName;
Bitmap myImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        avtarImg=(ImageView)findViewById(R.id.imageView);
        usernameTV=(TextView)findViewById(R.id.textView);
        followrsTV=(TextView)findViewById(R.id.textView2);
        followingTV=(TextView)findViewById(R.id.textView3);
        login=(TextView)findViewById(R.id.textView4);
        email=(TextView)findViewById(R.id.textView5);
        ownedrepos=(Button) findViewById(R.id.button2);
         SharedPreferences sharedPreferences = getSharedPreferences("MYPREFERENCE", MODE_PRIVATE);
        userName=sharedPreferences.getString("name","");
        Toast.makeText(getApplicationContext(),userName,Toast.LENGTH_LONG).show();

        loadData();
    }

    public void  loadData(){
        final GitHubUserEndPoint apiService= APIClient.getClient().create(GitHubUserEndPoint.class);

        Call<GitHunUser> call=apiService.getUser(userName);
        call.enqueue(new Callback<GitHunUser>() {
            @Override
            public void onResponse(Call<GitHunUser> call, Response<GitHunUser> response) {

                ImageDownloader task = new ImageDownloader();

                try {
                    myImage = task.execute(response.body().getAvatar()).get();

                } catch (Exception e) {

                    e.printStackTrace();

                }

                avtarImg.setImageBitmap(myImage);
                avtarImg.getLayoutParams().height=220;
                avtarImg.getLayoutParams().width=220;

                if(response.body().getName() == null) {
                    usernameTV.setText("No Name Provided");
                }
                else{ usernameTV.setText("UserName:"+ response.body().getName());}
                followrsTV.setText("Followers:"+ response.body().getFollowers());
                followingTV.setText("Following:"+ response.body().getFollowing());
                login.setText("Login:"+ response.body().getLogin());
                if(response.body().getEmail() == null){
                    email.setText("No Email Provided");
                }
                else{email.setText("Email:"+ response.body().getEmail());}
            }

            @Override
            public void onFailure(Call<GitHunUser> call, Throwable t) {

            }
        });
    }


    public class ImageDownloader extends AsyncTask<String ,Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... urls) {
            try{
                URL url =new URL(urls[0]);
                HttpURLConnection connection=(HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            } catch(MalformedURLException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }

            return null;
        }
    }

}

