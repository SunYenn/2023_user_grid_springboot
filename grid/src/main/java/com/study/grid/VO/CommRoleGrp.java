package com.study.grid.VO;

import lombok.Data;

@Data
public class CommRoleGrp extends CommData {

    private int role_grp_seq; // 권한 그룹 관리 시퀀스(PK)
    private String role_grp_name; // 권한 그룹 이름
    private String role_grp_desc; // 권한 그룹 설명
    private String stat_cd; // 상태 코드
    private String hv_acc_role_yn; // 접근 권한 여부

}

