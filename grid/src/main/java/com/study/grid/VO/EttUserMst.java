package com.study.grid.VO;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class EttUserMst extends CommData {

    private int user_seq; // 유저 관리 시퀀스(PK)
    private String user_id; // 로그인 id
    private String user_name; // 사용자 이름
    private String user_sno; // 사번
    private String user_email; // 이메일주소
    private String user_telno; // 전화번호
    private String card_id; // 출입카드 id
    private String card_exp_dt; // 카드 만료일
    private String user_stat_cd; // 사용자 상태 코드
    private String acct_exp_dt; // 계정 만료일
    private String login_fail_cnt; // 로그인 실패 횟수
    private String login_dt; // 로그인 일시
    private String last_login_ip; // 마지막 로그인 ip
    private String emp_type_cd; // 직급 코드
    private String zone_list; // 구역 리스트?

    private String role_grp_name; // 권한 그룹 이름

    public String getter(int j) {
        switch (j) {
            case 1 : return user_id;
            case 2 : return user_name;
            case 3 : return user_sno;
            case 4 : return user_email;
            case 5 : return user_telno;
            case 6 : return card_id;
            case 7 : return card_exp_dt;
            case 8 : return acct_exp_dt;
            case 9 : return login_dt;
            case 10 : return last_login_ip;
            case 11 : return getDel_yn();
        }
        return null;
    }


}
