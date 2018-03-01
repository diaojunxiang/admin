package test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class TestExcel {
	public static void main(String[] args) {
		// TODO 自动生成方法存根
		Cell cell = null;
		String sql = null;
		String insertDate = "";
		try {

			// 加载postgresql驱动
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection con = DriverManager.getConnection(
					"jdbc:sqlserver://120.27.45.135:1433; DatabaseName=o2o",
					"o2o_user", "dianbaoo2o630415");
			// 使用事务。不用也无所谓
			con.setAutoCommit(false);
			Statement smt = con.createStatement();
			// if (smt != null) System.out.println("smt is ok!");
			// 加载excel文件
			InputStream fs = new FileInputStream(
					"C:/Users/Administrator/Desktop/20180226车辆数据库.xls");
			// 得到 workbook
			Workbook wb = Workbook.getWorkbook(fs);
			/*
			 * 取得sheet，如果你的workbook里有多个sheet 可以利用 wb.getSheets()方法来得到所有的。
			 * getSheets() 方法返回 Sheet[] 数组 然后利用数组来操作。就是多次循环的事。
			 */
			Sheet sh = wb.getSheet(0);
			// 利用 sheet 的名字做表名创建数据库表。你可以自己规定
			// sql = "CREATE TABLE "
			// + sh.getName()
			// + " ("
			// +
			// "name varchar(50),sex varchar(10),age varchar(5),address varchar(30));";
			// smt.execute(sql);

			/*
			 * 开始循环，取得 cell 里的内容，这里都是按String来取的 为了省事，具体你自己可以按实际类型来取。或者都按
			 * String来取。然后根据你需要强制转换一下。
			 */
			for (int i = 0; i < sh.getRows(); i++) {
				for (int j = 0; j < sh.getColumns() - 1; j++) {
					cell = sh.getCell(j, i);
					if (j != sh.getColumns() - 2)
						// System.out.print(cell.getContents() + ",");
						insertDate += "'" + cell.getContents() + "',";
					else
						insertDate += "'" + cell.getContents() + "'";
					// System.out.print(cell.getContents());

				}
				/*
				 * 利用循环取每一行的数据。然后开始构造SQL语句。你可以 用自己的方法。这个方法我觉得比较偷懒。
				 */
				sql = "INSERT INTO t_qc(qc_pp,qc_cx,qc_nf,qc_ks,qc_sj,qc_dm)"
						+ " VALUES(" + insertDate + ");";
				System.out.println(sql);
				System.out.println(i);
				smt.execute(sql);
				/*
				 * 这里把 insertDate设置为空。不然 insertData下次 还会叠加上次的内容。也就失去了我们的目的。
				 */
				insertDate = "";

			}
			/*
			 * 提交事务
			 */
			con.commit();
			con.close();// 关闭数据库连接
			wb.close();// 关闭打开的文件，切记。切记
			System.out.println("OK!");
			// System.out.println(insertDate);
			// start sql;

		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (BiffException ex) {
			ex.printStackTrace();
		}

		catch (ClassNotFoundException ex) {
			System.out.println("class not found");
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
}
