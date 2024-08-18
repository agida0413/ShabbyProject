package com.sist.service.member.setting;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sist.common.SimpleCodeGet;
import com.sist.repository.member.MemberAccountRepository;
import com.sist.repository.member.MemberSettingRepository;
import com.sist.vo.MemberVO;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class ChangeInfoServiceImpl implements ChangeInfoService{

	private final MemberAccountRepository memberAccountRepository;
	private final MemberSettingRepository memberSettingRepository;
	private final SimpleCodeGet simpleCodeGet;
	
	
	//회원 정보 리턴
	@Override
	public ResponseEntity<?> getInfo() {
		// TODO Auto-generated method stub
	
		String email=simpleCodeGet.getEmail();
		
		MemberVO vo = memberAccountRepository.findByUserEmail(email);
		
		if(vo==null) {
			return new ResponseEntity<>("no-access",HttpStatus.UNAUTHORIZED);//401
		}
		
		
		return new ResponseEntity<>(vo,HttpStatus.OK);//401;
	}

	//회원정보 변경 = > 닉네임변경
	@Transactional
	@Override
	public ResponseEntity<?> nickNameUpdate(MemberVO vo) {
		// TODO Auto-generated method stub
		int idNum = simpleCodeGet.getIdNum();//고유 아이디 갖고오기 
		String nickname=vo.getNickname();//입력받은 닉네임 
		MemberVO findVo=memberAccountRepository.findByUserNickname(nickname);//닉네임에 해당하는 회원이 있는지검색
		
		if(!(findVo==null)) {//존재하면 즉, 중복되면 
			return new ResponseEntity<>("already nickanme",HttpStatus.BAD_REQUEST);//400
		}
		
		//중복검사를 통과하면
		vo.setIdNum(idNum);//데이터에 저장할 객체에 현재 회원 아이디번호 셋
		
		memberSettingRepository.updateNickName(vo);//최종적으로 vo에 닉네임과 ,고유번호 전달하여 데이터베이스 업데이트 
		
		return new ResponseEntity<>("OK",HttpStatus.OK);//성공 리턴
	}
	
	

}
