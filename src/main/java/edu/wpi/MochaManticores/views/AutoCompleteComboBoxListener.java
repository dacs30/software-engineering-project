        package edu.wpi.MochaManticores.views;

        import com.jfoenix.controls.JFXComboBox;
        import com.jfoenix.controls.JFXTextField;
        import edu.wpi.MochaManticores.database.DatabaseManager;
        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.event.EventHandler;
        import javafx.scene.control.ComboBox;
        import javafx.scene.input.KeyCode;
        import javafx.scene.input.KeyEvent;
        import javafx.util.Pair;

        import java.util.Collection;
        import java.util.LinkedList;
        import java.util.List;

public class AutoCompleteComboBoxListener<T> implements EventHandler<KeyEvent> {

    private JFXComboBox<String> comboBox;
    private StringBuilder sb;
    private ObservableList<T> data;
    private boolean moveCaretToPos = false;
    private int caretPos;

    public AutoCompleteComboBoxListener(final JFXComboBox comboBox) {
        this.comboBox =  comboBox;
        sb = new StringBuilder();
        data = comboBox.getItems();

        this.comboBox.setEditable(true);
       this.comboBox.setOnKeyPressed(new EventHandler<KeyEvent>() {

             @Override
            public void handle(KeyEvent t) {
                 comboBox.setVisible(false);
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
            caretPos = -1;
            moveCaret(comboBox.getEditor().getText().length());
            return;
        } else if (event.getCode() == KeyCode.DOWN) {
            if (!comboBox.isShowing()) {
                comboBox.show();
            }
            caretPos = -1;
            moveCaret(comboBox.getEditor().getText().length());
            return;
        } else if (event.getCode() == KeyCode.BACK_SPACE) {
            moveCaretToPos = true;
            caretPos = comboBox.getEditor().getCaretPosition();
        } else if (event.getCode() == KeyCode.DELETE) {
            moveCaretToPos = true;
            caretPos = comboBox.getEditor().getCaretPosition();
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
        if (!moveCaretToPos) {
            caretPos = -1;
        }
        moveCaret(t.length());
        if (!list.isEmpty()) {
           comboBox.show();
        }
    }

    private void moveCaret(int textLength) {
        if (caretPos == -1) {
            comboBox.getEditor().positionCaret(textLength);
        } else {
            comboBox.getEditor().positionCaret(caretPos);
        }
        moveCaretToPos = false;
    }

    public void setValue(String value) {
        this.comboBox.setValue(value);
    }



}