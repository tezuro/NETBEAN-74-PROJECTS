/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io;

/**
 *
 * @author tezuro
 */
public abstract class SocketBinding {

    public final static String X_CONSTANT = "X";
    public final static String Y_CONSTANT = "Y";
    protected String id = "ID";

    public SocketBinding(String idd) {
        id = idd;
    }

    /**
     * String aufbau: ID|X/Y|DoubleString
     *
     * @param line
     * @return 
     */
    public static String[] getDataFromLine(String line) {
        char[] c = line.toCharArray();
        String[] data = new String[10];
        int index = 0;
        int dataCount = 0;
        for (int i = 0; i < c.length; i++) {
            if (c[i] == '|') {
                data[dataCount] = line.substring(index, i);
                index = i + 1;
                dataCount++;
            }
        }
        return data;
    }
}
