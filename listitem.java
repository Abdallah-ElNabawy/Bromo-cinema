package com.example.hp.bromocinema;

/**
 * Created by HP on 11/10/2016.
 */
public class listitem {
    public String id;
    public String title ;
    public String description ;
    public String image;
    public String date;
    public String rate;
    public String vode;


    public listitem(String i ,String tit ,  String des ,String img,  String dat , String rat  ,String vod )
    {
        this.id=i;
        this.title=tit;
        this.description=des;
        this.image=img;
        this.date=dat;
        this.rate=rat;
        this.vode=vod;


    }

}
