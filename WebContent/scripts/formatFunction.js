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