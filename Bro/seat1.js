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
$(document).ready(function(){
    $("button").click(function(){
        $("#shop").append("<div id='table"+i+"' class='table'>"+i+"</div>");
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
    };
});