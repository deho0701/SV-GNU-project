var express = require('express');
var router = express.Router();

router.get('/',(req, res, next)=>{
    res.json(users);
    console.log(users);
  });
  
  module.exports = router;