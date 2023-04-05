package ibf2022.paf.assessment.server.controllers;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ibf2022.paf.assessment.server.models.Task;
import ibf2022.paf.assessment.server.services.TodoService;

@Controller
@RequestMapping("/task")
public class TasksController {

    @Autowired
    TodoService todoSvc;

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView saveTasks(@RequestBody MultiValueMap<String, String> formData){
        String username = formData.getFirst("username");
        Map<String,String> resultSet = formData.toSingleValueMap();
        resultSet.remove("username");
        Integer count = (resultSet.size())/3 ;
        String taskCount = count.toString();
        Map<String, String> model = new HashMap<String, String>();
        model.put("taskCount", taskCount);
        model.put("username", username);

        List<Task> tasksReceived = new LinkedList<Task>();
        for (int i = 0; i < count; i++) {
            Task task = new Task();
            String descriptionKey  = "description-"+i;
            String priorityKey = "priority-"+i;
            String dueDateKey = "dueDate-"+i;
            task.setDescription(resultSet.get(descriptionKey));
            task.setPriority(Integer.parseInt(resultSet.get(priorityKey)));
            task.setDueDate(java.sql.Date.valueOf(resultSet.get(dueDateKey)));
            tasksReceived.add(task);
        }

        try {
            Boolean result = todoSvc.upsertTask(username, tasksReceived);
            if(result){
                ModelAndView success = new ModelAndView("result", model, HttpStatus.OK);
                return success;
            }
            
            else{
                ModelAndView fail = new ModelAndView("error", model, HttpStatus.INTERNAL_SERVER_ERROR);
                return fail;
            }
        } catch (Exception e) {
            ModelAndView failException = new ModelAndView("error", model, HttpStatus.INTERNAL_SERVER_ERROR);
                return failException;
        }
        

        
    }
}
