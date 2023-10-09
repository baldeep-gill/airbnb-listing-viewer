import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.Scene;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import java.awt.event.MouseEvent;
import javafx.scene.control.TableRow;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import java.net.URI;
import javafx.scene.text.*;

/**
 * Helper class to create table of values as well as individual property descriptions
 */
public class CreateTable
{   
    private TableView<tableListing> table;
    private ObservableList<tableListing> data = FXCollections.observableArrayList();
    private Stage stage;
    private StackPane root;

    /**
     * Create the table, set the layout and change the scene
     */
    public CreateTable(){
        root = new StackPane();

        table = new TableView<tableListing>();
        table.setId("table");
        table.setEditable(false);

        TableColumn hostName = new TableColumn("Host Name");
        hostName.prefWidthProperty().bind(table.widthProperty().multiply(0.4));
        hostName.setCellValueFactory(
            new PropertyValueFactory<tableListing, String>("hostName")
        );

        TableColumn price = new TableColumn("Price");
        price.prefWidthProperty().bind(table.widthProperty().multiply(0.19));
        price.setCellValueFactory(
            new PropertyValueFactory<tableListing, Integer>("price")
        );

        TableColumn reviews = new TableColumn("Reviews");
        reviews.prefWidthProperty().bind(table.widthProperty().multiply(0.19));
        reviews.setCellValueFactory(
            new PropertyValueFactory<tableListing, Integer>("reviews")
        );

        TableColumn minNights = new TableColumn("Minimum Nights");
        minNights.prefWidthProperty().bind(table.widthProperty().multiply(0.19));
        minNights.setCellValueFactory(
            new PropertyValueFactory<tableListing, Integer>("minNights")
        );

        table.setItems(data);
        table.getColumns().addAll(hostName, price, reviews, minNights);

        //Check if user double clicks on a property (row) and display that property's description
        table.setOnMouseClicked(event -> {
                if( event.getClickCount() == 2 ) {
                    try
                    {
                        showDescription(table.getSelectionModel().getSelectedItem());
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            });

        root.getChildren().add(table);
    }
    
    /**
     * @return returns the StackPane containing the table
     */
    public StackPane getRoot(){
        return root;
    }
    
    /**
     * Creates a new window that contains the table
     */
    public void createWindow(){
        stage = new Stage();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("mystyle.css");
        stage.setScene(scene);
        stage.setHeight(700);
        stage.setWidth(900);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Sets title of window
     * @param name title of window
     */
    public void setWindowName(String name){
        stage.setTitle(name + " Properties");
        stage.show();
    }

    /**
     * Create a row in the table using all the column headings
     */
    public void addRow(String hostName, int price, int reviews, int minNights, String name, String roomType, int availability, 
    double longitude, double latitude, String neighbourhoodFull, String id){
        data.add(new tableListing(hostName, price, reviews, minNights, name, roomType, availability, longitude, latitude, neighbourhoodFull, id));
        table.setItems(data);
    }
    
    /**
     * Adds a single property listing to the data (to be added to table)
     * @param a tableListing object
     */
    public void addListing(tableListing listing){
        data.add(listing);
    }
    
    /**
     * Adds the new data specified to the table
     * @param ObservableList of tableListings
     */
    public void updateTable(ObservableList<tableListing> newData){
        table.setItems(newData);
    }

    /**
     * Method to create a new window and display description of a selected listing
     * @param listing listing to display in window
     */
    private void showDescription(tableListing listing) throws Exception{
        Stage stage1 = new Stage();
        Label id = new Label("Listing ID: " + listing.getId());
        Label name = new Label("Listing Name: " + listing.getNameString());
        Label hostName = new Label("Host Name: " + listing.getHostNameString());
        Label neighbourhoodFull = new Label("Neighbourhood: " + listing.getNeighbourhoodFullString());
        Label roomType = new Label("Room Type: " + listing.getRoomTypeString());
        Label price = new Label("Price: " + listing.getPriceInt());
        Label minNights = new Label("Minimum Nights: " + listing.getMinNightsInt());
        Label availability = new Label("Days Available per Year: " + listing.getAvailabilityInt());
        Hyperlink gMaps = new Hyperlink("Google Maps");

        String latitude = new String("" + listing.getLatitudeDouble());
        String longitude = new String("" + listing.getLongitudeDouble());
        //Create URL of listing's location on gMaps
        URI gMapsURI = new URI("https://www.google.com/maps/place/" + latitude + "," + longitude);
        // Checks for click on the google maps button
        gMaps.setOnAction(e -> openGMaps(gMapsURI));

        Button favourite = new Button();
        if (listing.getFavourite()){
            favourite.setText("Unfavourite");
        }
        
        else{
            favourite.setText("Favourite");
        }
        
        // Checks for click on the favourite button
        favourite.setOnAction(e -> {
                listing.toggleFavourite();
                if (listing.getFavourite()){
                    favourite.setText("Unfavourite");
                }
                else{
                    favourite.setText("Favourite");
                }

            });
        name.setMaxWidth(390);
        name.setWrapText(true);

        VBox root = new VBox();
        root.getChildren().addAll(id, name, hostName, neighbourhoodFull, roomType, price, minNights, availability, gMaps, favourite);

        Scene scene = new Scene(root);
        stage1.setTitle("ID: " + listing.getId() + " Description");
        stage1.setScene(scene);
        stage1.setWidth(400);
        stage1.setHeight(240);
        stage1.setResizable(true);
        stage1.show();
    }

    /**
     * Opens a browser window and shows user location of property on google maps
     */
    private void openGMaps(URI gMapsURI){
        try
        {
            java.awt.Desktop.getDesktop().browse(gMapsURI);
        }
        catch (java.io.IOException ioe)
        {
            ioe.printStackTrace();
        }
    }
    
    /**
     * @return an ObservableList of all rows in the table
     */
    public ObservableList<tableListing> getData(){
        return table.getItems();
    }
}
