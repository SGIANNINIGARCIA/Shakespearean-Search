package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;

public class SearchEngine{

	private HashMap<String, ArrayList<Entry>> entries;

	public SearchEngine(){
		entries = new HashMap<String, ArrayList<Entry>>(); 
		try { 
			//** readFile may throw an exception **//
			
			readFiles();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
  
	private void readFiles() throws IOException {   
		
		//** Stores a directory for reading **//

		File dir = new File("/home/codio/workspace/src/main/java/cms330/texts");    
		
		//** loops for each file in directory in the saved directory **//
		for (File file : dir.listFiles()) {    
			
			//** calls parser with current file as argument **//
			parser(file);                                     
		} 
	}
	private void parser(File file) throws IOException {

		Scanner in = new Scanner(new BufferedReader(new FileReader(file)));	  

		String currentLine = in.nextLine();
		
		//** saves first line in the file as the name of the play **//
		String playName = currentLine;                            
		String act = "";
		String scene = "";
		String character = "";
		String[] fields; 
		int i; 
		Entry e;
		
		//** saves the prologue. currently only for romeo and juliet **//
		
		while(!currentLine.contains("ACT")) {                     
			currentLine = in.nextLine();
			fields = currentLine.split(" ");
			for(i = 0; i < fields.length; i++) {
				if(fields[i].length() >= 4) {
					e = new Entry(playName, "PROLOGUE", "", "", currentLine);
					entryAdder(e, fields[i]);
				}
			}

		}
		
		//** reads each line per file **//
		while (in.hasNext()) {      
			
			//** splits line into words and stores them in array **//
			fields = currentLine.split(" ");   
			
			//** finds act **//
			if(fields[0].equals("ACT")){            
				act = currentLine;
				currentLine = in.nextLine();
			}
			
			//** finds scene **//
			else if (fields[0].equals("SCENE")){    
				
				//** sets current scene **//
				scene = setScene(fields, currentLine);                   
				currentLine = in.nextLine();      

			} else {
				fields = currentLine.split("");
				
				//** finds character **//
				if(!fields[0].contains("\t") && fields.length > 1 ) {  
					
					//** sets character **//
					character = setCharacter(fields, currentLine);                                  
				}
			}

			fields = currentLine.split(" ");
			
			//** finds and saves stage directions **//
			if(currentLine.contains("[") || currentLine.contains("]") ) {                          
				for(i = 0; i < fields.length; i++) {
					if(fields[i].length() >= 4) {
						e = new Entry(playName, act, scene, "STAGE DIRECTIONS", currentLine);
						entryAdder(e, fields[i]);
					}
				}
			} else {
				
				//** find and saves other lines **//
			for(i = 0; i < fields.length; i++) {                      
				if(fields[i].length() >= 4) {
					e = new Entry(playName, act, scene, character, currentLine);
					entryAdder(e, fields[i]);
				}
			}
		 }
			currentLine = in.nextLine();
		}  
		in.close();
	}

	private void entryAdder(Entry e, String key) {
		
		//** gets rid of extra characters for better querying **//

		key = key.replace(";", "").replace(":", "").replace(".", "").replace(",", "")
				.replace("?", "").replace("!", "").replace("[", "").replace("]", "")
      .replace("\t", "").replace("-", "").replace("'", ""); 
		
		key = key.toLowerCase();

		if(!entries.containsKey(key)) {
		
      ArrayList<Entry> n = new ArrayList<Entry>();
			n.add(e);
			entries.put(key, n);
		
    } else {
		
      ArrayList<Entry> n = 
					entries.get(key);
			n.add(e);
		  entries.put(key, n);
		
    }
	}
	

	private String setCharacter(String[] fields, String line) {	
		fields = line.split(" ");
	
    if(fields[0].indexOf("\t") == -1) {
			
      fields = line.replace("\t", " ").split(" ");
			return fields[0] + " " + fields[1];
		
    } else {
		
      fields = line.replace("\t", " ").split(" ");
		  return fields[0];
		
    }
	}
	
	private String setScene(String[] fields, String line) {
		fields = line.split("");
		fields = line.replace("\t", " ").split(" "); 
		
    return fields[0] + " " + fields[1];  
	}
  
  //** here for legacy **//
	private void searchBar(){
    System.out.println("Hello, welcome to 'To find or not to find' our Shakesperean search engine\n\n");
		System.out.println("Type 'He that dies pays all debts' if you wish to exit\n\n");
    System.out.println("Be aware. Our engine is not case sensitive, or cares about .,!-;?.");
    Scanner scnr = new Scanner(System.in);
		String query = "";

		while(true) {   

			System.out.print("Please type your query \n>");
			query = scnr.nextLine();
			ArrayList<Entry> e = entries.get(query.toLowerCase());

			if(query.contains("He that dies pays all debts")) {
				break;
			}
			if(e == null) {

				System.out.println("Couldnt find word, try another one");

			} else  {

				System.out.println("We have found " + e.size() + " instance/s of this word!\n");
				for(int j = 0; j < e.size(); j++) {
					System.out.println(e.get(j).toString() + "\n");
				}

			}
			
		} 
    System.out.println("\nFarewell! God knows when we shall meet again.\n");
		scnr.close();
	}
  
  public String searchBar(String query){
      
      String result = "";
			ArrayList<Entry> e = entries.get(query.toLowerCase());
     
      if(entries.get(query.toLowerCase()) == null){
        return "<p>Sorry,</p> <p> we couldn't find that word!</p>";
      }
				
      for(int j = 0; j < e.size(); j++) {
					result = result + "<p></p> <p>" + e.get(j).toString() + "</p>";
				}
      
      return result;

	}
  public String searchBar(String query, String text){
      
      String result = "";
			ArrayList<Entry> e = entries.get(query.toLowerCase());
    
      if(entries.get(query.toLowerCase()) == null){
        return "<p>Sorry,</p> <p> we couldn't find that word!</p>";
      }

			for(int j = 0; j < e.size(); j++) {
         if(text.equalsIgnoreCase(e.get(j).getPlay())) {
           result = result + "<p></p> <p>" + e.get(j).toString() + "</p>";
         }
			}     
      
    return result;
	}
  
}
