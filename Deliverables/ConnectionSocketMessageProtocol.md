# Communication Protocol over Socket

## Description
This document contains the description of the rules and the messages exchanged between clients and server over socket connection.
For testing purposes it is possible to test the socket connection with the `telnet` command.

## The Port number
The communication over socket connection uses the port number 8001.

## The messages
All the messages between send by the client and the server are terminated with `<CR>` (`\n`). All messages can generate an error response if incorrectly typed or sent while the session cannot accept that command.

The client can send this messages:

|Client Message |Server Response|Response Description|
|:---|:---|:---|
|`login <username><CR>`   |`SessionID: <SessionID>`   |See [The SessionID](### The SessionID mechanism) paragraph |
|`restore <SessionID><CR>`   |`NewSessionID: <SessionID>`   |Generates and sends a new SessionID *****|
|`action <game_action><CR>`   |`message_action`   |The message can be positive (legal action) or negative (illegal action or it's not the player's turn)|
|`play<CR>`   |`match_status`   |If there are not enough players to start a match, sends a waiting message, otherwise sends a Game Board string|
|`view<CR>`   |Send a string contains a game status, if the player is current playing   |The message contains the Game Board string|

  ***** `restore <SessionID><CR>`, the server restores the status of the player associated at the `<SessionID>`, and it generates a new **SessionID** of that player and it responds with a message `NewSessionID: <SessionID>`.

### The SessionID mechanism
The **SessionID** is used to identify uniquely the client after it is logged. It is randomly generated and it is unique for each player.
The SessionID is generated with a specific rule:
```
SessionID=<username><current_time_of_the_server_13_digits><randomly_generate_number_7_digits>
```
For security reasons it is regenerated each time the user sends a `restore` command with its previously generated SessionID.

## Game actions
In this paragraph we will discuss the game actions that can be performed by the players to advance the status of the game.

Only legal actions are executed by the game. Illegal actions are signaled to the player if confirmed (both illegal actions from the game rules prospected and legal actions performed by players not during their turn).

|Action|When is legal|Effect|
|:---|:---|:---|
|`pick <n>`   |A die should not already be picked; a tool card should not be active   |Picks the n-th die from the Dice Pool   |
|`place <n> <m>`   |A die should already be picked; a tool card should not be active   |Place the picked die in the current player's window frame in position (n-th row, m-th column)   |
|`pass`   |If a die was picked, should be placed; if a tool was activated, its effect should already be terminated   |Passes the turn to the next player   |
|`tool <n>`   |The legality of this command is determined by the n-th Tool Card in the Game Board; the player should have enough Favor Points   |Activates the n-th Tool Card   |
|`increment`   |The Grozing Pliers Tool Card should be active   |Increments the value of the picked die   |
|`decrement`   |The Grozing Pliers Tool Card should be active   |Decrements the value of the picked die   |

## Game session example
In this paragraph we present an hypothetical start of communication between Client and Server over Telnet with two actors, Foo and Baz.
```
+-----+                              +--------+                               +-----+
| Foo |                              | Server |                               | Baz |
+-----+                              +--------+                               +-----+
   |                                      |                                      |
   | login Foo                            |                                      |
   |------------------------------------->|                            login Baz |
   |                                      |<-------------------------------------|
   |    SessionID:Foo15263946694325825660 |                                      |
   |<-------------------------------------| Baz15263949356296327344              |
   |                                      |------------------------------------->|
   | play                                 |                                      |
   |------------------------------------->|                                 play |
   |                                      |<-------------------------------------|
   |         waiting for other players... |                                      |
   |<-------------------------------------| waiting for other players...         |
   |                                      |------------------------------------->|
   |                                      |                                      |
  ...                              <Timeout Event>                              ...
   |                                      |                                      |
   |                         <GameStatus> | <GameStatus>                         |
   |<-------------------------------------|------------------------------------->|
   |                                      |                                      |
```

At this point Foo and Baz can perform game actions, as showed in the next example.
```
+-----+                              +--------+                               +-----+
| Foo |                              | Server |                               | Baz |
+-----+                              +--------+                               +-----+
   |                                      |                                      |
   | pick 1                               |                                      |
   |------------------------------------->|                               pick 4 |
   |                                      |<-------------------------------------|
   | place 3 1                            |                                      |
   |------------------------------------->|                It's not your turn... |
   |                                      |------------------------------------->|
   | pass                                 |                                      |
   |------------------------------------->|                                      |
   |                                      |                                      |
   | view                                 |                                      |
   |------------------------------------->|                                      |
   |                                      |                                      |
   |                         <GameStatus> |                                      |
   |<-------------------------------------|                                      |
   |                                      |                               pick 4 |
   |                                      |<-------------------------------------|
   |                                      |                                      |
```

Now we present a `restore` example.
```
+-----+                              +--------+
| Foo |                              | Server |
+-----+                              +--------+
   |                                      |
   | quit                                 |
   |------------------------------------->|
   |                                      |
   |                         Disconnected |
   |<-------------------------------------|
   |                                      |
  ...                                    ...
   |                                      |
   | restore Foo15263946694325825660      |
   |------------------------------------->|
   |                                      |
   | NewSessionID:Foo15263967711523663762 |
   |<-------------------------------------|
   |                                      |
   | play                                 |
   |------------------------------------->|
   |                                      |
   |         waiting for other players... |
   |<-------------------------------------|
   |                                      |
```