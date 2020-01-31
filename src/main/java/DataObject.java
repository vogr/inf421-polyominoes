
public class DataObject {
	public DataObject U,D,R,L;
	ColumnObject C;
	
	DataObject(){
		U=null;
		D=null;
		R=null;
		L=null;
		C=null;
	}

	public DataObject(DataObject u, DataObject d, DataObject r, DataObject l, ColumnObject c) {
		super();
		U = u;
		D = d;
		R = r;
		L = l;
		C = c;
	}
	
	public DataObject(ColumnObject c) {
		C = c;
	}
	
	public void setColumn(ColumnObject c) {
		C = c;
	}
	
	public void setLeft(DataObject that) {
		this.L = that;
		that.R = this;
	}
	
	public void setRight(DataObject that) {
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
	
	public void removeUP() {
		this.U.D = this.D;
		this.D.U = this.U;
		this.C.decrementSize();
	}
	
	public void removeLR() {
		this.R.L = this.L;
		this.L.R = this.R;
	}
	
	public int test() {
		return 1;
	}
	
	public void coverColumn() {
		this.C.coverColumn();
	}
	
	public void uncoverColumn() {
		this.C.uncoverColumn();
	}
	
	

}
