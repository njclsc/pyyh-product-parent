package com.pyyh.product.init.pojo;

import java.util.List;

import org.quartz.Scheduler;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class QuartzConfigPojo {
	private List<QuartzConfigPojo> jobs;
	private String groupJobName;
	private String jobName;
	private String groupTriggerName;
	private String triggerName;
	private String cron;
	private String jobClassName;
	private boolean used;
	private Scheduler scheduler;
}
