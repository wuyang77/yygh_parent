package com.wuyang.yygh.common.exception;
/**
 * 自定义的一个异常类YyghException
 */
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class YyghException extends RuntimeException{
    @ApiModelProperty(value = "状态码")
    private Integer code;
    private String message;
}
