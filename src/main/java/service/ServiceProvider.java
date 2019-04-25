package service;

public class ServiceProvider {
	private static CountryService countryservice = new CountryService();

	public static CountryService getCountryService() {
		return countryservice;
	}
}