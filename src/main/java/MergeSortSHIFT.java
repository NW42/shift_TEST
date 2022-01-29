import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MergeSortSHIFT {
    private final int SORTREG;
    private final int DATAREG;
    private final String OUTDATANAME;
    private final List<List<String>> INDATA;
    private final List<String> OUTDATA;

    public MergeSortSHIFT(int SORTREG, int DATAREG, String OUTDATANAME, String[] inDataNames) {
        this.SORTREG = SORTREG;
        this.DATAREG = DATAREG;
        this.OUTDATANAME = OUTDATANAME;
        this.INDATA = new ArrayList<>();
        this.OUTDATA = new ArrayList<>();
        for (String inDataName : inDataNames) {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(inDataName))) {
                INDATA.add(new ArrayList<>());
                while (bufferedReader.ready()) {
                    String stt = bufferedReader.readLine();
                    if (stt.indexOf(' ') < 0)
                        INDATA.get(INDATA.size() - 1).add(stt);
                }
                System.out.println("Data from " + inDataName + " has been loaded");
            } catch (FileNotFoundException e) {
                System.out.printf("File %s not found%n", inDataName);
            } catch (IOException e) {
                System.out.println("Something goes wrong (IOException)");
            }
        }
    }

    public void sendSolution(){
        try (var writer = new FileWriter(this.OUTDATANAME)){
            for (String st: OUTDATA)
                writer.write(st + System.getProperty("line.separator"));
            writer.flush();
        } catch (IOException e) {
            System.out.println("Can't write output file");
        }
    }

    public boolean doMergeSort(){
        try {
            stepMergeSort(INDATA, OUTDATA);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void stepMergeSort(List<List<String>> inData, List<String> outData){
        int[] bufi = new int[inData.size()];
        String[] bufs = new String[inData.size()];
        int sortRegIndex;
        for (int i = 0; i < bufs.length; i++) {
            if (inData.get(i).size() > 0){
                if (SORTREG == 1)
                    sortRegIndex = 0;
                else
                    sortRegIndex = inData.get(i).size() - 1;
                if (DATAREG > 0)
                    bufi[i] = Integer.parseInt(inData.get(i).get(sortRegIndex));
                else
                    bufs[i] = inData.get(i).get(sortRegIndex);
            }
        }
        int fileIndex = (SORTREG == 1) ? (DATAREG > 0) ? getMin(bufi, inData) : getMin(bufs) :
                (DATAREG > 0) ? getMax(bufi, inData) : getMax(bufs);
        if (fileIndex >= 0) {
            int dataIndex = inData.get(fileIndex).size() - 1 - SORTREG * (inData.get(fileIndex).size() - 1);
            outData.add(inData.get(fileIndex).get(dataIndex));
            inData.get(fileIndex).remove(dataIndex);
            stepMergeSort(inData, outData);
        }
    }

    private int getMin(int[] intAr, List<List<String>> indata){
        int min = -1;
        for (int i = 0; i < intAr.length; i++) {
            if (indata.get(i).size() > 0) {
                min = i;
                break;
            }
        }
        if (min >= 0) {
            for (int i = 0; i < intAr.length; i++)
                if ((indata.get(i).size() > 0) && (intAr[min] > intAr[i]))
                    min = i;
        }
        return min;
    }

    private int getMax(int[] intAr, List<List<String>> indata){
        int min = -1;
        for (int i = 0; i < intAr.length; i++) {
            if (indata.get(i).size() > 0) {
                min = i;
                break;
            }
        }
        if (min >= 0) {
            for (int i = 0; i < intAr.length; i++)
                if ((indata.get(i).size() > 0) && (intAr[min] < intAr[i]))
                    min = i;
        }
        return min;
    }

    private int getMin(String[] intAr) {
        int min = -1;
        for (int i = 0; i < intAr.length; i++) {
            if (intAr[i] != null) {
                min = i;
                break;
            }
        }
        if (min >= 0) {
            for (int i = 0; i < intAr.length; i++) {
                if ((intAr[i] != null) && ((intAr[min].length() >= intAr[i].length()) ||
                        ((intAr[min].length() == intAr[i].length()) && (intAr[min].compareTo(intAr[i]) < 0))))
                    min = i;
            }
            intAr[min] = null;
        }
        return min;
    }

    private int getMax(String[] intAr) {
        int max = -1;
        for (int i = 0; i < intAr.length; i++) {
            if (intAr[i] != null) {
                max = i;
                break;
            }
        }
        if (max >= 0) {
            for (int i = 0; i < intAr.length; i++) {
                if ((intAr[i] != null) && ((intAr[max].length() < intAr[i].length()) ||
                        ((intAr[max].length() == intAr[i].length()) && (intAr[max].compareTo(intAr[i]) < 0))))
                    max = i;
            }
            intAr[max] = null;
        }
        return max;
    }
}
