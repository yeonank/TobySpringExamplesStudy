import org.example.user.dao.CountingConnectionMaker;
import org.example.user.dao.DConnectionMaker;
import org.example.user.dao.UserDao;
import org.example.user.dao.factory.CountingDaoFactory;
import org.example.user.dao.factory.DaoFactory;
import org.example.user.domain.User;
import org.junit.jupiter.api.Test;
//import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import java.sql.SQLException;

public class UserDaoTest {
    @Test
    public void addAndGet() throws SQLException, ClassNotFoundException{
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDao dao = context.getBean("userDao", UserDao.class);

        dao.deleteAll();
        assertThat(dao.getCount(), is(0));//데이터 삭제 후 테스트 결과 0

        User user = new User();
        user.setId("id1");
        user.setName("이름");
        user.setPassword("password1");

        dao.add(user);
        assertThat(dao.getCount(), is(1));

        User user2 = dao.get(user.getId());

        assertThat(user2.getName(), is(user.getName()));
        assertThat(user2.getPassword(), is(user.getPassword()));

        //DConnectionMaker에서 호출 횟수 가져오는 버전 test
        DConnectionMaker ccm = context.getBean("connectionMaker", DConnectionMaker.class);
        System.out.println("DConnectionMaker에서 호출한 횟수: " + ccm.getCounter());
    }
}
