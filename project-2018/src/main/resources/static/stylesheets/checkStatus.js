$(document).ready(function(){
	
	$('#checkBut').click(function(){
		var url = "certificate/" + $("#searchField").val();
		console.log(url);
	
		$.ajax({
			url: url,
			success: function(data){
				
				console.log(data);
				
			}
		});
	});
	
	
	 
});