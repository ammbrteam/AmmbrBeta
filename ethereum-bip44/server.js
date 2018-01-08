var bodyParser = require('body-parser');
var express = require('express');
var app = express();
app.use(bodyParser.json());
var bitcore = require('bitcore-lib');
var EthereumBip44 = require('ethereum-bip44');
var properties = require('./properties');
var xprivkey = properties.key;
var wallet = EthereumBip44.fromPrivateSeed(xprivkey);
var ethereumjsUtil = require('ethereumjs-util')
var Web3 = require('web3');
let KOVAN_RPC_URL = properties.gethUrl;
let provider = new Web3.providers.HttpProvider(KOVAN_RPC_URL);
let web3 = new Web3(provider);
var web3Utils = web3.utils;

app.post('/getAddress', function(req, res) {



var index =  req.body.index;
console.log("recieved request for :"+ index)
var addressVar = wallet.getAddress(index);
var addressVarChecksum = ethereumjsUtil.toChecksumAddress (addressVar);
console.log( addressVar + " address " + addressVarChecksum);
var successRes = {

index : index,
uniqueAddress : addressVarChecksum
}
 res.end(JSON.stringify(successRes));
});

app.post('/getkey', function(req, res) {

var index =  req.body.index;
console.log("request request recieved")
var pkey = wallet.getPrivateKey(index).toString('hex')
var successRes = {

key : pkey,
index : index
}
  res.end(JSON.stringify(successRes));

});




app.post('/getTransactionStatus', function(req, res) {

var hash = req.body.transactionHash;
console.log("request  recieved for hash :" + hash);

var receipt = web3.eth.getTransactionReceipt(hash).then((result) => {
console.log(result);
var status =  result.status;
var resultStatus =0;
if(status != null && status != undefined){
status = web3Utils.hexToNumber(status);
resultStatus = status;
}
var successRes = {

status  : resultStatus,
hash : hash
};


  res.end(JSON.stringify(successRes));

});









});








            var server = app.listen(8081, function() {
                var host = server.address().address
                var port = server.address().port

                console.log("Example app listening at http://%s:%s", host, port)

});




