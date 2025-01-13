package gg.projecteden.parchment.entity;

import org.bukkit.entity.Entity;

public abstract class EntityDataFragment<E extends Entity> {
    private E owner;
    private boolean persistent = true;

    protected EntityDataFragment() {
    }

    protected void onOwnerChange() {
    }

    protected void onOrphan() {
    }

    protected final E getOwner() {
        return this.owner;
    }

    protected final void setPersistent(boolean persistent) {
        this.persistent = persistent;
    }

    final boolean isPersistent() {
        return this.persistent;
    }

    final void setOwner(E entity) {
        this.owner = entity;
        this.onOwnerChange();
    }
}