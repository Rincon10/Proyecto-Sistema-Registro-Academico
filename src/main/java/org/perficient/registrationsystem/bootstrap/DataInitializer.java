package org.perficient.registrationsystem.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * Class BootStrap Created on 21/09/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 */
@Component
public class DataInitializer {

    private final DataSource dataSource;

    public DataInitializer(@Autowired DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadData() {
        ResourceDatabasePopulator resourceDatabasePopulator1 = new ResourceDatabasePopulator(false, false, "UTF-8", new ClassPathResource("sql/data.sql"));
        ResourceDatabasePopulator resourceDatabasePopulator2 = new ResourceDatabasePopulator(false, false, "UTF-8", new ClassPathResource("sql/triggers.sql"));

        resourceDatabasePopulator2.execute(dataSource);
        resourceDatabasePopulator1.execute(dataSource);
    }
}
