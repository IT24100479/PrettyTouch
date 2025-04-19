package com.janani.prettytouch.util;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TypeConverter {
    public static String intToString(int a){
        try{
            return Integer.toString(a);
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }
    public static String doubaleToString(double a){
        try{
            return Double.toString(a);
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }
    public static String localDateToString(LocalDate a){
        try{
            return a.toString();
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }
    public static String localDateTimeToString(LocalDateTime a){
        try{
            return a.toString();
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    public static int stringToInt(String a){
        try{
            return Integer.parseInt(a);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public static double stringToDouble(String a){
        try{
            return Double.parseDouble(a);
        }catch (Exception e){
            e.printStackTrace();
            return 0.0;
        }
    }

    public static LocalDate stringToLocalDate(String a){
        try{
            return LocalDate.parse(a);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public static LocalDateTime stringToLocalDateTime(String a){
        try{
            return LocalDateTime.parse(a);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
