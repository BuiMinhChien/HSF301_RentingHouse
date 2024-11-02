package com.spring.mvc;

import com.spring.mvc.common.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.MultipartResolver;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration //Đánh dấu class này là một class cấu hình Java.
@PropertySource("classpath:database.properties") //Cho phép nạp file cấu hình
@ComponentScan //Tự động phát hiện và nạp các bean trong các package khác nhau
@EnableTransactionManagement //Kích hoạt quản lý giao dịch, cho phép các phương thức xử lý giao dịch.
public class DatabaseConfig implements Constants.DatabaseConfig {
    private final Environment environment; // Use org.springframework.core.env.Environment class
    //Environment duoc inject vao trong constructor
    public DatabaseConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean(value = "dataSource", name = {"dataSource"}, autowireCandidate = true)
    DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getProperty(JDBC_DRIVER_CLASSNAME,
                JDBC_DRIVER_CLASSNAME_DEFAULT));
        dataSource.setUrl(environment.getProperty(JDBC_URL));
        dataSource.setUsername(environment.getProperty(JDBC_USERNAME));
        dataSource.setPassword(environment.getProperty(JDBC_PASSWORD));
        return dataSource;
    }
    //    @Bean
//    public PlatformTransactionManager transactionManager(DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//
//    }
    @Bean(autowireCandidate = true)
    HibernateTransactionManager getTransactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        System.out.println("--->getTransactionManager() = " + transactionManager ==  null ? " null " : "getTransactionManager is Not null");
        return transactionManager;
    }

    @Bean(autowireCandidate = true)
    LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(new String[] { "com.spring.mvc.entity", "com.spring.mvc", "com.spring.mvc.dao", "com.spring.mvc.dao.impl",  "com.spring.mvc.service.impl" });
        sessionFactory.setHibernateProperties(hibernateProperties());
        System.out.println("--> ...sessionFactory= " + (sessionFactory ==  null ? " null " : "SessionFactory is Not null"));
        return sessionFactory;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put(HIBERNATE_DIALECT, environment.getProperty(HIBERNATE_DIALECT));
        properties.put(HIBERNATE_SHOW_SQL, environment.getProperty(HIBERNATE_SHOW_SQL));
        properties.put(HIBERNATE_FORMAT_SQL, environment.getProperty(HIBERNATE_FORMAT_SQL));
//        properties.put(HIBERNATE_HBM2DDL_AUTO, environment.getProperty(HIBERNATE_HBM2DDL_AUTO));
        return properties;
    }

}
//1. SessionFactory
//Định nghĩa: SessionFactory là một đối tượng trong Hibernate được sử dụng để tạo ra các phiên (session) làm việc với cơ sở dữ liệu. Nó được cấu hình một lần và có thể được tái sử dụng để tạo ra nhiều phiên.
//Chức năng: SessionFactory quản lý các phiên và cung cấp phương thức để mở một phiên mới khi cần thiết. Mỗi phiên đại diện cho một ngữ cảnh làm việc duy nhất với cơ sở dữ liệu, cho phép thực hiện các thao tác như truy vấn và cập nhật dữ liệu.
//2. TransactionManager
//Định nghĩa: TransactionManager là một đối tượng chịu trách nhiệm quản lý các giao dịch. Trong Hibernate, bạn thường sử dụng HibernateTransactionManager để quản lý các giao dịch trong bối cảnh của Hibernate.
//Chức năng: TransactionManager đảm bảo rằng các thao tác với cơ sở dữ liệu được thực hiện trong một giao dịch. Điều này có nghĩa là nếu một trong những thao tác gặp lỗi, giao dịch sẽ bị hủy (rollback), giữ cho cơ sở dữ liệu ở trong trạng thái nhất quán.
//3. Mối liên hệ giữa SessionFactory và TransactionManager
//Tạo phiên và quản lý giao dịch: Khi bạn muốn thực hiện một thao tác trên cơ sở dữ liệu, bạn thường mở một phiên từ SessionFactory. Sau đó, bạn bắt đầu một giao dịch thông qua TransactionManager.
//Ràng buộc: Mỗi phiên (Session) có thể có một giao dịch liên kết với nó. Giao dịch này được quản lý bởi TransactionManager. Khi bạn gọi phương thức beginTransaction() trên phiên, TransactionManager sẽ bắt đầu một giao dịch cho phiên đó.
//Commit/Rollback: Khi bạn thực hiện các thao tác trên cơ sở dữ liệu và muốn lưu chúng, bạn gọi commit() trên giao dịch. Nếu có lỗi xảy ra và bạn muốn hủy bỏ các thay đổi, bạn gọi rollback(). TransactionManager đảm bảo rằng những thao tác này được thực hiện một cách an toàn.