package gg.projecteden.parchment.sidebar;

import gg.projecteden.parchment.entity.EntityData;
import gg.projecteden.parchment.entity.EntityDataFragment;
import gg.projecteden.parchment.entity.EntityDataKey;
import org.bukkit.entity.Player;

import java.util.Objects;

public final class Sidebar extends EntityDataFragment<Player> {
    private static final EntityDataKey<Sidebar, Player> DATA_KEY = EntityData.createKey(Sidebar::new, Player.class);

    private final SidebarBuffer[] buffer = new SidebarBuffer[2];

    private final StageImpl stage = new StageImpl();
    private final Runnable layoutListener;

    private SidebarLayout layout;

    private boolean visible;
    private int back;

    public static Sidebar get(Player player) {
        Objects.requireNonNull(player);

        return player.getStoredEntityData().get(Sidebar.DATA_KEY);
    }

    private Sidebar() {
        this.buffer[0] = SidebarBufferUtilSpec.IMPL_KEY.get().create("_sidebar_l");
        this.buffer[1] = SidebarBufferUtilSpec.IMPL_KEY.get().create("_sidebar_r");

        this.layoutListener = () -> {
            this.layout.update(this.stage);
            this.flush();
        };

        this.setPersistent(false);
    }

    public void applyLayout(SidebarLayout layout) {
        if (this.layout != null) {
            this.layout.unsubscribe(this.layoutListener);
            this.buffer[this.back].clear();
        }

        this.layout = layout;

        if (layout == null) {
            this.hide();
        } else {
            layout.setup(this.stage);
            this.flush();

            layout.subscribe(this.layoutListener);
        }
    }

    private void setTitle(String title) {
        this.buffer[this.back].setTitle(title);
    }

    private void setLine(int idx, String value, String display) {
        if (value == null) {
            this.buffer[this.back].clearLine(idx);
        } else {
            this.buffer[this.back].setLine(idx, value, display);
        }
    }

    private void flush() {
        SidebarBuffer front = this.buffer[this.back];
        SidebarBuffer back = this.buffer[this.back ^ 1];
        boolean shouldShow = !this.visible;

        if (front.hasDiverged(back)) {
            front.pushChanges();

            back.sync(front);
            this.back ^= 1;

            shouldShow = true;
        }

        if (shouldShow) {
            front.setActive();
            this.visible = true;
        }
    }

    private void hide() {
        SidebarBufferUtilSpec.IMPL_KEY.get().hideSidebar(this.getOwner());
        this.visible = false;
    }

    @Override
    protected void onOwnerChange() {
        this.buffer[0].setOwner(this.getOwner());
        this.buffer[1].setOwner(this.getOwner());

        if (this.visible) {
            this.buffer[this.back ^ 1].setActive();
        }
    }

    private final class StageImpl implements SidebarStage {
        @Override
        public void setTitle(String title) {
            Sidebar.this.setTitle(title);
        }

        @Override
        public void setLine(int line, String value) {
            this.setLine(line, value, null);
        }

        @Override
        public void setLine(int line, String value, String display) {
            Sidebar.this.setLine(line, value, display);
        }

    }
}