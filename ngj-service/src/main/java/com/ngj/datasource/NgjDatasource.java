package com.ngj.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.ngj.resource.ResourceLoadUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by guanxinquan on 16/2/29.
 */
@Slf4j
public class NgjDatasource extends DruidDataSource {

    private static final String DB_FILE = "db.cfg";

    public NgjDatasource() throws Exception {
        super();
        Properties p = ResourceLoadUtils.loadProperties(DB_FILE);
        setDriverClassName(p.getProperty("driver","com.mysql.jdbc.Driver"));
        setUrl(p.getProperty("url"));
        setUsername(p.getProperty("username"));
        setPassword(p.getProperty("password"));
        setFilters(p.getProperty("filters","stat"));
        setMaxActive(Integer.valueOf(p.getProperty("maxActive","20")));
        setInitialSize(Integer.valueOf(p.getProperty("initialSize","5")));
        setDefaultAutoCommit(true);
        log.info("start datasource connect to {}",p.getProperty("url"));
    }

    @Override
    @PostConstruct
    public void init() throws SQLException {
        super.init();
    }

    @Override
    @PreDestroy
    public void close() {
        super.close();
    }
}
