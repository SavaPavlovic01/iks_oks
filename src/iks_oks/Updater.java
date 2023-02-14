package iks_oks;

public class Updater extends Thread {
    
    private Listener listener;
    private Game game;

    public Updater(Game game){
        this.game=game;
        this.listener=new Listener(game);
    }

    @Override
    public void run(){
        while(true){
            while(listener.list.size()<2);
            int i=listener.getItem();
            int j=listener.getItem();
            game.matrix[i][j].updateField();
            game.updateM(i, j);
        }
    }
}
