package com.janani.prettytouch.constVar;

public class FIleConst {
    public static final String FILE_PATH = "E:\\Projects\\Janani\\PrettyTouch\\dataFilesachin hotelsachin\\";
    public static final String USER_FILE = "users.csv";
    public static final String[] USER_HEADERS = {"id","createdBy","createdAt","status","username","password","firstName","lastName","role","phoneNumber","dob"};
    public static final String SERVICE_FILE = "services.csv";
    public static final String[] SERVICE_HEADERS = {"id","createdBy","createdAt","status","type","serviceName","description","printPrice","realPrice","imageUrl"};
    public static final String APPOINTMENT_FILE = "appointments.csv";
    public static final String[] APPOINTMENT_HEADERS = {"id","createdBy","createdAt","status","serviceId","date","timeSlotId","requestData"};
}
