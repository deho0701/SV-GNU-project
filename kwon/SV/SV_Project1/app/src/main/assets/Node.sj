var express = require('express')
var http = require('http');
var app = express();
var bodyParser = require('body-parser')

app.use(bodyParser.urlencoded({extended: false}))
app.get(`/`, (req, res) => {
  console.log(req.query);
  res.send({"result": "GET 호출"});
})

app.post(`/`, (req, res) => {
  console.log(req.body);
  res.send({"result": "POST 호출"});
})

app.put(`/:id`, (req, res) => {
  console.log(`내용 PrimaryKey : ${req.params.id}`)
  console.log(req.body);
  res.send({"result": "UPDATE 호출"});
})

app.delete(`/:id`, (req, res) => {
  console.log(req.params.id);
  console.log(req.path)
  res.send({"result": "DELETE 호출"});
})

app.listen(5050, () => {
  console.log(`서버 실행, 포트 번호 5050`);
});