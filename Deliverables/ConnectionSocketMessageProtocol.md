# Comunication Protocol over Socket 

## Description 
In this document are descripted the rules and the messages  exchanged between clients and server over socket connection.
For testing porposes is possible to test the socket connection with the telnet command.
## The Port number
The communication over socket connection acctually uses the port number 8001.
## The messages
All the messages betweeen client and server are terminated with <CR> (\n).
The client can send this messages:
  - `login <username><CR>`, the server responds with an SessionID
  - `restore <SessionID><CR>`, the server restores the staus of the player associated at the <SessionID>, and it responds with a new SessionID of that player.
  - `action <game_command><CR>`, the server do a speciic <game_command> if the         user is playing in a match. The Server responds with a messagge.
  - `play<CR>`, Server searches a match, responding with a macth status string.
  - `view<CR>`, Server send a string contains a geme satus, if the player is current playing. If the player is not enrolling in any match the Server responds with an error message.
