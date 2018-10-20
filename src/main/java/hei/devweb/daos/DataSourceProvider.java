package hei.devweb.daos;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DataSourceProvider {


        private static MysqlDataSource dataSource;

        public static DataSource getDataSource() {
            if (dataSource == null) {
                dataSource = new MysqlDataSource();
                dataSource.setServerName("localhost");
                dataSource.setPort(3306);
                dataSource.setDatabaseName("annoncesjazz_sys");
                dataSource.setUser("annoncesjazz_user");
                dataSource.setPassword("Jujusolo1");
            }
            return dataSource;
        }
    }


