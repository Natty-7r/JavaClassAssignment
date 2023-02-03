package com.example.learningjavfx;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;
import java.util.Collection;

public class StudentData {
    static  int currentButton  = 1;
    static  String currentId;
    Student studentSearched;
    Student [] totalStudentsFound;
    int foundStudentIndex ;
    int totalStudents ;
    TextField studIdInput;


    public  MenuBar studentMenu(Stage stage){
        // menu bar
        MenuBar studentMenuBar =  new MenuBar();
        Menu studentMenu =  new Menu("Student Data");
        studentMenu.setStyle("" +
                "-fx-font-size:16px;" +
                "-fx-background:purple;"
        );
        // open menu
        MenuItem addBtn =  new MenuItem("Add Student ");
        addBtn.setOnAction(e->{
            HelloApplication.setStageContent(stage,true,"studentData","add");

        });

        // save menu
        MenuItem listAllBtn =  new MenuItem("List All");
        listAllBtn.setOnAction(e->{
            HelloApplication.setStageContent(stage,true,"studentData","list");

        });

        // save as menu
        MenuItem searchBtn =  new MenuItem("Search");
        searchBtn.setStyle("" +
                "-fx-background:purple;"
        );
        searchBtn.setOnAction(e->{
            HelloApplication.setStageContent(stage,true,"studentData","search");


        });

        studentMenu.getItems().addAll(addBtn ,listAllBtn,searchBtn);
        studentMenuBar.getMenus().add(studentMenu);

        return studentMenuBar;
    }
    public VBox setStudentContent(Stage stage , String subPage){
        VBox studentDataPage =  new VBox();
        if (subPage.equals("add"))
        studentDataPage.getChildren().addAll(studentMenu(stage),addStudent(stage));

        if(subPage.equals("list"))
        studentDataPage.getChildren().addAll(studentMenu(stage),studentList(stage));

       if(subPage.equals("search"))
        studentDataPage.getChildren().addAll(studentMenu(stage),searchStudent(stage));

        HelloApplication.studentSubPage=subPage;

        studentDataPage.setPrefWidth(550);
        studentDataPage.setMaxWidth(600);
        studentDataPage.setMinWidth(450);
      return studentDataPage;
    }
    public VBox searchStudent(Stage stage){
        VBox searchingPage =  new VBox();
        // globally needed
        VBox searchError =  new VBox();

        Label errorMessage = new Label();



        TextArea studentResultData =  new TextArea();
        VBox searchResult =  new VBox();
        searchError.setVisible(false);
        searchResult.setVisible(false);

        HBox searchContainer = new HBox();  //
        {
            searchContainer.setSpacing(10);
            searchContainer.setAlignment(Pos.CENTER);

            studIdInput = new TextField();
            studIdInput.setPrefWidth(420);
            studIdInput.setPromptText("Student ID");
            studIdInput.setStyle("" +
                    "-fx-border:none;" +
                    "-fx-outline:solid thin gray;" +
                    "-fx-padding: 8px 16px;" +
                    "-fx-font-size:14px;");

            Button searchBtn = new Button("Search");
            searchBtn.setStyle("" +
                    "-fx-font-size:16px;" +
                    "-fx-padding:4px 24px;" +
                    "-fx-background-color:purple;" +
                    "-fx-color:red;" +
                    "");
            searchBtn.setOnAction(e->{
                String idInput =  studIdInput.getText().trim();
                if(idInput.isEmpty()){
                    errorMessage.setText("PLEASE INSERT STUDENT ID !");
                    searchError.setVisible(true);
                    searchResult.setVisible(false);

                }
                else {
                    Student [] studentList =  Database.getStudentsList(0);
                    totalStudents =  studentList.length;

                    boolean studentFound =  false;
                    int index = 0;
                    if (studentList.length==0) {
                        errorMessage.setText("STUDENT LIST IS EMPTY !");
                        searchError.setVisible(true);
                        searchResult.setVisible(false);

                    }

                    else {
                        totalStudentsFound =  studentList;
                        for(Student stud : studentList){
                            if(stud.studId.equals(idInput)){
                                studentFound =  true;
                                studentSearched=  stud;
                                foundStudentIndex =  index;
                            }
                            index++;
                        }

                     if (!studentFound) {
                        errorMessage.setText("NO STUDENT ASSOCIATED WITH THE ID !");
                        searchError.setVisible(true);
                        searchResult.setVisible(false);

                    } else if (studentFound) {
                        currentId =  idInput;
                        studentResultData.setText("" +
                                "ID : " + studentSearched.studId +
                                "\nUSERNAME : " + studentSearched.username +
                                " \nGPA : " + studentSearched.gpa
                        );

                        searchError.setVisible(false);
                        searchResult.setVisible(true);


                    }}
                }

            });

            searchContainer.getChildren().addAll(studIdInput, searchBtn);
        }

        VBox resultBox =  new VBox();

        // search error container
        {
            searchError.setAlignment(Pos.CENTER);
            searchError.setPadding(new Insets(20, 0, 10, 0));

            errorMessage.setTextFill(Color.RED);
            errorMessage.setStyle("" +
                    "-fx-color:red;" +
                    "-fx-opacity:1;" +
                    "-fx-font-size:16px;" +
                    "");
            searchError.getChildren().addAll(errorMessage);
        }



        HBox studentInfo  =  new HBox();
        {

            studentResultData.setPrefHeight(120);
            studentResultData.setPrefWidth(400);


            studentResultData.setStyle("" +
                    "-fx-opacity:1;" +
                    "-fx-font-size:16px;" +
                    "-fx-font-weight:bold;"
            );


            studentInfo.setAlignment(Pos.CENTER);
            studentInfo.getChildren().addAll(studentResultData);
        }

        HBox buttonsContainer =  new HBox();
        {
            Button backBtn =  new Button(" <- Previous");
            backBtn.setTextFill(Color.WHITE);
            backBtn.setStyle("" +
                    "-fx-font-size:16px;" +
                    "-fx-padding:4px 16px;" +
                    "-fx-background-color:purple;"
            );
            backBtn.setOnAction(e->{


                if (foundStudentIndex==0) {
                    errorMessage.setText("NO STUDENT FOUND BEFORE THE CURRENT  !");
                    searchError.setVisible(true);
                    searchResult.setVisible(false);

                }
                else {
                    studentSearched = totalStudentsFound[--foundStudentIndex];
                     studIdInput.setText(studentSearched.studId);
                    studentResultData.setText("" +
                                "ID : " + studentSearched.studId +
                                "\nUSERNAME : " + studentSearched.username +
                                " \nGPA : " + studentSearched.gpa
                        );

                        searchError.setVisible(false);
                        searchResult.setVisible(true);
                }


            });


            Button nextBtn =  new Button("Next ->");
            nextBtn.setTextFill(Color.WHITE);
            nextBtn.setStyle("" +
                    "-fx-font-size:16px;" +
                    "-fx-padding:4px 16px;" +
                    "-fx-background-color:purple;"
            );
            nextBtn.setOnAction(e->{


                if (foundStudentIndex==totalStudents) {
                    errorMessage.setText("NO STUDENT FOUND AFTER THE CURRENT  !");
                    searchError.setVisible(true);
                    searchResult.setVisible(false);

                }
                else {
                    studentSearched = totalStudentsFound[++foundStudentIndex];
                    studIdInput.setText(studentSearched.studId);
                    studentResultData.setText("" +
                            "ID : " + studentSearched.studId +
                            "\nUSERNAME : " + studentSearched.username +
                            " \nGPA : " + studentSearched.gpa
                    );

                    searchError.setVisible(false);
                    searchResult.setVisible(true);
                }


            });

            buttonsContainer.setAlignment(Pos.CENTER);
            buttonsContainer.setSpacing(20);
            buttonsContainer.setPadding(new Insets(30,0,0,30));
            buttonsContainer.getChildren().addAll(backBtn,nextBtn);
        }

        searchResult.getChildren().addAll(studentInfo,buttonsContainer);

        searchingPage.getChildren().addAll(searchContainer,searchError,searchResult);
        searchingPage.setPadding(new Insets(10));
        return  searchingPage;
    }

    public VBox addStudent (Stage stage){
        VBox formContainer =  new VBox(); // whole form container
        formContainer.setAlignment(Pos.CENTER);
        formContainer.setSpacing(15);

        VBox loginErrorContainer =  new VBox();
        loginErrorContainer.setAlignment(Pos.CENTER);
        loginErrorContainer.setPadding(new Insets(20,0,10,0));

        Label header =  new Label("STUDENT REGISTRATION FORM ");
        header.setTextFill(Color.PURPLE);
        header.setStyle("" +
                "-fx-color:red;" +
                "-fx-opacity:1;" +
                "-fx-font-size:20px;" +
                "-fx-font-weight:bold;");

        Label searchError =  new Label("STUDENT NOT FOUND ! ");
        searchError.setTextFill(Color.RED);
        searchError.setStyle("" +
                "-fx-color:red;" +
                "-fx-opacity:0;" +
                "-fx-font-size:16px;" +
                "");
        loginErrorContainer.getChildren().addAll(header,searchError);


        //-----------

        HBox idContainer  = new HBox();  //id input
        idContainer.setSpacing(10);
        idContainer.setPadding(new Insets(0,0,0,65));
        idContainer.setAlignment(Pos.CENTER);
        Label idLabel =  new Label("ID: ");
        idLabel.setStyle("" +
                "-fx-font-size:16px;" +
                "-fx-font-weight:bold;" +
                "-fx-text-transform:uppercase;" );
        TextField idInput =  new TextField();
        idInput.setPrefWidth(250);
        idInput.setStyle("" +
                "-fx-border:none;" +
                "-fx-outline:solid thin gray;" +
                "-fx-padding: 8px 16px;" +
                "-fx-font-size:14px;" );
        idContainer.getChildren().addAll(idLabel,idInput);
        // ----------------


        HBox usernameContainer = new HBox();  //username input
        usernameContainer.setSpacing(10);
        usernameContainer.setAlignment(Pos.CENTER);
        Label usernameLabel =  new Label("USERNAME: ");
        usernameLabel.setStyle("" +
                "-fx-font-size:16px;" +
                "-fx-font-weight:bold;" +
                "-fx-text-transform:uppercase;" );
        TextField usernameInput =  new TextField();
        usernameInput.setPrefWidth(250);
        usernameInput.setStyle("" +
                "-fx-border:none;" +
                "-fx-outline:solid thin gray;" +
                "-fx-padding: 8px 16px;" +
                "-fx-font-size:14px;" );
        usernameContainer.getChildren().addAll(usernameLabel,usernameInput);


        //-------------



        HBox gradeContainer = new HBox();
        gradeContainer.setSpacing(10);
        gradeContainer.setPadding(new Insets(0,0,0,37));
        gradeContainer.setAlignment(Pos.CENTER);
        Label gradeLabel =  new Label("GRADE:");
        gradeLabel.setStyle("" +
                "-fx-font-size:16px;" +
                "-fx-font-weight:bold;" +
                "-fx-text-transform:uppercase;" );
        TextField gradeInput  = new TextField();
        gradeInput.setPrefWidth(250);
        gradeInput.setPadding(new Insets(4));
        gradeInput.setStyle("" +
                "-fx-border:none;" +
                "-fx-outline:solid thin gray;" +
                "-fx-padding: 8px 16px;"+
                "-fx-font-size:14px;" );
        gradeContainer.getChildren().addAll(gradeLabel,gradeInput);

        HBox btnsContainer =  new HBox();

        Button cancelBtn =  new Button("CANCEL");
        cancelBtn.setStyle("" +
                "-fx-font-size:16px;" +
                "-fx-padding:4px 16px;" +
                "-fx-background-color:red;" +
                "-fx-color:red;" +
//                "-fx-color:#fff;" +   // it get red when I set it white ???
                "");


        cancelBtn.setOnAction(e->{
            idInput.setText("");
            usernameInput.setText("");
            gradeInput.setText("");
        });

        Button saveBtn =  new Button("SAVE");
        saveBtn.setStyle("" +
                "-fx-font-size:16px;" +
                "-fx-padding:4px 24px;" +
                "-fx-background-color:purple;" +
                "-fx-color:red;" +
                "");

        saveBtn.setOnAction(e-> {

            String id = idInput.getText();
            String username = usernameInput.getText();
            String grade = gradeInput.getText();

            if(id.isEmpty()|| username.isEmpty()|| grade.isEmpty()){
                searchError.setText("INPUT FIELDS CANNOT BE EMPTY ! ");
                searchError.setStyle("" +
                        "-fx-color:red;" +
                        "-fx-opacity:1;" +
                        "-fx-font-size:16px;" +
                        "");
            }
            else {

                Student student = new Student(id, username, Double.parseDouble(grade));
                String insertionResult = Database.insertStudent(student);
                if (insertionResult.equals("inserted")) {
                    System.out.println("student inserted");
                    idInput.setText("");
                    usernameInput.setText("");
                    gradeInput.setText("");
                } else {
                    searchError.setText(insertionResult);
                    searchError.setStyle("" +
                            "-fx-color:red;" +
                            "-fx-opacity:1;" +
                            "-fx-font-size:16px;" +
                            "");
                }
            }

//
//            if((!id.startsWith("Ru")||id.startsWith("ru")||id.startsWith("rU")||id.startsWith("RU"))){
//                searchError.setText("Wrong format of ID !");
//                searchError.setStyle("" +
//                        "-fx-color:red;" +
//                        "-fx-opacity:1;" +
//                        "-fx-font-size:16px;" +
//                        "");
//            }

//            String studentData =  id +"," + username + "," + grade;
//            File studentDataFile =  new File("C:\\Users\\NATTY-7\\Desktop\\forJavaFx\\studentData.txt.txt");
//            fileSaver(studentDataFile,studentData,true);

        });

        btnsContainer.setAlignment(Pos.CENTER);
        btnsContainer.setSpacing(20);
        btnsContainer.setPadding(new Insets(30,0,0,80));
        btnsContainer.getChildren().addAll(saveBtn,cancelBtn);
        formContainer.getChildren().addAll( loginErrorContainer,idContainer,usernameContainer,gradeContainer,btnsContainer);
        formContainer.setPadding(new Insets(0,0,50,0));
        return formContainer;
    }
    public VBox studentList (Stage stage){
        VBox list =  new VBox();

        Label listHeader = new Label("Students List");
        listHeader.setFont(new Font("Arial", 20));
        listHeader.setTextFill(Color.PURPLE);
        listHeader.setStyle("" +
                "-fx-opacity:1;" +
                "-fx-font-size:20px;" +
                "-fx-font-weight:bold;"
        );

        HBox tableHeader =  new HBox();
        tableHeader.setSpacing(1);
        Label idLabel =  styleColumn( new Label("ID"),1);
        Label usernameLabel =  styleColumn( new Label("USERNAME"),1);
        Label gpaLabel =  styleColumn( new Label("GPA"),1);


        tableHeader.getChildren().addAll(idLabel,usernameLabel,gpaLabel);

        VBox  tableBody =  fillTableBody(5);
        list.setPadding(new Insets(5));
        list.getChildren().addAll(tableHeader,tableBody);
        return list;
    }
    public VBox fillTableBody (int size){
        String listCheck =  Database.isThereStudent();
        VBox tableBody =  new VBox();

        if(listCheck.equals("none")){
            VBox noListHeader =  new VBox();
            noListHeader.setAlignment(Pos.CENTER);
            noListHeader.setPadding(new Insets(30,0,30,0));

            Label header =  new Label("NO STUDENT IS REGISTERED YET! ");
            header.setTextFill(Color.PURPLE);
            header.setStyle("" +
                    "-fx-color:red;" +
                    "-fx-opacity:1;" +
                    "-fx-font-size:20px;" +
                    "-fx-font-weight:bold;"
            );
            noListHeader.getChildren().addAll(header);
            tableBody.setAlignment(Pos.CENTER);
            tableBody.getChildren().addAll(noListHeader);

        }
        else{
            int listSize =  Integer.parseInt(listCheck.split(":")[1]);
            Student [] studentList =  Database.getStudentsList(listSize);

                for (Student student : studentList) {

                    HBox tableRow = new HBox();
                    tableRow.setSpacing(1);

                    Label studIdLabel = styleColumn(new Label(student.studId), 2);
                    Label usernameLabel = styleColumn(new Label(student.username), 2);
                    Label gpaLabel = styleColumn(new Label(Double.toString(student.gpa)), 2);

                    tableRow.getChildren().addAll(studIdLabel, usernameLabel, gpaLabel);

                    tableBody.setSpacing(1);
                    tableBody.getChildren().add(tableRow);

            }
        }





        return  tableBody;
    }
    private Label styleColumn (Label column,int type ){
        String bgColor = "";
        Color color = null;

        column.setPrefWidth(200);;
        column.setPadding(new Insets(2,2,2,30));
        if(type==1){
            bgColor ="-fx-background-color:purple;";
            color =  Color.WHITE;
        }

        if(type ==2) {
            bgColor = "-fx-background-color: rgb(226, 232, 240);";
            color= Color.BLACK;
        }
        column.setStyle("-fx-font-size:18px;\n"+bgColor);
        column.setTextFill(color);

        return column;
    }

  /// unused methods
    private static void   changeButtonsColor (Button btn1 ,Button btn2 ,int current){
        if(current==1){
            btn1.setStyle("-fx-background-color:rgb(170, 116, 170);");
            btn1.setTextFill(Color.WHITE);

            btn2.setStyle("");
            btn2.setTextFill(Color.BLACK);


        }
        if(current==2){
            btn2.setStyle("-fx-background-color:rgb(170, 116, 170);");
            btn2.setTextFill(Color.WHITE);

            btn1.setStyle("");
            btn1.setTextFill(Color.BLACK);

        }


    }

    private void fileSaver (File fileDestination,String fileContent,boolean appendFile){
        try {
            FileWriter fileWriter = null ;
            if(appendFile)
                fileWriter = new FileWriter(fileDestination ,true);
            if(!appendFile)
                fileWriter = new FileWriter(fileDestination ,false);
            if(fileDestination.length()!=0)
                fileContent =  "\n" + fileContent;


            fileWriter.write(fileContent);
            fileWriter.close();
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }

    }

    private String fileReader (File fileDesination ,boolean isSearch,String id ){

        String resultString =  "";
        String read ="";
        boolean studentFound = false;
        try{
            BufferedReader fileReader =  new BufferedReader( new FileReader(fileDesination));

            if(isSearch){
                read =  fileReader.readLine();
                if(read.startsWith(id)){
                    studentFound =  true;
                    resultString =  read;

                }
                while(read!=null){
                    if(read.contains(id)) {
                        studentFound =  true;
                        resultString =  read;
                    }
                    read =  fileReader.readLine();
                }
                if(!studentFound) resultString =  null;

            }

            if(!isSearch){
                read =  fileReader.readLine();
                while(read!=null){
                    resultString += read + "|";
                    read =  fileReader.readLine();
                }
            }

        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
        return  resultString;

    }

    public VBox studentListTAble (Stage stage){
        VBox list =  new VBox();
        TableView<Student> table = new TableView<Student>();
        Label listHeader = new Label("Students List");
        listHeader.setFont(new Font("Arial", 20));
        listHeader.setTextFill(Color.PURPLE);
        listHeader.setStyle("" +
                "-fx-opacity:1;" +
                "-fx-font-size:20px;" +
                "-fx-font-weight:bold;"
        );
//        table.setEditable(true);



        TableColumn firstNameCol = new TableColumn("ID");
        firstNameCol.setMinWidth(100);
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<Student, String>("id"));



        TableColumn lastNameCol = new TableColumn("USERNAME");
        lastNameCol.setMinWidth(100);
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<Student, String>("username"));

        TableColumn emailCol = new TableColumn("GPA");
        emailCol.setMinWidth(200);
        emailCol.setCellValueFactory(
                new PropertyValueFactory<Student, Double>("gpa"));



        ObservableList<Student> data =
                FXCollections.observableArrayList(new Student(),
                        new Student("natty","natty7",3));

        table.setItems(data);
        table.getColumns().addAll(firstNameCol, lastNameCol, emailCol);
        VBox vbox = new VBox();
        vbox.setSpacing(5);
        list.setPadding(new Insets(10, 0, 0, 10));
        list.getChildren().addAll(listHeader,table);
        return list;
    }

}
class Student {
   String studId;
   String username;
   double gpa;

    Student(){
        this.studId =  ("778");
        this.username = ("username");
        this.gpa = (3);
    }
    Student(String studId , String username,double gpa){
        this.studId=  studId;
        this.username =username;
        this.gpa = gpa;
    }

  }
