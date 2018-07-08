# Communication Protocol between Client and Server

## Description
This document contains the description of the rules and the messages exchanged between clients and server.
For testing purposes it is possible to test the socket connection with the `telnet` command.

## The server messages
Before starting the overview on the communication protocol is important to explain the messages sent by server to the clients for both che communication methods. So all the next paragraphs are explained with the decoded messages for simplify the explanation.  
## Socket connection

### Default Port number

The communication over socket connection uses by default the port number **8001**. It can be modified through `res/network_config/constraints.config` file.

### The socket messages send between client and server
All the messages sent by the client and the server are terminated with `<CR>` (`\n`). All messages can generate an error response if incorrectly typed or sent while the session cannot accept that command.

The client can send this messages:

|Client Message |Server Response|Response Description|
|:---|:---|:---|
|`login <username><CR>`   |`SessionID: <SessionID>`   |See [The SessionID](#the-sessionid-mechanism) paragraph |
|`restore <SessionID><CR>`   |`NewSessionID: <SessionID>`   |Generates and sends a new SessionID *****|
|`action <game_action><CR>`   |`message_action`   |The message can be positive (legal action) or negative (illegal action or it's not the player's turn)|
|`play<CR>`   |`window_choices`   |If there are not enough players to start a match, sends a waiting message, otherwise sends a Game Board string|
|`view<CR>`   |Send a string contains a game status, if the player is current playing   |The message contains the Game Board string|
|`quit<CR>`   |  `nothing`  | Quit the connection with the server|
|`window <n><CR>`   | `noting`  |  Set the <n> window before to start a game.  The <n> reppresent the number of the window shows before to start a game.  |

  *****`restore <SessionID><CR>`, the server restores the status of the player associated at the `<SessionID>`, and it generates a new **SessionID** of that player and it responds with a message `NewSessionID: <SessionID>`.

## Game session over socket example
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
   |                     window 3         |                                      |
   |------------------------------------->|                      window 4        |
   |                                      |<-------------------------------------|
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
   | login Foo                            |
   |------------------------------------->|
   |                                      |
   |    SessionID:Foo15263946694325825660 |
   |<-------------------------------------|
   |                                      |
   | quit                                 |
   |------------------------------------->|
   |                                      |

  ...                                    ...
   |                                      |
   | restore Foo15263946694325825660      |
   |------------------------------------->|
   |                                      |
   | NewSessionID:Foo15263967711523663762 |
   |<-------------------------------------|
   |                                      |
   |                    restoring game... |
   |<-------------------------------------|
   |                                      |
   |                         <GameStatus> |
   |<-------------------------------------|
   |                                      |
```
## RMI connection

### Default Port number

The communication over socket connection uses by default the port number **8002**. It can be modified through `res/network_config/constraints.config` file.

### Communication through RMI
**RMI** is based over remote calls to remote objects.  Thanks to this fact, the communication over **RMI** is managed by the Java environments and its protocols. So it necessary only to define the protocol for the name of the exposed objects.  In particular, the name of the exposed object in the **RMI** registry is `Lobby`. This object is directly bind directly on registry and so is visible by all the clients how a single point.
This object have the methods necessary to perform basic operations for the registration and to get a game (See connection_and_controller_doc.md).  
The server over **RMI** connection exposes also all the game controllers of the created matches, but this time the the game controllers are not directly bind in the **RMI** registry. The server in facts, exposes this object through the remote reference binder on dynamic ports and it gives it only tho the correct clients.  This is done for security reasons and both for dimensional reasons, the exposed objects potencially can be a big number.  
So, only the players of a specific game have the right game controller and no other one can have access to it. 
Here is reported an high level diagram of the **RMI** exposed objects by the server.

```
    Skeleton bind on RMi registry
               +----------------+    +----------------+
  |----------->|   lobby        |--->| CentralServer  |                       Server side
  |            +----------------+    +----------------+
  |                                       /  \
  |                                      /    \ 
  |                                     /      \     new games genration
  |                                    /        \
  |                                   /          \
  |             +-----------------------+        +------------------------+
  |             | GameControllerWrapper |  ....  | GameControllerWrapper  |
  |             +-----------------------+        +------------------------+                       
  |                      |
  |                      |
  |                      v                                   
  |              +-------------------+
  |              |ConcreteGameManager| Skeleton accessible through the remote references on dynamic ports
  |              +-------------------+                            
  |                      |
  |                      |
  |                      v
------------------------------------------------------------------------------------------------  
  |                      |
  |                      |
  |                      v
 +--------------+     +--------------+                
 | Lobby        |     | GameManager  | 
 +--------------+     +--------------+              
 Stub for the lobby   Stub for the game                                     Client side    
```

The clients are authenticated all the time thanks to the `session` mechanism (See [The SessionID](#the-sessionid-mechanism) paragraph).  Thanks to this mechanism to authenticate the clients, all the remote calls that need an authentication have a parameter in which the clients put the **sessionID**.  
This permit to overcome the stateless of the remote calls.  
Also the clients exposed an remote object: `RemoteObserver`.  This remote object permits to the server to have a remote reference to all the clients and so it can send updates or to check if the are alive or not.  Obviously the clients do not exposed this object through an **RMI** registry but directly through the remote reference one random dynamic port. So only the server have the clients remote references.  
This fact comports that booth the server and the clients have to do the role of the "server" for the exposed objects.  
This architecture to works in **LAN** need to sets the `"java.rmi.server.hostname"` java property in the host that exposed one or more remote objects with its correct IP, so it must be set in the clients and in the server.
Obviously, the
## The SessionID mechanism
The **SessionID** is used to identify uniquely the client after it is logged. It is randomly generated and it is unique for each player.
The SessionID is generated with a specific rule:
```
SessionID=<username><current_time_of_the_server_13_digits><randomly_generate_number_max_7_digits>
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