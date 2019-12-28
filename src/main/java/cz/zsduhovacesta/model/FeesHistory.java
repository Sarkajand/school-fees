package cz.zsduhovacesta.model;

public class FeesHistory {

    private int studentVs;
    private int january;
    private int february;
    private int march;
    private int april;
    private int may;
    private int june;
    private int july;
    private int august;
    private int september;
    private int october;
    private int november;
    private int december;
    private int lastUpdate;

    public FeesHistory() {
        this.studentVs = 0;
        this.january = 0;
        this.february = 0;
        this.march = 0;
        this.april = 0;
        this.may = 0;
        this.june = 0;
        this.july = 0;
        this.august = 0;
        this.september = 0;
        this.october = 0;
        this.november = 0;
        this.december = 0;
        this.lastUpdate = 0;
    }

    public int getStudentVs() {
        return studentVs;
    }

    public void setStudentVs(int studentVs) {
        this.studentVs = studentVs;
    }

    public int getJanuary() {
        return january;
    }

    public void setJanuary(int january) {
        this.january = january;
    }

    public int getFebruary() {
        return february;
    }

    public void setFebruary(int february) {
        this.february = february;
    }

    public int getMarch() {
        return march;
    }

    public void setMarch(int march) {
        this.march = march;
    }

    public int getApril() {
        return april;
    }

    public void setApril(int april) {
        this.april = april;
    }

    public int getMay() {
        return may;
    }

    public void setMay(int may) {
        this.may = may;
    }

    public int getJune() {
        return june;
    }

    public void setJune(int june) {
        this.june = june;
    }

    public int getJuly() {
        return july;
    }

    public void setJuly(int july) {
        this.july = july;
    }

    public int getAugust() {
        return august;
    }

    public void setAugust(int august) {
        this.august = august;
    }

    public int getSeptember() {
        return september;
    }

    public void setSeptember(int september) {
        this.september = september;
    }

    public int getOctober() {
        return october;
    }

    public void setOctober(int october) {
        this.october = october;
    }

    public int getNovember() {
        return november;
    }

    public void setNovember(int november) {
        this.november = november;
    }

    public int getDecember() {
        return december;
    }

    public void setDecember(int december) {
        this.december = december;
    }

    public int getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(int lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public int countShouldPay () {
        return january + february + march + april + may + june +
                july + august + september + october + november + december;
    }
}
