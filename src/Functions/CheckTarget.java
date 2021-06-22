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
        if(this.client.getUserGold() == gold){
            System.out.println("True");
            return true;
        }
        System.out.println("False");
        return false;
    }

//    public String printResult(int gold) throws Exception{
//        String result = " ";
//        if(!this.checkExpectedGold(gold)){
//            result = "fail";
//            String bugName = "bug";
////            this.client.capScreen(bugName);
//            Thread.sleep(1000);
//            String out = "C:\\Users\\LAP60536_Local\\Downloads\\Exercise-main\\Exercise-main\\Bugs\\" + bugName + ".png";
//            this.client.wordReport.addImagesToWordDocument(new File(out));
//        }else{
//            result = "pass";
//        }
//        return result;
//    }

    public String printResult(int EXPECTED_GOLD) throws Exception {
        Thread.sleep(10000);
        String result = "";
        if (!client.checkTarget.checkExpectedGold(EXPECTED_GOLD)) {
            result = "fail";
            String bugName = this.client.title + "BUG_Gold";
            File out = new File("C:\\Users\\LAP60536_Local\\Downloads\\Exercise-main\\Exercise-main\\Bugs\\" + bugName + ".png");
            if (out.exists())
                out.delete();

            client.capScreen(bugName);
            if (out.exists()) {
                client.wordReport.addImagesToWordDocument(out);
            } else {
                System.out.println(out.getAbsolutePath() + " is not existed!");
            }
        } else {
            result = "pass";
            System.out.println("Reach the expected gold");
        }
        return result;
    }


}
