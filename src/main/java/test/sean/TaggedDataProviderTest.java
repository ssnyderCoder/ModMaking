package test.sean;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.sean.utility.TaggedData;
import com.sean.utility.TaggedDataProvider;

public class TaggedDataProviderTest {
	private TaggedDataProvider<String> dataProv = new TaggedDataProvider<String>();
	private final String datum1 = "Orange";
	private final String datum2 = "Banana";
	private final String datum3 = "Apple";
	private final String id1 = "ofruit";
	private final String id2 = "bfruit";
	private final String id3 = "afruit";
	private final String[] tags1 = {"color_orange", "fruit", "round"};
	private final String[] tags2 = {"color_yellow", "fruit", "pointy"};
	private final String[] tags3 = {"color_red", "fruit", "round"};

	@Before
	public void setup() throws Exception{
		dataProv.addTaggedData(new TaggedData<String>(id1, datum1, tags1));
		dataProv.addTaggedData(new TaggedData<String>(id2, datum2, tags2));
	}
	
	@Test
	public void testGetDataByID() {
		TaggedData tg1 = dataProv.getDataByID(id1);
		TaggedData tg2 = dataProv.getDataByID("bfr".concat("uit"));
		assertNotNull(tg1);
		assertNotNull(tg2);
	}

	@Test
	public void testGetDataByTags() {
		dataProv.addTaggedData(new TaggedData<String>(id3, datum3, tags3));
		
		Set<TaggedData<String>> testSet;
		testSet = dataProv.getDataByTags("fruit");
		assertTrue(testSet.size() == 3);

		testSet = dataProv.getDataByTags("fruit&color_yellow");
		assertTrue(testSet.size() == 1);

		testSet = dataProv.getDataByTags("fruit&!color_red");
		assertTrue(testSet.size() == 2);

		testSet = dataProv.getDataByTags("color_orange|color_yellow");
		assertTrue(testSet.size() == 2);

		testSet = dataProv.getDataByTags("round&color_orange|pointy");
		assertTrue(testSet.size() == 2);

		testSet = dataProv.getDataByTags("color_green");
		assertTrue(testSet.size() == 0);
		
		try{
			testSet = null;
			testSet = dataProv.getDataByTags(null);
			assertTrue(testSet.size() == 0);
		}catch(Exception e){
			assertNull(testSet);
		}
	}

	@Test
	public void testAddTaggedData() {
		dataProv.addTaggedData(new TaggedData<String>(id3, datum3, tags3));
		TaggedData tg = dataProv.getDataByID(id3);
		assertNotNull(tg);
		tg = dataProv.getDataByID("kfruit");
		assertNull(tg);
	}

}
