/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.bp.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Thinkpad
 */
public class DataItemRowMapper implements RowMapper {
    public Object mapRow(ResultSet rs, int index) throws SQLException{
        DataItemDAO dataItem = new DataItemDAO();
        dataItem.setDataItemCode("dataitem_code");
        dataItem.setDataItemFormat(rs.getString("dataitem_format"));
        dataItem.setDataItemName(rs.getString("dataitem_name"));
        dataItem.setDataItemUnit(rs.getString("dataitem_unit"));
        dataItem.setDataItemValue_Code(rs.getString("dataitem_value_type"));
        dataItem.setDataItemValue_Type(rs.getString("dataitem_value_code"));
        return dataItem;
    }
}
