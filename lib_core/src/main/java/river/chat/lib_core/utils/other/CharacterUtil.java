package river.chat.lib_core.utils.other;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.io.StringWriter;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;

/**
 * ================================================
 * Copyright (c) 2021 All rights reserved
 * 描述：处理字符串的工具类
 * Author: Scott
 * ================================================
 */
public class CharacterUtil {
    /**
     * json 格式化
     *
     * @param json json
     *
     * @return String
     */
    public static String jsonFormat(String json) {
        if (json == null || json.isEmpty()) {
            return "Empty/Null json content";
        }
        String message;
        try {
            json = json.trim();
            if (json.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(json);
                message = jsonObject.toString(4);
            } else if (json.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(json);
                message = jsonArray.toString(4);
            } else {
                message = json;
            }
        } catch (JSONException e) {
            message = json;
        }
        return message;
    }

    /**
     * xml 格式化
     */
    public static String xmlFormat(String xml) {
        if (xml == null || xml.isEmpty()) {
            return "Empty/Null xml content";
        }
        String message;
        try {
            Source xmlInput = new StreamSource(new StringReader(xml));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance()
                    .newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(xmlInput, xmlOutput);
            message = xmlOutput.getWriter()
                    .toString()
                    .replaceFirst(">", ">\n");
        } catch (TransformerException e) {
            message = xml;
        }
        return message;
    }

    /**
     * 解析请求服务器的请求参数
     */
    public static String parseParams(Request request) {
        try {
            RequestBody body = request.newBuilder()
                    .build()
                    .body();
            if (body == null) {
                return "";
            }
            Buffer requestbuffer = new Buffer();
            body.writeTo(requestbuffer);
            Charset charset = StandardCharsets.UTF_8;
            MediaType contentType = body.contentType();
            if (contentType != null) {
                charset = contentType.charset(charset);
            }
            assert charset != null;
            return CharacterUtil.jsonFormat(URLDecoder.decode(requestbuffer.readString(charset),
                                                              convertCharset(charset)));
        } catch (Throwable e) {
            e.printStackTrace();
            return "{\"error\": \"" + e.getMessage() + "\"}";
        }
    }

    /**
     * 是否可以解析
     */
    public static boolean isParseAble(MediaType mediaType) {
        return isText(mediaType) || isPlain(mediaType)
                || isJson(mediaType) || isForm(mediaType)
                || isHtml(mediaType) || isXml(mediaType);
    }

    public static boolean isText(MediaType mediaType) {
        if (mediaType == null) {
            return false;
        }
        return mediaType.type()
                .equals("text");
    }

    public static boolean isPlain(MediaType mediaType) {
        if (mediaType == null) {
            return false;
        }
        return mediaType.subtype()
                .toLowerCase()
                .contains("plain");
    }

    public static boolean isJson(MediaType mediaType) {
        if (mediaType == null) {
            return false;
        }
        return mediaType.subtype()
                .toLowerCase()
                .contains("json");
    }

    public static boolean isXml(MediaType mediaType) {
        if (mediaType == null) {
            return false;
        }
        return mediaType.subtype()
                .toLowerCase()
                .contains("xml");
    }

    public static boolean isHtml(MediaType mediaType) {
        if (mediaType == null) {
            return false;
        }
        return mediaType.subtype()
                .toLowerCase()
                .contains("html");
    }

    public static boolean isForm(MediaType mediaType) {
        if (mediaType == null) {
            return false;
        }
        return mediaType.subtype()
                .toLowerCase()
                .contains("x-www-form-urlencoded");
    }

    public static String convertCharset(Charset charset) {
        String s = charset.toString();
        int i = s.indexOf("[");
        if (i == -1) {
            return s;
        }
        return s.substring(i + 1, s.length() - 1);
    }

}
