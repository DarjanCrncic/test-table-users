package com.iscope2.tabletest.models.constants;

import java.util.HashMap;
import java.util.Map;

public enum Types {
	
	TEXT(1, "TEXT"),
    NUMBER(2, "NUMBER"),
    DATE(3, "DATE");

    private final Integer id;
    private final String text;

    private static Map<Integer, String> valueToTextMapping;

    private Types(Integer id, String text){
        this.id = id;
        this.text = text;
    }

    public static String getType(Integer i){
        if(valueToTextMapping == null){
            initMapping();
        }
        return valueToTextMapping.get(i);
    }

    private static void initMapping(){
        valueToTextMapping = new HashMap<>();
        for(Types s : values()){
            valueToTextMapping.put(s.id, s.getText());
        }
    }

    public Integer getId(){
        return id;
    }

    public String getText(){
        return text;
    }
}
