package com.ltts.project.controller;

import java.io.IOException;
import java.time.LocalTime;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ltts.project.boatScheduler.BoatScheduler;
import com.ltts.project.boatService.BoatService;
import com.ltts.project.model.BoatEssentials;

@RestController
public class BoatController {

	@Autowired
	BoatService bs;

	@Autowired
	BoatScheduler bsh;


	HashMap<String, BoatEssentials> BoatList = new HashMap();
	String hullG;
	
	

	
	  @PostMapping("/boat") 
	    public String updateBoat(@RequestBody String vehicle) throws IOException
	    {
	    	Object file = JSONValue.parse(vehicle);
	        JSONObject jsonObjectdecode = (JSONObject)file;
	        String hin=(String)jsonObjectdecode.get("hid");
	        hullG=hin;
	        
	        
	    	System.out.println(hin);
	    	
	    	if(hin.isEmpty()) {
	    		return "please enter details";
	    	}
	    	else {
	    		
	    		System.out.println("in else");
	    		String hull =(String)jsonObjectdecode.get("hid");
	    		String log=(String)jsonObjectdecode.get("log");
	            String lat=(String)jsonObjectdecode.get("lat");
	            String speed =(String)jsonObjectdecode.get("speed");
	            String noOfEngines=(String)jsonObjectdecode.get("eng");
	            int noe = Integer.parseInt(noOfEngines);
	            String stime = (java.time.LocalTime.now()).toString();
	            String ck=(String)jsonObjectdecode.get("ck");
	            String dp =(String)jsonObjectdecode.get("od");
	            
	  
	            BoatList.put(hull, new BoatEssentials(hull,noe,"running",stime,speed,lat,log,ck,dp));
	 		   
	 		    bs.startboat(BoatList, hull);
			 
	 		    System.out.println(BoatList);
	  
	 		    bs.startboat(BoatList, hull);
	 
	 		    bs.postEnginedata(); bs.postEventdata(); bs.postLocationdata();
	  
			    return "Boat updated Sucessfully";
	  
	    	}
	  
}
	 
	
		  
		  @GetMapping("/stopboat")
		  public String stopBoat() throws IOException {
		  
			  bs.stopBoatDynamics();
			  BoatList.remove(hullG);
		  
			  return "Boat "+ hullG +" stopped Sucessfully"; 
		  	
		  }
		  
		  
		  @GetMapping("/ActiveBoat")
		  public String ActiveBoat() throws IOException {
			  
			 BoatEssentials bss = BoatList.get(hullG);
			 if(bss==null) {
				 
				 return "No Active Simulations";
				 
			 }
			 else {
				 
				 
			 }
			 //BoatEssentials bss = BoatList.get(hullG);
			 String hull = bss.getHull_Id();
			 int no = bss.getNoofEngines();
			 String sp= bss.getBoat_Speed();
			 String door = bss.getDoorSensor();
			 int n = bss.getNoofEngines();
			 String depth = bss.getOceanDepth();
			 
			 return "Boat Running hullid  "+hull+ ""
			 		+ " Speed =  "+sp +""
			 				+ " Number of Engines =  "+no+""
			 						+ " Ocean depth =  "+depth+""
					 						+ " door =  "+door ; 
		  
		  }
		  
					  
		  
		  
		  
		  
		  
		   @PostMapping("/startboat") 
		    public String postController(@RequestBody String vehicle)
		    {
		    	Object file = JSONValue.parse(vehicle);
		        JSONObject jsonObjectdecode = (JSONObject)file;
		        String hin=(String)jsonObjectdecode.get("hid");
		        hullG=hin;
		        
		        
		    	System.out.println(hin);
		    	
		    	if(hin.isEmpty()) {
		    		return "please enter details";
		    	}
		    	else {
		    		
		    		System.out.println("in else");
		    		
		    		String hull =(String)jsonObjectdecode.get("hid");
		    		
		    		String log=(String)jsonObjectdecode.get("log");
		           
		            String lat=(String)jsonObjectdecode.get("lat");
		            
		            String speed =(String)jsonObjectdecode.get("speed");
		            String noOfEngines=(String)jsonObjectdecode.get("eng");
		            String ck=(String)jsonObjectdecode.get("ck");
		            String dp =(String)jsonObjectdecode.get("od");
		            
		            
		            int noe = Integer.parseInt(noOfEngines);
		    		 //double speed = Double.parseDouble(speeds);
			      
		    		 String stime = (java.time.LocalTime.now()).toString();
		    		 
		            BoatList.put(hull, new BoatEssentials(hull,noe,"running",stime,speed,lat,log,ck,dp));
	   
		            bs.startboat(BoatList, hull);
		       
		            return "BoatStarted";
		    
		  
		  
		    	}
		    }
}	    

