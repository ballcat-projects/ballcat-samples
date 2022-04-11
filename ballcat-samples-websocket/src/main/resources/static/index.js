let webSocketClient

function initWebSocketClient(client) {
    webSocketClient = client

    let responseTextDom = document.getElementById('responseText')
    webSocketClient.onmessage = event => {
        responseTextDom.value = responseTextDom.value + '\n' + event.data
    }

    webSocketClient.onopen = event => {
        responseTextDom.value = '连接开启！'
    }

    webSocketClient.onclose = event => {
        responseTextDom.value = responseTextDom.value + '\n' + '连接关闭！'
    }
}

function send(message) {
    if (webSocketClient) {
        if (webSocketClient.readyState === WebSocket.OPEN) {
            webSocketClient.send(message)
        } else {
            alert("发送失败，连接尚未开启！")
        }
    } else {
        alert('websocket 客户端尚未初始化！')
    }
}

function heartbeat() {
    // 正式环境应该定时发送心跳，避免断开连接
    let responseTextDom = document.getElementById('requestText')
    responseTextDom.value = JSON.stringify({type: 'ping'})
}

function sayHello(language) {
    let content = {
        type: 'say-hello',
        language: language
    };
    let responseTextDom = document.getElementById('requestText')
    responseTextDom.value = JSON.stringify(content)
}