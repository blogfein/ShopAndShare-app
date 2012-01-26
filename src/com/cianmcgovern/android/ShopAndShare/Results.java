package com.cianmcgovern.android.ShopAndShare;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cianmcgovern.android.ShopAndShare.Comparison.Comparator;

import android.util.Log;

/**
 * Singleton object that stores the results from image analysing
 * 
 * @author Cian Mc Govern
 *
 */
/**
 * @author cian
 *
 */
public class Results {
	
	private static HashResults<String, Item> results;
	
	private static Results _instance;
	
	public static Results getInstance(){
		if(_instance==null){
			_instance = new Results();
			results = new HashResults<String,Item>();
		}
		return _instance;
	}
	
	private Results(){
	}
	
	public void addItem(String productName, Item product) {
	    results.put(productName, product);
	}
	
	/**
	 * Takes in an array containing the list of products and the length of the array
	 * 
	 * @param inProducts
	 * @param length
	 * 
	 */
	public void setProducts(String[] inProducts,int length){
		for(int i=0;i<length;i++){
			String line = inProducts[i].trim();
			if(line.contains(".") && line.length() > 4){
				String product = parseProduct(line);
				String price = parsePrice(line);
				if(product != null){
				    Item x = new Item(product,price);
				    results.put(product, x);
				}
			}
			else
				Log.v("ShopAndShare","Couldn't find decimal point in string");
		}
	}
	
	/**
	 * Parses the input string for a decimal number and returns it as a string
	 * @param x
	 * @return
	 */
	public String parsePrice(String input){

	    String re1=".*?";	// Non-greedy match on filler
	    String re2="([+-]?\\d*\\.\\d+)(?![-+0-9\\.])";	// Float 1

	    Pattern p = Pattern.compile(re1+re2,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	    Matcher m = p.matcher(input);
	    
	    if (m.find()){
	        String float1=m.group(1);
	        return float1.toString();
	    }
	    else
	    	return "0.00";
	}
	
	/**
	 * Parses the input string and returns all words in the string as a single string
	 * 
	 * @param input
	 * @return
	 */
	public String parseProduct(String input){
		
	    String re1="((?:[a-z][a-z]+))";

	    Pattern p = Pattern.compile(re1,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	    Matcher m = p.matcher(input);
	    
	    String result = null;
	    
	    while(m.find()){
	    	if(result==null)
	    		result = m.group();
	    	else
	    		result = result + " " + m.group();
	    }
	    
	    // Optimise result using Comparator
	    if(result != null) {
	        result = Comparator.findClosestString(result);
	        return result.trim();
	    }
	    
	    else
	        return result;
	}
	
	public void setHashResults(HashResults<String,Item> in){
		results=in;
	}
	
	public HashResults<String,Item> getProducts(){
		return results;
	}
	
	public void changeKey(String old, String snew){
		Item x = results.get(old);
		
		results.remove(old);
		results.put(snew, x);
	}
	
	public void saveCurrentResults() throws IOException{
		
		if(!new File(Constants.saveDir).isDirectory())
			new File(Constants.saveDir).mkdir();
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_hh:mm:ss");
		df.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		String filename = Constants.saveDir+"/"+df.format(new Date());
		Log.v("ShopAndShare", "Save file is: "+filename);
		
		File f = new File(filename);
		FileOutputStream fos = new FileOutputStream(f);
		ObjectOutputStream ofos = new ObjectOutputStream(fos);
		ofos.writeObject(results);
		ofos.close();
		fos.close();
	}
}


