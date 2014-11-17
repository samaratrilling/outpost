PARAMETERS:
[mapid] [a radius of influence r] [L] [W] [gui] [player0] [player1] [player2] [player3] [ticks]"

For example,
"java Outpost.sim.Outpost map0 5 4 1 true dumb dumb dumb dumb 1000"
indicates using map0, R=5, L=4, W=1, enable the graphic interface,
all players are dumb player and at the tick =1000,
it is the end of the game.

2. For the player, you have two interface, move and delete.

For move interface, the input is king_outpostlist, Point[] grid.
The two input arguments are:
---the outpost information for all four kings
---information of the resource.
The return of this move should be an array of the id of the outpost and the
target position, ArrayList<movePair> type.

The delete interface takes the input of king_outpostlist and Point[] grid.
The return of the input should be the index of the outpost you want to delete.