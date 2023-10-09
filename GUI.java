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

/**
 * Main class that creates and displays the different statistic panels for the program.
 */
public class GUI extends Application
{
    private Stage stage;
    private Button leftButton, rightButton, updateButton;
    private BorderPane root, navigationBar;
    private ArrayList<Pane> paneList = new ArrayList<>();
    private int currentPanel;
    
    private ComboBox fromBox, toBox;
    private Label centreLabel = new Label("");

    //Loads the listings from the csv file
    private AirbnbDataLoader airbnbData = new AirbnbDataLoader();
    //ArrayList to hold the loaded listings
    private ArrayList<AirbnbListing> listings = airbnbData.load();
    //initialises panel classes
    private StatsPanel stats;
    private MapPanel map;
    private FavouritesPanel favourite;
        
    /**
     * The start method is the main entry point for every JavaFX application. 
     * It is called after the init() method has returned and after 
     * the system is ready for the application to begin running.
     * 
     * Create a root borderPane with navigation bar set to bottom.
     * Changing the current window display can be done by setting a new pane to the centre of borderPane root
     *
     * @param  stage the primary stage for this application.
     */
    @Override
    public void start(Stage stage)
    {
        this.currentPanel = 0;
        this.stage = stage;
        createWelcomePane();
        favourite = new FavouritesPanel();

        //Create panel navigation bar
        navigationBar = new BorderPane();
        navigationBar.setMinHeight(AnchorPane.USE_COMPUTED_SIZE);
        
        leftButton = new Button("<--");
        leftButton.setMinHeight(Label.USE_COMPUTED_SIZE);
        leftButton.setPrefWidth(100);
        leftButton.setOnAction(this::buttonLeft);
        
        
        rightButton = new Button("-->");
        rightButton.setMinHeight(Label.USE_COMPUTED_SIZE);
        rightButton.setPrefWidth(100);
        rightButton.setOnAction(this::buttonRight);
        
        updateButton = new Button("Update");
        updateButton.setPrefWidth(100);
        updateButton.setId("updateButton");
        updateButton.setVisible(false);
        updateButton.setOnAction(this::updateData);
                
        navigationBar.setLeft(leftButton);
        navigationBar.setRight(rightButton);
        navigationBar.setCenter(updateButton);
        navigationBar.setId("navigation");
        
        //Create scene root
        root = new BorderPane();
        root.setCenter(paneList.get(0));
        root.setBottom(navigationBar);
        
        // JavaFX must have a Scene (window content) inside a Stage (window)
        Scene scene = new Scene(root);
        scene.getStylesheets().add("mystyle.css");
        stage.setTitle("AirBnB Property Viewer");
        stage.setScene(scene);
        stage.setHeight(700);
        stage.setWidth(900);
        stage.setResizable(false);
        // Show the Stage (window)
        stage.show();
        
        disableButtons(true);
    }
    
    /**
     * Event handler for clicking the right navigational button
     */
    private void buttonRight(ActionEvent event)
    {
        int nextPanel = currentPanel + 1;
        changePanel(nextPanel);
    }
    
    /**
     * Event handler for clicking the left navigational button
     */
    private void buttonLeft(ActionEvent event)
    {
        int nextPanel = currentPanel - 1;
        changePanel(nextPanel);
    }
    
    
    /**
     * Event handler for clicking the Update button on the favourites panel
     * Gives the favourites panel the data of favourited listings
     */
    private void updateData(ActionEvent event){
        favourite.setData(map.getTableData());
    }
    
    /**
     * Set the state of the navigation buttons
     * 
     * @param state true for disabled, false for enabled
     */
    private void disableButtons(Boolean state)
    {
        leftButton.setDisable(state);
        rightButton.setDisable(state);
    }
    
    /**
     * Change the currently displayed pane to the next pane.
     * Loops around if end of pane list is reached
     * 
     * @param nextPanel index of the next pane in paneList to be displayed
     */
    private void changePanel(int nextPanel)
    {
        if(nextPanel > (paneList.size() - 1))
        {
            currentPanel = 0; //Loop back to the first panel
        }
        else if(nextPanel < 0)
        {
            currentPanel = paneList.size() - 1; //Loop to the last panel
        }
        else
        {
            currentPanel = nextPanel;
        }
        
        if (currentPanel == 3){
            updateButton.setVisible(true);
        }
        else{
            updateButton.setVisible(false);
        }
        //Set new pane
        Pane newPane = paneList.get(currentPanel);
        root.getChildren().clear();
        root.setCenter(newPane);
        root.setBottom(navigationBar);
        stage.show();
    }
    
    /**
     * Method to create the welcome pane that is seen on init
     */
    private void createWelcomePane()
    {
        //Create the price selection bar
        Pane topBar = new HBox();
        topBar.setMinHeight(HBox.USE_PREF_SIZE);
        topBar.setId("topbar");
        
        Label fromLabel = new Label("From: ");
        fromLabel.setMinWidth(Label.USE_PREF_SIZE);
        
        Label toLabel = new Label("To: ");
        toLabel.setMinWidth(Label.USE_PREF_SIZE);
        
        //Create list compatible with combobox and use generated list as available options
        ObservableList<String> fromOptions = FXCollections.observableArrayList();
        fromOptions.setAll(generateDropdownList(7000, 100));
        fromBox = new ComboBox(fromOptions);
        fromBox.setMinWidth(ComboBox.USE_PREF_SIZE);
        
        ObservableList<String> toOption = FXCollections.observableArrayList();
        toOption.setAll(generateDropdownList(7000, 100));
        toBox = new ComboBox(toOption);
        toBox.setMinWidth(ComboBox.USE_PREF_SIZE);
        
        fromBox.setOnAction(this::checkValues);
        toBox.setOnAction(this::checkValues);
        
        //Keeps all elements aligned to the right
        Pane filler = new Pane();
        HBox.setHgrow(filler, Priority.ALWAYS);
        
        topBar.getChildren().addAll(filler, fromLabel, fromBox, toLabel, toBox);
        
        
        Image image = new Image("welcomePic.png");
        centreLabel.setGraphic(new ImageView(image));
        
        Pane main = new BorderPane(centreLabel, topBar, null, null, null);
        main.setMinWidth(BorderPane.USE_PREF_SIZE);
        
        paneList.add(0, main); //Index 0
    }
    

    /**
     * Method to create additional panels and add them to paneList
     */
    private void createOtherPanes(int from, int to)
    {
        if (map != null){
            favourite.setData(map.getTableData());
        }
        stats = new StatsPanel(listings, from, to);
        map = new MapPanel(listings);
        StackPane blankPane = new StackPane();
        
        map.setPrices(from, to);
        if (paneList.size() == 4) {
            paneList.set(1, map.createPanel()); //1
            paneList.set(2, stats.createPanel()); //2
        }
        else{
            paneList.add(1, map.createPanel()); //1
            paneList.add(2, stats.createPanel()); //2
            paneList.add(3, favourite.createPanel()); //3
        }
    }
    
    /**
     * Generates a list of every nth number from 0 to maxValue to be used as options for the comboboxes
     * Allows for range of options to be changed
     * @param maxValue final value of list
     * @param interval spacing between each value in list
     * @return numbers arraylist of string to be converted and used for combo boxes
     */
    private ArrayList<String> generateDropdownList(int maxValue, int interval)
    {
        ArrayList<String> numbers = new ArrayList<>();
        for(int i = 0; i <= maxValue; i++)
        {
            if(i % interval == 0)
            {
                numbers.add("" + i);
            }
        }
        return numbers;
    }
    
    /**
     * Checks if values selected by user in comboboxes are valid
     */
    private void checkValues(Event event)
    {
        if(fromBox.getValue() != null && toBox.getValue() != null)
        {
            int from = Integer.parseInt((String) fromBox.getValue());
            int to = Integer.parseInt((String) toBox.getValue());
            if(from >= to)
            {
                disableButtons(true);
                valueAlert();
            }
            else
            {
                // enable buttons and do stats processing for values within that range
                // during the pane creation
                createOtherPanes(from, to);
                disableButtons(false);
            }
        }
    }
    
    /**
     * Shows alert dialogue for when user inputs values incorrectly
     */
    private void valueAlert()
    {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Invalid price range");
        alert.setHeaderText(null);
        alert.setContentText("Invalid price range selected.\nPlease ensure that the 'To' value is greater than the 'From' value");
        
        alert.showAndWait();
    }
}
