
public class cell {
	Coordinates value;
	cell next;
	int size;
	
	cell(){
		this.value = null;
		this.next = null;
		this.size = 0;
	}
	
	cell(Coordinates value){
		this.value = value;
		this.next = null;
		this.size = 1;
	}
	
	cell(Coordinates value, cell next){
		if(next == null)	this.size = 1;
		else 				this.size = 1 + next.size;
		this.value = value;
		this.next = next;
	}
	
	public cell copy() {
		if(this.next==null) 	return new cell(this.value.copy());
		return new cell(this.value.copy(), this.next.copy());
	}

}
