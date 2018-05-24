function checkStatus(){
	
	var $form = $("#checkStatus");
	var data = getFormData($form);
	var s = JSON.stringify(data);
	console.log(s);

	$.ajax({
		type : 'POST',
		url : "checkStatus/ocspRevoke",
		contentType : 'application/json',
		dataType : "text",
		data : s,
		success : function(data) {
			alert(data)
			if(data == "{\"text\":\"Povucen\"}"){
					$("#status").append("Sertifikat je povucen!");
			}else{
					$("#status").append("Sertifikat nije povucen!");
				 }
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
