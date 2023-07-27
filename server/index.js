let app = require('express')();
let server = require('http').Server(app);
let io = require('socket.io')(server);

server.listen(8080, function () {
    console.log('Server is running on port');
});

io.on('connection', function (socket) {
    console.log('a user connected');

    socket.emit('USER_ID', {id: socket.id});
    socket.on('disconnect', function () {
        console.log('user disconnected');
    });
});

io.on('error', function (err) {
    console.log(err);
});