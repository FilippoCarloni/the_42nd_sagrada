# Graphical User Interface


- [Introduction](#introduction)
- [The General Manager](#the-general-manager)
- [Flux Overview](#flux-overview)
    - [Preliminary Stages](#preliminary-stages)
    - [End Game](#end-game)
- [Main Board](#main-board)
    - [Drawing Classes](#drawing-classes)
        - [Dice Drawer](#dice-drawer)
        - [Round Track Drawer](#round-track-drawer)
        - [Window Frame Drawer](#window-frame-drawer)
        - [Cards Drawer](#cards-drawer)
    - [Behavioural Management Classes](#behavioural-management-classes)
        - [Tool Cards](#tool-cards)
        - [Window Frame Events](#window-frame-events)
    - [Utility Classes](#utility-classes)
        - [GUIColor](#guicolor)
        - [GUIShade](#guishade)
    

## Introduction

This guide explains how the **GUI** has been thought and realized, with implementation and graphical choices. 
The **GUI** has been entirely wrote in FXML language, and later decorated with a stylesheet file; it is launched by a single file, and then, for every screen the user will see, there is a specific controller class, with eventually support classes for drawing the needed elements. Everything in our GUI is **dynamically rendered** from .json files, without loading any image, to make our graphical aspect fully flexible to eventually future modifies to the game rules. Our **GUI** is by default set to a 1280x720 resolution, and is in width fully scalable. 



## The General Manager

Everything into the **GUI** is managed by the `GuiManager` class; these are the main methods:
- `setConnectionType`: called after a click on *Login* button (see [Preliminary stages](#preliminary-stages)), it set the chosen `ConnectionType`.
- `getInstance`: returns an instance of `GuiManager` if is already initialized, or it calls the private constructor to initialize a new one, with the connection type chosen; it implements a `Factory Pattern`.
- `startRefresh`: scheduler, that launch the `update` method one time every 100 milliseconds
- `update`: main method, that receive messages from the server and, basing on the message type, makes different actions; the possibility are:
    - GENERIC_MESSAGE: general message, that will be showed into the *Lobby TextArea* if the player is into the lobby screen, or into the *Game Board TextArea* if the player is into the main board
    - PRE_GAME_CHOICE: message that contains the private objective card of the player, and the four maps between which he has to choose is own; it makes the *Start* button enabled into the lobby scene. It will be parsed into a .json file, and then all maps and the card will be drawn into the window frame choice screen.
    - GAME_BOARD: message with all information about the actual game state; it will be parsed into a .json file, and then used to re-draw entirely the main board; if a player is into the window frame choice or into the lobby scene, the receiving of this message will make the *Go to GameBoard* button enable (if into the window frame choice scene), or will make the *Start* button able to launch directly the game board (if into the lobby scene).
    - ERROR_MESSAGE: message received when a player makes an error while playing, it will be showed into the *Game Board TextArea*.
    - CURRENT_PLAYER: message that contains the current player username; it will be showed into the *Game Board TextArea*.
    - GAME_STATS: end game message, it contains the final score. When it arrives an hidden button, the *Continue* one, will be showed and enabled on the game board, and a "Game ended" message will be showed into the *Game Board TextArea*. The player will be able to go to the End Game screen.



## Flux Overview

In this section is explained how the screens flux has been thought, in order to offer to user an experience comparable to commercial on-line games as much as possible. The screens flux is based on a **click-event** paradigm: every time the user is allowed to change the screen a button will be enabled, and the user will be able to go to the next step.

### Preliminary stages

Before the game starts, there are several steps that the user has to fulfill; for every step a new scene will be showed, and depending on which screen is now opened, the player will have to do some action in order to proceed, or simply wait for a specified time. 
The preliminary stages are:
- `Connection and Login`: first screen showed when a **GUI** client is started, it allows the player to choose his type of connection with the server side and then to login into the game.
- `Lobby`: when the player clicks on the *Login* button, if the connection is established and the username chosen is valid, this screen will be open; here the player will be able to enter into the lobby clicking on the *Enter into Lobby* button: after that into the *Lobby TextArea* messages will be showed, saying who is connected. When a game is ready to start, the *Start* button, disabled, will be enabled
- `Window Frame Choice`: last part of the preliminary stage part. In this stage the player will see his own *Private Objective* card, and four window frames with names and difficulties. The player will be able to choose which one between these four maps will be the map in the game, but there are two things to pay attention to: 
    - the default map is the first one
    - to choose a map different from the firs the player has to click on the *RadioButton* under the map
    - the player has a limited time, set into the .config file, to choose the map; after that time the *Go to GameBoard* button will be enabled and the match will start with the chosen maps (the default one if the player has not chosen any map)

### End Game

After the GAME_STAT message arrives (see [The General Manager](#the-general-manager)), an hidden button on the game board, the *Continue* button, will be showed and enabled, and the player will be able to go to the end game screen, to see the final score.
The score will be showed unordered: there will be players' username, with the points written. To print the points the class will take a .json file from [The General Manager](#the-general-manager), and will split it into a JSONArray of players, containing username and final score.
In this screen there will be a *Return to Lobby* button, that allows the player to stay logged in and starting a new game.


## Main Board

The main board is the **GUI** main screen, the one in which the player is supposed to stay the most of the time he plays, so it has to be graphically pleasant, but also immediately understandable. In order to make this things real, the board has been divided into three main blocks:
- MAIN_PLAYER: at the right of the board, there are all the things the main player needs:
    - *Main Board TextArea*: the TextArea in which all messages will be showed
    - *Action Buttons*: buttons that allow the player to make the UNDO, REDO and PASS actions
    - *Drafted Die*: square in which will be showed the drafted die; it shows not only the main player drafted die, but also the drafted die of another player
    - Main player username, private objective and window frame
- OTHER_PLAYERS : at the left of the board; it contains all other players' window frames, up to four, with the empty slots set in a light grey color
- GAME_ELEMENTS: the central part of the board contains everything is usable by the players:
    - *Dice Pool*: containing all `Dice` usable in the current turn; to pick a die a player has just to click on it
    - *Round Track*: containing all unused `Dice` of the previous turns; it shows only one `Die`, to see all of them the player has just to click on it
    - *Tool Cards*: to use them a player has to click on their name; after the first usage the name will change color, to make understandable that this tool has been already used
    - *Public Objective Cards*: not usable cards, if the tasks described into them are completed by a player, he will receive extra points

### Drawing Classes

All [Main Board](#main-board) elements are dynamically drawn from .json files. For that reason there are a lot of drawing classes, strictly connected to the [Behaviour Management Classes](#behavioural-management-classes), that will draw everything the user can see and interact with. 

#### Dice Drawer

Class that manage all dice drawings, from *Dice Pool* and *Round Track* to every `shadeConstraint` on the window frames. All methods in this class are static, to allow multiple uses without instantiate anything; the methods are:
- `diceFiller`: method to call only when the scene is initialized; it fills an horizontal `GridPane`, given as parameter, with a requested number of `Canvas` and `StackPane`. It is used to:
    - **Fill the *Dice Pool***: in this case on every `Canvas` will be set to send the *PICK* command to the `ConnectionController`, with the current position
    - **Fill other elements**: in this case there will be no action settings on `Canvas`
- `dicePoolReset`: method called every time [The General Manager](#the-general-manager) receives a GAME_BOARD message, to erase every drown die on the *Dice Pool* to fully re-draw them after that. It set every `StackPane` to the *BackGround Color*, and erase everything inside the `Canvas`
- `dicePoolDrawer`: `private` method called from the previous one to completely re-draw the *DicePool*, taking all necessaries information from a .json file passed as input to the previous method
- `dicePointsDrawer`: utility method, used both by the `dicePoolDrawer` and by the `framePainter` into the [Window Frame Drawer](#window-frame-drawer) class; it draws the dice points into the `GraphicsContext` of the specified `Canvas`
- `colorSetter`: utility method, used both by the `dicePoolDrawer` and by the `framePainter` into the [Window Frame Drawer](#window-frame-drawer) class; it set the `StackPane` color, to set the correct dice color

#### Round Track Drawer

Class that manages specifically the *Round Track* drawing. His methods are:
- `roundTrackStartingFiller`: method that fills the *Round Track* with `Canvas` and `StackPane`; called only when the [Main Board](#main-board) is initialized
- `roundTrackUpdate`: method called every time [The General Manager](#the-general-manager) receives a GAME_BOARD message. It erase every drown die on the *Round Track*, and after that it re-draw all of them.
- `seeAllDice`: method that is called with a click on every `Canvas` into the *Round Track*; it opens a new screen, in which all dice present on the *Round Track* will be drawn

#### Window Frame Drawer

Class that manage specifically the *Window Frames* drawing. It draws every *Window Frame* basing both on a .json file, that contains every information needed, and on a `double` that makes scalable the *Window Frame* dimension. His methods are:
- `frameFiller`: method that fills every *Window Frame* with `Canvas` and `StackPane`; called only when the [Main Board](#main-board) is initialized. If it is filling the main player's *Window Frame*, it calls the `clickEventOnWindowFrame` method (see [Window Frame Events](#window-frame-events)), to make the main player able to make actions on it
- `frameReset`: method called every time [The General Manager](#the-general-manager) receives a GAME_BOARD message, to erase every drown die on the *Window Frame* to fully re-draw them after that. It set every `StackPane` to the *BackGround Color*, and erase everything inside the `Canvas`
- `framePainterManager`: method called after a reset, it handles the *Window Frame* drawing or re-drawing
- `framePainter`: method called by the `framePainterManager`, it controls every `constraints` present on the *Window Frame*, and if there are some dice on it and eventually where they are, and then it calls the [Dice Drawer](#dice-drawer) methods, to properly draw the *Window Frame*. If there is some die on the *Window Frame*, it calls the `clickEventOnWindowFrame` method (see [Window Frame Events](#window-frame-events)), to set the correct action  

#### Cards Drawer

Class that manage all cards drawing; It draws every card basing both on a .json file, that contains every information needed. His methods are:
- `drawCards`: method that draws the cards
- `setPublicCards`: method that uses the `drawCards` method to draw every public card on the [Main Board](#main-board); after that, if he is setting a *Tool Card*, he calls the `toolBehaviourSetter` into the [Tool Cards](#tool-cards) class, to set the properly behaviour on it
- `setPrivateCards`: method that uses the `drawCards` method to draw the `privateObjectiveCard` both on *Window Frame Choice* and on his own screen


### Behavioural Management Classes

#### Tool Cards

Class that handle all tool cards behaviours, settings their behaviours. His methods are:
- `toolBehaviourSetter`: main method to handle tool cards behaviour. It discriminates between:
    - *Grozing Pliers*: this tool card allows the player to increase/decrease the drafted die's value; it needs a new small screen to choose the value and it's managed with the `private grozingPliersManagement` method 
    - *Flux Remover*: this tool card allows the player to choose a new value for a new drafted die; it needs a new small screen to choose the value and it's managed with the `private fluxRemoverManagement` method
    - Other tool cards 
- `openScreen`: `private` method used both by `grozingPliersManagement` and `fluxRemoverManagement` methods, to open the new small screen 

#### Window Frame Events

Class that manage all click events on the main player's *Window Frame*. His methods are:
- `clickEventsOnWindowFrame`: Method that will manage click events on window frame, discriminating between "place" and "move" actions:
    - **If there is a die into the selected position**: the local variable `temporaryCommand` will be initialized to `move` plus the row and column position, and will be called the `manageMoveEvents` method, to handle move actions.
    - **If there is not a die into the selected position**: the command will be set to `place` plus the row and column position, to allow the player to put a die into the selected position. 
- `manageMoveEvents`: method called by the previous one; it set on click on every `StackPane` on *Window Frame* to send to the `ConnectionController` to send the `temporaryCommand` concatenated with the next row and column position, to move a die. After that it calls the `resetPlaceAfterClick`, to reset the correct place command 


### Utility Classes

#### GUIColor

*Enum* class, that represents the colors that a Sagrada die can assume. It has five values that it can be assume, and every one has two local fields:
- `id`: an integer, that represents the correspondent (in terms of color) `privateObjective` id; it can assume the following values:
    - 23, corresponding to the *RED* color
    - 24, corresponding to the *GREEN* color
    - 25, corresponding to the *YELLOW* color
    - 26, corresponding to the *BLUE* color
    - 27, corresponding to the *PURPLE* color
- `color`: a `Color` variable (package `javafx.scene.paint`), used by the `setPrivateCards` method (see [Cards Drawer](#cards-drawer)) to color the `privateObjective` card


#### GUIShade
*Enum* class, that represents the values that a Sagrada die can assume. It has six values that it can be assume, and every one has two local fields:
- `value`: an integer, that represents the correspondent die shade
- `coordinates`: an array of double, contained into the `GUIParameters` class, that is used by the `dicePointsDrawer` method (see[Dice Drawer](#dice-drawer)) to draw dynamically every dice point





















