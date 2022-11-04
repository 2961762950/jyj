package com.bnzy.domian;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 修改记录
 */
@Data
public class Recording implements Serializable {
    public Recording() {
    }

    public Recording(Integer operate_id, String originally, String changed, LocalDateTime revise) {
        this.operate_id = operate_id;
        this.originally = originally;
        this.changed = changed;
        this.revise = revise;
    }

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.INPUT)//数据库id自行增长
    private Long id;

    private Integer operate_id;//操作员id

    private String originally;//原始值

    private String changed;//修改后

    public LocalDateTime revise;//时间

    public String type;//修改的对象（学生，老师）

}
