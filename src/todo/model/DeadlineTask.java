package todo.model;

import todo.exception.InvalidTaskException;

//
public class DeadlineTask extends Task {
 
 private String deadline; 

 public DeadlineTask(int id, String title, String description, String deadline) 
         throws InvalidTaskException {
     super(id, title, description); 
     this.deadline = deadline;
 }

 public String getDeadline() {
     return deadline;
 }

 @Override
 public String toString() {
     return "[期限: " + deadline + "] " + super.toString();
 }
}