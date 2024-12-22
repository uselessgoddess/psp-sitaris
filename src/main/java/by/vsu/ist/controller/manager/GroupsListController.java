package by.vsu.ist.controller.manager;

import by.vsu.ist.domain.Account;
import by.vsu.ist.service.AccountService;
import by.vsu.ist.service.ServiceContainer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/manager/group/list.html")
public class GroupsListController extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (ServiceContainer container = new ServiceContainer()) {
            AccountService accountService = container.getAccountServiceInstance();
            var groups = accountService.accountRepository.readGroups();
            req.setAttribute("groups", groups);

            var conflicts = new ArrayList<String>();
            {
                for (var group : groups) {
                    var also = groups.stream()
                            .filter(it -> !group.getId().equals(it.getId()))
                            .filter(it -> group.getCoach().name.equals(it.getCoach().name))
                            .filter(it -> group.getDate().equals(it.getDate()))
                            .toList();
                    for (var conflict : also) {
                        conflicts.add("Преподаватель: " + conflict.getCoach().name + " уже занимается с группой " + conflict.getId() + " в " + conflict.getDate());
                    }
                }

                for (var group : groups) {
                    var also = groups.stream()
                            .filter(it -> !group.getId().equals(it.getId()))
                            .filter(it -> group.getPlace().equals(it.getPlace()))
                            .filter(it -> group.getDate().equals(it.getDate()))
                            .toList();
                    for (var conflict : also) {
                        conflicts.add("Место: " + conflict.getPlace() + " уже занято в " + conflict.getPlace());
                    }
                }
            }
            req.setAttribute("conflicts", conflicts);

            req.getRequestDispatcher("/WEB-INF/jsp/manager/group/list.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
