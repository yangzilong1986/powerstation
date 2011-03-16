/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.bp.processor.polling;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author Thinkpad
 */
public class PollingJobProxy implements Job {

    private PollingJob job = null;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        this.job = (PollingJob) jobDataMap.get("PollingJob");
        this.job.execute(null);
    }
}
