package com.example.searchenginegui;

import com.example.searchenginegui.entity.Index5;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;

public class HelloApplication extends Application {
    //https://www.youtube.com/watch?v=cwJK_WpseKQ&list=PL6gx4Cwl9DGBzfXLWLSYVy8EbTdpGbUIG&index=10
    static final String filename = "src/main/java/com/example/searchenginegui/entity/WestburyLab.wikicorp.201004_100KB.txt";

    private static Index5 i;
    Stage window;
    Scene scene;
    Button button;
    TextArea resultArea;

    @Override
    public void start(Stage stage) throws IOException {
        window =stage;
        stage.setTitle("Search Engine");
        i = new Index5(filename);
        i.search3(); // make all returnItems
        i.ht.initHashTable();

        TextField input = new TextField();
        resultArea = new TextArea();  // Initialize the TextArea
        resultArea.setEditable(false);  // Make it non-editable
        button = new Button("search");
        button.setOnAction(e -> {
            resultArea.clear(); // remove text from last search
            StringBuilder result = new StringBuilder();
            String searchString = input.getText();
            Index5.ReturnItem returnItem = i.search4(searchString);
            Index5.DocItem tmp = returnItem.getStartDoc();
            while (tmp != null){
                result.append(tmp.documentName + " " + tmp.occ).append("\n");
                tmp = tmp.next;
            }

            // opdatér tekstboks resultArea
            // skriv result til  UI:
            resultArea.appendText("Search String: " + returnItem.searchstr + "\n");
            resultArea.appendText("Documents containing search string: " + searchString +"\n");
            Index5.DocItem currentDoc = returnItem.startDoc;
            while (currentDoc != null) {
                resultArea.appendText("Document: " + currentDoc.documentName +" "+ currentDoc.occ + "\n");
                currentDoc = currentDoc.next;
            }
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20,20,20,20));
        layout.getChildren().addAll(input, button, resultArea);

        scene = new Scene(layout, 320, 240);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
        System.out.println("Preprocessing " + filename);
        i = new Index5(filename);
        i.search3(); //make all ReturnItems
        i.ht.initHashTable();

        // start new thread for console input
        Thread consoleThread = new Thread(() -> {
            Scanner console = new Scanner(System.in);
            for (;;) {
                System.out.println("Input search string or type exit to stop");
                String searchstr = console.nextLine();
                if (searchstr.equals("exit")) {
                    break;
                }
                if (i.search(searchstr)) {
                    Index5.ReturnItem returnItem = i.ht.get(searchstr); //nyt hashtable
                    System.out.println("Searchtr: "+ searchstr);
                    System.out.println("Documents from hashtable: ");
                    Index5.DocItem currentDoc = returnItem.startDoc;
                    while (currentDoc != null){
                        System.out.println("Document: " + currentDoc.documentName + ": " + currentDoc.occ);
                        currentDoc = currentDoc.next;
                    }
                } else {
                    System.out.println(searchstr + " does not exist");
                }
            }
            console.close();
        });
        consoleThread.setDaemon(true);
        consoleThread.start();
    }
}