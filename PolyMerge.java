import java.util.Scanner;
import java.io.File;
import java.util.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/*
Abbey Silson 1315323
Curtis Barnes 1299191*/
public class PolyMerge{
	
	public static void main(String[] args){
	int numTapes;
	int runs = 0;
	int length = 0;
	String tempdir = System.getProperty("user.dir");
	String outputFileName = "output.txt";
	int zero; 
	int numLines = 1;
	int filesOpened = 0;
	boolean removal = false;
		
		//checks that the correct number of arguements have been passed in 
		try{
			if(args.length != 2){	//if incorrect number, print correct useage to console
			System.err.println("Usage: number of tapes, number of runs");
			return;
			}
			else{
				try{
					numTapes = Integer.parseInt(args[0]);	//sets the first parameter as the number of tapes to use
					zero = numTapes - 1;		//sets zero to the last tape index
					runs = Integer.parseInt(args[1]);	//sets the number of actual runs are in the input file
				}
				catch(Exception e){
					e.printStackTrace();
					return;
				}
			}

		int[] fibSeq = new int[runs];
		int fibNum = 0;
		int index = 0;
 
		fibSeq[index] = fibNum;
		fibNum++;
		index++;
		fibSeq[index] = fibNum;

		//System.out.println("fibNum " + fibNum + " runs" + runs);
		
		while(fibNum < runs){
			fibNum = fibSeq[index] + fibSeq[index-1];
			index++;
			fibSeq[index] = fibNum;
		}

		//System.out.println("fib : " + fibNum);
		int fibTapes = numTapes - 1;
		int[] fibList = new int[fibTapes];
		int x = fibTapes;
		for(int i=0; i < fibTapes; i++){
			fibList[i] = fibSeq[index - x];
			x--;
		}
		

		//System.out.println("runs for tapes: " + fibList[0] + ", " + fibList[1] + ", " + fibList[2]);

		File tempFile = new File(tempdir + File.separator + "temp.txt");	//gets the file to read the runs out of
		Scanner sc = new Scanner(tempFile);			//makes a scanner to read through the file

		int[] runsInFile = new int[numTapes];	//makes an array to store the number of runs that should be in each tape
		int currFile = 0;			//sets an index for the current file - used in while loop
		String line;

		File cOut = new File(tempdir + File.separator + "output" + currFile + ".txt");	//creates an output file to write the runs to
		BufferedWriter bw = new BufferedWriter(new FileWriter(cOut));	//makes a writer to write each line of the runs to the output file

		while(sc.hasNext()){	// while there is another line in the file
			line = sc.nextLine();	//set line to the next line
			
			if(line.equals("eol")){	//if the line is equal to the end of a run
				bw.write(line + "\n");	//write this to the run
				runsInFile[currFile]++;	//increment the number of runs in the current file
				if(runsInFile[currFile] == fibList[currFile]){	//if the correct number of runs is in the file, go to the next output file
					currFile++;	//go to the next file
					cOut = new File(tempdir + File.separator + "output" + currFile + ".txt"); //create a new file to output the next set of runs
					bw.close();	//close the writer
					bw = new BufferedWriter(new FileWriter(cOut)); //reset the writer to the new output file
				}	
			}
			else{
				bw.write(line + "\n"); //write the line of text to the file
			}
			length++;
		}
		//creates dummy runs to pad out tapes so it fits the fib distribution
		while(true){
			bw.write("eol" + "\n");	//write an end of run delimiter
			runsInFile[currFile]++;	//increment the number of runs in the current file
			if(fibList[currFile] == runsInFile[currFile]){	//if the correct number of runs is in the file, go to the next output file
				currFile++;	//move to the next file
				cOut = new File(tempdir + File.separator + "output" + currFile + ".txt");	//make a new outputfile
				bw.close();	
				bw = new BufferedWriter(new FileWriter(cOut));
			}	
			
			if(currFile == numTapes - 1) break;	//if all the tapes have been filled, break out of the loop
		}
		bw.close(); //close the writer
		sc.close(); //close the scanner
		
		int zCounter = 0;	//creates a variable to keep track of how many tapes have 0 runs 
		MinHeap output = new MinHeap(length);	//creates a minheap used to perform the merge sort
		int smallest = runsInFile[numTapes - 2];	//creates a variable used to check the smallest amount of runs to remove
		
		ArrayList<Scanner> scanners = new ArrayList<Scanner>();	//creates an array list of scanners used to read each tape
		File mergedOut = new File(tempdir + File.separator + "output" + zero + ".txt");	//set the first file to write to
		BufferedWriter write = new BufferedWriter(new FileWriter(mergedOut));	// writer to write to the output file
		ArrayList<File> files = new ArrayList<File>();	//array list to hold each file
		filesOpened++;	

		for(int i = 0; i <= numTapes - 1; i++){	
			File inRun = new File(tempdir + File.separator + "output" + i + ".txt"); //get the name of output file at i
			files.add(inRun); //add the file to the files array
			Scanner s = new Scanner(inRun);	//make a new scanner for the file
			scanners.add(s); //add the scanner to the array of scanners
		}	
		int in = 0; //used as an index counter
		int runsToRemove = runsInFile[0];	//sets how many runs to remove this pass - always begins with position 0, decremented in while loop
		int rtr = runsToRemove;	//variable of tapes being removed from current run - unchanging
		int pass = 0;
		int oZero = numTapes -1;
		int finalCount = 0;
		boolean finalMerge = false;
		while(zCounter < numTapes - 1){
			while(runsToRemove > 0){	//while there are still runs to be removed from the tape
				runsToRemove--;		
				for(int i = 0; i < scanners.size(); i++){	//for each of the scanners(tapes)
					if(runsInFile[i] == 0){continue;}	//if there is no runs in the file, move onto the next scanner
					while(scanners.get(i).hasNext()){	//while there is another line in the file
						String l = scanners.get(i).nextLine();	//get the next line
						//System.out.println(finalCount);
						if(finalMerge){		//if the final merge is about to occur, strip the "+" that escaped the oel delimiter in the makeruns file	
							if(l.contains("+eol")){
								l = l.substring(1);
								output.insert(l);
								}
							else{
								if(!l.equals("eol")){
									output.insert(l);
								}
							}
						}
						else{
							if(!l.equals("eol")){	//if it is NOT a end of line delimiter
								output.insert(l);	//add it to the heap
							}
							else{
								break;	//if it IS delimiter, break out and decrease the number of runs value 
							}
						}
					}
					runsInFile[i]--;
				}				
				while(output.getSize() > 0){	//while there is still items in the heap	
					write.write(output.remove() + "\n");	//write to the output file
				}
				if(!finalMerge){
					write.write("eol" + "\n");	//write an eol delimiter
				}
			}
			
			write.close();	//close the writer
			zCounter = 0;	//reset the zcounter to 0
			runsInFile[zero] = rtr;	//set the number of runs that are in the file at 0 as number of runs that have just been removed
			oZero = zero;
			finalCount = 0;
			
			for(int i = 0; i < numTapes; i++){//for each of the tapes
				File f = new File(tempdir + File.separator + "output" + i + ".txt");	//get the file for the current tape	
				if(i != oZero){//if it isn't the tape which is being written to 					
					overwrite(f, rtr);	//overwrite the file 
					filesOpened += 2;
				}
				if(runsInFile[i] != 0){	//find the new smallest number of runs to remove
					if(smallest > runsInFile[i]){	
					smallest = runsInFile[i];}
				}				
				if(runsInFile[i] == 0){	//if there are no runs in the file, incremenet the zcounter, set zero to this file
					zero = i;
					zCounter++;			
				}
				if(runsInFile[i] == 0 || runsInFile[i] == 1){ //if there is only 1 run, or 0 left in the file, increment final count
					finalCount++;
				}
				if(finalCount == numTapes){
						finalCount++;
						removal = true;
				}
				if(finalCount == numTapes+1){
					finalMerge = true;
				}
			}			
			runsToRemove = smallest;
			rtr = smallest;
			mergedOut = new File(tempdir + File.separator + "output" + zero + ".txt");
			write = new BufferedWriter(new FileWriter(mergedOut));
			filesOpened++;	
			pass++;
			output = new MinHeap(length);
			in = 0;
			scanners.clear();
			for(int i = 0; i <= numTapes - 1; i++){
				Scanner s = new Scanner(files.get(i));
				scanners.add(s);			
			}		
		}
			System.err.println(filesOpened);
		}
		catch(Exception e){
			e.printStackTrace();				
		}
		
	}

	/* method which takes in 2 parametes, a file and a number of runs and removes the number of runs from the beginning of that file, then uses a temp file to overwrite it */
	public static void overwrite(File _file, int runs){
	try{
	File tempO = new File("overwrite.txt");	//make a temp file to overwrite to
	BufferedWriter write = new BufferedWriter(new FileWriter(tempO));	//make a writer for this file

	File file = _file;
	Scanner s = new Scanner(file); //make a scanner for the passed in file
	//System.out.println(file);
	String line;

	while(runs > 0){	//while there are lines to remove
		if(s.hasNext()){	//while there is another line in the file
			line = s.nextLine();
			if(line.equals("eol")){	//if it is an end of line delimiter, decrement number of runs to remove
				runs--;
			}
		}
		else{
			runs--;	//if there is no other lines to read, decrement runs
		}
	}	
	while(s.hasNext()){	//while the scanner has something else to read
		String d = s.nextLine();
		write.write(d + "\n");	//write it to the temp file
	}
	write.close();
	s.close();
	BufferedWriter finalwrite = new BufferedWriter(new FileWriter(file));
	Scanner finals = new Scanner(tempO);	

	while(finals.hasNext()){
		finalwrite.write(finals.nextLine() + "\n");
	}
	tempO.delete();
	finals.close();
	finalwrite.close();
}
	catch(Exception e){
		e.printStackTrace();
}
}		
}
