// 모듈 import
var express = require('express');
var router = express.Router();
var mysql = require('mysql');
const app = express();
var bodyParser = require( 'body-parser' );
app.use(bodyParser.urlencoded({ extended: true }));    // post 방식 세팅
app.use(bodyParser.json());   

// DB 연결 정보
const { CONNREFUSED } = require('dns');
var pool = mysql.createPool({
  host     : 'localhost',
  user     : 'sv_app_all',
  password : '0000',
  database : 'sv_app_db'
})

/* 테이블 예약 페이지*/
//총 테이블 개수 구하는 API
app.get("/table_num",(req, res)=>{
  console.log(req.query);
  var store_name = 'phonecafe';//req.query.name;
  var sql = "SELECT COUNT (table_id) as table_num FROM table_info WHERE store_name='"+store_name+"'";
  pool.query(sql ,(err, result)=>{
    if (err) { 
      console.log("error in table_num");
    }else{
    console.log(result[0]);}
    res.json(result[0]);  
  });
});

//기존 테이블 정보 요청 API
app.all("/sit",(req, res)=>{
    console.log(req.query);
    var date = req.query.date;
    var time = req.query.time;
    time = parseInt(time); //int형으로 타입 고정
    var check_possible = true; //reset value     true가 예약가능 false가 예약 불가능
    var storename =req.query.name; 
    var table_id = req.query.table_id;
    var sql2 = "SELECT COUNT(*) as bool FROM reservation WHERE store_name='"+storename+"'AND table_id='"+table_id+"'AND date ='"+date+"'AND time>='"+(time-100)+"'AND time<='"+(time+100)+"'";
    pool.query(sql2 ,(err, result2)=>{
      if (err) { 
        console.log("error in sit");
      }
      else {
        if (result2[0].bool!=0){
          check_possible = false;
        }
        console.log(req.query.table_id+":::"+result2[0].bool);
      }
    });
    var sql = "SELECT table_id,table_x,table_y,booked FROM table_info WHERE store_name ='"+storename+"'";
    pool.query(sql ,(err,result)=>{
      if (err) { 
        console.log("error in sit2");
      }
      else {
        const id = req.query.table_id -1;
        console.log("원래값");
        console.log(result[id]);
        var result_add_possible_info = result[id];  //쿼리 결과 값이 변경이 안되어 다른 객체 이용
        result_add_possible_info["booked"]= check_possible;
        console.log("수정값");
        console.log(result_add_possible_info);
        //console.log(req.query.table_id+":::::"+check_possible);
        res.json(result_add_possible_info);
      }
    });
});


/* 결제 페이지 */
app.all("/pay",(req, res)=>{ //결제시 예약, 히스토리에 추가 
    console.log(req.body);
    var client_name = req.body.id; 
    var store_name = req.body.store_name ;
    var i =0;
    var date = req.body.date;
    var time = req.body.time;
    while(req.body.tables[i]!=null){
      var table_id = req.body.tables[i]; 
      console.log("table_id: "+table_id);
      sql="INSERT INTO reservation(store_name,table_id,time,client_name,date) VALUES ('"+store_name+"','"+table_id+"','"+time+"','"+client_name+"','"+date+"')";
      pool.query(sql ,(err, result)=>{
        if (err) { 
          console.log("error in pay1");
          console.log(err);
          res.json({pay:404});
        }else{
          console.log("pay1");
        }
      });
      sql="INSERT INTO history(store_name ,date,time,user_id) VALUES ('"+store_name+"','"+date+"','"+time+"','"+client_name+"')";
      pool.query(sql ,(err, result)=>{
        if (err) { 
          console.log("error in pay2");
          console.log(err);
          res.json({pay:404});
        }else{
          console.log("pay2");
        }
      });
      i=i+1;
    }
    res.json({pay:200}); 
});


/*히스토리 페이지*/
//히스토리 삭제 >> 히스토리 & 예약정보에서 삭제함
app.post("/history_del",(req, res)=>{  //예약 취소  - 데이터 받아오기 
  console.log(req.body);
  var store_name = req.body.store_name; 
  var date = req.body.date;
  var time = req.body.time;
  var id = req.body.id;
  var sql = "DELETE FROM history WHERE user_id='"+id+"'AND date='"+date+"'AND time='"+time+"'AND store_name='"+store_name+"'";
  pool.query(sql ,(err, result)=>{
    if (err) { 
      console.log("error in history_del");
      //return (err); 
      res.json({test:"err"});
    }else{
    console.log("history del");
    //console.log(result); 
    }
  });
  var sql2 = "DELETE FROM reservation WHERE client_name='"+id+"'AND date='"+date+"'AND time='"+time+"'AND store_name='"+store_name+"'";
  pool.query(sql2 ,(err, result)=>{
    if (err) { 
      console.log("error in history_del2");
      console.log(err); 
      res.json({test:"err"});
    }else{
    console.log("history del2");
    //console.log(result);
    res.json({test:"ok"});  
    }
  });
});

//히스토리 개수 요청 API
app.get("/history_num",(req, res)=>{ //히스토리 개수 
  var username = "hoho";
  var sql = "SELECT count(*) as history_num FROM history WHERE user_id='"+username+"'";
  pool.query(sql ,(err, result)=>{
    if (err) { 
      console.log("error in history_num");
    }else{
    console.log(result[0]);
    res.json(result[0]);
    }  
  });
});

//요청한 유저의 히스토리 정보 전송API
app.get("/history",(req, res)=>{
  console.log(req.query);
  var user_id = req.query.id;
  var history_id = req.query.history_id;
  var sql = "SELECT DATE_FORMAT(date,'%y-%m-%d') as date,store_name,time FROM history WHERE user_id='"+user_id+"'";
  pool.query(sql ,(err, result)=>{
    if (err) { 
      console.log("error in history"); 
    }else{
    console.log(result[history_id-1]);
    res.json(result[history_id-1]);  
    }
  });
});

//도면 이미지 전송
var fs = require('fs');
app.all("/photo",(req, res)=>{
  var img = 'img/2021_4_PC01.png';
  
  fs.readFile(img, function(err, data) {
    res.writeHead(200, {"Content-Type": "image/png"});
    res.write(data);
    res.end();
  });
});

module.exports = app;