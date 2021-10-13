
//도면 삽입
var fileInput = document.getElementById("fileInput");
//값이 변경될때 호출 되는 이벤트 리스너
fileInput.addEventListener('change',function(e){
    console.log(e.target.files)
    
    var file = e.target.files[0]; //선택된 파일
    var reader = new FileReader();
    reader.readAsDataURL(file); //파일을 읽는 메서드 

    reader.onload = function(){
        var photoFrame = document.createElement("div");
        photoFrame.style = `background : url(${reader.result}); background-size : cover`;
        photoFrame.className = "photoFrame";
        document.getElementById("shop").appendChild(photoFrame);
        e.target.value = "";
    }
})



//좌석 생성
var i = 1;
var TList = new Array();

$(document).ready(function(){
    $("#add").click(function(){
        let Table = new Object();
        Table.id = i;
        Table.reserve = false;
        TList.push(Table);
        console.log(Table);
        $("#shop").append("<div id='"+i+"' class='table'>"+i+"</div>");
        i++;
    });
})

window.addEventListener("load", function(){
    var shop = document.querySelector("#shop");
    var status = document.querySelector(".status");
    var dragging = false;
    var offset = {x:0, y:0};
    var current = null;

    var left = shop.offsetLeft;
    var top = shop.offsetTop;

    console.log(left);
    console.log(top);


    shop.onmousedown = function(e){
        if(e.target.classList.contains("table")){
            dragging = true;
            current = e.target;
            offset.x = e.offsetX;
            offset.y = e.offsetY;
        }
    };

    shop.onmousemove = function(e){
        if(!dragging) return;

        var x = e.pageX-offset.x - left;
        var y = e.pageY-offset.y - top;

        current.style.left = x+"px";
        current.style.top = y+"px";

        status.innerText = "(x, y):("+x+","+y+")";

    };

    shop.onmouseup = function(e){
        dragging = false;
        console.log(current);
        console.log(current.id);
        console.log(current.style.left);
        console.log(typeof(current.id));
        
        TList[parseInt(current.id)-1].x = current.style.left;
        TList[parseInt(current.id)-1].y = current.style.top;
        console.log(TList[parseInt(current.id)-1]);
    };
});

//json 파일에 좌석 정보 저장
import { writeFileSync } from 'fs';
$("#save").click(function(){
    let seat = JSON.stringify(TList);
    writeFileSync("seat.json", seat);
    console.log('ok');
});