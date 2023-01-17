package com.example.learningjavfx;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

public class NotepadPage {
    boolean isNew = true; // checking if file is new while saving
    File fileChoosed ; // store file opened
    public  VBox notepad(Stage stage){
        VBox notepad =  new VBox();

        // text area for notepad
        VBox textContainer =  new VBox();
        textContainer.setPadding(new Insets(10,20,20,20));
        TextArea notepadTextArea =  new TextArea();
        notepadTextArea.setPrefHeight(230);
        textContainer.getChildren().add(notepadTextArea);

        // menu bar
        MenuBar menuBar =  new MenuBar();
        Menu fileMenu =  new Menu("File");
        fileMenu.setStyle("" +
                "-fx-font-size:16px;"
        );
         // open menu
        MenuItem open =  new MenuItem("Open");
        open.setOnAction(e->{
            onFileOpen(stage,notepadTextArea);
        });
        // save menu
        MenuItem save =  new MenuItem("Save");
        save.setOnAction(e->{
            onFileSave(stage,notepadTextArea,isNew,false);
        });
        // save as menu
        MenuItem saveAs =  new MenuItem("Save As");
        saveAs.setOnAction(e->{
            onFileSave(stage,notepadTextArea,isNew,true);

        });
        fileMenu.getItems().addAll(open ,save,saveAs);
        menuBar.getMenus().add(fileMenu);

        HBox errorContainer =  new HBox();
        errorContainer.setAlignment(Pos.CENTER);
        Label errorMessage =  new Label("The  File format of chooosen file should be .txt");
        errorMessage.setStyle("" +
                "-fx-font-size:16px;" +
                "-fx-opacity:0;"
        );
        errorContainer.getChildren().add(errorMessage);
        errorMessage.setTextFill(Color.RED);

        HBox buttonsWhole =  new HBox();
        // buttons logic
        {
            // save button
            Button saveBtn = new Button("Save");
            saveBtn.setPadding(new Insets(3, 20, 3, 20));
            saveBtn.setTextFill(Color.rgb(0, 0, 0));
            saveBtn.setStyle("" +
                    "-fx-font-size:14px;" +
                    "-fx-background-color:rgb(231, 221, 205);"
            );
            // save button logic
            saveBtn.setOnAction(e->{
                onFileSave(stage,notepadTextArea,isNew,false);
            });
             // cancel button
            Button cancelBtn = new Button("Cancel");
            cancelBtn.setPadding(new Insets(3, 15, 3, 15));
            cancelBtn.setTextFill(Color.rgb(0, 0, 0));
            cancelBtn.setStyle("" +
                    "-fx-font-size:14px;" +
                    "-fx-background-color:rgb(209, 200, 182);"
            );
            cancelBtn.setOnAction(e->{
                notepadTextArea.setText("");
            });
            HBox buttonContainer =  new HBox();
            buttonContainer.setAlignment(Pos.CENTER_RIGHT);

            buttonContainer.setSpacing(10);
            HBox.setMargin(buttonContainer ,new Insets(0,10,0,0));
            buttonContainer.setPadding(new Insets(0, 20, 0, 0));
            buttonContainer.setAlignment(Pos.CENTER);
            buttonContainer.getChildren().addAll(saveBtn, cancelBtn);
            buttonsWhole.setAlignment(Pos.CENTER_RIGHT);
            buttonsWhole.getChildren().add(buttonContainer);
        }

        notepad.getChildren().addAll(menuBar,errorContainer,textContainer,buttonsWhole);

        notepad.setPrefWidth(550);
        notepad.setPrefHeight(370);
        return notepad;
    }
    private    void  onFileSave(Stage stage ,TextArea notepadText,boolean isNew,boolean isSaveAs){
        File initialSaveDirectory =  new File("C:\\Users\\NATTY-7\\Desktop\\forJavaFx");
        FileChooser fileChooser =  new FileChooser();
        fileChooser.setInitialDirectory(initialSaveDirectory);
        FileChooser.ExtensionFilter extensionFilter =  new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);

        if(isNew){  // saving file for first time
            fileChooser.setTitle("Save As ");
            fileChooser.setInitialFileName("untitled");
            File fileSaved = fileChooser.showSaveDialog(stage);
            String fileContent = notepadText.getText();
            if (fileSaved != null) {
                fileSaver(fileSaved, fileContent, false);
            }
            if (fileSaved == null) {
                System.out.println("file has not been selected !");
            }

        }
        if(!isNew){
            if(isSaveAs){
                fileChooser.setTitle("Save As ");
                String fileNameWithDot =  fileChoosed.getName().split(".txt")[0];
                String fileName =  fileNameWithDot.substring(0,fileNameWithDot.length());
                fileChooser.setInitialFileName(fileName);
                File fileSaved = fileChooser.showSaveDialog(stage);
                String fileContent = notepadText.getText();
                if (fileSaved != null) {
                    fileSaver(fileSaved, fileContent, false);
                }
                if (fileSaved == null) {
                    System.out.println("file has not been selected !");
                }
            }
            if (fileChoosed != null) {
                fileSaver(fileChoosed, notepadText.getText(), false);
            }
            if (fileChoosed == null) {
                System.out.println("file has not been selected !");
            }

        }




        if(!isNew) {
            // saving file after editing
            String fileContent = notepadText.getText();
            fileSaver(fileChoosed, fileContent, false);

            System.out.println(fileChoosed.getName());

        }


    }
    private  void onFileOpen(Stage stage,TextArea notepadTextArea){
        File initialDirectory=  new File("C:\\Users\\NATTY-7\\Desktop\\forJavaFx");


        FileChooser fileChooser =  new FileChooser();
        fileChooser.setInitialDirectory(initialDirectory);
        FileChooser.ExtensionFilter extensionFilter =  new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);
        fileChooser.setTitle("Open");


        fileChoosed =   fileChooser.showOpenDialog(stage);
        if(fileChoosed.exists())
            isNew =false;
        String fileContent  =  fileReader(fileChoosed);
        notepadTextArea.setText(fileContent);
    }
    private    String fileReader(File file){
        String totalFileRead ="";
        String singleLineRead = "";
        try {
            FileReader fileReader =  new FileReader(file);
            BufferedReader bufferedReader =  new BufferedReader(fileReader);

            while ((singleLineRead=bufferedReader.readLine())!=null){
                totalFileRead += singleLineRead+"\n";
            }

        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return totalFileRead;
    }
    private void fileSaver (File fileDestination,String fileContent,boolean appendFile){
        try {
            FileWriter fileWriter  = new FileWriter(fileDestination);
//            if(appendFile)
//                fileWriter = new FileWriter(fileDestination ,true);
//            if(!appendFile)
//                fileWriter = new FileWriter(fileDestination ,false);
//            if(fileDestination.length()!=0)
//                fileContent =  "\n" + fileContent;
            fileWriter.write(fileContent);
            fileWriter.close();
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }

    }

}
