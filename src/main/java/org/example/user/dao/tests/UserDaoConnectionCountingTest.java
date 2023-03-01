package org.example.user.dao.tests;

import org.example.user.dao.CountingConnectionMaker;
import org.example.user.dao.factory.CountingDaoFactory;
import org.example.user.dao.UserDao;
import org.example.user.domain.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class UserDaoConnectionCountingTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException{
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(CountingDaoFactory.class);
        UserDao dao = context.getBean("userDao", UserDao.class);//userDao 실행할 때마다 카운팅도 됨

        ////////////Connection Test
        User user = new User();
        user.setId("id1");
        user.setName("연정");
        user.setPassword("password1");
        dao.add(user);//connection 생성
        ////////////

        CountingConnectionMaker ccm = context.getBean("connectionMaker", CountingConnectionMaker.class);
        System.out.println("Connection counter: " + ccm.getCounter());
        //싱글톤 객체 CountingConnectionMaker의 카운팅 횟수를 가져올 수 있음


    }
}
