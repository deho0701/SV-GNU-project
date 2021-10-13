var table_num = 10;
var obj = document.getElementById("container");
console.log(obj);

for(var i=0; i < table_num; i++){
    var div = document.createElement("div");
    div.innerHTML = i+1;
    div.setAttribute("class", "table");
    obj.appendChild(div);
}