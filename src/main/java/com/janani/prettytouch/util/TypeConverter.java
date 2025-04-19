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
    public static String replaceNull(String a){
        return a==null?"":a;
    }

    public static boolean ValidateInt(String a){
        try{
            Integer.parseInt(a);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public static boolean ValidateDouble(String a){
        try{
            Double.parseDouble(a);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public static boolean ValidateLocalDate(String a){
        try{
            LocalDate.parse(a);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    public static boolean ValidateLocalDateTime(String a){
        try{
            LocalDateTime.parse(a);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    public static boolean stringIsNotEmpty(String a){
        return a!=null && !a.isEmpty();
    }

    public static boolean stringIsEmpty(String a){
        return a==null || a.isEmpty();
    }



}
