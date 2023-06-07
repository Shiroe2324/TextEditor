package shiroe2324;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class FontChooserDialog extends Dialog<ButtonType> {

    private ChoiceBox<String> fontChoiceBox;
    private ChoiceBox<Integer> sizeChoiceBox;
    private CheckBox boldCheckBox;
    private CheckBox italicCheckBox;
    private TextField previewTextField;

    public FontChooserDialog(String defaultFontFamily, int defaultFontSize) {
        setTitle("Select Font");
        setHeaderText(null);
        setResizable(false);

        // Font family choice box
        fontChoiceBox = new ChoiceBox<>();
        fontChoiceBox.getItems().addAll(Font.getFamilies());
        fontChoiceBox.setValue(defaultFontFamily);

        // Font size choice box
        sizeChoiceBox = new ChoiceBox<>();
        for (int i = 8; i <= 72; i++) {
            sizeChoiceBox.getItems().add(i);
        }
        sizeChoiceBox.setValue((int) defaultFontSize);

        // Bold and italic checkboxes
        boldCheckBox = new CheckBox("Bold");
        italicCheckBox = new CheckBox("Italic");

        // Preview text field
        previewTextField = new TextField("abcdefghijklmnopqrstuvwxyz");
        previewTextField.setEditable(false);
        previewTextField.setFont(getSelectedFont());

        // Event handlers for choice boxes and checkboxes
        fontChoiceBox.setOnAction(e -> updatePreview());
        sizeChoiceBox.setOnAction(e -> updatePreview());
        boldCheckBox.setOnAction(e -> updatePreview());
        italicCheckBox.setOnAction(e -> updatePreview());

        // GridPane to hold the controls
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));
        gridPane.addRow(0, new Label("Font:"), fontChoiceBox);
        gridPane.addRow(1, new Label("Size:"), sizeChoiceBox);
        gridPane.addRow(2, new Label("Style:"), boldCheckBox, italicCheckBox);
        gridPane.setAlignment(Pos.CENTER);

        // ScrollPane to display the preview text
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(previewTextField);
        scrollPane.setPrefViewportHeight(70);
        scrollPane.setFitToWidth(true);

        // BorderPane to hold the GridPane and ScrollPane
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(gridPane);
        borderPane.setCenter(scrollPane);

        // Set styles for the BorderPane
        borderPane.setStyle("-fx-background-color: #f0f0f0;");

        // Set the content and button types of the dialog pane
        getDialogPane().setContent(borderPane);
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Request focus on the OK button when the dialog is shown
        Platform.runLater(() -> getDialogPane().lookupButton(ButtonType.OK).requestFocus());
    }

    // Update the preview text field font based on the selected options
    private void updatePreview() {
        previewTextField.setFont(getSelectedFont());
    }

    // Get the selected font based on the choice box and checkbox values
    public Font getSelectedFont() {
        String fontFamily = fontChoiceBox.getValue();
        int fontSize = sizeChoiceBox.getValue();
        FontWeight fontWeight = boldCheckBox.isSelected() ? FontWeight.BOLD : FontWeight.NORMAL;
        FontPosture fontPosture = italicCheckBox.isSelected() ? FontPosture.ITALIC : FontPosture.REGULAR;
        return Font.font(fontFamily, fontWeight, fontPosture, fontSize);
    }
}
