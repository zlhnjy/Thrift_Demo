package com.example.thrift.service;

public class HelloWorldServiceImpl implements HelloWorldService.Iface {
    @Override
    public String sayHello(String username) {
        return "hello " + username + " !";
    }
}
