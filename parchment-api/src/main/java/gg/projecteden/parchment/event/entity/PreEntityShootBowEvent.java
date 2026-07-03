package gg.projecteden.parchment.event.entity;

import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class PreEntityShootBowEvent extends EntityEvent {
    private static final HandlerList handlers = new HandlerList();

    boolean relative = true;

    public PreEntityShootBowEvent(@NotNull Entity entity) {
        super(entity);
    }

    public boolean isRelative() {
        return this.relative;
    }

    public void setRelative(boolean relative) {
        this.relative = relative;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return handlers;
    }

}