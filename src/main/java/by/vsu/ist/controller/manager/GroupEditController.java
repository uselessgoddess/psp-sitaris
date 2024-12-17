package by.vsu.ist.controller.manager;

import by.vsu.ist.domain.Account;
import by.vsu.ist.domain.Group;
import by.vsu.ist.service.AccountService;
import by.vsu.ist.service.ServiceContainer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@WebServlet("/manager/group/edit.html")
public class GroupEditController extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = null;
        try {
            id = Long.parseLong(req.getParameter("id"));
        } catch (NumberFormatException ignored) {
        }
        if (id != null) {
            try (ServiceContainer container = new ServiceContainer()) {
                AccountService accountService = container.getAccountServiceInstance();
                Long finalId = id;
                Optional<Group> account = accountService.accountRepository.readGroups().stream().filter(group -> group.getId().equals(finalId)).findFirst();
                account.ifPresent(value -> req.setAttribute("group", value));
            } catch (SQLException e) {
                throw new ServletException(e);
            }
        }
        req.getRequestDispatcher("/WEB-INF/jsp/manager/group/edit.jsp").forward(req, resp);
    }
}
