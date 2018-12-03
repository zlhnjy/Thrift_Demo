package com.example.thrift.TSimpleServer;

import com.example.thrift.common.Constant;
import com.example.thrift.service.HelloWorldService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class HelloClientDemo {



    private void startClient(String username) {
        TTransport tTransport = new TSocket(Constant.SERVER_IP, Constant.SERVER_PORT, Constant.TIME_OUT);
        try {
            tTransport.open();
            TProtocol tProtocol = new TBinaryProtocol(tTransport);
            HelloWorldService.Client client = new HelloWorldService.Client(tProtocol);
            System.out.println(client.sayHello(username));
        } catch (TException e) {
            e.printStackTrace();
        } finally {
            tTransport.close();
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(() -> new HelloClientDemo().startClient("zhangsan "+Thread.currentThread().getName())).start();
        }
    }

}
