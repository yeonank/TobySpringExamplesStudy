package org.example.user.dao.factory;

import org.example.user.interfaces.ConnectionMaker;
import org.example.user.dao.CountingConnectionMaker;
import org.example.user.dao.DConnectionMaker;
import org.example.user.dao.UserDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

@Configuration
public class CountingDaoFactory {
    @Bean
    public UserDao userDao(){
        UserDao userDao = new UserDao();
        userDao.setDataSource(dataSource());
        return userDao;
    }

    @Bean
    public ConnectionMaker connectionMaker(){//CountingConnectionMaker에서 커넥션 생성 함수 실행
        return new CountingConnectionMaker(realConnectionMaker());
    }

    @Bean
    public ConnectionMaker realConnectionMaker(){//DaoFactory의 connectionMaker과 동일한 기능
        return new DConnectionMaker();
    }

    @Bean
    public DataSource dataSource(){
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

        dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
        dataSource.setUrl("jdbc:mysql://localhost:3306/tobys?useUnicode=true&serverTimezone=Asia/Seoul");
        dataSource.setUsername("root");
        dataSource.setPassword("1813756");

        return (DataSource) dataSource;
    }
}
