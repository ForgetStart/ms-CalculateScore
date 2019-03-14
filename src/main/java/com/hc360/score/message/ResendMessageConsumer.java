package com.hc360.score.message;

import com.hc360.bcs.bo.BusinInfo;
import com.hc360.jms.JMSConsumer;
import com.hc360.jms.activemq.ActiveMQ;
import com.hc360.jms.activemq.consumer.JMSMessageListener;
import com.hc360.score.common.AppContent;
import com.hc360.storm.hbase.JsonUtils;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.log4j.Logger;

import java.io.Serializable;

/**
 * ��broker��ȡ��Ϣ��������У��ȴ����Ѵ���
 * �ط� һ���ط�
 * @author hc360
 *
 */
public class ResendMessageConsumer extends JMSMessageListener{
	private static Logger logger = Logger.getLogger(AppContent.resendcalculatescorelog);
	
	public static void start() {
		try {
			JMSConsumer jconsumer = new JMSConsumer("CalculateScoreConsumer","storm.business.resend.queue",ActiveMQDestination.QUEUE_TYPE,new ResendMessageConsumer());
			ActiveMQ.initConsumer(jconsumer);
			
			logger.info("Create JMSConsumer success,queue for 'storm.business.resend.queue'");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("����JMSConsumerʱʧ�ܣ��쳣��Ϣ��", e);
		}
	}	

	@Override
	public Serializable doMessage(Serializable msg) {
		// ֻҪ����δ�����ͽ���Ϣ��������У�����������˾�������ֱ���пռ�Ϊֹ��

        //TODO �˳��߳�

		try{
			if(msg instanceof String){
				String message = (String)msg;
				Object object = JsonUtils.changeJson(message);
				BusinInfo busininfo = (BusinInfo)object;
				
				ResendMessageHandler.blockQueue.put(busininfo);
			}else if(msg instanceof BusinInfo){
				BusinInfo busininfo = (BusinInfo)msg;

                ResendMessageHandler.blockQueue.put(busininfo);
			}else{
				logger.error("The resend message type is not string: "+msg.toString());
			}
		}catch(Exception e){ 
			e.printStackTrace();
			logger.error("resend handler message exception:", e);
			throw new RuntimeException();
		}
		return null;
	}
}