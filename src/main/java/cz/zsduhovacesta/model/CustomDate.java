package cz.zsduhovacesta.model;

import java.util.Date;
import java.text.SimpleDateFormat;

public class CustomDate extends java.util.Date {

    public CustomDate(long date) {
        super(date);
    }

    @Override
    public String toString() {
        return new SimpleDateFormat("dd.MM.yyyy").format(this);
    }

    static CustomDate fromString (String strDate) {
        try {
            Date date = new SimpleDateFormat("dd.MM.yyyy").parse(strDate);
            long longDate = date.getTime();
            return new CustomDate(longDate);
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
