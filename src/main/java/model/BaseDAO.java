package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class BaseDAO {
	public final String url = "jdbc:postgresql://localhost:5432/worlddb";
	public final String username = "postgres";
	public final String password = "Burdeos1";

	public BaseDAO() {
	}

	protected Connection getConnection() throws SQLException, ClassNotFoundException {
		Connection con = null;
		Class.forName("org.postgresql.Driver");
		con = DriverManager.getConnection(url, username, password);
		return con;
	}
}