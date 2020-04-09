/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt;

/**
 *
 * @author gabor_hanacsek
 */
public class JobAdagolo implements Runnable {

    BeSheet b;

    public JobAdagolo(BeSheet b) {
        this.b = b;
    }

    @Override
    public void run() {
        //megkeressük az utolsó job ot ami illik a prefixbe
        int uccsofuto = 0;

        for (int i = 0; i < b.jPanel1.getComponentCount(); i++) {
            if (b.jPanel1.getComponent(i) instanceof PlannObject) {

                PlannObject po = (PlannObject) b.jPanel1.getComponent(i);
                //megvizsgáljuk, hogy a po job szamaban megvan e a control panelen megadott prefix
                if (po.getJob().contains(b.getM().cp.jTextField1.getText())) {
//levágjuk az utolsó 3 karaktert, azaz a futót
                    try {
                        int pofuto = Integer.parseInt(po.getJob().substring(po.getJob().length() - 3, po.getJob().length()));
//ha ez nagyobb mint az eddigi legnagyobb 
                        if (pofuto > uccsofuto) {
                            uccsofuto = pofuto;
                        }
                    } catch (Exception e) {
                    }

                }

            }
        }

//ha megvan az uccso akkor bejárjuk a cp tábláját és ahol ki van töltve a pn állomás és qty oda írunk egy job ot
        for (int i = 0; i < b.getM().cp.jTable1.getRowCount(); i++) {
            try {
                if (!b.getM().cp.jTable1.getValueAt(i, 1).toString().equals("") && !b.getM().cp.jTable1.getValueAt(i, 2).toString().equals("") && !b.getM().cp.jTable1.getValueAt(i, 3).toString().equals("")) {
                    uccsofuto++;
                    b.getM().cp.jTable1.setValueAt(b.getM().cp.jTextField1.getText() + uccsofuto, i, 0);
                }
            } catch (Exception e) {
            }
        }
    }

}


