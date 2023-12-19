import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class music_store_gui extends Application {
    private TextArea resultArea;
    private TextField securityCodeField;
    private TextField newArtistNameField;
    private TextField newArtistSSNField;
    private TextField deleteArtistSSNField;
    private TextField updateArtistSSNField;
    private TextField updateArtistNameField;

    private final String SECURITY_CODE = "cs430@SIUC";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Music Store Database Search");

        BorderPane borderPane = new BorderPane();

        TextField searchField = new TextField();
        searchField.setPromptText("Enter search criteria");

        Button searchButton = new Button("Search");
        searchButton.setOnAction(e -> searchDatabase(searchField.getText()));

        resultArea = new TextArea();
        resultArea.setEditable(false);

        securityCodeField = new TextField();
        securityCodeField.setPromptText("Enter security code");

        newArtistNameField = new TextField();
        newArtistNameField.setPromptText("Enter new artist name");

        newArtistSSNField = new TextField();
        newArtistSSNField.setPromptText("Enter new artist SSN");

        deleteArtistSSNField = new TextField();
        deleteArtistSSNField.setPromptText("Enter artist SSN to delete");

        updateArtistSSNField = new TextField();
        updateArtistSSNField.setPromptText("Enter artist SSN to update");

        updateArtistNameField = new TextField();
        updateArtistNameField.setPromptText("Enter new name for the artist");

        Button addArtistButton = new Button("Add Artist");
        addArtistButton.setOnAction(e -> addNewArtist());

        Button deleteArtistButton = new Button("Delete Artist");
        deleteArtistButton.setOnAction(e -> deleteArtist());

        Button updateArtistButton = new Button("Update Artist");
        updateArtistButton.setOnAction(e -> updateArtist());

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(searchField, searchButton, resultArea);

        VBox securityVBox = new VBox(10);
        securityVBox.setPadding(new Insets(10, 10, 10, 10));
        securityVBox.getChildren().addAll(
                securityCodeField,
                newArtistNameField,
                newArtistSSNField,
                addArtistButton,
                deleteArtistSSNField,
                deleteArtistButton,
                updateArtistSSNField,
                updateArtistNameField,
                updateArtistButton
        );

        borderPane.setLeft(vbox);
        borderPane.setRight(securityVBox);

        Scene scene = new Scene(borderPane, 700, 400);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void searchDatabase(String searchTerm) {
        try {
            Connection connection = DatabaseConnector.getConnection();
            String query = "SELECT 'Musician' AS type, ssn, name, '' AS artist_name, '' AS id FROM Musicians WHERE name LIKE ? " +
        "UNION SELECT 'Album' AS type, albumIdentifier AS id, title, Musicians.name AS artist_name, Album_Producer.ssn " +
        "FROM Album_Producer INNER JOIN Musicians ON Album_Producer.ssn = Musicians.ssn " +
        "WHERE Album_Producer.title LIKE ? " +
        "UNION SELECT 'Album' AS type, albumIdentifier AS id, title, Musicians.name AS artist_name, Album_Producer.ssn " +
        "FROM Album_Producer INNER JOIN Musicians ON Album_Producer.ssn = Musicians.ssn " +
        "WHERE Musicians.name LIKE ? " +
        "UNION SELECT 'Song' AS type, songId AS id, title, Musicians.name AS artist_name, Musicians.ssn " +
        "FROM Song_Appears INNER JOIN Musicians ON Song_Appears.author = Musicians.name " +
        "WHERE (Song_Appears.title LIKE ? OR Musicians.name LIKE ?) AND Song_Appears.songId IS NOT NULL";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                String currentMusicianSSN = null;
                for (int i = 1; i <= 5; i++) {
                    preparedStatement.setString(i, "%" + searchTerm.trim() + "%");
                }
    
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    StringBuilder resultText = new StringBuilder();
                    while (resultSet.next()) {
                        String type = resultSet.getString("type");
                        resultText.append("Type: ").append(type).append("\n");
                        
                        if ("Musician".equalsIgnoreCase(type)) {
                            currentMusicianSSN = resultSet.getString("ssn");
                            resultText.append("SSN: ").append(currentMusicianSSN).append("\t")
                                .append("Name: ").append(resultSet.getString("name")).append("\n");
                             } else if ("Album".equalsIgnoreCase(type)) {
                                String albumId = resultSet.getString(2); 
                                String title = resultSet.getString(3); 
                                resultText.append("Album ID: ").append(albumId).append("\t")
                                        .append("Title: ").append(title).append("\n");
                            } else if ("Song".equalsIgnoreCase(type)) {
                             resultText.append("Song ID: ").append(resultSet.getString(2)).append("\t")
                                .append("Title: ").append(resultSet.getString(3)).append("\n");
                            }
                        resultText.append("\n");
                    }
                    
                    resultArea.setText(resultText.toString());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            resultArea.setText("Error searching the database: " + e.getMessage());
        }
    }
    private void addNewArtist() {
        String securityCode = securityCodeField.getText();
        String artistName = newArtistNameField.getText();
        String artistSSN = newArtistSSNField.getText();

        if (SECURITY_CODE.equals(securityCode)) {
            try {
                Connection connection = DatabaseConnector.getConnection();
                String insertQuery = "INSERT INTO Musicians (name, ssn) VALUES (?, ?)";

                try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                    preparedStatement.setString(1, artistName);
                    preparedStatement.setString(2, artistSSN);

                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        resultArea.setText("New Artist Added Successfully");
                    } else {
                        resultArea.setText("Failed to Add New Artist");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                resultArea.setText("Error Adding New Artist: " + e.getMessage());
            }
        } else {
            resultArea.setText("Invalid security code. Adding new artist not allowed.");
        }
    }
    private void deleteArtist() {
        String securityCode = securityCodeField.getText();
        String artistSSNToDelete = deleteArtistSSNField.getText();

        if (SECURITY_CODE.equals(securityCode)) {
            try {
                Connection connection = DatabaseConnector.getConnection();
                String deleteQuery = "DELETE FROM Musicians WHERE ssn = ?";

                try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                    preparedStatement.setString(1, artistSSNToDelete);

                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        resultArea.setText("Artist Deleted Successfully");
                    } else {
                        resultArea.setText("No Artist Deleted. Check SSN.");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                resultArea.setText("Error Deleting Artist: " + e.getMessage());
            }
        } else {
            resultArea.setText("Invalid security code. Deleting artist not allowed.");
        }
    }
    private void updateArtist() {
        String securityCode = securityCodeField.getText();
        String artistSSNToUpdate = updateArtistSSNField.getText();
        String newArtistName = updateArtistNameField.getText();

        if (SECURITY_CODE.equals(securityCode)) {
            try {
                Connection connection = DatabaseConnector.getConnection();
                String updateQuery = "UPDATE Musicians SET name = ? WHERE ssn = ?";

                try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                    preparedStatement.setString(1, newArtistName);
                    preparedStatement.setString(2, artistSSNToUpdate);

                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        resultArea.setText("Artist Updated Successfully");
                    } else {
                        resultArea.setText("No Artist Updated. Check SSN.");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                resultArea.setText("Error Updating Artist: " + e.getMessage());
            }
        } else {
            resultArea.setText("Invalid security code. Updating artist not allowed.");
        }
    }
}