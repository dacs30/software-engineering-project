package edu.wpi.MochaManticores.Editors;

import edu.wpi.MochaManticores.App;
import edu.wpi.MochaManticores.Nodes.EdgeMapSuper;
import edu.wpi.MochaManticores.Nodes.EdgeSuper;
import edu.wpi.MochaManticores.Nodes.MapSuper;
import edu.wpi.MochaManticores.Nodes.NodeSuper;
import edu.wpi.MochaManticores.database.*;
import javafx.event.ActionEvent;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class mapEdit {

    public mapEdit() {
    }

    public boolean checkInput(List<String> fields) {
        for (String j : fields) {
            if (j.equals("")) {
                return false;
            }
        }
        return true;
    }

    public Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(Mdb.JDBC_URL);
        } catch (SQLException sqlException) {
            System.out.println("Connection failed. Check output console.");
            sqlException.printStackTrace();
            throw new SQLException();
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

    public boolean validNode(String nodeID){
        return MapSuper.getMap().containsKey(nodeID);
    }

    public boolean validEdge(String startNode, String endNode){
        return EdgeMapSuper.getMap().containsKey(startNode+"_"+endNode) || EdgeMapSuper.getMap().containsKey(endNode+"_"+startNode);
    }

    public void downloadCSV(ActionEvent e, String dstPath, String srcPath) {
        String path = getPath();
        if (path.equals("")) {

        } else {
            File dst = new File(path + dstPath);
            try {
                File source = new File(srcPath);
                Files.copy(source.toPath(), dst.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public boolean submitEditNodeToDB(NodeSuper nodeSuper, String selectedID) throws SQLException, FileNotFoundException {
        if (selectedID.equals("")) {
            DatabaseManager.addNode(nodeSuper);
        } else {
            DatabaseManager.modNode(selectedID, nodeSuper);
        }
        return true;

    }

    public boolean submitEditEdgeToDB(EdgeSuper edgeSuper, String selectedID, String oldStart, String oldEnd) throws SQLException, FileNotFoundException {
        if(selectedID.equals("")){
            DatabaseManager.addEdge(edgeSuper);
        }else{
            DatabaseManager.modEdge(selectedID, edgeSuper);
        }
        return true;
    }

    public void deleteNode(String IDtoDel) throws SQLException, FileNotFoundException {
            DatabaseManager.delElement(sel.NODE, IDtoDel);
    }

    public void deleteEdge(String edgeToDel) throws SQLException, FileNotFoundException {
        DatabaseManager.delElement(sel.EDGE, edgeToDel);
    }
}
