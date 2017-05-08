function getPlayerToken(player,gameId,channel,callback){
    var das = web3.eth.contract(dasABI).at(dasAddress);
    player = "0x" + player;
    token = das.getPlayerToken(player,gameId);
    if(token == '0x'){
        balance = getEtherBalance(player)
        if(web3.fromWei(balance,"ether") < 1){
            callback("No enough ether for Transaction",null)
        }else{
            das.InitGameToken().watch(function(error,result){
                console.log("player: ",result.args._seller,",gameId: " , result.args._gameId,",token: " , result.args._asset);
                token = result.args._asset;
                if(player == result.args._seller && gameId == result.args._gameId){
                    callback(null,deserializeToken(token));
                    if(channel.length == 42){
                        das.Channel().watch(function(error,result){
                            console.log("SetChannel --> player: " + result.args._player + ", gameId: " + result.args._gameId + ", channel: " + result.args._channel)
                            das.Channel().stopWatching();
                        });
                        das.channel(channel,gameId,{from:player,gas:'4700000',gasPrice:20000000000},function(error,tx){
                            console.log("das.chanel-->error:" + error +", txHash:" + tx)
                        })
                    }
                }
            })
            das.initGameToken(gameId,{from:player,gas:'4700000',gasPrice:50000000000},function(error,tx){
                console.log("das.initGameToken-->error:" + error + ", txHash:" + tx)
            })
        }
    }else{
        callback(null,deserializeToken(token));
    }
}

function deserializeToken(tokens){
    var number = tokens.length / 208;
    tokens = toekns.substr(2)
    var ret = []
    for(var i = 0 ; i < number ; i++){
        var t = tokens.substr(i * 208, 208)
        var token = "0x" + t.substr(0,64)
        var aliasName = "0x" + t.substr(64,64)
        var gameId = web3.toDecimal("0x" + t.substr(128,64))
        var isOnSell = web3.toDecimal("0x" + t.substr(192,16))
        var obj = {
            "token" : token,
            "alias" : aliasName,
            "gmaeId" : gameId,
            "isOnSell" : isOnSell,
        }
        ret.push(obj)
    }
    return ret
}
function getEtherBalance(player){
    balance = web3.eth.getBalance(player);
    return balance
}

function getUGTokenBalance(player){
    player = "0x" + player;
    var ugToken = web3.eth.contract(ugTokenABI).at(ugTokenAddress)
    return ugToken.balanceOf(player)
}

function getPlayerAddress(){
    return global_keystore.getAddresses()[0];
}