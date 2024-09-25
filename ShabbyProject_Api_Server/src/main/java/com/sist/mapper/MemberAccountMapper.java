package com.sist.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.sist.dto.member.EmailAuthDTO;
import com.sist.dto.member.MemberDTO;


@Mapper
public interface MemberAccountMapper {
	// <!-- 이메일을 통한 회원 검증 -->
	public MemberDTO findByUserEmail(String email);
	//<!--회원 고유번호를 통한 회원 셀렉트 -->
	public MemberDTO findByUserIdNum(int idNum);
	//회원 자기소개를 고유번호를 통한 셀렉트
	public MemberDTO introduceByUserIdNum(int idNum);
	//<!--닉네임을 통한 회원검증(중복 닉네임)-->
	public MemberDTO findByUserNickname(String nickname);
	//<!--핸드폰 번호 중복검증-->
	public MemberDTO findByUserPhone(String phone);
	//<!-- 회원가입-->
	public void join (MemberDTO dto);
	//	/<!-- 이메일 인증이력 저장 -->
	public void emailAuthSave(EmailAuthDTO dto);
	//<!--이메일 인증시 코드검증 셀렉트 여러 정보가 있을 수 있으니 가장최신것을 가져옴 -->
	public EmailAuthDTO emailAuthGetValidation(String email);
	//<!--이메일 인증 성공시 해당 로우 인증완료로 상태 업데이트-->
	public void emailAuthClear(int emailAuthNum);
	//<!--이메일 찾기 닉네임, 이름  일치여부 -->
	public MemberDTO findEmail(MemberDTO dto);
	//	<!--임시패스워드 발급 후 저장-->
	public void tempPasswordUpdate(MemberDTO dto);
	//공개.비공개 상태획득
	public String getLock(String nickname);
	//회원가입전 최종 validate
	public EmailAuthDTO emailAuthBeforeJoin(String email);
}
