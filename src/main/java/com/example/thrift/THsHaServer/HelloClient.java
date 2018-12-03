package com.example.thrift.THsHaServer;

import com.example.thrift.common.Constant;
import com.example.thrift.service.HelloWorldService;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TNonblockingTransport;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class HelloClient {

    private void startClient(String username) throws IOException, TException, InterruptedException {
        TProtocolFactory protocolFactory = new TBinaryProtocol.Factory();
        TAsyncClientManager manager = new TAsyncClientManager();
        TNonblockingTransport transport = new TNonblockingSocket(Constant.SERVER_IP, Constant.SERVER_PORT);

        HelloWorldService.AsyncClient asyncClient = new HelloWorldService.AsyncClient(protocolFactory, manager, transport);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        CallBack callBack = new CallBack(countDownLatch);
        asyncClient.sayHello(username, callBack);
        countDownLatch.await();
        System.out.println(callBack.getResult());
    }

    private class CallBack implements AsyncMethodCallback<HelloWorldService.AsyncClient.sayHello_call> {

        private CountDownLatch countDownLatch;
        private String result;

        public CallBack(CountDownLatch countDownLatch){
            this.countDownLatch = countDownLatch;
        }

        public String getResult() {
            return result;
        }

        @Override
        public void onComplete(HelloWorldService.AsyncClient.sayHello_call sayHello_call) {
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                this.result = sayHello_call.getResult();
            } catch (TException e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
        }

        @Override
        public void onError(Exception e) {
            countDownLatch.countDown();
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            try {
                new HelloClient().startClient("wangwu " + i);
            } catch (IOException | TException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
