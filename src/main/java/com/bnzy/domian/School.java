package com.bnzy.domian;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 学校
 */
@Data
public class School implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.INPUT)//数据库id自行增长
    private Integer id;

    private Integer ranking;//排名

    private Integer enrollment;//招生人数

    private String level;//层次（大专，本科，重点本科）

    private String headmaster;//校长名字

    private String name;//学校名称

    private String location;//学校位置


}
