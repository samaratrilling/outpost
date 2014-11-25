package outpost.group8;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import outpost.sim.Pair;
import outpost.sim.Point;
import outpost.sim.movePair;

public class Strategy {

	public static ArrayList<movePair> targetWaterResources(
			List<Pair> myOutPosts,
			HashMap<Integer, Location> targets,
			ArrayList<Location> openShore,
			HashSet<Integer[]> targetHistory) {
		// TODO Auto-generated method stub
		// Target water resources;
		ArrayList<movePair> returnlist = new ArrayList<movePair>();
		for (int i = 0; i < myOutPosts.size(); i++) {
			Pair pOutpost = myOutPosts.get(i);
		
			// If it's already at its destination, keep it there.
			Location outpost = new Location(pOutpost);
			/*if ()
			if (PlayerUtil.arrayListContainsLocation(openShore, outpost)) {
				targets.put(i, outpost);
				targetHistory.add(arrayify(new Integer(outpost.x), new Integer(outpost.y)));
				returnlist.add(new movePair(i, pOutpost));
			}*/
			// We already have a target locked for this outpost
			if (targets.containsKey(i)) {
				Location dest = targets.get(i);
				System.out.println("Target locked. destination: " + dest.x + ", " + dest.y);
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
                    //
                    if (Player.targetHistoryContains(possDest.x, possDest.y, targetHistory)) continue;
                    if (PlayerUtil.hashMapContainsLocationAsValue(targets, possDest)) continue;
                    if (Global.grid[possDest.x][possDest.y].water == false) {
                        double dist = PlayerUtil.manhattanDistance(possDest, outpost);
                        if (dist < shortestDist && dist > 10) {
                            dest = possDest;
                            shortestDist = dist;
                        }
                    }
				}
				// Choose the closest piece of unoccupied shore.
				System.out.println("destination chosen for outpost at " + outpost.x + ", " + outpost.y + 
						": " + dest.x + ", " + dest.y);
				targets.put(i, dest);
				openShore.remove(dest);
				targetHistory.add(Player.arrayify(outpost.x, outpost.y));
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
		return returnlist;
	}

}
