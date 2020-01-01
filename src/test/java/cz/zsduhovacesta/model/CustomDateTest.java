package cz.zsduhovacesta.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CustomDateTest {

    @Test
    public void testFromString() {
        String strDate = "12.05.2019";
        CustomDate customDate = CustomDate.fromString(strDate);
        assert customDate != null;
        assertEquals(1557612000000L, customDate.getTime());
    }

    @Test
    public void testWrongFormatOfStringReturnNull() {
        CustomDate customDate = CustomDate.fromString("12575568578");
        assertNull(customDate);
        customDate = CustomDate.fromString("12/08/2008");
        assertNull(customDate);
        customDate = CustomDate.fromString("125.75568.4578");
        assertNull(customDate);
        customDate = CustomDate.fromString("abc");
        assertNull(customDate);
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
        assert customDate != null;
        String dateToStr = customDate.toString();
        assertEquals(strDate, dateToStr);
    }
}