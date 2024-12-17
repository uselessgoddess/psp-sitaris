package by.vsu.ist.controller.manager;

import by.vsu.ist.domain.Account;
import by.vsu.ist.service.AccountService;
import by.vsu.ist.service.ServiceContainer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/manager/group/save.html")
@MultipartConfig
public class GroupSaveController extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			var id = Long.parseLong( req.getParameter("id"));
			var user = Long.parseLong(req.getParameter("user-id"));

			try(ServiceContainer container = new ServiceContainer()) {
				AccountService accountService = container.getAccountServiceInstance();
				accountService.accountRepository.addToGroup(id, user);
				resp.sendRedirect(req.getContextPath() + "/manager/group/list.html");
			} catch(SQLException e) {
				throw new ServletException(e);
			}
		} catch(IllegalArgumentException e) {
			resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, e.toString());
		}
	}
}
