package outpost.group8;

import java.util.ArrayList;

import outpost.sim.Pair;
import outpost.sim.Point;

public class Location {
	public int x;
    public int y;
    public boolean water;
    public double distance;
    public Location parent;
    
    //public int owner;
    public ArrayList<Pair> ownerlist = new ArrayList<Pair>();

    public Location() { x = 0; y = 0; water = false; parent = null;}

    public Location(int xx, int yy, boolean wt) {
        x = xx;
        y = yy;
        water = wt;
        parent = null;
       
    }

    public Location(Point o) {
        this.x = o.x;
        this.y = o.y;
        this.water = o.water;
        parent = null;
    }
    public Location(Pair o) {
        this.x = o.x;
        this.y = o.y;
        water = false;
        parent = null;
    }
    public Location(Location o) {
        this.x = o.x;
        this.y = o.y;
        this.water = o.water;
        parent = null;
    }
    
    public boolean equals(Point o) {
        return o.x == x && o.y == y ;
    }
    public boolean equals(Location o) {
        return o.x == x && o.y == y ;
    }
}
