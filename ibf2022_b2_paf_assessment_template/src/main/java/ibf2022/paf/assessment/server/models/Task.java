package ibf2022.paf.assessment.server.models;
import java.sql.Date;

public class Task {
    private String description;
    private Integer priority;
    private Date dueDate;
    private String taskId;
    
    public String getTaskId() {
        return taskId;
    }
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Integer getPriority() {
        return priority;
    }
    public void setPriority(Integer priority) {
        this.priority = priority;
    }
    public Date getDueDate() {
        return dueDate;
    }
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
    public Task(String description, Integer priority, Date dueDate, String taskId) {
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
        this.taskId = taskId;
    }
    public Task() {
    }
    
}
