#The Connection package

##Introduction
In this section of the documentation we explain how is dived the Connection package.
Also, all the  implementation choice are detailed described.
The connection package is divided in five packages:  
   - `client` package
   - `server` package
   - `constraints` package
   - `rmi` pakage
   - `socket` package
   
##The constraints package
In this package are contained all the classes containing the constants and settings needed by all the classes in the connection package.
In particular there are:
  - The messages that the server sends to the client and the message that the server prints in his internal logs.
  - The connection command.
  - The special commands.
  - the general settings of the all connection package.
the special commands are special action that the user can do in a match. It is necessary for the server know if the user try to do this particolar action, 
because they comport different behavioral based on the status of the match.
The special commands are:
  - `undo`, it permits to undo the last game action if it is possible.
  - `redo`, it permits to redo the last undoes action, it it is possible.
  - `pass`, it permits to pass a turn game, if it is possible.
The connection command are all the actions that a player can send to the server. Based on the this action, the server performs a specif task.  
They are divided in:
  - `action`, it permits to do the specified game action after the command.
  - `login`, it permits to login in server.
  - `quit`, it closes the connection with the server.
  - `wiew`, it asks to the server a game status, if the player are playing.
  - `window`, it sets the window for the game.
  - `play`, it asks to the server for a game.
  - `restore`, it restores a previously created session.
All this connection command are detailed explained in the protocol description documentation.

##The client package
The client package contains all the class necessary to manage the client side of the connection.  
The `ClientStatus` class permits to save and to load player session from file. This permits to create a session and to restore it successively.  
The essentially class for the client side is the `ConnectionManager`. This particular class manages all the directly iteration between the client and the server. 
It permits to choose the connection type, after that the class sett al the necessary to communicate with the server and all the details are hidden to the final user.
The `MessageBuffer` class partits to save in a synchronized way all the messages that the server sends to the client and successively read the messages.
The `RemoteObserver` is the remote interface to use with rmi connection, it contains the methods that the server calls to check if the client is alive or to update the client.

Here is reported a schema that described the connection architecture.
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
##The socket package
The socket package contains the two fundamental class to create and manages a multi-thread socket server.
The `ServerSocket` class creates a server socket that listens in a particular port for all incoming new connection over tcp.
The port is loaded from the `Settings` class.
After a new connection is accepted by the server socket, the connection stream is redirected in a new class, the `RemoteClient`.
This is done to overcome the issues that not permits to the central server socket to listen multiple connection in a single thread in a responsive way.
The `RemoteClient` class menages all the iteration with the client. In particualr, it elaborates all the conenction command sends by the clients and executes the command directly on the server.

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

#The RMI package
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