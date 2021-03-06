package eu.ydp.empiria.player.client.controller.workmode;

import java.util.EnumSet;

public enum PlayerWorkMode {
    FULL(new EmptyWorkModeSwitcher()) {
        @Override
        EnumSet<PlayerWorkMode> getAvailableTransitions() {
            return EnumSet.complementOf(EnumSet.of(TEST_SUBMITTED));
        }
    },
    PREVIEW(new PreviewWorkModeSwitcher()) {
        @Override
        EnumSet<PlayerWorkMode> getAvailableTransitions() {
            return EnumSet.noneOf(PlayerWorkMode.class);
        }
    },
    TEST(new TestWorkModeSwitcher()) {
        @Override
        EnumSet<PlayerWorkMode> getAvailableTransitions() {
            return EnumSet.of(PREVIEW, TEST_SUBMITTED);
        }
    },
    TEST_SUBMITTED(new TestSubmittedWorkModeSwitcher()) {
        @Override
        EnumSet<PlayerWorkMode> getAvailableTransitions() {
            return EnumSet.of(PREVIEW, TEST);
        }
    };

    private final WorkModeSwitcher workModeSwitcher;

    private PlayerWorkMode(WorkModeSwitcher workModeSwitcher) {
        this.workModeSwitcher = workModeSwitcher;
    }

    public WorkModeSwitcher getWorkModeSwitcher() {
        return workModeSwitcher;
    }

    public boolean canChangeModeTo(PlayerWorkMode newWorkMode) {
        return getAvailableTransitions().contains(newWorkMode);
    }

    abstract EnumSet<PlayerWorkMode> getAvailableTransitions();
}
