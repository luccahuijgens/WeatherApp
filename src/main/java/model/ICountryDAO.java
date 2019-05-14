package model;

import java.util.List;

public interface ICountryDAO {
	public List<Country> getCountries();

	public List<Country> get10LargestPopulations();

	public List<Country> get10LargestSurfaces();

	public Country getCountryByCode(String code);

	public boolean addCountry(Country c);

	public void deleteCountry(Country c);

	public void updateCountry(Country c,String ccode);
}
