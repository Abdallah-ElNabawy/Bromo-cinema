package com.example.hp.bromocinema;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.session.MediaController;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private static final String idA = "id";
    private static final String titleA = "title";
    private static final String rateA = "vote_average";
    private static final String descriptionA = "overview";
    private static final String dateA = "release_date";
    private static final String votecountA = "vote_count";
    private static final String imageA = "poster_path";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.cool);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        new jsonp(MainActivity.this).execute();





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    public class jsonp extends AsyncTask<String, String, String>  {
        final public ArrayList<listitem> item = new ArrayList<listitem>();
        myadapter myadap ;
        private ProgressDialog pdialog;
        Context context;
        HttpURLConnection connect;
        URL url;
        StringBuilder sb;
        String read;
        JSONArray Jarray;
        JSONObject object;

        public jsonp(Context context) { this.context=context;}

        @Override
        protected String doInBackground(String... params) {

            try {

                url = new URL("http://api.themoviedb.org/3/movie/top_rated?api_key=4396a759c475ccb9e7e585f444c431d2");
                connect = (HttpURLConnection) url.openConnection();
                connect.setRequestMethod("GET");
                connect.setDoInput(true);
                connect.setDoOutput(true);
                connect.connect();
                BufferedReader buffer = new BufferedReader(new InputStreamReader(connect.getInputStream()));

                String readline;
                sb = new StringBuilder();


                try {
                    while (((readline = buffer.readLine()) != null)) {
                        sb.append(readline + "\n");

                    }

                    read=sb.toString();
                }
                catch(Exception e){ e.getMessage(); }




            } catch (Exception e) {

                e.getMessage();

            }
            finally {
                connect.disconnect();
            }

            return read;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdialog=new ProgressDialog(context);
            pdialog.setMessage("Image Loading ..");
            pdialog.setCancelable(false);
            pdialog.show();

        }


        @Override
        protected void onPostExecute(String r){

        pdialog.dismiss();
            try {
                object = new JSONObject(read);
                Jarray = object.getJSONArray("results");
                for (int i = 0; i < Jarray.length(); i++) {

                    JSONObject jobj = Jarray.getJSONObject(i);
                    item.add(new listitem(
                            jobj.getString(idA).toString()
                            , jobj.getString(titleA).toString()
                            , jobj.getString(descriptionA).toString()
                            , "http://image.tmdb.org/t/p/w342/" + jobj.getString(imageA).toString()
                            , jobj.getString(dateA).toString()
                            , jobj.getString(rateA).toString()
                            , jobj.getString(votecountA).toString()));
                }
            }
            catch (Exception e) {

                e.getMessage();

            }

            myadap = new myadapter(MainActivity.this,item);
            GridView grid = (GridView) findViewById(R.id.gridView1);
            grid.setAdapter(myadap);

            grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    Intent myintent = new Intent(MainActivity.this, SecondActivity.class);
                    myintent.putExtra("id", item.get(position).id);
                    myintent.putExtra("title", item.get(position).title);
                    myintent.putExtra("description", item.get(position).description);
                    myintent.putExtra("image", item.get(position).image);
                    myintent.putExtra("date", item.get(position).date);
                    myintent.putExtra("rate", item.get(position).rate);
                    myintent.putExtra("vode", item.get(position).vode);
                    startActivity(myintent);


                }
            });



        }





    }


    public class myadapter extends BaseAdapter {
        Context context;
        ArrayList<listitem> items = new ArrayList<listitem>();

    public myadapter(Context context,ArrayList<listitem> item) {

        this.items = item;
        this.context=context;
    }


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position).title;
    }

    @Override
    public long getItemId(int position) {
        return position;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater flate = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = flate.inflate(R.layout.images, null);
        ImageView imagev = (ImageView) v.findViewById(R.id.imageimage);
        TextView rate=(TextView)v.findViewById(R.id.imagerate);
        TextView date=(TextView)v.findViewById(R.id.imagedate);
        TextView vode=(TextView)v.findViewById(R.id.imagevode);


        String url=items.get(position).image;
        Picasso.with(context).load(url).into(imagev);

        date.setText(items.get(position).date);
        rate.setText(items.get(position).rate);
        vode.setText(items.get(position).vode);






        return v;
    }
}



}

