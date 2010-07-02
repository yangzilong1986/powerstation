/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.bp.db;

import java.util.List;
import pep.bp.model.TermTaskDAO;

/**
 *
 * @author Thinkpad
 */
public interface TaskService {
    public List<TermTaskDAO> getTermTask_Polling();

}
