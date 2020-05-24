/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt;

import java.util.ArrayList;

/**
 *
 * @author gabor_hanacsek
 */
public class Teljesules implements Runnable {
    
    PlannObject p;
    
    Teljesules(PlannObject p) {
        this.p = p;
    }
    
    @Override
    public void run() {
//ki kell válogatni azokat a po-kat amikkel foglalkozni kell
        ArrayList<PlannObject> polista = new ArrayList<>();
        //betesszuk az eredetit 
        polista.add(p);
        p.setTeljesult(false);
        for (int i = 0; i < p.getbackendSheet().jPanel1.getComponentCount(); i++) {
            
            if (p.getbackendSheet().jPanel1.getComponent(i) instanceof PlannObject) {
                
                PlannObject po = (PlannObject) p.getbackendSheet().jPanel1.getComponent(i);
                po.setTeljesult(false);
//ha az eredeti po tol balra van vagy felette akkor berakjuk a listába
                if ((po.getLocation().x < p.getLocation().x) || (po.getLocation().x == p.getLocation().x && po.getLocation().y < p.getLocation().y)) {
                    polista.add(po);
                    
                }
                
            }
            
        }
//bejárjuk a polistát és megnézzük, hogy mizu
        for (PlannObject po : polista) {
            int osszterv = 0;
            int osszteny = 0;
            for (PlannObject p : polista) {
//ha egyezik a job pn ws akkor osszeadjuk a cuccokat
                if (po.getPn().equals(p.getPn()) && po.getJob().equals(p.getJob()) && po.getWorkStation().equals(p.getWorkStation())) {
                    
                    osszterv += p.getTerv();
                    osszteny += p.getTeny();
                    
                }
            }
//a végén megnézzük, hogy egyenlő e vagy nagyobb a teny, akkor a megvalosulas pipa
            if (osszteny >= osszterv) {
                
                po.setTeljesult(true);
            }
            
        }
        
    }
    
}
