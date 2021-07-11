package org.jmc.models;

import javax.annotation.Nonnull;

import org.jmc.BlockData;
import org.jmc.geom.Transform;
import org.jmc.geom.UV;
import org.jmc.threading.ChunkProcessor;
import org.jmc.threading.ThreadChunkDeligate;


/**
 * Model for piston extension block.
 */
public class PistonArm extends BlockModel
{

	@Nonnull
	private String[] getMtlSidesTop(BlockData data, int biome)
	{
		boolean sticky = data.state.get("type").equals("sticky");
		String[] abbrMtls = materials.get(data.state,biome);

		String[] mtlSides = new String[6];
		mtlSides[0] = sticky ? abbrMtls[1] : abbrMtls[0];
		mtlSides[1] = abbrMtls[2];
		mtlSides[2] = abbrMtls[2];
		mtlSides[3] = abbrMtls[2];
		mtlSides[4] = abbrMtls[2];
		mtlSides[5] = abbrMtls[0];
		return mtlSides;
	}

	@Nonnull
	private String[] getMtlSidesArm(BlockData data, int biome)
	{
		String[] abbrMtls = materials.get(data.state,biome);

		String[] mtlSides = new String[6];
		mtlSides[0] = abbrMtls[2];
		mtlSides[1] = abbrMtls[2];
		mtlSides[2] = abbrMtls[2];
		mtlSides[3] = abbrMtls[2];
		mtlSides[4] = abbrMtls[2];
		mtlSides[5] = abbrMtls[2];
		return mtlSides;
	}

	
	@Override
	public void addModel(ChunkProcessor obj, ThreadChunkDeligate chunks, int x, int y, int z, BlockData data, int biome)
	{
		String dir = data.state.get("facing");

		/*
		  The model is rendered facing up, then rotated
		*/
		Transform rotate = new Transform();
		Transform translate = new Transform();
		Transform rt;

		switch (dir)
		{
			case "down": rotate = Transform.rotation(180, 0, 0); break;
			case "north": rotate = Transform.rotation(-90, 0, 0); break;
			case "south": rotate = Transform.rotation(90, 0, 0); break;
			case "west": rotate = Transform.rotation(0, 0, 90); break;
			case "east": rotate = Transform.rotation(0, 0, -90); break;
		}
		translate = Transform.translation(x, y, z);		
		rt = translate.multiply(rotate);


		boolean[] drawSides;
		UV[] uvSide;
		UV[][] uvSides;

		// top
		uvSide = new UV[] { new UV(0,12/16f), new UV(1,12/16f), new UV(1,1), new UV(0,1) };
		uvSides = new UV[][] { null, uvSide, uvSide, uvSide, uvSide, null };
		addBox(obj,
				-0.5f, 0.25f, -0.5f,
				0.5f, 0.5f, 0.5f, 
				rt, 
				getMtlSidesTop(data,biome), 
				uvSides, 
				null);

		// arm (extends outside the block)
		drawSides = new boolean[] {false,true,true,true,true,false};
		uvSide = new UV[] { new UV(1,12/16f), new UV(1,1), new UV(0,1), new UV(0,12/16f) };
		uvSides = new UV[][] { null, uvSide, uvSide, uvSide, uvSide, null };
		addBox(obj,
				-0.125f, -0.75f, -0.125f,
				0.125f, 0.25f, 0.125f, 
				rt, 
				getMtlSidesArm(data,biome), 
				uvSides, 
				drawSides);
		
	}

}
