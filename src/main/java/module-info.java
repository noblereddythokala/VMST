module org.example.vmst {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jbcrypt;


    opens org.example.vmst to javafx.fxml;
    exports org.example.vmst;
}