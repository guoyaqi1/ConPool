package pool;

/**
 * Created by Administrator on 2019/10/25 0025.
 */
public class DBTest {
    public static void main (String[]args)throws Exception{
        System.out.println(ConPool.getInstance().toString());
        MyCon con =null;
        for (int i=0;i<5;i++){
            con=ConPool .getInstance().getCon();
        }
        System.out.println(ConPool.getInstance().toString());
        ConPool.getInstance().setFree(con);
        System.out.println(ConPool.getInstance().toString());
    }
}
