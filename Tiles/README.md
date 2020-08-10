IMPORTANT NOTE:
I started working on this Project on the 24th of August but I know my repository and history of commits do not show that. I learned how to
do that on August 29th, the day where you see my commits start to take place. I apologize for doing that and I promise that won't be the
case starting next Project where I will commit starting day one.  

TILES

1.HOW TO PLAY/SCORING

Like the NYTimes tiles game, my game follows the same procedure. Every clicked tile generates a green border. The first clicked tile is stored and the second clicked tile compares with the first 
clicked tile. The second clicked is stored and the third clicked tile compares with the second clicked tile and so on. If a click makes a tile empty, a grey border appears. After that, the user 
is free to click on any tile. If the user clicks on another empty file, a grey border appears on the other tile, unlike the NYTimes tiles game where you cannot click on an empty tile. If the user
clicks on a non-empty tile, the game resumes like before. If two tiles match any number of elements, the score increases by 1. If they do not match, the score drops to zero. However, like the game,
the highest combo/score is recorded and shown. The game is over when all the elements have been removed.

2.GAME LOGIC/ALGORITHM

At the start of every game, the board is set up with random elements. So, not two games is the same 99.9% of the time. The classes Board, Tile and Elements compromise the back end data necessary
for tracking tiles and their respective elements. One thing I want to emphasize is that there is a very basic algorithm when it comes to choosing elements for each tile. Per the requirement, all 
of my tiles have 3 elements. However, say I have 42 tiles in my board. From tile 0-20, there is randomness. From 21-41, the tiles are set up to match tiles from 0-20. So, tiles 0 and 21 match, 1
and 22 match, 2 and 23 match, ............ 20 and 41 match. When GUIDesign, the class that designs the GUI finds this algorithm, the board is set up and if looked upon carefully, the player
realizes that 2 tiles in the board are virtually identical. The good thing about this algorithm is that there is no odd element left behind. Since there are 42 tiles with 3 elements each, the first
21 tiles contain 21*3 = 63 random elements. The last 21 tiles contain the same 63 elements. Elements have a distinct shape and color and there are 4 shapes and 4 colors to choose from. As alluded 
to earlier, GUIDesign takes care of the GUI part of the game when it sets up tiles with their respective elements. Tiles in the GUI are made using canvas and the canvas itself if set at the center
of BorderPane. BorderPane was used because clicking on the canvas generates coordinates that I was able to work with. The class also works in conjunction with class MouseFunc to update the board 
after every click. Say, two tiles are clicked successively and they don't match, the board is not updated at all. If two tile have matching elements, using the canvasUpdate method in the GUIDesign
class, the board is updated. Similarly, other cases are dealt likewise to match the NYTimes tiles game. Lastly, GUIDesign also starts the animation once the initial board is set up and updates
the score using labels which have been added to GridPane. GUIDesign also stops the game once it knows that there are no tiles on the board. An endgame message pops up to reveal the final score 
and prompts the user to exit the program.

3.BUGS/THINGS TO IMPROVE

There are no bugs. However, as I said earlier, my games is not a mirror match of the NYTimes tiles game. Say, my click empties a tile of all elements. If I click next on another empty tile, the
tile will generate a border on the newly clicked tile. In the NYTimes game, you cannot click on an empty tile. In mine, you can. Excluding that, however, if a clicked tile runs out on elements,
and the user clicks on a non-empty tile next, the game resumes like the NYTimes tiles game. I will admit that this exception is because I tangled on my own logic so that is definitely something
I need to improve. I also feel that if I have more time, I will able to untangle out of own logic but since this game matches the basic requirements, I will do no further work. Also, to the
sharp user, my game is easy to finish because of how the algorithm is set up. However, given more time, I can write an algorithm that will introduce randomness to the second half of the tiles.
If you see my GUIDesign class properly, you will see that the GUIDesign constructor and the canvasUpdate method look the same. I tried making it a single method before but to no avail. I need to
be more wary of writing better methods from now on.

4.REFERENCES

I borrowed lines 76-93 from classmate Amun Kharel. I was having a hard time figuring out the logic of comparing two tiles and he helped me there. 