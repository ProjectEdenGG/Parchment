package gg.projecteden.parchment.event.block;

import org.bukkit.block.data.BlockData;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.Location;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class CustomBlockUpdateEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;

    private BlockData block;
    private UpdateType updateType;
    private Location location;

    public CustomBlockUpdateEvent(BlockData block, UpdateType updateType, Location location) {
        this.block = block;
        this.updateType = updateType;
        this.location = location;
    }

    public CustomBlockUpdateEvent(BlockData block, UpdateType updateType) {
        this.block = block;
        this.updateType = updateType;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    public BlockData getBlock() {
        return block;
    }

    public UpdateType getUpdateType() {
        return updateType;
    }

    public Location getLocation() {
        return location;
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

    public enum UpdateType {
        POWERED,
        SHAPE,
        INSTRUMENT,
        PITCH
    }

}