package com.study.grid.Service;

import com.study.grid.DAO.UserMstMapper;
import com.study.grid.VO.UserMst;
import com.study.grid.VO.UserPwd;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Slf4j
@Service("signService")
@RequiredArgsConstructor
public class LoginService {

    @Autowired
    private UserMstMapper mapper;

    @Autowired
    private ModelMapper modelMapper;

    public UserMst loginMember(UserMst vo, UserPwd pwd) throws NoSuchAlgorithmException {

        // 회원 엔티티 객체 생성 및 조회시작
        UserMst confirm = null;

        // 계정 유무 체크
        if (confirm == null) return null;

        // 비밀번호 일치 체크
        if (!PWencryption.encrypt(pwd.getUser_pwd()).equals(pwd.getUser_pwd())) return null;

        // 회원정보를 인증클래스 객체(authentication)로 매핑
        return modelMapper.map(confirm, UserMst.class);
    }

}