package outpost.group8;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import outpost.sim.Pair;
import outpost.group8.PlayerUtil;

public class MapAnalysis {
	
	public MapAnalysis() {
		
	}

	public static HashSet<Location> findWater(Location[][] grid) {
		HashSet<Location> waterPoints = new HashSet<Location>();
		for (Location[] line : grid) {
			for (Location l : line) {
				if (l.water) {
					waterPoints.add(l);
				}
			}
		}
		return waterPoints;
	}
	
	public static HashSet<Location> findShore(HashSet<Location> water){
		HashSet<Location> shore = new HashSet<Location>();
		for (Location l : water) {
			// It's ok to initialize this new location as not being water, because the overridden
			// .equals() only compares x and y.
			ArrayList<Location> possibleNeighbors = new ArrayList<Location>();
			possibleNeighbors.add(new Location(l.x, l.y - 1, false)); // north
			possibleNeighbors.add(new Location(l.x, l.y + 1, false)); // south
			possibleNeighbors.add(new Location(l.x + 1, l.y, false)); // east
			possibleNeighbors.add(new Location(l.x - 1, l.y, false)); // west
			possibleNeighbors.add(new Location(l.x + 1, l.y + 1, false)); // southeast
			possibleNeighbors.add(new Location(l.x - 1, l.y - 1, false)); // northwest
			possibleNeighbors.add(new Location(l.x - 1, l.y + 1, false)); // southwest
			possibleNeighbors.add(new Location(l.x + 1, l.y - 1, false)); // northeast
			
			for (Location possible : possibleNeighbors) {
				for (Location w : water) {
					if (possible.x != w.x && possible.y != w.y) {
						shore.add(possible);
					}
				}
			}
		}
		return shore;
	}
	
	/**
	 * Update the shore points that do not have one of our outposts on them.
	 * @param ourOutposts
	 * @param shorePoints
	 * @return
	 */
	public static ArrayList<Location> updateOpenShore(List<Pair> ourOutposts, HashSet<Location> shorePoints) {
		ArrayList<Location> openShore = new ArrayList<Location>();
		
		for (Location l : shorePoints) {
			for (Pair outpost : ourOutposts) {
				if (l.x == outpost.x && l.y == outpost.y) {
					System.out.println(l.x + ", " + l.y + " is not in openShore");
				} else {
					openShore.add(l);
				}
			}
		}
		
		return openShore;
	}
	
}
