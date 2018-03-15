var cacheHit = document.getElementById("cacheHit");
var cacheMiss = document.getElementById("cacheMiss");
var historyOfQuery = document.getElementById("historyOfQuery");
var queryResult = document.getElementById("queryResult");


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

            if(obj.typeOfMessage == 'statisticMessage'){
                cacheHit.innerHTML = obj.cacheHit;
                cacheMiss.innerHTML = obj.cacheMiss;

            }else if(obj.typeOfMessage == 'statusMessage'){
                if(obj.started == 'true'){
                    checkbox.checked = true;
                }else{
                    checkbox.checked = false;
                }

            }else if(obj.typeOfMessage == 'newQuery'){
                var newdiv = document.createElement("div");
                var timeNow = getCurrentTime();
                newdiv.innerHTML = '<p>' + timeNow + ": " + obj.query + '</p>';
                historyOfQuery.appendChild(newdiv);
                historyOfQuery.appendChild(document.createElement("hr"));
                historyOfQuery.scrollTop = historyOfQuery.scrollHeight;

            }else if(obj.typeOfMessage == 'queryResult'){
                var newdiv = document.createElement("div");
                var timeNow = getCurrentTime();
                newdiv.innerHTML = '<p>' + timeNow + ": " + obj.queryResult + '</p>';
                queryResult.appendChild(newdiv);
                queryResult.appendChild(document.createElement("hr"));
                queryResult.scrollTop = queryResult.scrollHeight;
            }
        }catch(e){}
}

function getCurrentTime() {
    var currentDate = new Date();
     return ((currentDate.getHours() < 10)?"0":"") + currentDate.getHours() +":"+
            ((currentDate.getMinutes() < 10)?"0":"") + currentDate.getMinutes() +":"+
            ((currentDate.getSeconds() < 10)?"0":"") + currentDate.getSeconds();
}

var checkbox = document.getElementById("launcherCheckBox");

checkbox.addEventListener('change', function(){
    if(websocket){
        if(checkbox.checked == true){
            websocket.send(JSON.stringify({
                commandToGenerator: "start"
            }));
        }else{
            websocket.send(JSON.stringify({
                commandToGenerator: "stop"
            }));
        }
    }
});

var findAllQueryButton = document.getElementById("findAllQuery");
var findByRandomIdButton = document.getElementById("findByRandomIdQuery");
var saveNewDataSetButton = document.getElementById("saveNewDataSetQuery");
var updateRandomDataSetButton = document.getElementById("updateRandomDataSetQuery");
var deleteByRandomIdButton = document.getElementById("deleteByRandomId");

findAllQueryButton.addEventListener('click', function(){
    if(websocket){
        websocket.send(JSON.stringify({
            commandToGenerator: "findAll"
        }));
    }
});

findByRandomIdButton.addEventListener('click', function(){
    if(websocket){
        websocket.send(JSON.stringify({
            commandToGenerator: "findByRandomId"
        }));
    }
});

saveNewDataSetButton.addEventListener('click', function(){
    if(websocket){
        websocket.send(JSON.stringify({
            commandToGenerator: "saveNewDataSet"
        }));
    }
});

updateRandomDataSetButton.addEventListener('click', function(){
    if(websocket){
        websocket.send(JSON.stringify({
            commandToGenerator: "updateRandomDataSet"
        }));
    }
});

deleteByRandomIdButton.addEventListener('click', function(){
    if(websocket){
        websocket.send(JSON.stringify({
            commandToGenerator: "deleteByRandomId"
        }));
    }
});