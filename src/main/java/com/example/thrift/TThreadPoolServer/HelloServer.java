package com.example.thrift.TThreadPoolServer;

import com.example.thrift.common.Constant;
import com.example.thrift.service.HelloWorldService;
import com.example.thrift.service.HelloWorldServiceImpl;
import org.apache.thrift.TProcessor;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

public class HelloServer {

    private void startServer() throws TTransportException {
        TProcessor tProcessor = new HelloWorldService.Processor<HelloWorldService.Iface>(new HelloWorldServiceImpl());

        TServerSocket serverSocket = new TServerSocket(Constant.SERVER_PORT);
        TThreadPoolServer.Args args = new TThreadPoolServer.Args(serverSocket);
        args.processor(tProcessor);

        // 线程池服务模型，使用标准的阻塞式IO，预先创建一组线程处理请求。
        TServer server = new TThreadPoolServer(args);
        server.serve();
    }

    public static void main(String[] args) throws TTransportException {
        new HelloServer().startServer();
    }

}
