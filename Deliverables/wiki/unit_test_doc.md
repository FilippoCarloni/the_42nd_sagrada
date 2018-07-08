# Tested functionality

The 42nd Sagrada was implemented with test-driven development, so, as a new model class appeared, a JUnit test class was implemented in order to certify the correct behavior of the produced class in most testable scenarios. This document contains an aggregate list of functionality tested correctly with JUnit 5 that certify correct behavior of implemented classes.

## The TestHelper class

The `TestHelper` class provides the following static and reusable methods in order to make the code more general and readable:
- `Game init(String fileName)`: loads game data from a JSON file called `fileName` in the test `res` folder and returns a `Game` object on which can be performed commands, as described in the next methods
- `void wrappedIllegalCommand(Game g, Player player, String cmd)`: certifies that the command `cmd` executed by `player` on the game `g` is not rule-compliant and therefore throws an `IllegalCommandException` with a message that explains, from Sagrada rules point of view, the reason of the failure
- `void wrappedLegalCommand(Game g, Player player, String cmd)`: certifies that the command `cmd` executed by `player` on the game `g` is rule-compliant and therefore doesn't throw any exception; this method tries to execute the command multiple times, then undoes and redoes a random number of times the game status and finally checks that the resulting game status can be correctly encoded in JSON and is equal to the starting one
- `void areWindowFramesEqual(WindowFrame w1, WindowFrame w2)`: asserts that the passed window frames share the same constraints and contain the same dice in the same slots
- `List<Player> getPlayerList(int numOfPlayers, boolean initialize)`: generates a list of players of size `numOfPlayers`; these players can be unitialized (window frame and private objective are `null`) or they can be initialized (window frame and private objective are randomly extracted from the available pool)
- `void fillMap(WindowFrame w)`: fills the window frame `w` with randomly extracted dice

## Verified functionality

- **Test class name**: `WindowFrameTest`
  - *Tested classes:* `PaperWindowFrame`, `Coordinate`, `WindowFrameDeck`
  - *Functionality:* checks the correct behavior when expected a null value returned; checks if placing operations behave correctly; checks if moving operations behave correctly; checks if the deck contains the 24 classic Sagrada cards; checks the correct behavior of the simple Coordinate class; checks the correct behavior of the cloning constructor
- **Test class name**: `UtilityTest`
  - *Tested classes:* `Color`, `Shade`
  - *Functionality:* check Color ID correctness; check Color Label correctness; check Shade ID; check Shade value
- **Test class name**: `TurnManagerTest`
  - *Tested classes:* `ArrayTurnManager`
  - *Functionality:* throws exceptions for illegal arguments; checks turn order of a 2/3/4-player game; tests JSON cloning correctness
- **Test class name**: `RoundTrackTest`
  - *Tested classes:* `PaperRoundTrack`
  - *Functionality:* checks default values at object initialization; checks round advancement; checks the correct behavior of the swap method; checks the correct behavior of the cloning constructor
- **Test class name**: `PlayerTest`
  - *Tested classes:* `ConcretePlayer`
  - *Functionality:* checks the correct behavior of getters and setters; checks the correct behavior of the cloning constructor
- **Test class name**: `GameDataTest`
  - *Tested classes:* `ConcreteGameData`, `ConcreteGame`
  - *Functionality:* checks the constructor initialization: players should be > 1 and <= 4 and everyone must have a window frame and a private objective; tests the correct cloning behavior; every player should receive a custom JSON that encodes the game data: the dice bag is not present and only the player that made the request has the private object encoded; tests the picking methods called at the start of the game to initialize players'maps
- **Test class name**: `DiceBagTest`
  - *Tested classes:* `ArrayDiceBag`
  - *Functionality:* checks if the dice bag contains the expected dice when created; checks if the insert method inserts the die correctly in the bag; the die must be exactly the same that was previously picked; checks the correct behavior of the cloning constructor
- **Test class name**: `DiceTest`
  - *Tested classes:* `PlasticDie`
  - *Functionality:* checks the correct behavior of getters and setters; checks the correct behavior of the cloning constructor; tests Exception calling
- **Test class name**: `RuleTest`
  - *Tested classes:* `Rule`, `AbstractRule`, `PlacingRule`, `ColorRule`, `ShadeRule`
  - *Functionality:* checks correct exception handling on illegal parameters; checks the Placing rule; checks matching with window frame color/shade constraints; generic tests on a particular window frame
- **Test class name**: `PlaceTest`
  - *Tested classes:* `ConcreteGame`, `ConcreteGameData`, `ConcreteCommandManager`, `ConcreteCommand`, `Place`
  - *Functionality:* testing generic functionality of place command; testing placing rules compliant behaviour of place command
- **Test class name**: `PickTest`
  - *Tested classes:* `ConcreteGame`, `ConcreteGameData`, `ConcreteCommandManager`, `ConcreteCommand`, `Pick`
  - *Functionality:* testing basic picking functionality
- **Test class name**: `PassTest`
  - *Tested classes:* `ConcreteGame`, `ConcreteGameData`, `ConcreteCommandManager`, `ConcreteCommand`, `Pass`
  - *Functionality:* pass is not allowed if you picked a die; pass advances the status of the game
- **Test class name**: `EndGame`
  - *Tested classes:* `ConcreteGame`, `ConcreteGameData`, `ConcreteCommandManager`, `ConcreteCommand`
  - *Functionality:* tests command inhibition when the game ends
- **Test class name**: `PublicObjectiveCardTest`
  - *Tested classes:* `PaperPublicObjectiveCard`, `PaperWindowFrame`, `PublicObjectiveCardDeck`
  - *Functionality:* asserts the correct size of the deck; evaluates score points on a generic frame and prints the values; asserts that every card returns 0 score points on an empty window frame; tests the correct cloning procedure for public objective cards
- **Test class name**: `PrivateObjectiveCardTest`
  - *Tested classes:* `PaperPrivateObjectiveCard`, `PaperWindowFrame`, `PrivateObjectiveCardDeck`
  - *Functionality:* checks the correct size of the deck; checks the correct evaluation of final score on a randomly filled frame; asserts the correct cloning procedure of a private objective card
- **Test class name**: `TapWheelTest`
  - *Tested classes:* `ConcreteGame`, `ConcreteCommand`, `ConcreteGameData`, `PaperToolCard`
  - *Functionality:* Tool activation; Tool card tear down on generic command execution; Pass without moving; Moving rules; Move one die, then pass; Move two dice; Tool tear down
- **Test class name**: `RunningPliersTest`
  - *Tested classes:* `ConcreteGame`, `ConcreteCommand`, `ConcreteGameData`, `PaperToolCard`
  - *Functionality:* tool activation; tool tear down; double turn in a row; unavailable tool activation on the second turn; activation not undoable; correct order of turns after tool activation
- **Test class name**: `LensCutterTest`
  - *Tested classes:* `ConcreteGame`, `ConcreteCommand`, `ConcreteGameData`, `PaperToolCard`
  - *Functionality:* tool activation; tool tear down (ID check); select with argument out of bound; swapping dice from round track
- **Test class name**: `LathekinTest`
  - *Tested classes:* `ConcreteGame`, `ConcreteCommand`, `ConcreteGameData`, `PaperToolCard`
  - *Functionality:* tool activation; tool tear down; first die movement; second die movement; same die movement; favor points check
- **Test class name**: `GrozingPliersTest`
  - *Tested classes:* `ConcreteGame`, `ConcreteCommand`, `ConcreteGameData`, `PaperToolCard`
  - *Functionality:* increase and decrease commands; tool activation; favor points constraints; tool tear down
- **Test class name**: `GrindingStoneTest`
  - *Tested classes:* `ConcreteGame`, `ConcreteCommand`, `ConcreteGameData`, `PaperToolCard`
  - *Functionality:* tool activation; tool tear down; die flipping
- **Test class name**: `GlazingHammerTest`
  - *Tested classes:* `ConcreteGame`, `ConcreteCommand`, `ConcreteGameData`, `PaperToolCard`
  - *Functionality:* before drafting condition; undo unavailable
- **Test class name**: `FluxRemover`
  - *Tested classes:* `ConcreteGame`, `ConcreteCommand`, `ConcreteGameData`, `PaperToolCard`
  - *Functionality:* tool activation; pass without changing shade or placing; pass after placing without selecting a new shade; pass after placing and selecting a new shade; tool tear down
- **Test class name**: `FluxBrushTest`
  - *Tested classes:* `ConcreteGame`, `ConcreteCommand`, `ConcreteGameData`, `PaperToolCard`
  - *Functionality:* tool activation; undo unavailable; pass without placing; place and tool tear down
- **Test class name**: `EglomiseBrushTest`
  - *Tested classes:* `ConcreteGame`, `ConcreteCommand`, `ConcreteGameData`, `PaperToolCard`
  - *Functionality:* favor points check; tool activation; tool tear down; correct moving according to the tool rules
