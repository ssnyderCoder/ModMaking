package com.sean.utility;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
/* Base class for data associated with "tag" words.*/
public class TaggedData<T> {
	private final HashSet<String> tags = new HashSet<String>();
	private final String id;
	private final T data;
	public TaggedData(String uniqueID, T theData, String[] theTags) {
		id = uniqueID;
		data = theData;
		for(String tag : theTags){
			tags.add(tag);
		}
	}
	
	public boolean addTag(String tag){
		return tags.add(tag);
	}
	
	public boolean hasTag(String tag){
		return tags.contains(tag);
	}
	
	public Set<String> getTags(){
		return tags;
	}
	
	public boolean equals(Object obj){
		return id.equals(obj);
	}
	
	public int hashCode(){
		return id.hashCode();
	}
	
	public String getID(){ return id;}
	
	public T getData(){ return data; }

}
