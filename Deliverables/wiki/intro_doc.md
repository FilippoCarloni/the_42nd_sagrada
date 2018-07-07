This is **The 42nd Sagrada**'s documentation. 

## What is The 42nd Sagrada

**The 42nd Sagrada** is a university project assigned by Prof. Luca Mottola to the 3rd year Computer Science and Engineering students of Politecnico di Milano. Its goal is to design, write and document a multiplayer videogame based on the tabletop game *Sagrada* of Floodgate Studios. The purpose of the project is demonstrating a solid approach to software development through the application of the principles of Software Engineering.

## How it was realized

We developed The 42nd Sagrada in a group of three people: Alberto Archetti, Paolo Boffi and Filippo Carloni. This is a Java 8 project with Maven integration, written with Intellij.

## What does it offer

The 42nd Sagrada can be played through LAN connectivity by multiple users. It is based on a client-server architecture. The connection can use two different technologies for internet communication: **socket** and **RMI**. RMI is the default choice, but, due to its verbosity and policy restrictions depending on the VM settings, its functionalities can cause some trouble. The quickest fix is to set the client's machine IP in the `res/network_config/constraints.config` under the tag `IP_CLIENT`. Socket on the other hand should work in any scenario.

Our Server is able to host **multiple matches** at the same time, handling client disconnection and reconnections.

The 42nd Sagrada implements a fully functional Sagrada game simulator, playable through **Command Line interface** (`java -jar Client.jar - cli`) or **Graphical User interface** (`java -jar Client.jar -gui`). The game logic is easily extensible through the configuration files: window frames are dynamically rendered (through **vector rendering**) by the user interfaces and can be easily added through a custom syntax; Tool Cards and Public Objective Cards are encoded in JSON syntax, so they can be added, deleted or manipulated just by modifying configuration files (they don't require project recompilation). 

## How to play

### Server

The Server can be launched by `java -jar Server.jar`. Its setting can be found in `res/network_config/constraints.config`:
- `IP_SERVER`: defines the IP of the Server
- `PORT_SOCKET`: defines what port should be used by socket connection
- `PORT_RMI`: defines what port should be used by RMI connection
- `LOBBY_REFRESH-TIME`: defines the time schedule (ns) of the status update of the players currently in lobby (checks for disconnections)
- `TURN_TIME`: defines the maximum duration of a turn (ns)
- `GAME_REFRESH`: defines the time schedule (ns) of the status update of the players currently in lobby (checks for disconnections)
- `LOBBY_WAITING_TIME`: defines the lobby waiting time (ns) before the launch of a new game; during this time players can enter the game
- `WINDOW_WAITING_TIME`: defines the duration (ns) of the pre-game phase in which players choose their widow frame

### Client

The Client can be launched by `java -jar Client.jar -[view_choice]`, where `view_choice` can be substituted with `cli` for Command Line interface or `gui` for Graphical User interface. Its setting can be found in `res/network_config/constraints.config`:
- `IP_SERVER`: defines the IP that the Server, which is hosting the game to be connected to, is currently using
- `PORT_SOCKET`: defines the Server socket connection port number 
- `PORT_RMI`: defines the Server RMI connection port number
- `IP_CLIENT`: identifies the current IP of the client's machine (only in case of RMI issues)