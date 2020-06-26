/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt;

import static bt.ControlPanel.jTable1;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author gabor_hanacsek
 */
public class Setup extends javax.swing.JDialog {

    /**
     * Creates new form Setup
     */
    public Setup(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        fillTable();
        fillCella();
        fillPn();
        fillWs();
    }

    public void fillTable() {
        //kinullázzuk a táblákat sorait
        DefaultTableModel model = new DefaultTableModel();
        model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        //kitöltjük a létező adatok táblét
        String query = "select tc_becells.cellname, tc_bepns.partnumber, tc_bestations.workstation, tc_prodmatrix.ciklusido from tc_prodmatrix\n"
                + "left join tc_becells on tc_becells.idtc_cells = tc_prodmatrix.id_tc_becells\n"
                + "left join tc_bepns on tc_bepns.idtc_bepns = tc_prodmatrix.id_tc_bepns\n"
                + "left join tc_bestations on tc_bestations.idtc_bestations = tc_prodmatrix.id_tc_bestations\n"
                + "where tc_becells.idtc_cells = tc_prodmatrix.id_tc_becells\n"
                + "and tc_bepns.idtc_bepns = tc_prodmatrix.id_tc_bepns\n"
                + "and tc_bestations.idtc_bestations = tc_prodmatrix.id_tc_bestations";
        PlanConnect pc = null;
        try {
            pc = new PlanConnect();
            pc.lekerdez(query);
            //feltöltjük a táblát az adatokkal
            while (pc.rs.next()) {

                model.addRow(new Object[]{pc.rs.getString("cellname"), pc.rs.getString("partnumber"), pc.rs.getString("workstation"), pc.rs.getString("ciklusido")});
            }

            jTable1.setModel(model);

        } catch (SQLException ex) {
            Logger.getLogger(Setup.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            Starter.e.sendMessage(ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Setup.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            Starter.e.sendMessage(ex);
        } finally {
            try {
                pc.kinyir();
            } catch (Exception e) {
                e.printStackTrace();
                Starter.e.sendMessage(e);
            }
        }

    }

    public void fillCella() {

        //kinullázzuk a tábla sorait
        DefaultTableModel model = new DefaultTableModel();
        model = (DefaultTableModel) jTable2.getModel();
        model.setRowCount(0);
        //kitöltjük a létező adatok táblét
        String query = "SELECT cellname from tc_becells";
        PlanConnect pc = null;
        try {
            pc = new PlanConnect();
            pc.lekerdez(query);
            //feltöltjük a táblát az adatokkal
            while (pc.rs.next()) {

                model.addRow(new Object[]{pc.rs.getString("cellname")});
            }

            jTable2.setModel(model);

        } catch (SQLException ex) {
            Logger.getLogger(Setup.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            Starter.e.sendMessage(ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Setup.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            Starter.e.sendMessage(ex);
        } finally {
            try {
                pc.kinyir();
            } catch (Exception e) {
                e.printStackTrace();
                Starter.e.sendMessage(e);
            }
        }

    }

    public void fillPn() {

        //kinullázzuk a tábla sorait
        DefaultTableModel model = new DefaultTableModel();
        model = (DefaultTableModel) jTable3.getModel();
        model.setRowCount(0);
        //kitöltjük a létező adatok táblét
        String query = "SELECT partnumber FROM planningdb.tc_bepns;";
        PlanConnect pc = null;
        try {
            pc = new PlanConnect();
            pc.lekerdez(query);
            //feltöltjük a táblát az adatokkal
            while (pc.rs.next()) {

                model.addRow(new Object[]{pc.rs.getString("partnumber")});
            }

            jTable3.setModel(model);

        } catch (SQLException ex) {
            Logger.getLogger(Setup.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            Starter.e.sendMessage(ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Setup.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            Starter.e.sendMessage(ex);
        } finally {
            try {
                pc.kinyir();
            } catch (Exception e) {
                e.printStackTrace();
                Starter.e.sendMessage(e);
            }
        }

    }

    public void fillWs() {

        //kinullázzuk a tábla sorait
        DefaultTableModel model = new DefaultTableModel();
        model = (DefaultTableModel) jTable4.getModel();
        model.setRowCount(0);
        //kitöltjük a létező adatok táblét
        String query = "SELECT workstation FROM planningdb.tc_bestations;";
        PlanConnect pc = null;
        try {
            pc = new PlanConnect();
            pc.lekerdez(query);
            //feltöltjük a táblát az adatokkal
            while (pc.rs.next()) {

                model.addRow(new Object[]{pc.rs.getString("workstation")});
            }

            jTable4.setModel(model);

        } catch (SQLException ex) {
            Logger.getLogger(Setup.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            Starter.e.sendMessage(ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Setup.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            Starter.e.sendMessage(ex);
        } finally {
            try {
                pc.kinyir();
            } catch (Exception e) {
                e.printStackTrace();
                Starter.e.sendMessage(e);
            }
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new BackgroundPanel(Variables.background.controlpanelkulso);
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Setup");
        setResizable(false);

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("Létező kombinációk"));
        jScrollPane1.setOpaque(false);

        jTable1.setAutoCreateRowSorter(true);
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cella", "PartNumber", "WorkStation", "Cycle Time (db/h)"
            }
        ));
        jTable1.setOpaque(false);
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setText("Kereső:");

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CellName"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setCellSelectionEnabled(true);
        jScrollPane2.setViewportView(jTable2);
        jTable2.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PartNumber"
            }
        ));
        jScrollPane3.setViewportView(jTable3);
        jTable3.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "WorkStation"
            }
        ));
        jScrollPane4.setViewportView(jTable4);
        jTable4.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jLabel2.setText("Új cella felvétele:");

        jLabel3.setText("Új partnumber felvétele:");

        jLabel4.setText("Új workstation felvétele:");

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/tervplus.png"))); // NOI18N
        jLabel5.setToolTipText("Cella felvétele");
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/tervplus.png"))); // NOI18N
        jLabel6.setToolTipText("PartNumber felvétele");
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/tervplus.png"))); // NOI18N
        jLabel7.setToolTipText("WorkStation felvétele");
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });

        jLabel8.setText("Ciklusidő módosítása vagy felvétele:");
        jLabel8.setToolTipText("Jelölj ki egy Cellát, egy Partnumber és egy Workstationt, add meg a db/h -t majd a + gombot!");

        jTextField5.setToolTipText("Jelölj ki egy Cellát, egy Partnumber és egy Workstationt, add meg a db/h -t majd a + gombot!");

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/tervplus.png"))); // NOI18N
        jLabel9.setToolTipText("Ciklusidő felvitele!");
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel5))
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel8))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel9))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jLabel6))
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(37, 37, 37)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(2, 2, 2)
                                            .addComponent(jLabel7))
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addGap(23, 23, 23)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jScrollPane3, jScrollPane4});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel9))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        // a kereső
        filter(jTextField1.getText());


    }//GEN-LAST:event_jTextField1KeyReleased

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        try {
            //cella felvétele
            PlanConnect pc = null;
            String query = "insert ignore tc_becells (cellname) values ('" + jTextField2.getText().trim() + "')";
            pc = new PlanConnect();
            pc.feltolt(query);
            //default title and icon
            JOptionPane.showMessageDialog(this,
                    "Sikeres mentés!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            Starter.e.sendMessage(ex);
        }
        //visszahívjuk az adatokat
        fillCella();
    }//GEN-LAST:event_jLabel5MouseClicked

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        //pn felvétele
        try {

            PlanConnect pc = null;
            String query = "insert ignore tc_bepns (partnumber) values ('" + jTextField3.getText().trim() + "')";
            pc = new PlanConnect();
            pc.feltolt(query);
            //default title and icon
            JOptionPane.showMessageDialog(this,
                    "Sikeres mentés!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            Starter.e.sendMessage(ex);
        }
        //visszahívjuk az adatokat
        fillPn();
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        //ws felvétele
        try {

            PlanConnect pc = null;
            String query = "insert ignore tc_bestations (workstation) values ('" + jTextField4.getText().trim() + "')";
            pc = new PlanConnect();
            pc.feltolt(query);
            //default title and icon
            JOptionPane.showMessageDialog(this,
                    "Sikeres mentés!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            Starter.e.sendMessage(ex);
        }

        //visszahívjuk az adatokat
        fillWs();

    }//GEN-LAST:event_jLabel7MouseClicked

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked

        //ciklusidő felvétele vagy updateje
        //leellenőrizzük, hogy ki van e jelölve minden táblában valami
        String cella = "";
        try {
            int row = jTable2.getSelectedRow();
            if ((row > -1)) {
                jTable2.setRowSelectionInterval(row, row);
            }
            cella = jTable2.getValueAt(jTable2.getSelectedRow(), 0).toString();
        } catch (Exception e) {
            //custom title, error icon
            JOptionPane.showMessageDialog(this,
                    "A cella adatod hiányzik \n A mentést nem folytatjuk!",
                    "Hiányzó adat!",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        String pn = "";
        try {
            int row = jTable3.getSelectedRow();
            if ((row > -1)) {
                jTable3.setRowSelectionInterval(row, row);
            }
            pn = jTable3.getValueAt(jTable3.getSelectedRow(), 0).toString();
        } catch (Exception e) {
            //custom title, error icon
            JOptionPane.showMessageDialog(this,
                    "A partnumber adatod hiányzik \n A mentést nem folytatjuk!",
                    "Hiányzó adat!",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        String ws = "";
        try {
            int row = jTable4.getSelectedRow();
            if ((row > -1)) {
                jTable4.setRowSelectionInterval(row, row);
            }
            ws = jTable4.getValueAt(jTable4.getSelectedRow(), 0).toString();
        } catch (Exception e) {
            //custom title, error icon
            JOptionPane.showMessageDialog(this,
                    "A workstation adatod hiányzik \n A mentést nem folytatjuk!",
                    "Hiányzó adat!",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        double ct = 0;
        try {
            ct = Double.parseDouble(jTextField5.getText().trim());
        } catch (Exception e) {
            //custom title, error icon
            JOptionPane.showMessageDialog(this,
                    "A ciklusidő adatod hiányzik vagy helytelen formátum! \n A mentést nem folytatjuk!",
                    "Hiányzó adat!",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        //ha bármelyik is üres maradt akkor kiállunk hibával és megszakítjuk a futást
        if (cella.equals("") || pn.equals("") || ws.equals("") || ct == 0) {

            //custom title, error icon
            JOptionPane.showMessageDialog(this,
                    "Az egyik adatod hiányzik \n A mentést nem folytatjuk!",
                    "Hiányzó adat!",
                    JOptionPane.ERROR_MESSAGE);
            return;

        }
//        System.out.println(cella + " " + pn + " " + ws + " " + ct);
//akkor most fel kell updatelni a prodmatrixot az adatokkal
        String insertquery = "insert ignore tc_prodmatrix (id_tc_bepns,id_tc_becells,id_tc_bestations,ciklusido,pk) values ((select tc_bepns.idtc_bepns from tc_bepns where tc_bepns.partnumber = '" + pn + "') , (select tc_becells.idtc_cells from tc_becells where tc_becells.cellname = '" + cella + "'), (select tc_bestations.idtc_bestations from tc_bestations where tc_bestations.workstation = '" + ws + "'), '" + ct + "',(concat((select tc_bepns.idtc_bepns from tc_bepns where tc_bepns.partnumber = '" + pn + "') , (select tc_becells.idtc_cells from tc_becells where tc_becells.cellname = '" + cella + "'), (select tc_bestations.idtc_bestations from tc_bestations where tc_bestations.workstation = '" + ws + "'))))\n"
                + "on duplicate key update tc_prodmatrix.ciklusido = values(ciklusido)";

        PlanConnect pc = null;

        try {
            pc = new PlanConnect();
            pc.feltolt(insertquery);

            //visszahívjuk az adatokat a táblába
            fillTable();

        } catch (SQLException ex) {
            ex.printStackTrace();
            Starter.e.sendMessage(ex);
        } finally {
            try {
                pc.kinyir();
            } catch (Exception e) {
                e.printStackTrace();
                Starter.e.sendMessage(e);
            }

        }
//most be kéne frissíteni a backendsheetek adatait
        //frissítjük az adatokat a backendsheeten
        for (int i = 0; i < MainWindow.jTabbedPane1.getTabCount(); i++) {
            BeSheet b = (BeSheet) MainWindow.jTabbedPane1.getComponentAt(i);
            b.adatleker(b.getName());
        }

        //default title and icon
        JOptionPane.showMessageDialog(this,
                "Sikeres mentés!");

    }//GEN-LAST:event_jLabel9MouseClicked

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void filter(String query) {
        DefaultTableModel model = new DefaultTableModel();
        model = (DefaultTableModel) jTable1.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(model);
        jTable1.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(query));

    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);

//        fillTable();
//        fillCella();
//        fillPn();
//        fillWs();
    }

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
            java.util.logging.Logger.getLogger(Setup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Setup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Setup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Setup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Setup dialog = new Setup(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables
}
