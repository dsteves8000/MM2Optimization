# MM2Optimization

//Project introduced by DSteves, current rank 1 in Total Clears, Easy Endless Clears, and number of World Records

The goal of this project is to find the most optimal approach to playing endless for each difficulty and knowing when you should play a level VS when you should skip a level.

There are a couple of assumptions that need to be made. Lets assume that you are able to play endless and (almost) never die and clear at around the pace of the current world record of the course. At the beginning of each level you encounter in endless, you should immediately pause, write down the current world record for that level into a list (before you clear it since this whole project revolves around current times rather than the ones that you set afterwards) and then unpause and play the level as normal.

Every time you reach a flag and get a clear, it will take 25 seconds between the time that you hit the flag, spam the A button, and be able to first start input for the next level. Every time you skip a level (or die, but we are ignoring this for now) it will take 10 seconds in handheld mode OR 13 seconds in nonhandheld mode due to the hold to skip process. I would like to assume everyone is playing in handheld mode for now for simplicity but I will consider the other option in the future. On top of this, every time you pause and check the world record it takes about 2 seconds to do so regardless of whether you decide to play the level or skip it. Because of this, your assumed clear time for the level will be 25 + 2 + (WR for that level) seconds or simplified, 27 + (WR for that level). Similarly, every time you skip a level it will take a flat rate of 10 + 2 seconds (... so 12 seconds) and the WR time will not matter.

We want to figure out a way to minimize the (total time spent)/(number of clears). The total time spent is calculated through the summation of choices we made on whether to play or skip a level, let Xi be all the world record times encountered indexed by i (level 1, level 2, etc.), and Ai be a boolean value that is either 1 if we play the level or 0 if we skip, we get this:

total time spent = sum(Xi*Ai + 15*Ai + 12),
number of clears = sum(Ai).

Please take a look at MM2ConstrainedOptimization.pdf for more details on the setup of the equation and the constraints.

The reason it is set up like this is if we use the following example:
You encounter 6 levels of world record times 10, 20, 30, 35, 40, 50

Here are all the possible ways to select time spent / # of clears

Xi: 10 20 30 35 40 50
Ai: 1  0  0  0  0  0  = 1
=>  37 12 12 12 12 12 = 97
	                       97/1 = 97

Xi: 10 20 30 35 40 50
Ai: 1  1  0  0  0  0  = 2
=>  37 47 12 12 12 12 = 132
	                       132/2 = 66

Xi: 10 20 30 35 40 50
Ai: 1  1  1  0  0  0  = 3
=>  37 47 57 12 12 12 = 177
	                    177/3 = 59


Xi: 10 20 30 35 40 50
Ai:  1  1  1  1  0  0  = 4
=>  37 47 57 62 12 12 = 227
	                    227/4 = 56.75


Xi: 10 20 30 35 40 50
Ai: 1  1  1  1  1  0  = 5
=>  37 47 57 62 67 12 = 282
	                    282/5 = 56.4
	                 => x = 40 seconds is the cutoff in this example based on the data, play levels that are <= 40 seconds
                          skip levels that are > 40 seconds


Xi: 10 20 30 35 40 50
Ai: 1  1  1  1  1  1  = 6
=>  37 47 57 62 67 77 = 347
	                   347/6 = 57.8333
                     
Out of the six possible choices for the cutoff *IN THIS EXAMPLE*, 97, 66,59, 56.75, 56.4, 57.8333
The fifth option, Xi = 40 has the optimal choice because 56.4 is the lowest value out of this example.
With this minimization, we can easily come up with the maximum possible levels that can be cleared in an hour by taking 3600/(the optimal minimum found in this problem), so 3600/56.4 which states that if you are playing at world record clearing pace of those 6 levels you will have a clear rate of 3600/56.4 = 63.8298 levels an hour. What my programs are doing is expanding into this math by brute forcing a strategy for this minimization based on different intervals. (Currently mine scans levels from 120 seconds to 0 seconds in intervals of 0.1 seconds, but this can be changed through modifying the code.


MM2clearoptimization.java takes in a text file (such as first200.txt, for example where the input is a list of world records, in my case, in easy mode where each record is separated by a new line in the text file) and outputs the optimal time to skip a level based on it's WR time under the assumption that you are clearing levels near WR pace for each level constantly, more or less.

MM2realtimeoptimization.java works very similarly to the other java file, but instead of taking in an input file you will be able to directly input WR times one by one into the program as you are seeing them pop up when you play endless. At first when you run the program and enter in a few records the output will NOT be accurate, but the more records you put in, the more the program learns and will converge to the optimal value as the sample size increases.
