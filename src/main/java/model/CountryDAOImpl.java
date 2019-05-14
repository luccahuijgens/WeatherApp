package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CountryDAOImpl extends BaseDAO implements ICountryDAO {

	public CountryDAOImpl() {
	}

	public List<Country> getCountries() {
		ArrayList<Country> countryList = new ArrayList<>();
		try (Connection conn = super.getConnection();
				PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Country order by name")) {
			countryList = getCountryListFromStatement(stmt);

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return countryList;
	}

	public List<Country> get10LargestPopulations() {
		ArrayList<Country> countryList = new ArrayList<>();
		try (Connection conn = super.getConnection();
				PreparedStatement stmt = conn
						.prepareStatement("SELECT * FROM Country ORDER BY population DESC LIMIT 10")) {
			countryList = getCountryListFromStatement(stmt);

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return countryList;
	}

	public List<Country> get10LargestSurfaces() {
		ArrayList<Country> countryList = new ArrayList<>();
		try (Connection conn = super.getConnection();
				PreparedStatement stmt = conn
						.prepareStatement("SELECT * FROM Country ORDER BY surfacearea DESC LIMIT 10")) {
			countryList = getCountryListFromStatement(stmt);

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return countryList;
	}

	private ArrayList<Country> getCountryListFromStatement(PreparedStatement stmt) throws SQLException {
		ArrayList<Country> countryList = new ArrayList<>();
		try (ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				countryList.add(convertCountry(rs));
			}
			return countryList;
		} catch (SQLException e) {
			throw new SQLException(e);
		}
	}

	public Country getCountryByCode(String code) {
		Country country = null;
		try (Connection conn = super.getConnection();
				PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Country WHERE iso3=?")) {
			stmt.setString(1, code);

			country = getCountryListFromStatement(stmt).get(0);

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return country;
	}

	public boolean addCountry(Country c) {
		try (Connection conn = super.getConnection();
				PreparedStatement stmt = conn
						.prepareStatement("Insert into country(code, iso3, name, capital) values(?,?,?,?)")) {

			stmt.setString(1, c.getCode());
			stmt.setString(2, c.getIso3Code());
			stmt.setString(3, c.getName());
			stmt.setString(4, c.getCapital());
			stmt.executeUpdate();
			conn.commit();
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	public void deleteCountry(Country c) {
		try (Connection conn = super.getConnection();
			PreparedStatement stmt = conn.prepareStatement("delete from country where iso3=?")){
			stmt.setString(1, c.getCode());

			// Een tweede statement uitvoeren
			stmt.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void updateCountry(Country c, String ccode) {
		try (Connection conn = super.getConnection();
			PreparedStatement stmt = conn.prepareStatement(
					"UPDATE country set code=?,iso3=?,name=?,capital=?,population=?,region=? WHERE code=?")){
			stmt.setString(1, c.getCode());
			stmt.setString(2, c.getIso3Code());
			stmt.setString(3, c.getName());
			stmt.setString(4, c.getCapital());
			stmt.setInt(5, c.getPopulation());
			stmt.setString(6, c.getRegion());
			stmt.setString(7, ccode);

			// Een tweede statement uitvoeren
			stmt.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private Country convertCountry(ResultSet rs) throws SQLException {
		String code = rs.getString("code");
		String name = rs.getString("name");
		String continent = rs.getString("continent");
		String region = rs.getString("region");
		double surface = rs.getDouble("surfacearea");
		int population = rs.getInt("population");
		String government = rs.getString("governmentform");
		String code2 = rs.getString("iso3");
		double latitude = rs.getDouble("latitude");
		double longitude = rs.getDouble("longitude");
		String capital = rs.getString("capital");
		return new Country(code, code2, name, capital, continent, region, surface, population, government,
				latitude, longitude);
	}
}