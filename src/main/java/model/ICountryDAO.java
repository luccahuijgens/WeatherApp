package model;

import java.util.List;

public interface ICountryDAO {
	public List<Country> getCountries();

	public List<Country> get10LargestPopulations();

	public List<Country> get10LargestSurfaces();

	public Country getCountryByCode(String code);

	public boolean addCountry(Country c);

	public boolean deleteCountry(Country c);

	public boolean updateCountry(Country c,String ccode);
}
