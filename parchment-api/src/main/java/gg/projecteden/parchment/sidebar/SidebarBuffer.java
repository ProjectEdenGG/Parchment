package gg.projecteden.parchment.sidebar;

import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Objects;

public abstract class SidebarBuffer {

    @SuppressWarnings("StringOperationCanBeSimplified")
    private static final String AUTO_SPACE = new String();
    protected final String name;
    protected final int size;
    protected final String[] liveLines;
    protected final String[] stagedLines;
    protected final String[] liveDisplays;
    protected final String[] stagedDisplays;

    protected String liveTitle = "";
    protected String stagedTitle = "";

    protected SidebarBuffer(String name, int size) {
        this.name = name;
        this.size = size;
        this.liveLines = new String[size];
        this.stagedLines = new String[size];
        this.liveDisplays = new String[size];
        this.stagedDisplays = new String[size];
    }

    protected abstract void setActive();

    protected abstract void pushChanges();

    protected abstract void setOwner(Player player);

    protected abstract boolean checkTitle(String title);

    protected abstract boolean checkLine(String line);

    void setTitle(String title) {
        this.stagedTitle = Objects.requireNonNullElse(title, "");
    }

    void setLine(int line, String value, String display) {
        Objects.requireNonNull(value);
        if (line < 0 || line > this.size - 1) {
            throw new IndexOutOfBoundsException();
        }

        this.stagedLines[line] = value;
        this.stagedDisplays[line] = display;

        for (int i = line - 1; i >= 0; i--) {
            if (this.stagedLines[i] == null) {
                this.stagedLines[i] = SidebarBuffer.AUTO_SPACE;
            } else {
                break;
            }
        }
    }

    void clearLine(int line) {
        if (line < 0 || line > this.size - 1) {
            throw new IndexOutOfBoundsException();
        }

        this.stagedLines[line] = SidebarBuffer.AUTO_SPACE;

        if (line + 1 == this.size || this.stagedLines[line + 1] == null) {
            for (int i = line; i >= 0; i--) {
                if (this.stagedLines[i] == SidebarBuffer.AUTO_SPACE) {
                   this.stagedLines[i] = null;
                } else {
                   break;
                }
            }
        }
    }

    void sync(SidebarBuffer live) {
        this.stagedTitle = live.liveTitle;
        System.arraycopy(live.liveLines, 0, this.stagedLines, 0, this.size);
        System.arraycopy(live.liveDisplays, 0, this.stagedDisplays, 0, this.size);
    }

    void clear() {
        this.stagedTitle = "";
        Arrays.fill(this.stagedLines, null);
        Arrays.fill(this.stagedDisplays, null);
    }

    boolean hasDiverged(SidebarBuffer live) {
        boolean out = !Objects.equals(this.stagedTitle, live.liveTitle);
        out = out || !Arrays.equals(this.stagedLines, live.liveLines);
        out = out || !Arrays.equals(this.stagedDisplays, live.liveDisplays);

        return out;
    }
}