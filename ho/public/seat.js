var obj = document.getElementById("shop");

table.onmousedown = function(event) {

    let shiftX = event.clientX - table.getBoundingClientRect().left;
    let shiftY = event.clientY - table.getBoundingClientRect().top;
  
    table.style.position = 'absolute';
    table.style.zIndex = 1000;
    document.body.append(table);
  
    moveAt(event.pageX, event.pageY);
  
    // 초기 이동을 고려한 좌표 (pageX, pageY)에서
    // 공을 이동합니다.
    function moveAt(pageX, pageY) {
      table.style.left = pageX - shiftX + 'px';
      table.style.top = pageY - shiftY + 'px';
    }
  
    function onMouseMove(event) {
      moveAt(event.pageX, event.pageY);
    }
  
    // mousemove로 공을 움직입니다.
    document.addEventListener('mousemove', onMouseMove);
  
    // 공을 드롭하고, 불필요한 핸들러를 제거합니다.
    table.onmouseup = function() {
      document.removeEventListener('mousemove', onMouseMove);
      table.onmouseup = null;
    };
  
};
  
table.ondragstart = function() {
    return false;
};