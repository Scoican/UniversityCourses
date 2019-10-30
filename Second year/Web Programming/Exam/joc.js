$(document).ready(function(){
	var	memory_array = ['1','1','2','2','3','3','4','4'];
	var memory_values = [];
	var memory_tile_ids = [];
	var tiles_flipped = 0;
	var adds = [];

	function shuffle(array){
		var j, x, i;
		for (i = array.length; i>0; i -= 1) {
			j = Math.floor(Math.random() * i);
			x = array[i - 1];
			array[i - 1] = array[j];
			array[j] = x;
		}
	}

	
	
	
	var output = '';
	shuffle(memory_array);
	for(var k = 0; k < memory_array.length; k++){
		output += '<div id="'+k+'"></div>';
	}
	
	$("#memory_board").html(output);
	
	$("#memory_board > div").on("click",function(){
		if($(this).html()=="" && memory_values.length < 2){
			var valP = $(this).attr("id");
			var val = memory_array[valP];
			$(this).css("background-image",'url(Poza'+val+'.jpg)');
		
			
		if(memory_values.length == 0){
			memory_values.push(val);
			memory_tile_ids.push(valP);
		} 
		else if(memory_values.length == 1){
				memory_values.push(val);
				memory_tile_ids.push(valP);
				if(memory_values[0] == memory_values[1]){
					tiles_flipped += 2;
					memory_values = [];
					memory_tile_ids = [];
					if(tiles_flipped == memory_array.length)
					{
					
						alert("END GAME- Start again")
						memory_values = [];
						
					}
					
				}else {
					function flip2Back(){
						if(memory_values.length != 0){
							var name1 = "#" + memory_tile_ids[0];
							var name2 = "#" + memory_tile_ids[1];
							$(name1).css("background-image", "url(download.png)");
							$(name2).css("background-image", "url(download.png)");
							memory_values = [];
							memory_tile_ids = [];
						}
					}
					setTimeout(flip2Back, 1000);
					
				}
			}
	}		
		
	});
	
		
});