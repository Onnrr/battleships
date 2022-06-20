# battleships

### About the game
This is an implementation of the classic battle ships game. It is played by two players online. Each of the players initialise the positions of their 5 ships of different sizes in their 10x10 grid. Grid has each position named with a letter for the row and a number for the column. After the setup players takes a guess about the position of their opponents ships in turns. If the guess is correct the guessed position of the ship sinks. If the guess is not correct (the guessed position is empty), the other player makes the guess. The first player to sink all of the opponent's ships wins the game.

### Classes

#### Player
Player class stores their grid and their opponent's grid. Their grid is set at the beggining but the opponent's grid is unknown at first. One of the players acts like the host and the other one acts like a client. TCP/IP protocole is used for the communication of the two players. This class is also used for initialising the scene with the data stored at the class.

#### SceneInitialise
This interface helps initialising the scenes with current player's information

#### GamePlayController
Controls the game page

#### Start controller
Controls the initial page