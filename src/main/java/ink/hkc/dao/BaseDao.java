package ink.hkc.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * 操作数据库的公共类--静态类
 */

public class BaseDao {

    //静态代码块，在类加载的时候执行
    static {
        init();
    }

    private static String driver;
    private static String url;
    private static String user;
    private static String password;

    //初始化连接参数，从配置文件里获得
    private static void init() {
        Properties params = new Properties();
        String configFile = "db.properties";
        InputStream is = BaseDao.class.getClassLoader().getResourceAsStream(configFile);
        try {
            params.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        driver = params.getProperty("driver");
        url = params.getProperty("url");
        user = params.getProperty("user");
        password = params.getProperty("password");
    }

    //获取数据库连接
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    //查询的公共类
    public static ResultSet execute(Connection connection, String sql, Object[] param, PreparedStatement statement, ResultSet resultSet) {
        try {
            statement = connection.prepareStatement(sql);
            for (int i = 0; i < param.length; i++) {
                statement.setObject(i+1, param[i]);
            }
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    //增删改的公共类
    public static int execute(Connection connection, String sql, Object[] param, PreparedStatement statement) {
        int resultNum = 0;
        try {
            statement = connection.prepareStatement(sql);
            for (int i = 0; i < param.length; i++) {
                statement.setObject(i+1, param[i]);
            }
            resultNum = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultNum;
    }

    //关闭资源的公共类
    public static boolean close(Connection connection, PreparedStatement statement, ResultSet resultSet) {
        boolean flag = true;

        if (resultSet != null) {
            try {
                resultSet.close();

                resultSet = null;
            } catch (SQLException e) {
                e.printStackTrace();
                flag = false;
            }
        }

        if (statement != null) {
            try {
                statement.close();

                statement = null;
            } catch (SQLException e) {
                e.printStackTrace();
                flag = false;
            }
        }

        if (connection != null) {
            try {
                connection.close();

                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
                flag = false;
            }
        }

        return true;
    }
}
