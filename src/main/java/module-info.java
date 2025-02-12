module Tennissembly {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.desktop;

    exports controller;
    exports view;
    exports model;
    opens controller to javafx.fxml;
    opens model to  javafx.fxml;
    opens view to javafx.fxml;
    exports tests;
    opens tests to javafx.fxml;
}