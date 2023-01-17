package com.example.learningjavfx;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.sql.SQLException;

public class HelloApplication extends Application {
    static  int currentButton =  1;
    static String loggedUsername ="SomeOne";

    static String studentSubPage = "search";

    @Override
    public void start(Stage stage) throws SQLException {

        Database.getConnection();
        boolean thereIsUser =  Database.isThereUser();
        if(thereIsUser)
      setStageContent(stage,false,"login",null);
        else
      setStageContent(stage,false,"signup",null);

    }

    private static VBox forReading (){
        VBox w =  new VBox();
        try {

//        FileInputStream in  = new FileInputStream("C:\\Users\\NATTY-7\\Pictures\\picForNode\\cat_reading.jpg");
//        Image image =  new Image(in);
//
//            ImageView imageView =  new ImageView(image);
//            imageView.setFitHeight(20);
//            imageView.setFitWidth(30);
//            Button btn =  new Button();
//
//            btn.setGraphic(imageView);
////            btn.setPrefHeight(20);
         ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList("hh","jj"));
         cb.getSelectionModel().selectFirst();


            Button b = new Button("j");

            TextField passwordField = new TextField();
            passwordField.setPromptText("Your password");
            ScrollBar sc = new ScrollBar();
            sc.setMin(0);
            sc.setMax(100);
            sc.setValue(50);
            sc.setOrientation(Orientation.VERTICAL);


            //A checkbox without a caption
            CheckBox cb1 = new CheckBox();
//A checkbox with a string caption
            CheckBox cb2 = new CheckBox("Second");

            cb1.setText("First");
            cb1.setSelected(true);


            final ToggleGroup group = new ToggleGroup();

            RadioButton rb1 = new RadioButton("Home");
            rb1.setToggleGroup(group);
            rb1.setSelected(true);

            RadioButton rb2 = new RadioButton("Calendar");
            rb2.setToggleGroup(group);

            RadioButton rb3 = new RadioButton("Contacts");
            rb3.setToggleGroup(group);
            Hyperlink link = new Hyperlink();
            link.setText("http://example.com");
            link.setOnAction(( e)-> {
                System.out.println("This link is clicked");
            });
            passwordField.requestFocus();

        w.getChildren().addAll(cb,b,passwordField);


        sc.setOnDragDone(e->{
        });
            b.setOnAction(e->{
                System.out.println(group.getSelectedToggle().getUserData());
                System.out.println(rb2.isSelected());

            });
        }
        catch (Exception e){}
        return w;
    }
    private static  void  setStage(Stage stage){

        stage.setHeight(450);
        stage.setMinHeight(350);
        stage.setMaxHeight(500);
        stage.setResizable(false);

    }
    public static HBox navigationBar(Stage stage){
        HBox navigationBar =  new HBox();
        navigationBar.setAlignment(Pos.TOP_LEFT);
        navigationBar.setPadding(new Insets(5));
        navigationBar.setSpacing(2);
        navigationBar.setStyle("" +
                "-fx-background-color:purple;" +
                ""
        );

        Button  countBtn  =  new Button("Folder Counter page ");

        Button notePad =  new Button("Notepad page");

        Button studentBtn =  new Button("Student page");
        changeButtonsColor(notePad,countBtn,studentBtn,currentButton);

        Button logoutBtn = new Button("Logout");

        { // buttons logic
            notePad.setOnAction(e->{
              currentButton = 1;
              changeButtonsColor(notePad,countBtn,studentBtn,currentButton);
              setStageContent(stage,true,"notepad",null);
            });

            countBtn.setOnAction(e->{
                currentButton = 2;
                changeButtonsColor(notePad,countBtn,studentBtn,currentButton);
                setStageContent(stage ,true,"folderCounter",null);

            });

            studentBtn.setOnAction(e->{
                currentButton = 3;
                changeButtonsColor(notePad,countBtn,studentBtn,currentButton);
                setStageContent(stage,true,"studentData",studentSubPage);

            });
            logoutBtn.setOnAction(e->{
                setStageContent(stage,false,"login",null);
            });

        }
        HBox logoutContainer =  new HBox();
        logoutContainer.getChildren().add(logoutBtn);
        HBox.setMargin(logoutContainer,new Insets(0,0,0,160));
        navigationBar.getChildren().addAll(notePad,countBtn,studentBtn,logoutContainer);
        return navigationBar;
    }

    public static HBox navigationMenuBar(Stage stage){
        HBox navigationBar =  new HBox();
        MenuBar navigationMenuBar =  new MenuBar();
        navigationBar.setAlignment(Pos.TOP_LEFT);
        navigationBar.setPadding(new Insets(5));
        navigationBar.setSpacing(2);
        navigationBar.setStyle("" +
                "-fx-background-color:purple;" +
                ""
        );

        Menu countMenu =  new Menu("Folder counter");
        MenuItem countBtn =  new MenuItem("Folder Counter ");
        countMenu.getItems().add(countBtn);


        Menu notepadMenu =  new Menu("Notepad");
        MenuItem notepadBtn =  new MenuItem("Notepad ");
        notepadMenu.getItems().add(notepadBtn);

        Menu studentMenu =  new Menu("student Data");
        MenuItem studentBtn =  new MenuItem("Student Data ");
        studentMenu.getItems().add(studentBtn);

           Menu logoutMenu =  new Menu("logout");

        MenuItem logoutBtn =  new MenuItem("Logout ");
        logoutMenu.getItems().add(logoutBtn);



//        Button  countBtn  =  new Button("Folder Counter page ");

        Button notePad =  new Button("Notepad page");

//        Button studentBtn =  new Button("Student page");
//        changeButtonsColor(notePad,countBtn,studentBtn,currentButton);

//        Button logoutBtn = new Button("Logout");

        { // buttons logic
            notePad.setOnAction(e->{
              currentButton = 1;
//              changeButtonsColor(notePad,countBtn,studentBtn,currentButton);
              setStageContent(stage,true,"notepad",null);
            });

            countBtn.setOnAction(e->{
                currentButton = 2;
//                changeButtonsColor(notePad,countBtn,studentBtn,currentButton);
                setStageContent(stage ,true,"folderCounter",null);

            });

            studentBtn.setOnAction(e->{
                currentButton = 3;
//                changeButtonsColor(notePad,countBtn,studentBtn,currentButton);
                setStageContent(stage,true,"studentData",null);

            });
            logoutBtn.setOnAction(e->{
                setStageContent(stage,false,"login",null);
            });

        }
        HBox logoutContainer =  new HBox();
        HBox.setMargin(logoutContainer,new Insets(0,0,0,160));
        navigationMenuBar.getMenus().addAll(countMenu,notepadMenu,studentMenu,logoutMenu);
        navigationBar.getChildren().add(navigationMenuBar);
        return navigationBar;
    }
    private static void   changeButtonsColor (Button btn1 ,Button btn2 ,Button btn3 ,int current){
        if(current==1){
            btn1.setStyle("-fx-background-color:rgb(170, 116, 170);");
            btn1.setTextFill(Color.WHITE);

            btn2.setStyle("");
            btn2.setTextFill(Color.BLACK);
            btn3.setStyle("");
            btn3.setTextFill(Color.BLACK);

        }
        if(current==2){
            btn2.setStyle("-fx-background-color:rgb(170, 116, 170);");
            btn2.setTextFill(Color.WHITE);

            btn1.setStyle("");
            btn1.setTextFill(Color.BLACK);
            btn3.setStyle("");
            btn3.setTextFill(Color.BLACK);

        }
        if(current==3){
            btn3.setStyle("-fx-background-color:rgb(170, 116, 170);");
            btn3.setTextFill(Color.WHITE);

            btn2.setStyle("");
            btn2.setTextFill(Color.BLACK);
            btn1.setStyle("");
            btn1.setTextFill(Color.BLACK);

        }

    }
    public static   void setStageContent(Stage stage,boolean isLoggedIn,String pageName, String subPage){

        VBox wholePage =  new VBox();

        VBox page = new VBox();

        if(isLoggedIn){
                stage.setTitle(loggedUsername);
            if(pageName.equals("notepad")){
                NotepadPage notepadPage =  new NotepadPage();
                page =  notepadPage.notepad(stage);
            }
            if(pageName.equals("folderCounter")){
                FolderCounterPage folderCounterPage = new FolderCounterPage();
                page = folderCounterPage.folderCounter(stage);
            }
            if(pageName.equals("studentData")){
                StudentData studentData =  new StudentData();
                page =  studentData.setStudentContent(stage,subPage);
            }
                wholePage.getChildren().addAll(navigationBar(stage),page);
         }
       if(!isLoggedIn){

           if(pageName.equals("login")){
               LoginPage loginPage =  new LoginPage();
               page =  loginPage.loginForm(stage);
               stage.setTitle("LOGIN");


           }
           else if(pageName.equals("signup")){
               SignupPage signupPage= new SignupPage();
               page = signupPage.SignupForm(stage);
               stage.setTitle("SIGNUP");

           }

           wholePage.getChildren().addAll(page);

        }

        wholePage.setAlignment(Pos.TOP_CENTER);
        page.setAlignment(Pos.CENTER);

        Scene scene = new Scene(wholePage);
        stage.setScene(scene);
        setStage(stage);
        stage.show();

    }



    public static void main(String[] args) {
        launch();
    }
}