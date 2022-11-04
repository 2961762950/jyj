package com.bnzy.domian;

import lombok.Data;

import java.io.Serializable;

/**
 * 招生条件
 */
@Data
public class Request  implements Serializable {

    public Request() {
    }

    public Request(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public Request(Integer schoolId, String request1, String request2, String request3) {
        this.schoolId = schoolId;
        this.request1 = request1;
        this.request2 = request2;
        this.request3 = request3;
    }

    private static final long serialVersionUID = 1L;

    private Integer schoolId;//学校id

    private String request1;//条件1

    private String request2;//条件2

    private String request3;//条件3
}
