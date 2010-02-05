package com.hisun.message;

import com.hisun.exception.HiException;
import com.hisun.util.HiByteBuffer;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class HiMessage implements Serializable {
    private static final long serialVersionUID = 1509358588794274761L;
    public static final String DEFAULT_ENCODING = "UTF-8";
    public static final String MESSAGE_TYPE_MNG = "SYSMNG";
    public static final String REQUEST_RESPONSE = "SCH";
    public static final String TYPE_RESPONSE = "rp";
    public static final String TYPE_REQUEST = "rq";
    public static final String TEXT_TYPE = "ECT";
    public static final String TEXT_TYPE_XML = "text/xml";
    public static final String TEXT_TYPE_PLAIN = "text/plain";
    public static final String TEXT_TYPE_ETF = "text/etf";
    public static final String SSC = "SSC";
    public static final String CMD = "CMD";
    public static final String SRT = "SRT";
    public static final String SDT = "SDT";
    public static final String MON = "MON";
    public static final String SRN = "SRN";
    public static final String SIP = "SIP";
    public static final String OIP = "OIP";
    public static final String OPT = "OPT";
    public static final String SFD = "SFD";
    public static final String SKY = "SKY";
    public static final String SNC = "SNC";
    public static final String STC = "STC";
    public static final String PAS = "PAS";
    public static final String APP = "APP";
    public static final String STM = "STM";
    public static final String ETM = "ETM";
    public static final String TMO = "TMO";
    public static final String NTF = "NTF";
    public static final String STF = "STF";
    public static final String OFN = "OFN";
    public static final String LSH = "LSH";
    public static final String BCH = "BCH";
    public static final String SUB = "SUB";
    public static final String TLR = "TLR";
    public static final String TRAN_RDO = "RDO";
    public static final String OLD = "OLD";
    public static final String NEW = "NEW";
    public static final String SID = "SID";
    public static final String STP = "STP";
    public static final String SPI = "SPI";
    public static final String SSP = "SSP";
    public static final String SMD = "SMD";
    public static final String SSZ = "SSZ";
    public static final String RSP = "RSP";
    public static final String FID = "FID";
    public static final int FLD_NAME_LEN = 3;
    public static final int SRV_NAME_LEN = 8;
    protected String requestId;
    protected String type;
    protected String priority;
    protected String splited;
    protected String msgId;
    protected String server;
    protected String status = "N";
    protected ConcurrentHashMap head;
    protected Object body;

    public HiMessage() {
        this.head = new ConcurrentHashMap();
    }

    public HiMessage(String server, String type) {
        this.server = server;
        this.requestId = createRequestId(server);
        this.type = type;

        this.head = new ConcurrentHashMap();
    }

    public HiMessage(String server, String type, Object body) {
        this.server = server;
        this.requestId = createRequestId(server);
        this.type = type;
        this.body = body;

        this.head = new ConcurrentHashMap();
    }

    public HiMessage(HiMessage hiMsg) {
        this.requestId = hiMsg.requestId;
        this.type = hiMsg.type;
        this.msgId = hiMsg.msgId;
        this.priority = hiMsg.priority;
        this.splited = hiMsg.splited;
        this.server = hiMsg.server;
        this.status = hiMsg.status;
        this.head = new ConcurrentHashMap();
        Iterator headIt = hiMsg.head.entrySet().iterator();

        LinkedList valList = null;
        LinkedList newValList = null;

        while (headIt.hasNext()) {
            Map.Entry headEntry = (Map.Entry) headIt.next();

            valList = (LinkedList) headEntry.getValue();
            newValList = (LinkedList) valList.clone();

            this.head.put(headEntry.getKey(), newValList);
        }
        this.body = hiMsg.body;
    }

    public HiMessage(String msgStr) throws HiException {
        if (StringUtils.isEmpty(msgStr)) {
            return;
        }
        this.head = new ConcurrentHashMap();

        int pos = 0;
        int headIdx = msgStr.indexOf(10);
        int bodyLen = 0;
        if (headIdx == -1) {
            headStr = msgStr + '\t';
        } else {
            headStr = msgStr.substring(0, headIdx) + '\t';
            if (msgStr.length() > headIdx + 1) {
                this.body = msgStr.substring(headIdx + 1);
            }
        }

        headIdx = headStr.indexOf(9);
        while (headIdx != -1) {
            String fld = headStr.substring(pos, headIdx);
            if (fld.length() < 3) {
                throw new HiException("215014", String.valueOf(3), fld, msgStr);
            }
            String fldNam = fld.substring(0, 3);

            if (fldNam.equals("SID")) {
                this.requestId = fld.substring(3);
                if (this.requestId.length() > 8) {
                    this.server = this.requestId.substring(0, 8);
                } else {
                    this.server = this.requestId;
                }
            } else if (fldNam.equals("STP")) {
                this.type = fld.substring(3);
            } else if (fldNam.equals("SPI")) {
                this.priority = fld.substring(3);
            } else if (fldNam.equals("SSP")) {
                this.splited = fld.substring(3);
            } else if (fldNam.equals("SMD")) {
                this.msgId = fld.substring(3);
            } else {
                setHeadItem(fldNam, fld.substring(3));
            }
            pos = headIdx + 1;
            headIdx = headStr.indexOf(9, pos);
        }

        if (this.body == null) return;
        String bodyType = getHeadItem("ECT");
        if (StringUtils.equalsIgnoreCase(bodyType, "text/plain")) {
            setBody(new HiByteBuffer(((String) this.body).getBytes()));
        } else {
            this.body = HiETFFactory.createETF((String) this.body);
        }
    }

    public String getHeadItem(String fldName) {
        LinkedList valList = (LinkedList) this.head.get(fldName);
        if ((valList == null) || (valList.size() == 0)) {
            return null;
        }
        return ((String) valList.getLast());
    }

    public String getHeadItemRoot(String fldName) {
        LinkedList valList = (LinkedList) this.head.get(fldName);
        if ((valList == null) || (valList.size() == 0)) {
            return null;
        }
        return ((String) valList.getFirst());
    }

    public Object getObjectHeadItem(String fldName) {
        LinkedList valList = (LinkedList) this.head.get(fldName);
        if ((valList == null) || (valList.size() == 0)) {
            return null;
        }
        return valList.getLast();
    }

    public void addHeadItem(String fldName, Object fldValue) {
        List valList = (List) this.head.get(fldName);
        if (valList == null) {
            valList = new LinkedList();
            valList.add(fldValue);
            this.head.put(fldName, valList);
        } else {
            valList.add(fldValue);
        }
    }

    public void setHeadItem(String fldName, Object fldValue) {
        long endit = 0L;
        long it = System.currentTimeMillis();

        List valList = (LinkedList) this.head.get(fldName);
        if (valList == null) {
            valList = new LinkedList();
            valList.add(fldValue);
            this.head.put(fldName, valList);
        } else if (valList.size() == 0) {
            valList.add(fldValue);
        } else {
            valList.set(valList.size() - 1, fldValue);
        }

        endit = System.currentTimeMillis();
        if (endit - it <= 200L) return;
    }

    public boolean hasHeadItem(String fldName) {
        return this.head.containsKey(fldName);
    }

    public int getHeadItemValSize(String fldName) {
        List valList = (List) this.head.get(fldName);
        if (valList == null) {
            return 0;
        }

        return valList.size();
    }

    public void delHeadItem(String fldName) {
        List valList = (LinkedList) this.head.get(fldName);
        if (valList != null) {
            valList.clear();
        }
        this.head.remove(fldName);
    }

    public Object delHeadItemVal(String fldName) {
        List valList = (List) this.head.get(fldName);
        if ((valList == null) || (valList.size() == 0)) {
            return null;
        }

        return ((LinkedList) valList).removeLast();
    }

    public void setHead(ConcurrentHashMap head) {
        this.head = head;
    }

    public ConcurrentHashMap getHead() {
        return this.head;
    }

    public void clearHead() {
        List valList = null;
        Iterator it = this.head.values().iterator();
        while (it.hasNext()) {
            valList = (List) it.next();
            valList.clear();
        }

        this.head.clear();
    }

    public Object getBody() {
        return this.body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isNormal() {
        return this.status.equalsIgnoreCase("N");
    }

    public String getRequestId() {
        return this.requestId;
    }

    public String genRequestId(String id) {
        return createRequestId(id);
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public HiMessage cloneNoBody() {
        HiMessage cloneMsg = new HiMessage();

        cloneMsg.requestId = this.requestId;
        cloneMsg.type = this.type;
        cloneMsg.msgId = this.msgId;
        cloneMsg.priority = this.priority;
        cloneMsg.splited = this.splited;
        cloneMsg.server = this.server;
        cloneMsg.status = this.status;
        cloneMsg.head = new ConcurrentHashMap();

        Iterator headIt = this.head.entrySet().iterator();

        LinkedList valList = null;
        LinkedList newValList = null;

        while (headIt.hasNext()) {
            Map.Entry headEntry = (Map.Entry) headIt.next();

            valList = (LinkedList) headEntry.getValue();
            newValList = (LinkedList) valList.clone();

            cloneMsg.head.put(headEntry.getKey(), newValList);
        }

        cloneMsg.body = null;

        return cloneMsg;
    }

    public HiMessage cloneNoBodyAndRqID() {
        HiMessage cloneMsg = cloneNoBody();

        cloneMsg.requestId = createRequestId(this.server);

        return cloneMsg;
    }

    public HiETF getETFBody() {
        return ((HiETF) this.body);
    }

    public String toString() {
        StringBuffer msgbuf = new StringBuffer();

        msgbuf.append("SID");
        msgbuf.append(this.requestId);
        msgbuf.append("\t");
        msgbuf.append("STP");
        msgbuf.append(this.type);
        msgbuf.append("\t");

        msgbuf.append("SSZ");
        if (this.body != null) {
            msgbuf.append(StringUtils.leftPad(String.valueOf(this.body.toString().length()), 12, "0"));
        } else {
            msgbuf.append(StringUtils.repeat("0", 12));
        }
        msgbuf.append("\t");

        if (this.head != null) {
            Iterator headIt = this.head.entrySet().iterator();

            LinkedList valList = null;

            while (headIt.hasNext()) {
                Map.Entry headEntry = (Map.Entry) headIt.next();

                valList = (LinkedList) headEntry.getValue();
                if (valList.isEmpty()) continue;
                if (StringUtils.equals((String) headEntry.getKey(), "SSZ")) continue;
                msgbuf.append(headEntry.getKey());

                msgbuf.append(String.valueOf(valList.getLast()));

                msgbuf.append("\t");
            }

        }

        msgbuf.setCharAt(msgbuf.length() - 1, '\n');

        if (this.body != null) {
            msgbuf.append(this.body);
        }
        return msgbuf.toString();
    }

    private String createRequestId(String server) {
        return HiMsgIdManager.getRequestId(server);
    }
}