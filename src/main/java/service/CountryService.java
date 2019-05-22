package service;

import java.util.List;

import model.Country;
import model.CountryDAOImpl;
import model.ICountryDAO;

public class CountryService {
	private static CountryService instance=null;
	
	private CountryService() {
		countrydao=new CountryDAOImpl();
	}
	
	public static CountryService getInstance() {
		if (instance==null) {
			instance=new CountryService();
		}
		return instance;
	}
	private ICountryDAO countrydao;

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

	public boolean deleteCountry(Country c) {
		return countrydao.deleteCountry(c);
	}

	public boolean updateCountry(Country c,String code) {
		return countrydao.updateCountry(c,code);
	}
}
