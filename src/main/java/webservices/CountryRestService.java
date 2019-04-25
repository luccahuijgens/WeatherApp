package webservices;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import model.Country;
import service.CountryService;
import service.ServiceProvider;

@Path("countries")
public class CountryRestService {
	CountryService service = ServiceProvider.getCountryService();

	@GET
	@Produces("application/json")
	public String getCountry(@QueryParam("countryCode") String countryCode) {
		if (countryCode==null) {
			JsonArrayBuilder jab = Json.createArrayBuilder();
			for (Country c : service.getAllCountries()) {
				JsonObjectBuilder job = convertJson(c);
				jab.add(job);
			}
			JsonArray array = jab.build();
			return (array.toString());
		} else {
			try {
				Country country = service.getCountryByCode(countryCode);
				JsonObjectBuilder job = convertJson(country);
				return (job.build().toString());
			} catch (Exception e) {
				return Response.status(404).build().toString();
			}
		}
	}

	@Path("/largestsurfaces")
	@GET
	@Produces("application/json")
	public String LargestSurf() {
		JsonArrayBuilder jab = Json.createArrayBuilder();
		List<Country> countrylist = service.get10LargestSurfaces();
		for (Country c : countrylist) {
			JsonObjectBuilder job = convertJson(c);
			jab.add(job);
		}
		return (jab.build().toString());
	}

	@Path("/largestpopulations")
	@GET
	@Produces("application/json")
	public String LargestPop() {
		JsonArrayBuilder jab = Json.createArrayBuilder();
		List<Country> countrylist = service.get10LargestPopulations();

		for (Country c : countrylist) {
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("name", c.getName());

			jab.add(job);
		}

		JsonArray array = jab.build();
		return (array.toString());
	}

	@POST
	//@RolesAllowed("user")
	@Produces("application/json")
	public String createCountry(@FormParam("iso2") String cd, @FormParam("iso3") String cd2,
			@FormParam("name") String nm, @FormParam("cap") String cap) {
		Country newCountry = new Country(cd, cd2, nm, cap);
		service.addCountry(newCountry);
		return Response.ok().build().toString();
	}

	@DELETE
	@RolesAllowed("user")
	@Path("{id}")
	public Response deleteCustomer(@PathParam("id") String code) {
		Country found = null;

		for (Country c : service.getAllCountries()) {
			if (c.getCode().equals(code)) {
				found = c;
				break;
			}
		}

		if (found == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		} else {
			service.deleteCountry(found);
			return Response.ok().build();
		}
	}

	@PUT
	@Path("{get_iso2}")
	public String updateCountry(@PathParam("get_iso2") String code,@FormParam("iso2") String cd, @FormParam("iso3") String cd2,
			@FormParam("name") String nm, @FormParam("cap") String cap, @FormParam("population") int pop, @FormParam("region") String region) {
		Country newCountry = new Country(cd, cd2, nm, cap,pop,region);
		service.updateCountry(newCountry,code);
		return Response.ok().build().toString();
	}

	private JsonObjectBuilder convertJson(Country c) {
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("name", c.getName());
		job.add("capital", c.getCapital());
		job.add("region", c.getRegion());
		job.add("surface", c.getSurface());
		job.add("population", c.getPopulation());
		job.add("lat", c.getLatitude());
		job.add("lon", c.getLongitude());
		job.add("code", c.getCode());
		job.add("iso3", c.getIso3Code());
		return job;
	}
}
