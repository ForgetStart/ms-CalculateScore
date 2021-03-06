package com.hc360.score.task;

import com.hc360.score.common.AppContent;
import com.hc360.score.threadpool.FixedThreadPool;
import org.apache.log4j.Logger;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 商机详情历史数据处理
 */
public class BusinIntroduceHistoryTask {

    private static Logger logger = Logger.getLogger(AppContent.calculatescorelog);

    // 线程的name的前缀
    static String threadName = "calculatescore_history";
    // 线程池core_Pool_Size值
    static int corePoolSize = 150;
    // 线程池maximumPoolSize值
    static int maximumPoolSize = 200;
    // 线程池队列大小值
    static int queues = 150;
    // 线程空闲时间超过keepAliveTime,线程将被终止
    static int keepalive = 60 * 1000;


    public static ThreadPoolExecutor pool = null;

    static{
        pool = (ThreadPoolExecutor) new FixedThreadPool().getExecutor(threadName, corePoolSize, maximumPoolSize, queues, keepalive);
    }

    /*public BusinIntroduceHistoryTask() {
        pool = (ThreadPoolExecutor) new FixedThreadPool().getExecutor(threadName, corePoolSize, maximumPoolSize, queues, keepalive);
    }*/

	/**
     * 商机详情历史数据处理
     *
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			long start = System.currentTimeMillis();
            logger.info("BusinIntroduceHistoryTask start............");

            /*for(int threadno=0;threadno<100;threadno++){
                Runnable runner = new BusinIntroduceHistoryThread(threadno);
                pool.execute(runner);
            }*/

            for(int threadno=100;threadno<200;threadno++){
                Runnable runner = new BusinIntroduceHistoryThread(threadno);
                pool.execute(runner);
            }

            logger.info("BusinIntroduceHistoryTask end...............");
            logger.info("BusinIntroduceHistoryTask use time:" + (System.currentTimeMillis() - start));

        }catch(Exception e){
			e.printStackTrace();
		}
		//System.exit(0);
	}

}
