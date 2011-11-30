/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Biometric;

/**
 *
 * @author hung
 */

import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.lang.Math.*;

public class Image {
    final int NUM_EYES = 3;
    final int NUM_SAMPLES = 500;

    BufferedImage img[] = new BufferedImage[NUM_EYES];
    int[][] signature = new int[NUM_EYES][NUM_SAMPLES];

    public void init() {
        for (int i = 0; i < NUM_EYES; i++) {
            img[i] = new BufferedImage(100, 100, BufferedImage.TYPE_BYTE_GRAY);
        }
    }

    public void loadDatabase() {
        try {
            img[0] = ImageIO.read(new File("eye1.jpg"));
            img[1] = ImageIO.read(new File("eye2.jpg"));
            img[2] = ImageIO.read(new File("eye3.jpg"));
        } catch (IOException e) {
            System.out.println(e);
        }

        for (int i = 0; i < NUM_EYES; i++) {
            signature[i] = DeriveEyeSignature(img[i]);
        }
    }

    public int[] DeriveEyeSignature(BufferedImage eye) {
        int cx = eye.getWidth() / 2;
        int cy = eye.getHeight() / 2;

        int x, y, sample;
        double r = 0.0;
        double theta = 0.0;
        int[] sign = new int[NUM_SAMPLES];

        // This loop is the radial sampler
        for (int count = 0; count < NUM_SAMPLES; count++) {
            r += 0.1; theta += 0.075;

            x = (int) (r * java.lang.Math.cos(theta) + cx);
            y = (int) (r * java.lang.Math.sin(theta) + cy);

            // Sample and mask to byte-width
            try {
            sample = eye.getRGB(x, y) & 0xFF;
            sign[count] = sample;
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println(x + " " + y + " " + r + " " + theta);
            }
        }

        return sign;
    }

    public double[] Score (int[] sign) {
        double tmpScore;
        double score[] = new double[NUM_EYES];

        for (int i = 0; i < NUM_EYES; i++) {
            tmpScore = 0.0;

            for (int j = 0; j < NUM_SAMPLES; j++) {
                if (java.lang.Math.abs(sign[j] - signature[i][j]) < 13) tmpScore++;
            }
            score[i] = tmpScore / (double) NUM_SAMPLES;
        }


        return score;
    }

    public int getNumEyes() {
        return NUM_EYES;
    }

    public int getNumSamples() {
        return NUM_SAMPLES;
    }


}
