package service;

public class ServiceProvider {
	private static CountryService countryservice = CountryService.getInstance();

	private ServiceProvider() {}
	public static CountryService getCountryService() {
		return countryservice;
	}
}