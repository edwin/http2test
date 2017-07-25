package com.gnv.edw;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 *  com.gnv.edw.Main
 * </pre>
 *
 * @author edwin < edwinkun at gmail dot com >
 * Jul 25, 2017 5:19:50 PM
 *
 */
public class Main {

    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.execute();
    }
    
    private void execute() throws Exception {
        ExecutorService threadExecutor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 500; i++) {
            threadExecutor.execute(new HTTP1Test(i));
        }
        for (int i = 0; i < 500; i++) {
            threadExecutor.execute(new HTTP2Test(i));
        }
    }
    
    private class HTTP1Test implements Runnable {
        int i = 0;

        public HTTP1Test() {
        }
        
        public HTTP1Test(int i) {
            this.i = i;
        }
        
        @Override
        public void run() {
            long lo = System.currentTimeMillis();
            StringBuffer output = new StringBuffer();
            Process p;
            try {
                p = Runtime.getRuntime().exec("E:\\software\\curl\\bin\\curl -I http://128.199.177.158:8080/Sweet-Bites,-Greentea-Brownies-with-Cashew-Ovomaltine-banner1.png");
                p.waitFor();
                BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    output.append(line + "\n");
                }
                System.out.println(i+" "+output.toString().split("\n")[0] + (System.currentTimeMillis() - lo));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class HTTP2Test implements Runnable {

        int i = 0;

        public HTTP2Test() {
        }
        
        public HTTP2Test(int i) {
            this.i = i;
        }
        
        @Override
        public void run() {
            long lo = System.currentTimeMillis();
            StringBuffer output = new StringBuffer();
            Process p;
            try {
                p = Runtime.getRuntime().exec("E:\\software\\curl\\bin\\curl -I --http2 https://128.199.177.158:8443/Sweet-Bites,-Greentea-Brownies-with-Cashew-Ovomaltine-banner1.png -k");
                p.waitFor();
                BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    output.append(line + "\n");
                }
                System.out.println(i+" "+output.toString().split("\n")[0] + (System.currentTimeMillis() - lo));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
