package servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import bd.Conexao;
//import resource.ResourceReader;

@WebServlet("/pages/ImprimirRelatorio")
public class ImprimirRelatorio extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private boolean relatorioGerado;

	public ImprimirRelatorio() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletOutputStream saida = null;

		Conexao con = new Conexao();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("REPORT_CONNECTION", con);
		parameters.put(JRParameter.REPORT_LOCALE, new Locale("pt", "BR"));
		String report = request.getParameter("rlt_nome");

		try {

			if (request.getParameter("credenciadoId") != null && !request.getParameter("credenciadoId").equals("0")) {
				// parameters.put("PSET_CODIGO",
				// request.getParameter("set_codigo") != null?
				// Integer.parseInt(request.getParameter("set_codigo")) : 0);
				parameters.put("CREDENCIADO", Integer.parseInt(request.getParameter("credenciadoId")));
			}
			
			if (request.getParameter("data") != null && !request.getParameter("data").equals("0")) {
				// parameters.put("PSET_CODIGO",
				// request.getParameter("set_codigo") != null?
				// Integer.parseInt(request.getParameter("set_codigo")) : 0);
				parameters.put("DATA", (request.getParameter("data")));
			}
			if (request.getParameter("id") != null && !request.getParameter("id").equals("0")) {
				// parameters.put("PSET_CODIGO",
				// request.getParameter("set_codigo") != null?
				// Integer.parseInt(request.getParameter("set_codigo")) : 0);
				parameters.put("ID", request.getParameter("id"));
			}
			/*
			 * if (request.getParameter("instrutorId2") != null &&
			 * !request.getParameter("instrutorId2").equals("0")){
			 * //parameters.put("PSET_CODIGO",
			 * request.getParameter("set_codigo") != null?
			 * Integer.parseInt(request.getParameter("set_codigo")) : 0);
			 * parameters.put("IDINSTRUTOR2",
			 * Integer.parseInt(request.getParameter("instrutorId2"))); } if
			 * (request.getParameter("instrutorId3") != null &&
			 * !request.getParameter("instrutorId3").equals("0")){
			 * //parameters.put("PSET_CODIGO",
			 * request.getParameter("set_codigo") != null?
			 * Integer.parseInt(request.getParameter("set_codigo")) : 0);
			 * parameters.put("IDINSTRUTOR3",
			 * Integer.parseInt(request.getParameter("instrutorId3"))); }
			 */

			/*
			 * if (request.getParameter("usuarioId") != null &&
			 * !request.getParameter("usuarioId").equals("0")){
			 * //parameters.put("PSET_CODIGO",
			 * request.getParameter("set_codigo") != null?
			 * Integer.parseInt(request.getParameter("set_codigo")) : 0);
			 * parameters.put("USUARIOID",
			 * Integer.parseInt(request.getParameter("usuarioId"))); }
			 */

			// if (request.getParameter("set_codigo") != null &&
			// !request.getParameter("set_codigo").equals("0")){
			// parameters.put("PSET_CODIGO", request.getParameter("set_codigo")
			// != null? Integer.parseInt(request.getParameter("set_codigo")) :
			// 0);
			// parameters.put("PSET_CODIGO",
			// Integer.parseInt((request.getParameter("set_codigo"))));
			// }

			String jasperFileName = "C:/detran-resource/Relatorios/";

			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperFileName + report + ".jasper", parameters,
					con.getConexao());
			saida = response.getOutputStream();

			this.relatorioGerado = jasperPrint.getPages().size() > 0;

			response.setContentType("application/pdf");
			response.setHeader("cache-control", "must-revalidate");
			JasperExportManager.exportReportToPdfStream(jasperPrint, saida);
			saida.flush();
			saida.close();
			parameters.clear();
			con.fechaConexao();
			con = null;

		} catch (JRException e) {
			e.printStackTrace();
		} finally {
			con = null;
		}

	}

	public boolean isRelatorioGerado() {
		return relatorioGerado;
	}
}
