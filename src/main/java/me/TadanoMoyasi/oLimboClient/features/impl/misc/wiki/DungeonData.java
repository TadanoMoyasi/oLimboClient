package me.TadanoMoyasi.oLimboClient.features.impl.misc.wiki;

import net.minecraft.util.BlockPos;

public class DungeonData {
	public String name;
	public boolean inDungeon;
	public String level;
	public double x;
	public double y;
	public double z;
	public String note;
	public double radius;
	public String url;
	
	public BlockPos getBlockPos() {
		return new BlockPos(x, y, z);
	}
}
