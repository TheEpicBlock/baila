package nl.theepicblock.baila;

import net.minecraft.block.BlockState;
import net.minecraft.text.Text;

public interface CachedBlockProvider {
	BlockState getCachedBlock();
	void setCachedBlock(BlockState v);
}
