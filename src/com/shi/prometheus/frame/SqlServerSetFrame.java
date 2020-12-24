package com.shi.prometheus.frame;

import com.shi.prometheus.db.cache.SqlServerConnectSetCache;
import com.shi.prometheus.common.PersistenceTask;
import com.shi.prometheus.db.entity.SqlServerConnectSet;
import com.shi.prometheus.utils.PrometheusTaskExecutor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @Description:
 * @Author: shizhikang
 * @Date: 2020/12/16
 */
public class SqlServerSetFrame extends JFrame {
    private JLabel portLabel;
    private JTextField portTextField;

    private JLabel dataBaseNameLabel;
    private JTextField dataBaseTextField;

    private JLabel userLabel;
    private JTextField userTextField;

    private JLabel passwordLabel;
    private JTextField passwordTextField;

    public SqlServerSetFrame() throws HeadlessException {
        setTitle("数据库连接设置");
        setSize(450, 500);
        setLocationRelativeTo(null);
        Container contentPane = getContentPane();
        setLayout(null);

        contentPane.setBackground(Color.WHITE);

        addPortLabel(contentPane);
        addDataBaseNameLabel(contentPane);
        addUserLabel(contentPane);
        addPasswordLabel(contentPane);
        addKeepAndCancelButton(contentPane);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void addPortLabel(Container jPanel) {
        portLabel = new JLabel("数据库端口号");
        portLabel.setBounds(60, 40, 150, 40);
        portLabel.setFont(new Font(portLabel.getFont().getName(), portLabel.getFont().getStyle(), 16));
        jPanel.add(portLabel);
        portTextField = new JTextField(SqlServerConnectSetCache.SQL_SERVER_PORT);
        portTextField.setBounds(200, 40, 150, 40);
        portTextField.setFont(new Font(null, Font.PLAIN, 16));
        jPanel.add(portTextField);
    }

    private void addDataBaseNameLabel(Container jPanel) {
        dataBaseNameLabel = new JLabel("数据库名");
        dataBaseNameLabel.setBounds(60, 100, 150, 40);
        dataBaseNameLabel.setFont(new Font(dataBaseNameLabel.getFont().getName(), dataBaseNameLabel.getFont().getStyle(), 16));
        jPanel.add(dataBaseNameLabel);
        dataBaseTextField = new JTextField(SqlServerConnectSetCache.SQL_SERVER_DATABASE);
        dataBaseTextField.setBounds(200, 100, 150, 40);
        dataBaseTextField.setFont(new Font(null, Font.PLAIN, 16));
        jPanel.add(dataBaseTextField);
    }

    private void addUserLabel(Container jPanel) {
        userLabel = new JLabel("账户名");
        userLabel.setBounds(60, 160, 150, 40);
        userLabel.setFont(new Font(userLabel.getFont().getName(), userLabel.getFont().getStyle(), 16));
        jPanel.add(userLabel);
        userTextField = new JTextField(SqlServerConnectSetCache.SQL_SERVER_USER);
        userTextField.setBounds(200, 160, 150, 40);
        userTextField.setFont(new Font(null, Font.PLAIN, 16));
        jPanel.add(userTextField);
    }

    private void addPasswordLabel(Container jPanel) {
        passwordLabel = new JLabel("密码");
        passwordLabel.setBounds(60, 220, 150, 40);
        passwordLabel.setFont(new Font(passwordLabel.getFont().getName(), passwordLabel.getFont().getStyle(), 16));
        jPanel.add(passwordLabel);
        passwordTextField = new JTextField(SqlServerConnectSetCache.SQL_SERVER_PASSWORD);
        passwordTextField.setBounds(200, 220, 150, 40);
        passwordTextField.setFont(new Font(null, Font.PLAIN, 16));
        jPanel.add(passwordTextField);
    }

    private void addKeepAndCancelButton(Container contentPane) {
        JButton keepButton = new JButton("保存");
        keepButton.setBounds(60, 300, 100, 50);
        keepButton.setFont(new Font(keepButton.getFont().getName(), keepButton.getFont().getStyle(), 20));
        contentPane.add(keepButton);
        keepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SqlServerConnectSetCache.SQL_SERVER_PORT = portTextField.getText();
                SqlServerConnectSetCache.SQL_SERVER_DATABASE = dataBaseTextField.getText();
                SqlServerConnectSetCache.SQL_SERVER_USER = userTextField.getText();
                SqlServerConnectSetCache.SQL_SERVER_PASSWORD = passwordTextField.getText();
                SqlServerConnectSet sqlServerConnectSet = new SqlServerConnectSet();
                sqlServerConnectSet.setId(SqlServerConnectSetCache.SQL_SERVER_CONNECT_SET_ID);
                sqlServerConnectSet.setPort(SqlServerConnectSetCache.SQL_SERVER_PORT);
                sqlServerConnectSet.setDataBaseName(SqlServerConnectSetCache.SQL_SERVER_DATABASE);
                sqlServerConnectSet.setUser(SqlServerConnectSetCache.SQL_SERVER_USER);
                sqlServerConnectSet.setPassword(SqlServerConnectSetCache.SQL_SERVER_PASSWORD);
                PrometheusTaskExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        PrometheusTaskExecutor.execute(new PersistenceTask<SqlServerConnectSet>(SqlServerConnectSet.class, sqlServerConnectSet, false, null));
                    }
                });
                getCurrentFrame().dispose();
                JOptionPane.showMessageDialog(getCurrentFrame(), "请重新测试数据库连接，判断是否可以启动");
            }
        });
        JButton cancelButton = new JButton("取消");
        cancelButton.setBounds(200, 300, 100, 50);
        cancelButton.setFont(new Font(keepButton.getFont().getName(), keepButton.getFont().getStyle(), 20));
        contentPane.add(cancelButton);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getCurrentFrame().dispose();
            }
        });
    }

    public JFrame getCurrentFrame() {
        return this;
    }
}
