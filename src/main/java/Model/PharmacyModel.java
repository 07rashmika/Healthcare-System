package Model;

import DBController.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PharmacyModel {


    public List<Medication> getLowStockMedications() {
        List<Medication> medications = new ArrayList<>();

        String query = "SELECT ItemID, ItemName, StockQuantity, ThresholdQuantity FROM Inventory WHERE StockQuantity < ThresholdQuantity";
        try (Connection conn = DBConnection.startConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                medications.add(new Medication(
                        rs.getInt("ItemID"),
                        rs.getString("ItemName"),
                        rs.getInt("StockQuantity"),
                        rs.getInt("ThresholdQuantity")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return medications;
    }
}
