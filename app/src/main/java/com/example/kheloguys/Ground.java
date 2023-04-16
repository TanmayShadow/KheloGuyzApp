package com.example.kheloguys;

import android.app.GameManager;

public class Ground {
    int Image;
    String Name;
    String Status;
    String Price;
    String Location;
    String Phone;
    String Email;
    String ImageUrl;
    String Games;

    public Ground(int image, String name, String status, String price ,String location,String phone,String email,String imageUrl,String games) {
        Image = image;
        Name = name;
        Status = status;
        Price = price;
        Location = location;
        Phone=phone;
        Email=email;
        ImageUrl=imageUrl;
        Games=games;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public String getLocation() {
        return Location;
    }
    public void setLocation(String loc){Location=loc;}

    public String getGames(){return Games;}
    public String getImageUrl(){return ImageUrl;}

    public String getEmail(){return Email;}
    public String getPhone(){return Phone;}

    public void setName(String name) {
        Name = name;
    }


    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getPrice() {
        return Games;
    }

    public void setPrice(String price) {
        Games = price;
    }
}
