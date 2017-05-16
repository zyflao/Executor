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
         * 获取可用的处理器数量，并根据这个数量指定线程池的大小。 
         */  
        availableProcessors = populateAvailableProcessors();  
        exec = Executors.newFixedThreadPool(availableProcessors);  
  
        completionService = new ExecutorCompletionService<Integer[]>(exec);  
    }  
  
    /** 
     * 获取10000个随机数中top 100的数。 
     */  
    public Integer[] top100(int[] values) {  
  
        /* 
         * 用十个线程，每个线程处理1000个。 
         */  
        for (int i = 0; i < 10; i++) {  
            completionService.submit(new Calculator(values, i * 1000,  
                    i * 1000 + 1000 - 1));  
        }  
  
        shutdown();  
  
        return populateTop100();  
    }  
  
    /** 
     * 计算top 100的数。 
     *  
     * 计算方法如下： 1. 初始化一个top 100的数组，数值都为0，作为当前的top 100. 2. 将这个当前的top 
     * 100数组依次与每个线程产生的top 100数组比较，调整当前top 100的值。 
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
     * 将当前top 100数组和一个线程返回的top 100数组比较，并调整当前top 100数组的数据。 
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
     * 关闭 executor 
     */  
    public void shutdown() {  
        exec.shutdown();  
    }  
  
    /** 
     * 返回可以用的处理器个数 
     */  
    private int populateAvailableProcessors() {  
        return Runtime.getRuntime().availableProcessors();  
    }  
}  