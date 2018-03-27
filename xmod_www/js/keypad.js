/*
 * Copyright(c) 2014, Steve Delahunty
 * All Rights Reserved
 *
 */
var currentValue = "";

$(document).ready(function(){
	var width=$(window).width();
	var height=$(window).height();

	$('#spinner').hide();

//	alert(width + " " + height);
//	keypadClear();
});

function keypadPush(key) {
	currentValue = currentValue + key
	keypadUpdateValue(currentValue);
}

function keypadClear() {
	currentValue = "";
	keypadUpdateValue("");
	$('#searchinfo').hide();
	$('#search_results_msg').html("");
}

function keypadBackspace() {
	var len = currentValue.length;
	if (len == 0) return;
	
	currentValue = currentValue.substring(0, len-1);
	len = currentValue.length;
	if (len == 0)
		keypadClear();
	else
		keypadUpdateValue(currentValue);
}

function keypadUpdateValue(val) {
	currentValue = val;
	$('#entryText').html(val);
	scheduleLookup(currentValue);
}