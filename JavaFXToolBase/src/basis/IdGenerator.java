/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basis;

/**
 *
 * @author tezuro
 */
public final class IdGenerator {

    private static Integer ID = 0;
    private static String user = "";

    private IdGenerator() {
    }

    public static void setUser(String newUser) {
        user = newUser;
    }

    public static String getNextID() {
        ID++;
        if (ID == Integer.MAX_VALUE) {
            ID = 0;
        }
        return user + ID.toString();
    }
}
