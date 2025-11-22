/*
 * Archivo principal para ejecutar la aplicación
 */
package core;

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.UIManager;
import core.view.MegaferiaFrame;

/**
 * Clase principal que inicia la aplicación Megaferia
 * @author edangulo
 */
public class Main {
    
    public static void main(String[] args) {
        System.setProperty("flatlaf.useNativeLibrary", "false");
        
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MegaferiaFrame().setVisible(true);
            }
        });
    }
}
