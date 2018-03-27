/*
 * Copyright (c) 2014, Steve Delahunty
 * All Rights Reserved
 *
 * Lookup names based on bib or name via ajax
 */
var timeoutId = 0;

function doLookup(val) {
	$('#spinner').show();
	lookup(currentValue);

}

function lookupSuccess(data)
{
	$('#spinner').hide();
	if (data == null) {
		$('#search_results_msg').html("0 records found.");
	} else {
		
		$('#searchinfo').show();
		$('#searchinfo').html(""); // Release it
		numRecordsFound = data.length;
		if (numRecordsFound == 50)
			$('#search_results_msg').html(data.length+"+ records found. Narrow your search.");
		else
			$('#search_results_msg').html(data.length+" records found");
		var url="show.html";
		var html="";
		var id='r_';

		for (i=0; i<data.length; i++) {
		    bib = data[i].bib;
		    name = data[i].last_name+", "+data[i].first_name;
		    html += "<a href='"+url+"?"+bib+"'>";
		    html += "<button class='resultButton'>";
		    html += "#" + bib+"   <b>" + name;
		    html += "</b></button></a>";
		}
		$('#searchinfo').html(html);
		//$('#t5').tablesorter();
	}
}

function lookup(val) {

	if (val.length == 0) return;
	$.ajax({
		url: 'ajax_lookup.php?'+val,
		//		  url: 'http://192.168.1.113/ajax_lookup.php?val='+val,
		data: val,
		dataType: 'json',
		type: 'post',
		success: lookupSuccess
	});
}
