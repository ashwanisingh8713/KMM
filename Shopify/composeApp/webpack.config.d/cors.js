var express = require('express')
var cors = require('cors')
var app = express()

//var whitelist = ['http://192.168.14.2:8080/',]
//
//cors.options = {
//    origin: whitelist,
//    methods: ['GET', 'POST', 'PUT', 'DELETE', 'OPTIONS'],
//    allowedHeaders: ['Content-Type', 'Authorization', 'X-Requested-With', 'X-HTTP-Method-Override', 'Accept', 'Origin'],
//    exposedHeaders: ['Content-Range', 'X-Content-Range', 'X-Total-Count', 'X-Page-Count', 'X-Page-Number', 'X-Page-Size', 'X-Page-Offset',],
//    credentials: false,
//    maxAge: 86400,
//    preflightContinue: true,
//    optionsSuccessStatus: 204,
//    allowedHeaders: 'Content-Type, Authorization, X-Requested-With',
//
//}





//
//var whitelist = ['http://localhost:8081/',]
//var corsOptions = {
//  origin: function (origin, callback) {
//    if (whitelist.indexOf(origin) !== -1) {
//      callback(null, true)
//    } else {
//        callback(new Error('Not allowed by CORS'))
//    }
//  }
//}
//
//app.post('*', cors(corsOptions), function (req, res, next) {
//    res.json({msg: 'This is CORS-enabled for a whitelisted domain.'})
//})
//
//app.listen(8081, function () {
//    console.log('CORS-enabled web server listening on port 80')
//})