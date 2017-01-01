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
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BuildCubeConcreteTest {

	private static final int CHUNK_X = 0;
	private static final int CHUNK_Z = 0;
	private static final BlockPos START_POS = new BlockPos(0,0,0);
	private static final Random RAND = new Random(55);
	private static final BuildTheme THEME = new BuildTheme();
	private final BuildModifiers modifiers = new BuildModifiers();
	private final StructureBoundingBox bounds = new StructureBoundingBox(0,0,0,10,10,10);
	private final TaggedDataProvider<BuildPlanTemplate> unusedTDP = new TaggedDataProvider<BuildPlanTemplate>();
	private final BuildCubeConcrete cube;
	private BuildBlockData block1 = new BuildBlockData("gravel");
	private BuildBlockData block2 = new BuildBlockData("sand");
	private BuildBlockData tileBlock = new BuildBlockData("chest", 2, "{Lock:Test}");
	private MockWorld mWorld = new MockWorld();
	
	public BuildCubeConcreteTest() throws Exception{
		cube = new TestableBuildCubeConcrete(unusedTDP, bounds);
	}
	@Before
	public void setUp() throws Exception {
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
		
		cube.generate(mWorld, RAND, CHUNK_X, CHUNK_Z, START_POS, THEME, modifiers);

		assertTrue(mWorld.getBlockData(START_POS).getBlockName() == cube.getBlockData(0, 0, 0).getBlockName());
		assertTrue(mWorld.getBlockData(START_POS.add(0, 1, 0)).getBlockName() == cube.getBlockData(0, 1, 0).getBlockName());
		assertTrue(mWorld.getBlockData(START_POS.add(0, 2, 0)).getBlockName() == cube.getBlockData(0, 2, 0).getBlockName());
		assertTrue(mWorld.getBlockData(START_POS.add(0, 3, 0)).getBlockName() == cube.getBlockData(0, 3, 0).getBlockName());
		assertTrue(mWorld.getBlockData(START_POS.add(0, 4, 0)) == null);
		//test tile entities too
	}
	
	@Test
	public void testGenerateMultipleChunks(){
		mWorld.clearBlocks();
		cube.clearAllBlocks();

		assertTrue(cube.setBlockData(0,0,0, block1));
		assertTrue(cube.setBlockData(1,1,0, block1));
		assertTrue(cube.setBlockData(2,2,0, block1));
		assertTrue(cube.setBlockData(3,3,0, block1));
		
		//only generates in active chunk
		cube.generate(mWorld, RAND, CHUNK_X, CHUNK_Z, START_POS.add(-2, 0, 0), THEME, modifiers);

		assertTrue(mWorld.getBlockData(START_POS.add(-2, 0, 0)) == null);
		assertTrue(mWorld.getBlockData(START_POS.add(-1, 1, 0)) == null);
		assertTrue(mWorld.getBlockData(START_POS.add(0, 2, 0)).getBlockName() == cube.getBlockData(2, 2, 0).getBlockName());
		assertTrue(mWorld.getBlockData(START_POS.add(1, 3, 0)).getBlockName() == cube.getBlockData(3, 3, 0).getBlockName());
		assertTrue(mWorld.getBlockData(START_POS.add(2, 4, 0)) == null);
		
		cube.generate(mWorld, RAND, CHUNK_X - 1, CHUNK_Z, START_POS.add(-2, 0, 0), THEME, modifiers);
		assertTrue(mWorld.getBlockData(START_POS.add(-2, 0, 0)).getBlockName() == cube.getBlockData(0, 0, 0).getBlockName());
		assertTrue(mWorld.getBlockData(START_POS.add(-1, 1, 0)).getBlockName() == cube.getBlockData(1, 1, 0).getBlockName());
	}
	
	@Test
	public void testGenerateRotate(){
		mWorld.clearBlocks();
		cube.clearAllBlocks();

		assertTrue(cube.setBlockData(0,0,0, block1));
		assertTrue(cube.setBlockData(1,0,0, block1));
		assertTrue(cube.setBlockData(2,0,0, block1));
		assertTrue(cube.setBlockData(3,0,0, block1));
		modifiers.setFacing(EnumFacing.EAST);
		cube.generate(mWorld, RAND, CHUNK_X, CHUNK_Z, START_POS, THEME, modifiers);
		assertTrue(mWorld.getBlockData(START_POS.add(0, 0, 0)).getBlockName() == cube.getBlockData(0, 0, 0).getBlockName());
		
		//if rotated, should not generate along north (default) axis
		assertTrue(mWorld.getBlockData(START_POS.add(1, 0, 0)) == null);
		assertTrue(mWorld.getBlockData(START_POS.add(2, 0, 0)) == null);
		assertTrue(mWorld.getBlockData(START_POS.add(3, 0, 0)) == null);
		
		//generates east by substituting x for -z and z for x
		assertTrue(mWorld.getBlockData(START_POS.add(0, 0, 1)).getBlockName() == cube.getBlockData(1, 0, 0).getBlockName());
		assertTrue(mWorld.getBlockData(START_POS.add(0, 0, 2)).getBlockName() == cube.getBlockData(2, 0, 0).getBlockName());
		assertTrue(mWorld.getBlockData(START_POS.add(0, 0, 3)).getBlockName() == cube.getBlockData(3, 0, 0).getBlockName());
		
		//generates west by substituting x for z and z for -x
		mWorld.clearBlocks();
		modifiers.setFacing(EnumFacing.WEST);
		cube.generate(mWorld, RAND, CHUNK_X, CHUNK_Z-1, START_POS, THEME, modifiers);
		assertTrue(mWorld.getBlockData(START_POS.add(0, 0, 0)) == null);
		assertTrue(mWorld.getBlockData(START_POS.add(0, 0, -1)).getBlockName() == cube.getBlockData(1, 0, 0).getBlockName());
		assertTrue(mWorld.getBlockData(START_POS.add(0, 0, -2)).getBlockName() == cube.getBlockData(2, 0, 0).getBlockName());
		assertTrue(mWorld.getBlockData(START_POS.add(0, 0, -3)).getBlockName() == cube.getBlockData(3, 0, 0).getBlockName());
		
		//generates south by substituting x for -x and z for -z
		mWorld.clearBlocks();
		modifiers.setFacing(EnumFacing.SOUTH);
		cube.generate(mWorld, RAND, CHUNK_X-1, CHUNK_Z, START_POS, THEME, modifiers);
		assertTrue(mWorld.getBlockData(START_POS.add(0, 0, 0)) == null);
		assertTrue(mWorld.getBlockData(START_POS.add(-1, 0, 0)).getBlockName() == cube.getBlockData(1, 0, 0).getBlockName());
		assertTrue(mWorld.getBlockData(START_POS.add(-2, 0, 0)).getBlockName() == cube.getBlockData(2, 0, 0).getBlockName());
		assertTrue(mWorld.getBlockData(START_POS.add(-3, 0, 0)).getBlockName() == cube.getBlockData(3, 0, 0).getBlockName());
		
		modifiers.setFacing(EnumFacing.NORTH);
	}
	
	@Test
	public void testGenerateTileEntity(){
		mWorld.clearBlocks();
		cube.clearAllBlocks();
	

		assertTrue(cube.setBlockData(0,0,0, tileBlock));
		cube.generate(mWorld, RAND, CHUNK_X, CHUNK_Z, START_POS, THEME, modifiers);
		//Can only create the actual TileEntities at runtime
		MockTileEntity chest = (MockTileEntity) mWorld.getTileEntity(START_POS);
		assertTrue(chest.getJsonText() == "{Lock:Test}");
		assertTrue(mWorld.getBlockData(START_POS).getBlockName() == cube.getBlockData(0, 0, 0).getBlockName());
		assertTrue(mWorld.getBlockData(START_POS).getMetaValue() == cube.getBlockData(0, 0, 0).getMetaValue());
	}
	
	@Test
	public void testGenerateTheme(){
	
	}

	@Test
	public void testGenerateStretch(){
	
	}
	
	@Test
	public void testGenerateRepeat(){
	
	}
}
