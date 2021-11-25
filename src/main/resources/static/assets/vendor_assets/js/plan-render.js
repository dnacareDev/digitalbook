console.log('render load');

function reRenderTableComp(type){
	var growType = type;
	var plan_repeat = document.querySelector('#plan_repeat').value;
	
	var factor1 = "";
		var factor2 = "";
		var factor3 = "";
		
		if(Object.keys(data1).length != 0){
			if(data1.type == "시비량"){
				factor1 = data1.level.split(",");
			}else if(data1.type == "시험재료"){
				factor1 = data1.text.split(",");
			}else{
				factor1 = data1.content.split(",");
			}
		}
		if(Object.keys(data2).length != 0){
			if(data2.type == "시비량"){
				factor2 = data2.level.split(",");
			}else if(data2.type == "시험재료"){
				factor2 = data2.text.split(",");
			}else{
				factor2 = data2.content.split(",");
			}
		}
		if(Object.keys(data3).length != 0){
			if(data3.type == "시비량"){
				factor3 = data3.level.split(",");
			}else if(data3.type == "시험재료"){
				factor3 = data3.text.split(",");
			}else{
				factor3 = data3.content.split(",");
			}
		}
		
		var plan_repeat = $("#plan_repeat").val();
		
		var grow_type = $("#grow_type option:selected").val();
		
		//구획 id 만들기 위한 변수들
		var num = 0;
		var num1 = 0;
		var level = 0;
		var id = "";
		var id1 = "";
		var id2 = "";
		var type = "";
		var type2 = "";
		var type3 = "";
		var repeat = "";
		
		//factor null 유무에 따라 factorArray 만들어 주기
		if(factor1 != "" && factor2 == "" && factor3 == ""){
			for(var i = 0; i < factor1.length; i++){
				id = "A"+(i+1);
				type = factor1[i];
				num++;
				var number = num;
				
				var data = {"num" : number, "id" : id, "type" : type};
				factorArray.push(data);
				
				id = "";
				type = "";
			}
		}else if(factor1 != "" && factor2 != "" && factor3 == ""){
			for(var i = 0; i < factor1.length; i++){
				id = "A"+(i+1);
				type = factor1[i];
				
				for(var j = 0; j < factor2.length; j++){
					id2 = id;
					id2 = id2 + '-B' + (j+1);
					type2 = type;
					type2 = type2 + ',' + factor2[j];
					num++;
					var number = num;
					
					var data = {"num" : number, "id" : id2, "type" : type2};
					factorArray.push(data);
				}
				id = "";
				type = "";
			}
		}else if(factor1 != "" && factor2 != "" && factor3 != ""){
			for(var i = 0; i < factor1.length; i++){
				id = "A"+(i+1);
				type = factor1[i];
				
				for(var j = 0; j < factor2.length; j++){
					id2 = id;
					id2 = id2 + '-B' + (j+1);
					type2 = type;
					type2 = type2 + ',' + factor2[j];
					
					for(var k = 0; k < factor3.length; k++){
						num++;
						var number = num;
						id3 = id2;
						id3 = id3 + '-C'+(k+1);
						type3 = type2;
						type3 = type3 + ',' + factor3[k];
						
						var data = {"num" : number, "id" : id3, "type" : type3};
						factorArray.push(data);
					}//end for3
					
				}//end for2
				id = "";
				type = "";
			}//end for1
		}
	
	
	if(growType == "0"){ //완전임의배치
		
		var copy_step_4 = $("#copy-step-4");
		add_list = "";
		
		add_list += '<table id="table2">';
		add_list += '<thead>';
		add_list += '<tr class="title">';
		add_list += '<th>구획번호</th>';
		add_list += '<th>구획 ID</th>';
		add_list += '<th>처리종류</th>';
		add_list += '<th>반복</th>';
		add_list += '<th>처리포장면적(㎡)</th>';
		add_list += '</tr>';
		add_list += '</thead>';
		add_list += '<tbody class="step4-container container1">';
		
		for(var i = 0; i < resultArray.length; i++){
			add_list += '<tr data-id="'+resultArray[i].id.split("-")[0]+"-"+resultArray[i].repeat+'" class="table_content">';
		    add_list += '<td class="table_num">'+resultArray[i].num+'</td>';
		    add_list += '<td>'+resultArray[i].id+'</td>';
		    add_list += '<td>'+resultArray[i].type+'</td>';
		    add_list += '<td>'+resultArray[i].repeat+'</td>';
		    add_list += '<td><input type="text" id="mil" class="seg_area" value="0"></td>';
		    add_list += '</tr>';
		}
		
		add_list += '</tbody>';
		add_list += '</table>';
		
		
		copy_step_4.empty();
		var start = performance.now();
		copy_step_4.append(add_list);
		var duration = performance.now() - start;
		
		$("#info_popup").css("display", "flex");
		setTimeout(function(){
			
			$(document).find(".container1").sortable({
				placeholder : "itemBoxHighlight",
				start : function(event, ui){
					
				},
				stop : function(event, ui){//해당 구획 이동 완료 후 정렬
					
					var checkId = ui.item[0].getAttribute("data-id");
					
					var elementEls = $(this).find(".table_content");
					elementEls = Array.prototype.slice.call(elementEls);
					var index = elementEls.indexOf(ui.item[0]);
					
					var isPrev = false;
					var isNext = false;
					if(typeof elementEls[index-1] != 'undefined'){
						var prevItem = elementEls[index-1].getAttribute("data-id");
						if(prevItem != checkId){
							isPrev = true;
						}
					}else{
						isPrev = true;
					}
					if(typeof elementEls[index+1] != 'undefined'){
						var nextItem = elementEls[index+1].getAttribute("data-id");
						if(nextItem != checkId){
							isNext = true;
						}
					}else{
						isNext = true;
					}
					
					if(isPrev && isNext){
						return false;
					}else{
						//아이디 정렬해주기 위한 idarr 만들기
	 					var idArr = new Array();
						for(var i = 0; i < resultArray.length; i++){
							idArr.push(resultArray[i].id);
						}
						
						var num = 1;
	 					var targetNum = parseInt(ui.item[0].children[0].innerText)-1;
	 					reorder(num, idArr);
	 					var moveNum = parseInt(ui.item[0].children[0].innerText)-1;
						
						//순서 바꿔주기
						let item = resultArray.splice(targetNum, 1);
						resultArray.splice(moveNum, 0, item[0]);
						
						//id와 구획번호 재정렬
						for(var i = 0; i < resultArray.length; i++){
							resultArray[i].num = (i+1);
							resultArray[i].id = idArr[i];
						}
						//console.log(resultArray);
						var getDataArr = [...resultArray];
						getDataResult(getDataArr, grow_type);
					}
					
				}//end stop
			});//end sortable
			
			$("#info_popup").css("display", "none");
		}, (duration*1000));
		
	}else if(growType == "1"){ //난괴법
	
		var arr = new Array();
		for(var i = 0; i < resultArray.length; i++){
			arr[i] = resultArray[i].id.split("-")[0];
		}
		var idArray = Array.from(new Set(arr));
		
		var copy_step_4 = $("#copy-step-4");
		
		add_list = "";
		var add_div = "";
		var result_div = "";
		
		for(var i = 0; i < plan_repeat; i++){
			
			add_list += '<div class="copy-title">집구 '+(i+1)+'</div>';
			add_list += '<table id="table2">';
			add_list += '<thead>';
			add_list += '<tr class="title">';
			add_list += '<th>구획번호</th>';
			add_list += '<th>구획 ID</th>';
			add_list += '<th>처리종류</th>';
			add_list += '<th>반복</th>';
			add_list += '<th>처리포장면적(㎡)</th>';
			add_list += '</tr>';
			add_list += '</thead>';
			add_list += '<tbody class="step4-container container'+(i+1)+'">';
			
			
			for(var j = 0; j < resultArray.length; j++){
				
				if(resultArray[j].repeat == (i+1)){
					var checkId = resultArray[j].id.split("-")[0];
					for(var k = 0; k < idArray.length; k++){
						if(checkId == idArray[k]){
							add_list += '<tr data-id="'+idArray[k]+'" class="table_content '+idArray[k]+'">';
						}
					}
				    add_list += '<td class="table_num">'+resultArray[j].num+'</td>';
				    add_list += '<td>'+resultArray[j].id+'</td>';
				    add_list += '<td>'+resultArray[j].type+'</td>';
				    add_list += '<td>'+resultArray[j].repeat+'</td>';
				    add_list += '<td><input type="text" id="mil" class="seg_area" value="0"></td>';
				    add_list += '</tr>';
				}
			}//end for2
			
			add_list += '</tbody>';
			add_list += '</table>';
			
			result_div += add_list;
			
			add_list = "";
		}//end for1
		
		copy_step_4.empty();
		var start = performance.now();
		copy_step_4.append(result_div);
		var duration = performance.now() - start;
		
		//end 난괴법 컴포넌트
		$("#info_popup").css("display", "flex");
		setTimeout(function(){
			//jquery sortable 적용
			for(var i = 0; i < plan_repeat; i++){
				$(document).find(".container"+(i+1)).sortable({
					placeholder : "itemBoxHighlight",
					start : function(event, ui){
						
					},
					stop : function(event, ui){//해당 구획 이동 완료 후 정렬
						
						var checkId = ui.item[0].getAttribute("data-id");
						
						var elementEls = $(this).find(".table_content");
						elementEls = Array.prototype.slice.call(elementEls);
						var index = elementEls.indexOf(ui.item[0]);
						
						var isPrev = false;
						var isNext = false;
						if(typeof elementEls[index-1] != 'undefined'){
							var prevItem = elementEls[index-1].getAttribute("data-id");
							if(prevItem != checkId){
								isPrev = true;
							}
						}else{
							isPrev = true;
						}
						if(typeof elementEls[index+1] != 'undefined'){
							var nextItem = elementEls[index+1].getAttribute("data-id");
							if(nextItem != checkId){
								isNext = true;
							}
						}else{
							isNext = true;
						}
						
						if(isPrev && isNext){
							return false;
						}else{
							//아이디 정렬해주기 위한 idarr 만들기
		 					var idArr = new Array();
							for(var i = 0; i < resultArray.length; i++){
								idArr.push(resultArray[i].id);
							}
							
							//해당 로우의 반복수
							var num = parseInt(ui.item[0].children[3].innerText);
							
							//해당 집구의 테이블 로우 개수
							var standardNum = $(".container1 .table_content").length;
							var checkNum = (num-1) * standardNum;
							
							var targetNum = parseInt(ui.item[0].children[0].innerText)-1+checkNum;
		 					reorder(num, idArr);
		 					var moveNum = parseInt(ui.item[0].children[0].innerText)-1+checkNum;

							let item = resultArray.splice(targetNum, 1);
							resultArray.splice(moveNum, 0, item[0]);
							var plusNum = 0;
							for(var i = checkNum; i < (checkNum+standardNum); i++){
								plusNum++;
								resultArray[i].num = plusNum;
								resultArray[i].id = idArr[i];
							}
							var getDataArr = [...resultArray];
							getDataResult(getDataArr, grow_type);
						}
						
					}//end stop
				});//end sortable
			}
			$("#info_popup").css("display", "none");
		}, (duration*1000));
	
	}else if(growType == "2"){ //분할구배치
		//분할구배치법에서 그룹 나눠줄 id array
		var arr = new Array();
		for(var i = 0; i < resultArray.length; i++){
			arr[i] = resultArray[i].id.split("-")[0];
		}
		var idArray = Array.from(new Set(arr));
		
		var copy_step_4 = $("#copy-step-4");
		
		add_list = "";
		var add_div = "";
		var result_div = "";
		
		for(var i = 0; i < plan_repeat; i++){
			
			add_list += '<div class="copy-title">집구 '+(i+1)+'</div>';
			add_list += '<table id="table2">';
			add_list += '<thead>';
			add_list += '<tr class="title">';
			add_list += '<th>구획번호</th>';
			add_list += '<th>구획 ID</th>';
			add_list += '<th>처리종류</th>';
			add_list += '<th>반복</th>';
			add_list += '<th>처리포장면적(㎡)</th>';
			add_list += '</tr>';
			add_list += '</thead>';
			add_list += '<tbody class="step4-container container'+(i+1)+'">';
			
			for(var j = 0; j < resultArray.length; j++){
				
				if(resultArray[j].repeat == (i+1)){
					var checkId = resultArray[j].id.split("-")[0];
					for(var k = 0; k < idArray.length; k++){
						if(checkId == idArray[k]){
							add_list += '<tr data-id="'+idArray[k]+'" class="table_content '+idArray[k]+'">';
						}
					}
				    add_list += '<td class="table_num">'+resultArray[j].num+'</td>';
				    add_list += '<td>'+resultArray[j].id+'</td>';
				    add_list += '<td>'+resultArray[j].type+'</td>';
				    add_list += '<td>'+resultArray[j].repeat+'</td>';
				    add_list += '<td><input type="text" id="mil" class="seg_area" value="0"></td>';
				    add_list += '</tr>';
				}
			}//end for2
			
			add_list += '</tbody>';
			add_list += '</table>';
			
			result_div += add_list;
						
			add_list = "";
		}//end for1
		
		copy_step_4.empty();
		var start = performance.now();
		copy_step_4.append(result_div);
		var duration = performance.now() - start;
		
		$("#info_popup").css("display", "flex");
		setTimeout(function(){
			//jquery sortable 적용
			for(var i = 0; i < plan_repeat; i++){
				$(document).find(".container"+(i+1)).sortable({
					placeholder : "itemBoxHighlight",
					start : function(event, ui){
						
					},
					stop : function(event, ui){//해당 구획 이동 완료 후 정렬
						
						var checkId = ui.item[0].getAttribute("data-id");
						
						var elementEls = $(this).find(".table_content");
						elementEls = Array.prototype.slice.call(elementEls);
						var index = elementEls.indexOf(ui.item[0]);
						
						var isPrev = false;
						var isNext = false;
						if(typeof elementEls[index-1] != 'undefined'){
							var prevItem = elementEls[index-1].getAttribute("data-id");
							if(prevItem != checkId){
								isPrev = true;
							}
						}else{
							isPrev = true;
						}
						if(typeof elementEls[index+1] != 'undefined'){
							var nextItem = elementEls[index+1].getAttribute("data-id");
							if(nextItem != checkId){
								isNext = true;
							}
						}else{
							isNext = true;
						}
						
						if(isPrev && isNext){
							return false;
						}else{
							//아이디 정렬해주기 위한 idarr 만들기
		 					var idArr = new Array();
							for(var i = 0; i < resultArray.length; i++){
								idArr.push(resultArray[i].id);
							}
							
							//해당 로우의 반복수
							var num = parseInt(ui.item[0].children[3].innerText);
							
							//해당 집구의 테이블 로우 개수
							var standardNum = $(".container1 .table_content").length;
							var checkNum = (num-1) * standardNum;
							
							var targetNum = parseInt(ui.item[0].children[0].innerText)-1+checkNum;
		 					reorder(num, idArr);
		 					var moveNum = parseInt(ui.item[0].children[0].innerText)-1+checkNum;
	
							let item = resultArray.splice(targetNum, 1);
							resultArray.splice(moveNum, 0, item[0]);
							var plusNum = 0;
							for(var i = checkNum; i < (checkNum+standardNum); i++){
								plusNum++;
								resultArray[i].num = plusNum;
								resultArray[i].id = idArr[i];
							}
						//	console.log('sortable', resultArray);
							var getDataArr = [...resultArray];
							getDataResult(getDataArr, grow_type);
						}
						
						
					}
				});//end sortable
			}
			$("#info_popup").css("display", "none");
		}, (duration*1000));
		
		//그룹별 색깔 적용해주기
		$(document).find(".table_content").each(function(i, item){
			
			// var segmentLi = $('#step4-segment > li  .segment');
			// console.log(idArray,'idArray');
			
			/* id_color9 */
			// !important 색상 10개이상 추가시 수정
			if(factor2.length !== 0){
				for(var j = 0; j < idArray.length; j++){
					var colorIndex = j % 10;
					if($(this).hasClass(idArray[j])){
						$(this).addClass("id_color" + (colorIndex + 1));
					}
				}	
			}
	
			
		});
	
	}else if(growType == "3"){
		
		//분할구배치법에서 그룹 나눠줄 id array
		var arr = new Array();
		for(var i = 0; i < resultArray.length; i++){
			arr[i] = resultArray[i].id.split("-")[0]+"-"+resultArray[i].id.split("-")[1];
		}
		var idArray = Array.from(new Set(arr));
		
		var copy_step_4 = $("#copy-step-4");
		
		add_list = "";
		var add_div = "";
		var result_div = "";
		
		for(var i = 0; i < plan_repeat; i++){
			
			add_list += '<div class="copy-title">집구 '+(i+1)+'</div>';
			add_list += '<table id="table2">';
			add_list += '<thead>';
			add_list += '<tr class="title">';
			add_list += '<th>구획번호</th>';
			add_list += '<th>구획 ID</th>';
			add_list += '<th>처리종류</th>';
			add_list += '<th>반복</th>';
			add_list += '<th>처리포장면적(㎡)</th>';
			add_list += '</tr>';
			add_list += '</thead>';
			add_list += '<tbody class="step4-container container'+(i+1)+'">';
			
			for(var j = 0; j < resultArray.length; j++){
				
				if(resultArray[j].repeat == (i+1)){
					var checkId = resultArray[j].id.split("-")[0]+"-"+resultArray[j].id.split("-")[1];
					for(var k = 0; k < idArray.length; k++){
						if(checkId == idArray[k]){
							add_list += '<tr data-id="'+idArray[k]+'" class="table_content '+idArray[k]+'">';
						}
					}
				    add_list += '<td class="table_num">'+resultArray[j].num+'</td>';
				    add_list += '<td>'+resultArray[j].id+'</td>';
				    add_list += '<td>'+resultArray[j].type+'</td>';
				    add_list += '<td>'+resultArray[j].repeat+'</td>';
				    add_list += '<td><input type="text" id="mil" class="seg_area" value="0"></td>';
				    add_list += '</tr>';
				}
			}//end for2
			
			add_list += '</tbody>';
			add_list += '</table>';
			
			result_div += add_list;
			
			add_list = "";
		}//end for1
		
		copy_step_4.empty();
		var start = performance.now();
		copy_step_4.append(result_div);
		var duration = performance.now() - start;
		
		$("#info_popup").css("display", "flex");
		setTimeout(function(){
			//jquery sortable 적용
			for(var i = 0; i < plan_repeat; i++){
				$(document).find(".container"+(i+1)).sortable({
					placeholder : "itemBoxHighlight",
					start : function(event, ui){
						
					},
					stop : function(event, ui){//해당 구획 이동 완료 후 정렬
						
						var checkId = ui.item[0].getAttribute("data-id");
						
						var elementEls = $(this).find(".table_content");
						elementEls = Array.prototype.slice.call(elementEls);
						var index = elementEls.indexOf(ui.item[0]);
						
						var isPrev = false;
						var isNext = false;
						if(typeof elementEls[index-1] != 'undefined'){
							var prevItem = elementEls[index-1].getAttribute("data-id");
							if(prevItem != checkId){
								isPrev = true;
							}
						}else{
							isPrev = true;
						}
						if(typeof elementEls[index+1] != 'undefined'){
							var nextItem = elementEls[index+1].getAttribute("data-id");
							if(nextItem != checkId){
								isNext = true;
							}
						}else{
							isNext = true;
						}
						
						if(isPrev && isNext){
							return false;
						}else{
							
							//아이디 정렬해주기 위한 idarr 만들기
		 					var idArr = new Array();
							for(var i = 0; i < resultArray.length; i++){
								idArr.push(resultArray[i].id);
							}
							
							//해당 로우의 반복수
							var num = parseInt(ui.item[0].children[3].innerText);
							
							//해당 집구의 테이블 로우 개수
							var standardNum = $(".container1 .table_content").length;
							var checkNum = (num-1) * standardNum;
							
							var targetNum = parseInt(ui.item[0].children[0].innerText)-1+checkNum;
		 					reorder(num, idArr);
		 					var moveNum = parseInt(ui.item[0].children[0].innerText)-1+checkNum;

							let item = resultArray.splice(targetNum, 1);
							resultArray.splice(moveNum, 0, item[0]);
							var plusNum = 0;
							for(var i = checkNum; i < (checkNum+standardNum); i++){
								plusNum++;
								resultArray[i].num = plusNum;
								resultArray[i].id = idArr[i];								
							}
							//console.log(resultArray);
							var getDataArr = [...resultArray];
							getDataResult(getDataArr, grow_type);

							
						}//end else
						
					}//end stop

				});//end sortable
			}
			$("#info_popup").css("display", "none");
		}, (duration*1000));
		
		//그룹별 색깔 적용해주기
	
		/* id_color9 */
		/*  ******원래 코드 *******
			for(var j = 0; j < idArray.length; j++){
				if($(this).hasClass(idArray[j])){
					$(this).addClass("id_color" + (j+1));
				}
			}
		*/
		if(factor2.length !== 0){
			$(document).find(".table_content").each(function(i, item){
				for(var j = 0; j < idArray.length; j++){
					var colorIndex = j % 10;
					if($(this).hasClass(idArray[j])){
						$(this).addClass("id_color" + (colorIndex + 1));
					}
				}
			});	
		}
	}	
}
