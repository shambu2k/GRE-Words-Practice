package com.example.grewordspractice.utils;

import java.util.Date;

public class DateToMinHours {

    public static long getMinutes(Date latest, Date old){
        long diff = latest.getTime() - old.getTime();
        long diffMinutes = diff / (60 * 1000) % 60;
        return diffMinutes;
    }

    public static long getHour(Date latest, Date old){
        long diff = latest.getTime() - old.getTime();
        long diffHours = diff / (60 * 60 * 1000) % 60;
        return diffHours;
    }

    public static long getDays(Date latest, Date old){
        long diff = latest.getTime() - old.getTime();
        long diffDays = diff / (24 * 60 * 60 * 1000);
        return diffDays;
    }
}
