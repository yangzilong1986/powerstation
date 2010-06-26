/*
 * 注意用法，put和setValue会将当前位置后移，而读取get总是从当前位置开始的。
 * rewind方法，将当前位置设置到最前面。
 * getValue方法返回整个序列，而不是从开始到当前位置的值。
 */

package pep.codec.protocol.gb;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import pep.codec.utils.BcdUtils;

/**
 *
 * @author luxiaochung
 */
public class PmPacketData{
    private IoBuffer dataBuff;

    public PmPacketData(){
        super();
        dataBuff = IoBuffer.allocate(0, false);
        dataBuff.setAutoExpand(true);
    }

    /**
     * 清除原来的数据
     */
    public PmPacketData clear(){
        dataBuff.clear();
        dataBuff.limit(dataBuff.position());
        return this;
    }

    /**
     * 回绕到开头，当一系列put或setValue后要从头读取时需要回绕到开头
     * @return
     */
    public PmPacketData rewind(){
        dataBuff.rewind();
        return this;
    }

    public byte[] getValue(){
        byte[] rslt = new byte[dataBuff.limit()];
        dataBuff.rewind().get(rslt);
        return rslt;
    }

    public PmPacketData setValue(byte[] bytes){
        dataBuff.clear().put(bytes);
        dataBuff.limit(dataBuff.position());
        return this;
    }

    public PmPacketData put(byte b){
        dataBuff.put(b);
        return this;
    }

    public byte get(){
        return dataBuff.get();
    }

    

    public PmPacketData putBin(long value,int length){
        byte[] b = new byte[length];
        for(int i=0;i<length;i++){
            int offset = (b.length-1-i)*8;
            b[length-1-i] = (byte)((value >>> offset) & 0xFF);
        }
        dataBuff.put(b);
        return this;
    }

    public PmPacketData put(byte[] bytes){
        dataBuff.put(bytes);
        return this;
    }

    public PmPacketData putDA(PmPacketDA da){
        dataBuff.put(da.getValue());
        return this;
    }

    public PmPacketData getDA(PmPacketDA da){
        byte[] bytes = new byte[2];
        dataBuff.get(bytes);
        da.setValue(bytes);
        return this;
    }
    
    public PmPacketData putDT(PmPacketDT dt){
        dataBuff.put(dt.getValue());
        return this;
    }
    
    public PmPacketData getDT(PmPacketDT dt){
        byte[] bytes = new byte[2];
        dataBuff.get(bytes);
        dt.setValue(bytes);
        return this;
    }

    public void putWord(int value){
        dataBuff.put((byte)(value % 0x100));
        dataBuff.put((byte)(value / 0x100));
    }

    public int getWord(){
        byte valueLow = dataBuff.get();
        byte valueHigh = dataBuff.get();
        return valueHigh*0x100 + valueLow;
    }

    public long getBin(int len){
        long value = 0;
        for(int i=0;i< len;i++){
            byte data = dataBuff.get();
            value +=  data << (8*i);
        }
        return value;
    }

    public PmPacketData putA1(DataTypeA1 a1){
        dataBuff.put(a1.getArray());
        return this;
    }
    
    public DataTypeA1 getA1(){
        byte[] array = new byte[6];
        dataBuff.get(array);
        return new DataTypeA1(array);
    }
    
    public PmPacketData putA2(DataTypeA2 a2){
        dataBuff.put(a2.getArray());
        return this;
    }
    
    public DataTypeA2 getA2(){
        byte[] array = new byte[6];
        dataBuff.get(array);
        return new DataTypeA2(array);
    }

    public PmPacketData putA3(DataTypeA3 a3){
        dataBuff.put(a3.getArray());
        return this;
    }
    
    public DataTypeA3 getA3(){
        byte[] array = new byte[4];
        dataBuff.get(array);
        return new DataTypeA3(array);
    }
    
    public PmPacketData putA4(DataTypeA4 a4){
        dataBuff.put(a4.getArray());
        return this;
    }

    public DataTypeA4 getA4(){
        byte[] array = new byte[1];
        dataBuff.get(array);
        return new DataTypeA4(array);
    }
    
    public PmPacketData putA5(DataTypeA5 a5){
        dataBuff.put(a5.getArray());
        return this;
    }

    public DataTypeA5 getA5(){
        byte[] array = new byte[2];
        dataBuff.get(array);
        return new DataTypeA5(array);
    }
    
    public PmPacketData putA6(DataTypeA6 a6){
        dataBuff.put(a6.getArray());
        return this;
    }

    public DataTypeA6 getA6(){
        byte[] array = new byte[2];
        dataBuff.get(array);
        return new DataTypeA6(array);
    }
    
    public PmPacketData putA7(DataTypeA7 a7){
        dataBuff.put(a7.getArray());
        return this;
    }
    
    public DataTypeA7 getA7(){
        byte[] array = new byte[2];
        dataBuff.get(array);
        return new DataTypeA7(array);
    }
    
    public PmPacketData putA8(DataTypeA8 a8){
        dataBuff.put(a8.getArray());
        return this;
    }
    
    public DataTypeA8 getA8(){
        byte[] array = new byte[2];
        dataBuff.get(array);
        return new DataTypeA8(array);
    }

    public PmPacketData putA9(DataTypeA9 a9){
        dataBuff.put(a9.getArray());
        return this;
    }

    public DataTypeA9 getA9(){
        byte[] array = new byte[3];
        dataBuff.get(array);
        return new DataTypeA9(array);
    }

    public PmPacketData putA10(DataTypeA10 a10){
        dataBuff.put(a10.getArray());
        return this;
    }

    public DataTypeA10 getA10(){
        byte[] array = new byte[3];
        dataBuff.get(array);
        return new DataTypeA10(array);
    }

    public PmPacketData putA11(DataTypeA11 a11){
        dataBuff.put(a11.getArray());
        return this;
    }

    public DataTypeA11 getA11(){
        byte[] array = new byte[4];
        dataBuff.get(array);
        return new DataTypeA11(array);
    }

    public PmPacketData putA12(DataTypeA12 a12){
        dataBuff.put(a12.getArray());
        return this;
    }

    public DataTypeA12 getA12(){
        byte[] array = new byte[6];
        dataBuff.get(array);
        return new DataTypeA12(array);
    }

    public PmPacketData putA13(DataTypeA13 a13){
        dataBuff.put(a13.getArray());
        return this;
    }

    public DataTypeA13 getA13(){
        byte[] array = new byte[4];
        dataBuff.get(array);
        return new DataTypeA13(array);
    }

    public PmPacketData putA14(DataTypeA14 a14){
        dataBuff.put(a14.getArray());
        return this;
    }

    public DataTypeA14 getA14(){
        byte[] array = new byte[5];
        dataBuff.get(array);
        return new DataTypeA14(array);
    }

    public PmPacketData putA15(DataTypeA15 a15){
        dataBuff.put(a15.getArray());
        return this;
    }

    public DataTypeA15 getA15(){
        byte[] array = new byte[5];
        dataBuff.get(array);
        return new DataTypeA15(array);
    }

    public PmPacketData putA16(DataTypeA16 a16){
        dataBuff.put(a16.getArray());
        return this;
    }

    public DataTypeA16 getA16(){
        byte[] array = new byte[4];
        dataBuff.get(array);
        return new DataTypeA16(array);
    }

    public PmPacketData putA17(DataTypeA17 a17){
        dataBuff.put(a17.getArray());
        return this;
    }

    public DataTypeA17 getA17(){
        byte[] array = new byte[4];
        dataBuff.get(array);
        return new DataTypeA17(array);
    }

    public PmPacketData putA18(DataTypeA18 a18){
        dataBuff.put(a18.getArray());
        return this;
    }

    public DataTypeA18 getA18(){
        byte[] array = new byte[3];
        dataBuff.get(array);
        return new DataTypeA18(array);
    }

    public PmPacketData putA19(DataTypeA19 a19){
        dataBuff.put(a19.getArray());
        return this;
    }

    public DataTypeA19 getA19(){
        byte[] array = new byte[2];
        dataBuff.get(array);
        return new DataTypeA19(array);
    }

    public PmPacketData putA20(DataTypeA20 a20){
        dataBuff.put(a20.getArray());
        return this;
    }

    public DataTypeA20 getA20(){
        byte[] array = new byte[3];
        dataBuff.get(array);
        return new DataTypeA20(array);
    }

    public PmPacketData putA21(DataTypeA21 a21){
        dataBuff.put(a21.getArray());
        return this;
    }

    public DataTypeA21 getA21(){
        byte[] array = new byte[2];
        dataBuff.get(array);
        return new DataTypeA21(array);
    }

    public PmPacketData putA22(DataTypeA22 a22){
        dataBuff.put(a22.getArray());
        return this;
    }

    public DataTypeA22 getA22(){
        byte[] array = new byte[1];
        dataBuff.get(array);
        return new DataTypeA22(array);
    }

    public PmPacketData putA23(DataTypeA23 a23){
        dataBuff.put(a23.getArray());
        return this;
    }

    public DataTypeA23 getA23(){
        byte[] array = new byte[3];
        dataBuff.get(array);
        return new DataTypeA23(array);
    }

    public PmPacketData putA24(DataTypeA24 a24){
        dataBuff.put(a24.getArray());
        return this;
    }

    public DataTypeA24 getA24(){
        byte[] array = new byte[2];
        dataBuff.get(array);
        return new DataTypeA24(array);
    }

    public PmPacketData putA25(DataTypeA25 a25){
        dataBuff.put(a25.getArray());
        return this;
    }

    public DataTypeA25 getA25(){
        byte[] array = new byte[3];
        dataBuff.get(array);
        return new DataTypeA25(array);
    }

    public PmPacketData putA26(DataTypeA26 a26){
        dataBuff.put(a26.getArray());
        return this;
    }

    public DataTypeA26 getA26(){
        byte[] array = new byte[2];
        dataBuff.get(array);
        return new DataTypeA26(array);
    }

    public PmPacketData putA27(DataTypeA27 a27){
        dataBuff.put(a27.getArray());
        return this;
    }

    public DataTypeA27 getA27(){
        byte[] array = new byte[4];
        dataBuff.get(array);
        return new DataTypeA27(array);
    }

    public PmPacketData putAscii(String str, int len){
        byte[] temp = str.getBytes(Charset.forName("US-ASCII"));
        
        int n;
        if (len<=temp.length)
            n = len;
        else
            n = temp.length;
        byte[] bytes = new byte[len];
        for(int i=0; i<len; i++) bytes[i]=0;
        System.arraycopy(temp, 0, bytes, 0, n);
        
        dataBuff.put(bytes);
        return this;
    }
    
    public String getAscii(int len){
        try {
            String str = dataBuff.getString(len, Charset.forName("US-ASCII").newDecoder());
            return str;
        } catch (CharacterCodingException ex) {
            Logger.getLogger(PmPacketData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    public PmPacketData putBS8(String bs8){
        dataBuff.put(BcdUtils.bitSetStringToBytes(bs8));
        return this;
    }

    public String getBS8(){
        byte[] bytes = new byte[1];
        dataBuff.get(bytes);
        return BcdUtils.bytesToBitSetString(bytes);
    }

    public PmPacketData putBS24(String bs8){
        dataBuff.put(BcdUtils.bitSetStringToBytes(bs8));
        return this;
    }

    public String getBS24(){
        byte[] bytes = new byte[3];
        dataBuff.get(bytes);
        return BcdUtils.bytesToBitSetString(bytes);
    }

    public PmPacketData putBS64(String bs8){
        dataBuff.put(BcdUtils.bitSetStringToBytes(bs8));
        return this;
    }

    public String getBS64(){
        byte[] bytes = new byte[8];
        dataBuff.get(bytes);
        return BcdUtils.bytesToBitSetString(bytes);
    }

    public PmPacketData putIPPORT(String ipport){
        dataBuff.put(BcdUtils.IpPortStringToBytes(ipport));
        return this;
    }

    public String getIPPORT(){
        byte[] bytes = new byte[6];
        dataBuff.get(bytes);
        return BcdUtils.bytesToIpPortString(bytes);
    }

    public PmPacketData putIP(String ip){
        dataBuff.put(BcdUtils.IPStringToBytes(ip));
        return this;
    }

    public String getIP(){
        byte[] bytes = new byte[4];
        dataBuff.get(bytes);
        return BcdUtils.bytesToIpString(bytes);
    }

    public PmPacketData putTEL(String telecode){
        dataBuff.put(BcdUtils.TelStringToBytes(telecode));
        return this;
    }

    public String getTEL(){
        byte[] bytes = new byte[8];
        dataBuff.get(bytes);
        return BcdUtils.bytesToTelString(bytes);
    }

    @Override
    public String toString(){
       // int pos = dataBuff.position();
        String result = BcdUtils.binArrayToString(dataBuff.array());
      //  dataBuff.position(pos);
        return result;

    }

    public boolean HaveDate(){
        return (this.dataBuff.position() < this.dataBuff.limit());
    }
}
