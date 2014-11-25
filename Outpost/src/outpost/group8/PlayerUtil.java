package outpost.group8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import outpost.sim.Pair;
import outpost.sim.Point;

public class PlayerUtil {

	public static Point[][] get2DGrid(Point[] grid, int size) {
		Point[][] twoDGrid = new Point[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				twoDGrid[i][j] = grid[i * size + j];
			}
		}
		return twoDGrid;
	}

	public static ArrayList<Pair> getTargetPoints(Pair start, int size) {
		ArrayList<Pair> pairList = new ArrayList<Pair>();
		for (int i = 0; i < 4; i++) {
			Pair tmp0 = new Pair(start);
			Pair tmp = null;
			if (i == 0) {
				tmp = new Pair(tmp0.x - 1, tmp0.y);
			}
			if (i == 1) {
				tmp = new Pair(tmp0.x + 1, tmp0.y);
			}
			if (i == 2) {
				tmp = new Pair(tmp0.x, tmp0.y - 1);
			}
			if (i == 3) {
				tmp = new Pair(tmp0.x, tmp0.y + 1);
			}
			if (tmp.x >= 0 && tmp.y >= 0 && tmp.x < size && tmp.y <size) {
				pairList.add(tmp);
			}

		}
		return pairList;
	}

	public static Pair movePairTo(Pair start, Point destination, int size) {
		Pair tempPair = new Pair(start);
		Pair movedPair = null;
		int xSign = destination.x - start.x;
		int ySign = destination.y - start.y;
		if (xSign < 0) {
			movedPair = new Pair(tempPair.x - 1, tempPair.y);
		}
		if (ySign < 0) {
			movedPair = new Pair(tempPair.x,tempPair.y - 1);
		}
		if (xSign > 0) {
			movedPair = new Pair(tempPair.x + 1, tempPair.y);
		}
		if (ySign > 0) {
			movedPair = new Pair(tempPair.x, tempPair.y + 1);
		}
		return movedPair;
	}
	// Make sure destination is not water otherwise it will not find the path.
	public static List<Location> movePairToDFS  (Pair start, Point destination) throws Exception {
		
		boolean stayAwayFromEdge = destination.x >= Global.r 
									&& destination.y >= Global.r ? true : false ;
		
		if (destination.water) {
			throw new Exception("destination cannot be water");
		}
		List<Location> pathToTarget = new ArrayList<>();
		Location temp = null;
		if (start.x == destination.x && start.y == destination.y) {
			pathToTarget.add(new Location(start));
			return pathToTarget;
		}
		Location st = new Location(start);
		List<Location> q = new LinkedList<>();
		boolean[][] visited = new boolean[100][100];
		for (int i = 0; i < 100; i++) {
			Arrays.fill(visited[i], false);
		}
		q.add(st);
		visited[st.x][st.y] = true;
		
		while (!q.isEmpty()) {
			temp = ((LinkedList<Location>) q).removeFirst();
			if (temp.x == destination.x && temp.y == destination.y) {
				break;
			}
			List<Location> neighbors = getAdjacent(temp);
			for (Location l : neighbors) {
				if (!visited[l.x][l.y]) {
					q.add(l);
					visited[l.x][l.y] = true;
					l.parent = temp;
				}
			}
		}
		while (temp.parent != null ){
			pathToTarget.add(0,temp);
			temp = temp.parent;
		}
		
		return pathToTarget;
	}

	public static int distanceFromEdge(Location location) {
		return Math.min(location.x, location.y);
	}
	
	public static List<Location> getAdjacent(Location loc) {
		List<Location> adjacentLocationsList = new ArrayList<>();
		Location temp = null;
		int [][] moves = {{1, 0}, {0, 1}, {-1, 0}, {0, -1} };
		
		for (int [] move : moves){
			int i = move[0];
			int j = move[1];
			if (isValidBoundry(loc.x+i,loc.y+j)) {
				temp = Global.grid[loc.x+i][loc.y+j];
				if(!temp.water){
					adjacentLocationsList.add(temp);
				}
			}
		}
		return adjacentLocationsList;
	}

	public static boolean isValidBoundry(int x, int y) {
		return !(x < 0 || y < 0 || x > 99 || y > 99);
	}

	public static Point getClosestWaterNotOwnedByUs(
					Pair pair,
					Point[] grid,
					List<Pair> myOutposts,
					int id,
					int r) {
		Double minDist = Double.MAX_VALUE;
		Point closestPoint = null;
		for (Point point : grid) {
			if (point.water
					&& !isControlledByMe(point, myOutposts, r)
			    && manhattanDistance(pair, point) < minDist) {
				closestPoint = point;
				minDist = manhattanDistance(pair, point);
			}
		}
		isControlledByMe(closestPoint, myOutposts, r);
		return closestPoint;
	}

	public static boolean isControlledByMe(Point p, List<Pair> myOutposts, int r) {
		for (Pair pair : myOutposts) {
			if (manhattanDistance(pair, p) <= r) {
				return true;
			}
		}
		return false;
	}

	public static double manhattanDistance(Pair a, Point b) {
		return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
	}
	
	public static double manhattanDistance(Pair a, Pair b) {
		return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
	}
	
	public static double manhattanDistance(Location a, Location b) {
		return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
	}
	
	public static double manhattanDistance(Location a, Pair b) {
		return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
	}


	public static Point[] getGridCopy(Point[] grid) {
		Point[] deepCopy = new Point[grid.length];
		int i = 0;
		for (Point point : grid) {
			deepCopy[i] = new Point(point);
			i++;
		}
		return deepCopy;
	}

	public static int getNumberOfWater(Point[] points) {
		int rtn = 0;
		for (Point point : points) {
			rtn += point.water ? 1 : 0;
		}
		return rtn;
	}

	public static List<Point> getWaterPoints(Point[] grid) {
		List<Point> rtn = new ArrayList<>();
		for (Point point : grid) {
			if (point.water) {
				rtn.add(point);
			}
		}
		return rtn;
	}

	public static int getNumberOfLand(Point[] points) {
		return points.length - getNumberOfWater(points);
	}

	public static Pair getStartPair(int id, int size) {
		switch (id) {
		case 0:
			return new Pair(0, 0);
		case 1:
			return new Pair(size - 1, 0);
		case 2:
			return new Pair(size - 1, size - 1);
		case 3:
			return new Pair(0, size - 1);
		}
		return null;
	}

	public static int furthestOutpost(ArrayList<Pair> outposts, int id, int size) {
		Pair startPair = getStartPair(id, size);
		int returnId = 0;
		double minDistance = Double.MAX_VALUE;
		for (int i = 0; i < outposts.size(); i++) {
			if (distance(startPair, outposts.get(i)) < minDistance) {
				returnId = i;
				minDistance = distance(startPair, outposts.get(i));
			}
		}
		return returnId;
	}

	public static double distance(Pair a, Pair b) {
		return Math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y));
	}

	public static double distanceFromPairToPoint(Pair a, Point b) {
		return Math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y));
	}

	public static int[] myResources(Point[] grid, List<Pair> myPair) {
		int numWater = 0;
		int numLand = 0;
		int[] resourcesArray = new int[2];
		for (Point point : grid) {
			for (Pair pair : myPair) {
				if (point.water && point.ownerlist.contains(pair)) {
					numWater++;
				}
				if (!point.water && point.ownerlist.contains(pair)) {
					numLand++;
				}
			}
		}
		resourcesArray[0] = numLand;
		resourcesArray[1] = numWater;
		return resourcesArray;
	}

    // Returns 0 if we need nothing for next outpost, 1 if we need water, 2 for land or 3 if both are needed
	public static int resourceNeeds(Point[] grid, List<Pair> myOutPosts, int L, int W) {
        int[] resources = myResources(grid, myOutPosts);
        int needs = 0;
        int outpost_count = myOutPosts.size();
        if (resources[0] < outpost_count*L) {
            needs = needs + 1;
        }
        if (resources[1] < outpost_count*W) {
            needs = needs + 2;
        }
        return needs;

	}

	public static boolean hashMapContainsLocationAsValue(HashMap<Integer, Location> hm, Location l) {
		Collection<Location> vals = hm.values();
		for (Location val : vals) {
			if (l.x == val.x && l.y == val.y) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean arrayListContainsLocation(ArrayList<Location> vals, Location l) {
		for (Location val : vals) {
			if (l.x == val.x && l.y == val.y) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean hashSetContainsPair(HashSet<Location> hs, Pair p) {
		Location[] vals = hs.toArray(new Location[1]);
		for (Location val : vals) {
			if (p.x == val.x && p.y == val.y) {
				return true;
			}
		}
		return false;
	}
	
	public static Location getLocationCorrespondingToPairFromHashSet(HashSet<Location> hs, Pair p) {
		Location[] vals = hs.toArray(new Location[1]);
		for (Location val : vals) {
			if (p.x == val.x && p.y == val.y) {
				return val;
			}
		}
		return null;
	}

}
