# Tested functionality

This document contains an aggregate list of functionality tested correctly with JUnit 5 that certify correct behavior of implemented classes.

## Tests on model

|Test class|Tested classes|Functionality|Notes|
|:---|:---|:---|:---|
|`DiceBagTest`   |`ClothDiceBag`   |The bag contains 90 dice when created; the 90 dice are 18 per color; no die can be picked if 90 dice were already picked   |Die extraction behaves randomly (not a rigorous test)   |
|`DiceTest`   |`PlasticDie`   |Every die shows the correct shade/color; dice can be cloned and the equals() method behaves as expected   |   |
|`GameBoxTest`   |`GameBox`   |Returns the expected objects   |   |
|`PrivateObjectiveCardTest`   |`AbstractDeck`, `AbstractCard`, `PaperPrivateObjectiveCard`, `PrivateObjectiveDeck`   |The deck contains 5 cards that can be drawn; the cards calculate the correct Value Points   |   |
|`PublicObjectiveCardTest`   |`AbstractDeck`, `AbstractCard`, `PublicObjectiveDeck`, `{SingleCardClass}`   |The deck contains 10 cards that can be drawn   |Cards return the correct Value Points (not a rigorous test)   |
|`RoundTrackTest`   |`PaperRoundTrack`   |Round track holds the dice correctly in only 10 slots; shows the correct sum of shades of every die; tells when the game is finished; allows to swap dice from it   |   |
|`TurnManagerTest`   |`ConcreteTurnManager`   |Returns the correct order of 2-4 player turns; tells when the round is starting/ending   |   |
|`UtilityTest`   |`Color`, `Shade`   |Methods findByID, getLabel, findByValue behave as expected for Color and Shade enums  |   |
|`WindowFrameTest`   |`Coordinate`, `FileWindowPatternGenerator`, `PaperWindowFrame`, `WindowFrameDeck`   |Dice can be placed and moved correctly without collisions; the deck loads every map from the .txt in /res/window_patterns folder; every Coordinate instance is in a consistent state (it cannot be out of bound)   |    |
