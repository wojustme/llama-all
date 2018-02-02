package com.wojustme.llama.core.coordinator;

import org.greenrobot.eventbus.Subscribe;

/**
 * @author xurenhe
 * @date 18/1/30
 */
public class CoordinatorEventHandler {

    @Subscribe
    private void recvEvent(String msg) {
        System.out.println(msg);
    }

}
