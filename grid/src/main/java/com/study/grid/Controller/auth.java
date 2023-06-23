package com.study.grid.Controller;


import com.study.grid.DAO.authMapper;
import com.study.grid.Security.AuthProvider;
import com.study.grid.Service.ConvertToIPv4;
import com.study.grid.Service.LoginService;
import com.study.grid.VO.UserData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;

@Slf4j
@RestController
@CrossOrigin("http://192.168.1.139:3000")
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class auth {

    private final LoginService loginService;

    private final AuthProvider authProvider;

    @Autowired
    private authMapper mapper;


    @PostMapping("/login") // 로그인
    public ResponseEntity<?> login(@RequestBody UserData data, HttpServletRequest request) throws NoSuchAlgorithmException {

        data.getEttUserMst().setLast_login_ip(ConvertToIPv4.convert(request.getRemoteAddr()));
        log.info("ip : {}", ConvertToIPv4.convert(request.getRemoteAddr()));
        UserData userData = loginService.loginMember(data.getEttUserMst(), data.getEttUserPwd());

        return ResponseEntity.ok()
            .header("accesstoken", authProvider
                    .createToken(
                            userData.getEttUserMst().getUser_id(),
                            userData.getEttUserMst().getUser_name(),
                            userData.getEttRoleGrp().getRole_grp_name()))
            .body(userData);
    }

}
