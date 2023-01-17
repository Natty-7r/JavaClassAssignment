package com.example.learningjavfx;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class LoginPage {
     String loggedUsername = "";

    public VBox loginForm(Stage stage){
        VBox form =  new VBox();
        form.setAlignment(Pos.BASELINE_CENTER);
        form.setPadding(new Insets(50,0,0,0));
        form.setSpacing(10);

        HBox errorContainer = new  HBox();
        errorContainer.setAlignment(Pos.CENTER);
        Label errorMessage =  new Label("Invalid Username or Password !");
        errorMessage.setPadding(new Insets(0,0,0,20));
        errorMessage.setStyle("" +
                "-fx-font-size:18px;" +
                "-fx-letter-spacing:1px;" +
                "-fx-opacity:0;"
        );
        errorMessage.setTextFill(Color.RED);
        errorContainer.getChildren().add(errorMessage);


        HBox headerContainer =  new HBox();
        // header logic
        {
            HBox.setMargin(headerContainer,new Insets(-10,0,10,0));
            headerContainer.setAlignment(Pos.CENTER);
            Label header =  new Label("Login Here ");
            header.setTextFill(Color.rgb(38, 38, 196));
            header.setPadding(new Insets(5,30,5,30));

            header.setStyle("" +
                    "-fx-font-size:24px;" +
                    "-fx-font-weight:bold;"
            );
            headerContainer.getChildren().add(header);

        }

        // username logic
        HBox usernameContainer = new HBox();
        TextField usernameInput = new TextField();

        {

            Label usernameLabel =  new Label("USERNAME: ");
            usernameLabel.setStyle("" +
                    "-fx-font-size:16px;" +
                    "-fx-font-weight:bold;"
            );
            usernameInput.setFont(Font.font(18));
            usernameInput.setPrefSize(250, 20);


            usernameContainer.setAlignment(Pos.CENTER);
            usernameContainer.setSpacing(5);
            usernameContainer.getChildren().addAll(usernameLabel,usernameInput);

        }

        HBox passwordContainer = new HBox();
        TextField passwordInput = new TextField();
        // password logic
        {
            Label passwordLabel = new Label("PASSWORD: ");
            passwordLabel.setStyle("" +
                    "-fx-font-size:16px;" +
                    "-fx-font-weight:bold;"
            );
            passwordInput.setFont(Font.font(18));
            passwordInput.setPrefSize(250, 20);
            passwordContainer.setAlignment(Pos.CENTER);
            passwordContainer.setSpacing(5);
            passwordContainer.getChildren().addAll(passwordLabel, passwordInput);
        }

        HBox buttonContainer =  new HBox();
        // buttons logic
        {
            Button loginBtn = new Button("Login ");
            loginBtn.setPadding(new Insets(3, 25, 3, 25));
            loginBtn.setTextFill(Color.rgb(255, 255, 255));
            loginBtn.setStyle("" +
                    "-fx-font-size:18px;" +
                    "-fx-background-color:rgb(38, 38, 196);"

            );

            loginBtn.setOnAction(e->{
                String inputUsername = usernameInput.getText().trim();
                String inputPassword =  passwordInput.getText().trim();
                String  loginMessage=  Database.logIn(inputUsername,inputPassword);

                if(loginMessage.equals("loggedIn"))
                 {
                     HelloApplication.loggedUsername =  inputUsername.substring(0,1).toUpperCase()+inputUsername.substring(1);
                    HelloApplication.setStageContent(stage,true,"notepad",null);
                }
                else{
                    errorMessage.setText(loginMessage);
                    errorMessage.setStyle("" +
                            "-fx-font-size:18px;" +
                            "-fx-letter-spacing:1px;" +
                            "-fx-opacity:1;"
                    );
                }
            });

            Button cancelBtn = new Button("Cancel");
            cancelBtn.setPadding(new Insets(3, 25, 3, 25));
            cancelBtn.setTextFill(Color.rgb(255, 255, 255));
            cancelBtn.setStyle("" +
                    "-fx-font-size:18px;" +
                    "-fx-background-color:rgb(255, 0, 0);"
            );
            // cancel button logic
            cancelBtn.setOnAction(e->{
                stage.hide();
            });
            Hyperlink gotoSignup =  new Hyperlink("Don't Have Account ? ");
            gotoSignup.setStyle("-fx-font-size:14px;" );
            gotoSignup.setOnAction(e->{
                HelloApplication.setStageContent(stage,false,"signup",null);
            });


            buttonContainer.setSpacing(20);
            buttonContainer.setPadding(new Insets(10, 0, 0, 70));
            buttonContainer.setAlignment(Pos.CENTER);
            buttonContainer.getChildren().addAll(loginBtn,gotoSignup);
        }

        form.getChildren().addAll(errorContainer,headerContainer,usernameContainer,passwordContainer,buttonContainer);

        form.setPrefWidth(550);
        form.setPrefHeight(400);
        return form;
    }
}
