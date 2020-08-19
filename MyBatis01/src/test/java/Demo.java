import com.bbxx.dao.IUserDao;
import com.bbxx.pojo.UserVO;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Demo {
    public static void main(String[] args) throws IOException {
        // 读取配置文件
        InputStream in = Resources.getResourceAsStream("SqlConfig.xml");
        // 创建sessionFactory构建器
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        // 创建Session工厂
        SqlSessionFactory build = builder.build(in);
        // 获得sqlSession
        SqlSession sqlSession = build.openSession();
        // 创建代理mapper对象
        IUserDao mapper = sqlSession.getMapper(IUserDao.class);
        // 执行查询方法
        List<UserVO> all = mapper.findAll();
        if (all == null) {
            return;
        }
        // 遍历查询到的数据
        all.stream().forEach(System.out::println);
        sqlSession.close();
    }
}
