/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.bp.db;

import java.util.List;
import pep.bp.model.PS;

/**
 *
 * @author Thinkpad
 */
public interface PSService {
    public List<PS> getTestPSList(String testDay,String testHour);

    public int getPsId(String LogicAddr, int GP_SN);

}
