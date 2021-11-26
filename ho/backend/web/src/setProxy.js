const proxy = require('http-proxy-middleware');

module.exports = function (app) {
    app.use(
        proxy('/home',{
            target :'http://localhost:5080/home'
        })
    );

};