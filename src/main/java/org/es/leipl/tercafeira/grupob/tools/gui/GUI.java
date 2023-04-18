package org.es.leipl.tercafeira.grupob.tools.gui;

import javax.swing.UIManager;
import com.formdev.flatlaf.FlatDarculaLaf;
import org.es.leipl.tercafeira.grupob.pojos.Horario;
import org.es.leipl.tercafeira.grupob.tools.FileUpload;
import org.es.leipl.tercafeira.grupob.tools.ImportFiles;
import org.json.simple.JSONArray;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {
    private static Horario horario;
    public static void createGUI() {

        try {
            UIManager.setLookAndFeel( new FlatDarculaLaf() );
        // create a new JFrame object
        JFrame frame = new JFrame();

        // create two JButton objects
        JButton button1 = new JButton("Upload Local");
        JButton button2 = new JButton("Upload Remoto");
        JButton button3 = new JButton("Convert to JSON");

        button1.setBounds(50,50,150,40);
        button2.setBounds(200,50,150,40);
        button3.setBounds(350,50,150,40);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileUpload fu = new FileUpload(frame);
                fu.uploadLocal();
                horario = fu.getHorario();
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FileUpload(frame).uploadUrl();
            }
        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (horario != null){
                    JSONArray json = ImportFiles.Horario2Json(horario);
                    ImportFiles.saveJSONtoFile(json);
                    JOptionPane.showMessageDialog(frame, "Ficheiro JSON criado em sistema!", "Success", JOptionPane.INFORMATION_MESSAGE);

                }
            }
        });

        // add the buttons to the JFrame object
        frame.add(button1);
        frame.add(button2);
        frame.add(button3);

        // set the layout of the JFrame object
        frame.setLayout(null);

        // set the preferred size of the JFrame object
        frame.setSize(600, 500);

        // set the title of the JFrame object
        frame.setTitle("Upload de Horário");

        // set the default close operation of the JFrame object
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);

        // make the JFrame object visible
        frame.setVisible(true);

        } catch( Exception ex ) {
            System.err.println( "Failed to initialize theme. Using fallback." );
        }
    }
}
