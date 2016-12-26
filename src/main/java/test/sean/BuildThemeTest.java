package test.sean;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import com.seanModTest.templates.BuildBlockData;
import com.seanModTest.templates.BuildTheme;

public class BuildThemeTest {

	private final BuildTheme testTheme = new BuildTheme();
	private final String TEST1 = "test1";
	private final String TEST2 = "test2";
	private final String TEST3 = "d_test1";
	private final String TEST1_BLOCK = "stone";
	private final String TEST3_BLOCK = "brick";
	private final BuildBlockData TEST2_BUILD_DATA = new BuildBlockData("cobblestone");

	private final String DEFAULT_PRIMARY_BLOCK = "stonebrick";
	private final String DEFAULT_OTHER_BLOCK = "dirt";
	@Before
	public void setUp() throws Exception {
		testTheme.addBBData(TEST1, TEST1_BLOCK);
	}

	@Test
	public void testAddBBDataStringBuildBlockData() {
		assertTrue(testTheme.hasBBDataForType(TEST1));
		assertTrue(testTheme.addBBData(TEST2, TEST2_BUILD_DATA));
		assertTrue(testTheme.hasBBDataForType(TEST2));
		assertTrue(testTheme.addBBData(TEST2, TEST1_BLOCK)); //does permit multiple blocks under same tag
	}

	@Test
	public void testGetRandomBBData() {
		assertTrue(testTheme.getRandomBBData(new Random(), TEST1).getBlockName() == TEST1_BLOCK);
		assertTrue(testTheme.getRandomBBData(new Random(), "primary").getBlockName() == DEFAULT_PRIMARY_BLOCK);
		assertTrue(testTheme.getRandomBBData(new Random(), "nothin").getBlockName() == DEFAULT_OTHER_BLOCK);
	}

	@Test
	public void testGetFineOrDamagedBBD() {
		assertFalse(testTheme.hasDamagedVariant(TEST1));
		assertTrue(testTheme.addBBData(TEST3, TEST3_BLOCK)); //damaged test 1 block
		assertTrue(testTheme.addDamageChance(TEST1, 1.0F));
		assertTrue(testTheme.hasDamagedVariant(TEST1));
		
		BuildBlockData bbd = testTheme.getRandomBBData(new Random(), TEST1);
		assertTrue(testTheme.getFineOrDamagedBBD(new Random(), bbd, TEST1).getBlockName() == TEST3_BLOCK);

		assertTrue(testTheme.addDamageChance(TEST1, 0.0F));
		assertTrue(testTheme.getFineOrDamagedBBD(new Random(), bbd, TEST1).getBlockName() == TEST1_BLOCK);
		
		assertFalse(testTheme.addDamageChance("supah", 1.0F));
		assertTrue(testTheme.addDamageChance("primary", 1.0F));
		assertTrue(testTheme.hasDamagedVariant("primary"));
		bbd = testTheme.getRandomBBData(new Random(), "primary");
		assertTrue(testTheme.getFineOrDamagedBBD(new Random(), bbd, "primary").getMetaValue() == 2); //damaged variant
		
	}

}
