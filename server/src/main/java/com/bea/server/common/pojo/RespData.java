package com.bea.server.common.pojo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.util.List;


/**
 * @author yanjun
 */
@ToString(exclude = {"MAPPER"})
@NoArgsConstructor
public class RespData {

    /**
     * 定义jackson对象
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 响应状态码
     */
    @Getter
    @Setter
    private int code;

    /**
     * 响应消息
     */
    @Getter
    @Setter
    private String msg;

    /**
     * 响应数据
     */
    @Getter
    @Setter
    private Object data;

    public RespData(RespCode respCode){
        this.code = respCode.getCode();
        this.msg = respCode.getMsg();
    }

    public RespData(RespCode respCode, Object data){
        this.code = respCode.getCode();
        this.msg = respCode.getMsg();
        this.data = data;
    }

    public RespData(int code, String msg, Object data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    public static RespData build(int code, String msg, Object data){
        return  new RespData(code,msg,data);
    }

    public static RespData build(RespCode respCode){
        return new RespData(respCode);
    }

    public static RespData build(RespCode respCode, Object data){
        return new RespData(respCode,data);
    }

    public static RespData ok(){
        return new RespData(RespCode.SUCCESS);
    }

    public static RespData ok(Object data){
        return  new RespData(RespCode.SUCCESS,data);
    }

    public static RespData fail(){
        return new RespData(RespCode.FAIL);
    }

    public static RespData fail(Object data){
        return  new RespData(RespCode.FAIL,data);
    }


    /**
     * 没有data对象，json转化RespData
     * @param json
     * @return
     */
    public static RespData format(String json){
        return formatToPojo(json,null);
    }

    /**
     * json结果集转化为RespData对象
     * @param jsonData
     * @param clazz
     * @return
     */
    public static RespData formatToPojo(String jsonData, Class<?> clazz) {
        try{
            Object obj = null;
            if (clazz == null) {
                return MAPPER.readValue(jsonData, RespData.class);
            } else{
                JsonNode jsonNode = MAPPER.readTree(jsonData);
                JsonNode data = jsonNode.get("data");
                if (data.isObject()) {
                    obj = MAPPER.readValue(data.traverse(), clazz);
                } else if (data.isTextual()) {
                    obj = MAPPER.readValue(data.asText(), clazz);
                }
                return build(jsonNode.get("code").intValue(), jsonNode.get("msg").asText(), obj);
            }
        }catch (Exception e){
            return null;
        }
    }

    public static RespData formatToList(String jsonData, Class<?> clazz) {
        try {
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (data.isArray() && data.size() > 0) {
                obj = MAPPER.readValue(data.traverse(),
                        MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
            }
            return build(jsonNode.get("code").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }


}
