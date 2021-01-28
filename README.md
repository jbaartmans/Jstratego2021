# Jstratego2021
Jstratego2021 Java REST service  based on SpringBoot Framework

A simple REST API application that is stateless that implements logic from the famous board game Stratego.
Used as backend for Svelte Stratego made by Casper Broeren.

## Calls

GET - /version  - Return current version of JStratego2021

GET - /game/new - Get a new random gamestate to start a new game with

POST /game/move  - POST a GameInput JSON object. Back-end will evaluate the input Move based on the input GameState and responds with a MoveResponse JSON object. MoveResponse objects tells the front-end the result of the move (e.q. is is valid) and which pieces to remove.

POST /game/ai - POST a GameInput JSON object. An AI will select a move from it's own pieces and returns as MoveResponse JSON object.

See 'resources' for examples of GameInput and MoveResponse