/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.bp.processor.planManager;
 
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author Thinkpad
 */
public class PlanJobProxy implements Job {

    private PlanJob job = null;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        this.job = (PlanJob) jobDataMap.get("PlanJob");
        this.job.execute(null);
    }
}
