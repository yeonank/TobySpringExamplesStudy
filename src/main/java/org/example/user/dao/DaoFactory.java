package org.example.user.dao;

import org.example.user.domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration//애플리케이션 컨텍스트가 사용할 정보 표시
public class DaoFactory {//팩토리를 통해 객체를 생성해서 반환함으로 Main에서 ConnectionMaker을 결정하는 것을 막음

    @Bean//Ioc용 오브젝트 생성 메소드
    public UserDao userDao(){
        return new UserDao(connectionMaker());
    }

    @Bean
    public ConnectionMaker connectionMaker(){
        return new DConnectionMaker();
    }
}
