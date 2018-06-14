
function revokeCert() {
	
	var $form = $("#revokeCertificate");
	var data = getFormData($form);
	var s = JSON.stringify(data);
	console.log(s);

	$.ajax({
		type : 'POST',
		url : "revokecontroller/revokeCertificate",
		contentType : 'application/json',
		dataType : "json",
		data : s,
		headers: createAuthorizationTokenHeader(),
		success : function(data) {
			alert("Uradjen je revoke");
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR: " + errorThrown);
		}
	});
	
}
function getFormData($form){
	
	 var unindexed_array = $form.serializeArray();
	    var indexed_array = {};

	    $.map(unindexed_array, function(n, i){
	        indexed_array[n['name']] = n['value'];
	    });

	    return indexed_array;
	
}