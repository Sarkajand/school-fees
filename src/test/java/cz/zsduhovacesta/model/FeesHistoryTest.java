package cz.zsduhovacesta.model;

import org.junit.jupiter.api.Test;

class FeesHistoryTest {

    @Test
    void countShouldPay() {
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
        assert newFeesHistory.countShouldPay() == 0;
        assert feesHistory.countShouldPay() == 120;
    }
}