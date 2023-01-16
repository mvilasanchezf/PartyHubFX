module com.crittergestionfx.controller.crittergestionfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens com.partyfx.controller to javafx.fxml;
    exports com.partyfx.controller;
    exports com.partyfx.model.objects;
    exports com.partyfx.model.exceptions;
    exports com.partyfx.model.services;
}