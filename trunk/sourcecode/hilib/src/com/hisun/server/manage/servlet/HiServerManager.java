package com.hisun.server.manage.servlet;


import com.hisun.hilog4j.HiLog;
import com.hisun.hilog4j.Logger;
import com.hisun.parser.svrlst.HiFrontTabNode;
import com.hisun.parser.svrlst.HiGroupNode;
import com.hisun.parser.svrlst.HiSVRLSTParser;
import com.hisun.parser.svrlst.HiServerNode;
import com.hisun.register.HiRegisterService;
import com.hisun.register.HiServiceObject;
import com.hisun.util.HiServiceLocator;
import com.hisun.util.HiStringManager;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;

import java.util.ArrayList;


public class HiServerManager {
    private static HiStringManager sm = HiStringManager.getManager();

    private static final String[] _modes = {"start", "restart", "stop", "pause", "resume", "list"};

    private static final String[][] _statusMsg = {{"0", "stoped"}, {"1", "started"}, {"2", "paused"}, {"3", "failed"}, {"4", "nodeploy"}, {null, "stoped"}};

    private static final String[][] _flagMsg = {{"0", "open"}, {"1", "open"}, {"debug", "open"}, {"INFO", "open"}, {"ERROR", "close"}, {null, "close"}};


    public static void main(String[] args) throws Exception {

        StringBuffer buffer = new StringBuffer();

        invoke(args, buffer);

        System.out.println(buffer);

    }


    public static boolean invoke(String[] args, StringBuffer buffer) throws Exception {

        int j;

        if (args.length < 3) {

            throw new Exception("config_file {start|restart|stop|pause|resume|list}  {-s|-a|-g} name");

        }


        int k = 0;

        for (k = 0; k < _modes.length; ++k) {

            if (_modes.equals(args[1])) {

                break;

            }

        }

        if (k > _modes.length) {

            throw new Exception("invalid mode:[" + args[1] + "]");

        }


        HiSVRLSTParser parser = new HiSVRLSTParser();

        HiFrontTabNode frontTab = parser.parser();

        HiServerNode server = null;

        HiGroupNode group = null;

        boolean successed = true;

        if (StringUtils.equalsIgnoreCase(args[1], "list")) {

            buffer.append(sm.getString("HiServerManager.list.head"));

            buffer.append(SystemUtils.LINE_SEPARATOR);

        }

        int count = 0;
        int failCount = 0;


        if (StringUtils.equalsIgnoreCase(args[1], "list")) {

            int i;

            buffer.append(sm.getString("HiServerManager.list.head"));

            buffer.append(SystemUtils.LINE_SEPARATOR);

            buffer.append("================================================================================");

            buffer.append(SystemUtils.LINE_SEPARATOR);

            ArrayList serverList = new ArrayList();

            if (StringUtils.equalsIgnoreCase(args[2], "-a")) {

                for (i = 0; i < frontTab.size(); ++i) {

                    group = frontTab.getGroup(i);

                    for (int j = 0; j < group.size(); ++j)

                        serverList.add(group.getServer(j));

                }

            } else if (StringUtils.equalsIgnoreCase(args[2], "-g")) {

                group = frontTab.getGroup(args[3]);

                if (group == null) {

                    throw new Exception("group:[" + args[3] + "] not existed!");

                }

                for (j = 0; j < group.size(); ++j)

                    serverList.add(group.getServer(j));

            } else if (StringUtils.equalsIgnoreCase(args[2], "-s")) {

                server = frontTab.getServer(args[3]);

                if (server == null) {

                    throw new Exception("server:[" + args[3] + "] not existed!");

                }

                serverList.add(server);

            } else {

                throw new Exception("invalid flag:[" + args[2] + "]");

            }

            for (j = 0; j < serverList.size(); ++j) {

                server = (HiServerNode) serverList.get(j);

                ++count;

                HiServiceObject service = null;

                try {

                    service = HiRegisterService.getService(server.getName());

                } catch (Exception e) {

                }

                addMsg(buffer, server.getName(), 10);


                if (service != null) {

                    addMsg(buffer, server.getAppName(), 8);

                    addMsg(buffer, server.getGrpNam(), 8);

                    addMsg(buffer, getStatusMsg(service.getRunning()), 8);

                    addMsg(buffer, service.getTime(), 16);

                } else {

                    ++failCount;

                    addMsg(buffer, server.getAppName(), 8);

                    addMsg(buffer, server.getGrpNam(), 8);

                    addMsg(buffer, "stoped", 8);

                    addMsg(buffer, " ", 16);

                }


                addMsg(buffer, server.getDesc(), 20);

                buffer.append(SystemUtils.LINE_SEPARATOR);

            }

            if (StringUtils.equalsIgnoreCase(args[1], "list")) {

                buffer.append("================================================================================");

                buffer.append(SystemUtils.LINE_SEPARATOR);

                buffer.append(sm.getString("HiServerManager.list.tail", Integer.valueOf(count), Integer.valueOf(count - failCount), Integer.valueOf(failCount)));

                buffer.append(SystemUtils.LINE_SEPARATOR);

            }

            return true;

        }


        if (StringUtils.equalsIgnoreCase(args[2], "-a")) {

            for (int i = 0; i < frontTab.size(); ++i) {

                group = frontTab.getGroup(i);

                for (j = 0; j < group.size(); ++j) {

                    ++count;

                    server = group.getServer(j);

                    if (!(invoke(args[1], server, buffer))) {

                        ++failCount;

                        successed = false;

                    }

                }

            }

        } else if (StringUtils.equalsIgnoreCase(args[2], "-g")) {

            group = frontTab.getGroup(args[3]);

            if (group == null) {

                throw new Exception("group:[" + args[3] + "] not existed!");

            }

            for (int j = 0; j < group.size(); ++j) {

                ++count;

                server = group.getServer(j);

                if (!(invoke(args[1], server, buffer))) {

                    ++failCount;

                    successed = false;

                }

            }

        } else if (StringUtils.equalsIgnoreCase(args[2], "-s")) {

            server = frontTab.getServer(args[3]);

            ++count;

            if (server == null) {

                throw new Exception("server:[" + args[3] + "] not existed!");

            }

            if (!(invoke(args[1], server, buffer))) {

                successed = false;

                ++failCount;

            }

        } else {

            throw new Exception("invalid flag:[" + args[2] + "]");

        }


        return successed;

    }


    public static boolean invoke(String mode, HiServerNode server, StringBuffer buffer) throws Exception {

        boolean existed = false;

        HiServiceLocator locator = HiServiceLocator.getInstance();

        if (!("JMS".equalsIgnoreCase(server.getType()))) try {

            locator.lookup("ibs/ejb/" + server.getName());

            existed = true;

        } catch (Exception e2) {

        }

        else existed = true;


        if (StringUtils.equalsIgnoreCase(mode, "list")) {

            HiServiceObject service = null;

            try {

                service = HiRegisterService.getService(server.getName());

            } catch (Exception e) {

            }

            addMsg(buffer, server.getName(), 10);


            if (service != null) {

                addMsg(buffer, server.getGrpNam(), 8);

                addMsg(buffer, server.getAppName(), 8);

                addMsg(buffer, getStatusMsg(service.getRunning()), 8);

                addMsg(buffer, service.getTime(), 16);

            } else {

                if (existed) {

                    addMsg(buffer, server.getGrpNam(), 8);

                    addMsg(buffer, server.getAppName(), 8);

                    addMsg(buffer, "stoped", 8);

                } else {

                    addMsg(buffer, " ", 8);

                    addMsg(buffer, " ", 8);

                    addMsg(buffer, "nodeploy", 8);

                }

                addMsg(buffer, " ", 16);

            }


            addMsg(buffer, server.getDesc(), 20);

            buffer.append(SystemUtils.LINE_SEPARATOR);


            return true;

        }


        if (!(existed)) {

            Logger log = HiLog.getErrorLogger("SYS.log");

            log.error("[" + server.getName() + "] not deployed");

            return false;

        }

        try {

            HiClientUtil.invoke(mode, server.getName(), server.getAppName(), server.getConfig_file(), server.getType());

        } catch (Exception e) {

            Logger log = HiLog.getErrorLogger("SYS.log");

            log.error("invoke server:[" + server.getName() + "] failure", e);


            return false;

        }

        return true;

    }


    public static String getStatusMsg(String status) {

        for (int i = 0; i < _statusMsg.length; ++i)

            if ((_statusMsg[i][0] == null) || (_statusMsg[i][0].equalsIgnoreCase(status))) {

                return _statusMsg[i][1];

            }

        return "";

    }


    public static String getFlagMsg(String status) {

        for (int i = 0; i < _flagMsg.length; ++i)

            if ((_flagMsg[i][0] == null) || (_flagMsg[i][0].equalsIgnoreCase(status))) {

                return _flagMsg[i][1];

            }

        return "";

    }


    public static StringBuffer addMsg(StringBuffer buffer, String data, int len) {

        buffer.append(StringUtils.rightPad(data, len, ' '));

        return buffer;

    }

}