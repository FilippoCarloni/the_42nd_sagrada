# Model

- [Introduction](#introduction)
- [Model Classes Overview](#model-classes-overview)
  - [Interface as a contract](#interface-as-a-contract)
  - [Game board classes](#game-board-classes)
- [Commands](#commands)
  - [Available commands](#available-commands)
  - [Command structure](#command-structure)
    - [The pick example](#the-pick-example)
    - [Why undos and redos](#why-undos-and-redos)
    - [Some commands can't be undone](#some-commands-can't-be-undone)
- [Advanced Model classes](#advanced-model-classes)
  - [Public Objectives](#public-objectives)
    - [Public objective structure](#public-objective-structure)
    - [The Column Color Variety example](#the-column-color-variety-example)
    - [Note on private objectives](#note-on-private-objectives)
  - [Tool Cards](#tool-cards)
    - [Tool types](#tool-types)
    - [The Lens Cutter example](#the-lens-cutter-example)
    - [Activator and command list](#activator-and-command-list)
  - [Placement rules](#placement-rules)
  - [Command manager](#command-manager)
- [References](#references)
  - [Card IDs](#card-ids)
  - [Patterns](#patterns)
  - [Conditions](#conditions)
  - [Instructions](#instructions)


## Introduction

This guide walks through the Model classes explaining how they interact in the general structure and the implementation choices. The Model is meant to be a black-box from the outside, with its usage defined by the `Game` interface. Here's the list of available methods:
- `getPrivateObjectives`: returns a list of Private Objective cards that must be shared between the players at the start of the game
- `getWindowFrames`: returns a list of Window Frames that must be shared between the players at the start of the game
- `executeCommand`: executes a command string on the current status of the game
- `getCurrentPlayer`: tells who's currently playing during this turn
- `undoCommand`: reverts the last command executed (it is executed unconditionally)
- `isUndoAvailable`: tells if the player is allowed to undo its last action
- `redoCommand`: forwards the game to the last undone state (it is executed unconditionally)
- `isRedoAvailable`: tells if the redo stack is not empty
- `isGameEnded`: tells when 10 rounds have been played
- `getScore`: returns the current score status of the players (it can be always calculated, even if the game is not already ended)
- `getData`: returns the complete status of the game or a custom status for a particular player (deletes other players' private objectives and the remaining content of the dice bag)

The Model implementation is based on a 1:1 correspondence with the JSON syntax in which it can be saved and from which it can be loaded. This serialization capability is particularly helpful with internet communication, as, once the server has serialized in JSON syntax the current game status, the serialization product can be sent (with some sensible information deletion) to the client which starts the parsing procedure in order to extract the information to be rendered. Every game status related class can be loaded from JSON syntax by a particular JSON factory, following a `Factory` design pattern.


## Model Classes Overview


### Interface as a contract

Nearly every Model class is described by its **interface**: this is particularly helpful for implementation changes in the concrete classes. The interface works as a contract that defines the behavior of a class without touching its internal implementation and highly enhances readability (at least from a behavioral point of view) for external users.

The best example is probably the dice bag: at first it was implemented as a random unlimited dice generator, but during the first months of experimentation its concept moved to a closer one to the actual game. The dice bag now holds the array of 90 dice, that can be randomly extracted, all initialized at the start of the game. At that time the code outside the dice bag was using only the `DiceBag` interface and not the concrete class itself, so the integration of the new implementation was basically reduced to a constructor change.


### Game board classes

- **Color and Shade** are represented with *enums*: this choice grants that every `Color` or `Shade` attribute in the model is always one consistent with the possible value pool
- **Dice** hold a color attribute, a shade attribute and a ID: the latter is used for dice cloning and correct recognition; it can be used even by the view to have custom rendering of some particular dice
- **Dice bag** is just an array of 90 dice, shuffled accordingly to picking and inserting instructions
- **Window frames** are represented with a map that connects a `Coordinate` (row index, column index) to a `Die` or a constraint (`Color` or `Shade`)
- **Round track** consists of an array of dice and an array of 10 integers that represents how many dice are currently placed in a particular slot
- **Players** hold a window frame, an integer that corresponds to the number of favor points still unused and a private objective card
- **Decks** are collections of different type of cards; the deck primarily exposes the `draw` method that returns a `Drawable` object, randomly picked from its internal collection; the types of cards hold by different types of decks are
  - Private objective cards: cards that evaluate score points on a particular window frame; the client has no information about the other players' private objectives
  - Public objective cards: cards that evaluate score points on a particular window frame following a predetermined pattern (see [Public objectives](#public-objectives))
  - Tool cards: cards that, once activated, can sensibly change the status of the game (see [Tool Cards](#tool-cards))
  - Window frames (see above)

## Commands

### Available commands

Commands are treated as strings. The player commits its actions by typing a specific command in the command line if using the CLI, otherwise the GUI itself sends a properly constructed command strings on click events. Here's the list of available commands:
- `pick <n>`: picks the n-th die of the dice pool
- `place <n> <m>`: places the picked die in the current player's window frame in the spot that corresponds to the n-th row and m-th column
- `pass`: passes the turn to the next player
- `tool <n>`: activates the tool card with n as its ID if available in the game board

The following list of commands is available only on particular tool card activations:
- `move <n> <m> <p> <q>`: moves the die in the spot that corresponds to the n-th row and the m-th column to the spot that corresponds to the p-th row and the q-th column
- `increase`: increases the value of the picked die by 1
- `decrease`: decreases the value of the picked die by 1
- `select <n>`: selects the n-th die on the round track or sets a particular shade value on the picked die depending on the currently activated tool card

Commands are composed by a list of conditions and a list of instructions:
- A **condition** is a particular boolean predicate that must be verified on the current game status id order to declare the command available
- An **instruction** is a particular action that will be performed unconditionally on the current game status when all the command conditions are positively checked

### Command structure

#### The pick example

As an example I bring the `pick` command with its JSON encoding:
```JSON
{
  "reg_exp": "pick \\d",
  "undoable": true,
  "conditions": [
    {"condition": "die_picked", "value": false},
    {"condition": "die_placed", "value": false},
    {"condition": "tool_not_active"},
    {"condition": "arg_comparison", "type_of_comparison": "greater_than", "index": 0, "bound": 0},
    {"condition": "arg_comparison", "type_of_comparison": "smaller_than", "index": 0, "bound": "dice_pool_size"}
  ],
  "instructions": [
    {"instruction": "tear_down_passive_tools"},
    {"instruction": "pick_die_from_dice_pool"}
  ]
}
```
In this JSON we can see:
- *reg_exp* is the regular expression that the passed command string must verify in order to be processed and eventually executed
- *undoable* is a boolean that states if the command can be undone according to the rules by the player (see [Some commands can't be undone](#some-commands-can't-be-undone))
- *conditions* is the list of conditions (see [Conditions](#conditions)) that must be verified in order to execute the command (in this example a die shouldn't already be picked, a die shouldn't already be placed in a window frame, a tool card shouldn't currently be active and finally the first argument passed should be an integer between 0 and the size of the dice pool)
- *instructions* is the list of actions (see [Instructions](#instructions)) that are effectively performed on the game status once the conditions are positively tested (in the example we have the effective die picking and the eventual shut down of passive tool cards)

#### Why undos and redos

Undos and redos allow the game status to be reverted and forwarded to or from a previous state. This functionality is not present in the Sagrada rules, but in a board game it's quite likely that someone picks the wrong die or spots a better die that the drafted one while thinking to his/her next move. `undo` and `redo` immediately answer to that requirement.

Another important `undo` role is played in the turn timer context. *The 42nd Sagrada* is a videogame played on the internet, so, by its nature, it requires a timer after which the player's move becomes an automatic `pass` in order to handle occasional disconnections and toxic behavior of some players. If the timer triggers when the game state is not consistent (there's a die picked, a tool card execution is not terminated...) the `undo` really shines: it can be called on the current status while the `pass` command is not allowed, meaning that, after the loop execution, the `pass` command will be legal and the state will be consistent with the rules of the game.

#### Some commands can't be undone

In this paragraph will be explained the role of the *undoable* boolean in the JSON command structure.

Some commands should not be "undoable". Consider for example the *Flux Brush* tool card: it re-rolls the drafted die. Let's suppose the user needs a 5 as the value of the drafted die. If the *Flux Brush* activation command was undoable, the player could execute the command, then undo and redo it while the current value was different from 5, gaining in the end the desired (but not rule-compliant) value. As another example, we can consider the `pass` command: when a player passes the turn, the `undo` functionality should be inhibited to the current player, because, otherwise, the `undo` would revert the game status and the turn will return to the player that had just passed.

The only way to fully adapt some commands to the undo-redo paradigm id to inhibit occasionally that functionality.


## Advanced Model classes

### Public Objectives

#### Public objective structure

Public objective cards' function in a Sagrada game is to identify a particular pattern of dice on a window frame and assign a score value based on the difficulty of the pattern (for example, a row pattern is always more difficult than a column pattern, because the number of slots of the window frame to take care of is greater). For this reason the implementation of these cards is based on the `FramePattern` functional interface that defines the `getNumOfPatterns` method. This method, as its signature says, returns the number of patterns, depending on the particular card that is calling it, on a given window frame.

#### The Column Color Variety example

As an example, I bring the Column Color Variety card:
```json
{
  "name": "Column Color Variety",
  "description": "Columns with no repeated colors.",
  "id": 15,
  "pattern": {"type": "difference", "place": "columns", "object": "color"},
  "points_per_pattern": 5
}
```
The JSON that encodes the card information holds:
- the *name* of the card
- the *description* of the card effect
- a unique *id* that identifies the card
- the particular *pattern* that the card looks for in the window frame (see [Patterns](#patterns))
- the number of *points* that the card grants for every pattern occurrence in the window frame

#### Note on private objectives

As a small side note on the objective-like cards, the **private objective cards** are pretty similar to their public relatives, but are easier to build, as they have a highly symmetrical structure based on the available dice colors. These cards are automatically generated through iteration on the `Color` *enum* values, so their analysis is analogous and easier than the previous one on the public cards.


### Tool Cards

#### Tool types

Tool cards alter the game status in various ways once activated. Although the tools pool in the original game is pretty various, the available cards can still be divided in smaller categories:
- **Active tool cards** require user actions in order to end their effect; as an example we can consider *Grozing Pliers*: it requires a choice by the user between increasing or decreasing the value of the drafted die
- **Passive tool cards** does not require an explicit user action in order to be terminated; their effect can be instantaneous or can be terminated on the next execution request of a basic command (`pick`, `place` or `pass`); we can consider for example *Flux Brush* card: it re-rolls the drafted die, but the tool card execution terminates on the next `place` or `pass` command

#### The Lens Cutter example

The tool card structure is more complex than the other cards. I first report an example, then explain briefly every attribute:
```json
{
  "name": "Lens Cutter",
  "description": "After drafting, swap the drafted die with a die from the Round Track.",
  "id": 5,
  "active": true,
  "activator":
  {
    "undoable": true,
    "conditions": [
      {"condition": "die_picked", "value": true}
    ],
    "instructions": [

    ]
  },
  "commands": [
    {
      "reg_exp": "select \\d+",
      "undoable": true,
      "conditions": [
        {"condition": "arg_comparison", "type_of_comparison": "greater_than", "index": 0, "bound": 0},
        {"condition": "arg_comparison", "type_of_comparison": "smaller_than", "index": 0, "bound": "round_track_size"}
      ],
      "instructions": [
        {"instruction": "swap_from_round_track"},
        {"instruction": "tear_down", "condition": null}
      ]
    }
  ]
}
```
In this JSON we can see:
- the *name* of the card
- the *description* of its effect
- the unique *id* that identifies the card
- the *active* boolean attribute (`false` means *passive*)
- the *activator* command
- the *commands* list that become available once the tool card is activated

#### Activator and command list

The most important attributes of a tool card are *activator* and *commands*:
- **activator**: this JSON tag identifies a command that, once executes, effectively activates the tool card; its *conditions* array defines the preconditions that must be verified by the game status in order to declare rule-compliant the tool card activation; its *instructions* array stores all the actions that will be performed on the game status once the tool card is executed (in the example there's no concrete action taken on *Lens Cutter* activation)
- **commands**: this JSON tag identifies the set (effectively unordered) of commands that become available while the tool card remains active; in the example the `select` command becomes available and allows the player to swap the drafted die with one round track die, then the tool card returns unconditionally inactive


### Placement rules

Placement rules are the set of rules that should be observed while placing (or moving) a die in the window frame. Sagrada defines three different kinds of rules:
- **Placing rule**: a die should be placed in the border of the window frame or adjacent orthogonally or diagonally to a previously placed die
- **Color rule**: a die should be placed in a spot that has no color constraint other that die's color; a die shouldn't be placed adjacent orthogonally to another same color die
- **Shade rule**: a die should be placed in a spot that has no shade constraint other that die's shade; a die shouldn't be placed adjacent orthogonally to another same shade die

Sagrada Tool cards merge these three rules in various ways, so, according to this requested malleability, rules have been implemented with a `Decorator` pattern. A `Decorator` pattern allows the user to freely compose the three kinds of rules, calling innested constructors:

```
// placing + color + shade
Rule composedRule1 = new ColorRule(new PlacingRule(new ShadeRule()));

// placing + color, shade rule will be ignored
Rule composedRule2 = new ColorRule(new PlacingRule());

// placing + shade, color rule will be ignored
Rule composedRule3 = new PlacingRule(new ShadeRule());
```

### Command manager

The command manager is the executor of passed command on the current game status. It is composed of:
- the current `GameData` that holds all the information that identify precisely the current game status (REMINDER: `GameData` can be encoded and decoded in JSON syntax)
- a stack of JSON objects that identifies the undone states (`undoCommands`)
- a stack of JSON objects that identifies the redone states (`redoCommands`)

To explain further the inner workings of the command manager I will analyze the program flow when the manager is asked to execute a new command by a player:
1. player asks the command manager to execute a command string (`cmd`)
2. the command manager checks if the game is finished; if the game is not finished the execution will continue
3. the manager allocates an instance of every possible command (will only be allocated **basic commands** and commands of **tool cards** currently active) and stores them in a list; iterating on this list, it extracts only the commands that are defined by a regular expression that matches with `cmd`
4. the manager pushed the current game data in `undoCommands`
5. the manager tries to execute every command that survived the check at (3): if the command is valid, `redoCommands` is cleared and execution terminates correctly; if the command is not valid, last inserted item in `undoCommands` is deleted and the error message string will be inserted in a error message list
6. if the execution is not terminated correctly, the user will receive the error message list, brought by an `IllegalCommandException`'s message


## References

### Card IDs

Here's the list of IDs defined for every Sagrada card. Cards are uniquely identified in Model and View only by their ID.

| ID | Name | Type |
|:---:|:---:|:---:|
| 1   | Grozing Pliers  | Tool  |
| 2   | Eglomise Brush  | Tool  |
| 3   | Copper Foil Burnisher  | Tool  |
| 4   | Lathekin  | Tool  |
| 5   | Lens Cutter  | Tool  |
| 6   | Flux Brush  | Tool  |
| 7   | Glazing Hammer  | Tool  |
| 8   | Running Pliers  | Tool  |
| 9   | Cork-backed Straightedge  | Tool  |
| 10   | Grinding Stone  | Tool  |
| 11   | Flux Remover  | Tool  |
| 12   | Tap Wheel  | Tool  |
| 13   | Color Diagonals   | Public Objective  |
| 14   | Color Variety  | Public Objective  |
| 15   | Column Color Variety  | Public Objective  |
| 16   | Column Shade Variety  | Public Objective  |
| 17   | Dark Shades  | Public Objective  |
| 18   | Light Shades  | Public Objective  |
| 19   | Medium Shades  | Public Objective  |
| 20   | Row Color Variety  | Public Objective  |
| 21   | Row Shade Variety  | Public Objective  |
| 22   | Shade Variety  | Public Objective  |
| 23   | Shades of Red  | Private Objective  |
| 24   | Shades of Green  | Private Objective  |
| 25   | Shades of Yellow  | Private Objective  |
| 26   | Shades of Blue  | Private Objective  |
| 27   | Shades of Purple  | Private Objective  |

### Patterns

Patterns are defined in JSON syntax and are loaded by `PublicObjectiveCard` instances, in order to adapt their fetching method to the pattern described by the card effect.

| Name | Description | Options | Examples |
|:---|---|---|---|
|`DiagonalsPattern`   |Looks for similarities in the window frame Diagonals   |Similarities can be checked on `Color` or `Shade`   | `{"type": "diagonals", "object": "color"}`|
|`DifferencePattern`   |Looks if there are no repeated features in a particular direction (row or column)   |May be checked on `Color` or `Shade`, looking at the row set or the column set   | `{"type": "difference", "place": "columns", "object": "shade"}`|
|`SetPattern`   | Identifies the number of distinct sets of dice in the window frame, based on a given set of features of the same type  | The type of the feature set can be `Color` or `Shade`  | `{"type": "set", "object": "color", "values": ["red", "green", "blue", "purple", "yellow"]}`; `{"type": "set", "object": "shade", "values": [5, 6]}` |

### Conditions

Here's the list of the predicates that can be checked on a game status in order to verify a particular status property.

REMINDER: every command string follows the structure `command_name [arguments]` where `[arguments]` is an optional vector of integers separated by spaces of variable length, depending on the command.

| Name | Description | Options | Examples |
|:---|---|---|---|
| `ArgComparison`  |Compares to a given bound a command argument   | An argument of a defined index must be >=, < or == to a given bound (can be hardcoded if integer or variable on the current status); available variable bounds are dice pool size, round track size, tool deck size, minimum shade or maximum shade  | `{"condition": "arg_comparison", "type_of_comparison": "greater_than", "index": 0, "bound": 0}`; `{"condition": "arg_comparison", "type_of_comparison": "smaller_than", "index": 0, "bound": "round_track_size"}`   |
| `DiePicked`  | Checks if there's a die in the "picked die" slot  | Can be verified positively or negatively  | `{"condition": "die_picked", "value": true}`  |
| `ShadeComparison`   | Compares the shade of the picked die to a particular value   | Comparison can be >=, < or ==; the bound can me the maximum shade or the minimum shade  |`{"condition": "shade_comparison", "type_of_comparison": "smaller_than", "bound": "maximum_shade"}`   |
|`FavorPointsCheck`   |Checks if the player has enough favor points to activate the chosen Tool Card   | -  |`{"condition": "favor_points_check"}`   |
|`ToolNotActivated`   |Checks if a Tool Card was not activated during this turn   | -  |`{"condition": "tool_not_activated"}`   |
|`ToolNotActive`   | Checks if there are no active Tool Cards (of ACTIVE type)  | -  |`{"condition": "tool_not_active"}`   |
|`DiePlaced`   | Checks if a die was placed during the current turn  | Can be verified positively or negatively  |`{"condition": "die_placed", "value": false}`   |
|`FirstTurnOfTheRound`   | Checks if it's (or isn't) the first turn of current player during this round  | Can be verified positively (first turn) or negatively (second turn)  |`{"condition": "first_turn", "value": false}`   |
|`Share`   | Checks if a die in the window frame shares a particular feature with at least one die from a given pool  | Available pools are round track, dice moved list and dice pool; features to be compared can be color or shade  |`{"condition": "share", "object": "color", "pool": "round_track", "value_if_empty": false}`   |
|`FreeSlot`   | Checks if the selected slot is (not) free from a die  | Can be verified positively or negatively  |`{"condition": "free_slot", "row_index": 0, "column_index": 1, "value": true}`   |
|`Move`   | Checks if the movement command is rule-compliant  | The rule check can be composed by three components: *placing rule*, *color rule* and *shade rule*  |`{"condition": "move_rule_check", "placing": true, "color": false, "shade": true}`   |
|`NotDieAlreadyMoved`   | Checks if during this turn a die has not already been moved  | A positive check would be pointless (there's no effect "after a die was moved"), so the check can only be negative  |`{"condition": "not_die_already_moved"}`   |
|`NumberOfDiceMoved`   |Checks the number of dice moved during this turn   |Comparison can be >=, < or ==; the bound is a hardcoded integer   |`{"condition": "num_of_dice_moved", "type_of_comparison": "equal_to", "bound": 2}`   |
|`Place`   | Checks if the die can be placed in a particular position  | The rule check can be composed by three components: *placing rule*, *color rule* and *shade rule*  |`{"condition": "placing_rule_check", "placing": true, "color": true, "shade": true}`   |
|`ValidCoordinates`   |Checks if even arguments are a legal row index and odd arguments a legal column index   | -  |`{"condition": "valid_coordinates"}`   |

### Instructions

Instructions are atomic actions that change the current game status. They are performed after several conditions check (see [Conditions](#conditions)).

REMINDER: every command string follows the structure `command_name [arguments]` where `[arguments]` is an optional vector of integers separated by spaces of variable length, depending on the command.

| Name | Description | Options | Examples |
|:---|---|---|---|
|`AdvanceGame`   |Passes the turn and resets the status variables   |-   |`{"instruction": "advance_game"}`  |
|`ReRollPool`   |Rolls all the dice currently present in the Dice Pool   | -  |`{"instruction": "re_roll_pool"}`   |
|`SwapFromRoundTrack`   | Swaps the picked die with one present on the Round Track  |-   |`{"instruction": "swap_from_round_track"}`   |
|`TakeTwoTurns`   | The current player will take two turns in a row  |-   |`{"instruction": "take_two_turns"}`   |
|`PickDieFromDiceBag`   | Picks a single die from the Dice Bag  | -  |`{"instruction": "pick_die_from_dice_bag"}`   |
|`PickDieFromPool`   |Picks the selected die from the Dice Pool   | -  |`{"instruction": "pick_die_from_dice_pool"}`   |
|`PlacePickedDie`   | Places the picked die in a free spot on the window frame  | -  |`{"instruction": "place_die"}`   |
|`ReturnDieToDiceBag`   | Returns the picked die to the Dice Bag  |-   |`{"instruction": "return_die_to_dice_bag"}`   |
|`RollDie`   | Rolls the picked die  |-   |`{"instruction": "roll_die"}`   |
|`SetShadeOfPickedDie`   |Sets the selected shade to the picked die   |The shade can be increased, decreased, flipped or set to a specific one (passed by argument)   |`{"instruction": "set_shade_of_picked_die", "argument": "decrease"}`; `{"instruction": "set_shade_of_picked_die", "argument": "no_constraint"}`   |
|`ManageFavorPoints`   | On Tool Card activation, removes favor points from the current player and gives the removed favor points to the activated Tool Card  | -  |`{"instruction": "manage_favor_points"}`   |
|`SetToolCardActivated`   |Sets the `toolCardActivated` state to `true` and updates the current `activeToolID`s   | -  |`{"instruction": "activate_tool"}`   |
|`TearDown`   |Ends the effect of a Tool Card   |This shut down can be condition-driven   |`{"instruction": "tear_down", "condition": null}`; `{"instruction": "tear_down", "condition": {"condition": "num_of_dice_moved", "type_of_comparison": "equal_to", "bound": 2}}`  |
|`TearDownPassiveTools`   | Ends the effect of a passive Tool Card; this is called by basic commands, in order to shut down automatically passive tools  |-   |`{"instruction": "tear_down_passive_tools"}`   |
|`MoveDie`   |Moves unconditionally a die from a position of the window frame to another one   | -  |`{"instruction": "move_die"}`   |
