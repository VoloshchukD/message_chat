const webSocket = new WebSocket("ws://localhost:9090/chat");

document.getElementById("send").onclick = () => {
    const messageContent = document.getElementById("message").value;
    webSocket.send(messageContent);
};

webSocket.onmessage = (event) => {
    const chatMessage = JSON.parse(event.data);
    renderMessage(chatMessage, chatMessage.currentSession);
};

const renderMessage = (message, isCurrentSession) => {
    const chat = document.getElementById("chat");
    const div = document.createElement("div");

    div.className = isCurrentSession ? "own" : "other";
    div.textContent = `${message.content}`;

    chat.appendChild(div);
    chat.scrollTop = chat.scrollHeight;
};
