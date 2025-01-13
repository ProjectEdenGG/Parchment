package gg.projecteden.parchment.event.block;

import org.bukkit.block.Block;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Called when a block drops resources in the world. The block will exist in the world at the time.
 * <p>
 * This event fires in between {@link org.bukkit.event.block.BlockBreakEvent BlockBreakEvent}
 * and {@link org.bukkit.event.block.BlockDropItemEvent BlockDropItemEvent}.
 */
public class BlockDropResourcesEvent extends BlockEvent {
    private static final HandlerList handlers = new HandlerList();
    private final @NotNull List<ItemStack> resources;

    public BlockDropResourcesEvent(@NotNull Block block, @NotNull List<ItemStack> resources) {
        super(block);
        this.resources = resources;
    }

    /**
     * Gets the resources being dropped by the block. This list is guaranteed to be mutable
     * and may be safely altered.
     * @return mutable list of items
     */
    public @NotNull List<ItemStack> getResources() {
        return resources;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return handlers;
    }
}