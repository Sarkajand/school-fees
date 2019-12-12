module schoolFees {

    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;
    requires java.base;
    requires slf4j.api;
    requires log4j;


    opens cz.zsduhovacesta;
    opens cz.zsduhovacesta.controller;
    opens cz.zsduhovacesta.model;
    opens cz.zsduhovacesta.service.csv;
    opens cz.zsduhovacesta.service.database;

}