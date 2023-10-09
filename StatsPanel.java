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
import javafx.scene.text.TextAlignment;

/**
 * Class to create stats panel.
 * Calculates statistics based on values inputted by user.
 */
public class StatsPanel
{
    private ArrayList<AirbnbListing> listings;
    private ArrayList<AirbnbListing> trimmedListings = new ArrayList<>();

    private BorderPane box1, box2, box3, box4;

    private String header1, header2, header3, header4, header5, header6, header7, header8;
    private String value1, value2, value3, value4, value5, value6, value7, value8;

    private ArrayList<String> titles = new ArrayList<String>();
    private Map<String, String> stats = new HashMap<String, String>();

    private Label stat1;
    private Label stat2;
    private Label stat3;
    private Label stat4;

    private Label title1;
    private Label title2;
    private Label title3;
    private Label title4;

    private int minPrice, maxPrice;

    /**
     * Constructor for objects of class StatsGUI
     */
    public StatsPanel(ArrayList<AirbnbListing> listings, int from, int to)
    {
        this.listings = listings;
        this.minPrice = from;
        this.maxPrice = to;
        trimListing();
    }

    /**
     * Creates layout of StatsPanel and returns the pane to be used
     * 
     * @return statsPane Pane object made for statspanel
     */
    public Pane createPanel(){
        //Call statistic methods and set labels to appropriate text.
        header1 = "Available Properties";
        value1 = "" + availableProperties();

        header2 = "Average Reviews";
        value2 = "" + averageReviews();

        header3 = "Entire homes";
        value3 = "" + roomType();

        header4 = "Most expensive borough";
        value4 = "" + mostExpensive();

        header5 = "Average availability";
        value5 = "" + averageAvailability();

        header6 = "Most available borough";
        value6 = "" + mostAvailableBorough();

        header7 = "Most reviewed borough";
        value7 = mostReviewedBorough();

        header8 = "Most reviewed host";
        value8 = "" + mostReviewedHost();

        stat1 = new Label(value1);
        stat2 = new Label(value2);
        stat3 = new Label(value3);
        stat4 = new Label(value4);

        title1 = new Label(header1);
        title2 = new Label(header2);
        title3 = new Label(header3);
        title4 = new Label(header4);

        //Map of headers to their respective value labels
        stats.put(header1, value1);
        stats.put(header2, value2);
        stats.put(header3, value3);
        stats.put(header4, value4);
        stats.put(header5, value5);
        stats.put(header6, value6);
        stats.put(header7, value7);
        stats.put(header8, value8);

        //List of headers to be used for getting next statistics
        titles.add(header1);
        titles.add(header2);
        titles.add(header3);
        titles.add(header4);
        titles.add(header5);
        titles.add(header6);
        titles.add(header7);
        titles.add(header8);

        VBox vbox1 = new VBox(title1, stat1);
        VBox vbox2 = new VBox(title2, stat2);
        VBox vbox3 = new VBox(title3, stat3);
        VBox vbox4 = new VBox(title4, stat4);

        stat1.setId("statLabel");
        stat2.setId("statLabel");
        stat3.setId("statLabel");
        stat4.setId("statLabel");

        title1.setId("statTitleLabel");
        title2.setId("statTitleLabel");
        title3.setId("statTitleLabel");
        title4.setId("statTitleLabel");

        //Enable text wrapping and changes alignment
        stat1.setWrapText(true);
        stat2.setWrapText(true);
        stat3.setWrapText(true);
        stat4.setWrapText(true);
        stat1.setTextAlignment(TextAlignment.CENTER);
        stat2.setTextAlignment(TextAlignment.CENTER);
        stat3.setTextAlignment(TextAlignment.CENTER);
        stat4.setTextAlignment(TextAlignment.CENTER);

        Button leftButton1 = new Button("<");
        leftButton1.setOnAction(this::buttonLeft1);

        Button rightButton1 = new Button(">");
        rightButton1.setOnAction(this::buttonRight1);

        Button leftButton2 = new Button("<");
        leftButton2.setOnAction(this::buttonLeft2);

        Button rightButton2 = new Button(">");
        rightButton2.setOnAction(this::buttonRight2);

        Button leftButton3 = new Button("<");
        leftButton3.setOnAction(this::buttonLeft3);

        Button rightButton3 = new Button(">");
        rightButton3.setOnAction(this::buttonRight3);

        Button leftButton4 = new Button("<");
        leftButton4.setOnAction(this::buttonLeft4);

        Button rightButton4 = new Button(">");
        rightButton4.setOnAction(this::buttonRight4);

        //setOnAction(e -> buttonRight(box1));

        leftButton1.setId("statButton");
        leftButton2.setId("statButton");
        leftButton3.setId("statButton");
        leftButton4.setId("statButton");
        rightButton1.setId("statButton");
        rightButton2.setId("statButton");
        rightButton3.setId("statButton");
        rightButton4.setId("statButton");

        GridPane statsPane = new GridPane();
        statsPane.setId("statsPane");

        //Columns to grow to max width
        ColumnConstraints leftCol = new ColumnConstraints();
        leftCol.setHgrow(Priority.ALWAYS);
        ColumnConstraints rightCol = new ColumnConstraints();
        rightCol.setHgrow(Priority.ALWAYS);
        statsPane.getColumnConstraints().addAll(leftCol, rightCol);

        int var = 424;
        int min = var;

        box1 = new BorderPane(vbox1, null, rightButton1, null, leftButton1);
        box1.setMinWidth(min);
        box1.setMaxWidth(var);
        box2 = new BorderPane(vbox2, null, rightButton2, null, leftButton2);
        box2.setMinWidth(min);
        box2.setMaxWidth(var);
        box3 = new BorderPane(vbox3, null, rightButton3, null, leftButton3);
        box3.setMinWidth(min);
        box3.setMaxWidth(var);
        box4 = new BorderPane(vbox4, null, rightButton4, null, leftButton4);
        box4.setMinWidth(min);
        box4.setMaxWidth(var);

        box1.setId("panes");
        box2.setId("panes");
        box3.setId("panes");
        box4.setId("panes");

        vbox1.setAlignment(Pos.TOP_CENTER);
        vbox2.setAlignment(Pos.TOP_CENTER);
        vbox3.setAlignment(Pos.TOP_CENTER);
        vbox4.setAlignment(Pos.TOP_CENTER);

        statsPane.add(box1,0,0);
        statsPane.add(box2,1,0);
        statsPane.add(box3,0,1);
        statsPane.add(box4,1,1);

        return statsPane;
    }    

    /**
     * Create a new list only containing properties that are within the selected price range
     */
    private void trimListing()
    {
        for(AirbnbListing listing: listings)
        {
            if((listing.getPrice() <= maxPrice) && (listing.getPrice() > minPrice))
            {
                trimmedListings.add(listing);
            }
        }
    }

    ///////////////// Methods to calculate statistics //////////////////

    /**
     * @return the number of available properties
     */
    protected int availableProperties()
    {
        int total = 0;
        for(AirbnbListing listing: trimmedListings)
        {
            if(listing.getAvailability365() > 0)
            {
                total++;
            }
        }
        return total;
    }

    /**
     * @return the average number of reviews
     */
    protected int averageReviews()
    {
        int runningTotal = 0;
        for(AirbnbListing listing: trimmedListings)
        {
            runningTotal += listing.getNumberOfReviews();
        }
        if(trimmedListings.size() == 0){
            return runningTotal;
        }
        return runningTotal / trimmedListings.size();
    }

    /**
     * @return the number of properties that are entire homes or apartments only
     */
    protected int roomType()
    {
        int runningTotal = 0;
        for(AirbnbListing listing: trimmedListings)
        {
            if(listing.getRoom_type().equals("Entire home/apt"))
                runningTotal++;
        }
        return runningTotal;
    }

    /**
     * @return the most expensive borough from the properties within the given price range
     */
    protected String mostExpensive()
    {
        //Hashmap of totals with boroughs as key
        Map<String, Integer> boroughMap = new HashMap<>();
        for(AirbnbListing listing: trimmedListings)
        {
            Integer neighbourhoodTotal = boroughMap.get(listing.getNeighbourhoodFull());
            boroughMap.put(listing.getNeighbourhoodFull(), listing.getPrice() * listing.getMinimumNights());
        }

        //Reassign totals into averages
        for(Map.Entry<String, Integer> entry: boroughMap.entrySet())
        {
            String borough = entry.getKey();
            boroughMap.put(borough, entry.getValue() / returnNumberInBorough(borough));
        }

        //Find the key for the largest value in the set
        String key = "";
        if(!(trimmedListings.size() == 0)){
            int maxVal = Collections.max(boroughMap.values());
            for(Map.Entry<String, Integer> entry: boroughMap.entrySet())
            {
                if(entry.getValue() == maxVal)
                {
                    key = entry.getKey();
                }
            }
            return key;
        }
        return "None";
    }

    /**
     * @return the average availability of all properties
     */
    protected int averageAvailability()
    {
        int runningTotal = 0;
        for(AirbnbListing listing: trimmedListings)
        {
            runningTotal += listing.getAvailability365();
        }
        if(trimmedListings.size() == 0){
            return runningTotal;
        }
        return runningTotal / trimmedListings.size();
    }

    /**
     * @return name of borough with the highest average of availability
     */
    protected String mostAvailableBorough()
    {
        Map<String, Integer> boroughMap = new HashMap<>();
        for(AirbnbListing listing: trimmedListings)
        {
            Integer neighbourhoodTotal = boroughMap.get(listing.getNeighbourhoodFull());
            boroughMap.put(listing.getNeighbourhoodFull(), listing.getAvailability365());
        }

        for(Map.Entry<String, Integer> entry: boroughMap.entrySet())
        {
            String borough = entry.getKey();
            boroughMap.put(borough, entry.getValue() / returnNumberInBorough(borough));
        }

        String key = "";
        if(!(trimmedListings.size() == 0)){
            int maxVal = Collections.max(boroughMap.values());
            for(Map.Entry<String, Integer> entry: boroughMap.entrySet())
            {
                if(entry.getValue() == maxVal)
                {
                    key = entry.getKey();
                }
            }
            return key;
        }
        return "None";
    }

    /**
     * @return name of borough with most reviews
     */
    protected String mostReviewedBorough()
    {
        Map<String, Integer> mostReviews = new HashMap<>();
        for(AirbnbListing listing: trimmedListings)
        {
            Integer neighbourhoodTotal = mostReviews.get(listing.getNeighbourhoodFull());
            mostReviews.put(listing.getNeighbourhoodFull(), listing.getNumberOfReviews());
        }

        String key = "";
        if(!(trimmedListings.size() == 0)){
            int maxVal = Collections.max(mostReviews.values());
            for(AirbnbListing listing: trimmedListings)
            {
                if(listing.getNumberOfReviews() == maxVal)
                {
                    key = listing.getNeighbourhoodFull();
                }
            }
            return key;
        }
        return "None";
    }

    /**
     * @return the name of the host with the largest number of properties listed
     */
    protected String hostMostProperties()
    {
        List<Integer> mostProperties = new ArrayList<>();
        for(AirbnbListing listing: trimmedListings)
        {
            mostProperties.add(listing.getCalculatedHostListingsCount());
        }

        String key = "";
        if(!(trimmedListings.size() == 0)){
            int maxVal = Collections.max(mostProperties);
            for(AirbnbListing listing: trimmedListings)
            {
                if(listing.getCalculatedHostListingsCount() == maxVal)
                {
                    key = listing.getHost_name();
                }
            }
            return key + " with " + maxVal + " properties.";
        }
        return "None";
    }

    /**
     * @return the name of the host with the most amount of reviews
     */
    protected String mostReviewedHost()
    {
        Map<String, Integer> mostReviews = new HashMap<>();
        for(AirbnbListing listing: trimmedListings)
        {
            Integer neighbourhoodTotal = mostReviews.get(listing.getHost_name());
            mostReviews.put(listing.getHost_name(), listing.getNumberOfReviews());
        }

        String key = "";
        String hostId = "";
        if(!(trimmedListings.size() == 0)){
            int maxVal = Collections.max(mostReviews.values());
            for(AirbnbListing listing: trimmedListings)
            {
                if(listing.getNumberOfReviews() == maxVal)
                {
                    key = listing.getHost_name();
                    hostId = listing.getHost_id();
                }
            }
            return key;
        }
        return "None";
    }

    /**
     * Helper method
     * 
     * @return number of properties in a certain borough
     */
    protected int returnNumberInBorough(String borough)
    {
        int total = 0;
        for(AirbnbListing listing: trimmedListings)
        {
            if(listing.getNeighbourhoodFull().equals(borough))
            {
                total++;
            }
        }
        return total;
    }

    //////////////////////////////////////////////////////////////

    /**
     * Event handlers for buttons on stats boxes
     */
    private void buttonLeft1(ActionEvent event)
    {
        title1.setText(checkHeaderRight(0, false));
        stat1.setText(stats.get(title1.getText()));
    }

    private void buttonRight1(ActionEvent event)
    {
        title1.setText(checkHeaderRight(0, true));
        stat1.setText(stats.get(title1.getText()));
    }

    private void buttonLeft2(ActionEvent event)
    {
        title2.setText(checkHeaderRight(1, false));
        stat2.setText(stats.get(title2.getText()));
    }

    private void buttonRight2(ActionEvent event)
    {
        title2.setText(checkHeaderRight(1, true));
        stat2.setText(stats.get(title2.getText()));
    }

    private void buttonLeft3(ActionEvent event)
    {
        title3.setText(checkHeaderRight(2, false));
        stat3.setText(stats.get(title3.getText()));
    }

    private void buttonRight3(ActionEvent event)
    {
        title3.setText(checkHeaderRight(2, true));
        stat3.setText(stats.get(title3.getText()));    
    }

    private void buttonLeft4(ActionEvent event)
    {
        title4.setText(checkHeaderRight(3, false));
        stat4.setText(stats.get(title4.getText()));
    }

    private void buttonRight4(ActionEvent event)
    {
        title4.setText(checkHeaderRight(3, true));
        stat4.setText(stats.get(title4.getText()));
    }

    /**
     * Method to check for next statistic to display when button is clicked.
     * 
     * @param buttonIndex integer value representing the pane the button belongs to.
     * @param directionRight determines right or left movement through list. True for right, False for left.
     */
    private String checkHeaderRight(int buttonIndex, boolean directionRight)
    {
        //Arraylist of statistic titles that are currently in use
        ArrayList<String> usedTitles = new ArrayList<>();

        stat1.setText(stats.get(title1.getText()));
        usedTitles.add(title1.getText());
        usedTitles.add(title2.getText());
        usedTitles.add(title3.getText());
        usedTitles.add(title4.getText());

        //go right
        if (directionRight){ 
            //Find index of currently displayed title in main title list 'titles' and start searching from there
            for (int index = titles.indexOf(usedTitles.get(buttonIndex)); index < titles.size(); index++){
                if (index == titles.size()-1){
                    index = -1;
                }
                //Returns the first title that is not in use.
                if (!usedTitles.contains(titles.get(index+1))){
                    return titles.get(index+1);
                }
            }
        }
        //go left
        else{
            for (int index = titles.indexOf(usedTitles.get(buttonIndex)); index >= 0; index--){
                if (index == 0){
                    index = titles.size();
                }
                if (!usedTitles.contains(titles.get(index-1))){
                    return titles.get(index-1);
                }
            }
        }
        return null;
    }

    /**
     * Methods to assist with testing
     */
    protected int getMinimumValue()
    {
        return minPrice;
    }

    protected int getMaximumValue()
    {
        return maxPrice;
    }

    protected ArrayList<AirbnbListing> getTrimmedListings()
    {
        return trimmedListings;
    }
}