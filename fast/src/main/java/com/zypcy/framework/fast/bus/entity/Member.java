package com.zypcy.framework.fast.bus.entity;

    import com.baomidou.mybatisplus.annotation.TableName;
    import java.io.Serializable;
    import io.swagger.annotations.ApiModel;
    import io.swagger.annotations.ApiModelProperty;
    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;

/**
* <p>
    * 
    * </p>
*
* @author zhuyu
* @since 2019-07-29
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @TableName("bus_member")
    @ApiModel(value="Member对象", description="")
    public class Member implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String memberName;


}
