/*data 넘기는 형식 *//*
var segmentData = [
{num: 1, id: 'A1-B1-C1-1', type: '60x20,60x25,70x40', segment_rotate: ,segment_x: ,segment_y: },,
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

var segmentData = [];
var copyResultArray = new Array();
var factorArray = new Array();

var ArrayNow = [];
var idArrayAll = [];

var factorLength1 = 0;
var factorLength2 = 0;
var factorLength3 = 0;

var segmentType = 0;
var segmentInnerType = '';
var segmentModify = "";

function getDataResult(getDataArr, type, innerType, modify){
	segmentData = getDataArr;
	segmentType = type;
	segmentInnerType = innerType;
	segmentModify = modify;
	tableEls = document.querySelectorAll('.step4-container .table_content');
	renderSegment(segmentData);
	
	var btnLeft = document.querySelector(".top_btn-left");
	
	
	console.log(segmentType,"segmentType");
	if(btnLeft){
		if(segmentType == 2 || segmentType == 3){
			btnLeft.style.display = "none";
			console.log("test");
		}else{
				btnLeft.style.display = "block";
		}
	}
	
}

function onClickColumnBtn(){
	var value = parseInt(columnInput.value, 10);
	if(value !== 0){
		column = columnInput.value;
		segmentData = resultArray;
		renderSegment(segmentData);
	}
}


function segmentText(data){
	var segmentEls = document.querySelectorAll('.segment');
	
	if(segmentEls.length > 0 && segmentEls !== undefined && segmentEls !== null){	
		for(var i = 0; i < data.length; i++){
			if(data[i].segment_title === ""){
				segmentEls[i].innerText = data[i].segmentId;
			}else{
				segmentEls[i].innerText = data[i].segment_title;
			}
			
			var segmentLis = segmentEls[i].parentNode.getAttribute("style","transform");
			var segmentLisP = segmentEls[i].getAttribute("style","transform");
			var segmnetLisTrans = segmentLis.substring(segmentLis.indexOf("(")+1,segmentLis.indexOf(")"));
			var segmentLisPRotate = segmentLisP.substring(segmentLisP.indexOf("(")+1,segmentLisP.indexOf(")"));
		
			var segX = segmnetLisTrans.split(",")[0].split("px")[0];
			var segY = segmnetLisTrans.split(",")[1].split("px")[0];
			var segR = segmentLisPRotate.split("deg")[0];
			
			resultArray[i].segment_horizon = segX;
			resultArray[i].segment_vertical = segY;
			resultArray[i].segment_aspect = segR;
		}
	}
}

function setFactorData(data){
	var arr = data;
	var aGet = 1;
	var bGet = 1;
	var cGet = 1;
	
	for(var i = 0; i < arr.length; i++){
		
				
	/*
		var aSplit = arr[i].id.split("-").length > 0 ? arr[i].id.split("-")[0].replace(/[^0-9]/g,'') :  //arr[i].id.split("-B")[0].split("A")[1];
		var bSplit = //arr[i].id.split("-C")[0].split("B")[1];
		var cSplit = //arr[i].id.split("-C")[1].split("-")[0];
	*/
	
		let splitStr = arr[i].id.split("-");
	
		var aSplit = splitStr.length > 0 ? splitStr[0].replace(/[^0-9]/g,'') : 0;
		var bSplit = splitStr.length > 1 ? splitStr[1].replace(/[^0-9]/g,'') : 0;
		var cSplit = splitStr.length > 2 ? splitStr[2].replace(/[^0-9]/g,'') : 0;

		
		if(aSplit > aGet){
			aGet = aSplit;
		}
		if(bSplit > bGet){
			bGet = bSplit;
		}
		if(cSplit > cGet){
			cGet = cSplit;
		}
	}
	factorLength1 = aGet;
	factorLength2 = bGet;
	factorLength3 = cGet;
}

function setColorSegment(data){
	
	var arr = data;
   //id_color1 ~ 10
	var percentage = 0;
	var orderRen = 0;
	var allRen = 0;
	var repeat = 0;
		
	var re = 1;
	
	for(var y = 0; y < arr.length; y++){
		var ySplit = arr[y].repeat;
		
		if(ySplit > re){
			re = ySplit;
		}
	}
	repeat = re;
	
	if(segmentType == "2"){
	
		percentage = factorLength1; 
		for(var f = 0; f < repeat; f++){
			for(var i = 0; i < percentage; i++){
				for(var t =  0; t < (factorLength2 * factorLength3); t++){
					var colorIndex = orderRen % 10;
					segmentEls[allRen].classList.add("id_color"+ (colorIndex + 1));
					allRen++;
				}	
				orderRen++;				
			}
			orderRen = 0;
			//console.log(orderRen,"orderRen");
		}
		
	}else if(segmentType == "3"){
		percentage = factorLength1 * factorLength2; 
		for(var f = 0; f < repeat; f++){
			for(var i = 0; i < percentage; i++){
				for(var t =  0; t < factorLength3; t++){
					var colorIndex = orderRen % 10;
					segmentEls[allRen].classList.add("id_color"+ (colorIndex + 1));
					allRen++;
				}
				orderRen++;
			}
			orderRen = 0;
			//console.log(orderRen,"orderRen");
		}
		
	}

}

function segmentComp(data, index){
	var {id, type, repeat, segmentId, segment_title, order} = data;
	var html = '';

	
	html += '<li>';
	if(segment_title === ""){
		html += 	'<div class="segment" data-segmentid= "'+ ArrayNow[index] + ' ">' + segmentId + '</div>';
	}else{
		html += 	'<div class="segment" data-segmentid= "'+ ArrayNow[index] + ' ">' + segment_title + '</div>';
	}
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

function activeList(thisOrder){
	
	var thisZindex = parseInt(segmentLi[thisIndex].style.zIndex, 10);
	var zArr = [];
	for(var z = 0; z < segmentEls.length; z++){	
		if(segmentLi[z].classList.contains('active') && z !== thisOrder){
			segmentLi[z].classList.remove('active');
		}
		if(tableEls[z].classList.contains('active') && z !== thisIndex){
			tableEls[z].classList.remove('active');
		}
		var allZIndex = parseInt(segmentLi[z].style.zIndex, 10);
		var temp = "";
		if(allZIndex < thisZindex){
			temp = allZIndex;
		}else if(allZIndex > thisZindex){
			temp = (allZIndex - 1);
		}else if(allZIndex === thisZindex){
			temp = (segmentEls.length - 1);
		}
		zArr.push(temp);	
	}
	for(var z = 0; z < segmentEls.length; z++){
		segmentLi[z].style.zIndex = zArr[z];
		resultArray[z].segment_zindex = zArr[z];
	}
	segmentLi[thisIndex].classList.add('active');
	tableEls[thisIndex].classList.add('active');
	
	if(selectBoxEl !== undefined && selectBoxEl !== null){
		selectBoxEl.selectedIndex = thisIndex;
	}
}

function onClickTableEls(e){
	var target = e.currentTarget;
	tableEls = document.querySelectorAll('.step4-container .table_content');
	tableEls = Array.prototype.slice.call(tableEls);
	
	thisIndex = tableEls.indexOf(target);
	
	var thisOrder = segmentData[thisIndex].order;
	
	activeList(thisOrder);
	
	var liTransform = segmentLi[thisOrder].style.transform;
	var translateValue = liTransform.split('translate')[1];
	
	var toScrollX = parseInt(translateValue.split(',')[0].split('(')[1].split('px')[0], 10);
	var toScrollY = parseInt(translateValue.split(',')[1].split(')')[0].split('px')[0], 10);

	$('.background-wrap').stop().animate( { scrollLeft : toScrollX } );
	$('.background-wrap').stop().animate( { scrollTop : toScrollY } );
}

function thisTransformGet(thisOrder){
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
			resultArray[thisIndex].segment_horizon = parseInt(posX, 10);
			resultArray[thisIndex].segment_vertical = parseInt(posY, 10);
		}else{
			segmentEls[thisIndex].style.transform = 'rotate(' + rotate + 'deg)';
			resultArray[thisIndex].segment_aspect = rotate;
		}
	}
}

function onMoveDown(e){
	
	var target = e.currentTarget;
	
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

	//console.log(data,"data");
	// 초기화
	copyResultArray = [];
	factorArray = [];
	
	ArrayNow = [];
	idArrayAll = [];
	
	var dataGet = data;
	// 초기화
	
	// 1차 : 컴포넌트 삽입 
	var compData = "";
	var textData = "";
	//onColorChange(dataGet);
	setFactorData(dataGet);
	
	
	if(segmentInnerType === 'innerText'){
		segmentText(dataGet);
		segmentInnerType = "";
	}else{
		stepFourWrap.innerHTML = '';
		for(var i = 0; i < dataGet.length; i++){
			compData += segmentComp(dataGet[i], i);
		}
		stepFourWrap.innerHTML = compData;
		segmentSetting(dataGet);
		setColorSegment(dataGet);
	}
}

function segmentSetting(data){

	var dataGet = data;
	// console.log(dataGet,"data");
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
	elWidth = 0;
	for(var i = 0; i < dataGet.length; i++){
		if(dataIdLength < dataGet[i].id.length){
			dataIdLength = dataGet[i].id.length;
		}
	}
	elWidth = (dataIdLength * 10) + 26;
	
	if(segmentType !== undefined){
		var planRepeat = document.querySelector("#plan_repeat");
		
		if(planRepeat == undefined){
			planRepeat = dataGet[dataGet.length - 1].repeat;
		}else{
			planRepeat = parseInt(document.querySelector("#plan_repeat").value, 10);
		}
		
		var type2Diff = 70;
		if(segmentType == 2){ // ************************분할구배치법
		//console.log("test3");
			
			var getDataId = segmentEls[elRender].getAttribute('data-segmentid');
			var getClass = document.getElementsByClassName(getDataId);			
			
			var getDataIdLength = 0; // 한 집구의 한 data섹션 갯수
			console.log("dataGetLength:",dataGet.length);
			for(var i = 0 ; i < dataGet.length; i++){
				var idTemp = dataGet[i].id;
				var splitOne = parseInt(dataGet[0].id.split("-")[0].split("A")[1], 10);
				var splitA = parseInt(idTemp.split("-")[0].split("A")[1], 10);

				if(splitOne === splitA){
					getDataIdLength++;
				}else{
					break;
				}
			}	
			var getPlanRepeatLength = Math.ceil(dataGet.length / planRepeat) // 한 집구당 갯수
			var getColumnLength = getPlanRepeatLength / getDataIdLength ; // 한 집구에 몇개의 data묶음이 있는지,
			if(factorLength3 == 0){
				factorLength3 = 1
			}
			
			for(var t = 0; t < planRepeat; t++){ // 집구별 반복
				for(var c = 0; c < getColumnLength; c++){ // column
					for(var r = 0; r < getDataIdLength; r++){ // row
						if(segmentLi[elRender] !== undefined && segmentLi[elRender] !== null){
							segmentEls[elRender].style.width = elWidth + 'px';
							
							if(segmentModify !== "modify" && segmentModify !== "result"){	
							console.log("resrthis");					
								zindex.push(elRender);
								segmentLi[elRender].style.zIndex = elRender;
								segmentLi[elRender].style.transform = 'translate(' + ( ( elWidth * c ) + WrapDiff ) + 'px, ' + ( (elHeight * r) + WrapDiff + (((elHeight * getDataIdLength + type2Diff ) * t) ) ) + 'px)';
								
								
								
								
								if(transformRotateCheck(elRender) !== undefined && transformRotateCheck(elRender) !== null){			
									segmentEls[elRender].style.transform = 'rotate(' + transformRotateCheck(elRender) + 'deg)';
								}else{
									segmentEls[elRender].style.transform = 'rotate(' + 0 + 'deg)';
								}
								
								dataGet[elRender].segment_horizon = ( ( elWidth * c ) + WrapDiff );
								dataGet[elRender].segment_vertical = ( (elHeight * r) + WrapDiff + (((elHeight * getDataIdLength + type2Diff ) * t) ) );
								dataGet[elRender].segment_zindex = elRender;
							}else{
							console.log("test10");
								segmentLi[elRender].style.transform = 'translate(' + dataGet[elRender].segment_horizon + 'px, ' + dataGet[elRender].segment_vertical + 'px)';
								segmentLi[elRender].style.zIndex = dataGet[elRender].segment_zindex;
								
								segmentEls[elRender].style.zIndex = dataGet[elRender].segment_zindex;
								segmentEls[elRender].style.transform = 'rotate(' + dataGet[elRender].segment_aspect + 'deg)';
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
			//console.log("tptptp");
			var getDataId = segmentEls[elRender].getAttribute('data-segmentid');
			var getClass = document.getElementsByClassName(getDataId);

			var getDataIdLength = 0; // 한 집구의 한 data섹션 갯수
			//console.log("dataGetLength:",dataGet.length);
			for(var i = 0 ; i < dataGet.length; i++){
				var idTemp = dataGet[i].id;
				var splitOne = parseInt(dataGet[0].id.split("-")[0].split("A")[1], 10);
				var splitA = parseInt(idTemp.split("-")[0].split("A")[1], 10);

				if(splitOne === splitA){
					getDataIdLength++;
				}else{
					break;
				}
			}	
			var getPlanRepeatLength = Math.ceil(dataGet.length / planRepeat) // 한 집구당 갯수
			var getColumnLength = getPlanRepeatLength / getDataIdLength ; // 한 집구에 몇개의 data묶음이 있는지,

			if(factorLength3 == 0){
				factorLength3 = 1
			}
			//copyResultArray.push(dataGet);
			//console.log(copyResultArray,dataGet,"this");
			
			if(factorLength2 !== 0){
				for(var t = 0; t < planRepeat; t++){
					for(var s = 0; s < factorLength1; s++){
						for(var r = 0; r < factorLength2; r++){
							for(var q = 0 ; q < factorLength3; q++){
								if(segmentLi[elRender] !== undefined && segmentLi[elRender] !== null){
									//console.log(factorLength1,"factorLength1",factorLength2,"factorLength2",factorLength3,"factorLength3");
									segmentEls[elRender].style.width = elWidth + 'px';
									
									if(segmentModify !== "modify" && segmentModify !== "result"){
										zindex.push(elRender);
										segmentLi[elRender].style.zIndex = elRender;
										segmentLi[elRender].style.transform = 'translate(' + ((elWidth * q) + ((elWidth * factorLength3) * s) + WrapDiff + 'px, ' + ( ((elHeight * r) + WrapDiff) + ((elHeight * factorLength2 + type2Diff) * t ) )) + 'px)';
										
										
										if(transformRotateCheck(elRender) !== undefined && transformRotateCheck(elRender) !== null){			
											segmentEls[elRender].style.transform = 'rotate(' + transformRotateCheck(elRender) + 'deg)';
										}else{
											segmentEls[elRender].style.transform = 'rotate(' + 0 + 'deg)';
										}
										
										dataGet[elRender].segment_horizon = ((elWidth * q) + ((elWidth * factorLength3) * s) + WrapDiff);
										dataGet[elRender].segment_vertical = ( ((elHeight * r) + WrapDiff) + ((elHeight * factorLength2 + type2Diff) * t ) );
										dataGet[elRender].segment_zindex = elRender;
									}else{
										segmentLi[elRender].style.transform = 'translate(' + dataGet[elRender].segment_horizon + 'px, ' + dataGet[elRender].segment_vertical + 'px)';
										segmentLi[elRender].style.zIndex = dataGet[elRender].segment_zindex;
										
										segmentEls[elRender].style.zIndex = dataGet[elRender].segment_zindex;
										segmentEls[elRender].style.transform = 'rotate(' + dataGet[elRender].segment_aspect + 'deg)';
									}
									
									/*var colorIndex = (s*r) % 10;
									//console.log(colorIndex,"colorIndex");
									if(factorLength2 !== 0){
										segmentEls[elRender].classList.add("id_color" + (colorIndex + 1));
									}*/
									
									// color 설정
									var idArrayAll2 = idArrayAll[0];
									if(idArrayAll2 !== undefined && segmentLi[elRender] !== null){					
										for(var j = 0; j < idArrayAll2.length; j++){
											var colorIndex = j % 10;
												if(segmentEls[elRender].classList.contains(idArrayAll2[j]) && factorLength2 !== 0){
													segmentEls[elRender].classList.add("id_color" + (colorIndex + 1));
												}
											}
										}
									elRender++;
									}
								}
							}
						}
					}
			}else{
				var getDataId = segmentEls[elRender].getAttribute('data-segmentid');
				var getClass = document.getElementsByClassName(getDataId);

				var getDataIdLength = 0; // 한 집구의 한 data섹션 갯수
				for(var i = 0 ; i < dataGet.length; i++){
					var idTemp = dataGet[i].id;
					var splitOne = parseInt(dataGet[0].id.split("-")[0].split("A")[1], 10);
					var splitA = parseInt(idTemp.split("-")[0].split("A")[1], 10);

					if(splitOne === splitA){
						getDataIdLength++;
					}else{
						break;
					}
				}	
				var getPlanRepeatLength = Math.ceil(dataGet.length / planRepeat) // 한 집구당 갯수
				var getColumnLength = getPlanRepeatLength / getDataIdLength ; // 한 집구에 몇개의 data묶음이 있는지,
				if(factorLength3 == 0){
					factorLength3 = 1
				}
				//copyResultArray.push(dataGet);
				//console.log(copyResultArray,dataGet,"this");
			
				for(var t = 0; t < planRepeat; t++){
					for(var c = 0; c < getColumnLength; c++){
						for(var r = 0; r < getDataIdLength; r++){
							if(segmentLi[elRender] !== undefined && segmentLi[elRender] !== null){
							
								segmentEls[elRender].style.width = elWidth + 'px';
								if(segmentModify !== "modify" && segmentModify !== "result"){
									zindex.push(elRender);
									segmentLi[elRender].style.zIndex = elRender;
									segmentLi[elRender].style.transform = 'translate(' + ( ( elWidth * c ) + WrapDiff ) + 'px, ' + ( (elHeight * r) + WrapDiff + (((elHeight * getDataIdLength + type2Diff ) * t) ) ) + 'px)';
									
									dataGet[elRender].segment_horizon = ( ( elWidth * c ) + WrapDiff );
									dataGet[elRender].segment_vertical = ( (elHeight * r) + WrapDiff + (((elHeight * getDataIdLength + type2Diff ) * t) ) );
									dataGet[elRender].segment_zindex = elRender;
									
									if(transformRotateCheck(elRender) !== undefined && transformRotateCheck(elRender) !== null){			
										segmentEls[elRender].style.transform = 'rotate(' + transformRotateCheck(elRender) + 'deg)';
									}else{
										segmentEls[elRender].style.transform = 'rotate(' + 0 + 'deg)';
									}
								}else{
									segmentLi[elRender].style.transform = 'translate(' + dataGet[elRender].segment_horizon + 'px, ' + dataGet[elRender].segment_vertical + 'px)';
									segmentLi[elRender].style.zIndex = dataGet[elRender].segment_zindex;
									
									segmentEls[elRender].style.zIndex = dataGet[elRender].segment_zindex;
									segmentEls[elRender].style.transform = 'rotate(' + dataGet[elRender].segment_aspect + 'deg)';
								}
								
								
								
								elRender++;
							}
						}
					}
				}
			}
			
			
		}else{ //*******************나머지(완전임의배치법, 난괴법)
			for(var r = 0; r < row; r++){
				for(var c = 0; c < column; c++){
					if(segmentLi[elRender] !== undefined && segmentLi[elRender] !== null){
					
						segmentEls[elRender].style.width = elWidth + 'px';
						
						if(segmentModify !== "modify" && segmentModify !== "result"){
						
							zindex.push(elRender);
							segmentLi[elRender].style.zIndex = elRender;
							segmentLi[elRender].style.transform = 'translate(' + ( ( elWidth * c ) + WrapDiff ) + 'px, ' + ( (elHeight * r) + WrapDiff ) + 'px)';
							
							dataGet[elRender].segment_horizon = ( ( elWidth * c ) + WrapDiff );
							dataGet[elRender].segment_vertical = ( (elHeight * r) + WrapDiff );
							dataGet[elRender].segment_zindex = elRender;
							
							if(transformRotateCheck(elRender) !== undefined && transformRotateCheck(elRender) !== null){			
								segmentEls[elRender].style.transform = 'rotate(' + transformRotateCheck(elRender) + 'deg)';
							}else{
								segmentEls[elRender].style.transform = 'rotate(' + 0 + 'deg)';
							}
						}else{
							segmentLi[elRender].style.transform = 'translate(' + dataGet[elRender].segment_horizon + 'px, ' + dataGet[elRender].segment_vertical + 'px)';
							segmentLi[elRender].style.zIndex = dataGet[elRender].segment_zindex;
							
							segmentEls[elRender].style.zIndex = dataGet[elRender].segment_zindex;
							segmentEls[elRender].style.transform = 'rotate(' + dataGet[elRender].segment_aspect + 'deg)';
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
				
					segmentEls[elRender].style.width = elWidth + 'px';
					
					if(segmentModify !== "modify" && segmentModify !== "result"){					
						zindex.push(elRender);
						segmentLi[elRender].style.zIndex = elRender;
						segmentLi[elRender].style.transform = 'translate(' + ( ( elWidth * c ) + WrapDiff ) + 'px, ' + ( (elHeight * r) + WrapDiff ) + 'px)';
						
						dataGet[elRender].segment_horizon = ( ( elWidth * c ) + WrapDiff );
						dataGet[elRender].segment_vertical = ( (elHeight * r) + WrapDiff );
						dataGet[elRender].segment_zindex = elRender;
						
						if(transformRotateCheck(elRender) !== undefined && transformRotateCheck(elRender) !== null){			
							segmentEls[elRender].style.transform = 'rotate(' + transformRotateCheck(elRender) + 'deg)';
						}else{
							segmentEls[elRender].style.transform = 'rotate(' + 0 + 'deg)';
						}
					}else{
						segmentLi[elRender].style.transform = 'translate(' + dataGet[elRender].segment_horizon + 'px, ' + dataGet[elRender].segment_vertical + 'px)';
						segmentLi[elRender].style.zIndex = dataGet[elRender].segment_zindex;
						
						segmentEls[elRender].style.zIndex = dataGet[elRender].segment_zindex;
						segmentEls[elRender].style.transform = 'rotate(' + dataGet[elRender].segment_aspect + 'deg)';
					}
					
					elRender++;
				}
			}
		}
	}
	
	for(var t = 0; t < segmentEls.length; t++){
		segmentEls[t].addEventListener('mousedown', onElDown);
		//segmentEls[t].addEventListener('touchstart', onElDown);
		
		if(segmentModify !== "result"){
			segmentMove[t].addEventListener('mousedown', onMoveDown);
			//segmentMove[t].addEventListener('touchstart', onMoveDown);
			
			segmentRotate[t].addEventListener('click', onRotateDown);		
		}else{
			segmentMove[t].style.display = "none !important";
			
			segmentRotate[t].style.display = "none !important";
		}
		
		if(tableEls[t] !== undefined){
			tableEls[t].addEventListener('click', onClickTableEls);
		}
	}
	
	resultArray = dataGet;
	
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



