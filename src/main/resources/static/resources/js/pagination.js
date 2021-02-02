function paginate(pag,uri){
	res = [];
    $.ajax({
    	url: uri+pag,
    	async: false,
    	success: function(respuesta) {
    		var obj = respuesta;
    		var n = 0;
    		$.each( obj, function( key, value ) {
    			res.push(obj[key]);
    			n++;
    			});
    		
    	
    	},
    	error: function() {
            console.log("No se ha podido obtener la información");
        }
    });
   return res;
 }
    