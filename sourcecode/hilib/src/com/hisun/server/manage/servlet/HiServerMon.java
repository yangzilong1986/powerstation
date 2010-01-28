package com.hisun.server.manage.servlet;


import com.hisun.register.HiRegisterService;

public class HiServerMon {
    private static final String[] _modes = {"open", "close"};

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {

            System.out.println("{open|close} name");


            return;
        }


        int k = 0;

        for (k = 0; k < _modes.length; ++k) {

            if (_modes.equals(args[0])) {
                break;
            }
        }

        if (k > _modes.length) {

            throw new Exception("invalid mode:[" + args[0] + "]");
        }
        try {

            if ("open".equalsIgnoreCase(args[0])) HiRegisterService.setMonSwitch(args[1], true);
            else HiRegisterService.setMonSwitch(args[1], false);
        } catch (Exception e) {
        }
    }
}