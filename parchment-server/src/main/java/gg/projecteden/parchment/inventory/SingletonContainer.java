package gg.projecteden.parchment.inventory;

import com.google.common.base.Preconditions;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

/**
 * A container which holds only one item and returns similar values to that of
 * {@link net.minecraft.world.SimpleContainer SimpleContainer}, meaning it will raise
 * {@link IndexOutOfBoundsException IndexOutOfBoundsExceptions} and return empty item stacks
 * where applicable to mirror that class.
 */
public class SingletonContainer implements net.minecraft.world.Container {

    private @NotNull ItemStack item;
    private int maxStackSize = Container.MAX_STACK;

    public SingletonContainer() {
        this.item = ItemStack.EMPTY;
    }

    public SingletonContainer(@NotNull ItemStack item) {
        this.item = Preconditions.checkNotNull(item, "item");
    }

    public SingletonContainer(org.bukkit.inventory.@NotNull ItemStack item) {
        this.item = CraftItemStack.asNMSCopy(Preconditions.checkNotNull(item, "item"));
    }

    public SingletonContainer(@NotNull Material material) {
        this(new org.bukkit.inventory.ItemStack(Preconditions.checkNotNull(material, "material")));
    }

    private static void throwIndexException(int index) {
        throw new IndexOutOfBoundsException("Received slot " + index + ", expected 0");
    }

    @Override
    public int getContainerSize() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return item.isEmpty();
    }

    @Override
    public ItemStack getItem(int slot) {
        if (slot < 0) {
            throwIndexException(slot);
        }
        return slot == 0 ? item : ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        if (slot < 0) {
            throwIndexException(slot);
        }
        ItemStack itemStack = slot == 0 && !item.isEmpty() ? item.split(amount) : ItemStack.EMPTY;
        if (!itemStack.isEmpty()) {
            setChanged();
        }
        return itemStack;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        if (slot != 0) {
            throwIndexException(slot);
        }
        ItemStack oldItem = item;
        item = ItemStack.EMPTY;
        return oldItem.isEmpty() ? ItemStack.EMPTY : oldItem;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        if (slot != 0) {
            throwIndexException(slot);
        }
        item = stack;
    }

    @Override
    public int getMaxStackSize() {
        return maxStackSize;
    }

    @Override
    public void setMaxStackSize(int size) {
        maxStackSize = size;
    }

    @Override
    public void setChanged() {

    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public List<ItemStack> getContents() {
        return Collections.singletonList(item);
    }

    @Override
    public void onOpen(CraftHumanEntity who) {

    }

    @Override
    public void onClose(CraftHumanEntity who) {

    }

    @Override
    public List<HumanEntity> getViewers() {
        return Collections.emptyList();
    }

    @Override
    public InventoryHolder getOwner() {
        return null;
    }

    @Override
    public Location getLocation() {
        return null;
    }

    @Override
    public void clearContent() {
        item = ItemStack.EMPTY;
    }
}