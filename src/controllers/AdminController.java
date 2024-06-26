package src.controllers;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Scanner;

import src.views.MenuView;

import src.models.Admin;
import src.models.User;

import src.dao.AdminDAO;
import src.dao.UserDAO;

public class AdminController {
    private Scanner scanner = new Scanner(System.in);

    private Admin admin = new Admin(-1,"", "", "");
    
    private AdminDAO adminDAO = new AdminDAO();
    private UserDAO userDAO = new UserDAO();
    
    private MenuView menu = new MenuView();
    
    public void addAdmin(){
        
        Class<?> adminClass = admin.getClass();
        Field[] fields = adminClass.getDeclaredFields();
        
        for(Field field: fields){
            field.setAccessible(true);
            boolean validInput = false;
            
            while(!validInput){
                if(field.getName().equals("id")){
                    validInput = true;
                    continue;
                }
                menu.clearScreen();
                System.out.println("Enter " + field.getName() + ": ");
                try {
                    field.set(admin, scanner.nextLine());
                    validInput = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        User user = new User(-1, admin.getEmail(), admin.getPassword(), "admin");

        adminDAO.addAdmin(admin);
        userDAO.addUser(user);
    } 

    public List<Admin> getAdmins(){
        return adminDAO.getAdmins();
    }
}
