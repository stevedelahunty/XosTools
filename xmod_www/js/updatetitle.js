function updateTitle() {
    
    $.ajax({
	url: 'ajax_gettitle.php',
	dataType: 'json',
	type: 'post',
	success: function(data) {
	    $('#racetitle').html(data.title);
	}
    });
}
