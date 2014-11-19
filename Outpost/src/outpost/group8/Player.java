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
    		 return returnlist;
    		
    	} else {
    		// Target cutting the supply line of closest Empire.
    		/*
    		System.out.println("Supposed to have more than 3 outposts from simulator");	
    		for (int i = 0 ; i < myOutPosts.size() ; i++) {
    			Pair pair = myOutPosts.get(i);
    			List<Pair> targetPoints = PlayerUtil.getTargetPoints(pair, size);
    			pair = PlayerUtil.movePairTo(pair, PairtoPoint(targetPoints.get(0)), size);
    			next = new movePair(i, pair);
    			returnlist.add(next);
    		}*/
    	
    		
    		counter = counter+1;
        	if (counter % 10 == 0) {
        		for (int i=0; i<100; i++) {
            		theta[i]=random.nextInt(4);
            	}
        	}
        	ArrayList<movePair> nextlist = new ArrayList<movePair>();
        	//System.out.printf("Player %d\n", this.id);
        	/*
        	for (int i=0; i<gridin.length; i++) {
        		grid[i]=new Point(gridin[i]);
        	}*/
        	grid = outpost.group8.PlayerUtil.getGridCopy(gridin);
        	
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
        			movePair nextl = new movePair(j, positions.get(theta[j]));
        			nextlist.add(nextl);
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
        		positions = PlayerUtil.getTargetPoints(prarr.get(prarr.size()-1),size);
        		boolean gotit=false;
        		while (!gotit) {
        			//Random random = new Random();
    				//int theta = random.nextInt(positions.size());
    				//System.out.println("we are here!!!");
        			if (theta[0]<positions.size()){
        			
        				if (!PairtoPoint(positions.get(theta[0])).water) {
    		    			movePair nextl = new movePair(prarr.size()-1, positions.get(theta[0]));
    		    			nextlist.add(nextl);
    		    			//next.printmovePair();
    		    			gotit = true;
    		    			break;
        				}
        			
        			}
        		//System.out.println("outpost 0 need to change the direction???");
        		theta[0] = random.nextInt(positions.size());
        		}
        		
        	//}
        	
        	
    		return nextlist;
    		
    	}
    	//return returnlist;
    }
	 static ArrayList<Pair> surround(Pair start) {
		   // 	System.out.printf("start is (%d, %d)", start.x, start.y);
		    	ArrayList<Pair> prlist = new ArrayList<Pair>();
		    	for (int i=0; i<4; i++) {
		    		Pair tmp0 = new Pair(start);
		    		Pair tmp;
		    		if (i==0) {
		    			//if (start.x>0) {
		    			tmp = new Pair(tmp0.x-1,tmp0.y);
		    	//		if (!PairtoPoint(tmp).water)
		    			prlist.add(tmp);
		    		//	}
		    		}
		    		if (i==1) {
		    			//if (start.x<size-1) {
		    			tmp = new Pair(tmp0.x+1,tmp0.y);
		    		//	if (!PairtoPoint(tmp).water)
		    			prlist.add(tmp);
		    			//}
		    		}
		    		if (i==2) {
		    			//if (start.y>0) {
		    			tmp = new Pair(tmp0.x, tmp0.y-1);
		    			//if (!PairtoPoint(tmp).water)
		    			prlist.add(tmp);
		    			//}
		    		}
		    		if (i==3) {
		    			//if (start.y<size-1) {
		    			tmp = new Pair(tmp0.x, tmp0.y+1);
		    			//if (!PairtoPoint(tmp).water)
		    			prlist.add(tmp);
		    			//}
		    		}
		    		
		    	}
		    	
		    	return prlist;
		    }
    
    static Point PairtoPoint(Pair pr) {
    	return grid[pr.x*size+pr.y];
    }
    static Pair PointtoPair(Point pt) {
    	return new Pair(pt.x, pt.y);
    }
}