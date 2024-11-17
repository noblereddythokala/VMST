module org.example.vmst {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.vmst to javafx.fxml;
    exports org.example.vmst;
}