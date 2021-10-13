//모듈 임포트
const express = require('express');
const path = require('path');
const app = express();
var mysql = require('mysql');
const session = require('express-session');
var bodyParser = require( 'body-parser' );
var cookieParser = require('cookie-parser');

const passport = require('passport'), LocalStrategy = require('passport-local').Strategy;
const fileStore = require('session-file-store')(session)

console.log("start");

//포트 설정
app.set('port', process.env.PORT || 5080);
app.listen(app.get('port'), () => {
  console.log(app.get('port'), '번 포트에서 대기 중');
}); 

const { type } = require('os');
const { CONNREFUSED } = require('dns');
var pool = mysql.createPool({
  host     : 'localhost',
  user     : 'sv_app_all',
  password : '0000',
  database : 'sv_app_db'
})

//뷰 엔진 생성 
var handlebars = require('express-handlebars')
	.create({ defaultLayout:'main' });
app.engine('handlebars', handlebars.engine);
app.set('view engine', 'handlebars');

//디렉토리 설정변경 
app.use( express.static(__dirname + '/public'));


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
    secret: false,  //나중에는 이 옵견 없애 줘야 함. 개발할 때만 쓰기
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
    })//유저 아이디 있는지 확인하는곳(일단 패스)
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
          res.redirect('/index.html');
      }
      else
      {
          res.redirect('/login.html');

      }
  }
);


//세션 확인용
app.get("/debug", (req, res) => {
  console.log(req.session);
  res.json({
    "req.session": req.session, // 세션 데이터
    "req.user": req.user, // 유저 데이터(뒷 부분에서 설명)
    "req._passport": req._passport, // 패스포트 데이터(뒷 부분에서 설명)
  })
})


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

//세팅 페이지 -정보확인
app.get("/setting",(req, res)=>
  {   
    if (req.session.passport != undefined)   //세션에 유저가 있다면
    {   
        console.log("user : ",req.session.passport.user);
        var username = req.session.passport.user;
        res.redirect('/setting.html');
    }
    else
    {
        res.redirect('/login.html');
  }}
);

//세팅 페이지 -정보 수정
app.get("/setting_edit",(req, res)=>
  {   
    if (req.session.passport != undefined)   //세션에 유저가 있다면
    {
        res.redirect('/setting_edit.html');
    }
    else
    {
        res.redirect('/login.html');
  }}
);

//app api 확인
app.all("/app",(req, res)=>
  {   
    console.log(req);
    res.send({"호출": "성공"});
  }
);

//setting
app.all("/get_session",(req, res)=>
  {   
    //console.log("req:");
    //return res;
  }
);

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