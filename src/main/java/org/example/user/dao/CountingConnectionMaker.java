package org.example.user.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class CountingConnectionMaker implements ConnectionMaker{
    int counter = 0;
    private ConnectionMaker realConnectionMaker;

    public CountingConnectionMaker(ConnectionMaker realConnectionMaker){
        this.realConnectionMaker = realConnectionMaker;
    }

    public Connection makeConnection() throws ClassNotFoundException, SQLException{
        this.counter++;//db 연결 횟수 카운팅 커넥션 생성
        return realConnectionMaker.makeConnection();
    }

    public int getCounter(){
        return this.counter;
    }
}
