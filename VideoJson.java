package com.example.hp.bromocinema;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;




public class VideoJson extends AsyncTask<String, String, String> {
    private static final String idv = "id";
    private static final String keyv = "key";
    private static final String namev = "name";
    private static final String sitev = "site";
    private static final String sizev = "size";
    private static final String typev = "type";

    final public ArrayList<videoitem> item = new ArrayList<videoitem>();
    myadapter2 myadap2;
    private ProgressDialog pdialog;
    Context context;
    HttpURLConnection connect;
    URL url;
    StringBuilder sb;
    String read;
    JSONArray Jarray;
    JSONObject object;
    String id;
    VideoView videoView;
    MediaController mediaController ;


    public VideoJson (Context context , String i) {   this.context=context;      this.id=i;   }

    @Override
    protected String doInBackground(String... params) {

        try {

            url = new URL("http://api.themoviedb.org/3/movie/"+id+"/videos?api_key=4396a759c475ccb9e7e585f444c431d2");
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
        pdialog.setMessage("video Loading ..");
        pdialog.setCancelable(false);
        pdialog.show();

    }



    @Override
    protected void onPostExecute(String r) {

        pdialog.dismiss();
        try {
            object = new JSONObject(read);
            Jarray = object.getJSONArray("results");
            for (int i = 0; i < Jarray.length(); i++) {

                JSONObject jobj = Jarray.getJSONObject(i);
                item.add(new videoitem(

                        jobj.getString(idv).toString()
                        , "https://www.youtube.com/watch?v=" + jobj.getString(keyv).toString()
                        , jobj.getString(namev).toString()
                        , jobj.getString(sitev).toString()
                        , jobj.getString(sizev).toString()
                        , jobj.getString(typev).toString()


                ));
            }
        } catch (Exception e) {

            e.getMessage();

        }



        myadap2 = new myadapter2(context,item);
        LayoutInflater flate = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = flate.inflate(R.layout.film, null);
        ListView listv = (ListView)v.findViewById(R.id.videolist);
        listv.setAdapter(myadap2);


        listv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent myintent = new Intent(Intent.ACTION_VIEW);
                myintent.setData(Uri.parse(item.get(position).urll));
                context.startActivity(myintent);


            }
        });





// setvideo();







    }





      /*  public void setvideo()
        {

            LayoutInflater flate = LayoutInflater.from(context);
            View v = flate.inflate(R.layout.film, null);
            videoView = (VideoView)v.findViewById(R.id.videoView);

            mediaController= new MediaController(context);
            mediaController.setAnchorView(videoView);
            mediaController.setMediaPlayer(videoView);
            videoView.setMediaController(mediaController);

            String s=item.get(1).key;
            Uri url = Uri.parse(s);
            videoView.setVideoURI(url);
            videoView.requestFocus();
            videoView.start();



        }*/



}

