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
            if(task.getPriority() > 3 || task.getPriority() < 1){
                throw new IllegalArgumentException("Priority more than 3");
            }

            if(task.getDescription().length() > 255){
                throw new IllegalArgumentException("More than 255 characters in description");
            }
            
            Integer inserted = taskRepo.insertTask(username, task);
            if(inserted == 0){
                throw new IllegalArgumentException("Tasks not added");
            }
        }
        return true;
    }

}
