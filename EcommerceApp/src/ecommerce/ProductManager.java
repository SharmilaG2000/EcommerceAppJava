package ecommerce;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductManager {
	
	public void listProducts() {
		String query = "Select * from products";
		try(Connection connection = DatabaseConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
				ResultSet resultSet = statement.executeQuery()){
			
			
			System.out.println("Products Available:");
			while(resultSet.next()) {
				System.out.println("ID: "+resultSet.getInt("id")+
						", Name: "+resultSet.getString("name")+
						", Price "+resultSet.getBigDecimal("price")+
						", Stock "+resultSet.getInt("stock")
						);
			}
			
		}
		catch(SQLException e){
			e.printStackTrace();
			
		}
	}

}
