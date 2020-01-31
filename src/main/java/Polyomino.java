import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Polyomino {
	int p;
	Coordinates inf, sup;
	public int[][] data;
	int size;
	
	Polyomino(int p){
		this.p = p;
		this.data = new int[p][2*p - 1];
		for(int i = 0; i<p-1; i++)
			this.data[p-1][i]=-1;
		this.inf = new Coordinates();
		this.sup = new Coordinates();
		this.size = 0;
	}

	Polyomino(int p, int[][] data, Coordinates inf, Coordinates sup) {
		this.p = p;
		this.data = data;
		this.inf = inf;
		this.sup = sup;
		this.size=p;
	}

	static Polyomino fromString(String repr) {
		if (repr.length() < 2) {
			throw new RuntimeException("String is too short to represent a polyomino.");
		}
		if (repr.charAt(0) != '[') {
			throw new RuntimeException("Expected '[' as the first character of the string.");
		}
		if (repr.charAt(repr.length() - 1) != ']') {
			throw new RuntimeException("Expected ']' as the last character of the string.");
		}
		repr = repr.substring(1, repr.length() - 1);
		String[] coordsString = repr.split(", ");
		int[][] coordinates = new int[2][coordsString.length];
		int i = 0;
		for (String coords : coordsString) {
			coords = coords.substring(1, coords.length() - 1);
			String[] xy = coords.split(",");
			coordinates[0][i] = Integer.parseInt(xy[0]);
			// System.out.println(coordinates[0][i]);
			coordinates[1][i++] = Integer.parseInt(xy[1]);
		}
		
		int p = coordinates[0].length;
		int posx = minimum(coordinates[0]);
		int posy = minimum(coordinates[1]);
		int[][] data = new int[2 * p - 1][p + 1];
		for (int j = 0; j < p; j++) {
			data[(p-1) - coordinates[1][j] - posy][coordinates[0][j] - posx + 1] = 1;
		}
		Coordinates inf = new Coordinates(posx, posy);
		posx = maximum(coordinates[0]);
		posy = maximum(coordinates[1]);
		Coordinates sup = new Coordinates(posx, posy);
		return new Polyomino(p, data, inf, sup);
	}
	
	public static int minimum(int[] table) {
		int min = table[0];
		for(int i = 1 ; i < table.length; i++)
			if(min > table[i]) min = table[i];
		return min;
	}
	
	public static int maximum(int[] table) {
		int max = table[0];
		for(int i = 1 ; i < table.length; i++)
			if(max < table[i]) max = table[i];
		return max;
	}

	static public List<Polyomino> list_from_file(File file) throws IOException {
		List<Polyomino> polyominoes = new LinkedList<>();
		// read a file line-by-line:
		// https://stackoverflow.com/a/5868528
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			for (String line; (line = br.readLine()) != null;) {
				polyominoes.add(fromString(line));
			}
		}
		return polyominoes;
	}

	public void print() {
		// StringBuilder b = new StringBuilder();
		// b.append("Polydomino : \n");
		System.out.println("Polyomino : \n");
		for (int i = 0; i < this.data.length; i++) {
			for (int j = 1; j < this.data[0].length; j++) {
				// b.append(this.data[i][j] + ' ');
				System.out.print(this.data[i][j]);
			}
			// b.append('\n');
			System.out.println();
		}
		// return b.toString();
	}
	
	public void printFixed() {
		// StringBuilder b = new StringBuilder();
		for (int i = 0; i < p-1; i++) {
			for (int j = 0; j < 2*p-1; j++) {
				// b.append(this.data[i][j] + ' ');
				System.out.print(this.data[i][j]);
			}
			// b.append('\n');
			System.out.println();
		}
		for (int j = 0; j < p-1 ; j++)
			System.out.print(0);
		for (int j = p-1; j < 2*p-1; j++)
			System.out.print(this.data[p-1][j]);
		System.out.println();
		// return b.toString();
	}

	/*
	 * /!\ All these methods are inplace!
	 */

	public Polyomino translate(int dx, int dy) {
		this.sup.add(dx, dy);
		this.inf.add(dx, dy);
		return this;

	}
	
	public Polyomino rotate_ccw() {
		int tmp = this.data[p-1][1];
		this.data[1]=new int[2*p-1];
		int empty_height =this.p-this.sup.y-1;
		int all_height = this.p - 1 - this.inf.y;
		int x = this.sup.x;
		for(int j =x+1; j>= this.inf.x+1;j--) {
			for(int i = all_height; i >= empty_height; i--) {
				this.data[this.p-2+j][all_height-i+1] = this.data[i][j];
				this.data[i][j]=0;
			}
		}
		this.data[p-1][1] = tmp;
		this.sup.set(this.sup.y, -this.inf.x);
		this.inf.set(this.inf.y, -x);
		return this;
	}
	
	public Polyomino rotate(int n_quarter_turns) {
		switch (n_quarter_turns % 4) {
		case 1:
			this.vertical().horizontal();
			this.center();
			this.rotate_ccw();
			
			break;
		case 2:
			this.vertical().horizontal();
			break;
		case 3:
			this.rotate_ccw();
			break;
		default:
			break;
		}
		return this;
	}

	public Polyomino mirror_y_axis(int y0) {
		this.vertical();
		int y = this.inf.y;
		this.inf.set(this.inf.x , 2 * y0 - this.sup.y);
		this.sup.set(this.sup.x , 2 * y0 - y);
		return this;
	}

	public Polyomino mirror_x_axis(int x0) {
		this.horizontal();
		int x = this.inf.x;
		this.inf.set(2*x0 - this.sup.x, this.sup.y);
		this.sup.set(2*x0 - x, this.inf.y);
		return this;
	}

	public Polyomino center() {
		int y = this.sup.y-this.inf.y;
		int x = this.sup.x-this.inf.x;
		for(int i = p-1-y; i<=p-1;i++) {
			for(int j = 1; j<=x+1;j++) {
				this.data[i][j]=this.data[y+i][this.inf.x+j];
				this.data[y+i][this.inf.x+j]=0;
			}
		}
		this.inf.set(0, 0);
		this.sup.set(x, y);
		return this;
	}

	public Polyomino vertical() {
		int x = this.sup.x-this.inf.x;
		for (int i = 0; i <= this.sup.y-this.inf.y; i++) {
			for (int j = 1; j < (this.p/2); j++) {
				int tmp = this.data[this.p - 1 -i][j];
				this.data[this.p-1-i][j] = this.data[this.p-1 -i][this.p - j + 1];
				this.data[this.p-1-i][this.p - j + 1] = tmp;
			}
			
			for (int j = 1; j <= x+1; j++) {
				this.data[this.p-1-i][j] = this.data[this.p-1-i][j+this.p-x-1];
				this.data[this.p-1-i][j+this.p-x-1]=0;
			}	
		}
		return this;
		
	}

	public Polyomino horizontal() {
		int y = this.sup.y;
		for (int j = 0; j <= this.sup.x-this.inf.x; j++) {
			for (int i = 0; i < this.p-1; i++) {
				this.data[2 * this.p - 2 - i][j+1] = this.data[i][j+1];
				this.data[i][j+1] = 0;
			}
		}
		this.sup.set(this.sup.x, -this.inf.y);
		this.inf.set(this.inf.x, -y);
		
		return this;
	}

	public Polyomino set(int x, int y) {
		return this.center().translate(x, y);
	}

	// this method is not in place
	public Polyomino dilatate(int r) {
		// TODO
		return null;
	}
	
	public void setOccupied(Coordinates c) {
		this.data[p-1-c.y][c.x+p-1] = 1;
		this.size++;
	}
	
	public void setReachable(Coordinates c) {
		this.data[p-1-c.y][c.x+p-1] = 2;
	}
	
	public void setFree(Coordinates c) {
		this.data[p-1-c.y][c.x+p-1] = 0;
	}
	
	public int getCell(Coordinates c) {
		return this.data[p-1-c.y][c.x+p-1];
	}
	
	public boolean inChess(Coordinates c) {
		if(c.y == p-1 && c.x+p-1 < 0)	return false;
		return p-1-c.y <= p -1 && c.x < p && p-1-c.y>=0 && c.x>-(p-1) ;
	}
	
	public void setData(int[][] data) {
		this.data = data;
	}
	
	public Polyomino copy() {
		int[][] data_cop = new int[data.length][data[0].length];
		for (int i = 0; i < this.data.length; i++) {
			for (int j = 0; j < this.data[0].length; j++)
				data_cop[i][j]=data[i][j];
		}
		return new Polyomino(p,data_cop,inf.copy(),sup.copy());
	}
}
