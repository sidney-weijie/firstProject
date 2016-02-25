package com.sidney.Hilbert;


//import org.apache.log4j.Logger;


import com.google.common.collect.*;


//Éú³ÉHilbert±àÂë
public class Hilbert {
    
	//static Logger logger = Logger.getLogger(Hilbert.class);

	//public static boolean debug = logger.isDebugEnabled();
    
	
	
	static ImmutableMap<String, Pair[][]> hilbert_map = null;
	static {
		hilbert_map = ImmutableMap.of("a", new Pair[][] {
				{ new Pair(0, "d"), new Pair(1, "a") },
				{ new Pair(3, "b"), new Pair(2, "a") } }, "b", new Pair[][] {
				{ new Pair(2, "b"), new Pair(1, "b") },
				{ new Pair(3, "a"), new Pair(0, "c") } }, "c", new Pair[][] {
				{ new Pair(2, "c"), new Pair(3, "d") },
				{ new Pair(1, "c"), new Pair(0, "b") } }, "d", new Pair[][] {
				{ new Pair(0, "a"), new Pair(3, "c") },
				{ new Pair(1, "d"), new Pair(2, "d") } });
	}

	/**
	 * Our x and y coordinates, then, should be normalized to a range of 0 to
	 * 2order-1
	 * 
	 * @param x
	 * @param y
	 * @param order
	 *            An order 1 curve fills a 2x2 grid, an order 2 curve fills a
	 *            4x4 grid, and so forth.
	 * @return
	 */
	public static long xy2d(int x, int y, int order) {
		String current_square = "a";
		long position = 0;
		int quad_x = 0;
		int quad_y = 0;
		int quad_position = 0;
		for (int i = order - 1; i >= 0; i--) {
			position <<= 2;
			quad_x = (x & (1 << i)) > 0 ? 1 : 0;
			quad_y = (y & (1 << i)) > 0 ? 1 : 0;

			Pair p = hilbert_map.get(current_square)[quad_x][quad_y];
			quad_position = p.no;
			current_square = p.square;
			position |= quad_position;
		}

		return position;
	}

	static int SCALE_FACTOR = (int) 1e5;
	static int hibert_order = 1;

	static double max_length = 1.5;

	static {
		int Max = (int) (max_length * SCALE_FACTOR);

		int size = 1;
		while (size < Max) {
			size <<= 1;
			hibert_order++;
		}
	}
	
	static class Pair {
		int no = 0;
		String square;

		Pair(int no, String square) {
			this.no = no;
			this.square = square;
		}
	}
}