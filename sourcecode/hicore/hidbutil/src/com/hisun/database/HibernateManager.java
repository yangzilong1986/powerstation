package com.hisun.database;

import com.hisun.exception.HiException;
import com.hisun.message.HiContext;
import com.hisun.util.HiICSProperty;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.HashMap;

public class HibernateManager {
    private static final HashMap factorys = new HashMap(3);

    public static Session getSession(String dsname) throws HiException {
        SessionFactory factory;
        SessionFactory _factory = (SessionFactory) factorys.get(dsname);
        if (_factory != null) {
            return _factory.openSession();
        }
        Configuration cfg = new Configuration();
        String userId = System.getProperty("userId");
        String password = System.getProperty("password");
        if (HiICSProperty.isJUnitEnv()) {
            String url = System.getProperty("db_url");
            String driver = System.getProperty("db_driver");
            cfg.setProperty("hibernate.connection.driver_class", driver);

            cfg.setProperty("hibernate.connection.url", url);
        } else {
            String dataSourceName = HiContext.getCurrentContext().getStrProp("@PARA", dsname);

            if (StringUtils.isEmpty(dataSourceName)) {
                throw new HiException("212008", dsname);
            }

            cfg.setProperty("hibernate.connection.datasource", dataSourceName);
        }

        try {
            factory = cfg.configure("/conf/hibernate.cfg.xml").buildSessionFactory();
            factorys.put(dsname, factory);
        } catch (Exception e) {
            throw HiException.makeException("220037", e.getMessage(), e);
        }

        return factory.openSession();
    }
}