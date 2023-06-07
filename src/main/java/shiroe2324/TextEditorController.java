package shiroe2324;

import java.io.*;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Font;
import javafx.stage.*;
import javafx.stage.FileChooser.ExtensionFilter;

public class TextEditorController {
    private File currentFile = null;
    private Stage primaryStage;
    private IntegerProperty caretPosition = new SimpleIntegerProperty();
    private final ExtensionFilter extensionfilter = new ExtensionFilter("Archivos de texto",
            "*.txt", "*.log", "*.csv", "*.xml", "*.json", "*.html", "*.htm", "*.css", "*.js", "*.jsx", "*.php",
            "*.py", "*.java", "*.c", "*.cpp", "*.h", "*.cs", "*.vb", "*.vbs", "*.sql", "*.pl", "*.rb", "*.swift",
            "*.go", "*.kotlin", "*.groovy", "*.ini", "*.cfg", "*.conf", "*.md", "*.rtf", "*.bat", "*.sh", "*.awk",
            "*.yml", "*.toml", "*.properties", "*.tex", "*.lisp", "*.scm", "*.pl/sql", "*.cobol", "*.ada",
            "*.fortran", "*.vhdl", "*.verilog", "*.asm", "*.cmd", "*.tex", "*.awk", "*.dat", "*.rtx", "*.m",
            "*.mm", "*.d", "*.ex", "*.exs", "*.coffee", "*.sass", "*.scss", "*.less", "*.lua", "*.ps1",
            "*.ts", "*.tsx", "*.groovy", "*.r", "*.R", "*.jl", "*.pde", "*.f90", "*.f95", "*.s", "*.hs", "*.lsp",
            "*.fs", "*.erl", "*.clj", "*.cljs", "*.cr", "*.pl6", "*.bak", "*.bakup", "*.backup",
            "*.old", "*.tmp", "*.text", "*.svg", "*.xml", "*.fxml");

    @FXML
    private TextArea textArea;
    @FXML
    private Label lineColumnLabel;
    @FXML
    private CheckMenuItem checkMenuItemLineWrap;

    @FXML
    private void textAreaKeyTyped() {
        updateLineColumnLabel();
    }

    @FXML
    private void textAreaKeyReleased() {
        updateLineColumnLabel();
    }

    // New menu item action
    @FXML
    private void menuItemNewAction() {
        textArea.setText("");
        currentFile = null;
        primaryStage.setTitle("Nuevo Archivo");
    }

    // New Window menu item action
    @FXML
    private void menuItemNewWindowAction() throws IOException {
        new TextEditor().start(new Stage());
    }

    // Open menu item action
    @FXML
    private void menuItemOpenAction() {
        // Create a FileChooser dialog
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Abrir Archivo");
        fileChooser.getExtensionFilters().add(extensionfilter);

        // Show the dialog and wait for the user to select a file
        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        // Check if a file was selected
        if (selectedFile != null) {
            try (BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(selectedFile), "UTF-8"))) {
                String line;
                StringBuilder content = new StringBuilder();

                // Read the file line by line and append the content to a StringBuilder
                while ((line = bufferedReader.readLine()) != null) {
                    content.append(line).append(System.lineSeparator());
                }

                // Set the content of the text area to the file content
                textArea.setText(content.toString());
                currentFile = selectedFile;

                // Set the title of the primary stage to the name of the selected file
                primaryStage.setTitle(selectedFile.getName());
            } catch (IOException e) {
                showErrorAlert(e);
            }
        }
    }

    // Save menu item action
    @FXML
    private void menuItemSaveAction() {
        if (currentFile != null) {
            try (Writer writer = new OutputStreamWriter(new FileOutputStream(currentFile), "UTF-8")) {
                writer.write(textArea.getText());
            } catch (IOException e) {
                showErrorAlert(e);
            }
        } else {
            menuItemSaveAsAction();
        }
    }

    // Save As menu item action
    @FXML
    private void menuItemSaveAsAction() {
        // Create a FileChooser dialog
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Archivo");
        fileChooser.getExtensionFilters().add(extensionfilter);

        // Show the dialog and wait for the user to select a file
        File selectedFile = fileChooser.showSaveDialog(primaryStage);

        // Check if a file was selected
        if (selectedFile != null) {
            try (Writer writer = new OutputStreamWriter(new FileOutputStream(selectedFile), "UTF-8")) {
                writer.write(textArea.getText());

                // Set the current file and update the window title
                currentFile = selectedFile;

                // Set the title of the primary stage to the name of the selected file
                primaryStage.setTitle(selectedFile.getName());
            } catch (IOException e) {
                showErrorAlert(e);
            }
        }
    }

    // Exit menu item action
    @FXML
    private void menuItemExitAction() {
        primaryStage.fireEvent(new WindowEvent(primaryStage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    // Copy menu item action
    @FXML
    private void menuItemCopyAction() {
        textArea.copy();
    }

    // Cut menu item action
    @FXML
    private void menuItemCutAction() {
        textArea.cut();
    }

    // Paste menu item action
    @FXML
    private void menuItemPasteAction() {
        textArea.paste();
    }

    // Undo menu item action
    @FXML
    private void menuItemUndoAction() {
        textArea.undo();
    }

    // Redo menu item action
    @FXML
    private void menuItemRedoAction() {
        textArea.redo();
    }

    // Select All menu item action
    @FXML
    private void menuItemSelectAllAction() {
        textArea.selectAll();
    }

    // Zoom In menu item action
    @FXML
    private void menuItemZoomInAction() {
        Font textFont = textArea.getFont();
        double size = textFont.getSize() + 2;

        if (textFont.getSize() < 72) {
            Font newFont = new Font(textFont.getFamily(), size);
            textArea.setFont(newFont);
        }
    }

    // Zoom Out menu item action
    @FXML
    private void menuItemZoomOutAction() {
        Font textFont = textArea.getFont();
        double size = textFont.getSize() - 2;

        if (textFont.getSize() > 8) {
            Font newFont = new Font(textFont.getFamily(), size);
            textArea.setFont(newFont);
        }
    }

    // Default Zoom menu item action
    @FXML
    private void menuItemZoomDefaultAction() {
        Font textFont = textArea.getFont();
        double size = 14;

        Font newFont = new Font(textFont.getFamily(), size);
        textArea.setFont(newFont);
    }

    // Font menu item action
    @FXML
    private void menuItemFontAction() {
        // Get the current font of the text area and create a FontChooserDialog with the
        // current font family and size
        Font textFont = textArea.getFont();
        FontChooserDialog fontChooser = new FontChooserDialog(textFont.getFamily(), (int) textFont.getSize());

        // Show the dialog and wait for the user to select a font
        fontChooser.showAndWait();

        // Process the font selection, get the selected font from the font chooser
        // dialog and set the selected font as the new font for the text area
        if (fontChooser.getResult() == ButtonType.OK) {
            Font selectedFont = fontChooser.getSelectedFont();
            textArea.setFont(selectedFont);
        }
    }

    // Line Wrap menu item action
    @FXML
    private void checkMenuItemLineWrapAction() {
        textArea.setWrapText(checkMenuItemLineWrap.isSelected());
    }

    @FXML
    private void initialize() {
        // Listener for changes in the text property of the text area
        textArea.textProperty()
                .addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    updateLineColumnLabel();
                });

        // Listener for changes in the caret position property of the text area
        textArea.caretPositionProperty()
                .addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                    caretPosition.set(newValue.intValue());
                });

        // Listener for changes in the caret position property
        caretPosition.addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            updateLineColumnLabel();
        });
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private void showErrorAlert(IOException e) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

    private void updateLineColumnLabel() {
        int caretPos = textArea.getCaretPosition();
        String text = textArea.getText();

        int line = 1;
        int column = 1;

        if (caretPos > 0 && caretPos <= text.length()) {
            for (int i = 0; i < caretPos; i++) {
                if (text.charAt(i) == '\n') {
                    line++;
                    column = 1;
                } else {
                    column++;
                }
            }
        }

        lineColumnLabel.setText("Línea: " + line + ", Columna: " + column);
    }
}
