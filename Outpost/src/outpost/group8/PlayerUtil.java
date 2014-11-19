package outpost.group8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import outpost.sim.Pair;
import outpost.sim.Point;

public class PlayerUtil {
	
	public static Point[][] get2DGrid(
			Point [] grid,
			int size) {
		Point[][] twoDGrid = new Point[size][size];
		for (int i = 0 ; i < size ; i++) {
			for (int j = 0 ; j < size ; j++) {
				twoDGrid[i][j] = grid[i*size + j];
			}
		}			
		return twoDGrid;
	}
	
	public static ArrayList<Pair> getTargetPoints(
			Pair start,
			int size) {
		    	ArrayList<Pair> pairList = new ArrayList<Pair>();
		    	for ( int i = 0; i<4; i++) {
		    		Pair tmp0 = new Pair(start);
		    		Pair tmp = null;
		    		if (i == 0) {	
		    			tmp = new Pair(tmp0.x-1,tmp0.y);
		    		}
		    		if (i == 1) {
		    			tmp = new Pair(tmp0.x+1,tmp0.y);
		    		}
		    		if (i == 2) {			
		    			tmp = new Pair(tmp0.x, tmp0.y-1);
		    		}
		    		if (i == 3) {
		    			tmp = new Pair(tmp0.x, tmp0.y+1);
		    		}
		    		if (tmp.x >= 0 && tmp.y >= 0 &&
		    				tmp.x < size && tmp.y < size){
		    			pairList.add(tmp);
		    		}
		    		
		    	}
		   
		    	return pairList;
	}
	
	public static Pair movePairTo(
			Pair start,
			Point destination,
			int size) {
    	ArrayList<Pair> pairList = new ArrayList<Pair>();
    	Pair tmp0 = new Pair(start);
		Pair tmp = null;
    	int xSign = destination.x - start.x;
    	int ySign = destination.y - start.y;
    	if (xSign  < 0) {
    		tmp = new Pair(tmp0.x-1, tmp0.y);
    	} else if (ySign < 0) {
    		tmp = new Pair(tmp0.x, tmp0.y - 1);
    	} else if (xSign > 0) {
    		tmp = new Pair(tmp0.x+1, tmp0.y);
    	} else if(ySign > 0) {
    		tmp = new Pair(tmp0.x, tmp0.y+1);
    	}
    	return tmp;
   }
	
	public static Point getClosestWater(
			Pair pair,
			Point[] grid){
		Double minDist = Double.MAX_VALUE;
		Point closestPoint = null;
		for (Point point : grid) {
			if (point.water && distanceFromPairToPoint(pair, point) < minDist) {
				closestPoint = point;
				minDist = distanceFromPairToPoint(pair, point);
			}
			
		}
		return closestPoint;
	}
	
	public static Point[] getGridCopy(Point[] grid) {
		Point [] deepCopy = new Point[grid.length];
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
	
	public static int getNumberOfLand(Point[] points) {
		return points.length - getNumberOfWater(points);
	}
	public static Pair getStartPair(
			int id,
			int size) {
		switch (id) {
			case 0:
				return new Pair(0,0);
			case 1:
				return new Pair(size-1, 0);
			case 2:
				return new Pair(size-1, size-1);
			case 3:
				return new Pair(0, size-1);			
		}
		return null;
	}
	public static int furthestOutpost(
			ArrayList<Pair> outposts,
			int id,
			int size) {
		Pair startPair = getStartPair(id, size);
		int returnId = 0;
		double minDistance = Double.MAX_VALUE;
		for (int i = 0 ; i < outposts.size() ; i++) {
			if (distance(startPair, outposts.get(i)) < minDistance) {
				returnId = i;
				minDistance = distance(startPair, outposts.get(i));
			}
		}
		return returnId;
	}
    public static double distance(Pair a, Pair b) {
        return Math.sqrt((a.x-b.x) * (a.x-b.x) +
                         (a.y-b.y) * (a.y-b.y));
    }
    public static double distanceFromPairToPoint(Pair a, Point b) {
        return Math.sqrt((a.x-b.x) * (a.x-b.x) +
                         (a.y-b.y) * (a.y-b.y));
    }
    
    public static int[] myResources(
    		Point [] grid,
    		List<Pair> myPair){
    	int numWater = 0;
    	int numLand = 0;
    	int [] rtn = new int[2];
    	for (Point point : grid) {
    		for ( Pair pair : myPair){
    			if (point.water && point.ownerlist.contains(pair)) {
    				numWater++;
    			} 
        		if (!point.water && point.ownerlist.contains(pair)){
        			numLand++;
        		}
    		}
    	}
    	rtn[0] = numLand;
    	rtn[1] = numWater;
    	return rtn;
    }
}
