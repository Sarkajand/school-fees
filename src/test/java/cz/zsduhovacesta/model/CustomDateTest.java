package cz.zsduhovacesta.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomDateTest {

    @Test
    void fromString() {
        String strDate = "12.05.2019";
        CustomDate customDate = CustomDate.fromString(strDate);
        assert customDate != null;
        assertEquals(1557612000000L, customDate.getTime());
    }

    @Test
    void toStringTest() {
        CustomDate customDate = new CustomDate(804290400000L);
        String strDate = customDate.toString();
        assertEquals("28.06.1995", strDate);
    }

    @Test
    void fromStringToString() {
        String strDateFrom = "01.01.2000";
        CustomDate customDate = CustomDate.fromString(strDateFrom);
        assert customDate != null;
        String strDateTo = customDate.toString();
        assertEquals(strDateFrom, strDateTo);
    }
}