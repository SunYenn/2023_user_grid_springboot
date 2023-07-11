package com.study.grid.VO;

import lombok.Data;

@Data
public class AllData {

    private EttUserMst ettUserMst;
    private EttUserPwd ettUserPwd;
    private EttRoleGrp ettRoleGrp;
    private EttUserRoleGrpMap ettUserRoleGrpMap;

    private Paging paging;

    public AllData() {}

    public AllData(EttUserMst mst, EttUserPwd pwd, EttRoleGrp roleGrp) {
        this.ettUserMst = mst;
        this.ettUserPwd = pwd;
        this.ettRoleGrp = roleGrp;
    }
}
