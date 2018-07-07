# The Connection package

- [Introduction](#introduction)
- [The constraints package](#the-constraints-package)
- [The client package](#the-client-package)
- [The server package](#the-server-package)
- [The socket package](#the-socket-package)
- [The RMI package](#the-rmi-package)
  - [RMI policy issues](#rmi-policy-issues)
## Introduction

In this section of the documentation we explain how the connection package is dived.
Also, all the  implementation choices are detailed and described here.  
The connection package is divided in five packages:  
   - `client` package
   - `server` package
   - `constraints` package
   - `rmi` package
   - `socket` package  

All the implementation choices are based on the fact that we have chosen to implements a **multi-match** server.  
It means that the server have to manage more games at the same time. So, all the server architecture is developed keeping in mind that all the actions must be parallelized in the more efficient way.    
Also to grant the maximum achievable performance, where it is was possible, the code is implemented using **functional programming** with **parallel streams**.  
Finally, the server prints all its logs information in the output of the console.  
 
## The constraints package

In this package are contained all the classes containing the constants and settings needed by all the classes in the connection package.  
In particular there are:
  - The messages that the server sends to the clients and the messages that the server prints in his internal logs
  - The connection commands.
  - The special commands.
  - The general settings of all connection packages. 
  
The special commands are special actions that the user can do in a match. It is necessary for the server know if the user try to do this particular action, because they imply different behaviors based on the status of the match.  
The special commands are:
  - `undo`- it allows the player to undo the last game action, if it is possible.
  - `redo`- it allows the player to redo the last undoes action, it it is possible.
  - `pass`- it allows the player to pass a turn game, if it is possible.  
  
The connection command are all the actions that a player can send to the server. Based on the these actions, the server performs a specif task.      
They are divided in:
  - `action`- it allows to do the specified game action after the command.
  - `login`- it allows to login into the server.
  - `quit`- it closes the connection with the server.
  - `wiew`- it asks to the server a game status, if the player are playing.
  - `window`- it sets the window for the game.
  - `play`- it asks to the server for a game.
  - `restore`- it restores a previously created session.  

All this connection command are detailed explained in the protocol description documentation.

## The client package
The client package contains all the classes necessary to manage the client side of the connection.  
The `ClientStatus` class allows to save and load player sessions from file. This allows to create a session and to restore it successively.    
The essentially class for the client side is the `ConnectionManager`. This particular class manages all direct iterations between the client and the server.  
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
                                             |  connection type choice
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
The `CentralServer` class manages all the basic operations:
  - `login`- logs a new user into the server.
  - `restore`- restores an user session previously created.
  - `get a game`- asks for a game, if the user that requires the game was already enrolled in an not ended game the server returns the old match. 
  - `close a game`- usable only internally by the `GameController` class to close an ended match.   
  
The `GameController` manages all the iteration with a specific instance of one game.  In particular this class have the references to only the player that are playing in the match that it controls. This fact permits to maximize the parallelism because a maximum of four player is permitted and so the player are grouped in a small parallized match.  
The `LobbyManger`manages the waiting area pre game, the lobby. This permits to the server to create small parallelized lobby before to start a new match with the player entered in the lobby.  
A match is started if the specified rules of the documentation are covered.  
An user, after it have connected to the server, is identified how a `OnLinePlayer`.  
The `OnLinePlayer` in fact contains the necessary information to identify the user, to contact it to check if it is alive and a references to the effective player references usable in the matches.  
The `ServerSession` class generates the unequivocally right session identifier for the `OnLinePlayer`(See the protocol documentation). 
The `WrappedGameController` class contains the reference to the `GameController` local references and the `GameManager` references to the **RMI** skeleton. This permit to get the right reference in the right scope(**RMI** or **socket**).  
The `GameObserver` is the the interface reference for the **server** package. In fact It permits to have a central point of conjunction for **RMI** and **socket**.   
So, a single instance of the `CentralServer` class is create for a single execution of the server.  This represent a bottleneck, but it is a little issue because it performs only single and rapid operations, and all the operations are performed with **parallel stream** if possible and the synchronised points are reduced to the essential.
The second point of the `server` in which is necessary achieve the maximum parallelism performances is in the `GameController` instance. In fact the players enrolled in a match have a synchronised access to this instance and to all its methods. In this case there is a difference refspect to the previously explained point of bottleneck. In fact, in a match can access a maximum of four player and all the game are managed how different instance, so the parallel issue is reduced to the minimum. The third point of bottleneck are the `LobbyManager` instance. This case have the same solution of the `GameController`, because the `LobbyManager` instance have the same player of the future `GameController` instance.  
Here is reported a general high level schema of the **server** architecture with Client 1 with **RMI** connection and the Client 2 **socket** connection.

```
                         +----------------+    +----------------+
            +----------->|   lobby        |--->| CentralServer  |<------------------------+    Server side
            |            +----------------+    +----------------+                         |
            |                                       /  \                                  |
            |                                      /    \                                 |
            |                                     /      \     new games generation       |
            |                                    /        \                               |
            |                                   /          \                              |
            |             +-----------------------+        +------------------------+     |
            |             | GameControllerWrapper |  ....  | GameControllerWrapper  |     |
            |             +-----------------------+        +------------------------+     |                  
            |                      |             \                                        |
            |                      |              \                                       |
            |                      v               \                                      |
            |              +-------------------+    \                                     |
            |              |ConcreteGameManager|     \                                    |
            |              +-------------------+      \                                   |
            |                      |                   \                                  |
            |                      |         +----------------+                           |
            |                      |         | GameController |                           |
            |                      |         +----------------+                           |
            |                      |                   |                                  |
            |                      |                   |                                  |
            |                      |           +--------------+                           |
            |                      |           | RemoteClient |---------------------------+
            |                      |           +--------------+
            |                      |                   |
            |                      |                   |
----------------------------------------------------------------------------------------------------------------------  
            |                      v                   v
            |              +-------------+      +-------------+ 
            |              |             |      |             |                        Client side  
            +--------------|   Client 1  |      |   Client 2  | 
                           |     RMI     |      |    socket   |
                           +-------------+      +-------------+
```

Here is represented an high level diagram that show how **RMI** and **socket** are a single point of entry with all the class of the `sever` package.
```
                                       +-------------+
                                       |             |
                                       |   server    | 
                                       |   package   |
                                       |             |
                                       +-------------+ 
                                             |
                                             | 
                                             |
                                             v 
                                     +----------------+
                                     | GameController | common interface between RMI and socket 
                                     +----------------+
                                           /    \
                                          /      \
                                         RMI   socket(TCP) 
                                          |      |
                                          |      |  


```   
           
## The socket package
The socket package contains the two fundamental classes to create and to manage a multi-thread socket server.  
The `ServerSocket` class creates a server socket that listens in a particular port for all incoming new connection over TCP.  
The port is loaded from the `Settings` class.
After a new connection is accepted by the server socket, the connection stream is redirected in a new class that runs in a parallel thread, the `RemoteClient`.
This is done to overcome the issues that not permits to the central server socket to listen multiple connection in a single thread in a responsive way.
The `RemoteClient` class manages all the iterations with the client. In particularly, it elaborates all the connection commands sended by the clients and executes the commands directly on the server. This is possible thanks to the fact that a `RemoteClient` have the reference to the `CentralServer` instance.  
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
  +---------------+       +--------------+         |           +----------------+
  | CentralServer |<------| RemoteClient |         |           |     Client     |
  +---------------+       +--------------+         |           +----------------+
  +----------------+         |     |                      <action command>|
  | GameController |<--------+     |<-------------------------------------|
  +----------------+               |                 update or response   |
                                   |------------------------------------->|
                                   |                                      |
                                                        
                                                   
                        
```

## The RMI package
The RMI package contains all the class regarding the RMI connection part.
The `Lobby` interfaces is directly bind on the RMI registry. it contains all the method to create a correct connection with the server through the RMI connection.
The `GameMenager` interfaces is the RMI reference for the game controller.
The `WrappedObserver` is a wonderful class, it essentially permits to use transparently a `RemoteObserver` because it hidden under a `GameObserver`.
This fact is possible thanks to the fact that the class wraps accurately a `RemoteObserver` and manages all the iteration over the RMI connection.

Here is reported a little schema with an of `WrappedObserver` iteration example.
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
  |                      |
  |                      v
 +--------------+     +--------------+                
 | Lobby        |     | GameManager  | 
 +--------------+     +--------------+              
 Stub for the lobby   Stub for the game                                     Client side    
             
```         

This approach permit us to have best improvements in parallelism with multiple clients.  
In facts, the RMI clients access in parallel only to the Lobby skeleton, but this is only for a short time. The `Lobby` performs small actions, this permits to reduce minimally the bottleneck in the central point references of the server.  
On the other hand, the skeleton of the game controller are accessed in parallel by max four users. This is granted by the fact that a match can have  a maximum of for players.
So, all the users have a references to the `Lobby` skeleton, but only a maximum of four players have a references to only one of the active game controllers.  
This permits a maximum parallelism in accord to ours multi-match implementation.

### RMI policy issues
**RMI** has with some virtual machine environments policies restriction that do not peremtis to work correctly.  
We have overcame these issues in much of these case, forcing the IP of the client in client application with the file setting of it.  
Also the **RMI** protocol permits to work in LAN only if the correctly **property** is configured.  
the specific java property is: `"java.rmi.server.hostname"`  configured with the proper IP of the client or server, depends who is the server for the **RMI** object.