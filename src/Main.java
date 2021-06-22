import Excel.ImportEcxel;
import Functions.CheckTarget;
import TestCase.General;
import TestCase.TestCase;
import Utilities.*;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;


public class Main {

    public static void main(String[] args) throws Exception {
//        int index = 46; // giá trị dòng thứ i tùy ý cần test trong bảng excel, chọn i > 1
//        String code = getCodeFromExcel(index - 1);
//
//        Client client1 = new Client("Client1");
//        client1.login("1300");
//        client1.enterGiftCode_directly("code"); // chọn trực tiếp icon GiftCode tại màn hình lobby
////        client1.enterGiftCode_settings(code); // chọn GiftCode tại màn hình Settings

//        ZPCheat cheat = new ZPCheat(); // khởi tạo tool cheat
//        ZPCheat.cheatData(2); // nhận tham số là test case id cần cheat
//
//        Bet bet = new Bet(client1);
//        bet.clickBet();
//        bet.clickChannel("manila");

//         generate script
        String[] testcase = new String[]{"CheckChannel", "CheckCalculateGold"};
        ImportEcxel excelFile = new ImportEcxel(testcase[1]+".xlsx");
        ArrayList<General> general = excelFile.getGeneral();
        ArrayList<TestCase> tc = excelFile.getTestCase();
        for (TestCase item : tc) {
            System.out.println(item.getName() + " " + item.getNumberClient());
            String h = " ";
//            for (Step s : item.getSteps())
//                h += item.generateTestScript(s);
            h+= item.generateAllScript(item.getSteps()) ;
            item.writer(h, testcase[1]);
        }


        // run test
//        ZPCheat cheat = new ZPCheat(); // khởi tạo tool cheat
//        ZPCheat.cheatData(31); // nhận tham số là test case id cần cheat
//

//        Client client1 = new Client("Client1");
//        System.out.println("Client 1 Infor: \n" + client1.getUserInfo().toString());
//
//        System.out.println("gold: " + client1.getUserGold());
//        client1.checkTarget.printResult(10);

//        Method methodG = CheckTarget.class.getDeclaredMethod("checkExpectedGold", int.class);
//        methodG.setAccessible(true);
//        methodG.invoke(client1.checkTarget, 10);

        //in bug vao word
//        final int EXPECTED_GOLD = 10;
//        if (!client1.checkTarget.checkExpectedGold(EXPECTED_GOLD)) {
//            String bugName = "bug";
//            File out = new File("C:\\Users\\LAP60536_Local\\Downloads\\Exercise-main\\Exercise-main\\Bugs\\" + bugName + ".png");
//            if (out.exists())
//                out.delete();
//
//            client1.capScreen(bugName);
//            if (out.exists()) {
//                client1.wordReport.addImagesToWordDocument(out);
//            } else {
//                System.out.println(out.getAbsolutePath() + " is not existed!");
//            }
//        } else {
//            System.out.println("Reach the expected gold");
//        }


//        Thread.sleep(2000);
//        System.out.println(client1.adbLog.getAdbLog().toString());


//        Client client2 = new Client("Client2");
//        System.out.println("Client 2 Infor: \n" + client2.getUserInfo().toString());
////
//        Method method = Client.class.getDeclaredMethod("openGame", null);
//        method.setAccessible(true);
//        method.invoke(client1, null);
//
//        Method method1 = Client.class.getDeclaredMethod("login", String.class);
//        method1.setAccessible(true);
//        method1.invoke(client1, "100");
//
//        System.out.println(client1.getUserInfo().toString());
//
//        Method method2 = Bet.class.getDeclaredMethod("clickBet", null);
//        method2.setAccessible(true);
//        method2.invoke(client1.bet, null);
//
//        Method method3 = Bet.class.getDeclaredMethod("clickChannel", String.class);
//        method3.setAccessible(true);
//        method3.invoke(client1.bet,"manila");
//
//        Method method4 = ZPCheat.class.getDeclaredMethod("closeWeb",null);
//        method4.setAccessible(true);
//        method4.invoke(cheat,null);
//        Method method5 = Client.class.getDeclaredMethod("closeGame", null);
//        method5.setAccessible(true);
//        method5.invoke(client1, null);
    }

}

