package Model;

import DBController.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryModel {

    private final Connection con = DBConnection.startConnection();

    // Method to add a new inventory item to the database
    public boolean createInventory(int itemID, String itemName, int stockQuantity, int thresholdQuantity, float unitPrice, String category) {
        String query = "INSERT INTO Inventory (itemID, itemName, stockQuantity, thresholdQuantity, unitPrice, category) VALUES (?, ?, ?, ?, ?, ?)";
        int result = 0;

        try (PreparedStatement prepstmt = con.prepareStatement(query)) {
            prepstmt.setInt(1, itemID);
            prepstmt.setString(2, itemName);
            prepstmt.setInt(3, stockQuantity);
            prepstmt.setInt(4, thresholdQuantity);
            prepstmt.setFloat(5, unitPrice);
            prepstmt.setString(6, category);

            result = prepstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Couldn't insert data: " + e.getMessage());
        }

        return result > 0; // returns true if any rows have been affected
    }

    // Method to retrieve all inventory details
    public List<Object[]> getInventoryDetails() {
        List<Object[]> inventoryDetails = new ArrayList<>();
        String query = "SELECT * FROM Inventory";

        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                inventoryDetails.add(new Object[]{
                        rs.getInt("itemID"),
                        rs.getString("itemName"),
                        rs.getInt("stockQuantity"),
                        rs.getInt("thresholdQuantity"),
                        rs.getFloat("unitPrice"),
                        rs.getString("category")
                });
            }
        } catch (SQLException e) {
            System.out.println("Couldn't get inventory details: " + e.getMessage());
        }

        return inventoryDetails;
    }

    // Method to remove an inventory item
    public boolean removeInventory(int itemID) {
        String query = "DELETE FROM Inventory WHERE itemID = ?";
        int result = 0;

        try (PreparedStatement prepstmt = con.prepareStatement(query)) {
            prepstmt.setInt(1, itemID);
            result = prepstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Couldn't delete row: " + e.getMessage());
        }

        return result > 0;
    }

    // Method to update an inventory item
    public boolean updateInventory(int itemID, String itemName, int stockQuantity, int thresholdQuantity, float unitPrice, String category) {
        String query = "UPDATE Inventory SET itemID = ?, itemName = ?, stockQuantity = ?, thresholdQuantity = ?, unitPrice = ?, category = ? WHERE itemID = ?";
        int result = 0;

        try (PreparedStatement prepstmt = con.prepareStatement(query)) {
            prepstmt.setInt(1, itemID);
            prepstmt.setString(2, itemName);
            prepstmt.setInt(3, stockQuantity);
            prepstmt.setInt(4, thresholdQuantity);
            prepstmt.setFloat(5, unitPrice);
            prepstmt.setString(6, category);
            prepstmt.setInt(7, itemID);  // Update based on itemID

            result = prepstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Couldn't update data: " + e.getMessage());
        }

        return result > 0;
    }
}

