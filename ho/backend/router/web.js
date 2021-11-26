var express = require('express');
var router = express.Router();
var mysql = require('mysql');
const app = express();
var bodyParser = require( 'body-parser' );
app.use(bodyParser.urlencoded({ extended: true }));    // post 방식 세팅
app.use(bodyParser.json());   

// DB 연결
const { CONNREFUSED } = require('dns');
var pool = mysql.createPool({
  host     : 'localhost',
  user     : 'sv_app_all',
  password : '0000',
  database : 'sv_app_db'
})

app.all("/seat",(req, res)=> //쿼리 수정
  { 
    console.log(req.body);
    var storename  = req.body.name;
    var i=0;
    var sql = "DELETE FROM table_info WHERE store_name ='"+storename+"';"
    //sql= sql+"INSERT INTO table_info (table_id,table_x,table_y,store_name) VALUES ('"+id+"','"+x+"','"+y+"','"+storename+"')";
    pool.query(sql ,(err, result)=>{  
      if (err) { 
        console.log("error");
      }
    });
    while(req.body.tables[i]!=null){
      console.log(req.body.tables[i].x);
      var id = req.body.tables[i].id;
      var x =req.body.tables[i].x;
      var y = req.body.tables[i].y;
      sql= "INSERT INTO table_info (table_id,table_x,table_y,store_name) VALUES ('"+id+"','"+x+"','"+y+"','"+storename+"')";
      pool.query(sql ,(err, result)=>{  
        if (err) { 
          console.log("error");
          //console.log(err); 
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



app.all("/setting",(req, res)=>
  {   
    console.log("setting clear");
    res.send("setting API claer");
  }
);

const reserveFile = require ('../web_test.json');
app.post("/reserve",(req, res)=>
  {   
    console.log(req.body);
    var store_name = req.body.name; // 해당 날짜의 데이터만 보내게 해야함 + 이미지 추가
    var sql = "SELECT table_num,time,client_name,people FROM reservation WHERE store_name ='"+store_name+"'";
    pool.query(sql ,(err, result)=>{
      if (err) { 
        console.log("error");
        return done(err); 
      }
      else {
        console.log(result);
        res.json(result);
      }
    });
  }
);
  
module.exports = app;