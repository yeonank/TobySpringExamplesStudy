import org.example.Exception.NoReturnException;
import org.example.user.dao.DConnectionMaker;
import org.example.user.dao.UserDao;
import org.example.user.dao.factory.DaoFactory;
import org.example.user.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import java.sql.SQLException;

public class UserDaoTest {
    private UserDao dao;
    private User user1;
    private User user2;
    private User user3;
    @Before
    public void setup(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        this.dao = context.getBean("userDao", UserDao.class);

        user1 = new User("gyumee", "박성철", "springno1");
        user2 = new User("leegw700", "이길원", "springno2");
        user3 = new User("bumjin", "박범진", "springno3");
    }
    @Test
    public void addAndGet() throws SQLException, ClassNotFoundException{
        dao.deleteAll();
        assertThat(dao.getCount(), is(0));//데이터 삭제 후 테스트 결과 0

        dao.add(user1);
        dao.add(user2);
        assertThat(dao.getCount(), is(2));

        User userget1 = dao.get(user1.getId());
        assertThat(userget1.getName(), is(user1.getName()));
        assertThat(userget1.getPassword(), is(user1.getPassword()));

        User userget2 = dao.get(user2.getId());
        assertThat(userget2.getName(), is(user2.getName()));
        assertThat(userget2.getPassword(), is(user2.getPassword()));

        //-----------------DConnectionMaker에서 호출 횟수 가져오는 버전 test
        /*DConnectionMaker ccm = context.getBean("connectionMaker", DConnectionMaker.class);
        System.out.println("DConnectionMaker에서 호출한 횟수: " + ccm.getCounter());*/
    }

    @Test
    public void count() throws SQLException, ClassNotFoundException{
        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        dao.add(user1);
        assertThat(dao.getCount(), is(1));

        dao.add(user2);
        assertThat(dao.getCount(), is(2));

        dao.add(user3);
        assertThat(dao.getCount(), is(3));
    }

    @Test(expected = NoReturnException.class)
    public void getUserFailure() throws SQLException, ClassNotFoundException{
        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        dao.get("unknown_id");
    }
}
