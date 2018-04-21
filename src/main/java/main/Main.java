package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Main {
    public static void main(String[] args) throws IOException {
        // Get URL from command line
        System.out.println("Input Wiki URL");
        BufferedReader br = new BufferedReader((new InputStreamReader(System.in)));
        String inputURL = br.readLine();

        // Connecting to database
        SQLiteJDBC.connectToDB();

        DBManager dbManager = new DBManager();
        ParseManager parseManager = new ParseManager(inputURL);

        // Executing requests to database
        dbManager.createArticlesTable();
        dbManager.insertArticleToDB(parseManager.getTitle());

        //Save crawled image to the database
        parseManager.getAndSaveImage();


    }

}
