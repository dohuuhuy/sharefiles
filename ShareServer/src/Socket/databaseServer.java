package Socket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

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
			try {
				preparedStatement.close();
				resultSet.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}

	public void SignUp(String userName, String passWord) throws SQLException {
		System.out.println(userName + passWord);
		String sql = "INSERT INTO User(userName,passWord) VALUES(?,?)";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = conection.prepareStatement(sql);
			preparedStatement.setString(1, userName);
			preparedStatement.setString(2, passWord);
			preparedStatement.executeUpdate();

			JOptionPane.showMessageDialog(null, "thành công");
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			try {
				preparedStatement.close();
				resultSet.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}

	public boolean checkLogin(String user, String pass) throws SQLException {

		if (!userExists(user)) {
			return false;
		}

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
			try {
				preparedStatement.close();
				resultSet.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}

}