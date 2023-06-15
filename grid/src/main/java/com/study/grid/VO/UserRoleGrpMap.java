package com.study.grid.VO;

import lombok.Data;

@Data
// 유저와 그룹 매칭(join) 용도읜 클래스
public class UserRoleGrpMap extends CommData {

    private int user_seq; // UserMSt - 유저 관리 시퀀스(FK)
    private int role_grp_seq; // CommRoleGrp - 권한 그룹 관리 시퀀스(FK)

}
