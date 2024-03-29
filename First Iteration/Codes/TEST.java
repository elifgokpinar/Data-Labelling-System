import java.util.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class TEST {

	   public static void main(String args[]) throws FileNotFoundException, IOException, ParseException, SecurityException {
		    Logger logger = Logger.getLogger("MyLog");
	        FileHandler fileHandler;
		   try {
	             
	            // This block configure the logger with handler and formatter
			    fileHandler = new FileHandler("tracing.log");
	            logger.addHandler(fileHandler);
	            //logger.setLevel(Level.ALL);
	            SimpleFormatter formatter = new SimpleFormatter();
	            fileHandler.setFormatter(formatter);
	       
	             
	        } catch (SecurityException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	     
		 
		   
		  ArrayList<LABEL>listOfLabel=new ArrayList<>();
		  ArrayList<INSTANCE>listOfInstance=new ArrayList<>();
	      JSONParser jsonParser = new JSONParser();
	      int maxLabel=0;
	      JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("CES3063F20_LabelingProject_Input-1.json"));
	      JSONObject jsonUser = (JSONObject) jsonParser.parse(new FileReader("configuration.json"));

	         //Parsing the contents of the JSON file
	         //Forming URL
	      
	         logger.info("Input file is read succesfully.\n");
	         logger.info("Dataset id: "+jsonObject.get("dataset id") + " is created");
	         logger.info("Dataset name: "+jsonObject.get("dataset name")+ " is created");
	         logger.info("maximum number of labels per instance: "+jsonObject.get("maximum number of labels per instance")+ " is created\n");
	         //Take the maximum labels for each instances
	         maxLabel = Integer.parseInt(jsonObject.get("maximum number of labels per instance").toString());
	      
	         
	         //Retrieving the array
	         JSONArray jsonArray = (JSONArray)(jsonObject.get("class labels"));
	         
	        
	         //Iterating the contents of the array
	      
	        
	         JSONArray jsonArray1 = (JSONArray)(jsonObject.get("instances"));
	       
	        
	         //Iterating the contents of the array
	    
	         JSONArray jsonArrayUser = (JSONArray)(jsonUser.get("users"));
	        	      
	      for (int i=0;i<jsonArray.size();i++) {
	    	  JSONObject a=(JSONObject) jsonArray.get(i);
		        long id =(long) a.get("label id");
		        String text=(String) a.get("label text");
		        LABEL a1=new LABEL(id,text);
		        listOfLabel.add(a1);
		        
	      }
    
	      for (int i=0;i<jsonArray1.size();i++) {
	    	  JSONObject a2=(JSONObject) jsonArray1.get(i);
		        long id1 =(long) a2.get("id");
		        String text2=(String) a2.get("instance");
		       INSTANCE a3=new INSTANCE(id1,text2);
		        listOfInstance.add(a3);
		        
	      }    
	      
	      //GENERATE A USER****************************************************************
		  ArrayList <USER> listOfUser =new ArrayList<USER>();
		 
	
	      int lengthUser=jsonArrayUser.size();
	      USER user;
	      String text2="";
	      String text3="";
	      for(int i=0; i<jsonArrayUser.size(); i++) {
	    	  JSONObject a2=(JSONObject) jsonArrayUser.get(i);
		        long id1 =(long) a2.get("user id");
		        text2=(String) a2.get("user name");
		        text3=(String) a2.get("user type");
		        user = new USER(id1,text2,text3);
	    	    listOfUser.add(user);
	    	    user.printUser(logger);
	      }
	      System.out.println();
          
	      
	      
	      //**********************RANDOM ASSIGNMENT************************************************
		  ArrayList <LABEL_ASSIGNMENT> listOfLabelAssignment =new ArrayList<LABEL_ASSIGNMENT>();
		  LABEL_ASSIGNMENT element ; 
  	      ArrayList<Integer> numberOfLabelIndex= new ArrayList<Integer>(); 
	  	  ArrayList<LABEL> storeOfLabel= new ArrayList<LABEL>(); 
	  	  ArrayList<Integer> numberOfUserIndex=new ArrayList<Integer>(); 

	      int sizeOfLabels=listOfLabel.size();
	      int numberOfUser=0;
	      int numberOfLabels=0;
	      int currentLabel=0;
	      int currentUser=0;
	      int controlIn=0;
	      int k=0; int i=0; int j=0;
	      for(i=0; i<listOfInstance.size(); i++) {
	  	  storeOfLabel= new ArrayList<LABEL>(); 
	  	      //Random value of max users
    	      numberOfUser= (int) (Math.random()* lengthUser)+1;

	    	  	  
	    	  
	    	  j=0;
	    	  currentUser=0;
	    	  while(j<numberOfUser) {
	    		controlIn=0;
	    		//The random value shows which label on the instance.
	    	    currentUser=(int) (Math.random()*(lengthUser));  
	    	    
	    	    //Check the label whether it exists.
	    	    if(numberOfUserIndex.size()!=0) {
	    	    	if(!numberOfUserIndex.contains(currentUser)) {
	    	    		numberOfUserIndex.add(currentUser);
	    	    		j++;
	    	    		controlIn=1;
	    	    	}
				   }
	    	    else {
    	    		numberOfUserIndex.add(currentUser);
    	    		j++;
    	    		controlIn=1;
	    	    }	
	    	    
	    	    if(controlIn==1) {
	    	           k=0;
	   	    	  currentLabel=0;
			   //The random value shows how many labels for current instance
	    	          numberOfLabels = (int) (Math.random()* maxLabel)+1;	    
	   	    	  while(k<numberOfLabels) {
	   	    		//The random value shows which label on the instance.
	   	    	    currentLabel=(int) (Math.random()*sizeOfLabels);  
	   	    	    //System.out.println(currentLabel);
	   	    	    //Check the label whether it exists.
	   	    	    if(numberOfLabelIndex.size()!=0) {
	   	    	    	if(!numberOfLabelIndex.contains(currentLabel+1)) {
	   	    	    		numberOfLabelIndex.add(currentLabel+1);
	   	    	    		storeOfLabel.add(listOfLabel.get(currentLabel));
	   	    	    		k++;
	   	    	    	}
	   				   }
	   	    	    else {
	       	    		numberOfLabelIndex.add(currentLabel+1);
	       	    		storeOfLabel.add(listOfLabel.get(currentLabel));
	       	    		k++;
	   	    	    }	            
	   	    	  } 
	    
	   	    	  element=new LABEL_ASSIGNMENT(listOfUser.get(currentUser).getUserId(),listOfInstance.get(i).getInstanceid(),storeOfLabel);
	   	          listOfLabelAssignment.add(element);
	   	          //print actions
	   	          element.sortLabels();
	   	          element.print(logger);
	   	     	  
                  storeOfLabel= new ArrayList<LABEL>(); 
	   	          numberOfLabelIndex.clear();
	    	    }
	    	  }
	    	  numberOfUserIndex.clear();
	      }
	     
	     Gson gson=new Gson();
	     String json2="";
	 
	    
	      try {
	         FileWriter file = new FileWriter("output.json");
	         //file.write(gsonInput.toJson(jsonObject));
	         file.write("{\n");
	         file.write("\"dataset id\":"+(jsonObject.get("dataset id").toString())+",\n"+"\"dataset name\":"
	    	         +(jsonObject.get("dataset name")).toString()+",\n" + "\"maximum number of labels per instance\":"
	        		 +(jsonObject.get("maximum number of labels per instance").toString()+",\n"));
	    	  
	         //Print labels to json file
	         file.write("\"class labels\":"+(jsonObject.get("class labels").toString())+" \n");
	         
	         
	         file.write("\"instances\":[\n");
	         //Print Instances to json file
	         for(int c=0;c<listOfInstance.size();c++) {
	    	     json2=gson.toJson(listOfInstance.get(c));
	    	     file.write(json2+",\n");
	         }
	         file.write("],\n");
	         
	         file.write("\"class label assignments\":[\n");
	         //Print Assignment to json file
	         for(int c=0;c<listOfLabelAssignment.size();c++) {

	    	     json2=gson.toJson(listOfLabelAssignment.get(c));
	    	     file.write(json2+",\n");
	         }
	         file.write("],\n");
	         
	         file.write("\"users" +":[\n");
	         
	         
	         //Print Users to json file
	         for(int c=0;c<listOfUser.size();c++) {

	    	     json2=gson.toJson(listOfUser.get(c));
	    	     file.write(json2+",\n");
	         }
	         file.write("]\n");
	         file.write("{\n");
	        
	         file.flush();
	         file.close();
	         logger.info("Output is written sucesfully.");
	      } catch (IOException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      }
	      //System.out.println();
	     // System.out.println("JSON file created: "+json2);
	   }
	   
}
