package by.vsu.ist.controller.manager;

import by.vsu.ist.domain.Account;
import by.vsu.ist.service.AccountService;
import by.vsu.ist.service.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@WebServlet("/manager/account/edit.html")
public class AccountEditController extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long id = null;
		try {
			id = Long.parseLong(req.getParameter("id"));
		} catch(NumberFormatException ignored) {}
		if(id != null) {
			try(ServiceFactory container = ServiceFactory.getInstance()) {
				AccountService accountService = container.getAccountServiceInstance();
				Optional<Account> account = accountService.findById("employee", id);
				account.ifPresent(value -> req.setAttribute("account", value));
			} catch(SQLException e) {
				throw new ServletException(e);
			}
		}
		req.getRequestDispatcher("/WEB-INF/jsp/manager/account/edit.jsp").forward(req, resp);
	}
}
