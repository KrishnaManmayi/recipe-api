package com.manmayi.recipe.data;

import com.manmayi.recipe.model.Recipe;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.persistence.EntityManagerFactory;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    EntityManagerFactory emf;

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    String[] fieldNames = {"id", "recipe_title", "url", "vote_count", "rating","description", "cuisine", "course",
            "diet", "prep_time", "cook_time", "ingredients","instructions", "author","tags", "category"};

    @Bean
    public FlatFileItemReader<RecipeInput> reader() {
        return new FlatFileItemReaderBuilder<RecipeInput>()
                .name("personItemReader")
                .resource(new ClassPathResource("food_recipes.csv"))
                .delimited()
                .names(fieldNames)
                .fieldSetMapper(new BeanWrapperFieldSetMapper<RecipeInput>() {{
                    setTargetType(RecipeInput.class);
                }})
                .build();
    }

    @Bean
    public RecipeDataProcessor processor() {
        return new RecipeDataProcessor();
    }

    @Bean
    public JpaItemWriter<Recipe> writer() {
        JpaItemWriter<Recipe> writer = new JpaItemWriter();
        writer.setEntityManagerFactory(emf);
        return writer;
    }

    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(JpaItemWriter<Recipe> writer) {
        return stepBuilderFactory.get("step1")
                .<RecipeInput, Recipe> chunk(50)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }
}
