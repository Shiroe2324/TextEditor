module shiroe2324 {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    opens shiroe2324 to javafx.fxml;
    exports shiroe2324;
}
