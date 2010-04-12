/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.codec.protocol.gb;

/**
 *
 * @author luxiaochung
 */
public class EventCountor {
    private byte ec1;
    private byte ec2;

    public byte getEc1(){
        return ec1;
    }

    public EventCountor setEc1(byte ec1){
        this.ec1 = ec1;

        return this;
    }

    public byte getEc2(){
        return ec2;
    }

    public EventCountor setEc2(byte ec2){
        this.ec2 = ec2;

        return this;
    }

    public byte[] getValue(){
        byte[] result = new byte[2];
        result[0] = ec1;
        result[1] = ec2;

        return result;
    }

    public EventCountor setValue(byte[] value){
        if (value.length==2) {
            ec1 = value[0];
            ec2 = value[1];
        }
        return this;
    }

    @Override
    public String toString(){
        StringBuffer buff = new StringBuffer();
        buff.append("ec1=").append(ec1);
        buff.append(", ec2=").append(ec2);

        return buff.toString();
    }
}
