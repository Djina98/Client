/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.controller;

import rs.ac.bg.fon.ps.view.coordinator.MainCoordinator;
import rs.ac.bg.fon.ps.view.form.FrmSoftware;

/**
 *
 * @author Djina
 */
public class SoftwareController {

    FrmSoftware frmSoftware;
    
    public SoftwareController(FrmSoftware frmSoftware) {
        this.frmSoftware = frmSoftware;
    }

    public void openForm() {
        frmSoftware.setLocationRelativeTo(MainCoordinator.getInstance().getMainContoller().getFrmMain());
        frmSoftware.setVisible(true);
    }
    
}
