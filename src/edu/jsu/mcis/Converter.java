package edu.jsu.mcis;

import java.io.*;
import java.util.*;
import com.opencsv.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class Converter {
    
    /*
    
        Consider the following CSV data:
        
        "ID","Total","Assignment 1","Assignment 2","Exam 1"
        "111278","611","146","128","337"
        "111352","867","227","228","412"
        "111373","461","96","90","275"
        "111305","835","220","217","398"
        "111399","898","226","229","443"
        "111160","454","77","125","252"
        "111276","579","130","111","338"
        "111241","973","236","237","500"
        
        The corresponding JSON data would be similar to the following (tabs and
        other whitespace have been added for clarity).  Note the curly braces,
        square brackets, and double-quotes!  These indicate which values should
        be encoded as strings, and which values should be encoded as integers!
        
        {
            "colHeaders":["ID","Total","Assignment 1","Assignment 2","Exam 1"],
            "rows":["111278","111352","111373","111305","111399","111160",
            "111276","111241"],
            "data":[[611,146,128,337],
                    [867,227,228,412],
                    [461,96,90,275],
                    [835,220,217,398],
                    [898,226,229,443],
                    [454,77,125,252],
                    [579,130,111,338],
                    [973,236,237,500]
            ]
        }
    
        Your task for this program is to complete the two conversion methods in
        this class, "csvToJson()" and "jsonToCsv()", so that the CSV data shown
        above can be converted to JSON format, and vice-versa.  Both methods
        should return the converted data as strings, but the strings do not need
        to include the newlines and whitespace shown in the examples; again,
        this whitespace has been added only for clarity.
    
        NOTE: YOU SHOULD NOT WRITE ANY CODE WHICH MANUALLY COMPOSES THE OUTPUT
        STRINGS!!!  Leave ALL string conversion to the two data conversion
        libraries we have discussed, OpenCSV and json-simple.  See the "Data
        Exchange" lecture notes for more details, including example code.
    
    */
    
    @SuppressWarnings("unchecked")
    public static String csvToJson(String csvString) {
        
        String results = "";
        
        try {
            
            CSVReader reader = new CSVReader(new StringReader(csvString));
            List<String[]> full = reader.readAll();
            Iterator<String[]> iterator = full.iterator(); //Don't Use
            
            JSONObject jsonHolder = new JSONObject(); // Creating an JSONObject that I am naming jsonHolder
             
            ArrayList<String> columns = new ArrayList<>(); //Needs to be a String      
            ArrayList<String> rows = new ArrayList<>(); //Needs to be a String
            ArrayList<ArrayList<Integer>> data = new ArrayList<>(); //Needs to be an integer

            String[] colHeaders = full.get(0);
            
            for (String e: colHeaders) { //A for-each loop going through the columns
                columns.add(e);
            }
            for (int i = 1; i < full.size(); ++i) { // Reading the row
                String[] row = full.get(i); 
                rows.add(row[0]); //Adding rows to the Array
                ArrayList<Integer> dataList = new ArrayList<>();
                for (int j = 1; j < row.length; ++j) {                  
                    dataList.add(Integer.parseInt(row[j]));
                }
                data.add(new ArrayList(dataList)); //Add the data to the array list
            }

            jsonHolder.put("colHeaders", columns); //Setting Output for columns
            jsonHolder.put("rowHeaders", rows); //Setting Output for rows     (Can not use ".set")
            jsonHolder.put("data", data); //Setting Output for data
            

            results = jsonHolder.toJSONString(); //Setting results = to the JSONObject as a String.
            
            // Code Inserted
            
        }        
        catch(Exception e) { return e.toString(); }
        
        return results.trim();
        
    }
    
    public static String jsonToCsv(String jsonString) {
        
        String results = "";
        
        try {

            StringWriter stringWriter = new StringWriter();
            CSVWriter csvWriter = new CSVWriter(stringWriter, ',', '"', '\n');
            
            JSONParser parser = new JSONParser(); //Creating a JSONParser
            JSONObject jsonObject = (JSONObject)parser.parse(jsonString); //Creating a JSONObject
            
            JSONArray colHeaders = (JSONArray)jsonObject.get("colHeaders");
            JSONArray rowHeaders = (JSONArray)jsonObject.get("rowHeaders");
            JSONArray data = (JSONArray)jsonObject.get("data");                      

            String[] columnArray = new String[colHeaders.size()]; //Naming this new String Array and Setting its size
            String[] rowArray = new String[rowHeaders.size()]; //Naming this new String Array and Setting its size
            String[] dataArray = new String[data.size()]; //Naming this new String Array and Setting its size        

            for (int i = 0; i < colHeaders.size(); i++){ // Getting column headers.
                columnArray[i] = colHeaders.get(i).toString(); //Placing them in an array.
            }           
       
            csvWriter.writeNext(columnArray); // Outputing columns          

            for (int i = 0; i < rowHeaders.size(); i++){ // Getting the row headers and row data          
                rowArray[i] = rowHeaders.get(i).toString();                          
                dataArray[i] = data.get(i).toString();
            }
            for (int i = 0; i < dataArray.length; i++) {                                     
                JSONArray dataValues = (JSONArray)parser.parse(dataArray[i]); // Parsing row data
                String[] row = new String[dataValues.size() + 1];
                row[0] = rowArray[i];
                for (int j = 0; j < dataValues.size(); j++) {
                    row[j+1] = dataValues.get(j).toString();
                }
                csvWriter.writeNext(row);
            }
                results = stringWriter.toString(); //Setting results to a string
            // Code Inserted
            
        }
        
        catch(Exception e) { return e.toString(); }
        
        return results.trim();
        
    }

}