package ecommerce;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CartManager {
    public void addToCart(int productId, int quantity) {
        String checkStockQuery = "SELECT stock FROM products WHERE id = ?";
        String updateStockQuery = "UPDATE products SET stock = stock - ? WHERE id = ?";
        String addToCartQuery = "INSERT INTO cart (product_id, quantity) VALUES (?, ?)";

        try (Connection connection = DatabaseConnection.getConnection()) {
            // Check stock availability
            try (PreparedStatement checkStockStmt = connection.prepareStatement(checkStockQuery)) {
                checkStockStmt.setInt(1, productId);
                ResultSet resultSet = checkStockStmt.executeQuery();
                if (resultSet.next()) {
                    int stock = resultSet.getInt("stock");
                    if (stock < quantity) {
                        System.out.println("Insufficient stock!");
                        return;
                    }
                }
            }

            // Add to cart
            try (PreparedStatement addToCartStmt = connection.prepareStatement(addToCartQuery)) {
                addToCartStmt.setInt(1, productId);
                addToCartStmt.setInt(2, quantity);
                addToCartStmt.executeUpdate();
                System.out.println("Product added to cart successfully!");
            }

            // Update product stock
            try (PreparedStatement updateStockStmt = connection.prepareStatement(updateStockQuery)) {
                updateStockStmt.setInt(1, quantity);
                updateStockStmt.setInt(2, productId);
                updateStockStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewCart() {
        String query = "SELECT c.id, p.name, c.quantity, p.price FROM cart c JOIN products p ON c.product_id = p.id";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            System.out.println("Cart Items:");
            while (resultSet.next()) {
                System.out.println("Cart ID: " + resultSet.getInt("id") +
                                   ", Product: " + resultSet.getString("name") +
                                   ", Quantity: " + resultSet.getInt("quantity") +
                                   ", Total Price: " + resultSet.getBigDecimal("price").multiply(
                                           new java.math.BigDecimal(resultSet.getInt("quantity"))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
