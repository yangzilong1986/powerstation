package pep.bp.realinterface.conf;


/**
 *
 * @author Thinkpad
 */
public class ProtocolDataItem {

    private String DataItemCode;
    private int Length;
    private String Format;//编码格式
    private String DefaultValue;//默认值

    private int BitNumber;//组数据项位数
    private String Description;
    private String GroupValue;//针对数据位定义数据项的情况
    private String IsGroupEnd="0";
    private String IsTd = "0";//0,1 表示是否是数据时标


    public ProtocolDataItem(){
        super();
    }
    
    public ProtocolDataItem(String DataItemCode,int Length,String Format,int BitNumber,String Description,String DefaultValue){
        super();
        this.DataItemCode = DataItemCode;
        this.Length = Length;
        this.Format = Format;
        this.BitNumber = BitNumber;
        this.Description = Description;
        this.DefaultValue = DefaultValue;
    }

    public ProtocolDataItem(String DataItemCode,int Length,String Format,int BitNumber,String Description,String DefaultValue,String IsGroupEnd){
        super();
        this.DataItemCode = DataItemCode;
        this.Length = Length;
        this.Format = Format;
        this.BitNumber = BitNumber;
        this.Description = Description;
        this.DefaultValue = DefaultValue;
        this.IsGroupEnd = IsGroupEnd;

    }

    public ProtocolDataItem(String DataItemCode,int Length,String Format,int BitNumber,String Description,String DefaultValue,String IsGroupEnd,String IsTd){
        super();
        this.DataItemCode = DataItemCode;
        this.Length = Length;
        this.Format = Format;
        this.BitNumber = BitNumber;
        this.Description = Description;
        this.DefaultValue = DefaultValue;
        this.IsGroupEnd = IsGroupEnd;
        this.IsTd = IsTd;

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
     * @return the BitNumber
     */
    public int getBitNumber() {
        return BitNumber;
    }

    /**
     * @param GroupCode the GroupCode to set
     */
    public void setBitNumber(int BitNumber) {
        this.BitNumber = BitNumber;
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

    /**
     * @return the IsTd
     */
    public String getIsTd() {
        return IsTd;
    }

    /**
     * @param IsTd the IsTd to set
     */
    public void setIsTd(String IsTd) {
        this.IsTd = IsTd;
    }
}
