package org.example.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DConnectionMaker implements ConnectionMaker{
    public Connection makeConnection() throws ClassNotFoundException, SQLException{
        //TODO 여기에 connection counter을 넣어도 되지 않을까 단일 책임 원칙만 피하면?
        //고객사가 여기서 counter 코드까지 구현해야 하는게 맞지 않아서?
        String classname = "com.mysql.cj.jdbc.Driver";//스프링 버전이 8 이상임으로 cj 넣어야 함
        String driverUrl = "jdbc:mysql://localhost:3306/tobys?useUnicode=true&serverTimezone=Asia/Seoul";
        //timezone 설정, jdbc drvier import
        Class.forName(classname);
        Connection c = DriverManager.getConnection(
                driverUrl, "root", "1813756");

        return c;
    }
}
