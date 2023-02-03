package com.example.learningjavfx;

import java.sql.*;
import java.util.Properties;

public class Database {
static   Connection connection = null;
    static  public Connection getConnection() {
        if(connection!=null ) return  connection;
        try{
         Class.forName("com.mysql.cj.jdbc.Driver");
         connection =  DriverManager.getConnection("jdbc:mysql://localhost:3306/forjava","root","next@7");
        }

        catch (ClassNotFoundException e){
            System.out.println(e);
        }
        catch (SQLException e) {
            System.out.println(e);        }
        return connection;
    }

    // user related model connection
    static  String    createUser (String email, String username, String password) throws SQLException {
        String  signupMessage = "user created";
        ResultSet user =  null;
        String userSelectionByEmail =  "select * from users where email=?";
        PreparedStatement selectionStatementByEmail =  connection.prepareStatement(userSelectionByEmail);
        selectionStatementByEmail.setString(1,email);
        user =  selectionStatementByEmail.executeQuery();
        if(user.next())
            signupMessage ="User Exits with this Email !";
        else {
        String userSelectionByUsername =  "select * from users where username=?";
        PreparedStatement selectionStatementByUsername =  connection.prepareStatement(userSelectionByUsername);
        selectionStatementByUsername.setString(1,username);
        user =  selectionStatementByUsername.executeQuery();
        if(user.next())
            signupMessage ="Reserved Username !";
        else{
            String insertion = "insert into users(email,username,password) values(?,?,?)";
            PreparedStatement statement = connection.prepareStatement(insertion);
            statement.setString(1, email);
            statement.setString(2, username);
            statement.setString(3, password);

            int result = statement.executeUpdate();
            if (result != 1)
                signupMessage = "signUp Failed Try Again !";
        }
        }

        return signupMessage;

    }
    static  public  String logIn(String username ,String password)  {
       String loginMessage =  "loggedIn";
        try {
        String userSelection =  "select * from users where username=?";
        PreparedStatement statement =  connection.prepareStatement(userSelection);
        statement.setString(1,username);
        ResultSet user =  statement.executeQuery();
        if(user.next()){
            String passwordFetched =  user.getString("password").trim();
            if (!passwordFetched.equals(password.trim()))
                loginMessage ="INVALID  PASSWORD !";
        }
        else
          loginMessage ="INVALID USERNAME !";

        }
        catch (Exception e){
            System.out.println(e);
           loginMessage ="LOGIN FAILED TRY AGAIN !";
        }
        return  loginMessage;

    }
    static public boolean  isThereUser  () throws SQLException {
        boolean thereIsUser =  false;
        String selectUsers =  "select * from users ";
        Statement  checkStatement =  connection.createStatement();
        ResultSet users =  checkStatement.executeQuery(selectUsers);
        if( users.next())
            thereIsUser =  true;

        return thereIsUser ;
    }


    // student data related model connection
    static public String  insertStudent(Student student) {
        String insertionMessage =  "inserted";
        try {
            String checkingString = "select * from students where studId = ?";
            PreparedStatement checkingStatement = connection.prepareStatement(checkingString);
            checkingStatement.setString(1, student.studId);
            ResultSet checkingResult = checkingStatement.executeQuery();
            if (checkingResult.next())
                insertionMessage = "STUDENT ALREADY EXITS  !";
            else {
                String insertionString = "insert into students values(?,?,?)";
                PreparedStatement insertionStatement = connection.prepareStatement(insertionString);
                insertionStatement.setString(1, student.studId);
                insertionStatement.setString(2, student.username);
                insertionStatement.setDouble(3, student.gpa);
                int insertionResult = insertionStatement.executeUpdate();
                if (insertionResult != 1)
                    insertionMessage = "Registration  Failed try Again";
            }
        }
        catch(Exception e){
            insertionMessage = "Registration Failed Please Try Again";
        }
        return insertionMessage;

    }
    static public String  isThereStudent(){
         String checkingMessage = "none";
         int listSize =  0;
        try {
            String checkingString = "select * from students";
            Statement checkingStatement = connection.createStatement();
            ResultSet checkingResult = checkingStatement.executeQuery(checkingString);
            while (checkingResult.next()) {
                listSize++;
            }
              if(listSize!=0)
                  checkingMessage ="There is:"+listSize;
        }catch (Exception e){
            checkingMessage = "none";
        }
        return  checkingMessage;

    }
    static public Student[] getStudentsList(int listSize){

        Student student;
        Student[]  studentsList = new Student[0];
        int listIndex =  0;
        int studentsListLength = 0;
        try {
            String selectionString = "select * from students";
            Statement selectionStatement = connection.createStatement();
            ResultSet listSelected = selectionStatement.executeQuery(selectionString);

            while (listSelected.next()){
                studentsListLength++;
            }
            listSelected = selectionStatement.executeQuery(selectionString);
           studentsList  = new Student[studentsListLength];

            while (listSelected.next()){
                student =  new Student(listSelected.getString("studId"),listSelected.getString("username"),listSelected.getDouble("gpa"));
                studentsList[listIndex++] =  student;

            }

        }catch (Exception e){

        }
        return  studentsList;

    }
    static public Student searchStudentById(String studId){
        Student student =  new Student("","",0);
        try {
            String checkingString = "select * from students where studId = ?";
            PreparedStatement checkingStatement = connection.prepareStatement(checkingString);
            checkingStatement.setString(1, studId);
            ResultSet studentSelected = checkingStatement.executeQuery();
            if (studentSelected.next())
            {
                student.studId =  studentSelected.getString("studId");
                student.username =  studentSelected.getString("username");
                student.gpa =  studentSelected.getDouble("gpa");
            }
        }
        catch(Exception e){

        }
        return  student;

    }


}
