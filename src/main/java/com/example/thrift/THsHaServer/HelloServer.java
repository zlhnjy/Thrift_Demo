package com.example.thrift.THsHaServer;

import com.example.thrift.common.Constant;
import com.example.thrift.service.HelloWorldService;
import com.example.thrift.service.HelloWorldServiceImpl;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;

public class HelloServer {

    private void startServer() throws TTransportException {
        TProcessor tProcessor = new HelloWorldService.Processor<HelloWorldService.Iface>(new HelloWorldServiceImpl());

        TNonblockingServerSocket serverSocket = new TNonblockingServerSocket(Constant.SERVER_PORT);
        TNonblockingServer.Args args = new TNonblockingServer.Args(serverSocket);
        args.processor(tProcessor);
//        args.transportFactory(new TFramedTransport.Factory());
//        args.protocolFactory(new TCompactProtocol.Factory());

        TNonblockingServer server = new TNonblockingServer(args);
        server.serve();
    }

    public static void main(String[] args) throws TTransportException {
        new HelloServer().startServer();
    }

}
