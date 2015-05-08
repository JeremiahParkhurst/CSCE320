package gomoku;

/**
 * This class will open the TitleView and instantiate the TitleViewController.
 * This is where the game will start, where the user can either play a 
 * single player game or a multi player game.
 */
public class Gomoku {

    public static void main(String[] args) {
        String ip = "";
        if(args.length >= 1){
            ip = args[0];
        }
        else{
            ip = "152.117.180.235";
        }
        TitleViewController vc = new TitleViewController(ip);
        vc.showView();
    }
}