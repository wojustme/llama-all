package com.wojustme.llama.util;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @author xurenhe
 * @date 2018/2/4
 */
public final class HttpFileUtils {

    public static String uploadFile(String destUrl, Map<String, String> textParts, File filePart) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String result = null;
        try {
            HttpPost httpPost = new HttpPost(destUrl);


            MultipartEntityBuilder mEntityBuilder = MultipartEntityBuilder.create();
            if (MapUtils.isNotEmpty(textParts))  {
                for (Map.Entry<String, String> textPart : textParts.entrySet()) {
                    String key = textPart.getKey();
                    String value = textPart.getValue();
                    if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(value)) {
                        mEntityBuilder.addPart(key, new StringBody(value, ContentType.MULTIPART_FORM_DATA));
                    }
                }
            }
            mEntityBuilder.addBinaryBody("file", filePart);
            httpPost.setEntity(mEntityBuilder.build());
            response = httpclient.execute(httpPost);

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity resEntity = response.getEntity();
                result = EntityUtils.toString(resEntity);
                // 消耗掉response
                EntityUtils.consume(resEntity);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            HttpClientUtils.closeQuietly(httpclient);
            HttpClientUtils.closeQuietly(response);
        }
        return result;
    }
}
