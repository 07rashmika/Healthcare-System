package Controller;

import DBController.DBConnection;
import Model.InventoryModel;
import View.Inventory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.List;

public class InventoryController {

    private final Inventory view;
    private final InventoryModel model;

    // Database connection
    private final Connection con = DBConnection.startConnection();

    // Constructor
    public InventoryController(InventoryModel model, Inventory view) {
        this.model = model;
        this.view = view;

//         Add button listeners
        this.view.getBtnAdd().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAddButton();
            }
        });

        this.view.getBtnUpdate().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleUpdateButton();
            }
        });

        this.view.getBtnDelete().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDeleteButton();
            }
        });
    }

    private void handleAddButton() {
            try {
                // 1. Fetch data from the view
                String itemID = view.getTxtItemID().getText();
                String itemName = view.getTxtItemName().getText();
                int stockQuantity = (int) view.getSpnQuantity().getValue();
                int thresholdQuantity = (int) view.getSpnThreshold().getValue();
                float unitPrice = Float.parseFloat(view.getTxtUnitPrice().getText());
                String category = view.getTxtCategory().getText();

                // data validation
                if (itemID.isEmpty() || itemName.isEmpty() || category.isEmpty()) {
                    JOptionPane.showMessageDialog(view.getMainPanel(),
                            "Please fill in all required fields.",
                            "Validation Error",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (stockQuantity < 0 || thresholdQuantity < 0 || unitPrice < 0) {
                    JOptionPane.showMessageDialog(view.getMainPanel(),
                            "Quantity and price values must be non-negative.",
                            "Validation Error",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                //model method
                boolean success = model.createInventory(
                        Integer.parseInt(itemID),
                        itemName,
                        stockQuantity,
                        thresholdQuantity,
                        unitPrice,
                        category
                );

                // 4. Provide feedback to the user
                if (success) {
                    JOptionPane.showMessageDialog(view.getMainPanel(),
                            "Item added successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(view.getMainPanel(),
                            "Failed to add item. Please try again.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view.getMainPanel(),
                        "Invalid numeric input. Please enter valid numbers.",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view.getMainPanel(),
                        "An unexpected error occurred: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace(); // Log the error for debugging
            }

        System.out.println("Add button clicked");
    }

    private void handleUpdateButton() {
        try {
            // 1. Fetch data from the view
            String itemID = view.getTxtItemID().getText();
            String itemName = view.getTxtItemName().getText();
            int stockQuantity = (int) view.getSpnQuantity().getValue();
            int thresholdQuantity = (int) view.getSpnThreshold().getValue();
            float unitPrice = Float.parseFloat(view.getTxtUnitPrice().getText());
            String category = view.getTxtCategory().getText();

            // 2. Validate the data
            if (itemID.isEmpty() || itemName.isEmpty() || category.isEmpty()) {
                JOptionPane.showMessageDialog(view.getMainPanel(),
                        "Please fill in all required fields.",
                        "Validation Error",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (stockQuantity < 0 || thresholdQuantity < 0 || unitPrice < 0) {
                JOptionPane.showMessageDialog(view.getMainPanel(),
                        "Quantity and price values must be non-negative.",
                        "Validation Error",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 3. Call the model method
            boolean success = model.updateInventory(
                    Integer.parseInt(itemID),
                    itemName,
                    stockQuantity,
                    thresholdQuantity,
                    unitPrice,
                    category
            );

            // 4. Provide feedback to the user
            if (success) {
                JOptionPane.showMessageDialog(view.getMainPanel(),
                        "Item updated successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(view.getMainPanel(),
                        "Failed to update item. Please try again.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view.getMainPanel(),
                    "Invalid numeric input. Please enter valid numbers.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view.getMainPanel(),
                    "An unexpected error occurred: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace(); // Log the error for debugging
        }
    }


    private void handleDeleteButton() {
        try {
            // 1. Fetch the itemID from the view
            String itemID = view.getTxtItemID().getText();

            // 2. Validate the data
            if (itemID.isEmpty()) {
                JOptionPane.showMessageDialog(view.getMainPanel(),
                        "Please enter the Item ID to delete.",
                        "Validation Error",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(view.getMainPanel(),
                    "Are you sure you want to delete this item?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION);

            if (confirm != JOptionPane.YES_OPTION) {
                return; // User canceled the deletion
            }

            // 3. Call the model method
            boolean success = model.removeInventory(Integer.parseInt(itemID));

            // 4. Provide feedback to the user
            if (success) {
                JOptionPane.showMessageDialog(view.getMainPanel(),
                        "Item deleted successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                refreshInventoryTable(); // Refresh the view after deletion
            } else {
                JOptionPane.showMessageDialog(view.getMainPanel(),
                        "Failed to delete item. Please check the Item ID and try again.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view.getMainPanel(),
                    "Invalid Item ID. Please enter a valid numeric value.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view.getMainPanel(),
                    "An unexpected error occurred: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace(); // Log the error for debugging
        }
    }


    // Add a new inventory item
    public boolean addInventoryItem(String itemID, String itemName, int stockQuantity, int thresholdQuantity, double unitPrice, String category) {
        String query = "INSERT INTO inventory (ItemID, ItemName, StockQuantity, ThresholdQuantity, UnitPrice, Category) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, itemID);
            pstmt.setString(2, itemName);
            pstmt.setInt(3, stockQuantity);
            pstmt.setInt(4, thresholdQuantity);
            pstmt.setDouble(5, unitPrice);
            pstmt.setString(6, category);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error adding inventory item: " + e.getMessage());
        }
        return false;
    }

    // update an inventory item
    public boolean updateInventoryItem(String itemID, String itemName, int stockQuantity, int thresholdQuantity, double unitPrice, String category) {
        String updateQuery = "UPDATE FROM inventory (ItemID, ItemName, StockQuantity, ThresholdQuantity, UnitPrice, Category) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = con.prepareStatement(updateQuery)) {
            pstmt.setString(1, itemID);
            pstmt.setString(2, itemName);
            pstmt.setInt(3, stockQuantity);
            pstmt.setInt(4, thresholdQuantity);
            pstmt.setDouble(5, unitPrice);
            pstmt.setString(6, category);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error adding inventory item: " + e.getMessage());
        }
        return false;
    }

    // delete an inventory item
    public boolean deleteInventoryItem(String itemID, String itemName, int stockQuantity, int thresholdQuantity, double unitPrice, String category) {
        String deleteQuery = "INSERT INTO inventory (ItemID, ItemName, StockQuantity, ThresholdQuantity, UnitPrice, Category) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = con.prepareStatement(deleteQuery)) {
            pstmt.setString(1, itemID);
            pstmt.setString(2, itemName);
            pstmt.setInt(3, stockQuantity);
            pstmt.setInt(4, thresholdQuantity);
            pstmt.setDouble(5, unitPrice);
            pstmt.setString(6, category);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error adding inventory item: " + e.getMessage());
        }
        return false;
    }

    private void refreshInventoryTable() {
        List<Object[]> inventoryData = model.getInventoryDetails();
        // Use inventoryData to update the table model
    }


}

