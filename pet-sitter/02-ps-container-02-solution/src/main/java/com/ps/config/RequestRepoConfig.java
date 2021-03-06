package com.ps.config;

import com.ps.repos.RequestRepo;
import com.ps.repos.impl.JdbcRequestRepo;
import org.springframework.context.annotation.*;

@Configuration
@Import(DataSourceConfig.class)
@ComponentScan(basePackages = {"com.ps"})
public class RequestRepoConfig {

    @DependsOn("dataSource")
    @Bean (initMethod = "init", destroyMethod = "destroy")
    public RequestRepo anotherRepo(){
        return new JdbcRequestRepo();
    }

    @Bean (initMethod = "init", destroyMethod = "destroy")
    public RequestRepo requestRepo(){
        return new JdbcRequestRepo();
    }
}
