package com.example.demo.task;

import com.example.demo.car.entity.GmCarOrder;
import com.example.demo.car.service.IGmCarOrderService;
import com.example.demo.task.MyTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ScheduledFuture;


@Component
public class TaskUtil {
		@Autowired
		private ThreadPoolTaskScheduler threadPoolTaskScheduler;

		//A wrapper class for storing task executions
		private   HashMap<Integer, ScheduledFuture<?>> scheduleMap = new HashMap<>();

		public    void addTask(IGmCarOrderService iGmCarOrderService, GmCarOrder data, Date  date){

			//Initialize a task (there can be multiple initial words)
			MyTask helloTask = new MyTask(iGmCarOrderService,data);
			String corn = getCorn(date);
			//Pass the task to the task scheduler for execution
			ScheduledFuture<?> schedule = threadPoolTaskScheduler.schedule(helloTask, new CronTrigger(corn));
			scheduleMap.put(data.getId(),schedule);
		}

			public void stopTask( Integer id){
				if(scheduleMap.containsKey(id)){//If this task is included
					ScheduledFuture<?> scheduledFuture = scheduleMap.get(id);
					if(scheduledFuture!=null){
						scheduledFuture.cancel(true);
					}
				}
			}



		private  String  getCorn(Date  date){
			SimpleDateFormat sdf = new SimpleDateFormat("ss mm HH dd MM ?");
			String formatTimeStr = null;
			if (date != null) {
				formatTimeStr = sdf.format(date);
			}
			return formatTimeStr;

		}
}
