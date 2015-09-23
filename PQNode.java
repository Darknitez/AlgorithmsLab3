public class PQNode<T> {
	//make PQNode contain key, value, and Handle
	int key;
	T value;
	Handle<T> handle;

	PQNode(int k, T v){
	    key = k;
	    value = v;
	    handle = new Handle<T>(0);
	}
	
	public int getKey() {
		return key;
	}
	public void setKey(int i){
		this.key = i;
	}
	public T getValue() {
		return value;
	}
	public void setVal(T val){
		this.value = val;
	}
	
	public int getHandIndex(){
		return handle.getIndex();
	}
	public void setHandIndex(int i){
		handle.setIndex(i);
	}
	public void setHand(Handle<T> h){
		this.handle = h;
	}
	
	public Handle<T> getHand(){
		return handle;
	}
	
	
	
}
