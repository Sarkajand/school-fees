package cz.zsduhovacesta.model;

import org.junit.jupiter.api.Test;

import java.util.regex.PatternSyntaxException;

import static org.junit.jupiter.api.Assertions.*;

public class CustomDateTest {

    @Test
    public void testFromString() {
        String strDate = "12.05.2019";
        CustomDate customDate = CustomDate.fromString(strDate);
        assertEquals(1557612000000L, customDate.getTime());
    }

    @Test
    public void testWrongFormatOfString1() {
        try {
            CustomDate customDate = CustomDate.fromString("12575568578");
            fail();
        } catch (PatternSyntaxException ignored) {

        }
    }

    @Test
    public void testWrongFormatOfString2() {
        try {
            CustomDate customDate = CustomDate.fromString("12/08/2008");
            fail();
        } catch (PatternSyntaxException ignored) {

        }
    }

    @Test
    public void testWrongFormatOfString3() {
        try {
            CustomDate customDate = CustomDate.fromString("abc");
            fail();
        } catch (PatternSyntaxException ignored) {

        }
    }

    @Test
    public void testWrongFormatOfString4() {
        try {
            CustomDate customDate = CustomDate.fromString("125.75568.4578");
            fail();
        } catch (PatternSyntaxException ignored) {

        }
    }

    @Test
    public void testToStringTest() {
        CustomDate customDate = new CustomDate(804290400000L);
        String strDate = customDate.toString();
        assertEquals("28.06.1995", strDate);
    }

    @Test
    public void testFromStringToString() {
        String strDate = "01.01.2000";
        CustomDate customDate = CustomDate.fromString(strDate);
        String dateToStr = customDate.toString();
        assertEquals(strDate, dateToStr);
    }
}