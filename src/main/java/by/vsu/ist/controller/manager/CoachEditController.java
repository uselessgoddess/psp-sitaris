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

@WebServlet("/manager/coach/edit.html")
public class CoachEditController extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long id = null;
		try {
			id = Long.parseLong(req.getParameter("id"));
		} catch(NumberFormatException ignored) {}
		if(id != null) {
			try(ServiceFactory container = ServiceFactory.getInstance()) {
				AccountService accountService = container.getAccountServiceInstance();
				Optional<Account> account = accountService.findById("coaches", id);
				account.ifPresent(value -> req.setAttribute("coach", value));
			} catch(SQLException e) {
				throw new ServletException(e);
			}
		}
		req.getRequestDispatcher("/WEB-INF/jsp/manager/coach/edit.jsp").forward(req, resp);
	}
}
