package cz.zsduhovacesta.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.PatternSyntaxException;

public class CustomDate extends java.util.Date {
    static final Logger logger = LoggerFactory.getLogger(CustomDate.class);

    public CustomDate(long date) {
        super(date);
    }

    @Override
    public String toString() {
        return new SimpleDateFormat("dd.MM.yyyy").format(this);
    }

    static CustomDate fromString(String strDate) {
        if (strDate.matches("\\d{2}\\.\\d{2}\\.\\d{4}")) {
            try {
                Date date = new SimpleDateFormat("dd.MM.yyyy").parse(strDate);
                long longDate = date.getTime();
                return new CustomDate(longDate);
            } catch (ParseException e) {
                logger.error("Parse date from string failed: ", e);
                throw new PatternSyntaxException("This shouldn't happen", "\\d{2}\\.\\d{2}\\.\\d{4}", -1);
            }
        } else {
            throw new PatternSyntaxException("String doesn't match format", "\\d{2}\\.\\d{2}\\.\\d{4}", -1);
        }
    }
}
