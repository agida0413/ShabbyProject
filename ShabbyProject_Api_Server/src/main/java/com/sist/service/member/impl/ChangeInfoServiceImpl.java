package com.sist.service.member.impl;



import java.net.http.HttpResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sist.common.exception.BadRequestException;
import com.sist.common.exception.InternerException;
import com.sist.common.util.CookieUtil;
import com.sist.common.util.PathVariableValidation;
import com.sist.common.util.SimpleCodeGet;
import com.sist.dto.api.ResponseDTO;
import com.sist.dto.member.MemberDTO;
import com.sist.dto.setting.ChangeNickNameDTO;
import com.sist.dto.setting.ChangePasswordDTO;
import com.sist.dto.setting.ChangePhoneDTO;
import com.sist.dto.util.AlarmChangeDTO;
import com.sist.jwt.JWTUtil;
import com.sist.mapper.FollowMapper;
import com.sist.repository.AlarmRepository;
import com.sist.repository.MemberAccountRepository;
import com.sist.repository.MemberSettingRepository;
import com.sist.service.member.ChangeInfoService;
import com.sist.service.member.RefreshService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class ChangeInfoServiceImpl implements ChangeInfoService{
	
	private final MemberAccountRepository memberAccountRepository;
	private final MemberSettingRepository memberSettingRepository;
	private final RefreshService refreshService;
	private final AlarmRepository alarmRepository;
	private final FollowMapper followMapper;
	//패스워드 암호화
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final JWTUtil jwtUtil;
	
	//회원 정보 리턴
	//2024.08.19 수정 = > 회원정보 전체를 넘기면 위험할 수 있음 ., 필요한 정보만 넘기게
	@Override
	public ResponseEntity<ResponseDTO<MemberDTO>> getLockedInfo() {//회원 공개여부 데이터
		// TODO Auto-generated method stub
		
		//회원 고유번호를 가져옴 
		int idNum=SimpleCodeGet.getIdNum();
			
			//고유번호에 해당하는 회원정보 가져오기 
			MemberDTO dto =	memberAccountRepository.findByIdNum(idNum);
	
		//만약 회원이 없다면 	
		if(dto==null) {
			
		throw new InternerException("비정상적인 접근입니다.","비정상적인 접근입니다.");//사용자 정의400에러 발생
		
		}
		//비공개/공개 상태여부를 가져옴 

		//전송객체 생성	
		MemberDTO sendDto=new MemberDTO();
		//전송데이터 세팅
		sendDto.setLocked(dto.getLocked());//비공개여부 
		sendDto.setNickname(dto.getNickname());//닉네임 
		  return new ResponseEntity<ResponseDTO<MemberDTO>>
			(new ResponseDTO<MemberDTO>(sendDto),HttpStatus.OK); //성공 
	}

	//선행조건 : 닉네임중복검사 통과 = >비밀번호 검증=>닉네임변경
	@Transactional
	@Override
	public ResponseEntity<ResponseDTO<Void>> nickNameUpdate(ChangeNickNameDTO dto,HttpServletResponse response,HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		
		//고유 아이디넘버 갖고오기 
		int idNum = SimpleCodeGet.getIdNum();
		
		//회원정보 갖고오기 
		MemberDTO findDto = memberAccountRepository.findByIdNum(idNum);
		
		//비밀번호가 일치하지않을시 
		if(!bCryptPasswordEncoder.matches(dto.getPassword(), findDto.getPassword())) {
			
			
			throw new BadRequestException("비밀번호가 일치 하지않습니다.");//사용자 정의400에러 발생
		}
		
		
		findDto.setNickname(dto.getNickname());//닉네임 세팅
		memberSettingRepository.updateNickName(findDto);//최종적으로 vo에 닉네임과 ,고유번호 전달하여 데이터베이스 업데이트 
	
		//현재 시큐리티 컨텍스트 홀더 닉네임 값 변경 
		SimpleCodeGet.setNickname(dto.getNickname());
		
		
	        String username = SimpleCodeGet.getEmail();//이메일
	        String strIdNum=String.valueOf(idNum);//고유번호 문자열로변환
			String nickname=dto.getNickname();//닉네임
	      
	        //새로운 jwt 토큰 발급
			//why? 기존 토큰이용 시 닉네임 변경 전 닉네임이 토큰에 들어있고 그 값을 파싱하여 서버에서 이용하기 때문에
			//이상현상 및 잘못된 값을 이용 큰 버그를 초래한다.
	        String newAccess = jwtUtil.createJwt("access", username, strIdNum,nickname, 300000L);
	        String newRefresh = jwtUtil.createJwt("refresh", username, strIdNum,nickname, 86400000L);
	        
	        String refresh=null;
	        
	        try {
		    	  refresh=(String)CookieUtil.getCookie("refresh", request);

			} catch (Exception e) {
				// TODO: handle exception
				 throw new BadRequestException("비정상적인 접근입니다.");//사용자 정의 익셉션 발생
				 
				 
			}
	        if(refresh.equals("")||refresh==null) {
	        	throw new  BadRequestException("비정상적인 접근입니다.");
	        }
	        
	      
	     	        
			refreshService.deleteRefresh(refresh); //Refresh 토큰 저장 DB에 기존의 Refresh 토큰 삭제 후 새 Refresh 토큰 저장
			
			 
			refreshService.addRefreshEntity(idNum, newRefresh, 86400000L,request);//새 토큰 데이터에 저장
	        
	        response.setHeader("access", newAccess); //새로운 토큰을 헤더에 추가 
	        
	        response.addCookie(CookieUtil.createCookie("refresh", newRefresh)); // 쿠키생성 메서드

		
		
		 return new ResponseEntity<ResponseDTO<Void>>
			(new ResponseDTO<Void>(),HttpStatus.OK); //성공 
	}

	
	
	
	//패스워드 변경 
	@Override
	public ResponseEntity<ResponseDTO<Void>>  passwordUpdate(ChangePasswordDTO dto) {
		// TODO Auto-generated method stub
		//validation을 위한 memberDto 객체 생성
		MemberDTO valDto=new MemberDTO();
		//비밀번호 셋팅
		valDto.setPassword(dto.getPassword());
		
		
		int idNum=SimpleCodeGet.getIdNum();//고유번호를 가져옴 
	
		MemberDTO findDto= memberAccountRepository.findByIdNum(idNum); // 데이터베이스에서 일치하는 회원정보 
		
		
		//입력받은 패스워드와 일치하지않다면 , 
		if(!bCryptPasswordEncoder.matches(dto.getPassword(), findDto.getPassword())) {
			throw new BadRequestException("비밀번호가 일치 하지않습니다.");//사용자 정의400에러 발생
		}
		
		if(bCryptPasswordEncoder.matches(dto.getNewPassword(),findDto.getPassword())) {//바꾸려는 패스워드와 기존패스워드가 일치할경우 
			throw new BadRequestException("기존 비밀번호와 동일한 비밀번호는 사용할 수 없습니다.");//사용자 정의400에러 발생 
		}
		String newPassword= bCryptPasswordEncoder.encode(dto.getNewPassword());// 새로운 패스워드 암호화 
		
		MemberDTO updateDto = new MemberDTO();//dto에 데이터베이스 업데이트 할 값 세팅
		updateDto.setIdNum(idNum);
		updateDto.setPassword(newPassword);
		
		memberSettingRepository.updatePassword(updateDto); //데이터 베이스 비밀번호 업데이트
		// 데이터베이스 리프레시토큰 삭제는 프론트영역에서 logout api를 곧바로 호출해주니까 해줄 필요가 없다(logout api에서 리프레시토큰 삭제)
		
		
		 return new ResponseEntity<ResponseDTO<Void>>
			(new ResponseDTO<Void>(),HttpStatus.OK); //성공 
	}

	
	//핸드폰 번호 변경 ( 비밀번호 검증= > 동일한 휴대폰번호인지 검증 = > 이미 있는 핸드폰번호 인지 검증 )
	@Transactional
	@Override
	public ResponseEntity<ResponseDTO<Void>>  phoneChange(ChangePhoneDTO dto) {
		// TODO Auto-generated method stub
		
		int idNum=SimpleCodeGet.getIdNum(); //회원 고유번호
		 
		MemberDTO findDto = memberAccountRepository.findByIdNum(idNum);//데이터 베이스에서 고유번호 기반 정보 같고옴 
		
		
		if(!bCryptPasswordEncoder.matches(dto.getPassword(), findDto.getPassword())) {//입력받은 비밀번호와 ,데이터베이스 패스워드가 다를시 
			
			throw new BadRequestException("비밀번호가 일치 하지않습니다.");//사용자 정의400에러 발생
		}
		
		if(dto.getPhone().equals(findDto.getPhone())) {//입력받은 핸드폰 번호와 현재 핸드폰 번호가 같을 시 
			
			throw new BadRequestException("기존 휴대폰번호와 동일한 번호로 수정할 수 없습니다.");//사용자 정의400에러 발생
		}
		
		MemberDTO findSamePhoneUser= memberAccountRepository.findByUserPhone(dto.getPhone());
		if (!(findSamePhoneUser==null)) {
			//입력한 핸드폰 번호와 전체회원의 핸드폰 번호중 동일한 핸드폰있는 회원목록을 갖고옴 
		
			//그 회원이 존재한다면 
			throw new BadRequestException("이미 존재하는 번호입니다.");//사용자 정의400에러 발생
		}
		
		
		
		
		
		findDto.setPhone(dto.getPhone());
	
		memberSettingRepository.updatePhone(findDto); // 데이터베이스에 휴대폰 번호 업데이트 
		
		 return new ResponseEntity<ResponseDTO<Void>>
			(new ResponseDTO<Void>(),HttpStatus.OK); //성공 
	}

	
	//공개/ 비공개 모드 변경
	@Override
	@Transactional
	public ResponseEntity<ResponseDTO<MemberDTO>> updateLockedState(MemberDTO dto) 
	{	
		
		int idNum=SimpleCodeGet.getIdNum();//회원 고유 아이디갖고오기 
		// TODO Auto-generated method stub
		String currentState=dto.getLocked();//현재 상태값 
		String changeState=""; //변경할 공개/비공개 모드 상태값 초기화
		
		if(currentState.equals("PUBLICID")) {// 만약 현재 상태값이 공개모드이면 
			changeState="LOCKED"; //공개모드 상태에서의 요청이라면 비공개모드로 
		}
		else if(currentState.equals("LOCKED")) {//만약 현재 상태값이 비공개모드이면
			changeState="PUBLICID";//비공개모드 상태에서의 요청이라면 공개모드로 
			//공개 모드로 변경시 그동안 수락하지 않았던 팔로우 요청을 모두 수락으로 변경 , 알람 테이블 또한 ~가 요청합니다 --> ~가 팔로우합니다.
			AlarmChangeDTO alcDTO=new AlarmChangeDTO();
			alcDTO.setIdNum(idNum);
			alarmRepository.changeAlarmStatus(alcDTO);
			followMapper.changeFollowStatus(idNum);
		}
		else {//나머지는 정의된 값이 없으므로 예외 
			
			throw new InternerException("서버 내부오류입니다. 잠시 뒤 이용해주세요.","비공개/공개여부 변경 작업중 오류발생"); // 서버 내부오류 500 발생 
			
		}
		
	
		
		
		dto.setIdNum(idNum);//고유번호 세팅
		dto.setLocked(changeState); // 변한 상태값 세팅 
		
		memberSettingRepository.updateLockedState(dto); // 데이터 베이스 업데이트 
		
		dto.setIdNum(0);//보안방지 고유번호 초기화
		 return new ResponseEntity<ResponseDTO<MemberDTO>>
			(new ResponseDTO<MemberDTO>(dto),HttpStatus.OK); //성공 , 회원정보전송
	}
	
	//회원삭제 회원검증=> 이름, 이메일, 비밀번호 검증 후
	@Override
	public ResponseEntity<ResponseDTO<Void>> deleteMember(MemberDTO dto) {
		// TODO Auto-generated method stub
		
		
		String sessionEmail=SimpleCodeGet.getEmail();// 현재 토큰기반 인증된 임시유지세션에서 이메일 갖고옴 
		int idNum=SimpleCodeGet.getIdNum();//토큰기반 인증된 임시유지세션에서 고유번호 갖고옴 
		
		if(!dto.getEmail().equals(sessionEmail)) {//만약 현재 세션이메일과 입력받은 이메일이 같지않다면 
			throw new BadRequestException("입력한 정보가 일치하지 않습니다."); // 익셉션 발생 
		}
		
		MemberDTO findDto= memberAccountRepository.findByUserEmail(dto.getEmail()); // 이메일은 동일한것이 확인 되었으니 이메일기반으로 기타 회원정보 가지고옴 
			
		if(!dto.getName().equals(findDto.getName())) {//입력한 이름과 동일하지않으면
			throw new BadRequestException("입력한 정보가 일치하지 않습니다.");//익셉션 발생 
		}
		
		if(!bCryptPasswordEncoder.matches(dto.getPassword(), findDto.getPassword())) {//입력받은 패스워드와 데이터베이스 패스워드가 일치하지않으면 
			throw new BadRequestException("입력한 정보가 일치하지 않습니다.");
		}
		
		dto.setIdNum(idNum);//dto에 세션고유번호 담기 
		memberSettingRepository.deleteMember(dto);//회원 삭제 
		
		 return new ResponseEntity<ResponseDTO<Void>>
			(new ResponseDTO<Void>(),HttpStatus.OK); //성공 
	}
	
	
	
	
	
	

}
