package com.example.hp.bromocinema;

import android.content.Intent;
import android.media.session.MediaController;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.film);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.cooll);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        Bundle tent =getIntent().getExtras();

        TextView title = (TextView)findViewById(R.id.filmtitle);
        TextView description = (TextView)findViewById(R.id.filmdes);
        ImageView image=(ImageView)findViewById(R.id.filmimage);
        TextView date = (TextView)findViewById(R.id.filmdate);
        TextView rate = (TextView)findViewById(R.id.filmrate);
        TextView vode = (TextView)findViewById(R.id.filmvode);


        title.setText(tent.getString("title"));
        description.setText(tent.getString("description"));
        date.setText(tent.getString("date"));
        rate.setText(tent.getString("rate"));
        vode.setText(tent.getString("vode"));

        String url=tent.getString("image");
        Picasso.with(this).load(url).into(image);

        String Id=tent.getString("id");


        new VideoJson(SecondActivity.this,Id).execute();




/*
  <VideoView

            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:id="@+id/videoView"
            android:layout_gravity="left" />


*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


}
