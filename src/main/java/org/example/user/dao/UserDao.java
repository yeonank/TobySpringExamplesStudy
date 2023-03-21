package org.example.user.dao;

import com.mysql.cj.x.protobuf.MysqlxPrepare;
import org.example.Exception.NoReturnException;
import org.example.user.domain.User;
import org.example.user.interfaces.StatementStrategy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class UserDao {//abstract 상속을 사용
    //private DataSource dataSource;
    //private Connection c와 같이 싱글톤객체에서는 상태가 변하는 변수를 갖으면 안된다
    //private JdbcContext jdbcContext;
    private JdbcTemplate jdbcTemplate;
    private RowMapper<User> userMapper = new RowMapper<User>() {
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
            return user;
        }
    };
    public UserDao() {
    }

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);//수동 di
        //this.dataSource = dataSource;(데이터소스 저장 필요 X)
    }

    public void add(final User user){//내부 클래스에서 외부 로컬 변수 접근 위해 final 설정
        this.jdbcTemplate.update("insert into users(id, name, password) values(?,?,?)", user.getId(), user.getName(), user.getPassword());
    }

    public User get(String id){
        return this.jdbcTemplate.queryForObject("select * from users where id = ?",
                new Object[]{id}, this.userMapper);
    }

    public void deleteAll(){
        this.jdbcTemplate.update("delete from users");
    }

    public int getCount(){
        return this.jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
    }

    public List<User> getAll(){
        return this.jdbcTemplate.query("select * from users order by id", this.userMapper);
    }
}
