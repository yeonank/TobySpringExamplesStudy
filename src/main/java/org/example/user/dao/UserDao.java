package org.example.user.dao;

import org.example.Exception.NoReturnException;
import org.example.user.domain.User;

import javax.sql.DataSource;
import java.sql.*;

public class UserDao {//abstract 상속을 사용
    //ConnectionMaker connectionMaker;
    private DataSource dataSource;
    //private Connection c와 같이 싱글톤객체에서는 상태가 변하는 변수를 갖으면 안된다

    public UserDao(){}
   /* public UserDao(ConnectionMaker connectionMaker){
        //connectionMaker = new DConnectionMaker()의 형태에서 벗어나 객체의 클래스 종속성을 없애기 위해 외부에서 객체를 주입
        this.connectionMaker = connectionMaker;
    }*/

    public void setDataSource(DataSource dataSource){
        this.dataSource = dataSource;
    }

    //수정자 메소드를 이용한 생성자 주입
    /*public void setConnectionMaker(ConnectionMaker connectionMaker){
        this.connectionMaker = connectionMaker;
    }*/
    public void add(User user) throws ClassNotFoundException, SQLException{
        Connection c = dataSource.getConnection();
        //timezone 설정, jdbc drvier import
        PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values(?,?,?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        c.close();

    }

    public User get(String id) throws ClassNotFoundException, SQLException, NoReturnException {
        Connection c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement(
                "select * from users where id = ?"
        );
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();

        User user = null;
        if(rs.next()){
            user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
        }

        rs.close();
        ps.close();
        c.close();

        if (user == null) throw new NoReturnException("매치하는 데이터가 없습니다");

        return user;
    }

    public void deleteAll() throws SQLException, ClassNotFoundException{
        Connection c = dataSource.getConnection();
        PreparedStatement ps = c.prepareStatement(
                "delete from users");
        ps.executeUpdate();

        ps.close();
        c.close();
    }

    public int getCount() throws SQLException, ClassNotFoundException {
        Connection c = dataSource.getConnection();
        PreparedStatement ps = c.prepareStatement(
                "select count(*) from users");
        ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt(1);

        rs.close();
        ps.close();
        c.close();

        return count;
    }

    //public abstract Connection getConnection() throws ClassNotFoundException, SQLException;
}
