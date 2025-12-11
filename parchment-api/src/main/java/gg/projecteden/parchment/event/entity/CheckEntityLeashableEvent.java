package gg.projecteden.parchment.event.entity;

import io.papermc.paper.entity.Leashable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityEvent;
import org.jetbrains.annotations.NotNull;

public class CheckEntityLeashableEvent extends EntityEvent {
    private static final HandlerList handlers = new HandlerList();

    private boolean isLeashable;

    public CheckEntityLeashableEvent(@NotNull Entity entity, boolean isLeashable) {
        super(entity);
        this.isLeashable = isLeashable;
    }

    public boolean isLeashable() {
        return isLeashable;
    }

    public void setLeashable(boolean leashable) {
        isLeashable = leashable;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean callEvent() {
        Bukkit.getPluginManager().callEvent(this);
        return this.isLeashable();
    }
}
