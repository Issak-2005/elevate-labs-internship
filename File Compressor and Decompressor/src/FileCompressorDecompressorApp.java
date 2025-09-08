import javafx.application.Application;
import javafx.geometry.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.File;
import java.util.List;

public class FileCompressorDecompressorApp extends Application 
{
    private TextArea statusArea = new TextArea();

    public static void main(String[] args) 
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) 
    {
        primaryStage.setTitle("File Compressor & Decompressor");

        Button compressBtn = new Button("Compress Files");
        Button decompressBtn = new Button("Decompress ZIP");

        compressBtn.setOnAction(e -> compressFiles(primaryStage));
        decompressBtn.setOnAction(e -> decompressFiles(primaryStage));

        HBox root0 = new HBox(10, compressBtn, decompressBtn);
        VBox root = new VBox(10, root0, statusArea);
        root.setPadding(new Insets(10));

        primaryStage.setScene(new Scene(root, 400, 250));
        primaryStage.show();
    }


    private void compressFiles(Window owner) 
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Files to Compress");
        List<File> files = fileChooser.showOpenMultipleDialog(owner);

        if (files == null || files.isEmpty()) {
            updateStatus("No files selected!");
            return;
        }

        FileChooser saveChooser = new FileChooser();
        saveChooser.setTitle("Save ZIP File");
        saveChooser.setInitialFileName("archive.zip");
        File zipFile = saveChooser.showSaveDialog(owner);

        if (zipFile == null) 
        {
            updateStatus("No save location chosen!");
            return;
        }

        try {
            FileCompressor.compressFiles(files, zipFile);
            updateStatus("Compression complete! Saved to: " + zipFile.getAbsolutePath());
        } catch (Exception ex) {
            updateStatus("Error: " + ex.getMessage());
        }
    }

    private void decompressFiles(Window owner) 
    {
        FileChooser zipChooser = new FileChooser();
        zipChooser.setTitle("Select ZIP File to Decompress");
        File zipFile = zipChooser.showOpenDialog(owner);

        if (zipFile == null) 
        {
            updateStatus("No ZIP file selected!");
            return;
        }

        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setTitle("Select Destination Directory");
        File destDir = dirChooser.showDialog(owner);

        if (destDir == null) 
        {
            updateStatus("No destination selected!");
            return;
        }

        try {
            FileDecompressor.decompressFiles(zipFile, destDir);
            updateStatus("Decompression complete!");
        } catch (Exception ex) {
            updateStatus("Error: " + ex.getMessage());
        }
    }

    private void updateStatus(String msg) 
    {
        statusArea.appendText(msg + "\n");
    }
}