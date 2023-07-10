package com.study.grid.VO;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class EttRoleGrp extends CommData {

    private int role_grp_seq; // 권한 그룹 관리 시퀀스(PK)
    private String role_grp_name; // 권한 그룹 이름
    private String role_grp_desc; // 권한 그룹 설명
    private String stat_cd; // 상태 코드
    private String hv_acc_role_yn; // 접근 권한 여부

    public String getter(int j) {
        switch (j) {
            case 1 : return role_grp_name;
            case 2 : return role_grp_desc;
            case 3 : return stat_cd;
            case 4 : return hv_acc_role_yn;
            case 5 : return getDel_yn();
            case 6 : return getCre_id();
            case 7 : return getCre_dt();
            case 8 : return getUdt_id();
            case 9 : return getUdt_dt();
        }
        return null;
    }

}

