package test.sean;

import com.sean.utility.TaggedDataProvider;
import com.seanModTest.templates.BuildBlockData;
import com.seanModTest.templates.BuildCubeConcrete;
import com.seanModTest.templates.BuildPlanTemplate;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;

public class TestableBuildCubeConcrete extends BuildCubeConcrete {

	//class created because blocks, items, etc are not registered until runtime
	public TestableBuildCubeConcrete(TaggedDataProvider<BuildPlanTemplate> tagDataProvider,
			StructureBoundingBox theBounds) throws Exception {
		super(tagDataProvider, theBounds);
	}

	@Override
	protected void setBlock(World world, BlockPos placePos, BuildBlockData block) {
		((MockWorld)world).setBlockData(placePos, block);
		TileEntity te = new MockTileEntity(block.getTileEntityJSON());
		((MockWorld)world).setTileEntity(placePos, te);
	}
}
