package com.study.grid.VO;

import lombok.Data;

@Data
// 유저와 그룹 매칭(join), 정렬, 검색, 페이징 용도의 클래스
public class UserRoleGrpMap extends CommData {

    private int user_seq; // UserMSt - 사용자 관리 시퀀스(FK)
    private int role_grp_seq; // CommRoleGrp - 권한 그룹 관리 시퀀스(FK)

    private String user_name_fg;
    private String cre_dt_fg;
    private String udt_dt_fg;

}
