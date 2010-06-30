/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.bp.db;

import pep.bp.model.TermTask;

/**
 *
 * @author Thinkpad
 */
public interface TaskService {
    public TermTask getTermTask(String logicAddress,String gp_char,int gp_sn);

}
