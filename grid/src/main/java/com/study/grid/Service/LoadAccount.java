package com.study.grid.Service;

import com.study.grid.DAO.UserMstMapper;
import com.study.grid.Security.CustomUserDetails;
import com.study.grid.VO.CommRoleGrp;
import com.study.grid.VO.UserMst;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoadAccount implements UserDetailsService {

    private final UserMstMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {

        UserMst user = null;
        CommRoleGrp role = null;

        if (user == null) return null;

        UserDetails member = new CustomUserDetails(user.getUser_id(), nickname, role.getRole_grp_name());

        return member;
    }
}
