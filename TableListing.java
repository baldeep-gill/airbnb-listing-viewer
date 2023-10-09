import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Helper class for use in creating tables
 */
public class tableListing
    {
        private final SimpleStringProperty hostName;
        private final SimpleIntegerProperty price, reviews, minNights;
        private final String nameString, hostNameString, roomTypeString, neighbourhoodFullString, id;
        private final int priceInt, minNightsInt, availabilityInt;
        private final double longitudeDouble, latitudeDouble;
        private Boolean favourite;

        /**
         * Constructor for objects of class tableRow
         */
        public tableListing(String hostName, int price, int reviews, int minNights, String name, String roomType, int availability, 
        double longitude, double latitude, String neighbourhoodFull, String id)
        {
            this.hostName = new SimpleStringProperty(hostName);
            this.price = new SimpleIntegerProperty(price);
            this.reviews = new SimpleIntegerProperty(reviews);
            this.minNights = new SimpleIntegerProperty(minNights);
            this.id = id;
            this.nameString = name;
            this.hostNameString = hostName;
            this.priceInt = price;
            this.minNightsInt = minNights;
            this.roomTypeString = roomType;
            this.availabilityInt = availability;
            this.longitudeDouble = longitude;
            this.latitudeDouble = latitude;
            this.neighbourhoodFullString = neighbourhoodFull;
            favourite = false;
        }
        
        /**
         * Getter methods
         */
        public String getHostName() {
            return hostName.get();
        }

        public int getPrice() {
            return price.get();
        }

        public int getReviews() {
            return reviews.get();
        }

        public int getMinNights() {
            return minNights.get();
        }

        public String getNameString() {
            return nameString;
        }

        public String getHostNameString() {
            return hostNameString;
        }

        public String getRoomTypeString() {
            return roomTypeString;
        }

        public String getNeighbourhoodFullString() {
            return neighbourhoodFullString;
        }

        public String getId() {
            return id;
        }

        public int getPriceInt() {
            return priceInt;
        }

        public int getMinNightsInt() {
            return minNightsInt;
        }

        public int getAvailabilityInt() {
            return availabilityInt;
        }

        public double getLongitudeDouble() {
            return longitudeDouble;
        }

        public double getLatitudeDouble() {
            return latitudeDouble;
        }
        
        public void toggleFavourite(){
            favourite = !favourite;
        }
        
        public Boolean getFavourite(){
            return favourite;
        }
    }