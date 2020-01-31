
public class ColumnObject extends DataObject {
	public ColumnObject R,L;
	private int S;
	public final int N;
	
	ColumnObject(){
		U=null;
		D=null;
		R=null;
		L=null;
		S=0;
		N=0;
	}
	public ColumnObject(DataObject u, DataObject d, ColumnObject r, ColumnObject l, int s, char n) {
		U = u;
		D = d;
		R = r;
		L = l;
		S = s;
		N = n;
	}
	
	public ColumnObject(char n) {
		N = n;
	}
	
	public ColumnObject(int n) {
		N = n;
	}
	
	public int getSize() {
		return S;
	}
	
	public void incrementSize() {
		S++;
	}
	
	public void decrementSize() {
		S--;
	}
	
	public void setSize(int s) {
		S = s;
	}

	public void setLeft(ColumnObject that) {
		this.L = that;
		that.R = this;
	}
	
	public void setRight(ColumnObject that) {
		this.R = that;
		that.L = this;
	}
	
	public void setUp(DataObject that) {
		this.U = that;
		that.D = this;
	}
	
	public void setDown(DataObject that) {
		this.D = that;
		that.U = this;
	}
	
	public void removeLR() {
		this.R.L = this.L;
		this.L.R = this.R;
	}
	
	public void coverColumn() {
		this.removeLR();
		DataObject current = this;
		while (current.D != this) {
			current = current.D;
			DataObject line = current;
			while (line.R != current) {
				line = line.R;
				line.removeUP();
			}
		}
	}
	
	public void uncoverColumn() {
		this.setRight(this.R);
		this.setLeft(this.L);
		DataObject current = this;
		while (current.U != this) {
			current = current.U;
			DataObject line = current;
			while (line.L != current) {
				line = line.L;
				line.setDown(line.D);
				line.setUp(line.U);
				line.C.incrementSize();
			}
		}
	}
	
	@Override
	public String toString() {
		return "ColumnObject [Size=" + S + ", Name=" + N + "]";
	}
	
	
	
	
}
