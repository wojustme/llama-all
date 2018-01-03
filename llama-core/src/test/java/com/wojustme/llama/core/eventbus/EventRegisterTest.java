package com.wojustme.llama.core.eventbus;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.junit.Test;

/**
 * @author xurenhe
 * @date 2018/1/3
 */
public class EventRegisterTest {

    @Test
    public void test_Event() throws Exception {
        EventBus.getDefault().register(this);
        EventBus.getDefault().post(new MessageEvent("hello"));
    }

    @Subscribe
    public void onEvent(MessageEvent event) {
        String msg = "消息：" + event.message;
        System.out.println(msg);
    }

    @Subscribe
    public void onEvent2(MessageEvent event) {
        String msg = "o消息：" + event.message;
        System.out.println(msg);
    }
}
