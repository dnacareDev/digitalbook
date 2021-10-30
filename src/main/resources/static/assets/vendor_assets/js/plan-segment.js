console.log('연결 성공');

/*data 넘기는 형식 *//*
var segmentData = [
{num: 1, id: 'A1-B1-C1-1', type: '60x20,60x25,70x40', repeat: 1, z-index:, sement_aspect: ,segment_horizon: ,segment_vertical: },,
];
*/

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

function getDataResult(getDataArr){
	segmentData = getDataArr;
	console.log(getDataArr);
	tableEls = document.querySelectorAll('.step4-container .table_content');
	renderSegment(segmentData);
}

function onClickColumnBtn(){
	var value = parseInt(columnInput.value, 10);
	if(value !== 0){
		column = columnInput.value;
		renderSegment(segmentData);
	}
}



function segmentComp(data){
	var {id, type, repeat} = data;
	
	var html = '';

	html += '<li>';
	html += 	'<div class="segment">' + id + '</div>';
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
	console.log(elsTransform);
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
	
	
	var tableElsTop = tableEls[thisIndex].offsetTop - 64;
	$('#copy-step-4').stop().animate( { scrollTop : tableElsTop } );
	
	
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
	// 초기화
	stepFourWrap.innerHTML = '';
	// 1차 : 컴포넌트 삽입
	for(var i = 0; i < dataGet.length; i++){
		stepFourWrap.innerHTML += segmentComp(dataGet[i]);
	}
	// 2차 : query , array 반환, addEvent
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
	for(var i = 0; i < dataGet.length; i++){
		elWidth = Math.ceil(segmentEls[i].clientWidth);
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
				elRender++;
			}
		}
	}
	
	for(var t = 0; t < segmentEls.length; t++){
		segmentEls[t].addEventListener('mousedown', onElDown);
		segmentEls[t].addEventListener('touchstart', onElDown);
		
		segmentMove[t].addEventListener('mousedown', onMoveDown);
		segmentMove[t].addEventListener('touchstart', onMoveDown);
		
		segmentRotate[t].addEventListener('click', onRotateDown);
	}
}


function addWindowEvent(){
	window.addEventListener('mousemove', onElMove);
	window.addEventListener('mouseup', onElUp);
	
	window.addEventListener('touchmove', onElMove);
	window.addEventListener('touchend', onElUp);
	
	stepFourWrap.addEventListener('mousedown', onClickStepFourWrap);
	stepFourWrap.addEventListener('touchstart', onClickStepFourWrap);
	
	columnBtn.addEventListener('click', onClickColumnBtn);
}

addWindowEvent();





