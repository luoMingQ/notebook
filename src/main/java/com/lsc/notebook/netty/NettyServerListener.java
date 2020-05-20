//package com.lsc.notebook.netty;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import javax.servlet.ServletContextEvent;
//import javax.servlet.ServletContextListener;
//
///**
// * Netty 服务监听
// * @Author: luosc
// * @Description:
// * @Date:created in 11:19 2020/5/8
// */
//public class NettyServerListener implements ServletContextListener {
//    private Logger logger = LoggerFactory.getLogger(NettyServerListener.class);
//
//    @Autowired
//    private NettyServer nettyServer;
//    @Override
//    public void contextInitialized(ServletContextEvent sce) {
//        logger.info("ServletContex初始化...");
//
//        Thread thread = new Thread(new NettyServerThread());
//        // 启动netty服务
//        thread.start();
//    }
//
//    @Override
//    public void contextDestroyed(ServletContextEvent sce) {
//
//    }
//
//    /**
//     * Netty 服务启动线程
//     */
//    private class NettyServerThread implements Runnable {
//
//        @Override
//        public void run() {
//            nettyServer.run();
//        }
//    }
//}
