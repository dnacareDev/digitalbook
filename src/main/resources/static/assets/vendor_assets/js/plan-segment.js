console.log('연결 성공');

var dummy = [
{num: 1, id: 'A-a-X', type: '60x10,70x15,32', repeat: 1},
{num: 2, id: 'A-a-Y', type: '60x10,70x15,32', repeat: 1},
{num: 3, id: 'A-a-Z', type: '60x10,70x15,32', repeat: 1},
{num: 4, id: 'A-b-X', type: '60x10,70x15,32', repeat: 1},
{num: 5, id: 'A-b-Y', type: '60x10,70x15,32', repeat: 1},
{num: 6, id: 'A-b-Z', type: '60x10,70x15,32', repeat: 1},
{num: 7, id: 'B-a-X', type: '60x10,70x15,32', repeat: 1},
{num: 8, id: 'B-a-Y', type: '60x10,70x15,32', repeat: 1},
{num: 9, id: 'B-a-Z', type: '60x10,70x15,32', repeat: 1},
{num: 10, id: 'B-b-X', type: '60x10,70x15,32', repeat: 1},
{num: 11, id: 'B-b-Y', type: '60x10,70x15,32', repeat: 1},
{num: 12, id: 'B-b-Z', type: '60x10,70x15,32', repeat: 1},
{num: 13, id: 'C-a-X', type: '60x10,70x15,32', repeat: 1},
{num: 14, id: 'C-a-Y', type: '60x10,70x15,32', repeat: 1},
{num: 15, id: 'C-a-Z', type: '60x10,70x15,32', repeat: 1},
{num: 16, id: 'C-b-X', type: '60x10,70x15,32', repeat: 1},
{num: 17, id: 'C-b-Y', type: '60x10,70x15,32', repeat: 1},
{num: 18, id: 'C-b-Z', type: '60x10,70x15,32', repeat: 1},
{num: 19, id: 'A-a-X', type: '60x10,70x15,32', repeat: 2},
{num: 20, id: 'A-a-Y', type: '60x10,70x15,32', repeat: 2},
{num: 21, id: 'A-a-Z', type: '60x10,70x15,32', repeat: 2},
{num: 22, id: 'A-b-X', type: '60x10,70x15,32', repeat: 2},
{num: 23, id: 'A-b-Y', type: '60x10,70x15,32', repeat: 2},
{num: 24, id: 'A1-b1-Z1', type: '60x10,70x15,32', repeat: 2},
{num: 25, id: 'B-a-X', type: '60x10,70x15,32', repeat: 2},
{num: 26, id: 'B-a-Y', type: '60x10,70x15,32', repeat: 2},
{num: 27, id: 'B-a-Z', type: '60x10,70x15,32', repeat: 2},
{num: 28, id: 'B-b-X', type: '60x10,70x15,32', repeat: 2},
{num: 29, id: 'B-b-Y', type: '60x10,70x15,32', repeat: 2},
{num: 30, id: 'B-b-Z', type: '60x10,70x15,32', repeat: 2},
{num: 31, id: 'C-a-X', type: '60x10,70x15,32', repeat: 2},
{num: 32, id: 'C-a-Y', type: '60x10,70x15,32', repeat: 2},
{num: 33, id: 'C-a-Z', type: '60x10,70x15,32', repeat: 2},
{num: 34, id: 'C-b-X', type: '60x10,70x15,32', repeat: 2},
{num: 35, id: 'C-b-Y', type: '60x10,70x15,32', repeat: 2},
{num: 36, id: 'C-b-Z', type: '60x10,70x15,32', repeat: 2},
{num: 37, id: 'A-a-X', type: '60x10,70x15,32', repeat: 3},
{num: 38, id: 'A-a-Y', type: '60x10,70x15,32', repeat: 3},
{num: 39, id: 'A-a-Z', type: '60x10,70x15,32', repeat: 3},
{num: 40, id: 'A-b-X', type: '60x10,70x15,32', repeat: 3},
{num: 41, id: 'A-b-Y', type: '60x10,70x15,32', repeat: 3},
{num: 42, id: 'A-b-Z', type: '60x10,70x15,32', repeat: 3},
{num: 43, id: 'B-a-X', type: '60x10,70x15,32', repeat: 3},
{num: 44, id: 'B-a-Y', type: '60x10,70x15,32', repeat: 3},
{num: 45, id: 'B-a-Z', type: '60x10,70x15,32', repeat: 3},
{num: 46, id: 'B-b-X', type: '60x10,70x15,32', repeat: 3},
{num: 47, id: 'B-b-Y', type: '60x10,70x15,32', repeat: 3},
{num: 48, id: 'B-b-Z', type: '60x10,70x15,32', repeat: 3},
{num: 49, id: 'C-a-X', type: '60x10,70x15,32', repeat: 3},
{num: 50, id: 'C-a-Y', type: '60x10,70x15,32', repeat: 3},
{num: 51, id: 'C-a-Z', type: '60x10,70x15,32', repeat: 3},
{num: 52, id: 'C-b-X', type: '60x10,70x15,32', repeat: 3},
{num: 53, id: 'C-b-Y', type: '60x10,70x15,32', repeat: 3},
{num: 54, id: 'C-b-Z', type: '60x10,70x15,32', repeat: 3},
];

function testFunc(arr){
	testArry = arr;
};

var stepFourWrap = document.getElementById('step4-segment');
var segmentLi = document.querySelectorAll('#step4-segment li');
var segmentEls = document.querySelectorAll('#step4-segment li .segment');
var segmentMove = document.querySelectorAll('#step4-segment li .arrowMove');
var segmentRotate = document.querySelectorAll('#step4-segment li .refresh');

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

var obDiffX = 0;
var obDiffY = 0;

/*움직임*/
var posX = 0;
var posY = 0;
var rotate = 0;

var mX = 0;
var mY = 0;

function onClickStepFourWrap(e){
	if(e.target !== e.currentTarget){
		return false;
	}
	
	for(var z = 0; z < segmentEls.length; z++){	
		if(segmentLi[z].classList.contains('active')){
			segmentLi[z].classList.remove('active');
		}
	}
}

function thisTransformGet(){
	var liTransform = segmentLi[thisIndex].style.transform;
	var elsTransform = segmentEls[thisIndex].style.transform;
	
	var translateValue = liTransform.split('translate')[1];
	var rotateValue = elsTransform.split('rotate')[1];
	
	thisX = parseInt(translateValue.split(',')[0].split('(')[1].split('px')[0], 10);
	thisY = parseInt(translateValue.split(',')[1].split(')')[0].split('px')[0], 10);
	
	thisRotate = parseInt(rotateValue.split('(')[1].split(')')[0].split('deg')[0], 10 );
	
	console.log(thisRotate);
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
	}
	segmentLi[thisIndex].classList.add('active');
	
	
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
		segmentMove[thisIndex].style.left = 0;
		segmentRotate[thisIndex].style.right = 0;
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
var column = 5; // 행수
var row = 0; // 줄수

var elWidth = 106; // element 가로폭
var elHeight = 36; // element 세로폭
var offsetX = 46; // 거터값
var offsetY = 30;

function renderSegment(){

	// 초기화
	stepFourWrap.innerHTML = '';
	// 1차 : 컴포넌트 삽입
	for(var i = 0; i < dummy.length; i++){
		stepFourWrap.innerHTML += segmentComp(dummy[i]);
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
	for(var c = 0; c < column; c++){
		console.log('c:',c);
		for(var r = 0; r < row; r++){
			if(segmentLi[elRender] !== undefined && segmentLi[elRender] !== null){
				segmentLi[elRender].style.transform = 'translate(' + ( elWidth * r) + 'px, ' + (elHeight * c) + 'px)';
				segmentEls[elRender].style.width = elWidth + 'px';
				segmentEls[elRender].style.transform = 'rotate(' + rotate + 'deg)';
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
}

addWindowEvent();

renderSegment();





