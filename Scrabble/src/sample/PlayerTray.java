package sample;

public class PlayerTray {
    String tray = "";
    Letters letters;

    /**
     * get tiles at the start
     * @param letters
     */
    public PlayerTray(Letters letters){
        tray = letters.getTiles(tray);
        this.letters = letters;
        //System.out.println("PlayerTray: "+tray);
    }

    public String getTray(){
        return tray;
    }

    /**
     * remove characters of a player tray if needed
     * @param chosenString
     */
    public void removeStringChar(String chosenString) {
        char letter = chosenString.charAt(1);
        //System.out.println("Letter: "+letter);
        int index = tray.indexOf(letter);
        //System.out.println("Index: "+index);
        tray = tray.substring(0, index) + tray.substring(index + 1);
        //System.out.println(tray);
    }

    /**
     * add characters to a  player tray if needed
     */
    public void addStringChar(char currChar){
        tray = tray + currChar;
        //System.out.println("House of new Orleans: "+tray);
    }

    /**
     * add characters to a  player tray if needed
     */
    public void addTray(String tray) {
        //System.out.println("Tray: "+tray);
        this.tray = this.letters.getTiles(tray);
        //System.out.println("ons: " +this.tray);
    }

}
