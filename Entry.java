package test;

public class Entry{
  private String play;
  private String act;
  private String scene;
  private String character;
  private String line;
  
  public Entry(String play, String act, String scene, String character, String line) {
    this.play = play;
    this.act = act;
    this.scene = scene;
    this.character = character;
    this.line = line;
  }
  
  public String getPlay() {
    return this.play;
  }
  public String toString(){
	    return play + "<br>" + act + ", " + scene +"<br>"
	         + character + "<br>" + line; 
	  }
                   
}