package abacus.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * JSON helper functions
 */
public class JsonUtil {

    public static <T> String toJson(T obj)
    {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.toJson(obj);
    }

    public static <T> List<String> toJson(List<T> objects)
    {
        List<String> result = null;
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        if (objects.size()>0)
        {
            result = new ArrayList<String>(objects.size());
            for (Object obj : objects){
                result.add(gson.toJson(obj));
            }
        }
        return result;
    }

    public static <T> T fromJson(String json, java.lang.Class<T> clazz)
    {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.fromJson(json, clazz);
    }
}
