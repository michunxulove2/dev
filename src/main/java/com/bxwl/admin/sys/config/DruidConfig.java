package com.bxwl.admin.sys.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.filter.config.ConfigTools;
import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * 
 * @Title DruidConfig.java
 * @Package com.bxwl.admin.sys.config
 * @Description druid 配置
 * @author fangbb
 * @date 2018年2月7日 上午11:37:34
 * @version V1.0
 */
@Configuration
public class DruidConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(DruidConfig.class);  
	   
	@Value("${spring.datasource.driverClassName}")  
    private String driverClassName;  
    @Value("${spring.datasource.url}")  
    private String url;  
    @Value("${spring.datasource.username}")  
    private String username;  
    @Value("${spring.datasource.password}")  
    private String password;  
    @Value("${spring.datasource.initialSize}")  
    private int initialSize;  
    @Value("${spring.datasource.minIdle}")  
    private int minIdle;  
    @Value("${spring.datasource.maxActive}")  
    private int maxActive;  
    @Value("${spring.datasource.maxWait}")  
    private int maxWait;  
    @Value("${spring.datasource.validationQuery}")  
    private String validationQuery;  
    @Value("${spring.datasource.testOnBorrow}")  
    private boolean testOnBorrow;  
    @Value("${spring.datasource.testOnReturn}")  
    private boolean testOnReturn;  
    @Value("${spring.datasource.testWhileIdle}")  
    private boolean testWhileIdle;  
    @Value("${spring.datasource.timeBetweenEvictionRunsMillis}")  
    private int timeBetweenEvictionRunsMillis;  
    @Value("${spring.datasource.minEvictableIdleTimeMillis}")  
    private int minEvictableIdleTimeMillis;  
    @Value("${spring.datasource.removeAbandoned}")  
    private boolean removeAbandoned;  
    @Value("${spring.datasource.removeAbandonedTimeout}")  
    private int removeAbandonedTimeout;  
    @Value("${spring.datasource.logAbandoned}")  
    private boolean logAbandoned;  
    @Value("${spring.datasource.filters}")  
    private String filters;  
    @Value("${spring.datasource.poolPreparedStatements}")  
    private boolean poolPreparedStatements;  
    @Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}")  
    private int maxPoolPreparedStatementPerConnectionSize;  
    @Value("${spring.datasource.connectionProperties}")
    private String connectionProperties;
    @Value("${spring.datasource.publicKey}")
    private String publicKey;
    /**
     * 
     * <p>Description:@Bean声明，为Spring容器所管理， @Primary表示这里定义的DataSource将覆盖其他来源的DataSource。 </p>
     * @return
     */
    @Bean  
    @Primary  
    public DataSource dataSource() {  
        DruidDataSource datasource = new DruidDataSource();
        datasource.setDriverClassName(driverClassName);  
        datasource.setUrl(url);  
        datasource.setUsername(username);  
        try {
        	//采用druid默认的公钥解密
			datasource.setPassword( ConfigTools.decrypt(publicKey, password));
		} catch (Exception e1) {
			LOGGER.error("解密", e1);
		}
        //其它配置  
        datasource.setInitialSize(initialSize);  
        datasource.setMinIdle(minIdle);  
        datasource.setMaxActive(maxActive);  
        datasource.setMaxWait(maxWait);  
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);  
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);  
        datasource.setValidationQuery(validationQuery);  
        datasource.setTestWhileIdle(testWhileIdle);  
        datasource.setTestOnBorrow(testOnBorrow);  
        datasource.setTestOnReturn(testOnReturn);  
        datasource.setRemoveAbandoned(removeAbandoned);
        datasource.setRemoveAbandonedTimeout(removeAbandonedTimeout);
        datasource.setLogAbandoned(logAbandoned);
        datasource.setPoolPreparedStatements(poolPreparedStatements);  
        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);  
        datasource.setConnectionProperties(connectionProperties);
        try {  
            datasource.setFilters(filters);  
        } catch (SQLException e) {  
            LOGGER.error("druid configuration initialization filter", e);  
        }  
        return datasource;  
    }

    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource)
            throws Exception {
        return new DataSourceTransactionManager(dataSource);
    }

}  
