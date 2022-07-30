package com.example.projecti_trello_app_backend.utils;

import com.example.projecti_trello_app_backend.entities.board.Board;
import com.example.projecti_trello_app_backend.entities.task.Task;
import com.example.projecti_trello_app_backend.entities.user.User;
import com.example.projecti_trello_app_backend.entities.workspace.Workspace;

public class EmailTemplate {


    // add email template
    public static String addTemplate(User byUser, User targetUser, Object obj)
    {
        StringBuilder emailTemp = new StringBuilder();
        emailTemp.append("<h2> Dear " + targetUser.getFirstName() + " " +targetUser.getLastName() +
                "<br> Project I Notification </h2><br>");
        emailTemp.append("<div> <h3> You have been ");
        if(obj instanceof Workspace)
        {
            Workspace workspace = (Workspace) obj;
            emailTemp.append("added to ")
                    .append(workspace.getWorkspaceTitle())
                    .append(" by " + byUser.getFirstName() +" "+ byUser.getLastName())
                    .append(" </h3> <br> </div>");
        }
        else if(obj instanceof Board)
        {
            Board board = (Board) obj;
            emailTemp.append("added to ")
                    .append(board.getBoardTitle())
                    .append(" by " + byUser.getFirstName() +" "+ byUser.getLastName())
                    .append("</h3> <br> </div>");
        }
        else {
             Task task = (Task) obj;
            emailTemp.append("assigned to ")
                    .append(task.getTaskName())
                    .append(" by " + byUser.getFirstName() +" "+ byUser.getLastName())
                    .append("</h3> <br> </div>");
        }
        return emailTemp.toString();
    }

    // update email Template :
     public static  String updateTemplate(User byUser, User targetUser ,Object obj)
     {
         StringBuilder emailTemp = new StringBuilder();
         emailTemp.append("<h2> Dear " + targetUser.getFirstName() + " " +targetUser.getLastName() +
                 "<br> Project I Notification </h2><br>");
         if(obj instanceof Workspace)
         {
             Workspace workspace = (Workspace) obj;
             emailTemp.append(workspace.getWorkspaceTitle() + " Workspace has been updated")
                     .append(" by " + byUser.getFirstName() +" "+ byUser.getLastName())
                     .append(" </h3> <br> </div>");
         }
         else if(obj instanceof Board)
         {
             Board board = (Board) obj;
             emailTemp.append(board.getBoardTitle() +" " + " Board has been udated ")
                     .append(" by " + byUser.getFirstName() +" "+ byUser.getLastName())
                     .append("</h3> <br> </div>");
         }
         else {
             Task task = (Task) obj;
             emailTemp.append(task.getTaskName() +" Task has been updated ")
                     .append(" by " + byUser.getFirstName() +" "+ byUser.getLastName())
                     .append("</h3> <br> </div>");
         }
         return emailTemp.toString();
     }

     //remove email template
    public static String removeTemplate(User byUser, User targetUser, Object obj)
    {
        StringBuilder emailTemp = new StringBuilder();
        emailTemp.append("<h2> Dear " + targetUser.getFirstName() + " " +targetUser.getLastName() +
                "<br> Project I Notification </h2><br>");
        emailTemp.append("<div> <h3> You have been ");
        if(obj instanceof Workspace)
        {
            Workspace workspace = (Workspace) obj;
            emailTemp.append(" removed from ")
                    .append(workspace.getWorkspaceTitle())
                    .append(" by " + byUser.getFirstName() +" "+ byUser.getLastName())
                    .append(" </h3> <br> </div>");
        }
        else if(obj instanceof Board)
        {
            Board board = (Board) obj;
            emailTemp.append(" removed from ")
                    .append(board.getBoardTitle())
                    .append(" by " + byUser.getFirstName() +" "+ byUser.getLastName())
                    .append("</h3> <br> </div>");
        }
        else {
            Task task = (Task) obj;
            emailTemp.append(" removed from ")
                    .append(task.getTaskName())
                    .append(" by " + byUser.getFirstName() +" "+ byUser.getLastName())
                    .append("</h3> <br> </div>");
        }
        return emailTemp.toString();
    }
}
