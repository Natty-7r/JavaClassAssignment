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

import java.sql.SQLException;

public class SignupPage {
    String username =  "natty";
    String password =  "123";

    public VBox SignupForm(Stage stage){
        VBox form =  new VBox();
        form.setAlignment(Pos.CENTER);
        form.setPadding(new Insets(0,0,0,0));
        form.setSpacing(10);

        HBox errorContainer = new  HBox();
        errorContainer.setAlignment(Pos.CENTER);
        Label errorMessage =  new Label("Signup Failed !");
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
            Label header =  new Label("Signup Here ");
            header.setTextFill(Color.rgb(38, 38, 196));
            header.setPadding(new Insets(5,30,5,30));

            header.setStyle("" +
                    "-fx-font-size:24px;" +
                    "-fx-font-weight:bold;"
            );
            headerContainer.getChildren().add(header);

        }


        // email logic
        HBox emailContainer = new HBox();
        TextField emailInput = new TextField();

        {

            Label emailLabel =  new Label("Email: ");
            emailLabel.setPrefWidth(180);
            emailLabel.setStyle("" +
                    "-fx-font-size:16px;" +
                    "-fx-font-weight:bold;"
            );
            emailInput.setFont(Font.font(18));
            emailInput.setPrefSize(250, 20);


            emailContainer.setAlignment(Pos.CENTER);
            emailContainer.setSpacing(5);
            emailContainer.getChildren().addAll(emailLabel,emailInput);

        }

        // username logic
        HBox usernameContainer = new HBox();
        TextField usernameInput = new TextField();

        {

            Label usernameLabel =  new Label("USERNAME: ");
            usernameLabel.setPrefWidth(180);
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
            passwordLabel.setPrefWidth(180);
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
        HBox confirmPasswordContainer = new HBox();
        TextField confirmPasswordInput = new TextField();
        // password logic
        {
            Label confirmPasswordLabel = new Label("CONFIRM PASSWORD: ");
            confirmPasswordLabel.setPrefWidth(180);
            confirmPasswordLabel.setStyle("" +
                    "-fx-font-size:16px;" +
                    "-fx-font-weight:bold;"
            );
            confirmPasswordInput.setFont(Font.font(18));
            confirmPasswordInput.setPrefSize(250, 20);
            confirmPasswordContainer.setAlignment(Pos.CENTER);
            confirmPasswordContainer.setSpacing(5);
            confirmPasswordContainer.getChildren().addAll(confirmPasswordLabel, confirmPasswordInput);
        }

        HBox buttonContainer =  new HBox();
        // buttons logic
        {
            Button signupBtn = new Button("Sign Up ");
            signupBtn.setPadding(new Insets(3, 25, 3, 25));
            signupBtn.setTextFill(Color.rgb(255, 255, 255));
            signupBtn.setStyle("" +
                    "-fx-font-size:18px;" +
                    "-fx-background-color:rgb(38, 38, 196);"
            );

            signupBtn.setOnAction(e->{

                String inputEmail =  emailInput.getText().trim();
                String inputUsername = usernameInput.getText().trim();
                String inputPassword =  passwordInput.getText().trim();
                String confirmedInput =  confirmPasswordInput.getText().trim();

                if(inputEmail.isEmpty()|| inputUsername.isEmpty() || inputPassword.isEmpty()||confirmedInput.isEmpty()){
                    errorMessage.setStyle("" +
                            "-fx-font-size:18px;" +
                            "-fx-letter-spacing:1px;" +
                            "-fx-opacity:1;"
                    );
                    errorMessage.setText("Input Field Can't be Empty !");

                }

                else if(!inputPassword.equals(confirmedInput)){
                    errorMessage.setStyle("" +
                            "-fx-font-size:18px;" +
                            "-fx-letter-spacing:1px;" +
                            "-fx-opacity:1;"
                    );
                    errorMessage.setText("Password Mismatch ! ");

                }
                else
                    {
                        try{
                        String  signupMessage = Database.createUser(inputEmail,inputUsername,inputPassword);
                        if(signupMessage.equals("user created")){
                            HelloApplication.loggedUsername =  inputUsername.substring(0,1).toUpperCase()+inputUsername.substring(1);
                            HelloApplication.setStageContent(stage,true,"notepad",null);
                        }  else{
                            errorMessage.setText(signupMessage);
                            errorMessage.setStyle("" +
                                    "-fx-font-size:18px;" +
                                    "-fx-letter-spacing:1px;" +
                                    "-fx-opacity:1;"
                            );
                        }
                        }
                        catch (SQLException exception){
                            System.out.println(exception);
                        }
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
            Hyperlink gotoLogin =  new Hyperlink(" Have  Account ?");
            gotoLogin.setOnAction(e->{
                HelloApplication.setStageContent(stage,false,"login",null);
            });

            buttonContainer.setSpacing(20);
            buttonContainer.setPadding(new Insets(10, 40, 0, 70));
            buttonContainer.setAlignment(Pos.CENTER_RIGHT);
            buttonContainer.getChildren().addAll(signupBtn, gotoLogin);
        }


        form.getChildren().addAll(errorContainer,headerContainer,emailContainer,usernameContainer,passwordContainer,confirmPasswordContainer,buttonContainer);

        form.setPrefWidth(550);
        form.setPrefHeight(400);
        return form;
    }
}


