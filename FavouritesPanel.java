import javafx.application.Application;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.*;
import java.io.File;
import java.util.*;
import javafx.event.*; 
import javafx.geometry.Pos;
import java.awt.Color;

/**
 * Class to create the favourites panel
 * Creates a table that displayes all listings the user has favourited
 */
public class FavouritesPanel
{
    private ObservableList<tableListing> data, favouritedData;
    private StackPane tablePane;
    private CreateTable table;

    /**
     * Constructor for objects of class FavouritesPanel
     */
    public FavouritesPanel()
    {
        data = FXCollections.observableArrayList();
        favouritedData = FXCollections.observableArrayList();
    }

    /**
     * Create the pane for the favourites panel by calling the CreateTable class
     * @return Pane object to be used as the centre panel in the root borderpane
     */
    public Pane createPanel()
    {
        table = new CreateTable();
        tablePane = table.getRoot();

        tablePane.setPrefHeight(1000);
        tablePane.setId("favouritesPane");

        return tablePane;
    }
    
    /**
     * Changes the data within the table to match new data
     * @param ObservableList of tableListings to go into the table
     */
    public void setData(ObservableList<tableListing> data){
        if (data != null){
            this.data = data;
            updateTable();
        }
    }
    
    /**
     * Updates the table by removing any listings that have been unfavourited,
     * and adds all new listings that are not already in the table;
     */
    private void updateTable() {
        ArrayList<tableListing> toRemove = new ArrayList<>();
        for (tableListing listing: favouritedData){
            if (!listing.getFavourite()){
                toRemove.add(listing);
            }
        }
        favouritedData.removeAll(toRemove);
        
        for (tableListing listing: data){
            if (listing.getFavourite() && !(favouritedData.contains(listing))){
                favouritedData.add(listing);
            }
        }
        
        if (!(favouritedData == null)){
            table.updateTable(favouritedData);
        }
    }
}
