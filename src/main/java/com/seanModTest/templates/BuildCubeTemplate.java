package com.seanModTest.templates;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.sean.utility.TaggedData;
import com.sean.utility.TaggedDataProvider;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;

public class BuildCubeTemplate extends BuildCube {
	private final String requirements;

	public BuildCubeTemplate(TaggedDataProvider<BuildPlanTemplate> tagDataProvider, StructureBoundingBox theBounds, String tagRequirements) throws Exception {
		super(tagDataProvider, theBounds);
		requirements = tagRequirements;
	}

	@Override
	public void generate(World world, Random rand, int chunkX, int chunkZ, BlockPos startPos, BuildTheme theme, BuildModifiers modifiers) {
		//get all BuildPlanTemplates that meet tag requirements
		Set<TaggedData<BuildPlanTemplate>> templates = this.dataProvider.getDataByTags(requirements);
		List<BuildPlanTemplate> validTemplates = new ArrayList<BuildPlanTemplate>();
		/*search through all that meet size requirements or instead are
		 * Stretchable: Smaller size, but can be stretched along appropriate axises
		 * Repeatable: Smaller size that is factor of required size and that can be tiled along appropriate axises
		 * Rotatable: Can be rotated 90, 180, 270 degrees to fit size requirements
		 */
		for(TaggedData<BuildPlanTemplate> templateTG : templates){
			BuildPlanTemplate template = templateTG.getData();
			if(templateMatchesSizeRequirements(template)){
				validTemplates.add(template);
			}
		}
		validTemplates.get(rand.nextInt(validTemplates.size())).generateInChunk(world, rand, chunkX, chunkZ, startPos, theme, modifiers);
	}

	private boolean templateMatchesSizeRequirements(BuildPlanTemplate template) {
		return template.getxSize() == this.getBounds().getXSize() && template.getySize() == this.getBounds().getYSize() && template.getzSize() == this.getBounds().getZSize();
	}

}
