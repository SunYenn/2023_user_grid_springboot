package com.study.grid.VO;

import lombok.Data;

@Data
public class EttUserPwd {

    private String user_pwd_seq; // 비밀번호 시퀀스(PK)
    private String user_seq; // 유저 관리 시퀀스(FK)
    private String user_pwd; // 비밀번호

}
