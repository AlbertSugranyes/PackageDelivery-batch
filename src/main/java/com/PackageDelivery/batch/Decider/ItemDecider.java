package com.PackageDelivery.batch.Decider;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

import java.util.Random;

public class ItemDecider implements JobExecutionDecider {
    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        Random rand = new Random();
        String result = rand.nextDouble() < 0.7 ? "CORRECT":"INCORRECT";
        System.out.println("Item is " + result);
        return new FlowExecutionStatus(result);
    }

}
