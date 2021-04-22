package edu.wpi.MochaManticores.views;
//TODO:comment :(

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.MochaManticores.App;
import edu.wpi.MochaManticores.Editors.mapEdit;
import edu.wpi.MochaManticores.Nodes.EdgeMapSuper;
import edu.wpi.MochaManticores.Nodes.EdgeSuper;
import edu.wpi.MochaManticores.Nodes.MapSuper;
import edu.wpi.MochaManticores.Nodes.NodeSuper;
import edu.wpi.MochaManticores.database.EdgeManager;
import edu.wpi.MochaManticores.database.Mdb;
import edu.wpi.MochaManticores.database.NodeManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Iterator;

public class edgesPage extends SceneController {
    public JFXTextField mapName;
    public JFXButton backButton;
    public TableView<Edge> dispTable;
    public TableColumn<Edge, String> startNode;
    public TableColumn<Edge, String> endNode;
    public TableColumn<Edge, String> nodeID;
    public ObservableList<Edge> listOfEdges = FXCollections.observableArrayList();

    private mapEdit editor = new mapEdit();

    @FXML
    public ImageView backgroundIMG;

    @FXML
    public VBox selectionPage;

    @FXML
    public VBox editPage;

    @FXML
    public StackPane dialogPane;
    public JFXTextField nodeIDField;
    public JFXTextField startNodeField;
    public JFXTextField endNodeField;
    public Edge selectedEdge;

    public class Edge extends RecursiveTreeObject<Edge> {
        public StringProperty startNode;
        public StringProperty endNode;
        public StringProperty nodeID;
        public StringProperty[] fields;

        public String getStartNode() {
            return startNode.get();
        }

        public StringProperty startNodeProperty() {
            return startNode;
        }

        public void setStartNode(String startNode) {
            this.startNode.set(startNode);
        }

        public String getEndNode() {
            return endNode.get();
        }

        public StringProperty endNodeProperty() {
            return endNode;
        }

        public void setEndNode(String endNode) {
            this.endNode.set(endNode);
        }

        public String getNodeID() {
            return nodeID.get();
        }

        public StringProperty nodeIDProperty() {
            return nodeID;
        }

        public void setNodeID(String nodeID) {
            this.nodeID.set(nodeID);
        }

        public StringProperty[] getFields() {
            return fields;
        }

        public void setFields() {
            fields = new StringProperty[]{startNode, endNode, nodeID};
        }

        public Edge(String startNode, String endNode, String nodeID) {
            this.startNode = new SimpleStringProperty(startNode);
            this.endNode = new SimpleStringProperty(endNode);
            this.nodeID = new SimpleStringProperty(nodeID);
            fields = new StringProperty[]{this.startNode, this.endNode, this.nodeID};
        }

        public Edge(StringProperty[] fields) {
            this.startNode = fields[0];
            this.endNode = fields[1];
            this.nodeID = fields[2];
        }

        @Override
        public String toString() {
            String s = "";
            s += ("Start Node: " + getStartNode() + "\n");
            s += ("End Node: " + getEndNode() + "\n");
            s += ("NodeID: " + getNodeID() + "\n");
            return s;
        }

    }

    public void initialize() {
        double height = super.getHeight();
        double width = super.getWidth();
        backgroundIMG.setFitHeight(height);
        backgroundIMG.setFitWidth(width);

        backgroundIMG.fitWidthProperty().bind(App.getPrimaryStage().widthProperty());
        backgroundIMG.fitHeightProperty().bind(App.getPrimaryStage().heightProperty());

        startNode = new TableColumn<>("startNode");
        startNode.setMinWidth(100);
        startNode.setCellValueFactory(
                new PropertyValueFactory<Edge, String>("startNode"));

        endNode = new TableColumn<Edge, String>("endNode");
        endNode.setMinWidth(100);
        endNode.setCellValueFactory(
                new PropertyValueFactory<Edge, String>("endNode"));
        nodeID = new TableColumn<>("nodeID");
        nodeID.setMinWidth(100);
        nodeID.setCellValueFactory(
                new PropertyValueFactory<Edge, String>("nodeID"));

        listOfEdges = buildTable("");
        editPage.setVisible(false);
        selectionPage.setVisible(true);
    }

    public void displayTable() {

    }

    public void addEdge(ActionEvent e) {
        loadEditPage(null);
    }

    public void loadCustomCSV(ActionEvent e) {
        FileChooser f = new FileChooser();
        File file = f.showOpenDialog(App.getPrimaryStage());
        if (file == null) {
            return;
        }
        System.out.println(file.getAbsolutePath());
        try {
            Mdb.databaseChangeCSVs(file.getAbsolutePath(), NodeManager.getNode_csv_path());
            cancel(e);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        buildTable("");
    }

    public void searchPressed(ActionEvent e) {
        String searchTerm = mapName.getText();

        if (mapName.getText().equals("")) {
            buildTable("");
        } else {
            buildTable(searchTerm);
        }
    }

    public void searchTyped(KeyEvent e) {
        String searchTerm = mapName.getText();

        if (mapName.getText().equals("")) {
            buildTable("");
        } else {
            buildTable(searchTerm);
        }
    }

    private ObservableList<Edge> buildTable(String searchTerm) {
        ObservableList<Edge> edges = FXCollections.observableArrayList();
        Iterator<EdgeSuper> mapIter = EdgeMapSuper.getMap().values().iterator();
        for (int i = 0; i < EdgeMapSuper.getMap().size(); i++) {
            EdgeSuper e = mapIter.next();
            Edge nodeToAdd = new Edge(e.getStartingNode(),
                    e.getEndingNode(),
                    e.edgeID);
            for (int j = 0; j < nodeToAdd.getFields().length; j++) {
                if (nodeToAdd.getFields()[j].get().toLowerCase().contains(searchTerm.toLowerCase()) || searchTerm.equals("")) {
                    edges.add(nodeToAdd);
                    break;
                }
            }

        }

        dispTable.setItems(edges);
        dispTable.getColumns().setAll(startNode, endNode, nodeID);
        return edges;
    }

    public void cancel(ActionEvent e) {
        buildTable("");
        mapName.setText("");
    }

    public void submit(ActionEvent e) {
        Edge n = dispTable.getSelectionModel().getSelectedItem();
        if (n == null) {
            loadErrorDialog();
        } else {
            selectedEdge = new Edge(n.getFields()); //TODO: selectedNode should not be a reference to n
            System.out.println("Node Info:\n" + n);
            loadEditPage(n);
        }

    }

    public void downloadCSV(ActionEvent e) {
        String path = getPath();
        if (path.equals("")) {

        } else {
            File dst = new File(path + "\\bwMEdges.csv");
            try {
                File source = new File("data/bwMEdges.csv");
                Files.copy(source.toPath(), dst.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public String getPath() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(App.getPrimaryStage());
        if (selectedDirectory != null) {
            String path = selectedDirectory.getAbsolutePath();
            return selectedDirectory.getAbsolutePath();//TODO: check windows or UNIX and start at ~/Downloads or $USER/downloads
        }
        return "";
    }

    public void loadErrorDialog() {
        dialogPane.toFront();
        dialogPane.setDisable(false);
        JFXDialogLayout message = new JFXDialogLayout();
        message.setHeading(new Text("Oops!"));
        message.setBody(new Text("Please select a table entry before editing."));
        JFXDialog dialog = new JFXDialog(dialogPane, message, JFXDialog.DialogTransition.CENTER);
        JFXButton exit = new JFXButton("DONE");
        exit.setOnAction(event -> {
            dialog.close();
            dialogPane.setDisable(true);
            dialogPane.toBack();
        });
        dialog.setOnDialogClosed(event -> {
            dialogPane.setDisable(true);
            dialogPane.toBack();
        });
        message.setActions(exit);
        dialog.show();
    }

    public void loadEditPage(Edge node) {
        selectionPage.setVisible(false);
        editPage.setVisible(true);

        if (node != null) {
            startNodeField.setText(node.getStartNode());
            endNodeField.setText(node.getEndNode());
            nodeIDField.setText(node.getNodeID());
        }

    }

//    public void submitEdit(ActionEvent e) throws FileNotFoundException, SQLException {
//        if (!checkInput()) {
//            loadEmptyDialog();
//        } else {
//            Connection connection = null;
//            try {
//                connection = getConnection();
//            } catch (SQLException except) {
//                return;
//            }
//
//            Edge n = null;
//
//            if (selectedEdge == null) {
//                if(!EdgeMapSuper.getMap().containsKey(startNodeField.getText()) || !EdgeMapSuper.getMap().containsKey(endNodeField.getText())){
//                    loadNoNodeDialog();
//                    return;
//                }
//                EdgeManager.addEdge(connection, nodeIDField.getText(), startNodeField.getText(), endNodeField.getText());
//                cancelEdit(e);
//                return;
//            }
//            for (Edge edge : listOfEdges) {
//                if (edge.getNodeID().equals(selectedEdge.getNodeID())) {
//                    n = updateEdge(edge);
//                    break;
//                }
//            }
//
//            if(!EdgeMapSuper.getMap().containsKey(startNodeField.getText()) || !EdgeMapSuper.getMap().containsKey(endNodeField.getText())){
//                loadNoNodeDialog();
//                return;
//            }
//
//            EdgeManager.updateEdge(connection, selectedEdge.getNodeID(), selectedEdge.getStartNode(),
//                    n.getStartNode(), selectedEdge.getEndNode(), n.getEndNode());
//
//
//            //TODO:Talk to CSV Manager
//            cancelEdit(e);
//            //NODETYPE IS NOT CHANGED AS WELL AS NEIGHBORS
//        }
//    }

    public void submitEdit(ActionEvent e) throws SQLException, FileNotFoundException {
        EdgeSuper edgeSuper;
        String selectedID;
        if(!checkInput()){
            loadEmptyDialog();
        }else{
            if(editor.validNode(startNodeField.getText()) && editor.validNode(endNodeField.getText())){
                edgeSuper = new EdgeSuper(startNodeField.getText()+"_"+endNodeField.getText(),
                        startNodeField.getText(),
                        endNodeField.getText());
                if(selectedEdge==null){
                    selectedID = "";
                    editor.submitEditEdgeToDB(edgeSuper,selectedID,"","");
                }else{
                    Edge n = null;
                    selectedID = selectedEdge.getNodeID();
                    for (Edge edge : listOfEdges) {
                        if (edge.getNodeID().equals(selectedEdge.getNodeID())) {
                            n = updateEdge(edge);
                            break;
                        }
                    }

                    if (!EdgeMapSuper.getMap().containsKey(startNodeField.getText()) || !EdgeMapSuper.getMap().containsKey(endNodeField.getText())) {
                        loadNoNodeDialog();
                        return;
                    }
                    editor.submitEditEdgeToDB(edgeSuper,selectedID,selectedEdge.getStartNode(),selectedEdge.getEndNode());
                    cancelEdit(e);
                }

            }
        }
    }

    public void delEdge(ActionEvent e) throws SQLException, FileNotFoundException {
        if(checkInput()){
            Connection connection = null;
            try {
                connection = DriverManager.getConnection(Mdb.JDBC_URL);
            } catch (SQLException sqlException) {
                System.out.println("Connection failed. Check output console.");
                sqlException.printStackTrace();
                return;
            }
            EdgeManager.delEdge(connection,selectedEdge.getNodeID());
            cancelEdit(null);
        }
    }

    public Edge updateEdge(Edge n) {
        n.setStartNode(startNodeField.getText());
        n.setEndNode(endNodeField.getText());
        n.setNodeID(nodeIDField.getText());
        n.setFields();
        System.out.println(n);
        System.out.println(selectedEdge);

        System.out.println("printed");
        return n;
    }

    public void cancelEdit(ActionEvent e) {
        editPage.setVisible(false);
        selectionPage.setVisible(true);
        cancel(e);
    }

    public boolean checkInput() {
        return editor.checkInput(Arrays.asList(startNodeField.getText(), endNodeField.getText(), nodeIDField.getText()));
    }

    public void back(ActionEvent e) {
        if (editPage.isVisible()) {
            cancelEdit(e);
        } else {
            super.back();
        }

    }

    public void loadEmptyDialog() {
        super.loadErrorDialog(dialogPane, "Looks like some of the fields are empty.");
    }

    public void loadNoNodeDialog(){
        super.loadErrorDialog(dialogPane, "No node exists with the given nodeID");
    }


}
