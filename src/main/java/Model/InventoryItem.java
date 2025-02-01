package Model;

public class InventoryItem {
    private int itemID;
    private String itemName;
    private int stockQuantity;
    private int thresholdQuantity;
    private double unitPrice;
    private String category; // Add this field

    public InventoryItem(int itemID, String itemName, int stockQuantity, int thresholdQuantity, double unitPrice, String category) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.stockQuantity = stockQuantity;
        this.thresholdQuantity = thresholdQuantity;
        this.unitPrice = unitPrice;
        this.category = category;
    }

    // Getters
    public int getItemID() { return itemID; }
    public String getCategory() { // Add this getter method
        return category;
    }
    public String getItemName() { return itemName; }
    public int getStockQuantity() { return stockQuantity; }
    public int getThresholdQuantity() { return thresholdQuantity; }
    public double getUnitPrice() { return unitPrice; }
}
