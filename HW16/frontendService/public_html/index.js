websocket = new WebSocket('ws://localhost:9999/wsserver');
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
            }
        }catch(e){}
}


document.addEventListener('click', function(e){
    if(e.target && e.target.id == 'signUpButton'){
        var login = document.getElementById("loginTextField").value;
        var password = document.getElementById("passwordTextField").value;
        if(websocket){
            websocket.send(JSON.stringify({
                typeOfMessage: "signUp",
                login: login,
                password: password
            }));
        }
    } else if(e.target && e.target.id == 'signInButton'){
        var login = document.getElementById("loginTextField").value;
        var password = document.getElementById("passwordTextField").value;
        if(websocket){
            websocket.send(JSON.stringify({
                typeOfMessage: "signIn",
                login: login,
                password: password
            }));
        }
    } else if(e.target && e.target.id == 'backToMainPageButton'){
        if(websocket){
            websocket.send(JSON.stringify({
                typeOfMessage: "backToMainPage"
            }));
        }
    }
});