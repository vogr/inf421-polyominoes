import java.util.HashSet;

public class DataStructure {
	public static ColumnObject H;
	
	DataStructure(){
		H = new ColumnObject();
	}
	
	public void dancing_links(int[][] M) {
		DataObject[][] Matrix = new DataObject[M.length][M[0].length];
		HashSet<Integer> visitedRows = new HashSet<Integer>();
		H = new ColumnObject(0);
		ColumnObject begin = H;

		// Create the ColumnObjects by going to the right
		for (int j = 0; j < M[0].length; j++) {
			H.setRight(new ColumnObject(1 + j));
			H = H.R;
			DataObject cell = H;
			// Create DataObjects by going down
			for (int i = 0; i < M.length; i++)
				if (M[i][j] == 1) {
					// Make sure the DataObject has not already been created by a loop on the rows
					// (see below)
					if (Matrix[i][j] == null)
						Matrix[i][j] = new DataObject(H);
					else
						Matrix[i][j].setColumn(H); // If already created, set the ColumnObject associated
					cell.setDown(Matrix[i][j]);
					H.incrementSize();
					cell = cell.D;
					// Make the connections in the row if it hasn't already been done
					if (!visitedRows.contains(i)) {
						DataObject rowLoop = cell;
						for (int k = j + 1; k < M[i].length; k++)
							if (M[i][k] == 1) {
								Matrix[i][k] = new DataObject();
								rowLoop.setRight(Matrix[i][k]);
								rowLoop = Matrix[i][k];
							}
						visitedRows.add(i);
						rowLoop.setRight(cell); //Close the loop on the row
					}
				}
			cell.setDown(H); //Close the loop on the column
		}
		H.setRight(begin); //Close the loop on the ColumnObjects
		H = begin;
	}
	
	//TODO
	public ColumnObject getMin() {
		ColumnObject current = this.H.R;
		ColumnObject minimumColumn = current;
		int minimum = current.getSize();
		while (this.H != current) {
			current = current.R;
			if(minimum < current.getSize()) {
				minimum = current.getSize();
				minimumColumn = current;
			}
		} 
		return minimumColumn;
	}
	
	public void print() {
		ColumnObject begin = this.H;
		do {
			System.out.println(this.H);
			this.H = this.H.R;
		} while (this.H != begin);
	}
	
	public HashSet<HashSet<HashSet<Integer>>> exactCover() {
		HashSet<HashSet<HashSet<Integer>>> P = new HashSet<HashSet<HashSet<Integer>>>();
		if(H.R == H) {P.add(new HashSet<HashSet<Integer>>()); return P;}
		ColumnObject x = this.getMin();
		x.coverColumn();
		DataObject t = x.U;
		while(t!=x) {
			HashSet<Integer> T = new HashSet<Integer>();
			T.add(t.C.N);
			DataObject y = t.L;
			while(y != t) {
				y.coverColumn();
				T.add(y.C.N);
				y=y.L;
			}
			for(HashSet<HashSet<Integer>> partition : exactCover()) {
				partition.add(T);
				P.add(partition);
			}
			y = t.R;
			while(y != t) {
				y.uncoverColumn();
				T.add(y.C.N);
				y=y.R;
			}
			t=t.U;
		}
		x.uncoverColumn();
		return P;
	}
}
