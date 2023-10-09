
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

/**
 * The test class StatsPanelTest.
 */
public class StatsPanelTest
{
    private AirbnbDataLoader airbnbData;
    private ArrayList<AirbnbListing> listings;
    private StatsPanel allListings, emptyListings, standardListings;

    /**
     * Default constructor for test class StatsPanelTest
     */
    public StatsPanelTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
        airbnbData = new AirbnbDataLoader();
        listings = airbnbData.load();
        allListings = new StatsPanel(listings, 0, 7000);
        //Create StatsPanel with price range so that trimmedListings size is 0
        emptyListings = new StatsPanel(listings, 7001, 7002);
        standardListings = new StatsPanel(listings, 1200, 3500);
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
    }

    @Test
    public void testInit()
    {
        assertEquals(0, allListings.getMinimumValue());
        assertEquals(7000, allListings.getMaximumValue());
    }

    @Test
    public void testListingTrimming()
    {
        StatsPanel statsPanel = new StatsPanel(listings, 0, 7000);
        assertTrue(allListings.getTrimmedListings().size() > 0);
    }

    @Test
    public void testEdgecaseListings()
    {
        StatsPanel statsPanel = new StatsPanel(listings, 6900, 7000);
        assertEquals(2, statsPanel.getTrimmedListings().size());
    }

    @Test
    public void testAvailableProperties()
    {
        StatsPanel statsPanel = new StatsPanel(listings, 0, 7000);
        assertEquals(41941, allListings.availableProperties());
        //Checked this value in excel
    }

    @Test
    public void testAvailablePropertiesEmpty()
    {
        assertEquals(0, emptyListings.getTrimmedListings().size());
        assertEquals(0, emptyListings.availableProperties());
    }

    @Test
    public void testAvailablePropertiesNormal()
    {
        assertEquals(25, standardListings.availableProperties());
    }

    @Test
    public void testAverageReviews()
    {
        assertEquals(12, allListings.averageReviews());
    }

    @Test
    public void testAverageReviewsEmpty()
    {
        assertEquals(0, emptyListings.getTrimmedListings().size());
        assertEquals(0, emptyListings.averageReviews());
    }

    @Test
    public void testAverageReviewsNormal()
    {
        assertEquals(1, standardListings.averageReviews());
    }

    @Test
    public void testRoomType()
    {
        assertEquals(27175, allListings.roomType());
    }

    @Test
    public void testRoomTypeEmpty()
    {
        assertEquals(0, emptyListings.getTrimmedListings().size());
        assertEquals(0, emptyListings.roomType());
    }

    @Test
    public void testRoomTypeNormal()
    {
        assertEquals(25, standardListings.roomType());
    }

    @Test
    public void testMostExpensive()
    {
        assertEquals("Greenwich", allListings.mostExpensive());
    }

    @Test
    public void testMostExpensiveEmpty()
    {
        assertEquals(0, emptyListings.getTrimmedListings().size());
        assertEquals("None", emptyListings.mostExpensive());
    }

    @Test
    public void testMostExpensiveNormal()
    {
        assertEquals("Camden", standardListings.mostExpensive());
    }

    @Test
    public void testAverageAvailability()
    {
        assertEquals(155, allListings.averageAvailability());
    }

    @Test
    public void testAverageAvailabiltyEmpty()
    {
        assertEquals(0, emptyListings.getTrimmedListings().size());
        assertEquals(0, emptyListings.averageAvailability());
    }

    @Test
    public void testAverageAvailabilityNormal()
    {
        assertEquals(249, standardListings.averageAvailability());
    }

    @Test
    public void testMostAvailable()
    {
        assertEquals("Barking and Dagenham", allListings.mostAvailableBorough());
    }

    @Test
    public void testMostAvailableEmpty()
    {
        assertEquals(0, emptyListings.getTrimmedListings().size());
        assertEquals("None", emptyListings.mostAvailableBorough());
    }

    @Test
    public void testMostAvailableNormal()
    {
        assertEquals("Camden", standardListings.mostAvailableBorough());
    }

    @Test
    public void testMostReviewedBorough()
    {
        assertEquals("Camden", allListings.mostReviewedBorough());
    }

    @Test
    public void testMostReviewedBoroughEmpty()
    {
        assertEquals(0, emptyListings.getTrimmedListings().size());
        assertEquals("None", emptyListings.mostReviewedBorough());
    }

    @Test
    public void testMostReviewedBoroughNormal()
    {
        assertEquals("Hounslow", standardListings.mostReviewedBorough());
    }

    @Test
    public void testMostReviewedHost()
    {
        assertEquals("Love LONDON", allListings.mostReviewedHost());
    }

    @Test
    public void testMostReviewedHostEmpty()
    {
        assertEquals(0, emptyListings.getTrimmedListings().size());
        assertEquals("None", emptyListings.mostReviewedHost());
    }

    @Test
    public void testMostReviewedHostNormal()
    {
        assertEquals("Liz", standardListings.mostReviewedHost());
    }
}
