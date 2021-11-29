console.log('render load');

function segmentTextLeft(data){
	var segmentEls = document.querySelectorAll('.table_id');
	var segmentElsType = document.querySelectorAll('.table_type');
	
	if(segmentEls.length > 0 && segmentEls !== undefined && segmentEls !== null){

		for(var i = 0; i < data.length; i++){
			segmentEls[i].innerText = data[i].segmentId;
			segmentElsType[i].innerText = data[i].type;
		}
	}
}

function reRenderTableComp(type, segmentInnerType){

	var dataGet = [...resultArray];
	
	if(segmentInnerType == 'innerText'){
		segmentTextLeft(dataGet);

	}
}
