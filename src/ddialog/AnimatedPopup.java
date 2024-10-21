package ddialog;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.Timer;
import java.awt.BorderLayout;

public class AnimatedPopup {
    private static JDialog popup;
    private static Timer timer;

    public static void main(String[] args) {
        showPopup();

        // Exemple pour simuler une fermeture après 5 secondes (appel extérieur)
        new Timer(5000, e -> closePopup()).start();
    }

    public static void showPopup() {
        // Créer une JDialog non modale
        popup = new JDialog();
        popup.setTitle("Animation Example");
        popup.setSize(200, 100);
        popup.setLocationRelativeTo(null);
        popup.setLayout(new BorderLayout());
        popup.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        popup.setModalityType(JDialog.ModalityType.MODELESS);

        // Créer le JLabel pour l'animation
        JLabel label = new JLabel("label", JLabel.CENTER);
        popup.add(label, BorderLayout.CENTER);

        // Tableau pour les étapes de l'animation
        String[] animationSteps = {"label", "label.", "label..", "label..."};
        final int[] index = {0}; // Index pour suivre l'étape actuelle

        // Créer un Timer pour l'animation, mise à jour toutes les 500ms
        timer = new Timer(500, e -> {

            System.out.println('e');
            // Mettre à jour le texte du JLabel avec l'étape actuelle
            label.setText(animationSteps[index[0]]);
            // Passer à l'étape suivante
            index[0] = (index[0] + 1) % animationSteps.length;
        });

        // Démarrer le timer
        timer.start();

        // Afficher la popup
        popup.setVisible(true);
    }

    public static void closePopup() {
        // Arrêter le timer pour l'animation
        // if (timer != null) {
        //     timer.stop();
        // }

        // Fermer la JDialog
        if (popup != null) {
            popup.dispose();
        }
    }
}

