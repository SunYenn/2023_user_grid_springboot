package com.study.grid.VO;

import lombok.Data;

@Data
public abstract class CommData {

    private String del_yn; // 삭제 여부
    private String cre_id; // 등록자
    private String cre_dt; // 등록일시
    private String udt_id; // 수정자
    private String udt_dt; // 수정일시

}
