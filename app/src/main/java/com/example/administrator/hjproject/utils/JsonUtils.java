package com.example.administrator.hjproject.utils;


import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.google.gson.JsonNull;
import com.google.gson.JsonSyntaxException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * JSON解析二次封装
 */
public class JsonUtils {

    // 采取单例模式
    private static Gson gson = new Gson();

    private JsonUtils() {
    }


    public static boolean isJson(String _s) {
        try {
            new JSONObject(_s);
            return true;
        } catch (JSONException _e) {
            _e.printStackTrace();
            return false;
        }
    }

    /**
     * @param src :将要被转化的对象
     * @return :转化后的JSON串
     * @MethodName : toJson
     * @Description : 将对象转为JSON串，此方法能够满足大部分需求
     */
    public static String toJson(Object src) {
        if (null == src) {
            return gson.toJson(JsonNull.INSTANCE);
        }
        try {
            return gson.toJson(src);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param json
     * @param classOfT
     * @return
     * @MethodName : fromJson
     * @Description : 用来将JSON串转为对象，但此方法不可用来转带泛型的集合
     */
    public static <T> Object fromJson(String json, Class<T> classOfT) {
        try {
            return gson.fromJson(json, (Type) classOfT);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param json
     * @param typeOfT
     * @return
     * @MethodName : fromJson
     * @Description : 用来将JSON串转为对象，此方法可用来转带泛型的集合，如：Type为 new
     * TypeToken<List<T>>(){}.getType()
     * ，其它类也可以用此方法调用，就是将List<T>替换为你想要转成的类
     */
    public static Object fromJson(String json, Type typeOfT) {
        try {
            return gson.fromJson(json, typeOfT);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取json中的某个值
     *
     * @param json
     * @param key
     * @return
     */
    public static String getValue(String json, String key) {
        try {
            JSONObject object = new JSONObject(json);
            return object.getString(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取json中的list值
     *
     * @param json
     * @return
     */
    public static String getListValue(String json) {
        try {
            JSONObject object = new JSONObject(json);
            return object.getString("name");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Double getDoubleValue(String json, String key) {
        try {
            JSONObject object = new JSONObject(json);
            return object.getDouble(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getIntValue(String json, String key) {
        try {
            JSONObject object = new JSONObject(json);
            return object.getInt(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * 把object转成Json
     *
     * @param obj
     * @return
     */
    public static String object2json(Object obj) {
        StringBuilder json = new StringBuilder();
        if (obj == null) {
            json.append("\"\"");
        } else if (obj instanceof String || obj instanceof Integer
                || obj instanceof Float || obj instanceof Boolean
                || obj instanceof Short || obj instanceof Double
                || obj instanceof Long || obj instanceof BigDecimal
                || obj instanceof BigInteger || obj instanceof Byte) {
            json.append("\"").append(string2json(obj.toString())).append("\"");
        } else if (obj instanceof Object[]) {
            json.append(array2json((Object[]) obj));
        } else if (obj instanceof List) {
            json.append(list2json((List<?>) obj));
        } else if (obj instanceof Map) {
            json.append(map2json((Map<?, ?>) obj));
        } else if (obj instanceof Set) {
            json.append(set2json((Set<?>) obj));
        } else {
            // json.append(bean2json(obj));
        }
        return json.toString();
    }


    /**
     * 把List<Pojo></>转成Json
     *
     * @param _list 需要转换的list对象
     * @return
     */
    public static JSONArray List2json(List<?> _list) throws JSONException {
        return new JSONArray(gson.toJsonTree(_list).getAsJsonArray().toString());
    }


//    /**
//     * 把bean转成Json
//     *
//     * @param bean
//     * @return
//     */
//    public static String bean2json(Object bean) {
//        StringBuilder json = new StringBuilder();
//        json.append("{");
//        PropertyDescriptor[] props = null;
//        try {
//            props = Introspector.getBeanInfo(bean.getClass(), Object.class)
//                    .getPropertyDescriptors();
//        } catch (IntrospectionException e) {
//        }
//        if (props != null) {
//            for (int i = 0; i < props.length; i++) {
//                try {
//                    String name = object2json(props[i].getName());
//                    String value = object2json(props[i].getReadMethod().invoke(
//                            bean));
//                    json.append(name);
//                    json.append(":");
//                    json.append(value);
//                    json.append(",");
//                } catch (Exception e) {
//                }
//            }
//            json.setCharAt(json.length() - 1, '}');
//        } else {
//            json.append("}");
//        }
//        return json.toString();
//    }

    /**
     * List转Json
     *
     * @param list
     * @return
     */
    public static String list2json(List<?> list) {
        StringBuilder json = new StringBuilder();
        json.append("[");
        if (list != null && list.size() > 0) {
            for (Object obj : list) {
                json.append(object2json(obj));
                json.append(",");
            }
            json.setCharAt(json.length() - 1, ']');
        } else {
            json.append("]");
        }
        return json.toString();
    }

    /**
     * 数组转Json
     *
     * @param array
     * @return
     */
    public static String array2json(Object[] array) {
        StringBuilder json = new StringBuilder();
        json.append("[");
        if (array != null && array.length > 0) {
            for (Object obj : array) {
                json.append(object2json(obj));
                json.append(",");
            }
            json.setCharAt(json.length() - 1, ']');
        } else {
            json.append("]");
        }
        return json.toString();
    }

    /**
     * Map转Json
     *
     * @param map
     * @return
     */
    public static String map2json(Map<?, ?> map) {
        StringBuilder json = new StringBuilder();
        json.append("{");
        if (map != null && map.size() > 0) {
            for (Object key : map.keySet()) {
                json.append(object2json(key));
                json.append(":");
                json.append(object2json(map.get(key)));
                json.append(",");
            }
            json.setCharAt(json.length() - 1, '}');
        } else {
            json.append("}");
        }
        return json.toString();
    }


    /**
     * Map转Json
     *
     * @param map
     * @return
     */
    public static JSONObject map2Json(Map<?, ?> map) throws JSONException {
        StringBuilder json = new StringBuilder();
        json.append("{");
        if (map != null && map.size() > 0) {
            for (Object key : map.keySet()) {
                json.append(object2json(key));
                json.append(":");
                json.append(object2json(map.get(key)));
                json.append(",");
            }
            json.setCharAt(json.length() - 1, '}');
        } else {
            json.append("}");
        }
        return new JSONObject(json.toString());
    }


    /**
     * Set转Json
     *
     * @param set
     * @return
     */
    public static String set2json(Set<?> set) {
        StringBuilder json = new StringBuilder();
        json.append("[");
        if (set != null && set.size() > 0) {
            for (Object obj : set) {
                json.append(object2json(obj));
                json.append(",");
            }
            json.setCharAt(json.length() - 1, ']');
        } else {
            json.append("]");
        }
        return json.toString();
    }

    /**
     * 字符串转Json
     *
     * @param s
     * @return
     */
    public static String string2json(String s) {
        if (s == null)
            return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            switch (ch) {
                case '"':
                    sb.append("\\\"");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                case '/':
                    sb.append("\\/");
                    break;
                default:
                    if (ch >= '\u0000' && ch <= '\u001F') {
                        String ss = Integer.toHexString(ch);
                        sb.append("\\u");
                        for (int k = 0; k < 4 - ss.length(); k++) {
                            sb.append('0');
                        }
                        sb.append(ss.toUpperCase());
                    } else {
                        sb.append(ch);
                    }
            }
        }
        return sb.toString();
    }


    public static String formatJson(String s) {
        if (s == null)
            return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            switch (ch) {
                case '{':
                    sb.append("\n");
                    sb.append(ch);
                    sb.append("\n");
                    break;
                case '[':
                    sb.append("\n");
                    sb.append(ch);
                    sb.append("\n");
                    break;
                case '}':
                    sb.append("\n");
                    sb.append(ch);
                    break;
                case ']':
                    sb.append("\n");
                    sb.append(ch);
                    break;
                case ',':
                    sb.append(ch);
                    sb.append("\n");
                    break;
                default:
                    sb.append(ch);
                    break;
            }
        }
        return sb.toString();
    }

    public static JSONObject mergeJOSNObject(JSONObject newJsonObject, JSONObject oldJsonObject) {
        JSONObject newJsonObj = new JSONObject();
        Iterator<String> keys1 = newJsonObject.keys();
        while (keys1.hasNext()) {
            String key = keys1.next();
            try {
                if (!newJsonObj.has(key))
                    newJsonObj.put(key, newJsonObject.get(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Iterator<String> keys2 = oldJsonObject.keys();
        while (keys2.hasNext()) {
            String key = keys2.next();
            try {
                if (!newJsonObj.has(key))
                    newJsonObj.put(key, oldJsonObject.get(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return newJsonObj;
    }

    public static  Map<String, String> jsonObj2Map(JSONObject jsonObject) {
        Map<String, String> map = new HashMap<>();
        Iterator<String> keys2 = jsonObject.keys();
        while (keys2.hasNext()) {
            String key = keys2.next();
            try {
                map.put(key, String.valueOf(jsonObject.get(key)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * 从asset路径下读取对应文件转String输出
     * @param mContext
     * @return
     */
    public static String getJson(Context mContext, String fileName) {
        // TODO Auto-generated method stub
        StringBuilder sb = new StringBuilder();
        AssetManager am = mContext.getAssets();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    am.open(fileName)));
            String next = "";
            while (null != (next = br.readLine())) {
                sb.append(next);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            sb.delete(0, sb.length());
        }
        return sb.toString().trim();
    }
}
