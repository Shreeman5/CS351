DOMINOS

1.HOW TO OPEN THE JAR VERSION OF THE CONSOLE GAME
Use
"java -jar dominos1.0_gautams.jar"
in the command line. 

2.HOW TO PLAY/SCORING/STRATEGY/GAME LOGIC

28 tiles are provided at the start of the game and both player and computer get
7 tiles each, boneyard gets 14. The 7 dominos seen at the start of the game is 
the player's dominos. To play, 1st: user has to select a domino using the left 
mouse button. Selection is signified by a purple border around the selected 
domino. Then, to place the domino on the board, user has to left click on the 
left side, compromised of light blue pixels, or on the right side, compromised 
of light grey pixels. If this order is messed up, user cannot play the game. If 
user tries to play on a side that he cannot play, error warning is given. To 
rotate a domino, user can right click on a domino. This is signified by a gold
border around the selected domino. Then, again user has to follow the order and
left click on the domino and then left click on either side of the board. Now 
that this is out of the way, user always gets first pick. Depending on what 
the user played, the computer follows with it's piece, making it 2 pieces. Now, 
the user has a choice of picking between the 2 ends of the domino chain. For
example, say the 2 ends are the numbers 3 and 4. If user has a domino with a
3 or 4 on it, user can decide to rotate or not rotate depending on whether
the 3 or 4 are on the left side or right side of the domino that he wants to
choose. In all of this, 0 acts as a wildcard. 0 can substitute for any number
and the user/computer can play their domino, containing 1 or 2 zeros, to match
any non-zero number. This is especially helpful when user/computer wants to
get out of a tight spot. By tight spot, I mean when user/computer has to
draw from the boneyard. In this game, the user does not have to worry about
manually drawing from the boneyard. After computer has played, the game will 
check whether the user has a matching piece and if he does not, the program 
automatically adds boneyard pieces to the user tray until a matching piece
is found. That way, if user/computer has no matching pieces, they continually 
draw from the boneyard until a matching piece is found. They also draw from 
the boneyard if and only if the boneyard is not empty and the 
usertray/computertray is empty (again, done automatically). The game only 
ends after the boneyard is empty and 1 of 2 things happen, 1st: both user 
and computer cannot make moves, 2nd: either tray of user/computer is empty. 
There's a small caveat in there too. Say that boneyard is empty and computer 
put down it's last piece. User will get a chance to play his remaining pieces 
until usertray is empty or he finds no matching piece. The same is true for 
the computer as well. So, now the scoring part begins. Assuming that game 
has ended, the scores of user and computer are compared. Both scores follow
the same procedure: adding up all the numbers left in each of user's and 
computer's dominos. Say, user has 2 dominos, consisting of 4 numbers, those 
numbers are added to give a value x. Say, computer has 3 dominos, consisting 
of 6 numbers, those numbers are added to give a value y. If x > y, computer 
wins. If y > x, user wins. Strategy to win really hinges on how the user
plays his big dominos. If the user has a 6|6 domino, he should play it
immediately, because it is the biggest domino and hence will increase the 
user's score, making him lose. The same is true for all of the "big" 
dominos.

3.ALGORITHM

Obviously, the game starts at the main class. Firstly, 28 dominos are created
in the Domino class and stored in an arraylist. Secondly, classes PlayerTray,
ComputerTray and Boneyard extract 7,7,14 dominos from the Domino class
respectively. the pieces are stored in an arraylist again. To mimic the 
coordinate nature of domino (they look like (1,2), (2,3) respectively), I have
utilised class Point and stored dominos as points in all of the aforementioned
arraylists. Now, we get to the logic and the GUI. Class GUIDesign finds the 
player's arraylist, finds how many points/dominos the arraylist has, and 
designs each domino in the GUI. Animation timer, background color and mouse 
click functionality is also initiated/made in this class. The dots in each 
domino are made in the 'designDotsInThisClass' method in class MouseFunction.
As you can see, the boneyard pieces and computer pieces are not shown because
they would discard the point of the game, where you cannot see what your 
opponent has. The function of each mouse click has already been explained
but here's the logic behind it. If user clicks on a domino, the method
'setBoardClickTrue' sets the variable isClicked in class playerTray to be true
and now the user is free to play, because isClicked is true, only on the left 
side, with the light blue pixels or the right side, with the light grey 
pixels. After the user clicks on either side, the method 'setBoardClickFalse'
sets isClicked to be false and the user is back to clicking another domino 
again. This ensures order on the program and removes bugs, because there is
very little code written for this logic, making the chance of error occurrence
to go down. Now, on to the meat of the program. Say, a user puts down a domino.
The first pick is always of the user's and is stored in a map called 
'dominoRightString', at key 0. If computer decides to play on the left of the
first domino, that domino is stored in a map called 'dominoLeftString' at index 1.
Subsequently, all dominos played to the left of the center domino is placed
in dominoLeftString. If computer decides to play right, that domino is stored in 
'dominoRightString' at index 1. Subsequently, all dominos played to the right of 
the center domino is placed in dominoRightString. So, we get the idea that 
dominoLeftString starts at index 1 and dominoRightString starts at index 0 and 
both increase their key value by 1 for each domino played. Now, why start with 
1 on the left but 0 on the right? Well, in the game, the first domino played 
left to the first ever domino picked by the user should be at the bottom since 
the first ever domino was on the top, as per the requirements. The domino 
played right to the first ever domino is on the bottom, per the requirements. 
So, the dominos on the bottom regardless of which map they are placed in will
be odd keys and the dominos on the top regardless of which map they are placed
in will be even keys. If we find odd keys, place them at higher y pixels and 
if we find even keys, place them at smaller y pixels. Also, the domino on the
center has a fixed pixel number and using that as a reference, all the dominos
in both of the maps will place themselves uniformly across the board. I have 
made the canvas big enough so that if the dominos are played on only one 
side, they will not cross the canvas. Speaking of which, canvas and
graphicsContext is used to draw the dominos and make the dots after every mouse 
click, be it the player's domino(s) or the board's domino(s). The canvas is set 
at the center of BoardPane. The end of game has already been explained and 
for the alert boxes that you see will inevitably come after a wrong move,
they are made using the class 'Alert'. In the end of game stuff, a pop-up will
appear which will declare the winner.

4.BUGS/THINGS TO IMPROVE

There are no bugs in the GUI game, I have extensively tested this about 50-60 
times and it never fails me. I would like to improve a thing though. If you 
play the GUI game, you will see that there will be some purple borders in the 
player's tray, instead of black borders. I admit that it is not clean but it 
meets the basic requirements, so I did not want to modify my code. I would like
that to not happen and perhaps the next project will help me code better in
that regard. In the console game, you will notice that the computer does
not ask the user whether he can rotate the first piece. After the user
plays his first piece, subsequent pieces that the user wants to play will be 
given the option to rotate. I would like to improve that and consider that a 
bug since the Professor showed us her game and the user was given the option
to rotate at the start as well. The best thing I improved about my code was
I decreased a lot of the tangled logic that I had made in the console game and
replaced it with less-code, efficient and logical code in the GUI game. 


