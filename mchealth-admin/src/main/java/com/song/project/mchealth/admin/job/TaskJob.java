package com.song.project.mchealth.admin.job;

import org.springframework.scheduling.annotation.Scheduled;

public class TaskJob {

	@Scheduled(cron = "0 0/30 * * * ?")
	public void job1() {
	}
}
