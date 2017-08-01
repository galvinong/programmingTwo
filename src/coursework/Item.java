package coursework;

/**
 * Created by Galvin on 4/23/2015.
 */
public class Item {
    //Represent item for sale in auction

    //Short title, full description
    String title, description;

    //Category(pre defined list of your choosing)
//    String[] category = {"Electronics", "Food", "Plants"};
    String category;

    //User ID of vendor
    String vendorID, itemID;

    //Start time, close time for bidding
    long startTime, closeTime;

    //Reserve price for item and list of current bids
    //Unique ID code

    public Item(String title, String description, String category, String vendorID, String itemID, long startTime, long closeTime) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.vendorID = vendorID;
        this.itemID = itemID;
        this.startTime = startTime;
        this.closeTime = closeTime;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getVendorID() {
        return vendorID;
    }

    public String getItemID() {
        return itemID;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getCloseTime() {
        return closeTime;
    }
}
