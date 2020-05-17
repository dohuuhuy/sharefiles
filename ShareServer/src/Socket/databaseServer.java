package Socket;

import java.sql.*;

public class databaseServer {
	Connection conection;

	public databaseServer() {
		conection = SqliteConnection.Connector();
		if (conection == null) {

			System.out.println("connection not successful");
			System.exit(1);
		}
	}

	public boolean isDbConnected() {
		try {
			return !conection.isClosed();
		} catch (SQLException e) {
		
			e.printStackTrace();
			return false;
		}
	}

	public boolean userExists(String user) throws SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "select * from User where userName = ? ";
		try {
			preparedStatement = conection.prepareStatement(query);
			preparedStatement.setString(1, user);
	
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			  System.out.println("Database exception : userExists()");
			return false;
			
		} finally {
			preparedStatement.close();
			resultSet.close();
		}
	}


	public boolean checkLogin(String user, String pass) throws SQLException {
		
		 if(!userExists(user)){ return false; }
		
		System.out.println("helo " +  user + pass);
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "select * from User where userName = ? and passWord = ?";
		try {
			preparedStatement = conection.prepareStatement(query);
			preparedStatement.setString(1, user);
			preparedStatement.setString(2, pass);

			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return true;
			} else {
				System.out.println("Hippie");
				return false;
			}

		} catch (Exception e) {
			   System.out.println("Database exception : userExists()");
			return false;
			// TODO: handle exception
		} finally {
			preparedStatement.close();
			resultSet.close();
		}
	}

	
}