module MyProjects {

    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;
    requires java.base;

    opens sample;
    opens sample.model;
}