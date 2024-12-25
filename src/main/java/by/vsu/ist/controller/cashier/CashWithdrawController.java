package by.vsu.ist.controller.cashier;

import by.vsu.ist.service.ServiceFactory;
import by.vsu.ist.service.TransferService;
import by.vsu.ist.web.SumRequestParser;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

@WebServlet("/cashier/cash/withdraw.html")
public class CashWithdrawController extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String accountNumber = req.getParameter("employee");
			if(accountNumber == null || accountNumber.isBlank()) throw new IllegalArgumentException();
			long sum = SumRequestParser.parse(req);
			try(ServiceFactory container = ServiceFactory.getInstance()) {
				TransferService transferService = container.getTransferServiceInstance();
				transferService.withdrawCash(accountNumber, sum);
				resp.sendRedirect(req.getContextPath() + "/cashier/account/list.html?msg=" + URLEncoder.encode("Операция выполнена успешно", StandardCharsets.UTF_8));
			} catch(SQLException e) {
				throw new ServletException(e);
			}
		} catch(IllegalArgumentException e) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
}
