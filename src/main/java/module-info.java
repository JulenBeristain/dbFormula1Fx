module eus.ehu.dbformula1fx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens eus.ehu.dbformula1fx to javafx.fxml;
    exports eus.ehu.dbformula1fx;
}