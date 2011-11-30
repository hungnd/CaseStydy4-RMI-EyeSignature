/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Client;

/**
 *
 * @author hung
 */

import Biometric.Image;
import java.io.*;
import javax.imageio.*;
import java.awt.image.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import Server.Validation;
import java.rmi.Naming;

public class Client {
    public static void main(String args[]) {

        try {
            //Registry registry = LocateRegistry.getRegistry(9999);
            Validation v = (Validation) Naming.lookup("rmi://localhost:9999/ebs");

            Image img = new Image();
            img.init();

            BufferedImage eye = new BufferedImage(100, 100, BufferedImage.TYPE_BYTE_GRAY);
            eye = ImageIO.read(new File("eye1.jpg"));

            // Derive this eye's Signature
            int[] signature = new int[img.getNumSamples()];
            signature = img.DeriveEyeSignature(eye);


            // Perform RMI call here
            String auth = v.Validate(signature);
            System.out.println(auth);
            /*
            if (b) {
                System.out.println("Authenticated.");
            } else {
                System.out.println("Denied Access.");
            }
            */
        } catch (IOException e) {
            System.out.println(e);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
