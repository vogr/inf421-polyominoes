import java.util.Iterator;

public class LinkedListCoordinates implements Iterable<cell>{
	cell first;
	
	LinkedListCoordinates(){
		this.first = null;
	}
	
	LinkedListCoordinates(cell first){
		this.first = first;
	}
	
	LinkedListCoordinates(Coordinates value){
		this.first = new cell(value);
	}
	
	LinkedListCoordinates(Coordinates value, cell next){
		this.first = new cell(value,next);
	}
	
	public Coordinates removeFirst() {
		Coordinates c = this.first.value;
		this.first = this.first.next;
		return c;
	}
	
	public LinkedListCoordinates addFirst(Coordinates c) {
		return new LinkedListCoordinates(c,this.first);
	}
	
	public boolean isEmpty() {
		return this.first == null;
	}
	
	public LinkedListCoordinates copy() {
		if(this.isEmpty())	return new LinkedListCoordinates();
		return new LinkedListCoordinates(this.first.copy());
	}
	
	public static void main(String[] args) throws CloneNotSupportedException {
		cell first1 = new cell(new Coordinates(3,8));
		LinkedListCoordinates test1 = new LinkedListCoordinates(first1);
		LinkedListCoordinates test2 = test1.copy();
		Coordinates c = new Coordinates(8,9);
		test2 = test2.addFirst(c);
		System.out.println(test1.first.value);
		System.out.println(test2.first.value);
	}

	@Override
	public Iterator<cell> iterator() {
		// TODO Auto-generated method stub
		cell current = this.first;
		Iterator<cell> it = new Iterator<cell>() {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return current.next !=  null;
            }

            @Override
            public cell next() {
                return current.next;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }
	
}
