package iks_oks;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

public class Listener extends Thread {
    
    private Game game;
    public ArrayList<Integer> list=new ArrayList<Integer>();

    public Listener(Game game){
        this.game=game;
    }

    public int getItem(){return list.remove(0);}

    @Override
    public void run(){
        while(true){
            try {
                Integer arg=(Integer)game.getIn().readObject();
                System.out.println(arg);
                if(arg==10){
                    int i= (Integer)game.getIn().readObject();
                    int j= (Integer)game.getIn().readObject();  
                    game.matrix[i][j].updateField(); 
                }
                if(arg==11){
                    game.OpWantsReset=true;
                    System.out.println("Op wants reset");
                    game.writeMessage("Opponent wants reset\n", Color.red);
                    
                    if(game.wantsReset)game.reset();
                }
                if(arg==12){
                    game.OpWantsReset=true;
                    game.reset();
                }
                if(arg==13){
                    String message=game.getIn().readObject().toString();
                    game.writeMessage(message, null);
    
                }

                /*
                list.add(arg);
                if(list.size()==2){
                    int i=this.getItem();
                    int j=this.getItem();
                    
                   
                    
                } */
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
