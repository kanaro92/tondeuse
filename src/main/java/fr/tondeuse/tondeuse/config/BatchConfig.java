package fr.tondeuse.tondeuse.config;

import fr.tondeuse.tondeuse.model.Tondeuse;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Bean
    public Job tondeuseJob(Step step1) {
        return new JobBuilder("tondeuseJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(ItemReader<String> reader, ItemProcessor<String, Tondeuse> processor, ItemWriter<Tondeuse> writer) {
        return new StepBuilder("step1", jobRepository)
                .<String, Tondeuse>chunk(1, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }


}
