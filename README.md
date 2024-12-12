# Minesweeper

This project is basically a school project (from École des Mines de Saint Étienne), in which we had to develop a Minesweeper game, with an solo and a online mode.

On top of these two demands, I decided to make the best version I could, by:

 - [ ] Make a good interface
 - - [ ] Simple but pretty
 - - [ ] Easy to use
 - - [ ] Dark and light theme
 - [ ] Make a good online part
 - - [ ] One player can host and administrate a game
 - - [ ] Auto detecting the server IP while searching a game on a LAN
 - - [ ] Handle all type of disconnexion

> I tried to realize the best minesweeper (in my opinion) that answer these needs.

> Also, I'm french (and you know french sucks at learning other languages). So my english might be not perfect (clearly not).

# Global architecture

This project try to implement a MVC model. And because it has an online mode, it must have a online part.

Finally, we can separate the project in three different part :

- logical part, which include the controller
- graphical part
- connexion part (client / serveur for the online game mode)

> we can also say that there is a utility part...

## Logical part

This part mainly include the controller itself. It makes the logic and have the control on the gui and the possible client. There is also the Minefield class, which hold a table with all mine position, and all game mechanic related method.

We can put in this part these class and enum :
- 

