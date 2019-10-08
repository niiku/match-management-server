# match-management-server
[![Build Status](https://travis-ci.org/table-tennis-tournament/match-management-server.svg?branch=master)](https://travis-ci.org/table-tennis-tournament/match-management-server) [![App Status](https://argocd.wheel.sh/api/badge?name=ttt-match-management)](https://argocd.wheel.sh/applications/ttt-match-management)

## Websockets

How to use the current websocket experimental feature:

```javascript
function connect() {
  var socket = new SockJS('/api/ttt-management-websocket');
  stompClient = Stomp.over(socket);
  stompClient.connect({}, function (frame) {
    setConnected(true);
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/table', function (table) {
      showGreeting(JSON.parse(table.body).content);
    });
  });
}
```
