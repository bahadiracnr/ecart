package com.ecart.auth_service;


import java.text.SimpleDateFormat;
import java.util.Date;

public class utils {

    public static String getCurrentDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return simpleDateFormat.format(date);
    }
}
