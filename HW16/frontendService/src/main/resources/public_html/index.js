var currentUrl = window.location.href;
var arr = currentUrl.split("/");
var hostPort = arr[2];
var arr = hostPort.split(":");
var port = arr[1];

websocket = new WebSocket('ws://localhost:' + port + '/wsserver');

websocket.onopen = function(data){
    console.log("Connected ");
}

websocket.onclose = function(data){
    console.log("Disconnected");
}

websocket.onmessage = function(data){
    console.log("Receive message : " + data.data);
        try{
            var obj = JSON.parse(data.data);

            if(obj.typeOfMessage == 'changeBody'){
                document.body.innerHTML = obj.htmlBody;

            }else if(obj.typeOfMessage == 'updateDbServices'){
                var dbServices = obj.dbServices;
                var dbServicesSelectList = document.getElementById("dbServicesSelectList");

                for(var i = dbServicesSelectList.length - 1; i >= 0 ; i--){
                    dbServicesSelectList.remove(i);
                }

                for(var i = 0; i < dbServices.length; i++){
                    var option = document.createElement("option");
                    option.text = dbServices[i];
                    dbServicesSelectList.add(option);
                }

                dbServicesSelectList.selectedIndex = -1;
            }
        }catch(e){}
}

document.addEventListener('click', function(e){
    if(e.target && e.target.id == 'signUpButton'){
        var login = document.getElementById("loginTextField").value;
        var password = document.getElementById("passwordTextField").value;
        var dbService = document.getElementById("dbServicesSelectList").value;
        if(dbService && dbService != null && dbService.length != 0){
            if(websocket){
                websocket.send(JSON.stringify({
                    typeOfMessage: "signUp",
                    login: login,
                    password: password,
                    dbService: dbService
                }));
            }
        }else{
            alert("Please choose a dbService");
        }
    } else if(e.target && e.target.id == 'signInButton'){
        var login = document.getElementById("loginTextField").value;
        var password = document.getElementById("passwordTextField").value;
        var dbService = document.getElementById("dbServicesSelectList").value;
        if(dbService && dbService != null && dbService.length != 0){
            if(websocket){
                websocket.send(JSON.stringify({
                    typeOfMessage: "signIn",
                    login: login,
                    password: password,
                    dbService: dbService
                }));
            }
        }else{
            alert("Please choose a dbService");
        }
    } else if(e.target && e.target.id == 'backToMainPageButton'){
        if(websocket){
            websocket.send(JSON.stringify({
                typeOfMessage: "backToMainPage"
            }));
        }
    }
});
