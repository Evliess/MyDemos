 module com.example.fei_yue {
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.web;

  requires org.controlsfx.controls;
  requires com.dlsc.formsfx;
  requires validatorfx;
  requires org.kordamp.ikonli.javafx;
  requires org.kordamp.bootstrapfx.core;
  requires eu.hansolo.tilesfx;
   requires fastjson;
     requires java.sql;

     opens com.example.fei_yue to javafx.fxml;
  exports com.example.fei_yue;
}