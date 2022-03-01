package com.sharkey.rpc.util;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class OkHttpUtil {
    private static final Logger logger = LoggerFactory.getLogger(com.sharkey.TParty.okhttp.OkHttpUtil.class);

    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder().retryOnConnectionFailure(false)
            .connectionPool(new ConnectionPool(200, 5, TimeUnit.MINUTES))
            .connectTimeout(30,TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build();;

    /**
     *  Get
     * @param url
     * @param headers
     * @param queries
     * @return Response
     */
    public static String doGet(String url, Object headers ,Object queries){
        Request request = new Request.Builder().headers(getHeaders(headers)).url(getCurl(url, queries)).get().build();
        return doExecute(request);
    }

    /**
     *  Post
     * @param url
     * @param json
     * @param headers
     * @return Response
     */
    public static String doPost(String url, Object headers, Object json){
        Request request = new Request.Builder().headers(getHeaders(headers)).url(url).post(getBody(json)).build();
        return doExecute(request);
    }

    /**
     *  补全url
     * @param url
     * @param queries
     * @return String
     */
    private static String getCurl(String url, Object queries){
        StringBuffer curl = new StringBuffer(url);
        curl.append("?");
        if(queries != null){
            for(String query : (String[])queries){
                String[] ss = query.split(":");
                curl.append(ss[0].trim()).append("=").append(ss[1].trim()).append("?");
            }
        }
        return curl.toString();
    }

    /**
     *  Header  String[] -> Headers
     * @param headers
     * @return Headers
     */
    private static Headers getHeaders(Object headers){
        Headers.Builder builder = new Headers.Builder();
        if(headers != null){
            for(String header : (String[])headers){
                if(header != null){
                    builder.add(header);
                }
            }
        }
        return builder.build();
    }

    /**
     *  JSON String -> RequestBody
     * @param json
     * @return
     */
    private static RequestBody getBody(Object json){
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        if(json == null){
            return RequestBody.create(JSON, "");
        }
        return RequestBody.create(JSON, (String)json);
    }

    /**
     * 执行request
     * @param request
     * @return 返回Response
     */
    private static String doExecute(Request request){
        try (Response response = okHttpClient.newCall(request).execute()) {
            assert response.body() != null;
            return response.body().string();
        } catch (Exception e) {
            logger.error("okhttp execute error -> {}", e.getMessage());
            return e.getMessage();
        }
    }
}
