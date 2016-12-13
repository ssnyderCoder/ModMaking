package com.sean.utility;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* Stores data under tagged words.  Data can be retrieved by name or by what tags it falls under*/
public class TaggedDataProvider<T> {
	private static final String ALL = "all";

	private Map<String, Map<String, TaggedData<T>>> allDataByTags;
	public TaggedDataProvider() {
		allDataByTags = new HashMap<String, Map<String, TaggedData<T>>>();
		allDataByTags.put(ALL, new HashMap<String, TaggedData<T>>());
	}
	
	public TaggedData getDataByID(String id){
		return allDataByTags.get(ALL).get(id);
	}
	
	//can function with and/or/not (Eg. "Dog&Blue|Cat&!Green")
	public Set<TaggedData<T>> getDataByTags(String searchTags){
		Set<TaggedData<T>> newSet = new HashSet<TaggedData<T>>();
		
		//process | (OR) and & (AND) tags
		String[] orTags = searchTags.split("[|]"); //must include [] since | is metacharacter
		for(String tags: orTags){
			String[] andTags = tags.split("&");
			Set<TaggedData<T>> andSet = null;
			for(String singleTag: andTags){
				if(andSet == null){
					andSet = new HashSet<TaggedData<T>>();
					andSet.addAll(processTag(singleTag));
				}
				else{
					andSet.retainAll(processTag(singleTag));
				}
			} //each AND tag processed
			newSet.addAll(andSet);
		} //each OR tag processed
		return newSet;
	}

	private Collection<TaggedData<T>> processTag(String singleTag) {
		if(singleTag.startsWith("!")){ //process NOT tag
			Set<TaggedData<T>> notSet = new HashSet<TaggedData<T>>();
			notSet.addAll(allDataByTags.get(ALL).values());
			if(allDataByTags.containsKey(singleTag.substring(1)))
				notSet.removeAll(allDataByTags.get(singleTag.substring(1)).values());
			return notSet;
		}
		else if(allDataByTags.containsKey(singleTag))
			return allDataByTags.get(singleTag).values();
		else
			return new HashSet<TaggedData<T>>();
	}
	
	public boolean addTaggedData(TaggedData tg){
		Map<String, TaggedData<T>> allMap = allDataByTags.get(ALL);
		allMap.put(tg.getID(), tg);
		Iterator<String> tags = tg.getTags().iterator();
		while(tags.hasNext()){
			String tag = tags.next();
			Map<String, TaggedData<T>> tagMap = allDataByTags.get(tag);
			if(tagMap == null){
				tagMap = new HashMap<String, TaggedData<T>>();
				allDataByTags.put(tag, tagMap);
			}
			tagMap.put(tg.getID(), tg);
		}
		return true;
	}

}
