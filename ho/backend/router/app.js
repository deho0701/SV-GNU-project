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
//총 테이블 개수 구하는 API
app.get("/table_num",(req, res)=>{
    var username = "test_store";
    var sql = "SELECT COUNT (table_id) as table_num FROM table_info WHERE store_name='"+username+"'";
    pool.query(sql ,(err, result)=>{
      if (err) { 
        console.log("error");
        return done(err); 
      }else{
      console.log(result[0]);}
      res.json(result[0]);  
    });
  });

//기존 테이블 정보 요청 API
app.all("/sit",(req, res)=>{
    console.log("sit ok");
    console.log(req.query);
    var username = "test_store"; 
    var sql = "SELECT table_id,table_x,table_y FROM table_info WHERE store_name ='"+username+"'";
    pool.query(sql ,(err, result)=>{
      if (err) { 
        console.log("error");
        return done(err); 
      }
      else {
        const id = req.query.table_id -1;
        console.log(result[id]);
        res.json(result[id]);
      }
    });
});

app.all("/pay",(req, res)=>{ //쿼리 수정
    console.log(req.body);
    res.json({pay:200}); 
    /*
    var sql = "DELETE FROM table_info WHERE store_name ='"+storename+"';"
    sql= sql+"INSERT INTO table_info (table_id,table_x,table_y,store_name) VALUES ('"+id+"','"+x+"','"+y+"','"+storename+"')";
    pool.query(sql ,(err, result)=>{
    });
    */
   });

  app.get("/history_num",(req, res)=>{
  var username = "hoho";
  var sql = "SELECT count(*) as history_num FROM history WHERE user_id='"+username+"'";
  pool.query(sql ,(err, result)=>{
    if (err) { 
      console.log("error");
      return (err); 
    }else{
    console.log(result[0]);}
    res.json(result[0]);  
  });
});

app.get("/history",(req, res)=>{
  console.log(req.query);
  var user_id = req.query.id;
  var history_id = req.query.history_id;
  var sql = "SELECT date,store_name FROM history WHERE user_id='"+user_id+"'";
  pool.query(sql ,(err, result)=>{
    if (err) { 
      console.log("error");
      return (err); 
    }else{
    console.log(result[history_id-1]);
    res.json(result[history_id-1]);  
    }
  });
});
  

  app.all("/test",(req, res)=>{
    req.query
    var sql = "INSERT INTO reservation (store_name) VALUES (GNU)";
    pool.query(sql ,(err, result)=>{
      if (err) { 
        console.log("error");
        return done(err); 
      }
    })
    
    console.log("oo");
    res.send("test ok");
  });

module.exports = app;