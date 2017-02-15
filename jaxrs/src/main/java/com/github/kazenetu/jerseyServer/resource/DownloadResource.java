package com.github.kazenetu.jerseyServer.resource;

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
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Path("dl")
public class DownloadResource {
    @GET
    @Path("pdf")
    @Produces("application/pdf")
    public Response pdf() {
    	String jasperPath = DownloadResource.class.getClassLoader().getResource("sample.jasper").getPath();

    	List<?> dataSourceList = Arrays.asList(new TestData("name",20));
    	JRDataSource dataSource = new JRBeanCollectionDataSource(dataSourceList);

    	Map<String,Object> params = new HashMap<>();

        try {
        	byte[] result = JasperRunManager.runReportToPdf(jasperPath,params,dataSource);

            return Response.ok(result)
                    //.header("Content-Disposition", "attachment; filename=" + URLEncoder.encode("test.pdf", "utf-8"))
                    .build();
        } catch (JRException e) {
			// TODO 自動生成された catch ブロック
            return Response.serverError().build();
		}
    }


}
