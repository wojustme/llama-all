package com.wojustme.llama.core.coordinator;

import org.greenrobot.eventbus.Subscribe;

/**
 * @author xurenhe
 * @date 18/1/30
 */
public class CoordinatorEventDispatch {

    private static CoordinatorEventDispatch instance;

    private CoordinatorEventDispatch() {
    }

    public static CoordinatorEventDispatch getInstance() {
        if (instance == null) {
            synchronized (CoordinatorEventDispatch.class) {
                if (instance == null) {
                    instance = new CoordinatorEventDispatch();
                }
            }
        }
        return instance;
    }

    @Subscribe
    public void recvEvent(CoordinatorEventData coordinatorEventData) {
        System.out.println(" --> " + coordinatorEventData.getMsg());
    }

}
