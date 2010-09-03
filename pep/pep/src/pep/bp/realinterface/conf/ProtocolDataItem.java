package pep.bp.realinterface.conf;


/**
 *
 * @author Thinkpad
 */
public class ProtocolDataItem {

    private String dataItemCode;
    private int length;
    private String format;//编码格式
    private String defaultValue;//默认值

    private int bitNumber;//组数据项位数
    private String description;
    private String groupValue;//针对数据位定义数据项的情况
    private String isGroupEnd="0";
    private String isTd = "0";//0,1 表示是否是数据时标
    private String LowBitBefore = "1";//低位在前


    public ProtocolDataItem(){
        super();
    }
    
    public ProtocolDataItem(String DataItemCode,int Length,String Format,int BitNumber,String Description,String DefaultValue){
        super();
        this.dataItemCode = DataItemCode;
        this.length = Length;
        this.format = Format;
        this.bitNumber = BitNumber;
        this.description = Description;
        this.defaultValue = DefaultValue;
    }

    public ProtocolDataItem(String DataItemCode,int Length,String Format,int BitNumber,String Description,String DefaultValue,String IsGroupEnd){
        super();
        this.dataItemCode = DataItemCode;
        this.length = Length;
        this.format = Format;
        this.bitNumber = BitNumber;
        this.description = Description;
        this.defaultValue = DefaultValue;
        this.isGroupEnd = IsGroupEnd;

    }

    public ProtocolDataItem(String DataItemCode,int Length,String Format,int BitNumber,
            String Description,String DefaultValue,String IsGroupEnd,String LowBitBefore){
        super();
        this.dataItemCode = DataItemCode;
        this.length = Length;
        this.format = Format;
        this.bitNumber = BitNumber;
        this.description = Description;
        this.defaultValue = DefaultValue;
        this.isGroupEnd = IsGroupEnd;
        this.LowBitBefore = LowBitBefore;

    }
    /**
     * @return the DataItemCode
     */
    public String getDataItemCode() {
        return dataItemCode;
    }

    /**
     * @return the Length
     */
    public int getLength() {
        return length;
    }

    /**
     * @return the Format
     */
    public String getFormat() {
        return format;
    }

    /**
     * @param DataItemCode the DataItemCode to set
     */
    public void setDataItemCode(String DataItemCode) {
        this.dataItemCode = DataItemCode;
    }

    /**
     * @param Length the Length to set
     */
    public void setLength(int Length) {
        this.length = Length;
    }

    /**
     * @param Format the Format to set
     */
    public void setFormat(String Format) {
        this.format = Format;
    }

    /**
     * @return the BitNumber
     */
    public int getBitNumber() {
        return bitNumber;
    }

    /**
     * @param GroupCode the GroupCode to set
     */
    public void setBitNumber(int BitNumber) {
        this.bitNumber = BitNumber;
    }

    /**
     * @return the Description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param Description the Description to set
     */
    public void setDescription(String Description) {
        this.description = Description;
    }

    /**
     * @return the DefaultValue
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * @param DefaultValue the DefaultValue to set
     */
    public void setDefaultValue(String DefaultValue) {
        this.defaultValue = DefaultValue;
    }

    /**
     * @return the GroupValue
     */
    public String getGroupValue() {
        return groupValue;
    }

    /**
     * @param GroupValue the GroupValue to set
     */
    public void setGroupValue(String GroupValue) {
        this.groupValue = GroupValue;
    }

    /**
     * @return the IsGroupEnd
     */
    public String getIsGroupEnd() {
        return isGroupEnd;
    }

    /**
     * @param IsGroupEnd the IsGroupEnd to set
     */
    public void setIsGroupEnd(String IsGroupEnd) {
        this.isGroupEnd = IsGroupEnd;
    }

    /**
     * @return the IsTd
     */
    public String getIsTd() {
        return isTd;
    }

    /**
     * @param IsTd the IsTd to set
     */
    public void setIsTd(String IsTd) {
        this.isTd = IsTd;
    }

    /**
     * @return the LowBitBefore
     */
    public String getLowBitBefore() {
        return LowBitBefore;
    }

    /**
     * @param LowBitBefore the LowBitBefore to set
     */
    public void setLowBitBefore(String LowBitBefore) {
        this.LowBitBefore = LowBitBefore;
    }
}
