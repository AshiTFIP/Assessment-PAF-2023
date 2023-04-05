package ibf2022.paf.assessment.server.services;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ibf2022.paf.assessment.server.models.Task;
import ibf2022.paf.assessment.server.models.User;
import ibf2022.paf.assessment.server.repositories.TaskRepository;
import ibf2022.paf.assessment.server.repositories.UserRepository;

@Service
public class TodoService {
    
    @Autowired
    JdbcTemplate template;

    @Autowired
    UserRepository userRepo;

    @Autowired
    TaskRepository taskRepo;

    @Transactional
    public Boolean upsertTask(String username, List<Task> tasks){
        Optional<User> user = userRepo.findUserByUsername(username);
        User newUser = new User();
        String newUserId = "";

        if(user.isEmpty()){
            newUser.setName(username.toUpperCase());
            newUser.setUsername(username);            
            newUserId = userRepo.insertUser(newUser);

            if(newUserId.equalsIgnoreCase("User not inserted")){
                throw new IllegalArgumentException("User not created");
            }
        }

        for(Task task:tasks){
            Integer inserted = taskRepo.insertTask(username, task);
            if(inserted == 0){
                throw new IllegalArgumentException("Tasks not added");
            }
        }
        return true;
    }

}
