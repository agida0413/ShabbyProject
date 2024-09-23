package com.sist.repository;

public interface BatchRepository {
	  // /* 현시간보다 인증만료시간이 적은 인증코드 레코드 삭제 */
		public void batchEmailAuth();
		//   /* 현시간보다 만료시간이 적은 리프레시 토큰 삭제 */
		public void batchTokenExpir();
		// /* 비정상 종료로 발생한(로그아웃을 통해 발생 x) 동일한 아이디에서 생성된 리프레시토큰을 삭제*/
		public void batchTokenDupl();
}
