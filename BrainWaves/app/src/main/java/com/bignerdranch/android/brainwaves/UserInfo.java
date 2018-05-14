package com.bignerdranch.android.brainwaves;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class UserInfo {

//    public String username;
//    public String email;

    public String NamePlayer;//=mUsername;
    public int AgeOfPlayer,BrainLevelPlayer;//=Integer.parseInt(mAge);
    public String CountryPlayer;//=mCountry;
//    int year;//=1900;
//    int month;//=06;
//    int day;//=2;
    //public GregorianCalendar born= new GregorianCalendar(1980,5,5);

    public UserInfo() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

//    public UserInfo(String username, String email) {
//        this.username = username;
//        this.email = email;
//    }

    public UserInfo(String Name, int age, String country) {
        NamePlayer = Name;
        AgeOfPlayer = age;
        CountryPlayer = country;
        BrainLevelPlayer = 1;
    }

    public String DisplayNamePlayer() {
        return NamePlayer;
    }
    public int DisplayAgePlayer() {
        return AgeOfPlayer;
    }
    public int DisplayBrainLevelPlayer() {
        return BrainLevelPlayer;
    }
    public String DisplayCountryPlayer() {
        return CountryPlayer;
    }
    /*public String getNamePlayer() {
        return NamePlayer;
    }

    public int getAgePlayer() {
        return AgePlayer;
    }

    public String getCountryPlayer() {
        return CountryPlayer;
    }*/

    @Override
    public String toString() {
        return "UserInfo{" +
                "NamePlayer='" + NamePlayer + '\'' +
                ", AgeOfPlayer=" + AgeOfPlayer +
                ", CountryPlayer='" + CountryPlayer + '\'' +
                ", BrainLevelOfPlayer='" + BrainLevelPlayer + '\'' +
                '}';
    }
}
