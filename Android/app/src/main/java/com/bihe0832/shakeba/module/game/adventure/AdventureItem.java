package com.bihe0832.shakeba.module.game.adventure;

/**
 * Created by hardyshi on 17/2/6.
 */
public class AdventureItem {
    public static final int TYPE_ID_QUESTION = 1;
    public static final int TYPE_ID_TASK = 2;

    public int id = 0;
    public int type = TYPE_ID_QUESTION;
    public String content = "出题人随机发挥吧~";

    public String getContent(){
        return content;
    }

    public int getType(){
        return type;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("id:" + id + "; ");
        sb.append("type:" + type + "; ");
        sb.append("content:" + content + ";");
        return sb.toString();
    }
}
