package Executor;

import java.util.Arrays;  
import java.util.concurrent.Callable;  
  
public class Calculator implements Callable<Integer[]> {  
  
    /** ����������� */  
    private int[] values;  
  
    /** ��ʼ���� */  
    private int startIndex;  
  
    /** �������� */  
    private int endIndex;  
  
    /** 
     * @param values 
     * @param startIndex 
     * @param endIndex 
     */  
    public Calculator(int[] values, int startIndex, int endIndex) {  
        this.values = values;  
        this.startIndex = startIndex;  
        this.endIndex = endIndex;  
    }  
  
    public Integer[] call() throws Exception {  
  
        // ��ָ����Χ�����ݸ��Ƶ�ָ����������ȥ  
        int[] subValues = new int[endIndex - startIndex + 1];  
        System.arraycopy(values, startIndex, subValues, 0, endIndex  
                - startIndex + 1);  
  
        Arrays.sort(subValues);  
  
        // �����������������ݣ�ȡ��top 100 �����ء�  
        Integer[] top100 = new Integer[100];  
        for (int i = 0; i < 100; i++) {  
            top100[i] = subValues[subValues.length - i - 1];  
        }  
        return top100;  
    }  
  
    /** 
     * @return the values 
     */  
    public int[] getValues() {  
        return values;  
    }  
  
    /** 
     * @param values 
     *            the values to set 
     */  
    public void setValues(int[] values) {  
        this.values = values;  
    }  
  
    /** 
     * @return the startIndex 
     */  
    public int getStartIndex() {  
        return startIndex;  
    }  
  
    /** 
     * @param startIndex 
     *            the startIndex to set 
     */  
    public void setStartIndex(int startIndex) {  
        this.startIndex = startIndex;  
    }  
  
    /** 
     * @return the endIndex 
     */  
    public int getEndIndex() {  
        return endIndex;  
    }  
  
    /** 
     * @param endIndex 
     *            the endIndex to set 
     */  
    public void setEndIndex(int endIndex) {  
        this.endIndex = endIndex;  
    }  
  
}  