package test.sean;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import com.sean.utility.TaggedDataProvider;
import com.seanModTest.templates.BuildBlockData;
import com.seanModTest.templates.BuildCubeConcrete;
import com.seanModTest.templates.BuildModifiers;
import com.seanModTest.templates.BuildPlanTemplate;
import com.seanModTest.templates.BuildTheme;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BuildCubeConcreteTest {

	private static final int CHUNK_X = 0;
	private static final int CHUNK_Z = 0;
	private static final BlockPos START_POS = new BlockPos(2,2,2);
	private static final Random RAND = new Random(55);
	private static final BuildTheme THEME = new BuildTheme();
	private static final BuildModifiers MODIFIERS = new BuildModifiers();
	private BuildCubeConcrete cube;
	private StructureBoundingBox bounds;
	private TaggedDataProvider<BuildPlanTemplate> unusedTDP = new TaggedDataProvider<BuildPlanTemplate>();
	private BuildBlockData block1 = new BuildBlockData("gravel");
	private BuildBlockData block2 = new BuildBlockData("sand");
	private MockWorld mWorld = new MockWorld();
	@Before
	public void setUp() throws Exception {
		bounds = new StructureBoundingBox(0,0,0,10,10,10); //bounds must start from 0,0,0
		cube = new TestableBuildCubeConcrete(unusedTDP, bounds);
	}

	@Test
	public void testBlockData() {
		cube.clearAllBlocks();
		//all tiles null by default
		assertTrue(cube.getBlockData(0, 0, 0) == null);
		
		//can re-assign same tile
		assertTrue(cube.setBlockData(0,0,0, block1));
		assertTrue(cube.getBlockData(0, 0, 0).getBlockName() == block1.getBlockName());
		assertTrue(cube.setBlockData(0,0,0, block2));
		assertTrue(cube.getBlockData(0, 0, 0).getBlockName() == block2.getBlockName());
		
		//cannot assign tile outside bounds of cube
		assertFalse(cube.setBlockData(-1, 0, 0, block2));
		assertFalse(cube.setBlockData(0, 0, 22, block2));
	}
	
	@Test
	public void testGenerateSimple(){
		mWorld.clearBlocks();
		cube.clearAllBlocks();

		assertTrue(cube.setBlockData(0,0,0, block1));
		assertTrue(cube.setBlockData(0,1,0, block1));
		assertTrue(cube.setBlockData(0,2,0, block1));
		assertTrue(cube.setBlockData(0,3,0, block1));
		
		cube.generate(mWorld, RAND, CHUNK_X, CHUNK_Z, START_POS, THEME, MODIFIERS);

		assertTrue(mWorld.getBlockData(START_POS).getBlockName() == cube.getBlockData(0, 0, 0).getBlockName());
		assertTrue(mWorld.getBlockData(START_POS.add(0, 1, 0)).getBlockName() == cube.getBlockData(0, 0, 0).getBlockName());
		assertTrue(mWorld.getBlockData(START_POS.add(0, 2, 0)).getBlockName() == cube.getBlockData(0, 0, 0).getBlockName());
		assertTrue(mWorld.getBlockData(START_POS.add(0, 3, 0)).getBlockName() == cube.getBlockData(0, 0, 0).getBlockName());
		assertTrue(mWorld.getBlockData(START_POS.add(0, 4, 0)) == null);
		//test tile entities too
	}
	
	@Test
	public void testGenerateModified(){
		//test rotate, stretch, 
	}

}
