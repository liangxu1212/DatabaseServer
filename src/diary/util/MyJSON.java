package diary.util;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by admin on 2017/5/2.
 */
public class MyJSON extends JSONObject{
    JSONObject data;
    public MyJSON(){
        data = new JSONObject();
    }
    public void setStatus(String status){
        this.put("status",status);
    }
    public void putData(String key,String data){
        this.data.put(key,data);
    }
    public void putData(String jsonString){
        this.data = JSONObject.parseObject(jsonString);
    }

    public String toJSONString(){
        this.put("data",data.toJSONString());
        return super.toJSONString();
    }
}
