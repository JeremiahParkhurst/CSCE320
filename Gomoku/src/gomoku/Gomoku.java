package gomoku;

/**
 * This class will open the TitleView and instantiate the TitleViewController.
 * This is where the game will start, where the user can either play a 
 * single player game or a multi player game.
 */
public class Gomoku {
    public static String IPaddress;
    public static void main(String[] args) {
        String ip = "";
        if(args.length >= 1){
            IPaddress = ip;
            ip = args[0];
        }
        else{
            IPaddress = ip;
            ip = "152.117.242.102";
        }
        TitleViewController vc = new TitleViewController(ip);
        IPaddress = ip;
        vc.showView();
    }
}