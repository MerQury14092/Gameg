package org.game.engine.protocol.core;

import java.util.HashMap;
import java.util.Map;

public class ClArgs {
    public static final Map<String, Object> options;

    static {
        options = new HashMap<>();
    }

    public static void init(String[] args){
        for(var arg: args){
            if(arg.charAt(0)=='-')
                parseFlagOrOption(arg);
        }
    }

    private static void parseFlagOrOption(String arg){
        if(arg.split("=").length > 1)
            parseOption(arg.split("=")[0].substring(1), arg.split("=")[1]);
        else
            // TODO: Разработать функционал флагов
            throw new RuntimeException("non implemented");
    }

    private static void parseOption(String name, String value){
        if(isInt(value))
            options.put(name, Integer.parseInt(value));
        else if(isDoub(value))
            options.put(name, Double.parseDouble(value));
        else
            options.put(name, value);
    }

    private static boolean isInt(String str){
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException ew){
            return false;
        }
        return true;
    }
    private static boolean isDoub(String str){
        try {
            Double.parseDouble(str);
        } catch (NumberFormatException ew){
            return false;
        }
        return true;
    }
}
