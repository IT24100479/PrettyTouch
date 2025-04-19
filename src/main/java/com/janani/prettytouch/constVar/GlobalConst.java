package com.janani.prettytouch.constVar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GlobalConst {
    public static final List<String> TIME_SLOT_LIST = List.of("7AM-11AM","11AM-2PM");
    public static final List<String> APPOINTMENT_STATUS_TYPE = List.of("Complete","Pending","Cancel","Deleted","Overdue");
    public static final int QUEUE_SIZE = 5;
    public static final String USER_TYPE_ADMIN = "admin";
    public static final String USER_TYPE_USER = "user";
}
