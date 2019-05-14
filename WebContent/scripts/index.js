fillCountryTable();
fillIPData();

function fillCountryTable(){
	$.getJSON("restservices/countries",function(countries){
		$.each(countries,function(i,country){
			$("#countryTable").append("<tr onclick=fillWeatherData("+country.lat+","+country.lon+",\""+encodeURIComponent(country.capital)+"\");fillMap("+country.lat+","+country.lon+",\""+encodeURIComponent(country.capital)+"\");resetFocus(); ><td>"
			+"<img src=https://www.countryflags.io/"+country.code+"/shiny/64.png>&emsp;"+country.name+"</td>"
			+"<td>"+country.capital+"</td>"
			+"<td>"+country.region+"</td>"
			+"<td>"+country.surface+"</td>"
			+"<td>"+country.population+"</td>"
			+"<td style=width:40px; onclick=openEditModal('"+country.iso3+"')><img style=height:24px; src=https://cdn4.iconfinder.com/data/icons/software-menu-icons/256/SoftwareIcons-68-512.png></td></tr>");
		})
	})
}

function resetFocus(){
	$('html, body').animate({
        scrollTop: $("#dashboardTitle").offset().top
    }, 2000);
}

function fillWeatherData(latitude,longitude,name){
    name=name.replace(/%20/g, " ");
	currentWeather.innerHTML=name;
	var localCache = localStorage.getItem(name);
	var timeDifference=0;
	if (localCache!=null){
	var cacheDate = new Date(JSON.parse(localCache).recorddate);
	var today=new Date();
	timeDifference=Math.round((((today-cacheDate) % 86400000) % 3600000) / 60000)
	}
	if (localCache == null || timeDifference>10){
	$.getJSON("http://api.openweathermap.org/data/2.5/weather?lat="+latitude+"&lon="+longitude+"&appid=63f1c99954fd033655434788845cac95", function(data){
		var storageobject={};
		storageobject['temperature']=convertTemp(data.main.temp)+" C";
		storageobject['forecast']=data.weather[0].main;
		storageobject['humidity']=data.main.humidity+"%";
		storageobject['windspeed']=convertSpeed(data.wind.speed)+" km/h";
		storageobject['winddirection']=direction(data.wind.deg);
		storageobject['sunrise']=convertTime(data.sys.sunrise);
		storageobject['sunset']=convertTime(data.sys.sunset);
		storageobject['recorddate']=new Date();
		localStorage.setItem(name,JSON.stringify(storageobject));

		Temperature.innerHTML=convertTemp(data.main.temp)+" C";
		Forecast.innerHTML=data.weather[0].main;
		Humidity.innerHTML=data.main.humidity+"%";
		Windspeed.innerHTML=convertSpeed(data.wind.speed)+" km/h";
		Winddirection.innerHTML=direction(data.wind.deg);
		Sunrise.innerHTML=convertTime(data.sys.sunrise);
		Sunset.innerHTML=convertTime(data.sys.sunset);
	});
	}else{
		let localCacheJson=JSON.parse(localCache)
		Temperature.innerHTML=localCacheJson.temperature;
		Forecast.innerHTML=localCacheJson.forecast;
		Humidity.innerHTML=localCacheJson.humidity;
		Windspeed.innerHTML=localCacheJson.windspeed;
		Winddirection.innerHTML=localCacheJson.winddirection;
		Sunrise.innerHTML=localCacheJson.sunrise;
		Sunset.innerHTML=localCacheJson.sunset;
	}
}

function fillIPData(){
$.getJSON("http://ip-api.com/json/?callback=?", function(data){
	Land.innerHTML=data.country;
	Regio.innerHTML=data.region;
	City.innerHTML=data.city;
	ZipCode.innerHTML=data.zip;
	TimeZone.innerHTML=data.timezone;
	IP.innerHTML=data.query;
	lat.innerHTML=data.lat;
	lon.innerHTML=data.lon;
	
	document.getElementById('ipData')
    .addEventListener('click', function (event) {
    	fillWeatherData(data.lat,data.lon,data.city);
    	fillMap(data.lat,data.lon,data.city);
    });
	fillWeatherData(data.lat,data.lon,data.city);
	fillMap(data.lat,data.lon,data.city);
});
}

function fillMap(lat,lon,name){
    name=name.replace(/%20/g, " ");
	currentLocation.innerHTML=name;
	var mapdiv=document.getElementById("map")
	mapdiv.remove();
	document.getElementById('mapContainer').innerHTML = "<div id='map'></div>";
	mapdiv=document.getElementById("map")
    var map = L.map(mapdiv, {
    	center: [lat, lon],
        zoom: 14,
        maxZoom: 18
    });

    const osmUrl = 'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
        osmAttrib = '&copy; <a href="https://openstreetmap.org/copyright">OpenStreetMap</a> contributors',
        osm = L.tileLayer(osmUrl, {
            attribution: osmAttrib
        }),
        drawnItems = L.featureGroup().addTo(map);

    const baseLayers = L.control.layers({
        'osm': osm.addTo(map),
        "google": L.tileLayer('https://www.google.cn/maps/vt?lyrs=s@189&gl=cn&x={x}&y={y}&z={z}', {
            attribution: 'google'
        }),
/*
 * "railway":
 * L.tileLayer('http://{s}.tiles.openrailwaymap.org/standard/{z}/{x}/{y}.png', {
 * attribution: '<a href="https://www.openstreetmap.org/copyright">©
 * OpenStreetMap contributors</a>, Style: <a
 * href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA 2.0</a> <a
 * href="http://www.openrailwaymap.org/">OpenRailwayMap</a> and OpenStreetMap',
 * minZoom: 2, maxZoom: 19, tileSize: 256 }),
 */
        "openstreetmap": L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}', {
            attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, <a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
            id: 'mapbox.streets',
            accessToken: 'pk.eyJ1IjoibHVjY2FoMDYwNyIsImEiOiJjam4ydGthZzIxczFiM3FwbGQ4ejBmNTd2In0.e5t_M6rd1aFttqch2vBD-A'
        })
    }).addTo(map);
    
    var marker=L.marker([lat,lon])
        marker.addTo(map)
    marker.bindPopup("<b>"+name+"</b>").openPopup();
    return map;
}

function convertTemp(temperature){
	var temp=Math.round(temperature-273);
	return temp;
}
function convertTime(time){
var date = new Date(time*1000);
var hours = date.getHours();
var minutes = "0" + date.getMinutes();
var seconds = "0" + date.getSeconds();
var formattedTime = hours + ':' + minutes.substr(-2) + ':' + seconds.substr(-2);
return formattedTime;}

function convertSpeed(speed){
	var newspeed=Math.round(speed*3.6)
	return newspeed;
}

function direction(dir){
	var direct="";
	if (dir == null){
		direct="Not Available";
	}
	if (dir<22.5||dir>337.5){
		direct="North";
	}
	if (dir<67.5||dir>22.5){
		direct="NorthEast";
	}
	if (dir<122.5||dir>67.5){
		direct="East";
	}
	if (dir<157.5||dir>112.5){
		direct="SouthEast";
	}
	if (dir<202.5||dir>157.5){
		direct="South";
	}
	if (dir<247.5||dir>202.5){
		direct="SouthWest";
	}
	if (dir<292.5||dir>247.5){
		direct="West";
	}
	if (dir<337.5||dir>292.5){
		direct="NorthWest"
	}
	return direct;
}

//Get the modal
var modal = document.getElementById('modal');

// Get the button that opens the modal
var btn = document.getElementById("addCountry");

// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

// When the user clicks the button, open the modal 
btn.onclick = function() {
  modal.style.display = "block";
}

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
  modal.style.display = "none";
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
  if (event.target == modal) {
    modal.style.display = "none";
  }
}

$('#submitForm').click(function(){
var myForm=document.getElementById('countryForm');
var iso2=myForm['iso2'].value;
var iso3=myForm['iso3'].value;
var name=myForm['name'].value;
var cap=myForm['cap'].value;
var dataString="name="+name+"&iso2="+iso2+"&iso3="+iso3+"&cap="+cap;
$.ajax({
    type: "POST",
    url: "restservices/countries",
    data: dataString,
    success: function() {
    }});
return false;
});

//Get the modal
var modal2 = document.getElementById('modal2');

// Get the <span> element that closes the modal
var span2 = document.getElementsByClassName("close")[1];

// When the user clicks on <span> (x), close the modal
span2.onclick = function() {
  modal2.style.display = "none";
}

//When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
  if (event.target == modal2) {
    modal2.style.display = "none";
  }
}

function openEditModal(code){
	 $.getJSON("restservices/countries?countryCode="+code,function(country){
	 var myForm=document.getElementById('countryEditForm');
	 myForm['iso2'].value=country.code;
	 myForm['iso3'].value=country.iso3;
	 myForm['name'].value=country.name;
	 myForm['cap'].value=country.capital;
	 myForm['region'].value=country.region;
	 myForm['population'].value=country.population;
	 modal2.style.display = "block";
	 });
}

$('#submitEditForm').click(function(){
	var myForm=document.getElementById('countryEditForm');
	 var iso2=myForm['iso2'].value
	 var iso3=myForm['iso3'].value
	 var name=myForm['name'].value;
	 var cap=myForm['cap'].value;
	 var region=myForm['region'].value;
	 var population=myForm['population'].value;
	var dataString="name="+name+"&iso2="+iso2+"&iso3="+iso3+"&cap="+cap+"&region="+region+"&population="+population;
	$.ajax({
	    type: "PUT",
	    url: "restservices/countries/"+iso2,
	    data: dataString,
	    success: function() {
	    }});
	return false;
	});