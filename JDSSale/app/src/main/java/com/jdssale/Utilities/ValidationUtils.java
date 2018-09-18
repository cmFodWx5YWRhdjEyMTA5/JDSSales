package com.jdssale.Utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Created by dikhong on 06-07-2018.
 */

public class ValidationUtils {
    public  static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static boolean isValidPassword(String password){
        // return Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20})").matcher(password).matches();
        return Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@$!%*?&_₹])[A-Za-z\\d#$@$!%*?&_₹]{8,20}").matcher(password).matches();
//    return password.length() >= 8;
    }


    public static boolean isValidPhoneNumber(String phone){
        return Pattern.compile("^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$").matcher(phone).matches();
    }

    public static boolean isValidDate(String s, String format) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(s);
            if (!s.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        if (date == null) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isValidBirthDate(String s, String format) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(s);
            Calendar dob = Calendar.getInstance();
            dob.setTime(date);
            Calendar date13yearsBefore = Calendar.getInstance();
            date13yearsBefore.add(Calendar.YEAR,-13);
            return !dob.after(date13yearsBefore);

        } catch (ParseException ex) {
            ex.printStackTrace();
            return false;
        }

    }
}
