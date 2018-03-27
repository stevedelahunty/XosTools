<?php

?>

<html lang='en'>
<head>
<meta http-equiv='content-type' content='text/html; charset=utf-8'>
<meta http-equiv='PRAGMA' content='no-cache'>
<meta name='Author' content='Steve Delahunty, steve.delahunty@gmail.com'>
<meta name='viewport' content='width=device-width, initial-scale=1, maximum-scale=1'>
<title>Downloadable Optics Module Monitor</title>
<script src='js/jquery-1.8.0.js'  type='text/javascript'></script>
<script src='js/lookup.js'  type='text/javascript'></script>
<script src='js/highcharts.js'  type='text/javascript'></script>
<script src='js/highcharts-3d.js'></script>
<script src='js/highcharts-more.js'  type='text/javascript'></script>
<script src='js/solid-gauge.js'  type='text/javascript'></script>
<script src='js/fastclick.js'  type='text/javascript'></script>
<style>
  #bar {
  height: 0;
  width: 7px;
  position: absolute;
  top: 360px;
  z-index: 3px;
  background-color: #cb4949;
  margin-left: 176px;
  }

  #background {
  background-image: url("http://3.bp.blogspot.com/-hVVw6rZDBn4/ToFRnKN2sxI/AAAAAAAAATU/1ZfrvzQeUEQ/s1600/thermometer.jpg");
  background-color: red;
  height: 460px;
  width: 360px;
  }
</style>
<script>

function doLookup()
{
	$.ajax({
		url:'ajax_lookup.php',
		type: 'post',
		dataType: 'json',
		cache: 'false',
		success: function(data) {
			$('#temperaturef').html(data.temp_f);
			timerHandle = setTimeout(doLookup, 1000);
				
		}
	});
	
}

$(document).ready(function() {
	timerHandle = setTimeout(doLookup, 1000);
});
</script>
<?php
$localIP = $_SERVER['SERVER_ADDR'];
?>

</head>
	<body>
	<div style='float: left;'>
	<a href='index.php'>RELOAD</a></div>
<h1><center>DOM (Downloadable Optics Module) - <?php
system ("ifconfig wlan0 | grep 'inet addr' | awk -F':' '{ print $2; }' | awk '{print $1;}'");
?>
</center></h1>
	<hr>
	<table width=100%>
	<tr>
	<td width=55% valign=top>
	<h2>Data from Arduino</h2>
	<table width=75% style='font-size: 24px; color: blue;' cellspacing=20>
	<tr><td >Temperature (f)</td><td><span id=temperaturef style='color: red'></span></td></tr>
	<tr><td >Temperature (c)</td><td><span id=temperaturec style='color: red'></span></td></tr>
	<tr><td >Humidity</td><td><span id=humidity  style='color: red'></span></td></tr>
	<tr><td >Potentiometer</td><td><span id=pot  style='color: red'></span></td></tr>
	<tr><td >Loop Counter</td><td><span id=loop  style='color: red'></span></td></tr>
	</table>
	<div style='background: green; width: 100%; height: 40px; font-size:32px; text-align: center; color: white; font-weight: bold;'>
	  ONLINE
	  </div>
	</td>
	<td width=50% valign=top>
	<h2>MCP25625 Registers</h2>
	<table width=75% style='font-size: 16px; color: blue;' cellspacing=5>
	<tr><td >BFPCTRL</td><td><span id=BFPCTRL style='color: red'></span></td></tr>
	<tr><td >TXRTSCTRL</td><td><span id=TXRTSCTRL style='color: red'></span></td></tr>
	<tr><td >CANSTAT</td><td><span id=CANSTAT style='color: red'></span></td></tr>
	<tr><td >CANCTRL</td><td><span id=CANCTRL style='color: red'></span></td></tr>
	<tr><td >TEC</td><td><span id=TEC style='color: red'></span></td></tr>
	<tr><td >REC</td><td><span id=REC style='color: red'></span></td></tr>
	<tr><td >CNF3</td><td><span id=CNF1 style='color: red'></span></td></tr>
	<tr><td >CNF2</td><td><span id=CNF2 style='color: red'></span></td></tr>
	<tr><td >CNF3</td><td><span id=CNF3 style='color: red'></span></td></tr>
	<tr><td >CANINTE</td><td><span id=CANINTE style='color: red'></span></td></tr>
	<tr><td >CANINTF</td><td><span id=CANINTF style='color: red'></span></td></tr>
	<tr><td >EFLG</td><td><span id=EFLG style='color: red'></span></td></tr>
	</table>
	</td>
	</tr>
	</table>
</body>
</html>
			
