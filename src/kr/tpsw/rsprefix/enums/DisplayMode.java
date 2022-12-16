package kr.tpsw.rsprefix.enums;

import org.jetbrains.annotations.NotNull;

import java.security.InvalidParameterException;

public enum DisplayMode {
    DEFAULT(1), PREFIX(2), SUFFIX(3);

    private final int index;

    DisplayMode(int index) {
        this.index = index;
    }

    @NotNull
    public static DisplayMode valueOf(int index) {
        if (index <= 0 || index > 3) {
            throw new InvalidParameterException(String.format("index is too small or high, index: %d", index));
        }
        for (DisplayMode mode : DisplayMode.values()) {
            if (mode.index == index) {
                return mode;
            }
        }
        throw new RuntimeException("Can't reachable code line");
    }

    public int getIndex() {
        return index;
    }

}
