package com.liwm.seckill.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {

    private int code;
    private T data;
    private String msg;

    public static <T> Result<T> success(T data) {
        return new Result(0, data, null);
    }

    public static <T> Result<T> fail(String message) {
        return new Result(-1, (Object)null,message);
    }

    public static <T> Result<T> fail(long code, String message) {
        return new Result(-1, (Object)null, message);
    }

    public static <T> Result<T> empty() {
        return new Result(0, (Object)null, "");
    }

}
