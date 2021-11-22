/*data 넘기는 형식 *//*
var segmentData = [
{num: 1, id: 'A1-B1-C1-1', type: '60x20,60x25,70x40', repeat: 1, z-index:, sement_aspect: ,segment_horizon: ,segment_vertical: },,
];
*/

// result query
var selectBoxEl = document.querySelector('#segment_option');

// table query
var tableWrap = document.querySelector('#copy-step-4');
var tableEls = document.querySelectorAll('.step4-container .table_content');
var columnBtn = document.querySelector('#step4-columnBtn');
var columnInput = document.querySelector('#mil2');

// segment query
var stepFourWrap = document.getElementById('step4-segment');
var segmentLi = document.querySelectorAll('#step4-segment li');
var segmentEls = document.querySelectorAll('#step4-segment li .segment');
var segmentMove = document.querySelectorAll('#step4-segment li .arrowMove');
var segmentRotate = document.querySelectorAll('#step4-segment li .refresh');

var copyResultArray = new Array();
var factorArray = new Array();

var ArrayNow = [];
var idArrayAll = [];

function getDataResult(getDataArr, type){
	segmentData = getDataArr;
	// segmentInner = inner;
	segmentType = type;
	
	tableEls = document.querySelectorAll('.step4-container .table_content');
	renderSegment(segmentData);
	onColorChange();
}

function onClickColumnBtn(){
	var value = parseInt(columnInput.value, 10);
	if(value !== 0){
		column = columnInput.value;
		renderSegment(segmentData);
	}
}

function onColorChange(result){
	copyResultArray = [];
	factorArray = [];
	
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
		
	if(segmentType == 0){ //완전임의배치******************
		console.log("0입니다");	
	}else if(segmentType == 1){ //난괴법******************
		console.log("1입니다.")
	}else if(segmentType == 2){ //분할구배치법******************
		console.log("2입니다.")
		
		//만들어준 factorArray를 가지고 최종 copyResultArray 만들어 주기
		for(var i = 0; i < plan_repeat; i++){
			level++;
			num1 = 0;
			for(var j = 0; j < factorArray.length; j++){
				num1++;
				var number = num1;
				var data = {"num" : number, "id" : factorArray[j].id+"-"+level, "type" : factorArray[j].type, "repeat" : i+1};
				
				copyResultArray.push(data);
			}
		}//end for
		
		//분할구배치법에서 그룹 나눠줄 id array
		var arr = new Array();
		for(var i = 0; i < copyResultArray.length; i++){
			arr[i] = copyResultArray[i].id.split("-")[0];
		}
		
		var idArray = Array.from(new Set(arr));
		var idArrayNow = "";
		
		for(var j = 0; j < copyResultArray.length; j++){
			var checkId = copyResultArray[j].id.split("-")[0];
			for(var k = 0; k < idArray.length; k++){
				if(checkId == idArray[k]){
					idArrayNow = idArray[k];
					ArrayNow.push(idArrayNow);		
				}
			}
		}
		// color 적용하기	
		idArrayAll.push(idArray);
	}else if(segmentType == 3){ //세세구배치법******************
		console.log("3입니다.");
		
		//만들어준 factorArray를 가지고 최종 copyResultArray 만들어 주기
		for(var i = 0; i < plan_repeat; i++){
			level++;
			num1 = 0;
			for(var j = 0; j < factorArray.length; j++){
				num1++;
				var number = num1;
				var data = {"num" : number, "id" : factorArray[j].id+"-"+level, "type" : factorArray[j].type, "repeat" : i+1};
				
				copyResultArray.push(data);
			}
		}//end for
		
		//분할구배치법에서 그룹 나눠줄 id array
		var arr = new Array();
		for(var i = 0; i < copyResultArray.length; i++){
			arr[i] = copyResultArray[i].id.split("-")[0]+"-"+copyResultArray[i].id.split("-")[1];
		}
		var idArray = Array.from(new Set(arr));
		var idArrayNow = "";
				
		for(var j = 0; j < copyResultArray.length; j++){
			var checkId = copyResultArray[j].id.split("-")[0]+"-"+copyResultArray[j].id.split("-")[1];
			for(var k = 0; k < idArray.length; k++){
				if(checkId == idArray[k]){
					idArrayNow = idArray[k];
					ArrayNow.push(idArrayNow);
				}
			}
		}
		// color 적용하기	
		idArrayAll.push(idArray);
		
	}
}

function segmentComp(data, index){
	var {id, type, repeat} = data;
	var html = '';
	
	
	html += '<li>';
    html += 	'<div class="segment ' + ArrayNow[index] + '" data-segmentid= "'+ ArrayNow[index] + ' ">' + id + '</div>';
	html +=     '<div class="arrowMove">';
	html +=         '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrows-move" viewBox="0 0 16 16">';
	html +=           '<path fill-rule="evenodd" d="M7.646.146a.5.5 0 0 1 .708 0l2 2a.5.5 0 0 1-.708.708L8.5 1.707V5.5a.5.5 0 0 1-1 0V1.707L6.354 2.854a.5.5 0 1 1-.708-.708l2-2zM8 10a.5.5 0 0 1 .5.5v3.793l1.146-1.147a.5.5 0 0 1 .708.708l-2 2a.5.5 0 0 1-.708 0l-2-2a.5.5 0 0 1 .708-.708L7.5 14.293V10.5A.5.5 0 0 1 8 10zM.146 8.354a.5.5 0 0 1 0-.708l2-2a.5.5 0 1 1 .708.708L1.707 7.5H5.5a.5.5 0 0 1 0 1H1.707l1.147 1.146a.5.5 0 0 1-.708.708l-2-2zM10 8a.5.5 0 0 1 .5-.5h3.793l-1.147-1.146a.5.5 0 0 1 .708-.708l2 2a.5.5 0 0 1 0 .708l-2 2a.5.5 0 0 1-.708-.708L14.293 8.5H10.5A.5.5 0 0 1 10 8z"/>';
	html +=         '</svg>';
	html +=     '</div>';
	html +=     '<div class="refresh">';
	html +=         '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-repeat" viewBox="0 0 16 16">';
	html +=           '<path d="M11.534 7h3.932a.25.25 0 0 1 .192.41l-1.966 2.36a.25.25 0 0 1-.384 0l-1.966-2.36a.25.25 0 0 1 .192-.41zm-11 2h3.932a.25.25 0 0 0 .192-.41L2.692 6.23a.25.25 0 0 0-.384 0L.342 8.59A.25.25 0 0 0 .534 9z"/>';
	html +=           '<path fill-rule="evenodd" d="M8 3c-1.552 0-2.94.707-3.857 1.818a.5.5 0 1 1-.771-.636A6.002 6.002 0 0 1 13.917 7H12.9A5.002 5.002 0 0 0 8 3zM3.1 9a5.002 5.002 0 0 0 8.757 2.182.5.5 0 1 1 .771.636A6.002 6.002 0 0 1 2.083 9H3.1z"/>';
	html +=         '</svg>';
	html +=     '</div>';
	html += '</li>';

	return html;
}

var isClick = false;
var isMove = false;
var thisIndex = 0;

var thisX = 0;
var thisY = 0;
var thisRotate = 0;

var offsetX = 0;
var offsetY = 0;

var diffX = 0;
var diffY = 0;

/* 오브젝트와의 차이 */
var obDiffX = 0;
var obDiffY = 0;

/*움직임*/
var posX = 0;
var posY = 0;
var rotate = 0;

/* 마우스 포지션 */
var mX = 0;
var mY = 0;

/*z index */
var zindex = [];

function onClickStepFourWrap(e){
	if(e.target !== e.currentTarget){
		return false;
	}
	
	for(var z = 0; z < segmentEls.length; z++){	
		if(segmentLi[z].classList.contains('active')){
			segmentLi[z].classList.remove('active');
		}
		if(tableEls[z].classList.contains('active')){
			tableEls[z].classList.remove('active');
		}
	}
}

function activeList(){
	for(var z = 0; z < segmentEls.length; z++){	
		if(segmentLi[z].classList.contains('active') && z !== thisIndex){
			segmentLi[z].classList.remove('active');
		}
		if(tableEls[z].classList.contains('active') && z !== thisIndex){
			tableEls[z].classList.remove('active');
		}
		if(z === thisIndex){		
			segmentLi[thisIndex].style.zIndex = segmentLi.length - 1;
		}else{
			segmentLi[z].style.zIndex = z;
		}
	}
	segmentLi[thisIndex].classList.add('active');
	tableEls[thisIndex].classList.add('active');
	
	if(selectBoxEl !== undefined && selectBoxEl !== null){
		selectBoxEl.selectedIndex = thisIndex;
	}
}

function onClickTableEls(e){
	var target = e.currentTarget;
	thisIndex = tableEls.indexOf(target);
	
	activeList();
	
	var segmentLiTop = segmentLi[thisIndex].offsetTop - 64;
	var segmentLiLeft = segmentLi[thisIndex].offsetLeft - 64;
	
	thisTransformGet();
	$('.background-wrap').stop().animate( { scrollTop : thisY } );
	$('.background-wrap').stop().animate( { scrollLeft : thisX } );
}

function thisTransformGet(){
	/*클릭한 개체의 transform 정보를 받아옵니다.*/
	var liTransform = segmentLi[thisIndex].style.transform;
	var elsTransform = segmentEls[thisIndex].style.transform;
	
	var translateValue = liTransform.split('translate')[1];
	var rotateValue = elsTransform.split('rotate')[1];
	
	thisX = parseInt(translateValue.split(',')[0].split('(')[1].split('px')[0], 10);
	thisY = parseInt(translateValue.split(',')[1].split(')')[0].split('px')[0], 10);
	
	thisRotate = parseInt(rotateValue.split('(')[1].split(')')[0].split('deg')[0], 10 );
}

function transformRotateCheck(index){
	var elsTransform = segmentEls[index].style.transform;
	
	if(elsTransform !== undefined && elsTransform !== null && elsTransform !== ''){	
		var rotateValue = elsTransform.split('rotate')[1];
		return parseInt(rotateValue.split('(')[1].split(')')[0].split('deg')[0], 10 );
	}else{
		return undefined;
	}
}

function onElDown(e){
	e.preventDefault();
	isClick = true;
	var target = e.currentTarget;
	thisIndex = segmentEls.indexOf(target);
	
	activeList();
	
	var tableElsTop = tableEls[thisIndex].offsetTop - 64;
	$('#copy-step-4').stop().animate( { scrollTop : tableElsTop } );
	$('#segment_list').stop().animate( { scrollTop : tableElsTop } );
	
	
	thisTransformGet(); // transform 속성 가져오기
}

function transform(){
	if(isClick){	
		if(isMove){
			segmentLi[thisIndex].style.transform = 'translate(' + posX + 'px, ' + posY + 'px)';
		}else{
			segmentEls[thisIndex].style.transform = 'rotate(' + rotate + 'deg)';
		}
	}
	
}

function onMoveDown(e){
	
	isMove = true;
	
	offsetX = stepFourWrap.getBoundingClientRect().left;
	offsetY = stepFourWrap.getBoundingClientRect().top;
	
	obDiffX = segmentLi[thisIndex].getBoundingClientRect().left - segmentMove[thisIndex].getBoundingClientRect().left;
	obDiffY = segmentLi[thisIndex].getBoundingClientRect().top - segmentMove[thisIndex].getBoundingClientRect().top;
	
	diffX = mX - segmentMove[thisIndex].getBoundingClientRect().left - obDiffX;
	diffY = mY - segmentMove[thisIndex].getBoundingClientRect().top - obDiffY;
}

function onRotateDown(e){
	isMove = false;
	thisTransformGet();
	if(thisRotate === 0){
		rotate = 90;
		segmentMove[thisIndex].style.left = '-10px';
		segmentRotate[thisIndex].style.right = '-10px';
	}else{
		rotate = 0;
		segmentMove[thisIndex].style.left = '-40px';
		segmentRotate[thisIndex].style.right = '-40px';
	}
	transform();
}

function onElMove(e){

	if(e.type === 'touchmove'){
        var touch = e.touches[0] || e.changedTouches[0];
        mX = touch.clientX ;
		mY = touch.clientY ;
        posX = mX - offsetX - diffX;
        posY = mY - offsetY - diffY;
    }else{
        mX = e.clientX ;
		mY = e.clientY ;
        posX = mX - offsetX - diffX;
        posY = mY - offsetY - diffY;
    }
	
	if(isMove){
		transform();
	}
}

function onElUp(e){
	isMove = false;
}

var elRender = 0;
var column = 10; // 행수 // 10이 기본값
var row = 0; // 줄수

var elWidth = 106; // element 가로폭
var elHeight = 53; // element 세로폭

var WrapDiff = 46; // wrap diff

function renderSegment(data){
	var dataGet = data;
	onColorChange(dataGet);
	// 초기화
	stepFourWrap.innerHTML = '';
	// 1차 : 컴포넌트 삽입 
	var str = "";
	
	for(var i = 0; i < dataGet.length; i++){
		str += segmentComp(dataGet[i], i);
	}
	stepFourWrap.innerHTML = str;
	segmentSetting(dataGet);
}

function typeSetting(){

}

function segmentSetting(data){
	var dataGet = data;
	// 2차 : query , array 반환, addEvent
	tableEls = document.querySelectorAll('.step4-container .table_content');
	tableEls = Array.prototype.slice.call(tableEls);
	
	segmentLi = document.querySelectorAll('#step4-segment li');
	segmentEls = document.querySelectorAll('#step4-segment li .segment');
	segmentMove = document.querySelectorAll('#step4-segment li .arrowMove');
	segmentRotate = document.querySelectorAll('#step4-segment li .refresh');
	
	segmentLi = Array.prototype.slice.call(segmentLi);
	segmentEls = Array.prototype.slice.call(segmentEls);
	segmentMove = Array.prototype.slice.call(segmentMove);
	segmentRotate = Array.prototype.slice.call(segmentRotate);
	
	// 줄수 계산
	row = Math.ceil(segmentEls.length / column);
	elRender = 0;
	
	// 제일 큰 width 값 elWidth에 넣기
	elWidth = 0;
	for(var i = 0; i < dataGet.length; i++){
		if(elWidth < Math.ceil(segmentEls[i].clientWidth)){
			elWidth = Math.ceil(segmentEls[i].clientWidth);
		}
	}
	
	// step4 wrap width height 세팅
	var wrapWidth = ( column * elWidth ) + ( 46 * 2 );
	var wrapHeight = ( row * elHeight ) + ( 33 * 2 );
	stepFourWrap.style.width = wrapWidth + 'px';
	stepFourWrap.style.height = wrapHeight + 'px';
	
	// 배치 세팅
	if(row === 0){
		row = 1;
	}
	// elwidth 0 이하로 불러올때 대비
	var dataIdLength = 0;
	if(elWidth <= 0){
		for(var i = 0; i < dataGet.length; i++){
			if(dataIdLength < dataGet[i].id.length){
				dataIdLength = dataGet[i].id.length;
			}
		}
		elWidth = (dataIdLength * 10) + 26;
	}
	
	
	if(segmentType !== undefined){
	
		var planRepeat = document.querySelector("#plan_repeat").value;
		
		var type2Diff = 70;
		
	
		if(segmentType == 2){ // ************************분할구배치법
			
			var getDataId = segmentEls[elRender].getAttribute('data-segmentid');
			var getClass = document.getElementsByClassName(getDataId);
		
			var getDataIdLength = Math.ceil(getClass.length / (2 * planRepeat)); // 한 집구의 한 data섹션 갯수
			var getPlanRepeatLength = Math.ceil(copyResultArray.length / planRepeat) // 한 집구당 갯수
			
			var getColumnLength = Math.ceil(getPlanRepeatLength / getDataIdLength ); // 한 집구에 몇개의 data묶음이 있는지,
			console.log(getDataIdLength,"getDataIdLength,row");
			console.log(getColumnLength,"getColumnLength,column");
			
			
			for(var t = 0; t < planRepeat; t++){ // 집구별 반복
				for(var c = 0; c < getColumnLength; c++){ // column
					for(var r = 0; r < getDataIdLength; r++){ // row
						if(segmentLi[elRender] !== undefined && segmentLi[elRender] !== null){
							
							zindex.push(elRender);
							segmentLi[elRender].style.zIndex = elRender;
							segmentLi[elRender].style.transform = 'translate(' + ( ( elWidth * c ) + WrapDiff ) + 'px, ' + ( (elHeight * r) + WrapDiff + (((elHeight * getDataIdLength + type2Diff ) * t) ) ) + 'px)';
							
							segmentEls[elRender].style.width = elWidth + 'px';
							
							
							if(transformRotateCheck(elRender) !== undefined && transformRotateCheck(elRender) !== null){			
								segmentEls[elRender].style.transform = 'rotate(' + transformRotateCheck(elRender) + 'deg)';
							}else{
								segmentEls[elRender].style.transform = 'rotate(' + 0 + 'deg)';
							}
							
							// color 설정
							var idArrayAll2 = idArrayAll[0];
							
							if(idArrayAll2 !== undefined && segmentLi[elRender] !== null){
												
								for(var j = 0; j < idArrayAll2.length; j++){
								var colorIndex = j % 10;
									if(segmentEls[elRender].classList.contains(idArrayAll2[j])){
										segmentEls[elRender].classList.add("id_color" + (colorIndex + 1));
									}
								}
							}
					
							elRender++;
						}
					}
				}
			}
				
		}else if(segmentType == 3){ //*******************세세구배치법
		
			var getDataId = segmentEls[elRender].getAttribute('data-segmentid');
			var getClass = document.getElementsByClassName(getDataId);
		
			console.log(getClass,getClass.length,"getClassLength");

			var getDataIdLength = Math.ceil(getClass.length / (2 * planRepeat)); // 한 집구의 한 data섹션 갯수
			var getPlanRepeatLength = Math.ceil(copyResultArray.length / planRepeat) // 한 집구당 전체 갯수
			
			var getColumnLength = Math.ceil(getPlanRepeatLength / getDataIdLength ); // 한 집구에 몇개의 data묶음이 있는지, 
			
			console.log(getPlanRepeatLength,"getPlanRepeatLength");
			console.log(getDataIdLength,"getDataIdLength");
			console.log(getColumnLength,"getColumnLength");
			
			for(var t = 0; t < planRepeat; t++){
				for(var r = 0; r < getDataIdLength; r++){ // row
					for(var c = 0; c < getColumnLength; c++){ // column
						if(segmentLi[elRender] !== undefined && segmentLi[elRender] !== null){
							zindex.push(elRender);
							segmentLi[elRender].style.zIndex = elRender;
							segmentLi[elRender].style.transform = 'translate(' + ( ( elWidth * c ) + WrapDiff ) + 'px, ' + ( (elHeight * r) + WrapDiff + ( elHeight * getDataIdLength + type2Diff ) * t ) + 'px)';
							segmentEls[elRender].style.width = elWidth + 'px';
							
							
							if(transformRotateCheck(elRender) !== undefined && transformRotateCheck(elRender) !== null){			
								segmentEls[elRender].style.transform = 'rotate(' + transformRotateCheck(elRender) + 'deg)';
							}else{
								segmentEls[elRender].style.transform = 'rotate(' + 0 + 'deg)';
							}
							
							// color 설정
							var idArrayAll2 = idArrayAll[0];
							
							if(idArrayAll2 !== undefined && segmentLi[elRender] !== null){
												
								for(var j = 0; j < idArrayAll2.length; j++){
								var colorIndex = j % 10;
									if(segmentEls[elRender].classList.contains(idArrayAll2[j])){
										segmentEls[elRender].classList.add("id_color" + (colorIndex + 1));
									}
								}
							}
					
							elRender++;
						}
					}
				}
			}
			
		}else{ //*******************나머지(완전임의배치, 난괴법)
			for(var r = 0; r < row; r++){
				for(var c = 0; c < column; c++){
					if(segmentLi[elRender] !== undefined && segmentLi[elRender] !== null){
						zindex.push(elRender);
						segmentLi[elRender].style.zIndex = elRender;
						segmentLi[elRender].style.transform = 'translate(' + ( ( elWidth * c ) + WrapDiff ) + 'px, ' + ( (elHeight * r) + WrapDiff ) + 'px)';
						segmentEls[elRender].style.width = elWidth + 'px';
						
						
						if(transformRotateCheck(elRender) !== undefined && transformRotateCheck(elRender) !== null){			
							segmentEls[elRender].style.transform = 'rotate(' + transformRotateCheck(elRender) + 'deg)';
						}else{
							segmentEls[elRender].style.transform = 'rotate(' + 0 + 'deg)';
						}
						
						// color 설정
						var idArrayAll2 = idArrayAll[0];
						
						if(idArrayAll2 !== undefined && segmentLi[elRender] !== null){
											
							for(var j = 0; j < idArrayAll2.length; j++){
							var colorIndex = j % 10;
								if(segmentEls[elRender].classList.contains(idArrayAll2[j])){
									segmentEls[elRender].classList.add("id_color" + (colorIndex + 1));
								}
							}
						}
				
						elRender++;
					}
				}
			}
		}
	}else{
		for(var r = 0; r < row; r++){
			for(var c = 0; c < column; c++){
				if(segmentLi[elRender] !== undefined && segmentLi[elRender] !== null){
					zindex.push(elRender);
					segmentLi[elRender].style.zIndex = elRender;
					segmentLi[elRender].style.transform = 'translate(' + ( ( elWidth * c ) + WrapDiff ) + 'px, ' + ( (elHeight * r) + WrapDiff ) + 'px)';
					segmentEls[elRender].style.width = elWidth + 'px';
					
					
					if(transformRotateCheck(elRender) !== undefined && transformRotateCheck(elRender) !== null){			
						segmentEls[elRender].style.transform = 'rotate(' + transformRotateCheck(elRender) + 'deg)';
					}else{
						segmentEls[elRender].style.transform = 'rotate(' + 0 + 'deg)';
					}
					
					// color 설정
					var idArrayAll2 = idArrayAll[0];
					
					if(idArrayAll2 !== undefined && segmentLi[elRender] !== null){
										
						for(var j = 0; j < idArrayAll2.length; j++){
						var colorIndex = j % 10;
							if(segmentEls[elRender].classList.contains(idArrayAll2[j])){
								segmentEls[elRender].classList.add("id_color" + (colorIndex + 1));
							}
						}
					}
			
					elRender++;
				}
			}
		}
	}
	
	
	
	for(var t = 0; t < segmentEls.length; t++){
		segmentEls[t].addEventListener('mousedown', onElDown);
		//segmentEls[t].addEventListener('touchstart', onElDown);
		
		segmentMove[t].addEventListener('mousedown', onMoveDown);
		//segmentMove[t].addEventListener('touchstart', onMoveDown);
		
		segmentRotate[t].addEventListener('click', onRotateDown);
		
		if(tableEls !== undefined){
			tableEls[t].addEventListener('click', onClickTableEls);
		}
	}
	
}

function addWindowEvent(){
	window.addEventListener('mousemove', onElMove);
	window.addEventListener('mouseup', onElUp);
	
	window.addEventListener('touchmove', onElMove);
	window.addEventListener('touchend', onElUp);
	
	stepFourWrap.addEventListener('mousedown', onClickStepFourWrap);
	stepFourWrap.addEventListener('touchstart', onClickStepFourWrap);
	
	if(columnBtn !== undefined && columnBtn !== null){
		
		columnBtn.addEventListener('click', onClickColumnBtn);
	}
}

addWindowEvent();



