package user;

import com.jale.weblog.article.api.dataobject.Article;
import com.jale.weblog.user.api.dataobject.User;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = UserTest.class)
public class UserTest extends TestCase {

    @Test
    public void login() {
        User user = new User();
        user.setUserName("jale");
        user.setPassword("123456");

        String result = post("user/login", user);

        System.out.println(result);
    }

    @Test
    public void getUserArticle() {
        Article article = new Article();
        article.setUserId(3L);

        String result = post("user/getUserArticle", article);

        System.out.println(result);
    }

    @Test
    public void needToken() {
        String result = post("user/needAuth", null);

        System.out.println(result);
    }

    @Test
    public void redisSave() {
        String result = get("user/redisSave");

        System.out.println(result);
    }

}
