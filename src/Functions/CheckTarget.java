package Functions;

import Utilities.Client;

import java.io.File;

public class CheckTarget {
    Client client;
    public static final String imgPath = System.getProperty("user.dir") + "\\Image\\";

    public CheckTarget(Client client) {
        this.client = client;
    }

    public boolean checkExpectedGold(int gold) throws Exception{
        if(((int) this.client.getUserGold()) == gold){
            System.out.println("True");
            return true;
        }
        System.out.println("False");
        return false;
    }

    public String printResult(int gold) throws Exception{
        String result = " ";
        if(this.checkExpectedGold(gold) == false){
            result = "fail";
            String bugName = "bug";
            this.client.capScreen(bugName);
            Thread.sleep(1000);
            String out = "C:\\Users\\LAP60536_Local\\Downloads\\Exercise-main\\Exercise-main\\Bugs\\" + bugName + ".png";
            this.client.wordReport.addImagesToWordDocument(new File(out));
        }else{
            result = "pass";
        }
        return result;
    }

    public void printResult () throws Exception{

    }

}
