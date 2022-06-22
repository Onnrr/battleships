# battleships

### About the game
This is an implementation of the classic battle ships game. It is played by two players online. Each of the players initialise the positions of their 10 ships of different sizes in their 10x10 grid. Grid has each position named with a letter for the row and a number for the column. After the setup players takes a guess about the position of their opponents ships in turns. If the guess is correct the guessed position of the ship sinks. If the guess is not correct (the guessed position is empty), the other player makes the guess. The first player to sink all of the opponent's ships wins the game.

### Classes
#### Player
Player class stores their grid and their opponent's grid. Their grid is set at the beggining but the opponent's grid is unknown at first. One of the players acts like the host and the other one acts like a client. TCP/IP protocole is used for the communication of the two players. This class is also used for initialising the scene with the data stored at the class.

#### Cell
Cell class inherits button with two extra attributes row and column of the position of the button. It is used in the grids to help determine the position and adjacent buttons.

#### AppManager
Controls the change of scenes and effects

### Controllers
#### GamePlayController
Controls the game page

#### StartController
Controls the initial page

#### SetupController
Controls the setup page where the ships are placed



### Interfaces
#### SceneInitialise
This interface helps initialising the scenes with current player's information

### Threads
#### ServerConnect
Waits for other player to join while host is in setup page

#### WaitOpponent
Waits for other player to make a move and sends the result to other player

#### ReadyWait
When a player is ready pushes the ready button and it startes this process. User sends message "READY" to the other user and starts waiting for other player to also be ready. The game starts when both of them are ready.
