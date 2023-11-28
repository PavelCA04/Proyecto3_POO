
package Game;

import javax.swing.JLabel;
import javax.swing.JOptionPane;




public class GameWindow extends javax.swing.JFrame {

    /**
     * Creates new form GameWindow
     */
    GameController gamecontroller;
    
    public GameWindow(GameController gamecontroller) {
        initComponents();
        this.gamecontroller= gamecontroller;
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlGame = new javax.swing.JPanel();
        pnlStats = new javax.swing.JPanel();
        btnNextLevel = new javax.swing.JButton();
        ShotsFiredLabel = new javax.swing.JLabel();
        KilledTanksLabel = new javax.swing.JLabel();
        BonusLabel = new javax.swing.JLabel();
        LvlLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlGame.setMaximumSize(new java.awt.Dimension(1088, 832));
        pnlGame.setMinimumSize(new java.awt.Dimension(1088, 832));
        pnlGame.setPreferredSize(new java.awt.Dimension(1088, 832));

        javax.swing.GroupLayout pnlGameLayout = new javax.swing.GroupLayout(pnlGame);
        pnlGame.setLayout(pnlGameLayout);
        pnlGameLayout.setHorizontalGroup(
            pnlGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1088, Short.MAX_VALUE)
        );
        pnlGameLayout.setVerticalGroup(
            pnlGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        pnlStats.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnlStats.setMaximumSize(new java.awt.Dimension(282, 832));
        pnlStats.setMinimumSize(new java.awt.Dimension(282, 832));

        btnNextLevel.setText("Next Level");
        btnNextLevel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextLevelActionPerformed(evt);
            }
        });

        ShotsFiredLabel.setFont(new java.awt.Font("Source Sans Pro", 1, 18)); // NOI18N
        ShotsFiredLabel.setText("Shots Fired :");

        KilledTanksLabel.setFont(new java.awt.Font("Source Sans Pro", 1, 18)); // NOI18N
        KilledTanksLabel.setText("Killed Tanks :");

        BonusLabel.setFont(new java.awt.Font("Source Sans Pro", 1, 18)); // NOI18N
        BonusLabel.setText("Bonus Captured :");

        LvlLabel.setFont(new java.awt.Font("Source Sans Pro", 1, 24)); // NOI18N
        LvlLabel.setText("Level: 1");

        javax.swing.GroupLayout pnlStatsLayout = new javax.swing.GroupLayout(pnlStats);
        pnlStats.setLayout(pnlStatsLayout);
        pnlStatsLayout.setHorizontalGroup(
            pnlStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStatsLayout.createSequentialGroup()
                .addGroup(pnlStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlStatsLayout.createSequentialGroup()
                        .addGap(87, 87, 87)
                        .addComponent(btnNextLevel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlStatsLayout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addGroup(pnlStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(LvlLabel)
                            .addGroup(pnlStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(ShotsFiredLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(KilledTanksLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(BonusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)))))
                .addContainerGap(115, Short.MAX_VALUE))
        );
        pnlStatsLayout.setVerticalGroup(
            pnlStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStatsLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(LvlLabel)
                .addGap(33, 33, 33)
                .addComponent(btnNextLevel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(135, 135, 135)
                .addComponent(ShotsFiredLabel)
                .addGap(84, 84, 84)
                .addComponent(KilledTanksLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(103, 103, 103)
                .addComponent(BonusLabel)
                .addContainerGap(317, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlGame, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlStats, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlStats, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlGame, javax.swing.GroupLayout.DEFAULT_SIZE, 836, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNextLevelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextLevelActionPerformed
        gamecontroller.nextLevel();
    }//GEN-LAST:event_btnNextLevelActionPerformed
    public void pauseGame(){
        String[] options = {"Resume", "Quit Game"};
        int choice = JOptionPane.showOptionDialog(
                this,
                "Game Paused",
                "Pause Game",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == 0) {
            // User selected "Resume"
            // Add logic to unpause the game
            // You can call a method or set a flag to resume the game
            // For demonstration purposes, let's print a message
            System.out.println("Game Resumed");
        } else if (choice == 1) {
            // User selected "Quit Game"
            this.dispose();
        }
    }
    public JLabel getBonusLabel() {
        return BonusLabel;
    }

    public JLabel getLvlLabel() {
        return LvlLabel;
    }
    
    public JLabel getKilledTanksLabel() {
        return KilledTanksLabel;
    }

    public JLabel getShotsFiredLabel() {
        return ShotsFiredLabel;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BonusLabel;
    private javax.swing.JLabel KilledTanksLabel;
    private javax.swing.JLabel LvlLabel;
    private javax.swing.JLabel ShotsFiredLabel;
    private javax.swing.JButton btnNextLevel;
    public javax.swing.JPanel pnlGame;
    public javax.swing.JPanel pnlStats;
    // End of variables declaration//GEN-END:variables
}
