package com.wuyang.yygh.common.result;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
//统一返回的结果，方便前端人员看Swagger接口
@Data
public class R {
    private Integer code;
    private String message;
    private boolean success;
    private Map<String,Object> data = new HashMap<>();


    private R(){} //构造器私有化,当我们去new一个对象时，会在heap中开辟内存空间，对进行实例化，我们可以新建一个构造器，随后进行赋值
    //链式调用
    public static R ok(){
        R r = new R();
        r.setCode(20000);
        r.setMessage("成功");
        r.setSuccess(true);
        return r;
    }
    public static R error(){
        R r = new R();
        r.setCode(20001);
        r.setMessage("失败");
        r.setSuccess(true);
        return r;
    }
    public R code(Integer code){
        this.setCode(code);
        return this;
    }
    public R message(String message){
        this.setMessage(message);
        return this;
    }
    public R success(boolean success){
        this.setSuccess(success);
        return this;
    }

    public R data(String key,Object value){
        this.data.put(key,value);
        return this;
    }

    public R data(Map<String,Object> map){
        this.setData(map);
        return this;
    }

}
