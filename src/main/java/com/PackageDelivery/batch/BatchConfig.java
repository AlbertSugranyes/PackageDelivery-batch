package com.PackageDelivery.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private PlatformTransactionManager transactionManager;
    @Bean
    public Job packageDeliveryJob(){
        return new JobBuilder("packageDeliveyJob",jobRepository)
                .start(packageItemStep())
                .next(driveToAddressStep())
                .next(givePackageToCustomerStep())
                .build();
    }
    @Bean
    public Step packageItemStep(){
        return new StepBuilder("packageItemStep",jobRepository)
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        String item = chunkContext.getStepContext().getJobParameters().get("item").toString();
                        String date = chunkContext.getStepContext().getJobParameters().get("run.date").toString();

                        System.out.printf("The %s has been packaged on %s.%n",item,date);
                        return RepeatStatus.FINISHED;
                    }
                },transactionManager)
                .build();
    }
    @Bean
    public Step driveToAddressStep(){
        return new StepBuilder("driveToAddress",jobRepository)
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("Successfully arrived to the address");
                        return RepeatStatus.FINISHED;
                    }
                },transactionManager)
                .build();
    }
    @Bean
    public Step givePackageToCustomerStep(){
        return new StepBuilder("givePackageToCustomer",jobRepository)
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("Given the package to the customer");
                        return RepeatStatus.FINISHED;
                    }
                },transactionManager)
                .build();
    }
}
