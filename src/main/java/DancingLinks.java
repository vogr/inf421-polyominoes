import java.util.*;

public class DancingLinks {
	public ColumnObject H;
	int[][] M;

	DancingLinks (int[][] M) {
		this.M = M;
		H = new ColumnObject(0);
		DataObject[][] matrix = new DataObject[M.length][M[0].length];
		HashSet<Integer> visitedRows = new HashSet<>();

		ColumnObject current_col = H;
		// Create the ColumnObjects by going to the right
		for (int j = 0; j < M[0].length; j++) {
			current_col.setRight(new ColumnObject(1 + j));
			current_col = current_col.R;
			DataObject current_line = current_col;
			// Create DataObjects by going down
			for (int i = 0; i < M.length; i++)
				if (M[i][j] == 1) {
					// Make sure the DataObject has not already been created by a loop on the rows
					// (see below)
					if (matrix[i][j] == null)
						matrix[i][j] = new DataObject(current_col, i);
					else
						matrix[i][j].setColumn(current_col); // If already created, set the ColumnObject associated
					current_line.setDown(matrix[i][j]);
					current_col.incrementSize();
					current_line = current_line.D;
					// Make the connections in the row if it hasn't already been done
					if (!visitedRows.contains(i)) {
						DataObject rowLoop = current_line;
						for (int k = j + 1; k < M[i].length; k++)
							if (M[i][k] == 1) {
								matrix[i][k] = new DataObject(i);
								rowLoop.setRight(matrix[i][k]);
								rowLoop = matrix[i][k];
							}
						visitedRows.add(i);
						rowLoop.setRight(current_line); //Close the loop on the row
					}
				}
			current_line.setDown(current_col); //Close the loop on the column
		}
		current_col.setRight(H); //Close the loop on the ColumnObjects
	}

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
		ColumnObject cur_col = this.H;
		do {
			System.out.println(cur_col);
			cur_col = cur_col.R;
		} while (cur_col != this.H);
	}
	
	public List<List<Integer>> exactCover() {
		List<List<Integer>> P = new ArrayList<>();

		if(H.R == H) {P.add(new ArrayList<>()); return P;}
		ColumnObject x = this.getMin();
		x.coverColumn();
		DataObject t = x.U;
		while(t != x) {
			DataObject y = t.L;
			while(y != t) {
				y.coverColumn();
				y = y.L;
			}
			for(List<Integer> partition : exactCover()) {
				if (partition.contains(t.line))  {
					System.out.println("Error");
				}
				partition.add(t.line);
				P.add(partition);
			}
			y = t.R;
			while(y != t) {
				y.uncoverColumn();
				y=y.R;
			}
			t = t.U;
		}
		x.uncoverColumn();
		return P;
	}

	public void print_solutions(List<List<Integer>> P) {
		for(List<Integer> partition : P) {
			Collections.sort(partition);
			System.out.print("Une solution est : ");
			for(int i : partition) {
				System.out.print("{ ");
				for(int j = 0 ; j < M[i].length ; j++)
					if(M[i][j] == 1) System.out.print(j + " ");
				System.out.print("}");
			}
			System.out.print(String.format("\t(lignes de M : %s)", partition));
			System.out.println();
		}
	}
}
