$(document).ready(function(){
	
	$('#checkBut').click(function(){
		var url = "certificate/" + $("#searchField").val();
		console.log(url);
		
		$("#inputCheck").modal("toggle");
		
		$("#checkDiv").empty();
		$("#exampleModalLabelReg2").empty();
	
		$.ajax({
			url: url,
			success: function(data){
				
				$("#exampleModalLabelReg2").append("Certificate with serial number: " + data.serialNumber);
				
				newCertificate = "<div><label>Serial number:</label>"  + data.serialNumber + "</div>"
								+"<div><label>Issuer:</label>"  + data.issuer + "</div>"
								+"<div><label>Valid from:</label>"  + data.validFrom + "<strong> to </strong>" + data.validUntil + "</div>"
								+"<div><label>AIA:</label>"  + data.aia + "</div>";
								
				$("#checkDiv").empty().append(newCertificate);
				
			}
		});
		
		$("#checkCertificate").modal("toggle");
	});
	
	
	 
});