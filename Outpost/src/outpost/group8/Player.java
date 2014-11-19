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
	boolean flag = true;
	
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
    
    public int delete(ArrayList<ArrayList<Pair>> king_outpostlist, Point[] gridin) {
    	return PlayerUtil.furthestOutpost(king_outpostlist.get(id), this.id, size);
    }
    
	public ArrayList<movePair> move(
			ArrayList<ArrayList<Pair>> king_outpostlist,
			Point[] gridin,
			int r,
			int L,
			int W,
			int t){
		ArrayList<movePair> returnlist = new ArrayList<movePair>();
    	List<Pair> myOutPosts = king_outpostlist.get(this.id);
    	int [] resources = PlayerUtil.myResources(gridin, myOutPosts);
    	movePair next = null;
    	int numOutPost = Math.min(resources[0], resources[1]);
    	
    	// Target water resources when there are less than 3 Outposts.
    	if (myOutPosts.size() < 3) {
    		
    		for (int i = 0 ; i < myOutPosts.size() ; i++) {
    			Pair pair = myOutPosts.get(i);
    			Point closestWater = PlayerUtil.getClosestWater(pair, gridin);
    			pair = PlayerUtil.movePairTo(pair, closestWater, size);
    			next = new movePair(i, pair);
    			returnlist.add(next);
    		}
    		
    		
    	} else {
    		// Target cutting the supply line of closest Empire.
    		System.out.println("Supposed to have more than 3 outposts from simulator");	
    		for (int i = 0 ; i < myOutPosts.size() ; i++) {
    			Pair pair = myOutPosts.get(i);
    			List<Pair> targetPoints = PlayerUtil.getTargetPoints(pair, size);
    			pair = PlayerUtil.movePairTo(pair, PairtoPoint(targetPoints.get(0)), size);
    			next = new movePair(i, pair);
    			returnlist.add(next);
    		}
    		
    		
    	}
    	return returnlist;
    }
    
    static Point PairtoPoint(Pair pr) {
    	return grid[pr.x*size+pr.y];
    }
    static Pair PointtoPair(Point pt) {
    	return new Pair(pt.x, pt.y);
    }
}
