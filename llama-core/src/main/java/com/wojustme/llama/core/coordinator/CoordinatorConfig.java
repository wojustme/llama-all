package com.wojustme.llama.core.coordinator;

/**
 * @author xurenhe
 * @date 2018/1/30
 */
public class CoordinatorConfig {

    /**
     * 启动端口
     */
    private int httpServerPort;

    /**
     * 上传文件的根路径
     */
    private String uploadFileSavePath;

    public int getHttpServerPort() {
        return httpServerPort;
    }

    public void setHttpServerPort(int httpServerPort) {
        this.httpServerPort = httpServerPort;
    }

    public String getUploadFileSavePath() {
        return uploadFileSavePath;
    }

    public void setUploadFileSavePath(String uploadFileSavePath) {
        this.uploadFileSavePath = uploadFileSavePath;
    }
}
