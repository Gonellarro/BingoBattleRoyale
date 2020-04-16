var socket = new WebSocket("ws://localhost:8080/BingoWeb.2.0/MenuControler");
socket.onmessage = onMessage;

function onMessage(event) {
    var log = document.getElementById("log");
    var data = JSON.parse(event.data);
    log.value = data.value;
}
