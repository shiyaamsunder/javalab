package lab6;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.PatternSyntaxException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JButton;
import javax.swing.JScrollPane;

import javax.swing.BoxLayout;

import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JTextField;
import javax.swing.RowFilter;

import javax.swing.JLabel;
import java.awt.BorderLayout;

public class Question2 {
	private static Statement statement = null;
	private static ResultSet resultSet = null;
	private static Connection conn = null;
	private static JTable table;
	private static JTextField filterText;

	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JFrame frame = new JFrame();
		frame.setSize(500, 500);
		frame.setTitle("JDBC");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);

		JPanel top = new JPanel();
		top.setBackground(UIManager.getColor("controlHighlight"));
		top.setBounds(10, 10, 466, 112);
		frame.getContentPane().add(top);
		top.setLayout(null);

		JTextArea textArea = new JTextArea();
		textArea.setBounds(10, 10, 446, 50);
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		top.add(textArea);

		JButton btnExecute = new JButton("Execute");
		btnExecute.setBounds(10, 70, 216, 21);
		top.add(btnExecute);

		JButton btnClear = new JButton("Clear");
		btnClear.setBounds(236, 70, 220, 21);
		top.add(btnClear);

		JPanel bottom = new JPanel();
		bottom.setBounds(10, 132, 466, 289);
		frame.getContentPane().add(bottom);
		bottom.setLayout(new BoxLayout(bottom, BoxLayout.X_AXIS));

		JScrollPane scrollPane = new JScrollPane();
		bottom.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		JPanel panel = new JPanel();
		panel.setBounds(10, 426, 466, 27);
		frame.getContentPane().add(panel);
		panel.setLayout(new BorderLayout(5, 5));

		JLabel lblNewLabel = new JLabel("Filter: ");
		panel.add(lblNewLabel, BorderLayout.WEST);

		filterText = new JTextField();
		panel.add(filterText);
		filterText.setColumns(10);

		JButton btnFilter = new JButton("Apply Filter");
		panel.add(btnFilter, BorderLayout.EAST);
		frame.setVisible(true);

		btnExecute.addActionListener(ev -> {
			String query = textArea.getText().toString();
			System.out.println(query);

			try {
				conn = PostgresConnection.connect();
				statement = conn.createStatement();
				resultSet = statement.executeQuery(query);
				ResultSetMetaData metadata = resultSet.getMetaData();

				DefaultTableModel model = (DefaultTableModel) table.getModel();

				int colCount = metadata.getColumnCount();
				String[] colNames = new String[colCount];
				for (int i = 0; i < colCount; i++) {
					colNames[i] = metadata.getColumnName(i + 1);
				}
				model.setColumnIdentifiers(colNames);

				for (int i = 1; i <= colCount; i++) {

				}
				while (resultSet.next()) {
					String[] row = new String[colCount];
					for (int i = 0; i < colCount; i++) {
						row[i] = resultSet.getString(i + 1);
					}
					model.addRow(row);
				}

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(frame, e1.getMessage());

			}
		});

		btnClear.addActionListener(ev -> {
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			model.setRowCount(0);
			model.setColumnCount(0);
		});

		final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
		table.setRowSorter(sorter);

		btnFilter.addActionListener(ev -> {
			String text = filterText.getText();

			if (text.length() == 0) {
				sorter.setRowFilter(null);
			} else {
				try {
					sorter.setRowFilter(RowFilter.regexFilter(text));
				} catch (PatternSyntaxException e) {
					JOptionPane.showMessageDialog(null, "Bad Regex Pattern", "Bad Regex Patter",
							JOptionPane.ERROR_MESSAGE);
				}
			}

		});

	}
}
