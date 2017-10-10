package destiny.fate.common.net.handler.backend;

import destiny.fate.common.net.handler.backend.cmd.Command;
import destiny.fate.common.net.handler.backend.pool.MySqlDataPool;
import destiny.fate.common.net.handler.frontend.FrontendConnection;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

/**
 * Created by zhangtianlong01 on 2017/10/9.
 */
public class BackendConnection {

    private static final Logger logger = LoggerFactory.getLogger(BackendConnection.class);

    public int charsetIndex;

    public String charset;

    private long id;

    private ChannelHandlerContext ctx;

    /**
     * 当前连接所属的连接池
     */
    private MySqlDataPool mySqlDataPool;

    /**
     * 后端连接同步latch
     */
    public CountDownLatch syncLatch;

    public FrontendConnection frontend;

    /**
     * 前后端连接堆积的command,通过队列来实现线程间的无锁化
     */
    private ConcurrentLinkedQueue<Command> cmdQueue;

    public BackendConnection(MySqlDataPool mySqlDataPool) {
        this.mySqlDataPool = mySqlDataPool;
        syncLatch = new CountDownLatch(1);
        cmdQueue = new ConcurrentLinkedQueue<Command>();
    }
}