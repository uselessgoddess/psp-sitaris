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
            Long id = null;
            String idParam = req.getParameter("id");
            if(idParam != null) {
                id = Long.parseLong(idParam);
            }

            try (ServiceContainer container = new ServiceContainer()) {
                AccountService accountService = container.getAccountServiceInstance();

                var coachId = req.getParameter("coach-id");
                if (coachId != null && !coachId.isBlank()) {
                    accountService.accountRepository.changeCoachOfGroup(id, Long.parseLong(coachId));
                }
                var userId = req.getParameter("user-id");
                if (userId != null && !userId.isBlank()) {
                    accountService.accountRepository.addToGroup(id, Long.parseLong(userId));
                }
                resp.sendRedirect(req.getContextPath() + "/manager/group/list.html");
            } catch (SQLException e) {
                throw new ServletException(e);
            }
        } catch (IllegalArgumentException e) {
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, e.toString());
        }
    }
}
