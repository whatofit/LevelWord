package level;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MouseInputListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.ormlitedao.bean.Word;
import com.ormlitedao.daoimpl.WordDaoImpl;

//import com.genericsdao.bean.Word;
//import com.genericsdao.daoimp.WordDaoImpl;

/*
 JTable常见用法细则 
 JTable是Swing编程中很常用的控件,这里总结了一些常用方法以备查阅.

 一.创建表格控件的各种方式:
 1)  调用无参构造函数.
 JTable table = new JTable();
 2)  以表头和表数据创建表格.
 Object[][] cellData = {{"row1-col1", "row1-col2"},{"row2-col1", "row2-col2"}};
 String[] columnNames = {"col1", "col2"};
 JTable table = new JTable(cellData, columnNames);
 3)  以表头和表数据创建表格,并且让表单元格不可改.
 String[] headers = { "表头一", "表头二", "表头三" };
 Object[][] cellData = null;
 DefaultTableModel model = new DefaultTableModel(cellData, headers) {
 public boolean isCellEditable(int row, int column) {
 return false;
 }
 };
 table = new JTable(model);

 二.对表格列的控制
 1) 设置列不可随容器组件大小变化自动调整宽度.
 table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
 2) 限制某列的宽度.
 TableColumn firsetColumn = table.getColumnModel().getColumn(0);
 firsetColumn.setPreferredWidth(30);
 firsetColumn.setMaxWidth(30);
 firsetColumn.setMinWidth(30);
 3) 设置当前列数.
 DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
 int count=5;
 tableModel.setColumnCount(count);
 4) 取得表格列数
 int cols = table.getColumnCount();
 5) 添加列
 DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
 tableModel.addColumn("新列名");
 6) 删除列
 table.removeColumn(table.getColumnModel().getColumn(columnIndex));// columnIndex是要删除的列序号

 三.对表格行的控制
 1) 设置行高
 table.setRowHeight(20);
 2) 设置当前行数
 DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
 int n=5;
 tableModel.setRowCount(n);
 3) 取得表格行数
 int rows = table.getRowCount();
 4) 添加表格行
 DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
 tableModel.addRow(new Object[]{"sitinspring", "35", "Boss"});
 5) 删除表格行
 DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
 model.removeRow(rowIndex);// rowIndex是要删除的行序号

 四.存取表格单元格的数据
 1) 取单元格数据
 DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
 String cellValue=(String) tableModel.getValueAt(row, column);// 取单元格数据,row是行号,column是列号
 2) 填充数据到表格.
 注:数据是Member类型的链表,Member类如下:
 public class Member{
 // 名称
 private String name;

 // 年龄
 private String age;

 // 职务
 private String title;
 }
 填充数据的代码:
 public void fillTable(List members){
 DefaultTableModel tableModel = (DefaultTableModel) table
 .getModel();
 tableModel.setRowCount(0);// 清除原有行

 // 填充数据
 for(Member member:members){
 String[] arr=new String[3];
 arr[0]=member.getName();
 arr[1]=member.getAge();
 arr[2]=member.getTitle();

 // 添加数据到表格
 tableModel.addRow(arr);
 }

 // 更新表格
 table.invalidate();
 }
 2) 取得表格中的数据
 public List getShowMembers(){
 List members=new ArrayList();

 DefaultTableModel tableModel = (DefaultTableModel) table
 .getModel();

 int rowCount=tableModel.getRowCount();

 for(int i=0;i     Member member=new Member();

 member.setName((String)tableModel.getValueAt(i, 0));// 取得第i行第一列的数据
 member.setAge((String)tableModel.getValueAt(i, 1));// 取得第i行第二列的数据
 member.setTitle((String)tableModel.getValueAt(i, 2));// 取得第i行第三列的数据

 members.add(member);
 }

 return members;
 }

 五.取得用户所选的行
 1) 取得用户所选的单行
 int selectRows=table.getSelectedRows().length;// 取得用户所选行的行数
 DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
 if(selectRows==1){
 int selectedRowIndex = table.getSelectedRow(); // 取得用户所选单行  

 .// 进行相关处理
 }
 2) 取得用户所选的多行
 int selectRows=table.getSelectedRows().length;// 取得用户所选行的行数
 DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
 if(selectRows>1)
 int[] selRowIndexs=table.getSelectedRows();// 用户所选行的序列

 for(int i=0;i     // 用tableModel.getValueAt(row, column)取单元格数据
 String cellValue=(String) tableModel.getValueAt(i, 1);
 }
 }

 六.添加表格的事件处理
 view.getTable().addMouseListener(new MouseListener() {
 public void mousePressed(MouseEvent e) {
 // 鼠标按下时的处理
 }
 public void mouseReleased(MouseEvent e) {
 // 鼠标松开时的处理
 }
 public void mouseEntered(MouseEvent e) {
 // 鼠标进入表格时的处理
 }
 public void mouseExited(MouseEvent e) {
 // 鼠标退出表格时的处理
 }
 public void mouseClicked(MouseEvent e) {
 // 鼠标点击时的处理
 }
 });
 * */

public class LevelWord extends JFrame {
    // we are using the in-memory H2 database
    // private final static String DATABASE_URL = "jdbc:h2:mem:LevelDict";
    // private final static String DATABASE_URL =
    // "jdbc:h2:file:LevelDict";//C:/data/sample (Windows only)
    // private final static String DATABASE_URL = "jdbc:h2:./LevelDict.h2";
    // jdbc:h2:tcp://dbserv:8084/~/sample

    /**
	 * 
	 */
    private static final long serialVersionUID = -902723851633869382L;

    // sqlite
    private final static String DATABASE_URL = "jdbc:sqlite:LevelDict.db3";

    private static ConnectionSource connectionSource;
    public static WordDaoImpl wordDao;

    private DefaultTableModel tableModel; // 表格模型对象
    private JTable table;
    private JTextField aTextField;
    private JTextField bTextField;
    private JTextField cTextField;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LevelWord frame = new LevelWord();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // destroy the data source which should close underlying
                    // connections
                    // if (connectionSource != null) {
                    // try {
                    // //connectionSource.close();
                    // //connectionSource = null;
                    // } catch (IOException e) {
                    // // TODO Auto-generated catch block
                    // e.printStackTrace();
                    // }
                    // }
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public LevelWord() throws SQLException {
        setIconImage(Toolkit.getDefaultToolkit().getImage(
                "E:\\workspace\\LevelWord_PC\\resoure\\word1.jpg"));
        setTitle("LevelWord");
        setBounds(100, 100, 800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // String[] columnHeadersNames = { "PartsOfSpeech", "Meaning",
        // "exampleSentence" }; // 列名
        // String[][] tableCellValues = { { "A1", "B1", "C1" },
        // { "A2", "B2", "C2" }, { "A3", "B3", "C3" },
        // { "A4", "B4", "C4" }, { "A5", "B5", "C5" } }; // 数据
        // String sqlDrop = "drop table if exists levelWordTable;";
        // DBUtil.ExecuteUpdate(sqlDrop);

        // final WordDaoImpl wordDao = new WordDaoImpl();
        // int affectRowCount = wordDao.create();
        // create our data-source for the database

        // String sqlCreate =
        // "CREATE TABLE IF NOT EXISTS levelWordTable (frequency,spelling,minLevel,partsOfSpeech,meaning,exampleSentence);";
        // int count = dbMgr.executeUpdate(sqlCreate);
        // String sqlSelect =
        // "select frequency,spelling,minLevel,partsOfSpeech,meaning,exampleSentence from levelWordTable;";
        // ResultSet rs = dbMgr.executeQuery(sqlSelect);
        // // Object[][] tableCellValues = DBUtil.ResultSetToObjectArray(rs);
        // Vector titleVector = new Vector(); // headVector/column Names/表头集合
        // Vector cellsVector = new Vector(); // rowsVector/rows data/数据体集合
        // try {
        // if (rs != null) {
        // ResultSetMetaData rsmd = rs.getMetaData();
        // for (int i = 1; i <= rsmd.getColumnCount(); ++i) {
        // titleVector.addElement(rsmd.getColumnName(i));//
        // columnNames.add(rsmd.getColumnName(i));
        // }
        // while (rs.next()) {
        // Vector curRow = new Vector();
        // for (int i = 1; i <= rsmd.getColumnCount(); ++i) {
        // curRow.addElement(rs.getString(i));// curRow.add(rs.getString(i));
        // }
        // curRow.addElement("");
        // cellsVector.addElement(curRow); // rows.add(curRow);
        // }
        // }
        // } catch (SQLException e1) {
        // // TODO Auto-generated catch block
        // e1.printStackTrace();
        // }
        // titleVector.addElement("operate");

        try {
            connectionSource = new JdbcConnectionSource(DATABASE_URL);
            wordDao = new WordDaoImpl(connectionSource);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        tableModel = new DefaultTableModel(0, 10) {
            /**
			 * 
			 */
            private static final long serialVersionUID = 2416119554735756716L;

            public boolean isCellEditable(int row, int column) {
                // JTextField tf = new JTextField();
                // tf.addKeyListener(new KeyAdapter() {
                // public void keyReleased(KeyEvent e) {
                // event(e);
                // };
                // });
                // tf.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
                // tf.setSelectionStart(0);
                // tf.setSelectionEnd(tf.getText().length());
                // table.getColumnModel().getColumn(column)
                // .setCellEditor(new DefaultCellEditor(tf));

                return true;// 默认是true
            }

            // private void event(KeyEvent e) {
            // int row = table.getSelectedRow();
            // int column = table.getSelectedColumn();
            //
            // DefaultCellEditor obj = (DefaultCellEditor) (table
            // .getColumnModel().getColumn(column).getCellEditor());
            // if (obj != null) {
            // JComponent com = (JComponent) obj.getComponent();
            // Object value = null;
            // if (com instanceof JTextField) {
            // value = ((JTextField) com).getText();
            // } else if (com instanceof JToggleButton) {
            // value = ((JToggleButton) com).isSelected();
            // }
            // System.out.println("row:" + row + " ,column:" + column
            // + " ,value:" + value);
            // System.out.println("e.getKeyCode:" + e.getKeyCode());
            // System.out
            // .println("KeyEvent.VK_ENTER:" + KeyEvent.VK_ENTER);
            // switch (e.getKeyCode()) {
            // case KeyEvent.VK_ENTER:
            // String id = (String) tableModel.getValueAt(row, 0);
            // // String rowColumn = (String)
            // // tableModel.getValueAt(row, column);
            // String freq = (String) tableModel.getValueAt(row, 1);
            // Word word = new Word();
            // word.setId(id);
            // word.setFrequency(freq);
            // wordDao.update(word);
            // break;
            // case KeyEvent.VK_SPACE:
            // break;
            // case KeyEvent.VK_BACK_SPACE:
            // break;
            // case KeyEvent.VK_ESCAPE:
            // break;
            // case KeyEvent.VK_UP:
            // break;
            // case KeyEvent.VK_DOWN:
            // break;
            // case KeyEvent.VK_LEFT:
            // break;
            // case KeyEvent.VK_RIGHT:
            // break;
            // default:
            // break;
            // }
            // }
            // }
        };
        table = new JTable(tableModel);
        // table.setBackground(Color.white);
        final MouseInputListener mouseInputListener = getMouseInputListener(table);// 添加鼠标右键选择行
        table.addMouseListener(mouseInputListener);
        table.addMouseMotionListener(mouseInputListener);
        table.setCellSelectionEnabled(true);
        table.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                // 当引起TableModel改变的事件是UPDATE时并且是第二列时候:
                // when table action is update.
                int row = e.getFirstRow();
                int column = e.getColumn();
                System.out.println("tableChanged row:" + row + ",column:"
                        + column);
                if (column >= 0) {
                    TableModel model = (TableModel) e.getSource();
                    String columnName = model.getColumnName(column);
                    System.out.println("tableChanged columnName:" + columnName);
                    Object data = model.getValueAt(row, column);
                    System.out.println("tableChanged,value:" + data);
                    if (e.getType() == TableModelEvent.UPDATE) {
                    }
                }
            }
        });

        // table.setFillsViewportHeight(true);
        // 1) 设置列不可随容器组件大小变化自动调整宽度.
        // table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        // 2) 限制某列的宽度.
        // TableColumn firsetColumn = table.getColumnModel().getColumn(0);
        // firsetColumn.setPreferredWidth(30);
        // firsetColumn.setMaxWidth(30);
        // firsetColumn.setMinWidth(30);
        // 3) 设置当前列数.
        // DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        // tableModel.setColumnCount(5);
        // 4) 取得表格列数
        // int colCnt = table.getColumnCount();
        // 5) 添加列
        // DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        // tableModel.addColumn("新列名");
        // 6) 删除列
        // table.removeColumn(table.getColumnModel().getColumn(columnIndex));//
        // columnIndex是要删除的列序号

        table.setRowHeight(30); // 设置行高
        JScrollPane scrollPane = new JScrollPane(table); // 支持滚动
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        // jdk1.6
        // 排序:
        // table.setRowSorter(new TableRowSorter(tableModel));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // 单选
        // 为表格添加监听器// 鼠标事件
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // 或 if(e.getClickCount() > 1)
                                              // 实现双击事件(前提是设置table不能编辑)
                    int row = ((JTable) e.getSource()).rowAtPoint(e.getPoint()); // 获得行位置
                    int col = ((JTable) e.getSource()).columnAtPoint(e
                            .getPoint()); // 获得列位置
                    System.out.println("右键双击鼠标：row=" + row + ",col=" + col);
                    String cellVal = (String) (tableModel.getValueAt(row, col)); // 获得点击单元格数据
                    System.out.println("右键双击鼠标：cellVal=" + cellVal);
                    aTextField.setText((row + 1) + "");
                    // txtboxCol.setText((col+1)+"");
                    // txtboxContent.setText(cellVal);
                } else if (e.getClickCount() == 1) {
                    int selectedRow = table.getSelectedRow(); // 获得选中行索引
                    Object oa = tableModel.getValueAt(selectedRow, 0);
                    Object ob = tableModel.getValueAt(selectedRow, 1);
                    Object oc = tableModel.getValueAt(selectedRow, 2);
                    // System.out.println("getClickCount,oa=" + oa);
                    // System.out.println("getClickCount,ob=" + ob);
                    // System.out.println("getClickCount,oc=" + oc);
                    if (oa != null) {
                        aTextField.setText(oa.toString()); // 给文本框赋值
                    }
                    if (ob != null) {
                        bTextField.setText(ob.toString());
                    }
                    if (oc != null) {
                        cTextField.setText(oc.toString());
                    }
                } else {
                    return;
                }
            }
        });
        // 监听事件
        table.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent e) {
                        if (e.getValueIsAdjusting()) {// 连续操作
                            int rowIndex = table.getSelectedRow();
                            if (rowIndex != -1) {
                                System.out.println("表格行被选中" + rowIndex);
                                // buttonAlt.setEnabled(true);
                                // buttonDel.setEnabled(true);
                            }
                        }

                    }
                });
        // 取消表格正在编辑的状态。
        table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        setTableCol();
        scrollPane.setViewportView(table);

        final JPanel panelNorth = new JPanel();
        getContentPane().add(panelNorth, BorderLayout.NORTH);

        panelNorth.add(new JLabel("Frequency: "));
        aTextField = new JTextField("", 6);
        panelNorth.add(aTextField);

        panelNorth.add(new JLabel("Spelling: "));
        bTextField = new JTextField("", 20);
        panelNorth.add(bTextField);

        panelNorth.add(new JLabel("Level: "));
        cTextField = new JTextField("", 4);
        panelNorth.add(cTextField);

        final JButton addButton = new JButton("添加"); // 添加按钮
        addButton.addActionListener(new ActionListener() {// 添加事件
                    public void actionPerformed(ActionEvent e) {
                        /*
                         * String sqlSelect =
                         * "select COUNT(*) as totalCount from Word where spelling = ? and partsOfSpeech=?"
                         * ; Object param[] = { bTextField.getText(),
                         * aTextField.getText() }; DBUtil dbMgr = new DBUtil();
                         * ResultSet rs = dbMgr.executeQuery(sqlSelect, param);
                         * int totalCount = 0; try { if (rs.next()) { totalCount
                         * = rs.getInt("totalCount"); } } catch (SQLException
                         * e1) { // TODO Auto-generated catch block
                         * e1.printStackTrace(); } if (totalCount == 0) { //
                         * insert db String[] rowValues = {
                         * aTextField.getText(), bTextField.getText(),
                         * cTextField.getText() }; tableModel.addRow(rowValues);
                         * // 添加一行 } else if (totalCount == 1) { // update db //
                         * 写数据库 String sqlUpdate =
                         * "update partsOfSpeech,meaning,exampleSentence from Word;"
                         * ; dbMgr.executeUpdate(sqlUpdate); }
                         */
                        // 查数据库中是否有相同拼写及词性的记录
                        // 若不存在,则插入;若存在,则更新(全部字段还是指定字段)/或先删除,后插入,Id会变,
                        Word word = new Word("159", "Spelling");
                        word.setLevel("9");
                        try {
                            CreateOrUpdateStatus cuStatus = wordDao
                                    .createOrUpdate(word);
                            System.out
                                    .println("CreateOrUpdateStatus.isCreated()="
                                            + cuStatus.isCreated());
                            System.out
                                    .println("CreateOrUpdateStatus.isUpdated()="
                                            + cuStatus.isUpdated());
                            System.out
                                    .println("CreateOrUpdateStatus.getNumLinesChanged()="
                                            + cuStatus.getNumLinesChanged());
                            // connectionSource.close();
                        } catch (SQLException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        // wordDao.select(word);
                        // wordDao.insert(word);
                        // wordDao.update(word);
                        tableModel.addRow(new Object[] { "sitinspring", "35",
                                "Boss" });
                        int rowCount = table.getRowCount() + 1; // 行数加上1
                        System.out.println("actionPerformed rowCount="
                                + rowCount);
                        // aTextField.setText("A" + rowCount);
                        // bTextField.setText("B" + rowCount);
                        // cTextField.setText("C" + rowCount);
                        aTextField.setText("");
                        bTextField.setText("");
                        cTextField.setText("");
                    }
                });
        panelNorth.add(addButton);

        final JButton updateButton = new JButton("修改"); // 修改按钮
        updateButton.addActionListener(new ActionListener() {// 添加事件
                    public void actionPerformed(ActionEvent e) {
                        int row = table.getSelectedRow();// 获得选中行的索引
                        if (row != -1) // 是否存在选中行
                        {
                            // 修改指定的值：
                            tableModel.setValueAt(aTextField.getText(), row, 1);
                            tableModel.setValueAt(bTextField.getText(), row, 2);
                            tableModel.setValueAt(cTextField.getText(), row, 5);
                            Word word = new Word();// 其他字段没有设置，会被更新修改清空删除掉
                            for (int column = 0; column <= 8; column++)
                                switch (column) {
                                case 0:
                                    int id = (int) tableModel.getValueAt(row,
                                            column);
                                    word.setId(id);
                                    break;
                                case 1:
                                    String frequency = (String) tableModel
                                            .getValueAt(row, column);
                                    word.setFrequency(frequency);
                                    break;
                                case 2:
                                    String spelling = (String) tableModel
                                            .getValueAt(row, column);
                                    word.setSpelling(spelling);
                                    break;
                                case 3:
                                    String phoneticDJ = (String) tableModel
                                            .getValueAt(row, column);
                                    word.setPhoneticDJ(phoneticDJ);
                                    break;
                                case 4:
                                    String phoneticKK = (String) tableModel
                                            .getValueAt(row, column);
                                    word.setPhoneticKK(phoneticKK);
                                case 5:
                                    String level = (String) tableModel
                                            .getValueAt(row, column);
                                    word.setLevel(level);
                                case 6:
                                    String partsOfSpeech = (String) tableModel
                                            .getValueAt(row, column);
                                    word.setPartsOfSpeech(partsOfSpeech);
                                case 7:
                                    String meanings = (String) tableModel
                                            .getValueAt(row, column);
                                    word.setMeanings(meanings);
                                case 8:
                                    String sentences = (String) tableModel
                                            .getValueAt(row, column);
                                    word.setSentences(sentences);
                                default:
                                    break;
                                }
                            System.out.println("del_btn,id=" + word);
                            try {
                                LevelWord.wordDao.update(word);
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                });
        panelNorth.add(updateButton);

        final JButton delButton = new JButton("删除");
        delButton.addActionListener(new ActionListener() {// 添加事件
                    public void actionPerformed(ActionEvent e) {
                        int selectedRow = table.getSelectedRow();// 获得选中行的索引
                        if (selectedRow != -1) // 存在选中行
                        {
                            int id = (int) tableModel
                                    .getValueAt(selectedRow, 0);
                            tableModel.removeRow(selectedRow); // 删除行
                            try {
                                wordDao.deleteById(String.valueOf(id));
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                });
        panelNorth.add(delButton);

        final JPanel panelSouth = new JPanel();
        getContentPane().add(panelSouth, BorderLayout.SOUTH);

        final JButton insertButton = new JButton("插入");
        insertButton.addActionListener(new ActionListener() {// 添加事件
                    public void actionPerformed(ActionEvent e) {
                        int selectedRow = table.getSelectedRow();// 获得选中行的索引
                        if (selectedRow == -1) {// 没有选中的行
                            selectedRow = 0;
                        }
                        if (selectedRow != -1) // 存在选中行
                        {
                            Word word = new Word("Spelling"
                                    + (2 * selectedRow + 1));
                            word.setFrequency("" + (selectedRow * 2 + 1));
                            word.setLevel("15");
                            try {
                                wordDao.create(word);
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                            tableModel.addRow(new Object[] { "sitinspring",
                                    "35", "Boss" });
                        }
                    }
                });
        panelSouth.add(insertButton);

        final JButton refreshButton = new JButton("刷新");
        refreshButton.addActionListener(new ActionListener() {// 添加事件
                    public void actionPerformed(ActionEvent e) {
                        // tableModel.setDataVector(wordDao.selectAll2Vector(),
                        // wordDao.getTableTitle());
                        tableModel.setDataVector(wordDao.selectAll2Vector(),
                                wordDao.getTableTitle());
                        tableModel.fireTableDataChanged();
                        setTableCol();
                    }
                });
        panelSouth.add(refreshButton);
    }

    public void setTableCol() {
        // JComboBox comboBox = new JComboBox();
        // comboBox.addItem("分可数名词countable noun(c.)");
        // comboBox.addItem("不可数名词uncountable noun(u.)");
        // comboBox.addItem("及物动词verb transitive(vt)");
        // comboBox.addItem("不及物动词verb intransitive(vi)");
        // comboBox.addItem("助动词auxiliary verb(aux.v)");
        // comboBox.addItem("情态动词modal verb");
        // comboBox.addItem("短语动词phr v(phrasal verb)");
        // comboBox.addItem("形容词 Adjectives(a./adj.)");
        // comboBox.addItem("副词 Adverbs(ad./adv.)");
        // comboBox.addItem("数词 Numeral(num.)");
        // comboBox.addItem("感叹词 Interjection(interj.) ");
        // comboBox.addItem("代（名）词 Pronouns(pron.)");
        // comboBox.addItem("介词 Prepositions(prep.)");
        // comboBox.addItem("冠词 Article(art.)");
        // comboBox.addItem("连词 Conjunction（conj.）");
        // comboBox.addItem("疑问词 Interrogative (int.)");
        // comboBox.addItem("量词 Quantifier(quant.)");
        // comboBox.addItem("复数plural(pl.)");

        String[] items = new String[] { "Snowboarding", "Rowing", "Knitting",
                "Speed reading", "None of the above" };
        JComboBox<String> comboBox = new JComboBox<String>(items);
        // Dimension d = comboBox.getPreferredSize();
        // comboBox.setPopupWidth(d.width);

        // TableColumn tableColumn = table.getColumn("partsOfSpeech");
        // 利用TableColumn类中的setCellEditor()方法来设置单元格的编辑器
        // DefaultCellEditor类可以将表格中的单元格设置成下拉框
        // tableColumn.setCellEditor(new DefaultCellEditor(comboBox));
        TableColumnModel tcm = table.getColumnModel();
        tcm.getColumn(6).setCellEditor(new DefaultCellEditor(comboBox));
        // TableColumn tc = table.getColumn("operate");
        TableColumn tc = tcm.getColumn(9);// 第10列/最后一列
        tc.setPreferredWidth(120);
        tc.setCellRenderer(new WordTableCellRenderer());
        tc.setCellEditor(new WordTableCellEditor());
    }

    /*
     * 添加鼠标右键单击选择监听，并显示右键菜单
     */
    private static MouseInputListener getMouseInputListener(final JTable jTable) {
        return new MouseInputListener() {
            public void mouseClicked(MouseEvent e) {
                processEvent(e);
            }

            public void mousePressed(MouseEvent e) {
                processEvent(e);
            }

            public void mouseReleased(MouseEvent e) {
                processEvent(e);
                if ((e.getModifiers() & MouseEvent.BUTTON3_MASK) != 0
                        && !e.isControlDown() && !e.isShiftDown()) {
                    // popupMenu.show(tableLyz, e.getX(), e.getY());//右键菜单显示
                }
            }

            public void mouseEntered(MouseEvent e) {
                processEvent(e);
            }

            public void mouseExited(MouseEvent e) {
                processEvent(e);
            }

            public void mouseDragged(MouseEvent e) {
                processEvent(e);
            }

            public void mouseMoved(MouseEvent e) {
                processEvent(e);
            }

            private void processEvent(MouseEvent e) {
                if ((e.getModifiers() & MouseEvent.BUTTON3_MASK) != 0) {
                    int modifiers = e.getModifiers();
                    modifiers -= MouseEvent.BUTTON3_MASK;
                    modifiers |= MouseEvent.BUTTON1_MASK;
                    MouseEvent ne = new MouseEvent(e.getComponent(), e.getID(),
                            e.getWhen(), modifiers, e.getX(), e.getY(),
                            e.getClickCount(), false);
                    jTable.dispatchEvent(ne);
                }
            }
        };
    }
}