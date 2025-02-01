package Controller;

import DBController.DBConnection;
import Model.InventoryItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryReportController {
    public List<String> getCategories() {
        List<String> categories = new ArrayList<>();
        String query = "SELECT DISTINCT Category FROM Inventory";

        try (Connection conn = DBConnection.startConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                categories.add(rs.getString("Category"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }

    public List<InventoryItem> getReportByCategoryAndDate(String category, String month, String year) {
        List<InventoryItem> report = new ArrayList<>();
        String query = "SELECT ItemID, StockQuantity, Category FROM Inventory " +
                "WHERE Category = ? AND MONTH(itemAddedDate) = ? AND YEAR(itemAddedDate) = ?";

        try (Connection conn = DBConnection.startConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, category);
            stmt.setInt(2, getMonthNumber(month));  // Convert month name to number
            stmt.setString(3, year);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int itemID = rs.getInt("ItemID");
                    int stockQuantity = rs.getInt("StockQuantity");
                    String itemCategory = rs.getString("Category");

                    report.add(new InventoryItem(itemID, "",stockQuantity, 0, 0, itemCategory));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return report;
    }

    private int getMonthNumber(String month) {
        switch (month) {
            case "January": return 1;
            case "February": return 2;
            case "March": return 3;
            case "April": return 4;
            case "May": return 5;
            case "June": return 6;
            case "July": return 7;
            case "August": return 8;
            case "September": return 9;
            case "October": return 10;
            case "November": return 11;
            case "December": return 12;
            default: return 0;
        }
    }
}
