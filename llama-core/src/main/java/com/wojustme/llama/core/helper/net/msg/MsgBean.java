package com.wojustme.llama.core.helper.net.msg;

/**
 * @author xurenhe
 * @date 2017/12/29
 */
public class MsgBean {

    private MsgType msgType;

    private byte[] msgData;


    public MsgBean(MsgType msgType, byte[] msgData) {
        this.msgType = msgType;
        this.msgData = msgData;
    }

    public MsgType getMsgType() {
        return msgType;
    }

    public void setMsgType(MsgType msgType) {
        this.msgType = msgType;
    }

    public byte[] getMsgData() {
        return msgData;
    }

    public void setMsgData(byte[] msgData) {
        this.msgData = msgData;
    }
}
