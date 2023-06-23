package com.study.grid.Service;

import com.study.grid.DAO.authMapper;
import com.study.grid.Security.CustomUserDetails;
import com.study.grid.VO.EttRoleGrp;
import com.study.grid.VO.EttUserMst;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoadAccount implements UserDetailsService {

    private final authMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {

        EttUserMst user = null;
        EttRoleGrp role = null;

        if (user == null) return null;

        UserDetails member = new CustomUserDetails(user.getUser_id(), nickname, role.getRole_grp_name());

        return member;
    }
}
