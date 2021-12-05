var express = require('express');
var router = express.Router();
var mysql = require('mysql');
const app = express();
var bodyParser = require( 'body-parser' );
app.use(bodyParser.urlencoded({ extended: true }));    // post 방식 세팅
app.use(bodyParser.json());   
const multer = require("multer");

// DB 연결
const { CONNREFUSED } = require('dns');
var pool = mysql.createPool({
  host     : 'localhost',
  user     : 'sv_app_all',
  password : '0000',
  database : 'sv_app_db'
})

//////////////////////////////////////////////////////////////////////////////////////////

//이미지 설정
const storage = multer.diskStorage({
  destination: "./img",
  filename: function(req, file, cb) {
    console.log("img ok");
    cb(null, file.originalname);
  }
});

var upload = multer({storage: storage});

app.post("/photo_up",upload.single("img"),(req, res)=>{
  //console.log(req.file);
});


var fs = require('fs');
app.all("/photo_down",(req, res)=>{
  var img = 'img/2021_4_PC01.png';
  
  fs.readFile(img, function(err, data) {
    res.writeHead(200, {"Content-Type": "image/png"});
    res.write(data);
    res.end();
  });
});
////////////////////////////////////////////////////////////////////////////////////////

/*예약 현황*/
const reserveFile = require ('../web_test.json');
app.post("/reserve",(req, res)=>
  {   
    console.log(req.body);
    var store_name = req.body.name; // 해당 날짜의 데이터만 보내게 해야함
    var sql = "SELECT table_id,time,client_name FROM reservation WHERE store_name ='"+store_name+"'";
    pool.query(sql ,(err, result)=>{
      if (err) { 
        console.log("error in reserve");
      }
      else {
        console.log(result);
        res.json(result);
      }
    });
  }
);
///////////////////////////////////////////////////////////////////////////////////////
/* 자리 배치 페이지 */
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

////////////////////////////////////////////////////////////////////////////////////////////
/* 일반설정 페이지 */
app.all("/pre_setting",(req, res)=>
  { 
    console.log(req.body);
    var store_name = req.body.name;
    var sql = "SELECT store_ID,store_name,address,store_call FROM store WHERE store_name ='"+store_name+"'";
    pool.query(sql ,(err, result)=>{
      if (err) { 
        console.log(err);
      }
      else {
        console.log(result);
        res.json(result);
      }
    });  
    console.log("setting clear");
    res.send("setting API claer");
  }
);

app.all("/setting",(req, res)=>
  {   
    console.log("setting clear");
    res.send("setting API claer");
  }
);





  
module.exports = app;