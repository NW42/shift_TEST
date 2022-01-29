/**
 * @author nw42
 * @version 0.5.1
 */

public class Shift_TEST {
    static int sortReg = 1; // 1 - inc / 0 - dec
    static int dataReg = 1; // 1 - int / 0 - String
    static String outDataName;
    static String[] inDataNames = new String[0];

    public static void main(String[] args) {
        if (!parseArguments(args)){
            System.out.println("Incorrect arguments sequence");
            System.exit(0);
        }
        MergeSortSHIFT merge = new MergeSortSHIFT(sortReg, dataReg, outDataName, inDataNames);
        if (merge.doMergeSort()) {
            merge.sendSolution();
            System.out.println("Output file was created");
        }
        else
            System.out.println("Output file wasn't created");
    }

    private static boolean parseArguments(String[] args){
        if ((args.length < 3))
            return false;
        int requiredParams = 0;
        boolean isDataReg = false;
        for (int i = 0; i < args.length; i++) {
            switch (args[i]){
                case "-a" -> sortReg = 1;
                case "-d" -> sortReg = 0;// добавить тернарный оператор
                case "-i" -> {
                    dataReg = 1;
                    if (!isDataReg){
                        requiredParams++;
                        isDataReg = true;
                    }
                }
                case "-s" ->{
                   dataReg = 0;
                    if (!isDataReg){
                        requiredParams++;
                        isDataReg = true;
                    }
                }
                default -> {
                    if (outDataName == null){
                        if ((!(requiredParams++ == 1)) || (i == args.length - 1))
                            return false;
                        outDataName = args[i];
                        inDataNames = new String[args.length - i - 1];
                    }
                    else {
                        if (!(requiredParams++ > 1))
                            return false;
                        inDataNames[requiredParams - 3] = args[i];
                    }
                }
            }
        }
        return true;
    }
}
