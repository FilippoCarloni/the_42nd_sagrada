# The Connection package

## Introduction
In this section of the documentation we explain how is dived the Connection package.
Also, all the  implementation choice are detailed described here.  
The connection package is divided in five packages:  
   - `client` package
   - `server` package
   - `constraints` package
   - `rmi` pakage
   - `socket` package  
All the implementation chooses are based on the fact that we have chose to implements a multi-match server.  
It means that the server have to manage more games at the same time. So, all the server architecture is developed keeping in mind that all the actions must be parallelized in the more efficient way.    
Also to granted the maximum achievable performance, where it is was possible, the code is implemented using functional programming with paralleled streams.  
Finally, the server prints all its logs information in the output of the console.  
 
## The constraints package
In this package are contained all the classes containing the constants and settings needed by all the classes in the connection package.  
In particular there are:
  - The messages that the server sends to the clients and the messages that the server prints in his internal logs.
  - The connection commands.
  - The special commands.
  - The general settings of the all connection package.  
  
The special commands are special actions that the user can do in a match. It is necessary for the server know if the user try to do this particular action,  
because they comport different behavioral based on the status of the match.  
The special commands are:
  - `undo`, it permits to undo the last game action, if it is possible.
  - `redo`, it permits to redo the last undoes action, it it is possible.
  - `pass`, it permits to pass a turn game, if it is possible.  
  
The connection command are all the actions that a player can send to the server. Based on the these actions, the server performs a specif task.      
They are divided in:
  - `action`, it permits to do the specified game action after the command.
  - `login`, it permits to login in server.
  - `quit`, it closes the connection with the server.
  - `wiew`, it asks to the server a game status, if the player are playing.
  - `window`, it sets the window for the game.
  - `play`, it asks to the server for a game.
  - `restore`, it restores a previously created session.  

All this connection command are detailed explained in the protocol description documentation.

## The client package
The client package contains all the classes necessary to manage the client side of the connection.  
The `ClientStatus` class permits to save and to load player sessions from file. This permits to create a session and to restore it successively.    
The essentially class for the client side is the `ConnectionManager`. This particular class manages all the directly iterations between the client and the server.  
It permits to choose the connection type, after that the class sett al the necessary to communicate with the server and all the details are hidden to the final user.  
The `MessageBuffer` class permits to save in a synchronized way all the messages that the server sends to the client and successively read the messages.  
The `RemoteObserver` is the remote interface to use with rmi connections, it contains the methods that the server calls to check if the client is alive or to update the client.  

Here is reported a schema that describes the connection architecture from the client side prospective.  
```
                                       +-------------+
                                       |             |
                                       |   Client    | 
                                       |             |
                                       +-------------+ 
                                             |
                                             |  connection type chose
                                             |
                                             v 
                                     +-------------------+
                                     | ConnectionManager |
                                     +-------------------+
                                           /    \
                                          /      \
                                         RMI   socket(TCP) 
                                          |      |
                                          |      |  
                                       +-------------+
                                       |             |
                                       |   Server    | 
                                       |             |
                                       +-------------+ 

```
## The server package
The server package is the central package of the connection package. Is the central package because it is the core part of the all server architecture.  
Here is also located the class executable of the server.  
The `CentralServer` class menages all the basic operations:
  - `login`, logs a new user in the server.
  - `restore`, restores an user session previously created.
  - `get game`, asks for a game, if the user that requires the game was already enrolled in an not ended game the server returns the old match. 
  - `close a game`, usable only internally by the `GameController` class to close an ended match.   
  
The `GameController` menages all the iteration with a specific instance of one game.  In particular this class have the references to only the player that are playing in the match that it controls. This fact permits to maximize the parallelism because a maximum of four player is permitted and so the player are grouped in a small parallized match.  
The `LobbyManger`manages the waiting area pre game, the lobby. This permits to the server to create small parallelized lobby before to start a new match with the player entered in the lobby.  
A match is started if the specified rules of the documentation are covered.  
An user, after it have connected to the server, is identified how a `OnLinePlayer`.  
The `OnLinePlayer` in fact contains the necessary information to identify the user, to contact it to check if it is alive and a references to the effective player references usable in the matches.  
The `ServerSession` generate the univocally right session identify for the `OnLinePlayer`(See the protocol documentation). 

## The socket package
The socket package contains the two fundamental classes to create and to manage a multi-thread socket server.  
The `ServerSocket` class creates a server socket that listens in a particular port for all incoming new connection over TCP.  
The port is loaded from the `Settings` class.
After a new connection is accepted by the server socket, the connection stream is redirected in a new class that runs in a parallel thread, the `RemoteClient`.
This is done to overcome the issues that not permits to the central server socket to listen multiple connection in a single thread in a responsive way.
The `RemoteClient` class menages all the iterations with the client. In particularly, it elaborates all the connection commands sended by the clients and executes the commands directly on the server. This is possible thanks to the fact that a `RemoteClient` have the reference to the `CentralServer` instance.  
Here is reported a little schema with an iteration example.
```
                   Server side            |              Client side
                  +-------------+         |           +----------------+
                  | SeverSocket |         |           |     Client     |
                  +-------------+         |           +----------------+
                          |                      connection setup|
                          |<-------------------------------------|
                          |                  ok for setup        |
                          |------------------------------------->|
                          |                                      |
                         
                                         ...... 
                                         
         Starts a thread for this stream, the iteration is moved in another class.
                    Server side            |              Client side
                  +--------------+         |           +----------------+
                  | RemoteClient |         |           |     Client     |
                  +--------------+         |           +----------------+
                          |                      <action command>|
                          |<-------------------------------------|
                          |                 update or response   |
                          |------------------------------------->|
                          |                                      |
                                                   
                        
```

## The RMI package
The RMI package contains all the class regarding the RMI connection part.
The `Lobby` interfaces is directly bind on the RMI registry. it contains all the method to create a correct connection with the server through the RMI connection.
The `GameMenager` interfaces is the RMI reference for the game controller.
The `WrappedObserver` is a wonderful class, it essentially permits to use transparently a `RemoteObserver` because it hidden under a `GameObserver`.
This fact is possible thanks to the fact that the class wraps accurately a `RemoteObserver` and manages all the iteration over the RMI connection.

Here is reported a little schema with an iteration example.
```
                 Server side                                 |              Client side
+--------+                          +------------+           |           +----------------+
| Server |                          | WrappedObs |           |           | RemoteObserver |
+--------+                          +------------+           |           +----------------+
   |                                      |                                      |
   | update                               |                                      |
   |------------------------------------->|                           update     |
   |                                      |------------------------------------->|
   |  alive                               |                                      |
   |------------------------------------->| ping                                 |
   |                                      |------------------------------------->|
   | alive or not                         |                                      |
   |<-------------------------------------|                                      |
   |                                      |                                      |
```

The `GameMenager` are exposed thought the RMI thanks to the `RMI Factory Pattern`.
With this pattern the `CentralServer` is like a big factory that generates new `GameControllerWrapper` in which are contained the `ConcreteGameManager`.
The `ConcreteGameManager` contains the `GameController` references and it this class is the `skeleton` not bind directly in the RMI registry, but accessible only with the remote references returned by the `getGame` method.  
Here there is a representation schema of the RMI structure.  
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
  |                      v
 +--------------+     +--------------+                
 | Lobby        |     | GameManager  | 
 +--------------+     +--------------+              
  Stub for the lobby   Stub for the game                                     Client side    
             
```         

This approach permit us to have best improvements in the parallelism with multiple clients.  
in facts, the RMI clients access in parallel only to the Lobby skeleton, but this is only for a short time. The `Lobby` performs small actions, this permits to reduce minimally the bottleneck in the central point references of the server.  
In the other hand, the skeleton of the game controller are accessed in parallel by max four users. This is granted by the fact that a match can have  a maximum of for players.
So, all the users have a references to the `Lobby` skeleton, but only a maximum of four players have a references to only one of the active game controller.  
This permits a maximum parallelism in accord to ours multi-match implementation.