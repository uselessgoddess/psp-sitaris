package by.vsu.ist.controller.manager;

import by.vsu.ist.domain.Account;
import by.vsu.ist.service.AccountService;
import by.vsu.ist.service.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/manager/group/delete.html")
@MultipartConfig
public class GroupDeleteController extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Long id = null;
			String idParam = req.getParameter("id");
			if (idParam != null) {
				id = Long.parseLong(idParam);
			}


			try(ServiceFactory container = ServiceFactory.getInstance()) {
				AccountService accountService = container.getAccountServiceInstance();

				accountService.accountRepository.deleteGroup(id);

				resp.sendRedirect(req.getContextPath() + "/manager/group/list.html");
			} catch(SQLException e) {
				throw new ServletException(e);
			}
		} catch(IllegalArgumentException e) {
			resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, e.toString());
		}
	}
}
