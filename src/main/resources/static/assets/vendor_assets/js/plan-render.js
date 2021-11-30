console.log('render load');

function segmentTextLeft(data){
	var segmentEls = document.querySelectorAll('.table_id');
	var segmentElsType = document.querySelectorAll('.table_type');
	var segmentElsId = document.querySelectorAll(".table_repeat");
	var growType = document.querySelector("#grow_type").getAttribute("data-select");
	

	
	if(segmentEls.length > 0 && segmentEls !== undefined && segmentEls !== null){

		for(var i = 0; i < data.length; i++){
			segmentEls[i].innerText = data[i].id;
			segmentElsType[i].innerText = data[i].type;
			if(growType && growType == 0 && segmentElsId){
					segmentElsId[i].innerText = data[i].repeat;	// 완전임의배치일 경우
			}
		
			

		}
	}
}

function reRenderTableComp(type, segmentInnerType){

	var dataGet = [...resultArray];
	
	if(segmentInnerType == 'innerText'){
		segmentTextLeft(dataGet);

	}
}
