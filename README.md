# battleships

### About the game
This is an implementation of the classic battle ships game. It is played by two players online. Each of the players initialise the positions of their 5 ships of different sizes in their 10x10 grid. Grid has each position named with a letter for the row and a number for the column. After the setup players takes a guess about the position of their opponents ships in turns. If the guess is correct the guessed position of the ship sinks. If the guess is not correct (the guessed position is empty), the other player makes the guess. The first player to sink all of the opponent's ships wins the game.

### Classes

#### Player
Player class will be an abstract class that will be inherited by the two players. The reason that the player1 and player2 has different classes is because one of them will act like the server of the game and the other will act like the client. However, it will be useful to treat players as if they are of the same type. Players keep the number of their correct guesses and has methods to make guesses.

#### Player 1 and Player 2
Player 1 is the player that hosts the game and acts like the server and the player 2 connects to player 1. TCP/IP protocole is used for the communication of the players. Players send each other their guesses as a string and gets a string in return whether the guess was a hit or a miss.

#### Ship
Ship has attribute length as an integer and direction as a boolean value. Each players have 5 ships with different lenghts to place in their grid.

#### Table
Table is a 10x10 grid of buttons. Table also stores a 2D (10x10) array of integers to determine whether the specific position is empty or not.