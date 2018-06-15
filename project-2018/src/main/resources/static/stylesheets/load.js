function loadCertificate(){
	
	var $form = $("#loadCertificate");
	var data = getFormData($form);
	var s = JSON.stringify(data);
	console.log(s);

	$.ajax({
		type : 'POST',
		url : "loadCertificate/load",
		contentType : 'application/json',
		dataType : "json",
		data : s,
		headers: createAuthorizationTokenHeader(),
		success : function(data) {
			
			$("#issuer").empty();
			$("#subject").empty();
			$("#serialNumber").empty();
			$("#sa").empty();
			$("#type").empty();
			$("#pk").empty();
			
			$("#issuer").append("<hr><div><label>Issuer:</label><label id= \"issuer\">"+data.issuer+"</label></div>");
			$("#subject").append("<div><label>Subject:</label><label id =\"subject\">"+data.subject+"</label></div>");
			$("#serialNumber").append("<div> <label>Serial Number:</label><label id = \"serialNumber\">"+data.serialNumber+"</label></div>");
			$("#sa").append("<div><label>Signature Algorithm:</label><label id = \"sa\">"+data.signatureAlgorithm+"</label></div>");
			$("#type").append("<div><label>Type:</label><label id = \"type\">"+data.type+"</label></div>");
			$("#pk").append("<div><label>Public Key:</label><label id = \"pk\">"+data.publicKey+"</label></div><hr>");
		},
		error : function(XMLHttpRequest, textStatus, errorThrown,data) {
			
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

//function renderLoad(sscData){
	
	//console.log(sscData.fileName)
//}
