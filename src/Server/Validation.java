/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Server;

/**
 *
 * @author hung
 */

import java.rmi.*;

public interface Validation extends Remote {
    public String Validate(int[] sig) throws RemoteException;
}
