package com.study.grid.DAO;

import com.study.grid.VO.EttRoleGrp;
import com.study.grid.VO.EttUserMst;
import com.study.grid.VO.EttUserPwd;
import com.study.grid.VO.EttUserRoleGrpMap;

import java.util.ArrayList;

@org.apache.ibatis.annotations.Mapper
public interface UserManageMapper {

    int IdCheck(String userId);

    ArrayList<EttRoleGrp> getRoleGrpList();
    int getUserSeq();

    // 사용자 등록
    void istUserMst(EttUserMst ettUserMst);
    void istUserPwd(EttUserPwd ettUserPwd);
    void istUserRoleGrpMap(EttUserRoleGrpMap ettUserRoleGrpMap);
}
