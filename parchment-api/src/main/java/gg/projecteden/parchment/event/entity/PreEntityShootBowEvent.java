package gg.projecteden.parchment.event.entity;

import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class PreEntityShootBowEvent extends EntityEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private final @NotNull ItemStack bow;
    private final @NotNull ItemStack arrow;
    boolean relative = true;
    boolean cancelled = false;

    public PreEntityShootBowEvent(@NotNull Entity entity, @NotNull ItemStack bow, @NotNull ItemStack arrow) {
        super(entity);
        this.bow = bow;
        this.arrow = arrow;
    }

    public @NotNull ItemStack getBow() {
        return this.bow;
    }

    public @NotNull ItemStack getArrow() {
        return this.arrow;
    }

    public boolean isRelative() {
        return this.relative;
    }

    public void setRelative(boolean relative) {
        this.relative = relative;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
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