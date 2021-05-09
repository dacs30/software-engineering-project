package edu.wpi.MochaManticores.database;

import edu.wpi.MochaManticores.Exceptions.InvalidElementException;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public abstract class Manager<Value> {
    /*
    function: loadFromCSV()
    loads elements from the specified CSV path
     */
    abstract void loadFromCSV();

    /*
    function: addElement(v)
    adds an element of template value to database
     */
    abstract void addElement(Value v);

    /*
    function: delElement(s)
    deletes element of given ID string
     */
    abstract void delElement(String s) throws SQLException;

    /*
    function: modElement(s,v)
    modifies element of ID s to become element v
     */
    abstract void modElement(String s, Value v) throws SQLException;

    /*
    function: saveElements()
    saves elements to given CSV file
     */
    abstract void saveElements() throws FileNotFoundException, SQLException;

    /*
    function: getElement()
    returns Value object, specified by ID
     */
    abstract Value getElement(String s) throws InvalidElementException;

    /*
    function: getCSV_path()
    getter for CSV_path
    return string
     */
    abstract String getCSV_path();

    /*
    function setCSV_path()
    setter for CSV_path
     */
    abstract void setCSV_path(String s);

    /*
    function: cleanTable()
    saves and empties database table
     */
    abstract void cleanTable() throws SQLException;

    public abstract void updateElementMap() throws SQLException;
}
