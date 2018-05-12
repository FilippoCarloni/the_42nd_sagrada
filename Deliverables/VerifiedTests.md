# Tested functionality

This document contains an aggregate list of functionality tested correctly with JUnit 5 that certify correct behavior of implemented classes.

## Tests on model

|Test class|Tested classes|Functionality|Notes|
|:---|:---|:---|:---|
|`CommandManagerTest`   |`ConcreteCommandManager`, `AbstractCommand`, `Pass`, `Pick`, `Place`   |Players can only play during their turn; players can select dice from dice pool; players can place selected die in their window frame according to the placing rules; players may pass their turn to advance the game   |Simulates a VERY simple game execution 1 turn long   |
|`DiceBagTest`   |`ClothDiceBag`   |The bag contains 90 dice when created; the 90 dice are 18 per color; no die can be picked if 90 dice were already picked   |Die extraction behaves randomly (not a rigorous test)   |
|`DiceTest`   |`PlasticDie`   |Every die shows the correct shade/color; dice can be cloned and the equals() method behaves as expected   |   |
|`GameBoxTest`   |`GameBox`   |Returns the expected objects   |   |
|`PlayerTest`   |`ConcretePlayer`   |Verifies that getters and setters of private objective and window frame behave correctly   |   |
|`PrivateObjectiveCardTest`   |`AbstractDeck`, `AbstractCard`, `PaperPrivateObjectiveCard`, `PrivateObjectiveDeck`   |The deck contains 5 cards that can be drawn; the cards calculate the correct Value Points   |   |
|`PublicObjectiveCardTest`   |`AbstractDeck`, `AbstractCard`, `PublicObjectiveDeck`, `{SingleCardClass}`   |The deck contains 10 cards that can be drawn   |Cards return the correct Value Points (not a rigorous test)   |
|`RoundTrackTest`   |`PaperRoundTrack`   |Round track holds the dice correctly in only 10 slots; shows the correct sum of shades of every die; tells when the game is finished; allows to swap dice from it   |   |
|`TurnManagerTest`   |`ConcreteTurnManager`   |Returns the correct order of 2-4 player turns; tells when the round is starting/ending   |   |
|`RuleTest`   |`ColorRule`, `PlacingRule`, `ShadeRule`, `RuleDecorator`   |Rules compose in the correct way; Shade and Color rules check the window constraints; Shade and Color rules check the neighbor dice; Placing rule prevents dice from be placed at long distance   |   |
|`UtilityTest`   |`Color`, `Shade`   |Methods findByID, getLabel, findByValue behave as expected for Color and Shade enums  |   |
|`WindowFrameTest`   |`Coordinate`, `FileWindowPatternGenerator`, `PaperWindowFrame`, `WindowFrameDeck`   |Dice can be placed and moved correctly without collisions; the deck loads every map from the .txt in /res/window_patterns folder; every Coordinate instance is in a consistent state (it cannot be out of bound)   |    |
