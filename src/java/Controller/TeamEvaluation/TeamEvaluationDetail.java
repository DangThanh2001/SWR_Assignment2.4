/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.TeamEvaluation;

import DAO.DAOTeamEvaluation;
import DAO.DAOchangePass;
import Enitiy.Class_s;
import Enitiy.Team;
import Enitiy.TeamEvaluation;
import Enitiy.User;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author tqbao
 */
@WebServlet(name = "TeamEvaluationDetail", urlPatterns = {"/TeamEvaluationDetail"})
public class TeamEvaluationDetail extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession();
            User Loged = (User) session.getAttribute("Loged");
            if (Loged == null) {
                request.getRequestDispatcher("Login_sen").forward(request, response);
            }
//            try {
//                if (Loged.getRole_id() > 2) {
//                    request.setAttribute("messE", "Seems like you don't have permission to do this");
//                    request.getRequestDispatcher("/jsp/Class/Error.jsp").forward(request, response);
//                    return;
//                }
//            } catch (Exception e) {
//            }
            String service = request.getParameter("go");
            DAOTeamEvaluation dao = new DAOTeamEvaluation();
            if (service.equals("UpdateEval")) {
                String team_id = request.getParameter("team_id");
                Team list = dao.viewTeam(team_id);
                User user = list.getClassroom().getTrainer();
                if (dao.getTeamBy(team_id).isEmpty()) {
                    response.sendRedirect("TeamList?go=listAllTeam&cid="+list.getClass_id());
                } else {
                    TeamEvaluation teameva = dao.getTeamBy(team_id).get(0);
                    request.setAttribute("Team", list);
                    request.setAttribute("User", user);
                    request.setAttribute("Class_s", list.getClassroom());
                    request.setAttribute("TeamEvaluation", teameva);
                    request.getRequestDispatcher("jsp/TeamEvaluation/TeamEvaluationDetail.jsp").forward(request, response);
                }
            }
            if (service.equals("TeamEvalDetail")) {
                int team_eval_id = Integer.parseInt(request.getParameter("team_eval_id"));
                int eva_id = Integer.parseInt(request.getParameter("eva_id"));
                int criteria_id = Integer.parseInt(request.getParameter("criteria_id"));
                int teamID = Integer.parseInt(request.getParameter("teamID"));
                int grade = Integer.parseInt(request.getParameter("grade"));
                String note = request.getParameter("note");
                dao.editTeamEval(team_eval_id, eva_id, criteria_id, teamID, grade, note);
                response.sendRedirect("TeamEvaluationDetail");
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
