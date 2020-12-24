package com.shi.prometheus.frame;

import com.shi.prometheus.RootBootStrap;
import com.shi.prometheus.business.barrier.boot.BarrierClientBoot;
import com.shi.prometheus.business.cloud.netty.CloudNettyClient;
import com.shi.prometheus.common.ConnectStatusConstants;
import com.shi.prometheus.db.SqlLiteDao;
import com.shi.prometheus.db.cache.SqlServerConnectSetCache;
import com.shi.prometheus.db.entity.SqlServerConnectSet;
import com.shi.prometheus.utils.PrometheusTaskExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ClientFrame extends JFrame {

    public static final Logger logger = LogManager.getLogger(ClientFrame.class);

    public static int START_X = 100;
    public static int START_Y = 40;

    public static int BUTTON_WIDTH = 300;
    public static int BUTTON_HEIGHT = 40;

    public static int PLUGIN_HEIGHT_PER = 80;

    public static int PLUGIN_HEIGHT_NUM = 3;

    private JPanel contentPane;

    private JButton testSQLServerConnectButton;

    private JButton startButton;

    public ClientFrame() throws HeadlessException {
        initalizeFrame();
        //开一个线程用来支持开机自启动
        PrometheusTaskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                SqlServerConnectSet sqlServerConnectSet = new SqlLiteDao<SqlServerConnectSet>().queryById(SqlServerConnectSet.class, SqlServerConnectSetCache.SQL_SERVER_CONNECT_SET_ID.toString());
                if (sqlServerConnectSet != null && sqlServerConnectSet.getStatus() != null && sqlServerConnectSet.getStatus() == ConnectStatusConstants.SUCCESS) {
                    EventQueue.invokeLater(new Runnable() {
                        public void run() {
                            startClient();
                        }
                    });
                }
            }
        });
    }

    private void initalizeFrame() {
        setSize(BUTTON_WIDTH + 200, PLUGIN_HEIGHT_PER * PLUGIN_HEIGHT_NUM);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        contentPane = new JPanel();
        contentPane.setBounds(0, 0, BUTTON_WIDTH + 200, PLUGIN_HEIGHT_PER * PLUGIN_HEIGHT_NUM);
        contentPane.setBackground(Color.WHITE);
        contentPane.setLayout(null);

        addTestSQLServerConnectButtonToPanel(contentPane);
        addStartButtonToPanel(contentPane);

        add(contentPane);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Object[] options ={"继续关闭", "取消","后台运行"};
                int result = JOptionPane.showOptionDialog(getCurrentFrame(),"退出程序会导致停车场设备无法被监控，是否确认退出?", "警告",
                        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                System.out.println("result:" + result);
                if(result == JOptionPane.YES_OPTION){
                    getCurrentFrame().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    System.exit(0);
                } else if (result == JOptionPane.NO_OPTION){
                    getCurrentFrame().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                } else if (result == JOptionPane.CANCEL_OPTION) {
                    systemTray();
                    setVisible(false);
                }
                super.windowClosing(e);
            }
        });
    }

    private void systemTray() {
        if (SystemTray.isSupported()) {
            URL resource = ClientFrame.class.getClassLoader().getResource("64.png");
            ImageIcon icon = new ImageIcon(resource);
            TrayIcon trayIcon = new TrayIcon(icon.getImage(),"监控工具");
            SystemTray sysTray = SystemTray.getSystemTray();
            trayIcon.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 1) {
                        sysTray.remove(trayIcon);
                        getCurrentFrame().setVisible(true);
                    }
                }
            });
            try {
                sysTray.add(trayIcon);
            } catch (AWTException e1) {
                logger.error(e1);
            }
        } else {
            JOptionPane.showMessageDialog(getCurrentFrame(), "当前系统不支持系统托盘功能！");
        }
    }

    private void addTestSQLServerConnectButtonToPanel(JPanel contentPane) {
        startButton = new JButton("测试SqlServer连接");
        startButton.setBounds(START_X, START_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
        startButton.setFont(new Font("宋体", Font.PLAIN, 20));
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Connection connection = null;
                try {
                    connection = DriverManager.getConnection(SqlServerConnectSetCache.getSQLServerURL(), SqlServerConnectSetCache.SQL_SERVER_USER, SqlServerConnectSetCache.SQL_SERVER_PASSWORD);
                    JOptionPane.showMessageDialog(getCurrentFrame(), "数据库连接测试成功");
                    PrometheusTaskExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            RootBootStrap.initDBStat();
                            SqlServerConnectSet sqlServerConnectSet = new SqlLiteDao<SqlServerConnectSet>().queryById(SqlServerConnectSet.class, SqlServerConnectSetCache.SQL_SERVER_CONNECT_SET_ID.toString());
                            if (sqlServerConnectSet == null) {
                                return;
                            }
                            sqlServerConnectSet.setStatus(ConnectStatusConstants.SUCCESS);
                            new SqlLiteDao<SqlServerConnectSet>().createOrUpdateOne(SqlServerConnectSet.class, sqlServerConnectSet);
                        }
                    });
                } catch (SQLException ex) {
                    int result = JOptionPane.showConfirmDialog(getCurrentFrame(), "数据库连接失败，请修正数据库设置", "警告", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (result == JOptionPane.YES_OPTION) {
                        SqlServerSetFrame sqlServerSetFrame = new SqlServerSetFrame();
                        sqlServerSetFrame.setVisible(true);
                    }
                    PrometheusTaskExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            SqlServerConnectSet sqlServerConnectSet = new SqlLiteDao<SqlServerConnectSet>().queryById(SqlServerConnectSet.class, SqlServerConnectSetCache.SQL_SERVER_CONNECT_SET_ID.toString());
                            if (sqlServerConnectSet == null) {
                                return;
                            }
                            sqlServerConnectSet.setStatus(ConnectStatusConstants.FAIL);
                            new SqlLiteDao<SqlServerConnectSet>().createOrUpdateOne(SqlServerConnectSet.class, sqlServerConnectSet);
                        }
                    });
                } finally {
                    if (connection != null) {
                        try {
                            connection.close();
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(getCurrentFrame(), ex.getMessage(), "数据库关闭连接异常", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });
        contentPane.add(startButton);
    }

    private void addStartButtonToPanel(JPanel contentPane) {
        startButton = new JButton("启动监控");
        startButton.setBounds(START_X, START_Y + 80, BUTTON_WIDTH, BUTTON_HEIGHT);
        startButton.setFont(new Font("宋体", Font.PLAIN, 20));
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object[] options ={"已测试通过，继续启动", "未完成测试，返回测试"};
                int result = JOptionPane.showOptionDialog(getCurrentFrame(), "启动之前，请先测试数据库连接，保证数据库能够连接成功，否则获取不到道口数据！", "警告", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (result == JOptionPane.YES_OPTION) {
                    startClient();
                }
            }
        });
        contentPane.add(startButton);
    }

    private void startClient() {
        startButton.setText("已启动");
        startButton.repaint();
        startButton.setEnabled(false);
        PrometheusTaskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                CloudNettyClient.getInstance().startMonitor();
                BarrierClientBoot.getInstance().connectAllBarrier();
            }
        });
    }

    public JFrame getCurrentFrame() {
        return this;
    }
}
