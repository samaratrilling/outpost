package outpost.group8;

import java.util.*;

import outpost.sim.Pair;
import outpost.sim.Point;
import outpost.sim.movePair;

public class Player extends outpost.sim.Player {
	static int size =100;
	static Point[] grid = new Point[size*size];
	static Location[][] locGrid;
	static Random random = new Random();
	static int[] theta = new int[100];
	static int counter = 0;
	boolean flag = true;
	
	static HashSet<Location> waterPoints;
	static HashSet<Location> shorePoints;
	// key = outpost id (integer, counting up from 0). assumes outposts will be in the same
	// order always (fragile)
	HashMap<Integer, Location> targets = new HashMap<Integer, Location>();
	HashSet<Integer[]> targetHistory = new HashSet<Integer[]>();
	ArrayList<Location> openShore = new ArrayList<Location>();

	// Helper classes
	static Global globalHelper = new Global();
	static MapAnalysis mapHelper = new MapAnalysis();
	
	public static boolean initGlobal = false;
	
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
		
		if(!initGlobal){
			locGrid = Global.initGlobal(gridin);
			initGlobal = true;
		}
		
		// Do map analysis
		if (shorePoints == null || waterPoints == null) {
			waterPoints = MapAnalysis.findWater(locGrid);
			shorePoints = MapAnalysis.findShore(waterPoints);
		}
		// An arraylist of all the pieces of shore that do not currently have one of our
		// pieces occupying them.
		
		ArrayList<movePair> returnlist = new ArrayList<movePair>();
    	List<Pair> myOutPosts = king_outpostlist.get(this.id);
    	int [] resources = PlayerUtil.myResources(gridin, myOutPosts);
    	movePair next = null;
    	int numOutPost = Math.min(resources[0], resources[1]);
    	
    	// Keep track of the shoreline that we occupy.
		openShore = MapAnalysis.updateOpenShore(myOutPosts, shorePoints);

    	// Target water resources when there are less than 3 Outposts.
    	/*if (myOutPosts.size() < 3) {
    		
    		for (int i = 0 ; i < myOutPosts.size() ; i++) {
    			Pair pair = myOutPosts.get(i);
    			//Point closestWater = PlayerUtil.getClosestWaterNotOwnedByUs(pair, gridin, myOutPosts, this.id, r);
    			Location followLocation = PlayerUtil.movePairToDFS(pair, new Point(60, 20,true)).get(0);
    			//Location closeWater = new Location(closestWater);
    			pair = new Pair(followLocation.x, followLocation.y); //pair = new Pair(pair.x, pair.y-1);
    			next = new movePair(i, pair);
    			returnlist.add(next);
    		}
    	}
    	else {*/
			// Target water resources;
			for (int i = 0; i < myOutPosts.size(); i++) {
				Pair pOutpost = myOutPosts.get(i);
			
				// If it's already on the shore, keep it there.
				Location outpost = new Location(pOutpost);
				if (PlayerUtil.arrayListContainsLocation(openShore, outpost)) {
					targets.put(i, outpost);
					targetHistory.add(arrayify(outpost.x, outpost.y));
					returnlist.add(new movePair(i, pOutpost));
				}
				// We already have a target locked for this outpost
				else if (targets.containsKey(i)) {
					Location dest = targets.get(i);
					Location step = null;
					try {
						step = PlayerUtil.movePairToDFS(pOutpost, new Point(dest.x, dest.y, Global.grid[dest.x][dest.y].water)).get(0);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						System.out.println("Destination cannot be water");
						e.printStackTrace();
					}
					returnlist.add(new movePair(i, new Pair(step.x, step.y)));
				}
				// This is a new outpost - we need to give it a target piece of shoreline.
				else {
					Location dest = new Location(0, 0, false); // dummy value
					double shortestDist = Double.MAX_VALUE;
					for (Location possDest : openShore) {
						// Look through all pieces of shore that are not occupied currently
						// AND are not the intended destination of an existing outpost.
						// Find the closest one.
						if (!targetHistoryContains(possDest.x, possDest.y, targetHistory)) {
							if (!PlayerUtil.hashMapContainsLocationAsValue(targets, possDest)) {
								if (Global.grid[possDest.x][possDest.y].water == false) {
									double dist = PlayerUtil.manhattanDistance(possDest, outpost);
									if (dist < shortestDist && dist > 10) {
										dest = possDest;
										shortestDist = dist;
									}
								}
							}
						}
					}
					// Choose the closest piece of unoccupied shore.
					System.out.println("destination chosen for outpost at " + outpost.x + ", " + outpost.y + 
							": " + dest.x + ", " + dest.y);
					targets.put(i, dest);
					targetHistory.add(arrayify(outpost.x, outpost.y));
					Location step = null;
					try {
						step = PlayerUtil.movePairToDFS(pOutpost, new Point(dest.x, dest.y, Global.grid[dest.x][dest.y].water)).get(0);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						System.out.println("Destination cannot be water");
						e.printStackTrace();
						
					}
					System.out.println("next step for " + outpost.x + ", " + outpost.y + ": " + step.x + ", " + step.y);
					returnlist.add(new movePair(i, new Pair(step.x, step.y)));
				}
			
			}
    	//}
			
		for (movePair mp : returnlist) {
			mp.printmovePair();
			System.out.println();
		}
    	return returnlist;
    }
	
	static boolean targetHistoryContains(Integer x, Integer y, HashSet<Integer[]> targetHist) {
		for (Integer[] arr : targetHist) {
			if (arr[0] == x && arr[1] == y) {
				return true;
			}
		}
		return false;
	}

	static Integer[] arrayify (Integer x, Integer y) {
		Integer[] target = new Integer[2];
		target[0] = x;
		target[1] = y;
		return target;
	}

    static Point PairtoPoint(Pair pr) {
    	return grid[pr.x*size+pr.y];
    }
    static Pair PointtoPair(Point pt) {
    	return new Pair(pt.x, pt.y);
    }
}