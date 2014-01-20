/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io;

/**
 *
 * @author tezuro
 */
public interface IBindable {
    public static final String SKILL_MARKER = "S";
    public void proccessData(String[] data);
    public String getID();
    
}
