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
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/manager/account/save.html")
@MultipartConfig
public class AccountSaveController extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Account account = new Account();
			String idParam = req.getParameter("id");
			if(idParam != null) {
				account.setId(Long.parseLong(idParam));
			}

			var list = new ArrayList<String>();
			req.getParameterNames().asIterator().forEachRemaining(list::add);
			String name = req.getParameter("name");
			if(name == null) throw new IllegalArgumentException("name is null: " + list);

			var photoPart = req.getPart("photo");
			var photo = photoPart.getInputStream().readAllBytes();

			account.setName(name);
			account.photo = photo;
			try(ServiceFactory container = ServiceFactory.getInstance()) {
				AccountService accountService = container.getAccountServiceInstance();
				accountService.save("employee",account);
				resp.sendRedirect(req.getContextPath() + "/manager/account/list.html");
			} catch(SQLException e) {
				throw new ServletException(e);
			}
		} catch(IllegalArgumentException e) {
			resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, e.toString());
		}
	}
}
