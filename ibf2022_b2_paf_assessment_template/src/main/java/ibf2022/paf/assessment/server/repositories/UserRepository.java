package ibf2022.paf.assessment.server.repositories;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ibf2022.paf.assessment.server.models.User;

@Repository
public class UserRepository {

    @Autowired
    JdbcTemplate template;

    private final String FINDBYUSERNAME_SQL = "select * from user where username = ?";
    private final String INSERTUSER_SQL = "insert into user (user_id, username, name) values ( ?, ?, ?)";


    public Optional<User> findUserByUsername(String username){
        User toReturn = new User();
        List<User> users = template.query(FINDBYUSERNAME_SQL, BeanPropertyRowMapper.newInstance(User.class), username);
        if (users.size() == 0){
            toReturn = null;
        }
        else{
            toReturn = users.get(0);
        }
        return Optional.ofNullable(toReturn);
    }

    public String insertUser(User user){
        String newUserId = UUID.randomUUID().toString().replace("-", "");
        newUserId = newUserId.substring(0, 8);
        user.setUserId(newUserId);
        Integer inserted = template.update(INSERTUSER_SQL, user.getUserId(),user.getUsername(), user.getName());
        if (inserted == 0){
            return "User not inserted";
        }
        else return newUserId;
    }
}
