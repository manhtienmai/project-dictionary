module com.example.dictionary_application {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.net.http;
    requires com.google.gson;
    requires javafx.media;
    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpcore;

    opens application to javafx.fxml;
    exports application;
    exports application.controller;
    opens application.controller to javafx.fxml;
    exports application.util;
    opens application.util to javafx.fxml;
    exports application.service;
    opens application.service to javafx.fxml;
}