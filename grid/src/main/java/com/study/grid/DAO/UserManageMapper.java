package com.study.grid.DAO;

import com.study.grid.VO.*;

import java.util.ArrayList;

@org.apache.ibatis.annotations.Mapper
public interface UserManageMapper {

    int countUserMstList(Paging paging);
    ArrayList<EttUserMst> selectUserMstList(Paging paging);

    ArrayList<EttRoleGrp> getRoleGrpList();

    int getUserSeq();
    
    // 사용자 등록
    void istUserMst(EttUserMst ettUserMst);
    void istUserPwd(EttUserPwd ettUserPwd);
    void istUserRoleGrpMap(EttUserRoleGrpMap ettUserRoleGrpMap);

    // 사용자 정보 수정
    void udtUserMst(EttUserMst ettUserMst);
    void udtUserRoleGrpMap(EttUserRoleGrpMap ettUserRoleGrpMap);

    // 사용자 정보 가져오기
    EttUserMst getUserData(String id);
}
