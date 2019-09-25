import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


/*Abbey Silson 1315323
Curtis Barnes 1299191*/

public class MakeRuns{
	public static void main(String[] args){
		try{
		ArrayList<String> babyArray = new ArrayList<String>(); //Array stores lines of text that are too small for the priority queue
		int max; //Maximum size of priority queue
		int counter = 0; 
		int runCount = 0; //number of runs produced
		String lastOut; //Last run retrieved from priority queue
		String tempDir = System.getProperty("user.dir"); //Directory for storing temp files
		File tempFile = new File(tempDir + File.separator + "temp.txt"); //Temporary file that stores each run
//File.createTempFile("temp", ".tmp");
		//System.out.println("temp file in default location: " + tempFile.getAbsolutePath());
		BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile)); //Used to write runs to temporary file
	
		if(args.length != 1){ //Ensures user has input a size for the priority queue
			System.err.println("Please enter a maximum size for the priority queue");
			return;
		}
		else{
			try{
				max = Integer.parseInt(args[0]); //Takes user input to set the maximum size of priority queue		
			}
			catch(Exception e){ //If there is either no or invalid input
				System.err.println("Please enter a max priority queue size");
				return;
			}
		}
			
		MinHeap pq = new MinHeap(max); //Creates priority queue with a maximum size equal to the users input
			
			Scanner s = new Scanner(System.in); //Creates scanner for reading lines of text from a file

			while(s.hasNext()){ //While the scanner has more lines to read
				if(counter < max){
				String line = s.nextLine(); //Stores the next line read by the scanner
				if(line.contains("eol")){ //If the line contains the characters used to delimit runs
					line = "+" + line; //Adds a plus symbol to the line, so the text is not mistaken for a legitimate end of line delimiter
				}
				pq.insert(line);
				counter++;}
				else break;								
			}
			lastOut = pq.remove(); //Retrieves text at the head of the priority queue and removes it from the queue. Remaining items, will reshuffle to occupy empty space
			bw.write(lastOut + "\n");	
			String current = "";
			
			//System.out.println("printing after first removal");
			//pq.print();
			while(s.hasNext() || babyArray.size() > 0 || pq.getFront() != null){ //While there are more lines to read or the priority queue or baby array are not empty
				if(s.hasNext() == false && babyArray.size() == 0){ //If only the priority queue still has items
					lastOut = pq.remove(); //Retrieves text at the head of the priority queue and removes it from the queue. Remaining items, will reshuffle to occupy empty space
					System.out.println("removed front");
					bw.write(lastOut + "\n"); //Writes line to text file and moves to new line				
				}
				else{
					if(s.hasNext()){ //While there are more lines to read in  text file
						current = s.nextLine(); //stores next line of text in file
						//System.out.println(current);
						if(current.contains("eol")){ //If the line contains the characters used to delimit runs
							current = "+" + current; //Adds a plus symbol to the line, so the text is not mistaken for a legitimate end of line delimiter
						}						
						}
					else if(babyArray.size() > 0){	 //If baby array contains values					
						current = babyArray.get(0); //stores next line of text from the babyArray
						babyArray.remove(0); //Remove value from baby array
						}

					
					if(pq.getFront() == null){ //If the priority queue is empty
						//System.out.println("no front");
						if(current.compareTo(lastOut) > 0){ //Compares current line 
							bw.write(lastOut + "\n"); //Writes line to text file and moves to new line
						}
						else{
							babyArray.add(current); //Add current line to the baby array
							pq.decrementMax(); //Decrease maximum size of priority queue
							//System.out.println("dec max" + pq.getMaxSize());
						}
					}
					else{		
						//System.out.println("current: " + current);
						if(current.compareTo(pq.getFront()) > 0){ //If the current line of text is greater than the head of the priority
							pq.insert(current); //Adds current line of text to priority queue
							//System.out.println("added " + current);
						}
						else{
							babyArray.add(current); //Add line of text to baby array
							pq.decrementMax(); //Decrease maximum size of priority queue by 1
							//System.out.println("added to baby " + pq.getMaxSize());
						}
					
						//pq.print();
						lastOut = pq.remove();
						//System.out.println("removed front");
						//pq.print();
						bw.write(lastOut + "\n"); //Writes line to text file and moves to new line
						
					}
				}
				if(pq.getMaxSize() == 0){ //If the priority queue is empty		
						bw.write("eol" + "\n"); //Writes end of line delimiter to text file and moves to new line
						//System.out.println("printed end");
						runCount++; //Increments the number of runs
						pq.setMaxSize(max); //Creates new priority queue
						counter = 0; //Resets counter to zero
						for(int i = 0; i < babyArray.size(); i++){ //For each item in the baby array
							if(counter < max){ //If the number of items in the priority queue does not exceed the maximum capacity
								pq.insert(babyArray.get(i)); //Take value from baby array and add it to priority queue
								babyArray.remove(i); //Remove item from babyArray
								counter++; //Increase count of items in priority queue
							}
							else //Prioirty queue is already full
								break;
						}
						while(s.hasNext()){	
							if(counter < max){
								pq.insert(s.nextLine());
								counter++;							
							}
							else
								break;
						}
					lastOut = pq.remove(); //Retrieves text at the head of the priority queue and removes it from the queue. Remaining items, will reshuffle to occupy empty space
					bw.write(lastOut + "\n"); //Writes line to text file and moves to new line
					//pq.print();
					}
				
			}
			
			bw.write("eol" + "\n"); //Writes end of line delimiter to text file and moves to new line
			runCount++; //Increments the number of runs
			System.out.println(runCount);
			bw.close(); //Closes buffered writer
			
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}
}
