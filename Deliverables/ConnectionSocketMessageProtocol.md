# Communication Protocol over Socket 

## Description 
This document contains the description of the rules and the messages exchanged between clients and server over socket connection.  
For testing purposes is possible to test the socket connection with the `telnet` command.
## The Port number
The communication over socket connection actually uses the port number 8001.
## The messages
All the messages between send by the client and the server are terminated with `<CR>` (\n).
The client can send this messages:
  - `login <username><CR>`, the server responds with an `SessionID: <SessionID>`.
  - `restore <SessionID><CR>`, the server restores the status of the player associated at the `<SessionID>`, and it generates a new **SessionID** of that player and it responds with a message `NewSessionID: <SessionID>`.
  - `action <game_acton><CR>`, the server do a specific `<game_action>` if the user is playing in a match. The Server responds with a messages relative to the action.
  - `play<CR>`, Server searches a match, responding with a match status string.
  - `view<CR>`, Server send a string contains a game status, if the player is current playing. If the player is not enrolling in any match the Server responds with an error message.
### The SessionID mechanism
The **SessionID** is used to identify uniquely the client after he is logged. It is randomly generate and it is unique for each player.  