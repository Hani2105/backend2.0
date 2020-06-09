/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.Icon;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.util.Timer;

/**
 *
 * @author gabor_hanacsek
 */
public class MainWindow extends javax.swing.JFrame {

    /**
     * Creates new form MainWindow
     */
    public SetPlannObjectData spo = new SetPlannObjectData(this, false);
    public LoginScreen ls = new LoginScreen(this, true, this);
    public ControlPanel cp = new ControlPanel(this, false, this);
    public static JogosultsagKezelo j;
    public SfdcHatter sfdchatter = new SfdcHatter(this, true);
    public MentesHatter menteshatter = new MentesHatter(this, true);
    public SessionObject so = new SessionObject();
    public Anyaghiany ahrogzito = new Anyaghiany(this, false);
    public Allasido allasrogzito = new Allasido(this, false);
    public Statisztika statisztika = new Statisztika(this, false);

    //a panel szelessege es magassaga
//inicializálás
    public MainWindow() throws IOException, SQLException, ClassNotFoundException, UnsupportedLookAndFeelException, InterruptedException {
//a kinézet beállítása
        UIManager.setLookAndFeel(new MetalLookAndFeel());
//komponensek inicializálása
        initComponents();

//az ikon beállítása
        setIcon();

//a pn kommentek lekérése
        pnCommentLeker();
//a jgosultsági szintek beállítása
        j = new JogosultsagKezelo(cp, spo);
//beállítjuk a jogosultságot nullára alapból
        Variables.jogosultsag = 0;
        j.kezel();
//a tabbedpanel kinézetének beállítása      
        jTabbedPane1.setUI(new MyTabbedPaneUI(jTabbedPane1));
        setExtendedState(getExtendedState() | MAXIMIZED_BOTH);
//a splitpane szélességének beállítása
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        jSplitPane1.setDividerLocation((screenSize.width / 100) * 95);
//lekérdezzük, hogy milyen celláink vannak
        getCellas();
//a sessionadatok beolvasása, ha null írunk egyet
        if (new SessionKezelo(this).sessionOlvas() == null) {
            new SessionKezelo(this).sessionIr(this.so);
            System.out.println("session.dat létrehozása..");
        } else {
            this.so = new SessionKezelo(this).sessionOlvas();
            //kijeloljuk az user cellait
            cellakKijelolese();
        }

//láthatóvá tesszük a főablakot
        this.setVisible(true);

//lecsekkoljuk a verziót
        Thread t = new Thread(new Versioncheck(this));
        t.start();
        t.join();

//láthatóvá tesszük a loginscreent
        ls.setVisible(true);

    }

    public void setIcon() {

        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/pictures/mainicon.png")));
    }

    public void cellakMentese() {
        ArrayList<String> cellak = new ArrayList<>();
        // Get the index of all the selected items
        int[] selectedIx = jList1.getSelectedIndices();
        // Get all the selected items using the indices
        for (int i = 0; i < selectedIx.length; i++) {
            cellak.add(jList1.getModel().getElementAt(selectedIx[i]));
        }
        so.setCellak(cellak);
        try {
            //kiirjuk az so-t
            new SessionKezelo(this).sessionIr(so);
        } catch (IOException ex) {
            ex.printStackTrace();
            Starter.e.sendMessage(ex);
        }
    }

    public void cellakKijelolese() {
        try {
            int[] selected = new int[so.getCellak().size()];
            int counter = 0;

            for (int i = 0; i < so.getCellak().size(); i++) {

                for (int l = 0; l < jList1.getModel().getSize(); l++) {

                    if (so.getCellak().get(i).equals(jList1.getModel().getElementAt(l))) {

                        selected[counter] = l;
                        counter++;

                    }
                }
            }

            jList1.setSelectedIndices(selected);

        } catch (Exception e) {
            System.out.println("Nem sikerült beolvasni a cella adatokat!");
            e.printStackTrace();
            Starter.e.sendMessage(e);

        }
    }

    public void getCellas() {
//lekérdezzük, hogy egyáltalán milyen celláink vannak
        String query = "SELECT distinct tc_becells.cellname FROM tc_becells";
        PlanConnect pc = null;
        DefaultListModel listModel = new DefaultListModel();
        try {
            pc = new PlanConnect();

            try {
                pc.lekerdez(query);

                while (pc.rs.next()) {

                    listModel.addElement(pc.rs.getString(1));
                }

                this.jList1.setModel(listModel);

            } catch (SQLException ex) {
                ex.printStackTrace();
                Starter.e.sendMessage(ex);

            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
                Starter.e.sendMessage(ex);

            }

        } catch (Exception e) {
            e.printStackTrace();
            Starter.e.sendMessage(e);
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

    //paint, jelenleg a választó vonalakat rajzolja   
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jTabbedPane1 = new TabbedPaneWithCloseIcons();
        jPanel1 = new BackgroundPanel(Variables.background.oldalmenu);
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        jTextField1 = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jCheckBox3 = new javax.swing.JCheckBox();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jMenuBar2 = new MyMenubar();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Backend Tervező 2.0");
        setSize(new java.awt.Dimension(800, 600));

        jSplitPane1.setDividerLocation(600);
        jSplitPane1.setDividerSize(15);
        jSplitPane1.setOneTouchExpandable(true);

        jTabbedPane1.setOpaque(true);
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(1000, 600));
        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane1StateChanged(evt);
            }
        });
        jSplitPane1.setLeftComponent(jTabbedPane1);

        jPanel1.setMinimumSize(new java.awt.Dimension(160, 100));

        jDateChooser1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tól", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        jDateChooser1.setOpaque(false);
        jDateChooser1.setPreferredSize(new java.awt.Dimension(100, 42));

        jDateChooser2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ig", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        jDateChooser2.setOpaque(false);

        jLabel2.setText("Terv intervallum:");

        jProgressBar1.setForeground(new java.awt.Color(0, 153, 153));
        jProgressBar1.setRequestFocusEnabled(false);
        jProgressBar1.setString("Lekér!");
        jProgressBar1.setStringPainted(true);
        jProgressBar1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jProgressBar1MouseClicked(evt);
            }
        });

        jTextField1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Kereső", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        jTextField1.setOpaque(false);
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jLabel3.setText("SFDC beállítások:");

        jCheckBox1.setText("SFDC, JOB figyelés!");
        jCheckBox1.setToolTipText("Ha be van kapcsolva, a megvalósulás kitöltésénél figyelembe vesszük a JOB számot is!");
        jCheckBox1.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jCheckBox1.setOpaque(false);
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jCheckBox2.setText("SFDC, first pass only!");
        jCheckBox2.setToolTipText("Ha be van kapcsolva, csak a first pass mennyiséget vesszük figyelembe!");
        jCheckBox2.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jCheckBox2.setOpaque(false);

        jLabel5.setFont(new java.awt.Font("Magneto", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 153, 153));
        jLabel5.setText("Ismeretlen!");

        jLabel6.setFont(new java.awt.Font("Magneto", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 153, 153));
        jLabel6.setText(" Idegen!");

        jLabel7.setText("Üdv");

        jLabel8.setText("Mint:");

        jLabel4.setText("Cellák:");

        jScrollPane1.setViewportView(jList1);

        jCheckBox3.setSelected(true);
        jCheckBox3.setText("Összes sheetre!");
        jCheckBox3.setToolTipText("Ha be van kapcsolva, a megvalósulás kitöltésénél figyelembe vesszük a JOB számot is!");
        jCheckBox3.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jCheckBox3.setOpaque(false);

        jLabel9.setFont(new java.awt.Font("Lucida Calligraphy", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 153, 153));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("0");

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Job státus lekérés");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField1)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addComponent(jSeparator3)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCheckBox2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jDateChooser2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(1, 1, 1))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox3)
                .addGap(6, 6, 6)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        jSplitPane1.setRightComponent(jPanel1);

        jMenuBar2.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N

        jMenu3.setBackground(new java.awt.Color(102, 153, 255));
        jMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/menu.png"))); // NOI18N
        jMenu3.setText("Menü");
        jMenu3.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N

        jMenuItem4.setBackground(new java.awt.Color(102, 153, 255));
        jMenuItem4.setText("Control panel");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem4);

        jMenu1.setBackground(new java.awt.Color(102, 153, 255));
        jMenu1.setText("Statisztikák");
        jMenu1.setOpaque(true);

        jMenuItem1.setBackground(new java.awt.Color(51, 153, 255));
        jMenuItem1.setText("Aktuális heti kihasználtság");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenu3.add(jMenu1);

        jMenuItem3.setBackground(new java.awt.Color(102, 153, 255));
        jMenuItem3.setText("Login");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem3);

        jMenuBar2.add(jMenu3);

        setJMenuBar(jMenuBar2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 771, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 592, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public static void pnCommentLeker() {
        Variables.pnkomment.clear();
        //letároljuk az összes pnkommentet a variablesbe
        String query = "select pn_data.PartNumber,pn_data.Comment from pn_data";
        PlanConnect pc = null;
        try {
            pc = new PlanConnect();

            try {
                pc.lekerdez(query);
            } catch (SQLException ex) {
                ex.printStackTrace();
                Starter.e.sendMessage(ex);
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
                Starter.e.sendMessage(ex);
            }
            try {
                while (pc.rs.next()) {

                    String[] adatok = new String[2];
                    adatok[0] = pc.rs.getString(1);
                    adatok[1] = pc.rs.getString(2);
                    Variables.pnkomment.add(adatok);

                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                Starter.e.sendMessage(ex);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Starter.e.sendMessage(e);

        } finally {
            try {
                pc.kinyir();
            } catch (Exception e) {
                e.printStackTrace();
                Starter.e.sendMessage(e);
            }
        }

    }

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        //login screen
        ls.setVisible(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // loader panel
        cp.setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1StateChanged
        //változik a seleted tab
        try {
            cp.tablaTorol();
        } catch (Exception e) {
        }
        try {
            cp.adatlekerBeSheetrol();
        } catch (Exception e) {
        }
        //a control panelen kitoltjuk a muszakjelentest
        try {
            cp.muszakjelentesToControlPanel();
        } catch (Exception e) {
        }
        //kitoroljuk az adatokat h ne legyen zavaro
        try {
            cp.jTextPane1.setText("");
        } catch (Exception e) {
        }
        //segedlet frissitese
        try {
            cp.segedlet();
        } catch (Exception e) {
        }
        //a jobfigyeles pipajat beallitjuk a sheet ertekenek megfeleloen
        try {
            BeSheet b = (BeSheet) jTabbedPane1.getComponentAt(jTabbedPane1.getSelectedIndex());
            jCheckBox1.setSelected(b.isJobfigyeles());
        } catch (Exception e) {
        }


    }//GEN-LAST:event_jTabbedPane1StateChanged

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        // kereseő
        BeSheet b = (BeSheet) this.jTabbedPane1.getComponentAt(this.jTabbedPane1.getSelectedIndex());
        Component[] components = b.jPanel1.getComponents();
        for (int i = 0; i < components.length; i++) {
            if (components[i] instanceof PlannObject) {

                PlannObject po = (PlannObject) components[i];
                if (!po.getJob().contains(jTextField1.getText()) && !po.getPn().contains(jTextField1.getText())) {

                    po.setVisible(false);
                } else {
                    po.setVisible(true);
                }

            }

        }
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jProgressBar1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jProgressBar1MouseClicked
        // terv lekérése
        jProgressBar1.setString("Lekérés folyamatban!");
        Thread t = new Thread(new TervLeker(this));
        t.start();
        //beállítjuk az uj cellákat a sessionba
        cellakMentese();

    }//GEN-LAST:event_jProgressBar1MouseClicked

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // heti statisztika lekérése
        statisztika.setVisible(true, Variables.statisztika.heti);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // job figyelés beállítása a sheeten
        //az aktiv besheet jobfigyeles erteket beallitjuk
        BeSheet b = (BeSheet) jTabbedPane1.getComponentAt(jTabbedPane1.getSelectedIndex());
        if (jCheckBox1.isSelected()) {

            b.setJobfigyeles(true);
        } else {

            b.setJobfigyeles(false);

        }
    }//GEN-LAST:event_jCheckBox1ActionPerformed

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
                if ("Metal".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Starter.e.sendMessage(e);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new MainWindow().setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    Starter.e.sendMessage(e);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JCheckBox jCheckBox1;
    public static javax.swing.JCheckBox jCheckBox2;
    public static javax.swing.JCheckBox jCheckBox3;
    public static com.toedter.calendar.JDateChooser jDateChooser1;
    public static com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    public static javax.swing.JLabel jLabel5;
    public static javax.swing.JLabel jLabel6;
    public static javax.swing.JLabel jLabel7;
    public static javax.swing.JLabel jLabel8;
    public static javax.swing.JLabel jLabel9;
    public static javax.swing.JList<String> jList1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    public static javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSplitPane jSplitPane1;
    public static javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
