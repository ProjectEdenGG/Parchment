package gg.projecteden.parchment.entity;

import org.bukkit.entity.Entity;

import java.util.*;
import java.util.function.Supplier;

public final class EntityData {
    private static final Map<UUID, List<DataSlot>> PARKED = new HashMap<>();
    private static int DATA_IDX;

    private final List<EntityDataFragment<?>> data = new ArrayList<>();
    private final Class<?> ownerType;
    private Entity owner;

    EntityData(Class<?> ownerType) {
        this.ownerType = ownerType;
    }

    public static <T extends EntityDataFragment<E>, E extends Entity> EntityDataKey<T, E> createKey(
        Supplier<T> generator,
        Class<E> ownerType
    ) {
        return new EntityDataKey<>(ownerType, generator, EntityData.DATA_IDX);
    }

    public static EntityData create(Entity entity) {
        EntityData data = new EntityData(entity.getClass());

        List<DataSlot> slots = EntityData.PARKED.get(entity.getUniqueId());
        if (slots != null) {
            for (DataSlot slot : slots) {
                data.set(slot.idx, slot.data);
            }
        }

        data.setOwner(entity);

        return data;
    }

    public <T extends EntityDataFragment<E>, E extends Entity> T get(EntityDataKey<T, E> key) {
        while (this.data.size() <= key.getIdx()) {
            this.data.add(null);
        }

        T out = cast(this.data.get(key.getIdx()));
        if (out == null) {
            this.checkEntityType(key.ownerType);

            out = key.getGenerator().get();
            out.setOwner(cast(this.owner));

            this.data.set(key.getIdx(), out);
        }

        return out;
    }

    public <T extends EntityDataFragment<E>, E extends Entity> boolean clear(EntityDataKey<T, E> key) {
        if (this.data.size() <= key.getIdx()) {
            return false;
        }

        this.checkEntityType(key.ownerType);
        return this.data.set(key.getIdx(), null) != null;
    }

    public void orphan() {
        for (EntityDataFragment<?> frag : this.data) {
            if (frag != null) {
                frag.onOrphan();
            }
        }

        List<DataSlot> persist = new ArrayList<>();

        for (int i = this.data.size() - 1; i >= 0; i--) {
            EntityDataFragment<?> frag = this.data.get(i);
            if (frag != null && frag.isPersistent()) {
                persist.add(new DataSlot(frag, i));
            }
        }

        if (!persist.isEmpty()) {
            EntityData.PARKED.put(this.owner.getUniqueId(), persist);
        }
    }

    void set(int slot, EntityDataFragment<?> data) {
        while (this.data.size() <= slot) {
            this.data.add(null);
        }

        this.data.set(slot, data);
    }

    void setOwner(Entity entity) {
        Class<?> ownerType = entity.getClass();
        if (!ownerType.equals(this.ownerType)) {
            throw new IllegalArgumentException(String.format(
                "Wrong entity type. (entity=%s@%s, expect=%s@%s)",
                ownerType, ownerType.getClassLoader(),
                this.ownerType, this.ownerType.getClassLoader()
            ));
        }

        this.owner = entity;

        for (EntityDataFragment<?> frag : this.data) {
            if (frag != null) {
                frag.setOwner(cast(entity));
            }
        }
    }

    private void checkEntityType(Class<?> ownerType) {
        if (!ownerType.isAssignableFrom(this.ownerType)) {
            throw new IllegalArgumentException(String.format(
                "Incompatible entity types. (key=%s@%s, expect=%s@%s)",
                ownerType, ownerType.getClassLoader(),
                this.ownerType, this.ownerType.getClassLoader()
            ));
        }
    }

    private <S, T> T cast(S src) {
        return (T) src;
    }

    private static final class DataSlot {
        private final EntityDataFragment<?> data;
        private final int idx;

        private DataSlot(EntityDataFragment<?> data, int idx) {
            this.data = data;
            this.idx = idx;
        }
    }
}