package com.csu.rocketmq;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.concurrent.*;

@Component
@RestController
public class CreateOrderService {

  private TransactionMQProducer producer;

  // 初始化transactionListener 和 producer
  @PostConstruct
  public void init() throws MQClientException {
    ExecutorService pool = new ThreadPoolExecutor(5, 200,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(1024) , new ThreadPoolExecutor.AbortPolicy());
    TransactionListener transactionListener = createTransactionListener();
    producer = new TransactionMQProducer("myGroup");
    producer.setExecutorService(pool);
    producer.setTransactionListener(transactionListener);
    producer.start();
  }

  // 创建订单服务的请求入口
  @RequestMapping("/testTransaction")
  public boolean createOrder(@RequestBody Object request) throws MQClientException {
    // 根据创建订单请求创建一条消息
    Message msg = createMessage(request);
    // 发送事务消息
    SendResult sendResult = producer.sendMessageInTransaction(msg, request);
    // 返回：事务是否成功
    return sendResult.getSendStatus() == SendStatus.SEND_OK;
  }

  private Message createMessage(Object request) {
    Message message=new Message();
    message.setTopic("rocket-mq-transaction");
    message.setBody(JSON.toJSONBytes(request));
    return message;
  }

  private TransactionListener createTransactionListener() {
    return new TransactionListener() {
      @Override
      public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        try {
          // 执行本地事务创建订单
          System.out.println("创建订单！");
          Thread.sleep(5000);
          System.out.println("创建订单成功！");
          // 如果没抛异常说明执行成功，提交事务消息
          return LocalTransactionState.COMMIT_MESSAGE;
        } catch (Throwable t) {
          // 失败则直接回滚事务消息
          return LocalTransactionState.ROLLBACK_MESSAGE;
        }
      }

      // 反查本地事务
      @Override
      public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        // 从消息中获得订单ID
        String orderId = msg.getUserProperty("orderId");

        // 去数据库中查询订单号是否存在，如果存在则提交事务；
        // 如果不存在，可能是本地事务失败了，也可能是本地事务还在执行，所以返回UNKNOW
        //（PS：这里RocketMQ有个拼写错误：UNKNOW）
        return LocalTransactionState.COMMIT_MESSAGE;
      }
    };
  }
}