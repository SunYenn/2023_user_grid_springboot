package com.study.grid.DAO;

import com.study.grid.VO.EttRoleGrp;
import com.study.grid.VO.EttUserMst;
import com.study.grid.VO.EttUserPwd;

@org.apache.ibatis.annotations.Mapper
public interface AuthMapper {

    // login시 사용
    EttUserMst IdCheck(String userId);
    EttUserPwd PwCheck(int userSeq);
    int getGrpSeq(int userSeq);
    EttRoleGrp getGrp(int roleGrpSeq);
    void addLoginFailCnt(int userSeq);
    void successLogin(EttUserMst mst);

}
