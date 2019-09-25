COMPX301-19A — Assignment One
External sort merge
Due: 30 March 2019 — 11pm
Description: Implement in Java an external sort merge. The assignment should be done in pairs. There are two degrees of difficulty to choose from. The B Solution asks for a balanced 2-way sort merge with fixed length initial runs. The A Solution asks for a polyphase sort merge with variable length runs. Both programs are to sort lines of text into ascending order based upon the judgment the Java String compareTo() method. The sort should accept standard input and produce standard output (i.e. System.in and System.out), although temporary files can be used during the sort.

B Solution
Implement two java programs. The first accepts standard input and creates initial runs of a fixed length, using heapsort, and distributes them into two output files. The program should take one integer argument specifiying the maximum size of an initial run. The program must be called MakeRuns. The second program must be called SortMerge and it should perform a balanced two-way merge on the two output files created by MakeRuns. It should produce as standard output the final sorted data. The maximum grade possible for this solution is a B+.

A Solution
Implement two java programs. The first is called MakeRuns and accepts standard input and creates initial runs using the replacement selection strategy. These runs are written to a single temporary file. The program should accept a single integer argument specifying the size of (min)heap to use when making the runs, and it should report (i.e. print to standard out) how many runs were produced. The second program must be called PolyMerge and should accept a single integer argument specifying how many files to use for a polyphase sort merge. It should also obtain the number of runs produced by MakeRuns, then calculate a good distribution of the intial runs for the number of available files. It should then distribute the runs accordingly and execute a polyphase sort merge, producing as output the final sorted input. The maximum grade possible for this solution is an A+.

Both
Obviously a good sort will run quickly, but the critical factor here is the number of times the program moves each datum from one file to another. Whichever solution you implement, keep track of how many times any file is opened for output (starting after initial runs have been distributed to the temporary files) and report this count to standard error (i.e. System.err) after the final sorted output has been produced. Students are encouraged to share ideas and results (not code) through the general forum. Some large text files will be posted on Moodle for testing.

Submission requirements:
Create an empty directory whose name is the student IDs of both partners, separated by an underscore character. Place copies of your well-documented source code in the folder, along with an optional plain text README file if you wish to communicate anything to the marker (such as how to use your program, or any limitations, features, etc.). Do not include any other files. Compress the folder and submit it via Moodle. Make sure your names and student ID#s are included in the header documentation of each source code file.
Tony C. Smith, 8/03/2019



Running MakeRuns - takes a single int as input, the size of the heap to use
followed by < filename.txt as the text file to be read in 
outputs an int to the console, the number of runs produced.

Running PolyMerge
takes 2 parameters
1st -  number of tapes to use
2nd -  number of runs produced by makeruns

will automatically read in from the temp.txt file produced by makeruns
