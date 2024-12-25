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

@WebServlet("/manager/coach/save.html")
@MultipartConfig
public class CoachSaveController extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Account account = new Account();
			String idParam = req.getParameter("id");
			if(idParam != null) {
				account.setId(Long.parseLong(idParam));
			}

			String name = req.getParameter("name");
			if(name == null) throw new IllegalArgumentException("user id is null");

			var photoPart = req.getPart("photo");
			var photo = photoPart.getInputStream().readAllBytes();

			account.setName(name);
			account.photo = photo;
			try(ServiceFactory container = ServiceFactory.getInstance()) {
				AccountService accountService = container.getAccountServiceInstance();
				accountService.save("coaches",account);
				resp.sendRedirect(req.getContextPath() + "/manager/coach/list.html");
			} catch(SQLException e) {
				throw new ServletException(e);
			}
		} catch(IllegalArgumentException e) {
			resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, e.toString());
		}
	}
}
