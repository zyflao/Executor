package Executor;
import java.util.Arrays;  
import java.util.concurrent.ExecutionException;  
import java.util.concurrent.ExecutorCompletionService;  
import java.util.concurrent.ExecutorService;  
import java.util.concurrent.Executors;  
  
public class ConcurrentCalculator {  
  
    private ExecutorService exec;  
  
    private ExecutorCompletionService<Integer[]> completionService;  
  
    private int availableProcessors = 0;  
  
    public ConcurrentCalculator() {  
  
        /* 
         * ��ȡ���õĴ������������������������ָ���̳߳صĴ�С�� 
         */  
        availableProcessors = populateAvailableProcessors();  
        exec = Executors.newFixedThreadPool(availableProcessors);  
  
        completionService = new ExecutorCompletionService<Integer[]>(exec);  
    }  
  
    /** 
     * ��ȡ10000���������top 100������ 
     */  
    public Integer[] top100(int[] values) {  
  
        /* 
         * ��ʮ���̣߳�ÿ���̴߳���1000���� 
         */  
        for (int i = 0; i < 10; i++) {  
            completionService.submit(new Calculator(values, i * 1000,  
                    i * 1000 + 1000 - 1));  
        }  
  
        shutdown();  
  
        return populateTop100();  
    }  
  
    /** 
     * ����top 100������ 
     *  
     * ���㷽�����£� 1. ��ʼ��һ��top 100�����飬��ֵ��Ϊ0����Ϊ��ǰ��top 100. 2. �������ǰ��top 
     * 100����������ÿ���̲߳�����top 100����Ƚϣ�������ǰtop 100��ֵ�� 
     *  
     */  
    private Integer[] populateTop100() {  
        Integer[] top100 = new Integer[100];  
        for (int i = 0; i < 100; i++) {  
            top100[i] = new Integer(0);  
        }  
  
        for (int i = 0; i < 10; i++) {  
            try {  
                adjustTop100(top100, completionService.take().get());  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            } catch (ExecutionException e) {  
                e.printStackTrace();  
            }  
        }  
        return top100;  
    }  
  
    /** 
     * ����ǰtop 100�����һ���̷߳��ص�top 100����Ƚϣ���������ǰtop 100��������ݡ� 
     */  
    private void adjustTop100(Integer[] currentTop100, Integer[] subTop100) {  
        Integer[] currentTop200 = new Integer[200];  
  
        System.arraycopy(currentTop100, 0, currentTop200, 0, 100);  
        System.arraycopy(subTop100, 0, currentTop200, 100, 100);  
  
        Arrays.sort(currentTop200);  
  
        for (int i = 0; i < currentTop100.length; i++) {  
            currentTop100[i] = currentTop200[currentTop200.length - i - 1];  
        }  
    }  
  
    /** 
     * �ر� executor 
     */  
    public void shutdown() {  
        exec.shutdown();  
    }  
  
    /** 
     * ���ؿ����õĴ��������� 
     */  
    private int populateAvailableProcessors() {  
        return Runtime.getRuntime().availableProcessors();  
    }  
}  