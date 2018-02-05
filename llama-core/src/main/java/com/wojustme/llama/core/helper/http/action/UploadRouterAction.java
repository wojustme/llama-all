package com.wojustme.llama.core.helper.http.action;

import com.wojustme.llama.core.coordinator.*;
import com.wojustme.llama.core.helper.http.HttpUtils;
import com.wojustme.llama.core.helper.http.bean.ResponseContentTypeEnum;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http.multipart.*;
import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * @author xurenhe
 * @date 2018/1/27
 */
public class UploadRouterAction extends RouterAction {

    private static final HttpDataFactory FACTORY = new DefaultHttpDataFactory(DefaultHttpDataFactory.MAXSIZE);

    static {
        DiskFileUpload.deleteOnExitTemporaryFile = true;
        DiskFileUpload.baseDirectory = null;
        DiskAttribute.deleteOnExitTemporaryFile = true;
        DiskAttribute.baseDirectory = null;
    }

    private HttpRequest httpRequest;

    private HttpPostRequestDecoder postDecoder;

    private CoordinatorData coordinatorData;

    private Map<String, String> uploadMsg;

    public UploadRouterAction(CoordinatorData coordinatorData, HttpRequest httpRequest) {
        this.coordinatorData = coordinatorData;
        this.httpRequest = httpRequest;
        this.postDecoder = new HttpPostRequestDecoder(FACTORY, httpRequest);
        this.uploadMsg = new HashMap<>();
    }

    @Override
    public void hanlderHttpContent(ChannelHandlerContext ctx, HttpContent httpContent) throws Exception {
        if (postDecoder != null) {
            postDecoder.offer(httpContent);
        }
        readHttpDataChunkByChunk();
        if (httpContent instanceof LastHttpContent) {
            sendUploadResponse(ctx);
            reset();
        }
    }

    private void reset() {
        httpRequest = null;
        if (postDecoder != null) {
            postDecoder.destroy();
            postDecoder = null;
        }
    }

    private void sendUploadResponse(ChannelHandlerContext ctx) throws Exception{
        String okMsg = "200 ok";
        // 构建回应的消息
        FullHttpResponse response = HttpUtils.buildResponse(okMsg, ResponseContentTypeEnum.TEXT, httpRequest);

        ctx.writeAndFlush(response);
        ctx.close();
    }

    private void readHttpDataChunkByChunk() {
        while (postDecoder.hasNext()) {
            InterfaceHttpData data = postDecoder.next();
            try {
                writeHttpData(data);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                data.release();
            }
        }
    }

    private void writeHttpData(InterfaceHttpData data) throws IOException {

        if (data.getHttpDataType() == InterfaceHttpData.HttpDataType.Attribute) {
            Attribute attribute = (Attribute) data;
            uploadMsg.put(attribute.getName(), attribute.getValue());
        }

        if (data.getHttpDataType() == InterfaceHttpData.HttpDataType.FileUpload) {
            FileUpload fileUpload = (FileUpload) data;
            if (fileUpload.isCompleted()) {
                String fileName = fileUpload.getFilename();
                File dest = new File(coordinatorData.getCoordinatorConfig().getUploadFileSavePath(), fileName);
                fileUpload.renameTo(dest);
                postDecoder.removeHttpDataFromClean(fileUpload);
                uploadMsg.put("jarFileName", fileName);
                EventBus.getDefault().post(new CoordinatorEventData(CoordinatorEvent.SUMBIT_TOPOLOGY, buildUploadTopologyEventData()));
            }

        }
    }

    private Map<String, String> buildUploadTopologyEventData() {
        Map<String, String> uploadEventData = new HashMap<>();
        // 运行主类
        String mainCls = uploadMsg.get("class");
        // topology名
        String topologyName = uploadMsg.get("topology");
        // jar文件包名
        String jarFileName = uploadMsg.get("jarFileName");

        uploadEventData.put("mainClass", mainCls);
        uploadEventData.put("topologyName", topologyName);
        uploadEventData.put("jarFileName", jarFileName);

        return uploadEventData;

    }
}
