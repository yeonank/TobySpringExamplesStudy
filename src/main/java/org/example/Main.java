package org.example;

import org.example.user.dao.DaoFactory;
import org.example.user.dao.UserDao;
import org.example.user.domain.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        /*ConnectionMaker connectionMaker = new DConnectionMaker();
        //connection 객체를 외부에서 주입함으로 클라이언트에게 책임을 떠넘김
        UserDao dao = new UserDao(connectionMaker);*/

        //UserDao dao = new DaoFactory().userDao(); DaoFactory에서 커넥션 생성

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        //Unsupported class file major version 59 error=> jdk 버전 11로 변경
        UserDao dao = context.getBean("userDao", UserDao.class);//ApplicationContext가 관리하는 오브젝트 요청
        //userDao는 빈 이름

        User user = new User();

        user.setId("id1");
        user.setName("연정");
        user.setPassword("password1");

        dao.add(user);

        System.out.println(user.getId()+" 등록 성공");
        User user2 = dao.get(user.getId());
        System.out.println(user2.getName());
    }
}