package com.raoqian.topactivity.app_unlock_clock;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raoqian on 2019/4/14
 */

public class ScreenObserver {
    static List<ScreenOnListener> screenOnListeners = new ArrayList<>();

    public static void addScreenLIstener(ScreenOnListener listener) {
        if (!screenOnListeners.contains(listener)) {
            screenOnListeners.add(listener);
        }
    }

    public static boolean removeScreenLIstener(ScreenOnListener listener) {
        return screenOnListeners.remove(listener);
    }

    public static void screenChange(boolean open) {
        for (ScreenOnListener screenOnListener : screenOnListeners) {
            screenOnListener.onScreenOpen(open);
        }
    }

}
