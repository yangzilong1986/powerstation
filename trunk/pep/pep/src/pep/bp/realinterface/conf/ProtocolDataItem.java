package pep.bp.realinterface.conf;

/**
 *
 * @author Thinkpad
 */
public class ProtocolDataItem {

    private String DataItemCode;
    private int Length;
    private String Format;//编码格式

    private String GroupCode;//

    public ProtocolDataItem(){
        super();
    }
    
    public ProtocolDataItem(String DataItemCode,int Length,String Format,String GroupCode){
        super();
        this.DataItemCode = DataItemCode;
        this.Length = Length;
        this.Format = Format;
        this.GroupCode = GroupCode;
    }
    /**
     * @return the DataItemCode
     */
    public String getDataItemCode() {
        return DataItemCode;
    }

    /**
     * @return the Length
     */
    public int getLength() {
        return Length;
    }

    /**
     * @return the Format
     */
    public String getFormat() {
        return Format;
    }

    /**
     * @param DataItemCode the DataItemCode to set
     */
    public void setDataItemCode(String DataItemCode) {
        this.DataItemCode = DataItemCode;
    }

    /**
     * @param Length the Length to set
     */
    public void setLength(int Length) {
        this.Length = Length;
    }

    /**
     * @param Format the Format to set
     */
    public void setFormat(String Format) {
        this.Format = Format;
    }

    /**
     * @return the GroupCode
     */
    public String getGroupCode() {
        return GroupCode;
    }

    /**
     * @param GroupCode the GroupCode to set
     */
    public void setGroupCode(String GroupCode) {
        this.GroupCode = GroupCode;
    }
}
