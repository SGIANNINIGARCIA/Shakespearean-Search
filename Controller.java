package test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
public class Controller {
    SearchEngine se;

    // Constructor initializes class members
    public Controller() {

        this.se = new SearchEngine();
    }

    //Maps to base page
    @RequestMapping("/")
    public String index() {

        String indexHtml = null;

        try {
            byte[] bytes = Files.readAllBytes(Paths.get("html/index.html"));
            indexHtml = new String(bytes);
        } catch(Exception e) {
            e.printStackTrace();
        }

        return indexHtml;
    }
    
    //Mapping for the results of all texts
    @RequestMapping("/query")
    public String hello(@RequestParam(value="query", defaultValue="death") String query) {
         
        String result = se.searchBar(query);  
        return result;
    }
    
    //Mapping for the results for only Midsummer Nights Dream
    @RequestMapping("/midsummer")
    public String midsummer(@RequestParam(value="query", defaultValue="death") String query) {
        
        String result = se.searchBar(query, "A MIDSUMMER NIGHT'S DREAM");  
        return result;
    }
   
    //Mapping the results for only Romeo and Juliet
    @RequestMapping("/rj")
    public String rj(@RequestParam(value="query", defaultValue="death") String query) {
        
        String result = se.searchBar(query, "Romeo and Juliet");  
        return result;
    }
  
    //Mapping the results only for Macbeth
    @RequestMapping("/macbeth")
    public String macbeth(@RequestParam(value="query", defaultValue="death") String query) {
        
        String result = se.searchBar(query, "macbeth");  
        return result;
    }
  
      //Mapping for the results of all texts
    @RequestMapping("/back")
    public void back() {
       index();  
       
    }
  
      @RequestMapping("/help")
    public String help() {

        String helpHtml = null;

        try {
            byte[] bytes = Files.readAllBytes(Paths.get("html/help.html"));
            helpHtml = new String(bytes);
        } catch(Exception e) {
            e.printStackTrace();
        }

        return helpHtml;
    }
}