package kr.or.ddit.member.service;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import kr.or.ddit.member.dao.IMemberDAO;

@Component
public class RealMemberDeleteJob {
	private static final Logger logger = 
				LoggerFactory.getLogger(RealMemberDeleteJob.class);
	
	@Inject
	private IMemberDAO dao;
	
	@Scheduled(cron = "0 0 3 ? * MON")
	@Scheduled(cron = "*/5 * * * * *")
	public void realDelete() {
		// 실제로 탈퇴를 처리. 특정 조건이 만족했을때 실행 처리
		Map<String, Object> pMap = new HashMap<>();
		dao.realDeleteMembers(pMap);
		
		Integer delCount = (Integer) pMap.get("delcount");
		logger.info("스케쥴러가 동작했으며, {} 명이 탈퇴되었음.", delCount);
	}
}
