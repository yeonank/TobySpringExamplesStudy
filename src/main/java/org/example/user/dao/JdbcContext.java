package org.example.user.dao;

import org.example.user.interfaces.StatementStrategy;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcContext {
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource){
        this.dataSource = dataSource;
    }
    public void workWithStatementStrategy(StatementStrategy stmt) throws SQLException{
        Connection c = null;
        PreparedStatement ps = null;

        try {
            c = dataSource.getConnection();
            ps = stmt.makePreparesStatement(c);// 어떤 구현체도 같은 함수 이름을 갖고 있기에 확장에는 열리고 변화에는 닫혀있음
            ps.executeUpdate();
        }catch (SQLException e){
            throw e;
        }finally {
            if (ps != null){
                try{
                    ps.close();
                }catch (SQLException e){}
            }
            if(c!= null){
                try {
                    c.close();
                }catch(SQLException e){}
            }
        }
    }

}
