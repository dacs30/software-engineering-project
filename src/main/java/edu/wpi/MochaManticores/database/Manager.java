package edu.wpi.MochaManticores.database;

import edu.wpi.MochaManticores.Exceptions.InvalidElementException;

import java.io.FileNotFoundException;
import java.sql.SQLException;

abstract class Manager<Value> {
    abstract void loadFromCSV();
    abstract void addElement(Value v);
    abstract void delElement(String s) throws SQLException;
    abstract void modElement(String s, Value v) throws SQLException;
    abstract void saveElements() throws FileNotFoundException, SQLException;
    abstract Value getElement(String s) throws InvalidElementException;
    abstract String getCSV_path();
    abstract void setCSV_path(String s);
    abstract void cleanTable() throws SQLException;
}
