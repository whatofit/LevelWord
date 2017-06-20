package level;

//http://www.apihome.cn/api/java/JTable.html

//重写TableUI，来达到实现表格的合并和分解。

//真正实现表格多行多列合并的是在MultiSpanCellTableUI这个类里实现，这个类完全重新绘制表格从而达到合并的目的。
//代码很精练，不到100行，就搞定了表格的绘制。
//
//MultiSpanCellTable 继承于JTable，主要是重载了和表格定位相关的方法，
//比如rowColumnAtPoint，getCellRect，rowAtPoint这几个方法，
//根据Model里的数据来重新计算，道理也很简单,这样才能正确的判断用户选择的单元格，
//因为被合并的单元格是无法选择的和无法显示的，所以必须要在这里进行。代码还是百来行。
//
//AttributiveCellTableModel继续于DefaultTableModel，
//最关键的东西在这里，CellAttribute，他的实现是交给DefaultCellAttribute这个对象的，这个是存储单元格合并信息的地方，
//而且也是MultiSpanCellTable在计算的重要数据，和MultiSpanCellTableUI绘制的核心数据之一。
//DefaultCellAttribute有三个接口分别对应表格的功能。
//
//CellAttribute保存的是真正的视野上的表格信息，包括表格有多少行多少列（这是视觉上的不是逻辑上），
//JTable逻辑数据任然是在传统的model，逻辑数据每一次改变都将会映射到这里，会刷新视觉数据。
//
//CellSpan 保存的是单元格的合并属性，可视行，行跨度，列跨度。这些是UI绘制必需的东西。
//还有就是合并和拆分的功能也在这里，非常值得注意的是，在拆分合并表格的时候，他没有处理任何TableModel和Table的东西，就是在这里处理数据，
//处理完毕JTable重刷，这种M和V隔离的是相当的清晰。

//单元格的合并和拆分.
//http://www.blogjava.net/zeyuphoenix/archive/2010/04/12/318097.html
//JTable的单元格可编辑时可以把它看做一个JTextField,不可操作时可以看做一个JLabel,对于单元格的合并和拆分操作来说就是把JLabel或JTextField进行合并和拆分的过程.
//JTable单元格的合并简单来说就是把你选定的要合并的单元格的边线擦掉,然后调整宽度和高度,再在这几个合并的单元格外围画一个新的边线,然后设置JTable的UI,刷新就可以了.

//Example from http://www.crionics.com/products/opensource/faq/swing_ex/SwingExamples.html
/* (swing1.1) (swing#1356,#1454) */

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.apache.commons.lang3.StringUtils;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.mergetablecells.AttributiveCellTableModel;
import com.mergetablecells.ICellSpan;
import com.mergetablecells.MultiSpanCellTable;
import com.ormlitedao.daoimpl.WordDaoImpl;

/*   ----------------------------------------------
 *  |         SNo.        |
 *   ----------------------------------------------
 *  |          |     1    |
 *  |   Name   |-----------------------------------
 *  |          |     2    |
 *   ----------------------------------------------
 *  |          |     1    |
 *  |          |-----------------------------------
 *  | Language |     2    |
 *  |          |-----------------------------------
 *  |          |     3    |
 *   ----------------------------------------------
 */

/**
 * @version 1.0 11/26/98
 */
public class LevelWord extends JFrame {

    /**
   * 
   */
    private static final long serialVersionUID = -2140084363480237258L;
    // sqlite
    private final static String DATABASE_URL = "jdbc:sqlite:LevelDict.db3";
    private static ConnectionSource connectionSource;
    public static WordDaoImpl wordDao;

    private MultiSpanCellTable fixedTable;
    private AttributiveCellTableModel fixedTableModel;

    public LevelWord() {
        super("Levle word Multi-Span Cell");
        // setTitle("LevelWord");
        setIconImage(Toolkit.getDefaultToolkit()
                .getImage("./resoure/word1.jpg"));
        setSize(800, 600);
        // setBounds(100, 100, 800, 600);
        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            connectionSource = new JdbcConnectionSource(DATABASE_URL);
            wordDao = new WordDaoImpl(connectionSource);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        fixedTableModel = new AttributiveCellTableModel(
                wordDao.selectAll2Vector(), wordDao.getTableTitle());// 10行,6列
        fixedTable = new MultiSpanCellTable(fixedTableModel);
        fixedTable.setCellSelectionEnabled(true);
        fixedTable.setRowHeight(30); // 设置行高
        // fixedTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //
        // 单选
        fixedTable.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                // 当引起TableModel改变的事件是UPDATE时并且是第二列时候:
                // when table action is update.
                int row = e.getFirstRow();
                int column = e.getColumn();
                System.out.println("tableChanged row:" + row + ",column:"
                        + column);
                if (column >= 0) {
                    AttributiveCellTableModel model = (AttributiveCellTableModel) e
                            .getSource();
                    String columnName = model.getColumnName(column);
                    System.out.println("tableChanged columnName:" + columnName);
                    Object data = model.getValueAt(row, column);
                    System.out.println("tableChanged,value:" + data);
                    if (e.getType() == TableModelEvent.UPDATE) {
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(fixedTable);
        scrollPane.setViewportView(fixedTable);
        refreshTableModel();
        final ICellSpan cellAtt = (ICellSpan) fixedTableModel
                .getCellAttribute();
        // cellAtt.combine(new int[] { 0 }, new int[] { 0, 1 });
        // cellAtt.combine(new int[] { 1, 2 }, new int[] { 0 });
        // cellAtt.combine(new int[] { 3, 4, 5 }, new int[] { 0 });

        final JPanel panelSouth = new JPanel();
        getContentPane().add(panelSouth, BorderLayout.SOUTH);

        final JButton insertButton = new JButton("插入");
        insertButton.addActionListener(new ActionListener() {// 添加事件
                    public void actionPerformed(ActionEvent e) {
                        int selectedRow = fixedTable.getSelectedRow();// 获得选中行的索引
                        if (selectedRow == -1) {// 没有选中的行
                            selectedRow = 0;
                        }
                        if (selectedRow != -1) // 存在选中行
                        {
                            // Word word = new Word("Spelling"
                            // + (2 * selectedRow + 1));
                            // word.setFrequency("" + (selectedRow * 2 + 1));
                            // word.setLevel("15");
                            // try {
                            // wordDao.create(word);
                            // } catch (SQLException e1) {
                            // e1.printStackTrace();
                            // }
                            // tableModel.addRow(new Object[] { "sitinspring",
                            // "35", "Boss" });
                        }
                    }
                });
        panelSouth.add(insertButton);

        final JButton refreshButton = new JButton("刷新");
        refreshButton.addActionListener(new ActionListener() {// 添加事件
                    public void actionPerformed(ActionEvent e) {
                        refreshTableModel();
                    }
                });
        panelSouth.add(refreshButton);

        JButton combineBtn = new JButton("Combine");
        combineBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] columns = fixedTable.getSelectedColumns();
                int[] rows = fixedTable.getSelectedRows();
                System.out.println("getSelectedColumns="
                        + Arrays.toString(columns));
                System.out.println("getSelectedRows=" + Arrays.toString(rows));
                cellAtt.combine(rows, columns);
                // tableModel.changeAllCellAttribute();
                fixedTable.clearSelection();
                fixedTable.revalidate();
                fixedTable.repaint();
            }
        });
        panelSouth.add(combineBtn);

        JButton splitBtn = new JButton("Split");
        splitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int column = fixedTable.getSelectedColumn();
                int row = fixedTable.getSelectedRow();
                cellAtt.split(row, column);
                fixedTable.clearSelection();
                // revalidate()是把布局管理器对应的容器内的子组件重新计算大小,布局并绘制。
                // revalidate()是Jcompnent的方法。它并不是立即改变组件的大小，而是标记该组件需要改变大小。
                // 这样就可以避免了多个组件都要改变大小时带来的重复计算。
                // 但是，如果想重新计算一个Jframe中的所有组件，就需要调用repaint()方法
                // validate方法是告诉父容器，“我更新了，你要重绘！”。
                // 容器自身的重绘，轻量级的方法一般调用repain()。
                fixedTable.revalidate();
                fixedTable.repaint();
            }
        });
        panelSouth.add(splitBtn);
        getContentPane().add(scrollPane);
    }

    public boolean cellEquals(Object self, Object other) {
        String strSelf = String.valueOf(self).trim();
        String strOther = String.valueOf(other).trim();
        return StringUtils.equals(strSelf, strOther);
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

        String[] items = new String[] { "n.", "v.", "vt.", "vi.", "adj.",
                "adv.", "num.", "int.", "pron.", "prep.", "art.", "conj.",
                "abbr.", "aux.", "det.", "link-v." };
        // abbr.(abbreviation) 缩写;
        // aux.(auxiliary) 助动词
        // det.(deterninate) 限定词
        // link-v. (Link Verb) 系动词&连系动词

        JComboBox<String> comboBox = new JComboBox<String>(items);
        // Dimension d = comboBox.getPreferredSize();
        // comboBox.setPopupWidth(d.width);

        // TableColumn tableColumn = table.getColumn("partsOfSpeech");
        // 利用TableColumn类中的setCellEditor()方法来设置单元格的编辑器
        // DefaultCellEditor类可以将表格中的单元格设置成下拉框
        // tableColumn.setCellEditor(new DefaultCellEditor(comboBox));
        TableColumnModel tcm = fixedTable.getColumnModel();
        tcm.getColumn(6).setCellEditor(new DefaultCellEditor(comboBox));
        // TableColumn tc = table.getColumn("operate");
        TableColumn tc = tcm.getColumn(9);// 第10列/最后一列
        tc.setPreferredWidth(120);
        tc.setCellRenderer(new WordTableCellRenderer());
        tc.setCellEditor(new WordTableCellEditor());
    }

    // 设置table数据
    public void refreshTableModel() {
        Vector<Vector<Object>> recordList = wordDao.selectAll2Vector();
        fixedTableModel.setDataVector(recordList, wordDao.getTableTitle());
        ICellSpan cellAtt = (ICellSpan) fixedTableModel.getCellAttribute();
        int columnCnt = fixedTableModel.getColumnCount();
        for (int nCurColumn = 1; nCurColumn < columnCnt && nCurColumn <= 5; nCurColumn++) {// 从第i列开始合并,跳过Id列，
            int nStartRow = 0;
            Object vStartValue = recordList.get(0).get(
                    nCurColumn >= 4 ? 1 : nCurColumn);// 获取第0行的第i列或第1列的数据

            for (int nCurRow = 1; nCurRow < recordList.size(); nCurRow++) {// 从第j行开始与前一行比较
                Object vCurValue = recordList.get(nCurRow).get(
                        nCurColumn >= 4 ? 1 : nCurColumn);// 获取第i列或第1列的数据
                if (nCurRow == recordList.size() - 1) {
                    int spanRowCnt;
                    if (!cellEquals(vCurValue, vStartValue)) {// 最后一条记录的本columu的值不与倒数第二条记录的本column的值相同
                        spanRowCnt = nCurRow - nStartRow; // 边界：不包含最后一条记录
                    } else {
                        spanRowCnt = nCurRow - nStartRow + 1; // 边界：包含最后一条记录
                    }
                    int[] cellVec = new int[spanRowCnt];
                    for (int idx = 0; idx < spanRowCnt; idx++) {
                        cellVec[idx] = nStartRow + idx;
                    }
                    if (cellVec.length > 1) {
                        cellAtt.combine(cellVec, new int[] { nCurColumn });
                    }
                    nStartRow = nCurRow;
                    vStartValue = vCurValue;
                } else if (!cellEquals(vCurValue, vStartValue)) {
                    int spanRowCnt = nCurRow - nStartRow; // 边界：不包含最后一条记录
                    int[] cellVec = new int[spanRowCnt];
                    for (int idx = 0; idx < spanRowCnt; idx++) {
                        cellVec[idx] = nStartRow + idx;
                    }
                    if (cellVec.length > 1) {
                        cellAtt.combine(cellVec, new int[] { nCurColumn });
                    }
                    nStartRow = nCurRow;
                    vStartValue = vCurValue;
                }
            }
        }
        setTableCol();
        fixedTableModel.fireTableDataChanged();
        fixedTable.clearSelection();
        fixedTable.revalidate();
        fixedTable.repaint();
    }

    public static void main(String[] args) {
        LevelWord frame = new LevelWord();
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
                // destroy the data source which should close underlying
                // connections
                if (connectionSource != null) {
                    try {
                        connectionSource.close();
                        connectionSource = null;
                    } catch (IOException ioe) {
                        // TODO Auto-generated catch block
                        ioe.printStackTrace();
                    }
                }
            }
        });
        frame.setVisible(true);
    }
}
