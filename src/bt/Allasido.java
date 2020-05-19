/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt;

import static bt.ControlPanel.jTable1;
import bt.PlannObject.AnyagHiany;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author gabor_hanacsek
 */
public class Allasido extends javax.swing.JDialog {

    /**
     * Creates new form Allasido
     */
    PlannObject p;
    int x;
    int y;

    public Allasido(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        createTable();
    }

    private void createTable() {
//itt csináljuk meg a tábla tulajdonságait
        TableColumn testColumn = jTable1.getColumnModel().getColumn(2);
        JComboBox<String> comboBox = new JComboBox<>();
//az inibol kell a lista, hogy milyen elemeket tartalmazzon a legordulo
        String[] lista = Variables.allasidolegordulo.split(",");
        comboBox.addItem("");
        for (String s : lista) {
            comboBox.addItem(s);
        }
//beallitjuk a culomba a cuccot
        testColumn.setCellEditor(new DefaultCellEditor(comboBox));

    }

    public void setVisible(PlannObject p, boolean b) {
        super.setVisible(b);
        this.p = p;
        getData();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setOpacity(0.95F);
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Állásidő felvétele"));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Tól (yyyy-MM-dd hh:mm)", "Ig (yyyy-MM-dd hh:mm)", "Felelős", "Komment", "id"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, true, true, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setCellSelectionEnabled(true);
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(4).setMinWidth(30);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(30);
            jTable1.getColumnModel().getColumn(4).setMaxWidth(30);
        }

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 51, 51));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/close_1.png"))); // NOI18N
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 51, 51));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/save.png"))); // NOI18N
        jLabel2.setToolTipText("Adatok mentése");
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/deleteallas.png"))); // NOI18N
        jLabel3.setToolTipText("Felvitt adat törlése");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 676, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 313, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(686, 313));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        // TODO add your handling code here:
        this.setVisible(false);

    }//GEN-LAST:event_jLabel1MouseClicked
//adatok kiszedese a plannobjectbol

    private void getData() {
        ArrayList data = null;
        data = p.getAllasidoLista();
        //kinullazzuk az adatokat
        DefaultTableModel model = new DefaultTableModel();
        model = (DefaultTableModel) jTable1.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {

            for (int c = 0; c < model.getColumnCount(); c++) {

                model.setValueAt("", i, c);
            }

        }
        if (data != null) {

//beallitjuk az adatokat
            for (int i = 0; i < data.size(); i++) {
                PlannObject.AllasidoLista a = (PlannObject.AllasidoLista) data.get(i);
                if (a.tol.length() > 16) {
                    model.setValueAt(a.tol.substring(0, a.tol.length() - 5), i, 0);
                } else {
                    model.setValueAt(a.tol.substring(0, a.tol.length()), i, 0);
                }
                if (a.ig.length() > 16) {
                    model.setValueAt(a.ig.substring(0, a.ig.length() - 5), i, 1);
                } else {
                    model.setValueAt(a.ig.substring(0, a.ig.length()), i, 1);
                }

                model.setValueAt(a.felelos, i, 2);
                model.setValueAt(a.komment, i, 3);
                model.setValueAt(a.id, i, 4);
            }
        }

        jTable1.setModel(model);
    }


    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
//Az állásidők mentése
        ment();
    }//GEN-LAST:event_jLabel2MouseClicked

    private void ment() {

//felvesszuk a tabla datait a plannobject tarolojaba
//leellenorizzuk, hogy megfeleloen van e kitoltve a tabla
        for (int i = 0; i < jTable1.getRowCount(); i++) {
            String tol = "";
            try {
//a tol leellenőrzése
                tol = jTable1.getValueAt(i, 0).toString().trim();
//akkor megyünk tovább ha van tol dátum
                if (!tol.equals("")) {
                    if (!Pattern.matches("[0-9]{4}[-][0-9]{2}[-][0-9]{2}[ ][0-9]{2}[:][0-9]{2}", tol)) {
                        JOptionPane.showMessageDialog(p.getMainWindow(),
                                "Nem megfelelő dátum formátum a " + (i + 1) + " .sorban!",
                                "Hiba",
                                JOptionPane.ERROR_MESSAGE);
                        return;

                    }
//az ig leellenőrzése
                    String ig = jTable1.getValueAt(i, 1).toString().trim();
                    if (!Pattern.matches("[0-9]{4}[-][0-9]{2}[-][0-9]{2}[ ][0-9]{2}[:][0-9]{2}", ig)) {
                        JOptionPane.showMessageDialog(p.getMainWindow(),
                                "Nem megfelelő dátum formátum a " + (i + 1) + " .sorban!",
                                "Hiba",
                                JOptionPane.ERROR_MESSAGE);
                        return;

                    }

//a felelős kitöltésének 
                    String felelos = jTable1.getValueAt(i, 2).toString();
                    if (felelos.equals("")) {
                        JOptionPane.showMessageDialog(p.getMainWindow(),
                                "Nem választottál ki felelőst a " + (i + 1) + " .sorban!",
                                "Hiba",
                                JOptionPane.ERROR_MESSAGE);
                        return;

                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(p.getMainWindow(),
                        "Nem megfelelő vagy hiányzó adatok!!",
                        "Hiba",
                        JOptionPane.ERROR_MESSAGE);
                return;

            }
        }
//ha eddig eljutottunk akkor kitörölhetjük a listát és felvihetjük az új adatokat
        p.clearAllasidoLista();

        for (int i = 0; i < jTable1.getRowCount(); i++) {

            if (!jTable1.getValueAt(i, 2).equals("")) {

                String tol = jTable1.getValueAt(i, 0).toString().trim();
                String ig = jTable1.getValueAt(i, 1).toString().trim();
                String felelos = jTable1.getValueAt(i, 2).toString().trim();
                String komment = "";
                try {
                    komment = jTable1.getValueAt(i, 3).toString().trim();
                } catch (Exception e) {
                }
                String id = "";
                try {
                    id = jTable1.getValueAt(i, 4).toString().trim();
                } catch (Exception e) {
                }
                p.addAllasidoLista(tol, ig, felelos, komment, id);
            }

        }
//beállítjuk a tooltip textet
        p.formatText();
        p.repaint();

//az allasidok rogzitese az adatbazisba
        String query = "insert ignore downtimes_production (line,datefrom,dateto,downtimename,comments,pktomig,id) values";
        String adatok = "";

        //bejarjuk a plannobjecteket es ha nagyobb az allasidos lista nullanal begyujtjuk az adatokat
        if (p.getAllasidoLista().size() > 0) {

            for (int m = 0; m < p.getAllasidoLista().size(); m++) {

                adatok += "('" + p.getbackendSheet().getName() + "','" + p.getAllasidoLista().get(m).tol + "','" + p.getAllasidoLista().get(m).ig + "','" + p.getAllasidoLista().get(m).felelos + "','" + p.getAllasidoLista().get(m).komment + "',concat('" + p.getStartdate() + "', (select tc_becells.idtc_cells from tc_becells where tc_becells.cellname = '" + p.getbackendSheet().getName() + "'), (select tc_bestations.idtc_bestations from tc_bestations where tc_bestations.workstation = '" + p.getWorkStation() + "'), (select tc_bepns.idtc_bepns from tc_bepns where tc_bepns.partnumber = '" + p.getPn() + "'), '3', '" + p.getJob() + "'),'" + p.getAllasidoLista().get(m).id + "'),";

            }

            adatok = adatok.substring(0, adatok.length() - 1);
            PlanConnect pc = null;
            try {
                query = query + adatok + "on duplicate key update datefrom = values (datefrom), dateto = values (dateto), downtimename = values (downtimename), comments = values(comments)";
                pc = new PlanConnect();
                pc.feltolt(query);
//visszakérdezzük
                query = "select * from downtimes_production where pktomig = concat('" + p.getStartdate() + "', (select tc_becells.idtc_cells from tc_becells where tc_becells.cellname = '" + p.getbackendSheet().getName() + "'), (select tc_bestations.idtc_bestations from tc_bestations where tc_bestations.workstation = '" + p.getWorkStation() + "'), (select tc_bepns.idtc_bepns from tc_bepns where tc_bepns.partnumber = '" + p.getPn() + "'), '3', '" + p.getJob() + "')";
                pc.lekerdez(query);
                DefaultTableModel model = new DefaultTableModel();
                model = (DefaultTableModel) jTable1.getModel();
                for (int i = 0; i < model.getRowCount(); i++) {

                    for (int c = 0; c < model.getColumnCount(); c++) {

                        model.setValueAt("", i, c);
                    }

                }
                p.clearAllasidoLista();
                int i = 0;
                while (pc.rs.next()) {

                    model.setValueAt(pc.rs.getString("datefrom").substring(0, pc.rs.getString("datefrom").length() - 5), i, 0);
                    model.setValueAt(pc.rs.getString("dateto").substring(0, pc.rs.getString("dateto").length() - 5), i, 1);
                    model.setValueAt(pc.rs.getString("downtimename"), i, 2);
                    model.setValueAt(pc.rs.getString("comments"), i, 3);
                    model.setValueAt(pc.rs.getString("id"), i, 4);
                    p.addAllasidoLista(pc.rs.getString("datefrom"), pc.rs.getString("dateto"), pc.rs.getString("downtimename"), pc.rs.getString("comments"), pc.rs.getString("id"));
                    i++;

                }

                jTable1.setModel(model);
//default title and icon
                JOptionPane.showMessageDialog(p.getMainWindow(),
                        "Sikeres mentés!");

            } catch (Exception e) {
                e.printStackTrace();
                Starter.e.sendMessage(e);
            } finally {
                try {
                    pc.kinyir();
                } catch (Exception e) {
                }
            }
        }
    }


    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        // TODO add your handling code here:
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        // TODO add your handling code here:
        this.setLocation(evt.getXOnScreen() - x, evt.getYOnScreen() - y);
    }//GEN-LAST:event_formMouseDragged

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        //allasido torlese
        //kitöröljük az adatbázisból
        PlanConnect pc = null;
        String query = "delete from downtimes_production where downtimes_production.id = '" + jTable1.getValueAt(jTable1.getSelectedRow(), 4).toString() + "'";
        try {
            pc = new PlanConnect();
            pc.feltolt(query);
            //visszakérdezzük
            query = "select * from downtimes_production where pktomig = concat('" + p.getStartdate() + "', (select tc_becells.idtc_cells from tc_becells where tc_becells.cellname = '" + p.getbackendSheet().getName() + "'), (select tc_bestations.idtc_bestations from tc_bestations where tc_bestations.workstation = '" + p.getWorkStation() + "'), (select tc_bepns.idtc_bepns from tc_bepns where tc_bepns.partnumber = '" + p.getPn() + "'), '3', '" + p.getJob() + "')";
            pc.lekerdez(query);

            DefaultTableModel model = new DefaultTableModel();
            model = (DefaultTableModel) jTable1.getModel();
            for (int i = 0; i < model.getRowCount(); i++) {

                for (int c = 0; c < model.getColumnCount(); c++) {

                    model.setValueAt("", i, c);
                }

            }
            p.clearAllasidoLista();
            int i = 0;
            while (pc.rs.next()) {

                model.setValueAt(pc.rs.getString("datefrom").substring(0, pc.rs.getString("datefrom").length() - 5), i, 0);
                model.setValueAt(pc.rs.getString("dateto").substring(0, pc.rs.getString("dateto").length() - 5), i, 1);
                model.setValueAt(pc.rs.getString("downtimename"), i, 2);
                model.setValueAt(pc.rs.getString("comments"), i, 3);
                model.setValueAt(pc.rs.getString("id"), i, 4);
                p.addAllasidoLista(pc.rs.getString("datefrom"), pc.rs.getString("dateto"), pc.rs.getString("downtimename"), pc.rs.getString("comments"), pc.rs.getString("id"));
                p.formatText();
                i++;

            }

            jTable1.setModel(model);
            //default title and icon
            JOptionPane.showMessageDialog(p.getMainWindow(),
                    "Sikeres törlés!");

        } catch (Exception e) {
            e.printStackTrace();
            Starter.e.sendMessage(e);
        } finally {
            try {
                pc.kinyir();
            } catch (Exception e) {
            }

        }
    }//GEN-LAST:event_jLabel3MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Allasido.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Allasido.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Allasido.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Allasido.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Allasido dialog = new Allasido(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
