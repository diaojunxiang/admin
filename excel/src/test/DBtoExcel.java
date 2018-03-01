package test;

import java.io.File;
import jxl.*;
import jxl.write.*;
import jxl.write.biff.RowsExceededException;
import java.sql.*;
import java.util.*;

public class DBtoExcel {
	public void WriteExcel(ResultSet rs, String filePath, String sheetName,
			Vector columnName) {
		WritableWorkbook workbook = null;
		WritableSheet sheet = null;

		int rowNum = 1; // 从第一行开始写入
		try {
			workbook = Workbook.createWorkbook(new File(filePath)); // 创建Excel文件
			sheet = workbook.createSheet(sheetName, 0); // 创建名为 sheetName 的工作簿

			this.writeCol(sheet, columnName, 0); // 首先将列名写入
			// 将结果集写入
			while (rs.next()) {
				Vector col = new Vector(); // 用以保存一行数据

				for (int i = 1; i <= columnName.size(); i++) { // 将一行内容保存在col中
					col.add(rs.getString(i));
				}
				// 写入Excel
				this.writeCol(sheet, col, rowNum++);

			}
			System.out.println("OK");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 关闭
				workbook.write();
				workbook.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/***
	 * 将数组写入工作簿
	 * 
	 * @param sheet
	 *            要写入的工作簿
	 * @param col
	 *            要写入的数据数组
	 * @param rowNum
	 *            要写入哪一行
	 * @throws WriteException
	 * @throws RowsExceededException
	 */
	private void writeCol(WritableSheet sheet, Vector col, int rowNum)
			throws RowsExceededException, WriteException {
		int size = col.size(); // 获取集合大小

		for (int i = 0; i < size; i++) { // 写入每一列
			Label label = new Label(i, rowNum, (String) col.get(i));
			sheet.addCell(label);
		}
	}

	public static void main(String[] args) throws Exception {
		String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String URL = "jdbc:sqlserver://192.163.0.16:1433; DatabaseName=o2o";
		String USERNAME = "o2o_user";
		String USERPASSWORD = "dianbaoo2o630415";

		String sql = "Select * from t_qc"; // 查询语句
		Vector columnName = new Vector(); // 列名
		columnName.add("qc_id");
		columnName.add("qc_pp");
		columnName.add("qc_cx");
		columnName.add("qc_nf");
		columnName.add("qc_ks");
		columnName.add("qc_sj");
		columnName.add("qc_tpwjm");
		columnName.add("qc_dm");

		// 连接数据库
		Class.forName(DRIVER);
		Connection conn = DriverManager.getConnection(URL, USERNAME,
				USERPASSWORD);
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		// 导出文件
		new DBtoExcel()
				.WriteExcel(rs, "C:/Users/Administrator/Desktop/car.xls",
						"人物信息", columnName);
	}
}
