//모듈 import
const express = require('express');
const path = require('path');
const app = express();
var mysql = require('mysql');
const session = require('express-session');
var bodyParser = require( 'body-parser' );
var cookieParser = require('cookie-parser');
const cors =require('cors');
const passport = require('passport'), LocalStrategy = require('passport-local').Strategy;
const fileStore = require('session-file-store')(session)

console.log("start");
app.use(express.static(path.join(__dirname, '예약앱')));

//포트 설정
app.set('port', process.env.PORT || 5080);
app.listen(app.get('port'), () => {
  console.log(app.get('port'), '번 포트에서 대기 중');
}); 
const { type } = require('os');

// DB 연결
const { CONNREFUSED } = require('dns');
var pool = mysql.createPool({
  host     : 'localhost',
  user     : 'sv_app_all',
  password : '0000',
  database : 'sv_app_db'
})

//react - node.js 라우팅
app.use(cors());

//routing - APIs
const appRouter = require('./router/app.js');
var webRouter = require("./router/web.js");
var login = require("./router/login.js");
app.use('/app', appRouter);
app.use('/web', webRouter);
app.use('/login', login);

//뷰 엔진 생성 
var handlebars = require('express-handlebars')
	.create({ defaultLayout:'main' });
app.engine('handlebars', handlebars.engine);
app.set('view engine', 'handlebars');

//디렉토리 설정변경 
app.use( express.static(__dirname + '/public_server/'));

// passport용
app.use (cookieParser());
app.use(bodyParser.urlencoded({ extended: true }));    // post 방식 세팅
app.use(bodyParser.json());                            // json 사용 하는 경우의 세팅

//세션 옵션
app.use(session({
  secret: 'secret key',
  resave: true,
  saveUninitialized:false,
  cookie: {
    secret: false,  //외부로 배포할 때는 이 옵션 없애 줘야 함. 개발할 때만 쓰기
    maxAge: 24 * 60 * 60 * 1000  //쿠키 유지 (하루)24 * 60 * 60 * 1000
  },
  store: new fileStore()
}));

app.use(passport.initialize()); //passport 사용
app.use(passport.session()); //passport 사용시 세션 활용

//로그인 성공시 세션에 저장
passport.serializeUser((user, done)=>{
  console.log(user);
  return done(null, user);
});

//세션 불러오기(사용자 확인)
passport.deserializeUser((user, done)=>{
  console.log("okokok");
  return done(null, user);
});

//passport strategy 세팅   
passport.use(new LocalStrategy({
    usernameField: 'id',   //login.html의 name에 따라 수정
    passwordField: 'pw'
  },
  (username, password, done)=>{  //done을 어떻게 호출하는지에 따라 성공or 실패 구분    
    /*
    var exists_id = 'SELECT * FROM user WHERE EXISTS(SELECT * FROM user WHERE id='+username+')';
    pool.query(exists_id,(err,result,fields)=>{  
    })//유저 아이디 있는지 확인하는곳
    */
    console.log(username); 
    var sql = "SELECT pw FROM user WHERE id ='"+username+"'";
    pool.query(sql ,(err, result)=>{
      console.log(result[0].pw);//sql 리턴값 파싱
      //에러 발생
      if (err) { 
        console.log("error");
        return done(err); 
      }
      /*
      //username 자체가 DB에 없을 때
      if (!user) {
        return done(null, false, { message: 'Incorrect username.' });
      }
      */
     
      // username은 맞지만 비밀번호가 틀릴 때
      if (password != result[0].pw ) { 
        console.log("not correct password");
        return done(null, false, { message: 'Incorrect password.' });
      }
      console.log("clear");
      //인증 성공
      return done(null,username);
    })
  }
));

/*/////////  라우터 //////*/
//메인
app.get("/home",(req, res)=>
  {   //세션정보는 req.session 에 들어 있다 
      if (req.session.passport != undefined)   //세션에 유저가 있다면
      {
          res.redirect('index.html');
      }
      else
      {
          res.sendFile('login.html');
      }
  }
);

//로그인
app.post("/login",
  passport.authenticate('local', { 
    //successRedirect: '/index.html', //성공시, 메인페이지 이동
    failureRedirect: '/login.html' //실패시 로그인 페이지 이동
}),(req,res)=>{
  req.session.save(function(){
    console.log("ok");
    res.redirect('/index.html');    
  })
});

//로그 아웃 처리
app.get('/logout',(req,res)=>{
  //passport 정보 삭제
  req.logout();
  //서버측 세션 삭제
  req.session.destroy(()=>{
      //클라이언트 측 세션 암호화 쿠키 삭제
      res.cookie('connect.sid','',{maxAge:0});
      res.redirect('/home');
  });
});

//에러 페이지 404 (맨 밑에 존재해야함)
app.use(function(req, res, next){
	res.status(404);
	res.render('404');
});

// 에러 페이지 500
app.use(function(err, req, res, next){
	console.error(err.stack);
	res.status(500);
	res.render('500');
});
