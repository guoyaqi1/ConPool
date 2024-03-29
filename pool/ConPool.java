package pool;

/**
 * Created by Administrator on 2019/10/24 0024.
 */

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
/**
 * 数据库连接池
 * 该池为单例池
 * 用户可从此池中获取含有状态的数据库连接
 */

public class ConPool {
    private List<MyCon>freeCons=new ArrayList<MyCon>();//空闲连接列表
    private List<MyCon>buzyCons=new ArrayList<MyCon>();//繁忙连接列表
    private int max=10;//最大连接数是10
    private int min=2;//最小连接数是2
    private int  current=0;//当前连接数
    private static ConPool instance;//单例实例
    private ConPool (){
        while (this.min> this.current){
            this.freeCons.add(this.createCon());
        }
    }
    /**
     * 获取池实例
     */
    public static ConPool getInstance(){
        if(instance==null)
            instance=new ConPool();
        return instance;
    }
    /**
     * 获取空闲数据库连接
     * 先从空闲列表中找出一个连接
     * 如果空闲列表中没有连接，则试图创建一个连接
     */
    public MyCon getCon(){
        MyCon mycon =this.getFreeCon();
        if (mycon!=null){
            return mycon;
        }else{
            return this.getNewCon();
        }
    }
    /**
     * 获取一个空闲连接
     */
    private MyCon getFreeCon(){
        if (freeCons.size()>0){
            MyCon con =freeCons.remove(0);
            con.setState(MyCon.BUZY);
            this.buzyCons.add(con);
            return con;
        }else{
            return  null;
        }
    }
    /**
     * 试图获取一个新连接
     * 如果当前连接数小于最大，则创建新的连接，否则返回null
     */
    private MyCon getNewCon(){
        if (this.current<this.max ){
            MyCon myCon=this.createCon();
            myCon.setState(MyCon.BUZY);
            this.buzyCons.add(myCon);
            return myCon;
        }else{
            return null;
        }
    }
    /**
     * 创建新的连接，并更新当前连接总数
     */
    private MyCon createCon(){
        try{
            Connection con=MySqlDAO.getConnection();
            MyCon mycon=new MyCon(con);
            this.current++;
            return mycon;
        }catch(Exception e){}
        return null;
    }
    /**
     * 将连接设为空闲状态
     */
    public void setFree(MyCon con){
        this.buzyCons.remove(con);
        con.setState(MyCon.FREE);
        this.freeCons.add(con);
    }
    /**
     * 输入当前池的连接状态
     */
    public String toString(){
        return "当前连接数：" + this.current + "	空闲连接数：" + this.freeCons.size() +
                "	繁忙连接数：" + this.buzyCons.size();
    }
}
