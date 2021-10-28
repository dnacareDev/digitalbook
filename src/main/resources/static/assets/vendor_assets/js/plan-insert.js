window.addEventListener("load", function(){ //javascript
//    let section = document.getElementById("section"),
//    step = section.children,  
//    n = 0;
//    
//    
//    let slideUp = (title, target, duration=500) => {
//        title.classList.remove("active");
//        target.style.transitionProperty = 'height, margin, padding';
//        target.style.transitionDuration = duration + 'ms';
//        target.style.boxSizing = 'border-box';
//        target.style.height = target.offsetHeight + 'px';
//        target.offsetHeight;
//        target.style.overflow = 'hidden';
//        target.style.height = 0;
//        target.style.paddingTop = 0;
//        target.style.paddingBottom = 0;
//        target.style.marginTop = 0;
//        target.style.marginBottom = 0;
//        window.setTimeout( () => {
//            target.style.display = 'none';
//            target.style.removeProperty('height');
//            target.style.removeProperty('padding-top');
//            target.style.removeProperty('padding-bottom');
//            target.style.removeProperty('margin-top');
//            target.style.removeProperty('margin-bottom');
//            target.style.removeProperty('overflow');
//            target.style.removeProperty('transition-duration');
//            target.style.removeProperty('transition-property');
//        }, duration);
//    }
//    let slideDown = (title, target, duration=500) => {
//        target.style.removeProperty('display');
//        let display = window.getComputedStyle(target).display;
//        if (display === 'none')
//        display = 'block';
//        title.classList.add("active");
//        target.style.display = display;
//        let height = target.offsetHeight;
//        target.style.overflow = 'hidden';
//        target.style.height = 0;
//        target.style.paddingTop = 0;
//        target.style.paddingBottom = 0;
//        target.style.marginTop = 0;
//        target.style.marginBottom = 0;
//        target.offsetHeight;
//        target.style.boxSizing = 'border-box';
//        target.style.transitionProperty = "height, margin, padding";
//        target.style.transitionDuration = duration + 'ms';
//        target.style.height = height + 'px';
//        target.style.removeProperty('padding-top');
//        target.style.removeProperty('padding-bottom');
//        target.style.removeProperty('margin-top');
//        target.style.removeProperty('margin-bottom');
//        window.setTimeout( () => {
//            target.style.removeProperty('height');
//            target.style.removeProperty('overflow');
//            target.style.removeProperty('transition-duration');
//            target.style.removeProperty('transition-property');
//        }, duration);
//    }
//    let slideToggle = (title, target, duration = 500) => {
//        if (window.getComputedStyle(target).display === 'none') {
//            return slideDown(title, target, duration);
//        } else {
//            return slideUp(title, target, duration);
//        }
//    }
//    
//    for(let i=0; i<step.length; i++){
//        let stepInner = step[i].children;
//        for(let k=0; k<stepInner.length; k++){
//            let [title, content] = stepInner[k].children;
//            
//            title.addEventListener("click", function(e){
//            	if (k === 0) {
//            		setTimeout(() => {
//            			const container = document.querySelector('#map_regist');
//            			
//            			const options = { 
//            			    center: new kakao.maps.LatLng(33.450701, 126.570667),
//            			    level: 3
//            			};
//            			const map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴
//            			
//            			function resizeMap() {	    
//            			    mapContainer.style.width = '650px';
//            			    mapContainer.style.height = '650px'; 
//            			}
//
//            			function relayout() {	    
//            			    map.relayout();
//            			}
//            			
//            			// 전송데이타 관련 변수들
//            			let markers = [];
//            			let coords = [];
//            			let __polygon;
//            			
//            			function createMarker(lng, lat, map){
//            			    const markerPosition  = new kakao.maps.LatLng(lat, lng); 
//            			
//            			    // 마커를 생성합니다
//            			    const marker = new kakao.maps.Marker({
//            			        position: markerPosition
//            			    });
//            			
//            			    marker.setMap(map);
//            			    markers.push(marker);
//            			    coords.push({ lat, lng })
//            			}
//            			
//            			function requestServer()
//            			{
//            			    // $.ajax({ 
//            			    //     url: "http://localhost:8080/api/",
//            			    //     data: JSON.stringify({'coords': coords}),                        
//            			    //     method: "POST",            
//            			    //     dataType: "json"      
//            			    // }).done(function(json) {
//            			    //     console.log(json, "response")
//            			    // })        
//            			    // .fail(function(xhr, status, errorThrown) {
//            			    //    console.error(errorThrown)
//            			    // })              
//            			}
//            			
//            			// 배열에 추가된 마커들을 지도에 표시하거나 삭제하는 함수입니다
//            			function clearMarkers(isRequest) {
//            			    for (var i = 0; i < markers.length; i++) {
//            			        markers[i].setMap(null);
//            			    }            
//            			
//            			    if (isRequest) {
//            			        requestServer();
//            			        markers = [];
//            			//         coords = [];
//            			    } else {
//            			        markers = [];
//            			        coords = [];
//            			    }
//            			}
//            			
//            			// 이벤트 관련 변수들
//            			var drawingFlag = false; 
//            			var drawingPolygon; 
//            			var polygon; 
//            			var areaOverlay; 
//            			
//            			kakao.maps.event.addListener(map, 'click', function(mouseEvent) { 
//            			    // 마커 생성   
//            			    const coord = mouseEvent.latLng;
//            			    createMarker(coord.La, coord.Ma, map)    
//            			    
//            			    var clickPosition = mouseEvent.latLng; 
//            			    
//            			    // 지도 클릭이벤트가 발생했는데 다각형이 그려지고 있는 상태가 아니면
//            			    if (!drawingFlag) {
//            			        // 상태를 true로, 다각형을 그리고 있는 상태로 변경합니다
//            			        drawingFlag = true;
//            			        
//            			        // 지도 위에 다각형이 표시되고 있다면 지도에서 제거합니다
//            			        if (polygon) {  
//            			            polygon.setMap(null);      
//            			            polygon = null;  
//            			        }
//            			        
//            			        // 지도 위에 면적정보 커스텀오버레이가 표시되고 있다면 지도에서 제거합니다
//            			        if (areaOverlay) {
//            			            areaOverlay.setMap(null);
//            			            areaOverlay = null;
//            			        }
//            			    
//            			        // 그려지고 있는 다각형을 표시할 다각형을 생성하고 지도에 표시합니다
//            			        drawingPolygon = new kakao.maps.Polygon({
//            			            map: map, // 다각형을 표시할 지도입니다
//            			            path: [clickPosition], // 다각형을 구성하는 좌표 배열입니다 클릭한 위치를 넣어줍니다
//            			            strokeWeight: 3, // 선의 두께입니다 
//            			            strokeColor: '#00a0e9', // 선의 색깔입니다
//            			            strokeOpacity: 1, // 선의 불투명도입니다 0에서 1 사이값이며 0에 가까울수록 투명합니다
//            			            strokeStyle: 'solid', // 선의 스타일입니다
//            			            fillColor: '#00a0e9', // 채우기 색깔입니다
//            			            fillOpacity: 0.2 // 채우기 불투명도입니다
//            			        }); 
//            			        
//            			        // 그리기가 종료됐을때 지도에 표시할 다각형을 생성합니다 
//            			        polygon = new kakao.maps.Polygon({ 
//            			            path: [clickPosition], // 다각형을 구성하는 좌표 배열입니다 클릭한 위치를 넣어줍니다 
//            			            strokeWeight: 3, // 선의 두께입니다 
//            			            strokeColor: '#00a0e9', // 선의 색깔입니다   
//            			            strokeOpacity: 1, // 선의 불투명도입니다 0에서 1 사이값이며 0에 가까울수록 투명합니다
//            			            strokeStyle: 'solid', // 선의 스타일입니다
//            			            fillColor: '#00a0e9', // 채우기 색깔입니다
//            			            fillOpacity: 0.2 // 채우기 불투명도입니다
//            			        });
//            			
//            			        
//            			    } else { // 다각형이 그려지고 있는 상태이면 
//            			        
//            			        // 그려지고 있는 다각형의 좌표에 클릭위치를 추가합니다
//            			        // 다각형의 좌표 배열을 얻어옵니다
//            			        var drawingPath = drawingPolygon.getPath();
//            			    
//            			        // 좌표 배열에 클릭한 위치를 추가하고
//            			        drawingPath.push(clickPosition);
//            			        
//            			        // 다시 다각형 좌표 배열을 설정합니다
//            			        drawingPolygon.setPath(drawingPath);
//            			        
//            			    
//            			        // 그리기가 종료됐을때 지도에 표시할 다각형의 좌표에 클릭 위치를 추가합니다
//            			        // 다각형의 좌표 배열을 얻어옵니다
//            			        var path = polygon.getPath();
//            			    
//            			        // 좌표 배열에 클릭한 위치를 추가하고
//            			        path.push(clickPosition);
//            			        
//            			        // 다시 다각형 좌표 배열을 설정합니다
//            			        polygon.setPath(path);
//            			    }
//            			
//            			});
//            			
//            			// 지도에 마우스무브 이벤트를 등록합니다
//            			// 다각형을 그리고있는 상태에서 마우스무브 이벤트가 발생하면 그려질 다각형의 위치를 동적으로 보여주도록 합니다
//            			kakao.maps.event.addListener(map, 'mousemove', function (mouseEvent) {
//            			
//            			    // 지도 마우스무브 이벤트가 발생했는데 다각형을 그리고있는 상태이면
//            			    if (drawingFlag){
//            			
//            			        // 마우스 커서의 현재 위치를 얻어옵니다 
//            			        var mousePosition = mouseEvent.latLng; 
//            			        
//            			        // 그려지고있는 다각형의 좌표배열을 얻어옵니다
//            			        var path = drawingPolygon.getPath();
//            			        
//            			        // 마우스무브로 추가된 마지막 좌표를 제거합니다
//            			        if (path.length > 1) {
//            			            path.pop();
//            			        } 
//            			        
//            			        // 마우스의 커서 위치를 좌표 배열에 추가합니다
//            			        path.push(mousePosition);
//            			
//            			        // 그려지고 있는 다각형의 좌표를 다시 설정합니다
//            			        drawingPolygon.setPath(path);
//            			    }             
//            			});     
//            			
//            			// 지도에 마우스 오른쪽 클릭 이벤트를 등록합니다
//            			// 다각형을 그리고있는 상태에서 마우스 오른쪽 클릭 이벤트가 발생하면 그리기를 종료합니다
//            			kakao.maps.event.addListener(map, 'rightclick', function (mouseEvent) {
//            			
//            			    // 지도 오른쪽 클릭 이벤트가 발생했는데 다각형을 그리고있는 상태이면
//            			    if (drawingFlag) {
//            			        
//            			        // 그려지고있는 다각형을  지도에서 제거합니다
//            			        drawingPolygon.setMap(null);
//            			        drawingPolygon = null;  
//            			        
//            			        // 클릭된 죄표로 그릴 다각형의 좌표배열을 얻어옵니다
//            			        var path = polygon.getPath();
//            			    
//            			        // 다각형을 구성하는 좌표의 개수가 3개 이상이면 
//            			        if (path.length > 2) {
//            			            
//            			            // 지도에 다각형을 표시합니다
//            			            polygon.setMap(map); 
//            			
//            			            var area = Math.round(polygon.getArea()), // 다각형의 총면적을 계산합니다
//            			                // content = '<div class="info">총면적 <span class="number"> ' + area + '</span> m<sup>2</sup></div>'; // 커스텀오버레이에 추가될 내용입니다
//            			                
//            			            // 면적정보를 지도에 표시합니다
//            			            areaOverlay = new kakao.maps.CustomOverlay({
//            			                map: map, // 커스텀오버레이를 표시할 지도입니다 
//            			                content: "",  // 커스텀오버레이에 표시할 내용입니다
//            			                xAnchor: 0,
//            			                yAnchor: 0,
//            			                position: path[path.length-1]  // 커스텀오버레이를 표시할 위치입니다. 위치는 다각형의 마지막 좌표로 설정합니다
//            			            });      
//            			            clearMarkers(true)
//            			            
//            			        } else { 
//            			            
//            			            // 다각형을 구성하는 좌표가 2개 이하이면 다각형을 지도에 표시하지 않습니다 
//            			            polygon = null;  
//            			            clearMarkers()
//            			        }
//            			        
//            			        // 상태를 false로, 그리지 않고 있는 상태로 변경합니다
//            			        drawingFlag = false;          
//            			    }  
//            			});
//            		}, [])
//            	}
//                slideToggle(title, content);
//            });
//        }
//    }
    
});