package org.example.user.dao;

import org.example.user.domain.User;

import java.sql.*;

public class UserDao {//abstract 상속을 사용
    ConnectionMaker connectionMaker;
    //private Connection c와 같이 싱글톤객체에서는 상태가 변하는 변수를 갖으면 안된다
    public UserDao(ConnectionMaker connectionMaker){
        //connectionMaker = new DConnectionMaker()의 형태에서 벗어나 객체의 클래스 종속성을 없애기 위해 외부에서 객체를 주입
        this.connectionMaker = connectionMaker;
    }
    public void add(User user) throws ClassNotFoundException, SQLException{
        Connection c = connectionMaker.makeConnection();
        //timezone 설정, jdbc drvier import
        PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values(?,?,?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        c.close();

    }

    public User get(String id) throws ClassNotFoundException, SQLException{
        Connection c = connectionMaker.makeConnection();

        PreparedStatement ps = c.prepareStatement(
                "select * from users where id = ?"
        );
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        rs.next();
        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));

        rs.close();
        ps.close();
        c.close();

        return user;
    }

    //public abstract Connection getConnection() throws ClassNotFoundException, SQLException;
}
