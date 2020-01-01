package cz.zsduhovacesta.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FeesHistoryTest {

    @Test
    public void testCountShouldPay() {
        FeesHistory newFeesHistory = new FeesHistory();
        FeesHistory feesHistory = new FeesHistory();
        feesHistory.setJanuary(10);
        feesHistory.setFebruary(10);
        feesHistory.setMarch(10);
        feesHistory.setApril(10);
        feesHistory.setMay(10);
        feesHistory.setJune(10);
        feesHistory.setJuly(10);
        feesHistory.setAugust(10);
        feesHistory.setSeptember(10);
        feesHistory.setOctober(10);
        feesHistory.setNovember(10);
        feesHistory.setDecember(10);
        assertEquals(0, newFeesHistory.countShouldPay());
        assertEquals(120, feesHistory.countShouldPay());
    }
}