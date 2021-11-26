/*
console.log("get session");
document.getElementById("id").innerHTML='id : '+id;
document.getElementById("PW").innerHTML="PW : "+ pw;
document.getElementById("call number").innerHTML="call number : "+call;
document.getElementById("nick name").innerHTML="nick name : "+nick_name;
*/

var ttt = "";
console.log(ttt);
fetch("/get_session").then((response)=>{
  console.log(response);
}).catch(error => console.log(error))                                     ;
console.log("get session : "+userid);
/* 데이터 베이스 접속*/

var id,pw,call,nick_name;
var username = deho;
var sql = "SELECT pw FROM user WHERE id ='"+username+"'";
pool.query(sql ,(err, result)=>{
  if (error){
    console.log(error);
  }
  console.log(results);
  id = results[0].id;
  pw = results[0].pw;
  call = results[0].call_user;
  nick_name = results[0].nickname;

});

document.getElementById("id").innerHTML="id : "+ id;
document.getElementById("PW").innerHTML="PW : "+ pw;
document.getElementById("call number").innerHTML="call number : "+call;
document.getElementById("nick name").innerHTML="nick name : "+nick_name;





