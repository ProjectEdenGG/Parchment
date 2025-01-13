package gg.projecteden.parchment.entity;

import org.bukkit.entity.Entity;

import java.util.function.Supplier;

public final class EntityDataKey<T extends EntityDataFragment<E>, E extends Entity> {
    private final Supplier<T> generator;
    private final int idx;

    final Class<E> ownerType;

    EntityDataKey(Class<E> ownerType, Supplier<T> generator, int idx) {
        this.generator = generator;
        this.idx = idx;

        this.ownerType = ownerType;
    }

    Supplier<T> getGenerator() {
        return this.generator;
    }

    int getIdx() {
        return this.idx;
    }
}