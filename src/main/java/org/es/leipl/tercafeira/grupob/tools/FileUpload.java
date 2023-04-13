package org.es.leipl.tercafeira.grupob.tools;

import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class FileUpload {
    JFrame parent;
    public FileUpload(JFrame parent) {
        this.parent = parent;
    }

    public File uploadLocal() {
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jfc.setDialogTitle("Escolha o ficheiro para fazer upload");
        jfc.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV ou JSON", "csv", "JSON");
        jfc.addChoosableFileFilter(filter);

        int returnValue = jfc.showOpenDialog(parent);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            System.out.println(selectedFile.getAbsolutePath());
            System.out.println("Present Project Directory : "+ System.getProperty("user.dir"));

            File archiveFile = new File(System.getProperty("user.dir") + "/arquivo/Horario.csv");

            try {
                FileUtils.copyFile(selectedFile,archiveFile);
            } catch (IOException e) {
                System.err.format("IOException: ", e);
            }
            System.out.println("File uploaded -> "+ selectedFile);
            return selectedFile;
        }
        return null;
    }

    public void downloadUrl() {
        JTextField urlField;
        JFrame frame = new JFrame("CSV Downloader");

        JPanel urlPanel = new JPanel(new FlowLayout());
        urlField = new JTextField(30);
        urlPanel.add(urlField);

        JButton downloadButton = new JButton("Download");
        downloadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                downloadCSV(urlField);
            }
        });

        urlPanel.add(downloadButton);
        frame.getContentPane().add(urlPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLocation(frame.getX() - 200, frame.getY());
        frame.pack();
        frame.setVisible(true);
    }

    private void downloadCSV(JTextField urlField) {
        String url = urlField.getText().trim();

        try {
            URL csvUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) csvUrl.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                String fileName = url.substring(url.lastIndexOf("/") + 1);
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Save CSV File");
                fileChooser.setSelectedFile(new File(fileName));
                int userSelection = fileChooser.showSaveDialog(null);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File archiveFile = fileChooser.getSelectedFile();
                    InputStream inputStream = connection.getInputStream();
                    FileOutputStream outputStream = new FileOutputStream(archiveFile);

                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }

                    outputStream.close();
                    inputStream.close();

                    JOptionPane.showMessageDialog(null, "CSV file downloaded successfully.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Failed to download CSV file. Response code: " + responseCode);
            }
        } catch (MalformedURLException e) {
            JOptionPane.showMessageDialog(null, "Invalid URL: " + url);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error downloading CSV file: " + e.getMessage());
        }
    }
}