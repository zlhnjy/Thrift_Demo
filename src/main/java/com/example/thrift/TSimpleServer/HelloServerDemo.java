package com.example.thrift.TSimpleServer;

import com.example.thrift.common.Constant;
import com.example.thrift.service.HelloWorldService;
import com.example.thrift.service.HelloWorldServiceImpl;
import org.apache.thrift.TProcessor;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

public class HelloServerDemo {

    private void startServer() throws TTransportException {
        TProcessor tProcessor = new HelloWorldService.Processor<HelloWorldService.Iface>(new HelloWorldServiceImpl());

        //简单的单线程服务模型
        TServerSocket tServerSocket = new TServerSocket(Constant.SERVER_PORT);
        TServer.Args args = new TServer.Args(tServerSocket);
        args.processor(tProcessor);
        TServer server = new TSimpleServer(args);
        server.serve();
    }

    public static void main(String[] args) throws TTransportException {
        HelloServerDemo helloServerDemo = new HelloServerDemo();
        helloServerDemo.startServer();
    }

}
