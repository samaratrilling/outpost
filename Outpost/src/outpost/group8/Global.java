package outpost.group8;

import outpost.sim.Point;
import outpost.group8.Location;

public class Global {
	public static Location [][] grid ;
	
	public Global(){
		
	}
	public static Location[][] initGlobal(Point [] g){
		grid = new Location[100][100];
		for (int i = 0 ; i < 100 ; i++) {
			for (int j = 0 ; j <100 ; j++) {
				grid[i][j] = new Location(g[i*100+j]);
			}
		}
		return grid;
	}
}
