import {User} from './User.js';
import {Vector3} from './scripts/Vector3.js';
import express from 'express';
import http from 'http';
import {Server} from 'socket.io';

let app = express();
let server = http.Server(app);
let io = new Server(server);

const users = [];

server.listen(8080, function () {
    console.log('Server is running on port');
});

io.on('connection', function (socket) {
    socket.emit('USER_ID', socket.id);
    let user = new User(
        socket.id, 
        "Name " + socket.id, 
        new Vector3(
            (Math.floor(Math.random() * 10) + 0) / 10, 
            0, 
            (Math.floor(Math.random() * 10) + 0) / 10));
    users.push(user);
    socket.broadcast.emit('ADD_USER', user);
    socket.emit('ADD_USERs', users);
    socket.on('disconnect', function () {
        console.log('disconnect')
        socket.broadcast.emit('user-disconnect', socket.id);
        users.forEach(function (u, i) {
            if (u.id == socket.id) {
                users.splice(i, 1);
            }
        })
    });
});

io.on('error', function (err) {
    console.log(err);
});
