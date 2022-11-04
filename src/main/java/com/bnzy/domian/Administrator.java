package com.bnzy.domian;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 管理员
 */
@Data
public class Administrator implements Serializable {
    public Administrator() {
    }

    public Administrator(String name, String password, String account, String camp) {
        this.name = name;
        this.password = password;
        this.account = account;
        this.camp = camp;
    }

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.INPUT)//数据库id自行增长
    private Integer id;

    private String name;//管理员姓名

    private String password;//密码

    private String account;//账户

    private String camp;//归属地（学校名称，教育局）

    private Integer state;//0表示启用，1表示禁用


}
