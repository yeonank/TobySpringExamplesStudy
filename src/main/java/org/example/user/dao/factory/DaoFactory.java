package org.example.user.dao.factory;

import org.example.user.interfaces.ConnectionMaker;
import org.example.user.dao.DConnectionMaker;
import org.example.user.dao.UserDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

@Configuration//애플리케이션 컨텍스트가 사용할 정보 표시
public class DaoFactory {//팩토리를 통해 객체를 생성해서 반환함으로 Main에서 ConnectionMaker을 결정하는 것을 막음

    @Bean//Ioc용 오브젝트 생성 메소드
    public UserDao userDao() {
        UserDao userDao = new UserDao();
        userDao.setDataSource(dataSource());
        return userDao;
        //return new UserDao(connectionMaker());
    }

    @Bean
    public ConnectionMaker connectionMaker() {
        return new DConnectionMaker();
    }

    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

        dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
        dataSource.setUrl("jdbc:mysql://localhost:3306/tobys?useUnicode=true&serverTimezone=Asia/Seoul");
        dataSource.setUsername("root");
        dataSource.setPassword("1813756");

        return dataSource;
    }
}
