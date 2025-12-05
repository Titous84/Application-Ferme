package com.ferme.bertbeach;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Classe principale JavaFX de l'application.
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        //FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("vues/parcelles-liste.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("vues/parcelles-liste.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);

        stage.setTitle("La Ferme de St-Bert-Beach");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

