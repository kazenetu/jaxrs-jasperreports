package com.github.kazenetu.jerseyServer.resource;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.github.kazenetu.jerseyServer.entity.TestData;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Path("dl")
public class DownloadResource {

	/**
	 * PDF(Label表示)の表示
	 * @return
	 */
	@GET
	@Path("pdf")
	@Produces("application/pdf")
	public Response pdf() {

		List<?> dataSourceList = Arrays.asList(new TestData("name", 20));
		JRDataSource dataSource = new JRBeanCollectionDataSource(dataSourceList);

		Map<String, Object> params = new HashMap<>();

		try {
			return Response.ok(getPdfBytes("sample", params, dataSource))
					// .header("Content-Disposition", "attachment; filename=" +
					// URLEncoder.encode("test.pdf", "utf-8"))
					.build();
		} catch (JRException e) {
			return Response.serverError().build();
		}
	}

	/**
	 * PDF(バーコード表示)の表示
	 * @return
	 */
	@GET
	@Path("pdf2")
	@Produces("application/pdf")
	public Response pdf2() {

		List<?> dataSourceList = Arrays.asList(new TestData("name", 20));
		JRDataSource dataSource = new JRBeanCollectionDataSource(dataSourceList);

		Map<String, Object> params = new HashMap<>();

		try {
			byte[] result = getPdfBytes("sample2", params, dataSource);
			return Response.ok(result)
					// .header("Content-Disposition", "attachment; filename=" +
					// URLEncoder.encode("test.pdf", "utf-8"))
					.build();
		} catch (JRException e) {
			return Response.serverError().build();
		}
	}

	/**
	 * PDF(日本語表示)の表示
	 * @return
	 */
	@GET
	@Path("pdf3")
	@Produces("application/pdf")
	public Response pdf3() {

		List<?> dataSourceList = Arrays.asList(new TestData("name", 20));
		JRDataSource dataSource = new JRBeanCollectionDataSource(dataSourceList);

		Map<String, Object> params = new HashMap<>();

		try {
			byte[] result = getPdfBytes("sample3", params, dataSource);
			return Response.ok(result)
					// .header("Content-Disposition", "attachment; filename=" +
					// URLEncoder.encode("test.pdf", "utf-8"))
					.build();
		} catch (JRException e) {
			return Response.serverError().build();
		}
	}

	/**
	 * PDFデータの取得
	 *
	 * @param reportName レポート名
	 * @param params パラメータ
	 * @param dataSource データソース
	 * @return PDFデータ
	 * @throws JRException 実行時の例外エラー
	 */
	private byte[] getPdfBytes(String reportName, Map<String, Object> params, JRDataSource dataSource)
			throws JRException {
		String filePath = getResourcePath(reportName + ".jasper");

		if (filePath != null) {
			return JasperRunManager.runReportToPdf(filePath, params, dataSource);
		}

		filePath = getResourcePath(reportName + ".jrxml");
		JasperReport report = JasperCompileManager.compileReport(filePath);
		return JasperRunManager.runReportToPdf(report, params, dataSource);
	}

	/**
	 * リソースのパスを取得する
	 *
	 * @param fileName リソースファイル名
	 * @return リソースファイルのパス
	 */
	private String getResourcePath(String fileName) {
		URL fileUrl = DownloadResource.class.getClassLoader().getResource(fileName);
		if (fileUrl == null) {
			return null;
		}
		return fileUrl.getPath();
	}
}
