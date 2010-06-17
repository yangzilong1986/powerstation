package pep.bp.realinterface.conf;

import java.util.Map;

/**
 *
 * @author Thinkpad
 */
public class ProtocolDataItem {

    private String DataItemCode;
    private int Length;
    private String Format;//编码格式
    private String DefaultValue;//默认值

    private String GroupCode;//
    private String Description;
    private String GroupValue;//针对数据位定义数据项的情况
    private String IsGroupEnd;

    public ProtocolDataItem(){
        super();
    }
    
    public ProtocolDataItem(String DataItemCode,int Length,String Format,String GroupCode,String Description,String DefaultValue){
        super();
        this.DataItemCode = DataItemCode;
        this.Length = Length;
        this.Format = Format;
        this.GroupCode = GroupCode;
        this.Description = Description;
        this.DefaultValue = DefaultValue;
    }

    public ProtocolDataItem(String DataItemCode,int Length,String Format,String GroupCode,String Description,String DefaultValue,String IsGroupEnd){
        super();
        this.DataItemCode = DataItemCode;
        this.Length = Length;
        this.Format = Format;
        this.GroupCode = GroupCode;
        this.Description = Description;
        this.DefaultValue = DefaultValue;
        this.IsGroupEnd = IsGroupEnd;

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

    /**
     * @return the Description
     */
    public String getDescription() {
        return Description;
    }

    /**
     * @param Description the Description to set
     */
    public void setDescription(String Description) {
        this.Description = Description;
    }

    /**
     * @return the DefaultValue
     */
    public String getDefaultValue() {
        return DefaultValue;
    }

    /**
     * @param DefaultValue the DefaultValue to set
     */
    public void setDefaultValue(String DefaultValue) {
        this.DefaultValue = DefaultValue;
    }

    /**
     * @return the GroupValue
     */
    public String getGroupValue() {
        return GroupValue;
    }

    /**
     * @param GroupValue the GroupValue to set
     */
    public void setGroupValue(String GroupValue) {
        this.GroupValue = GroupValue;
    }

    /**
     * @return the IsGroupEnd
     */
    public String getIsGroupEnd() {
        return IsGroupEnd;
    }

    /**
     * @param IsGroupEnd the IsGroupEnd to set
     */
    public void setIsGroupEnd(String IsGroupEnd) {
        this.IsGroupEnd = IsGroupEnd;
    }
}
