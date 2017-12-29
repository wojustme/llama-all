package com.wojustme.llama.core.helper.net.msg;

/**
 * @author xurenhe
 * @date 2017/12/29
 */
public enum MsgType {

    /**
     * 默认
     */
    UNKNOWN(0),
    /**
     * 心跳，hello包
     */
    HEARTBEAT_HELLO(1),
    /**
     * 发送数据
     */
    SEND_DATA(2),
    /**
     * 接收成功
     */
    RECV_OK(3);

    public int val;

    MsgType(int val) {
        this.val = val;
    }


    public static MsgType getMsgType(int val) {
        switch (val) {
            case 0:
                return UNKNOWN;
            case 1:
                return HEARTBEAT_HELLO;
            case 2:
                return SEND_DATA;
            case 3:
                return RECV_OK;
            default:
                return UNKNOWN;
        }
    }
}
