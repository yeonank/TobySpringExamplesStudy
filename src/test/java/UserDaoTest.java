import org.example.Exception.NoReturnException;
import org.example.user.dao.DConnectionMaker;
import org.example.user.dao.UserDao;
import org.example.user.dao.factory.DaoFactory;
import org.example.user.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import java.sql.SQLException;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)//스프링 테스트 컨텍스트 프레임워크
@ContextConfiguration(locations = "/applicationContext.xml")//AC 저장된 위치
public class UserDaoTest {
    @Autowired//AC에서 자동으로 자기 자신 빈 등록
    private ApplicationContext context;//테스트 오브젝트 생성 후 컨테이너에 의해 자동으로 주입됨
    @Autowired
    private UserDao dao;
    private User user1;
    private User user2;
    private User user3;
    @Before
    public void setup(){
        //AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        //this.dao = this.context.getBean("userDao", UserDao.class);

        user1 = new User("gyumee", "박성철", "springno1");
        user2 = new User("leegw700", "이길원", "springno2");
        user3 = new User("bumjin", "박범진", "springno3");

        System.out.println(this.context);//@Before가 실행돼도 AC는 동일함
        System.out.println(this);//UserDaoTest는 다르다
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

    @Test(expected = EmptyResultDataAccessException.class)
    public void getUserFailure() throws SQLException, ClassNotFoundException{
        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        dao.get("unknown_id");
    }

    @Test
    public void getAll(){
        dao.deleteAll();

        List<User> user0_L = dao.getAll();
        assertThat(user0_L.size(), is(0));

        dao.add(user1);
        List<User> user1_L = dao.getAll();
        assertThat(user1_L.size(), is(1));
        checkSameUser(this.user1, user1_L.get(0));

        dao.add(user2);
        List<User> user2_L = dao.getAll();
        assertThat(user2_L.size(), is(2));
        checkSameUser(this.user1, user2_L.get(0));
        checkSameUser(this.user2, user2_L.get(1));

        dao.add(user3);
        List<User> user3_L = dao.getAll();
        assertThat(user3_L.size(), is(3));
        checkSameUser(this.user3, user3_L.get(0));
        checkSameUser(this.user1, user3_L.get(1));
        checkSameUser(this.user2, user3_L.get(2));//알파벳순
    }

    private void checkSameUser(User user1, User user2){
        assertThat(user1.getId(), is(user2.getId()));
        assertThat(user1.getName(), is(user2.getName()));
        assertThat(user1.getPassword(), is(user2.getPassword()));
    }
}
