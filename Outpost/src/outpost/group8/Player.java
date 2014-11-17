package outpost.group8;

import java.util.*;

import outpost.sim.Pair;
import outpost.sim.Point;
import outpost.sim.movePair;

public class Player extends outpost.sim.Player {
	static int size =100;
	static Point[] grid = new Point[size*size];
	static Random random = new Random();
	static int[] theta = new int[100];
	static int counter = 0;
	
    public Player(int id_in) {
		super(id_in);
		// TODO Auto-generated constructor stub
	}

	public void init() {
    	for (int i=0; i<100; i++) {
    		theta[i]=random.nextInt(4);
    	}
    }
    
    static double distance(Point a, Point b) {
        return Math.sqrt((a.x-b.x) * (a.x-b.x) +
                         (a.y-b.y) * (a.y-b.y));
    }
    
    // Return: the next position
    // my position: dogs[id-1]

    
    public int delete(ArrayList<ArrayList<Pair>> king_outpostlist, Point[] gridin) {
    	//System.out.printf("haha, we are trying to delete a outpost for player %d\n", this.id);
    	int del = random.nextInt(king_outpostlist.get(id).size());
    	return del;
    }
    
    /**
     * Gudberger will write this
     * @return
     */
    public boolean checkQuota(ArrayList<movePair> proposed) {
    	// 1. takes in all the new moves we propose to make
    	// 2. checks whether or not those new moves result in us being able to keep
    	// all the outposts we currently have and add one new one on the next season
    	return false;
    }
    
    /**
     * Gudbergur will write this.
     * If we cannot fill the quote in this turn, decides where to move
     * the outposts in order to make sure we can meet quota.
     * @param board
     * @param moves
     * @return
     */
    public ArrayList<Point> fillQuota(Point[] board, ArrayList<movePair> moves) {
    	// Only gets called if we won't meet quota.
    	// takes in all positions of current outposts, and the board.
    	// If we have extra outposts (don't need them for the quarter-circle),
    	// try to send those ones to places where they will occupy enough land
    	// or water to fill our quota.
    	
    	// If we don't have extra outposts, it decides which points from the
    	// current quarter-circle we should take out in order to move them to
    	// occupy the resources we need.
    	// This may involve
    		// - making the quarter-circle smaller, to make sure we can keep an unbroken circle
    		// - leaving a gap in the quarter-circle
    	// we should choose one to do.
    	return null;
    }
    
    /**
     * Samara will write this.
     * Defines the positions for points that form a minimal quarter-circumference
     * around the home square.
     * Distance between points in qCirc should be r.
     * @param board The board (basically the water configuration)
     * @param radius The radius for the quarter circle
     * @return
     */
    public ArrayList<Point> defineQCirc(Point[] board, double radius) {
    	ArrayList<Point> qCirc = new ArrayList<Point>();
    	// Define a naive quarter circle irrespective of water.
    	
    	// Distance between points in qCirc is r, and distance to edge of board
    	// is r.
    	
    	// Deal with what happens if things are too close on a water square.
    	qCirc = wrapAroundWater(board, qCirc);
    	
    	return qCirc;
    }
    
    /**
     * Samara will write this.
     * Decides what to do if the circumference overlaps with water.
     * Either delete all the cells that fall on water, or move them off water,
     * Tries to maintain the minimum number of points needed for the circumference.
     * @param board
     * @param qCirc
     * @return
     */
    public ArrayList<Point> wrapAroundWater(Point[] board, ArrayList<Point> qCirc) {
    	
    	return null;
    }
    
    public ArrayList<movePair> move(ArrayList<ArrayList<Pair>> king_outpostlist, Point[] gridin){
    	// Find what the current qCirc (quarter circle) target is. What's the radius we're
    	// trying to achieve for our defensive wall?
    	
    	// If we have not achieved the qCirc, match each outpost Pair to its ideal Point on
    	// the qCirc and find the next point it should go to on its way to its ideal point.
    	
    	// IF THE NUMBER OF OUTPOSTS WE HAVE IS LESS THAN 36 (full grid coverage of our quarter of the board)
    	// For this set of new moves we've just generated, check if the quota would be fulfilled
    	// by them (will this new board configuration result in us having enough land and water
    	// so that we'll definitely get a new outpost?
    	
    	// If we have achieved the qCirc, increment the radius by 1.
    	
    	// Do we have enough outposts to achieve this new qCirc?
    	// If so, assign each qCirc to move to its new ideal place. (keep an ideal list
    	// and an actual list; when the lists are the same, then we've achieved the qCirc.)
    	
    	// If not, just stay put. Allow the "fill quota" part to take care of deciding if we
    	// have to move to make sure we'll have enough area in the next season.
    	
    	// Return the new list of next moves on the way to filling the next qCirc.
    	
    	//----------------------------------------------
    	// OLD PLAYER
    	
    	// Initialize thetas
    	counter = counter+1;
    	if (counter % 10 == 0) {
    		for (int i=0; i<100; i++) {
        		theta[i]=random.nextInt(4);
        	}
    	}
    	
    	// Initialize the new list of moves and the points on the grid.
    	ArrayList<movePair> nextlist = new ArrayList<movePair>();
    	//System.out.printf("Player %d\n", this.id);
    	for (int i=0; i<gridin.length; i++) {
    		grid[i]=new Point(gridin[i]);
    	}
    	
    	// Decide what our new moves will be.
    	ArrayList<Pair> prarr = new ArrayList<Pair>();
    	prarr = king_outpostlist.get(this.id);
    	for (int j =0; j<prarr.size()-1; j++) {
    		ArrayList<Pair> positions = new ArrayList<Pair>();
    		positions = surround(prarr.get(j));
    		boolean gotit=false;
    		while (!gotit) {
    			//Random random = new Random();
				//int theta = random.nextInt(positions.size());
				//System.out.println(theta);
    		//if (!PairtoPoint(positions.get(theta[j])).water && positions.get(theta[j]).x>0 && positions.get(theta[j]).y>0 && positions.get(theta[j]).x<size && positions.get(theta[j]).y<size) {
    			if (theta[j]<positions.size()){
        			if (positions.get(theta[j]).x>=0 && positions.get(theta[j]).y>=0 && positions.get(theta[j]).x<size && positions.get(theta[j]).y<size) {
        		
        				if (!PairtoPoint(positions.get(theta[j])).water) {
    			movePair next = new movePair(j, positions.get(theta[j]));
    			nextlist.add(next);
    			//next.printmovePair();
    			gotit = true;
    			break;
    		}
        			}
    			}
    		//System.out.println("we need to change the direction???");
    		theta[j] = random.nextInt(positions.size());
    		}
    	}
    	/*if (prarr.size()>noutpost) {
			movePair mpr = new movePair(prarr.size()-1, new Pair(0,0));
			nextlist.add(mpr);
			//mpr.printmovePair();
		}*/
    	//else {
    		ArrayList<Pair> positions = new ArrayList<Pair>();
    		positions = surround(prarr.get(prarr.size()-1));
    		boolean gotit=false;
    		while (!gotit) {
    			//Random random = new Random();
				//int theta = random.nextInt(positions.size());
				//System.out.println("we are here!!!");
    			if (theta[0]<positions.size()){
    			if (positions.get(theta[0]).x>=0 && positions.get(theta[0]).y>=0 && positions.get(theta[0]).x<size && positions.get(theta[0]).y<size) {
    		
    				if (!PairtoPoint(positions.get(theta[0])).water) {
    			movePair next = new movePair(prarr.size()-1, positions.get(theta[0]));
    			nextlist.add(next);
    			//next.printmovePair();
    			gotit = true;
    			break;
    		}
    			}
    			}
    		//System.out.println("outpost 0 need to change the direction???");
    		theta[0] = random.nextInt(positions.size());
    		}
    		
    	//}
    	
    	
    	return nextlist;
    
    }
    
    
    static ArrayList<Pair> surround(Pair start) {
   // 	System.out.printf("start is (%d, %d)", start.x, start.y);
    	ArrayList<Pair> prlist = new ArrayList<Pair>();
    	for (int i=0; i<4; i++) {
    		Pair tmp0 = new Pair(start);
    		Pair tmp;
    		if (i==0) {
    			tmp = new Pair(tmp0.x-1,tmp0.y);
    			prlist.add(tmp);
    		}
    		if (i==1) {
    			tmp = new Pair(tmp0.x+1,tmp0.y);
    			prlist.add(tmp);
    		}
    		if (i==2) {
    			tmp = new Pair(tmp0.x, tmp0.y-1);
    			prlist.add(tmp);
    		}
    		if (i==3) {
    			tmp = new Pair(tmp0.x, tmp0.y+1);
    			prlist.add(tmp);
    		}
    		
    	}
    	
    	return prlist;
    }
    
    /**
     * Returns the board point corresponding to the given pair.
     * @param pr
     * @return
     */
    static Point PairtoPoint(Pair pr) {
    	return grid[pr.x*size+pr.y];
    }
    
    /**
     * Returns the pair location corresponding to the given point.
     * @param pt
     * @return
     */
    static Pair PointtoPair(Point pt) {
    	return new Pair(pt.x, pt.y);
    }
}
