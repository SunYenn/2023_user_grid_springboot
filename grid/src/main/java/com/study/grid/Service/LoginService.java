package com.study.grid.Service;

import com.study.grid.DAO.AuthMapper;
import com.study.grid.Exception.ForbiddenException;
import com.study.grid.Exception.UserNotFoundException;
import com.study.grid.VO.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Service("signService")
@RequiredArgsConstructor
public class LoginService {

    @Autowired
    private AuthMapper mapper;

    @Autowired
    private ModelMapper modelMapper;

    public AllData loginMember(EttUserMst istMst, EttUserPwd istPw) throws NoSuchAlgorithmException {

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = simpleDateFormat.format(date);

        // 회원 엔티티 조회 및 객체 생성
        EttUserMst mst = mapper.IdCheck(istMst.getUser_id());

        // 계정 유무 체크
        if (mst == null)
            throw new UserNotFoundException("This account does not exist");

        // 계정 SEQ를 통해 비밀번호 조회 및 객체 생성
        EttUserPwd pwd = mapper.PwCheck(mst.getUser_seq());

        // 비밀번호 일치 체크
        if (!PWencryption.encrypt(istPw.getUser_pwd()).equals(pwd.getUser_pwd())) {
            mapper.addLoginFailCnt(mst.getUser_seq()); // 로그인 실패 횟수 증가
            throw new ForbiddenException("Passwords do not match");
        }

        // UserRoleGrpMap에서 ROLE_GRP_SEQ 조회
        int roleGrpSeq = mapper.getGrpSeq(mst.getUser_seq());

        // ROLE_GRP_SEQ를 통해 그룹 조회
        EttRoleGrp roleGrp = mapper.getGrp(roleGrpSeq);

        mst.setLast_login_ip(istMst.getLast_login_ip());
        mst.setLogin_dt(strDate);
        mapper.successLogin(mst);

        // 회원정보를 인증클래스 객체(authentication)로 매핑
        AllData allData = new AllData(mst, pwd, roleGrp);
        return modelMapper.map(allData, AllData.class);

    }

}