package main;

import Settings.Setting;

import java.io.IOException;
import java.util.ArrayList;

class MyThread extends Thread{
    public void run(){
        System.out.println("shut down hook task completed..");
    }
}

public class Main {

    private static ArrayList<Adapter> adapters = new ArrayList<Adapter>();

    public static void main(String[] vals) {
        System.out.println("Hi!!!!");
        Runtime r=Runtime.getRuntime();
        r.addShutdownHook(new MyThread());

        init();
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void init()  {
        try {
            //initConfigAccess();
            Setting.Create("C:\\MyConfig.xml");

            for(int i = 0; i < Setting.getCountAdapters(); i++){
                try{
                    Adapter adapter = new Adapter(Setting.getSetting(i));
                    adapters.add(adapter);
                    adapter.start();
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
