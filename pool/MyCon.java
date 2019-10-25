package pool;

/**
 * Created by Administrator on 2019/10/24 0024.
 */
import java.sql.Connection;

public class MyCon {
    public static final int FREE=100;//当前连接空闲
    public static final int BUZY=101;//当前连接繁忙
    public static final int CLOSED=102;//当前连接关闭
    private Connection  con;  //持有的数据库连接
    private int state=FREE;//数据库连接当前的状态，初始状态是空闲
    public MyCon(Connection con){
        this.con=con;
    }
    public Connection getCon(){
        return con;
    }
    public int getState(){
        return state;
    }
    public void setState(int state){
        this.state=state;
    }
}
