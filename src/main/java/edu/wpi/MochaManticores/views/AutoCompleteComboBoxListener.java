        package edu.wpi.MochaManticores.views;

        import com.jfoenix.controls.JFXComboBox;
        import edu.wpi.MochaManticores.database.DatabaseManager;
        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.event.EventHandler;
        import javafx.scene.input.KeyCode;
        import javafx.scene.input.KeyEvent;
        import javafx.util.Pair;

        import java.util.LinkedList;

public class AutoCompleteComboBoxListener<T> implements EventHandler<KeyEvent> {

    private final JFXComboBox<String> comboBox;
    private final StringBuilder stringBuilder;
    private final ObservableList<T> data;
    private boolean moveLetterToPos = false;
    private int letterPosition;

    public AutoCompleteComboBoxListener(final JFXComboBox comboBox) {
        this.comboBox =  comboBox;
        stringBuilder = new StringBuilder();
        data = comboBox.getItems();

        this.comboBox.setEditable(true);
       this.comboBox.setOnKeyPressed(new EventHandler<KeyEvent>() {

             @Override
            public void handle(KeyEvent t) {
                 comboBox.hide();
              }
             });
        this.comboBox.setOnKeyReleased(AutoCompleteComboBoxListener.this);
    }

    @Override
    public void handle(KeyEvent event) {

        if (event.getCode() == KeyCode.ESCAPE) {
            if (comboBox.isShowing()) {
                comboBox.hide();
            }
            comboBox.getEditor().deselect();
            comboBox.getEditor().setText("");
            return;
        } else if (event.getCode() == KeyCode.UP) {
            letterPosition = -1;
            moveLetter(comboBox.getEditor().getText().length());
            return;
        } else if (event.getCode() == KeyCode.DOWN) {
            if (!comboBox.isShowing()) {
                comboBox.show();
            }
            letterPosition = -1;
            moveLetter(comboBox.getEditor().getText().length());
            return;
        } else if (event.getCode() == KeyCode.BACK_SPACE) {
            moveLetterToPos = true;
            letterPosition = comboBox.getEditor().getCaretPosition();
        } else if (event.getCode() == KeyCode.DELETE) {
            moveLetterToPos = true;
            letterPosition = comboBox.getEditor().getCaretPosition();
        }

        if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT
                || event.isControlDown() || event.getCode() == KeyCode.HOME
                || event.getCode() == KeyCode.END || event.getCode() == KeyCode.TAB) {
            return;
        }
        DatabaseManager databaseManager = new DatabaseManager();


        ObservableList list = FXCollections.observableArrayList();
        LinkedList<Pair<String, String>> searchResults = DatabaseManager.getElementIDs();
        searchResults.forEach(s -> {
            list.add(s.toString());
        });
        String t = comboBox.getEditor().getText();

        comboBox.setItems(list);
        comboBox.getEditor().setText(t);
        if (!moveLetterToPos) {
            letterPosition = -1;
        }
        moveLetter(t.length());
        if (!list.isEmpty()) {
           comboBox.show();
        }
    }

    private void moveLetter(int textLength) {
        if (letterPosition == -1) {
            comboBox.getEditor().positionCaret(textLength);
        } else {
            comboBox.getEditor().positionCaret(letterPosition);
        }
        moveLetterToPos = false;
    }

    public void setValue(String value) {
        this.comboBox.setValue(value);
    }



}