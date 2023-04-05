package ibf2022.paf.assessment.server.repositories;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ibf2022.paf.assessment.server.models.Task;

@Repository
public class TaskRepository {

    @Autowired
    JdbcTemplate template;
    
    private final String INSERTTASK_SQL = "insert into task (task_id, description, priority, due_date, username) values (?, ?, ?, ?, ?);";

    public Integer insertTask(String username, Task task){
        String newTaskId = UUID.randomUUID().toString().replace("-", "");
        newTaskId = newTaskId.substring(0, 20);
        task.setTaskId(newTaskId);
        Integer inserted = template.update(INSERTTASK_SQL, task.getTaskId(), task.getDescription(), task.getPriority(), task.getDueDate(), username);
        return inserted;
    }
}
