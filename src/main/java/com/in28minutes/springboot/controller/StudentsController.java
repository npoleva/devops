package com.in28minutes.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

@RestController
public class StudentsController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/students/all")
    public String show() {
        try {
            List<Map<String, Object>> users = jdbcTemplate.queryForList("SELECT * FROM STUDENTS;");

            StringBuilder html = new StringBuilder();
            html.append("<html><head><style>");
            html.append("table { border-collapse: collapse; width: 100%; }");
            html.append("th, td { border: 1px solid black; padding: 8px; text-align: left; }");
            html.append("th { background-color: #f2f2f2; }");
            html.append("</style></head><body>");
            html.append("<h1>Users from Database</h1>");
            html.append("<table>");

            if (!users.isEmpty()) {
                html.append("<tr>");
                for (String column : users.get(0).keySet()) {
                    html.append("<th>").append(column).append("</th>");
                }
                html.append("</tr>");

                for (Map<String, Object> row : users) {
                    html.append("<tr>");
                    for (Object value : row.values()) {
                        html.append("<td>").append(value != null ? value.toString() : "null").append("</td>");
                    }
                    html.append("</tr>");
                }
            } else {
                html.append("<tr><td colspan='3'>No users found</td></tr>");
            }

            html.append("</table>");
            html.append("</body></html>");

            return html.toString();

        } catch (Exception e) {
            return "<html><body><h2>Error: " + e.getMessage() + "</h2></body></html>";
        }
    }
}