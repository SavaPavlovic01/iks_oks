package iks_oks;

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
                list.add(arg);
                if(list.size()==2){
                    int i=this.getItem();
                    int j=this.getItem();
                    
                    game.matrix[i][j].updateField();
                    
                }
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
