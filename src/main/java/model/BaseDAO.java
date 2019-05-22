package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class BaseDAO {
	public static final String URL = "jdbc:postgresql://localhost:5432/worlddb";
	public static final String USERNAME = "postgres";
	public static final String PASSWORD = "Burdeos1";

	public BaseDAO() {
	}

	protected Connection getConnection() throws SQLException, ClassNotFoundException {
		Connection con = null;
		Class.forName("org.postgresql.Driver");
		con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		return con;
	}
}