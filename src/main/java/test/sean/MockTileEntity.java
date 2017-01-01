package test.sean;

import net.minecraft.tileentity.TileEntity;

public class MockTileEntity extends TileEntity {

	private final String jsonText;
	public MockTileEntity(String JSON) {
		jsonText = JSON;
	}
	public String getJsonText() {
		return jsonText;
	}

}
