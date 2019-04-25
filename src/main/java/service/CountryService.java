package service;

import java.util.List;

import model.Country;
import model.CountryDAO;

public class CountryService {

	private CountryDAO countrydao = new CountryDAO();

	public List<Country> getAllCountries() {
		return countrydao.getCountries();
	}

	public List<Country> get10LargestPopulations() {
		return countrydao.get10LargestPopulations();
	}

	public List<Country> get10LargestSurfaces() {
		return countrydao.get10LargestSurfaces();
	}

	public Country getCountryByCode(String code) {
		return countrydao.getCountryByCode(code);
	}

	public boolean addCountry(Country c) {
		return countrydao.addCountry(c);
	}

	public void deleteCountry(Country c) {
		countrydao.deleteCountry(c);
	}

	public void updateCountry(Country c,String code) {
		countrydao.updateCountry(c,code);
	}
}
