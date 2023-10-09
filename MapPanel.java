import javafx.application.Application;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
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
 * Class to create map panel
 * Creates buttons for each borough that open a table of listings when clicked on
 */
public class MapPanel
{
    // instance variables
    private ArrayList<AirbnbListing> listings;
    private ArrayList<AirbnbListing> filteredListings;
    private int fromPrice, toPrice;
    private CreateTable boroughWindow;
    private ObservableList<tableListing> tableData = FXCollections.observableArrayList();

    /**
     * Constructor for objects of class MapPanel
     */
    public MapPanel(ArrayList<AirbnbListing> listings)
    {
        // initialise instance variables
        this.listings = listings;
        filteredListings = new ArrayList<AirbnbListing>();
    }

    /**
     * Create the pane for the map screen
     * @return Pane object to be used as the centre panel in the root borderpane
     */
    public Pane createPanel()
    {
        GridPane mainPane = new GridPane();
        mainPane.setId("mapPane");

        //Hashmap for holding the boroughs and their positions in the grid
        Map<String, String> boroughs = new HashMap<String, String>();
        //Holds all the buttons on the map pane
        ArrayList<Button> buttons = new ArrayList<Button>();

        //Adds the different boroughs to the hashmap - (column, row)
        boroughs.put("(4,0)", "ENFI");

        boroughs.put("(2,1)", "BARN");
        boroughs.put("(3,1)", "HRGY");
        boroughs.put("(4,1)", "WALT");

        boroughs.put("(0,2)", "HRRW");
        boroughs.put("(1,2)", "BREN");
        boroughs.put("(2,2)", "CAMD");
        boroughs.put("(3,2)", "ISLI");
        boroughs.put("(4,2)", "HACK"); 
        boroughs.put("(5,2)", "REDB");
        boroughs.put("(6,2)", "HAVE");

        boroughs.put("(0,3)", "HILL");
        boroughs.put("(1,3)", "EALI");
        boroughs.put("(2,3)", "KENS");
        boroughs.put("(3,3)", "WSTM");
        boroughs.put("(4,3)", "TOWH");
        boroughs.put("(5,3)", "NEWH");
        boroughs.put("(6,3)", "BARK");

        boroughs.put("(1,4)", "HOUN");
        boroughs.put("(2,4)", "HAMM");
        boroughs.put("(3,4)", "WAND");
        boroughs.put("(4,4)", "CITY");
        boroughs.put("(5,4)", "GWCH");
        boroughs.put("(6,4)", "BEXL");

        boroughs.put("(1,5)", "RICH");
        boroughs.put("(2,5)", "MERT");
        boroughs.put("(3,5)", "LAMB");
        boroughs.put("(4,5)", "STHW");
        boroughs.put("(5,5)", "LEWS");

        boroughs.put("(2,6)", "KING");
        boroughs.put("(3,6)", "SUTT");
        boroughs.put("(4,6)", "CROY");
        boroughs.put("(5,6)", "BROM");

        // Iterates through every cell in the grid
        for (int col = 0; col < 7; col++){
            for (int row = 0; row < 7; row++){
                // Gets the current grid position as a string
                String coordinate = ("(" + col + "," + row + ")");
                // Checks if a borough is located in the grid cell
                if (boroughs.containsKey(coordinate)){
                    // Adds a button to that grid cell
                    Button button = new Button(boroughs.get(coordinate));
                    buttons.add(button);
                    button.setOnAction(this::mapButtonClick);
                    mainPane.add(button, col, row);
                }
            }
        }

        // Iterates through all the buttons on the map
        // Stores the most and least number of properties in any borough
        int mostProperties = 0;
        int leastProperties = 9999999;
        for (Button button: buttons){
            button.setPrefSize(100, 70);

            // Number of properties available in the borough (button name is the borough name)
            int availableProperties = numberOfProperties(button.getText());
            if (availableProperties > mostProperties){
                mostProperties = availableProperties;
            }
            else if (availableProperties < leastProperties){
                leastProperties = availableProperties;
            }

            button.setId("mapButton");
        }
        
        //Assigns buttons colours depending on number of properties in that buttons borough
        //Gets range of number of properties within price range
        float range = mostProperties - leastProperties;
        for (Button button: buttons){
            float availableProperties = numberOfProperties(button.getText());
            // Calculates a value between 0.0 and 1.0 depending on the number of properties this borough has available
            // 0.0 would mean this borough has the least proeprties, 1.0 meaning the most properties
            float colour = ((availableProperties - leastProperties)/range);
            //Assigns a colour to the button based on the value that was calculated (1.0 is green, 0.0 is red, 0.5 is yellow)
            Color c = new Color(Color.HSBtoRGB(colour * 0.4f, 0.9f, 0.9f));
            button.setStyle("-fx-background-color: rgb("+ c.getRed() + "," + c.getGreen() + ",0)");
        }

        return mainPane;
    }

    /**
     * Calculates the number of properties that are available in a borough
     * @param The 4 letters representing a borough name, e.g. "CROY" is Croydon
     * @return The total number of properties
     */
    public int numberOfProperties(String borough){
        int total = 0;
        // Iterates through all listings
        for (AirbnbListing listing: listings){
            if (listing.getNeighbourhood().equals(borough) 
            && listing.getAvailability365() > 0
            && listing.getPrice() >= fromPrice
            && listing.getPrice() <= toPrice){

                total++;
                filteredListings.add(listing);
            }
        }
        return total;
    }

    /**
     * Creates a new window showing properties of the borough that was clicked
     */
    private void mapButtonClick(ActionEvent event){
        Button clickedButton = (Button) event.getTarget();
        String borough = clickedButton.getText();
        for (AirbnbListing listing : listings){
            if (listing.getNeighbourhood().equals(borough)){
                borough = listing.getNeighbourhoodFull();
                generateTable(borough);
                break;
            }
        }   
    }

    /**
     * Creates a table of values for properties within a borough
     * @param full name of the borough the table is being made for
     */
    private void generateTable(String borough){
        boroughWindow = new CreateTable();
        boroughWindow.createWindow();
        boroughWindow.setWindowName(borough);
        for (AirbnbListing listing: filteredListings){
            if (listing.getNeighbourhoodFull().equals(borough)){
                boroughWindow.addRow(listing.getHost_name(),
                    listing.getPrice(),
                    listing.getNumberOfReviews(),
                    listing.getMinimumNights(),
                    listing.getName(),
                    listing.getRoom_type(),
                    listing.getAvailability365(),
                    listing.getLongitude(),
                    listing.getLatitude(),
                    listing.getNeighbourhoodFull(),
                    listing.getId()
                );
            }
        }
        mergeTableData(boroughWindow.getData());
    }
    
    /**
     * @return a list of rows from the table(s) of a borough or boroughs
     */
    public ObservableList<tableListing> getTableData(){
        return tableData;
    }
    
    /**
     * Combines the data of multiple borough tables (if the user opens more than one at a time)
     * @param List of rows from the most recently opened borough table
     */
    public void mergeTableData(ObservableList<tableListing> dataToBeMerged){
        for (tableListing listing: dataToBeMerged){
            tableData.add(listing);
        }
    }
    
    /**
     * Setter method to assign price range
     * @param from - minimum value
     * @param to - maximum value
     */
    public void setPrices(int from, int to){
        fromPrice = from;
        toPrice = to;
    }
}
