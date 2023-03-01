package org.example.user.dao.factory;

import org.example.user.dao.ConnectionMaker;
import org.example.user.dao.CountingConnectionMaker;
import org.example.user.dao.DConnectionMaker;
import org.example.user.dao.UserDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CountingDaoFactory {
    @Bean
    public UserDao userDao(){
        return new UserDao(connectionMaker());
    }

    @Bean
    public ConnectionMaker connectionMaker(){//CountingConnectionMaker에서 커넥션 생성 함수 실행
        return new CountingConnectionMaker(realConnectionMaker());
    }

    @Bean
    public ConnectionMaker realConnectionMaker(){//DaoFactory의 connectionMaker과 동일한 기능
        return new DConnectionMaker();
    }
}
