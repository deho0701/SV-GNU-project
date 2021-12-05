// 모듈 import
var express = require('express');
var router = express.Router();
var mysql = require('mysql');
const app = express();
var bodyParser = require( 'body-parser' );
app.use(bodyParser.urlencoded({ extended: true }));    // post 방식 세팅
app.use(bodyParser.json());   
const multer = require("multer");

// DB 연결 정보
const { CONNREFUSED } = require('dns');
var pool = mysql.createPool({
  host     : 'localhost',
  user     : 'sv_app_all',
  password : '0000',
  database : 'sv_app_db'
})

/* 예약현황 페이지*/
const reserveFile = require ('../web_test.json');
app.post("/reserve",(req, res)=>
  {   
    console.log(req.body);
    var store_name = req.body.name; // 해당 날짜의 데이터만 보내게 해야함
    var sql = "SELECT table_id,time,client_name FROM reservation WHERE store_name ='"+store_name+"'";
    pool.query(sql ,(err, result)=>{
      if (err) { 
        console.log("error in reserve1");
      }
      else {
        res.json(result);
      }
    });
  }
);

app.post("/reserve_tables",(req, res)=>{
  var store_name = req.body.name;
  var sql = "SELECT table_id,table_x,table_y FROM table_info WHERE store_name ='"+store_name+"'";
    pool.query(sql ,(err, result)=>{
      if (err) { 
        console.log("error in reserve_tables");
      }else{
      res.json(result);
      }
    });
});

/* 자리 배치 페이지 */
//이미지 저장 옵션 (경로,이름)
const storage = multer.diskStorage({
  destination: "./img",
  filename: function(req, file, cb) {
    console.log("img ok");
    cb(null, file.originalname);
  }
});

//이미지 업로드
var upload = multer({storage: storage});
app.post("/photo_up",upload.single("img"),(req, res)=>{
  //console.log(req.file);
});

//이미지 다운로드
var fs = require('fs');
app.all("/photo_down",(req, res)=>{
  var img = 'img/2021_4_PC01.png';
  
  fs.readFile(img, function(err, data) {
    res.writeHead(200, {"Content-Type": "image/png"});
    res.write(data);
    res.end();
  });
});

//기존의 자리 배치 정보 가져오는 API
app.post("/pre_seat",(req, res)=>
  {   
    var username = req.body.name; 
    var sql = "SELECT table_id,table_x,table_y FROM table_info WHERE store_name ='"+username+"'";
    pool.query(sql ,(err, result)=>{
      if (err) { 
        console.log("error");
        return (err); 
      } 
      else {
        console.log(result);
        res.json(result);
      }
    });
  }
);

//변경된 자리 저장
app.all("/seat",(req, res)=>
  { 
    console.log(req.body);
    var storename  = req.body.name;
    var i=0;
    console.log(storename);
    var sql = "DELETE FROM table_info WHERE store_name ='"+storename+"';"
    pool.query(sql ,(err, result)=>{  
      if (err) { 
        console.log("error del");
      }
    });
    while(req.body.tables[i]!=null){
      console.log(req.body.tables[i].x);
      var id = req.body.tables[i].table_id;
      var x =req.body.tables[i].table_x;
      var y = req.body.tables[i].table_y;
      sql= "INSERT INTO table_info (table_id,table_x,table_y,store_name) VALUES ('"+id+"','"+x+"','"+y+"','"+storename+"')";
      pool.query(sql ,(err, result)=>{  
        if (err) { 
          console.log("error add");
          console.log(err); 
        }
        else {
          console.log("clear");
        }
      });
      i=i+1;
    }
    res.send("change clear");
  }
);

/* 일반설정 페이지 */
//기존의 설정 가져옴
app.all("/pre_setting",(req, res)=>{ 
    console.log(req.body);
    var store_name = req.body.name;
    var sql = "SELECT * FROM store WHERE store_name ='"+store_name+"'";
    pool.query(sql ,(err, result)=>{
      if (err) { 
        console.log(err);
      }
      else {
        console.log(result);
        res.json(result);
      }
    });  
  }
);

//변경된 설정 저장
app.all("/setting",(req, res)=>
  {   
    console.log(req.body);
    var name = req.body.name;
    var address= req.body.address;
    var start_time = req.body.start_time;
    var end_time = req.body.end_time;
    var table_time = req.body.table_time;
    var sql = "UPDATE store SET address = '"+address+"',start_time='"+start_time+"',end_time ='"+end_time+"',table_time='"+table_time+"' WHERE store_name ='"+name+"'";
    pool.query(sql ,(err, result)=>{
      if (err) { 
        console.log(err);
      }
      else {
        console.log(result);
        res.json({set:"변경완료"});
      }
    });
  }
);

module.exports = app;