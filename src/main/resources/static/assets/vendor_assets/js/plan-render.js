console.log('render laod');

function randomArrangementComp(){
	// 임의배치 컴포넌트
	var body = $("#table2");
	add_list = "";
	
	for(var i = 0; i < resultArray.length; i++){
		add_list += '<tr class="table_content">';
	    add_list += '<td class="table_num">'+resultArray[i].num+'</td>';
	    add_list += '<td>'+resultArray[i].id+'</td>';
	    add_list += '<td>'+resultArray[i].type+'</td>';
	    add_list += '<td>'+resultArray[i].repeat+'</td>';
	    add_list += '<td><input type="text" id="mil" value="0"></td>';
	    add_list += '</tr>';
	}
}

function randomizedBlockComp(){

	// 난괴법 컴포넌트
	var body = $("#table2");
	
	add_list = "";
	var add_div = "";
	var result_div = "";
	
	for(var i = 0; i < plan_repeat; i++){
		
		add_list += '<div>집구 '+(i+1)+'</div>';
		add_list += '<table>';
		add_list += '<thead>';
		add_list += '<tr class="title">';
		add_list += '<td>구획번호</td>';
		add_list += '<td>구획 ID</td>';
		add_list += '<td>처리종류</td>';
		add_list += '<td>반복</td>';
		add_list += '<td>처리포장면적(㎡)</td>';
		add_list += '</tr>';
		add_list += '</thead>';
		add_list += '<tbody class="step4-container">';
		
		for(var j = 0; j < resultArray.length; j++){
			
			if(resultArray[j].repeat == (i+1)){
				add_list += '<tr class="table_content">';
			    add_list += '<td class="table_num">'+resultArray[j].num+'</td>';
			    add_list += '<td>'+resultArray[j].id+'</td>';
			    add_list += '<td>'+resultArray[j].type+'</td>';
			    add_list += '<td>'+resultArray[j].repeat+'</td>';
			    add_list += '<td><input type="text" id="mil" value="0"></td>';
			    add_list += '</tr>';
			}
		}//end for2
		
		add_list += '</tbody>';
		add_list += '</table>';
		
		result_div += add_list;
		
		add_list = "";
	}//end for1
	
	copy_step_4.empty();
	copy_step_4.append(result_div);
	
	$(document).find(".step4-container").sortable({
		placeholder : "itemBoxHighlight",
		start : function(event, ui){
			
		},
		stop : function(event, ui){
			reorder();
		}
	});//end sortable
}


	document.querySelector('#random01').addEventListener('click', onRandomOne);
	function onRandomOne(){
		var testData = [...resultArray];
	}
