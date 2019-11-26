module MyProjects {

    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;
    requires java.base;
    requires commons.csv;

    opens sample;
    opens sample.model;
}