
public class Main {
    public static void main(String[] args) {
        try {
            
            System.out.println("Creating database and table Musicians...");
            DatabaseConnector.executeUpdate("CREATE DATABASE IF NOT EXISTS Music_Store", "Musicians");
            DatabaseConnector.executeUpdate("USE Music_Store", "Musicians");
            DatabaseConnector.executeUpdate("CREATE TABLE Musicians(ssn CHAR(10), name CHAR(30) NOT NULL, PRIMARY KEY(ssn))", "Musicians");
            System.out.println("Musicians table creation completed.");

            System.out.println("Creating table Song_Appears...");
            DatabaseConnector.executeUpdate("CREATE TABLE Song_Appears(songId INTEGER, author CHAR(30), title CHAR(30), albumIdentifier INTEGER NOT NULL, PRIMARY KEY (songId), FOREIGN KEY (albumIdentifier) REFERENCES Album_Producer (albumIdentifier))", "Song_Appears");
            System.out.println("Song_Appears table creation completed.");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}