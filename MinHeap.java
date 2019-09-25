/*
Abbey Silson 1315323
Curtis Barnes 1229191*/

// min heap used for 
public class MinHeap { 
    private String[] Heap; 
    private int size; 
    private int maxsize; 
  
  //variable to set index for the root of the heap
    private static final int FRONT = 1; 
  
  //constructor to create a minheap, takes in an int to set the size of the heap 
    public MinHeap(int maxsize) 
    { 
        this.maxsize = maxsize; 
        this.size = 0; 
        Heap = new String[this.maxsize + 1]; 
        Heap[0] = ""; 
    } 
  
    // returns the parent of the index passed in 
    private int parent(int pos) 
    { 
        return pos / 2; 
    } 
  
    // returns the left child of the index passed in 
    private int leftChild(int pos) 
    { 
        return (2 * pos); 
    } 
  
    // returns the right child of the index passed in as pos
    private int rightChild(int pos) 
    { 
        return (2 * pos) + 1; 
    } 
  
    // checks if the string at the position passed in is a leaf node, returns true if yes
    private boolean isLeaf(int pos) 
    { 
        if (pos >= (size / 2) && pos <= size) { 
            return true; 
        } 
        return false; 
    } 
  
    // swaps two nodes of the heap 
    private void swap(int fpos, int spos) 
    { 
        String tmp; //makes a temp variable so the 2 strings can be swapped
        tmp = Heap[fpos]; 
        Heap[fpos] = Heap[spos]; 
        Heap[spos] = tmp; 
    } 
  
    // sorts the heap 
    private void minHeapify(int pos) 
    { 
        // If the node is a non-leaf node and greater 
        // than any of its child 
        if (!isLeaf(pos) || (pos == 1 )) { //if the current index is not a leaf node, or is the root
		if(pos == 1){//if it is the root
			
			if(Heap[leftChild(pos)] == null){//if it has a left child, return 
				return;
			}
			else if(Heap[rightChild(pos)] == null){ ///if it has no right child
				if(Heap[pos].compareTo(Heap[leftChild(pos)]) > 0){ //swap the left child with the current 
					swap(pos, leftChild(pos));
					}
				return;
			}
				
		}
			
            if (Heap[pos].compareTo(Heap[leftChild(pos)]) > 0 || Heap[pos].compareTo(Heap[rightChild(pos)]) > 0) { //if either of the children is smaller
  		
                // swap the current with the left child and resort the tree
                // the left child 
                if (Heap[leftChild(pos)].compareTo(Heap[rightChild(pos)]) < 0) { //if the left child is smaller
                    swap(pos, leftChild(pos)); 
                    minHeapify(leftChild(pos)); 
                } 
                else { //swap current with the right child
                    swap(pos, rightChild(pos)); 
                    minHeapify(rightChild(pos)); 
                } 
            } 
        } 
    } 
  
    // adds a string to the tree at the next available position in the tree, then sorts the tree to ensure min heap order is enstilled
    public void insert(String element) 
    { 
        Heap[++size] = element; 
        int current = size;   
        while (Heap[current].compareTo(Heap[parent(current)]) < 0) { 
            swap(current, parent(current)); 
            current = parent(current); 
        } 
    } 
  
    // Function to print the contents of the heap 
    public void print() 
    { 
        for (int i = 1; i <= size / 2; i++) { 
            System.out.print(" PARENT : " + Heap[i] 
                     + " LEFT CHILD : " + Heap[2 * i] 
                   + " RIGHT CHILD :" + Heap[2 * i + 1]); 
            System.out.println(); 
        } 
System.out.println(); 
	if(Heap[FRONT] == null){
		System.out.println("no front");
	}
    } 
  
    // builds the heap using the sort function
    public void minHeap() 
    { 
        for (int pos = (size / 2); pos >= 1; pos--) { 
            minHeapify(pos); 
        } 
    } 

	//reduces the max size of the heap by 1
	public void decrementMax(){
		maxsize--;
	}
  
    // removes the root of the tree and returns this as a string 
    public String remove() 
    { 
        String popped = Heap[FRONT]; 
        Heap[FRONT] = Heap[size--];
		Heap[++size] = null;
        minHeapify(FRONT); 
		size--;
        return popped; 
    } 

	//returns the max size of the tree
	public int getMaxSize(){
		return maxsize;
	}

	//returns the string at the front(root) of the tree
	public String getFront(){
		return Heap[FRONT];
	}
	
	//sets the max size of the tree
	public void setMaxSize(int max){
		maxsize = max;
	}
	
	//returns the current size (index pointer) of the tree
	public int getSize(){
		return size;
	}

	//sets the size of the tree
	public void setSize(int s){
		size = s;
	}  
} 
