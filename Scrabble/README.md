SCRABBLE

NOTE: I made minor adjustments to my console jar and added some code to my GUI 
part. It is 2 days past the deadline but the Professor said that it is alright.

1. Console Version

    A. HOW TO PLAY

        Use "java -jar Scrabble.jar sowpods.txt(or any dictionary file of your 
        choosing)" on the command line. Next, VERY IMPORTANT, include 
        "#########" at the end of the input you put in the command line. 
        I found online that because I used "System.in" while reading the 
        input, my code will never find the last line. So, to counter that, every input
        I put in has to end in "#########". My console game meets the requirement that
        no matter how many boards are put in, all will be solved until "#########" is 
        found in standard input. 

    B. BUGS & IMPROVEMENTS

        My game does not handle 2 wildcards. As a result, if you send in 2 
        wildcards, I substitute 1 wildcard at the start with the letter 'a'. As a
        result, if you send in 2 wildcards, board will be solved but there is a slim
        chance that my output will match with yours. Lastly, my board solver is a bit
        deficient. Say, in the board '_ r _' is present where '_' represents a playing
        block. If the computer has 'ac*****'(where ***** represents any 5 alphabets), 
        computer will not consider the possibility of 'arc'. My point is, my game does 
        not jump over already played pieces on the board. Other than that, my computer
        does everything. From the above example, my computer will play the permutation
        and combination of every 7 alphabets in the tray, on the left of 'r', on the
        right of 'r', on the top of 'r' and on the bottom of 'r'. I will expand more 
        about bugs and improvements when I explain my main game. 
    
2. GUI Version: Use "java -jar Scrabble.jar" to open jar file and please have "sowpods.txt"
   and "scrabble_board.txt" on the same folder as the jar.
    
    A. GAME LOGIC & HOW TO PLAY
    
        It is a given that player and computer both get 7 tiles at the start. Doing so
        removes 14 tiles from the bag, which is given 100 tiles at the start. As the
        game goes on, player and computer draw from the bag and reduce the number of
        tiles in the bag, until the bag is empty, the point where the game ends. So,
        how does the player play? The player can select a tile from his given tiles by
        using a left click. To play on the board, he can place it in any free block and 
        the tile is put there. If the player wants to change position of the tile in the
        board, he can click on another empty block and the tile changes it's position
        to the new block. If the player wants to bring a played tile back to his tray,
        he can right click on the tile which will bring it back. OF NOTE: If a player 
        plays a tile and puts it in the board and then plays another tile on the board,
        the older tile cannot be moved anymore. It has to be returned back to the tray
        and put back on the board if player wants to change its position. RECOMMENDED:
        play a tile on the board, place it in any block you want and use the right click
        to return it back. 
        Next, play the pieces in such a way that will form a word/words,
        horizontally or vertically. How to make a move? Assuming you have put down piece(s),
        you can press the 'submit' button to make your move. If you make a move that 
        generates 1 wrong word, your move will be rejected and computer will make a move. 
        Player is given first move, if you don't play at the center, you are allowed to 
        try again until you make a move that will touch the center block. Next, if you 
        play diagonally, you are allowed to try again, until you make a horizontal/vertical 
        move. So, assuming that you make a valid move, your score will be calcluated 
        accordingly and computer will make a valid move as well. If you want to replace
        some words off your tray, hit the 'replace' button first, then select the pieces 
        that you want to replace by clicking them and hit 'replace' again. Doing so 
        replaces the pieces but you will lose your turn to the computer. If you get a 
        wildcard, you can put it in the board and click any other block on the board
        to change letter[One click makes A-->B-->C.....-->Z-->A, where --> is one click]. 
        RECOMMENDED: Play the wild card at the end when you have played all the other words 
        from your potential move, makes playing easier. 
    
    B. SCORING & STRATEGY
    
        https://scrabble.hasbro.com/en-us/rules
        See the scoring section, my game follows the exact rules that Scrabble does, except
        for number 9. 
        
    C. ALGORITHM
    
        Board is stored in a double array. Fake Board is stored in a double array.
        
        The best algorithm has to be 'TriNode', which I used for storing my dictionary.
        Now, instead of searching the whole dictionary for a word, my game finds the validity
        of a word in <= 7 steps, a huge win when it comes to speeding things up. You can see
        the mechanics of 'TrieNode' in the Vocabulary class. Next, for the combination of 
        letters of a tray, for the computer, I firstly find all the possible combinations 
        of the 7 letters using the 'printCombination' and 'combinationUtil' methods from class
        'ComputerTray'. Then I find the permutation of all the words generated by combination
        using the 'printPermutation' method from class 'ComputerTray'.
        Now, we have a list of all possible strings that can be inserted into the board. 
        Next, the computer scans the whole board and stores the top, bottom, left and right
        (if the coordinates are empty) coordinates of a piece which is already on the board.
        Now, the computer loops through all the found coordinates and within the loop, it 
        loops over all the possible strings that were generated. Say, if I had 900 possible 
        strings and 30 coordinates, that would be the computer checking 900*30= 27000 times
        for the best move. Anyway, say a string gave a valid move, which is checked using
        'TrieNode', it is sent for scoring. There, using the official rules of scrabble, a
        score is calculated. That score is stored until another string comes whose move will
        beat the score of the previous string. At the same time, the coordinates of the move 
        and the string itself are stored as well. Then, after all the checking has been done,
        the best move, best string and best coordinates are used to place the computer's move 
        on the GUI and the board. For the player, the best thing I could do was make a fake 
        board. Until the player has pressed 'submit', a fake board acts as the GUI. Say, submit 
        was pressed and move was valid, real board copies fake board. Say move was invalid, 
        fake board copies real board. Next, the GUI updates after every click on the board
        and fake board and real board update accordingly. 
        
    D. BUGS, POSITIVES & IMPROVEMENTS 
        
        Personally, this was the best project I did in my life. It challenged me a lot and even 
        though I have done 70% of what you said, I feel happy with my product. Saying that,
        the best positive of my game is that the time taken to make a move is less than 2 seconds.
        My console tried to handle 2 wilcards but that just broke my game and I felt that if I
        included that code, which used a double boolean array to find the best combination of
        letters, I would mess up the flow of the game. I have no idea on how to improve my game
        to accomodate 2 wildcards but I am sure that with the right idea, I could implement it
        in my game and the game would be < 2 seconds. Another thing that could impact running time
        was the inclusion of in-between plays. See the example I gave in the 'BUGS & IMPROVEMENTS'
        section of the Console Version. You see, if I want to play before and after an established
        set of words, I need to have permutations and combinations, for the computer, on either
        side of the established set of letters. I would have loved to do it but I had no time to 
        do it. My commit history and the amount of time I have put into this project confirms this. 
        Anyway, I am sure that adding the "in-between" code would not have affected my game by a 
        whole lot. Another negative for this game was that I do not have a end-of-game. In future
        projects I can make that but owing to the incompleteness of my GUI part of the game, I 
        did not do it.
        I did my best when it came to wildcards. Computer and player can handle 1 
        wildcard easily, but not 2. The thing is, after both player and computer play wildcards
        on the GUI, the wildcard tile changes as the game goes on, which is against the rules 
        because once wildcard is on the board, it should not change. Also, I will give the reason
        why I put the "RECOMMENDED" lines in this README. I found out, during my testing that,
        if a player put a piece on the board and then put another and removed the one just put,
        and then you click on the board, for some reason, the one you just removed appears on
        the board again at a previously clicked block in addition to already appearing on the 
        tray. I do not know how to fix that. Another positive that I did was using 'TrieNode' 
        to store my dictionary. Not doing that could have impacted running time by a lot. Another
        highlight was the accuracy of the class 'Score'. Every move is claculated precisely and
        every wildcard is treated as a zero. Another positive was doing "in-between" plays for
        my player, which I could not do for my computer. After doing this, I realized that I 
        could have decreased the amount of code I wrote because the way how I set up the method
        for processing and calculating "in-between" plays also accomodated non-"in-between" plays. 
        I also repeated the same code in 4-5 classes at the start when I was coding but time 
        caught up to me and now I can't delete it for fear of ruining the game. Some other positives
        are that I was comfortable coding for 10 days straight and getting mostly good results, 
        making nice 'submit' and 'replace' functions, good methods for permutation and combination,
        a mostly functional and decent looking GUI and my console having almost identical output to
        the one given by the Professor.  