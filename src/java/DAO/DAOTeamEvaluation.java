/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import ConnectDB.ConnectJDBC;
import Enitiy.Class_s;
import Enitiy.Team;
import Enitiy.TeamEvaluation;
import Enitiy.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author tqbao
 */
public class DAOTeamEvaluation extends ConnectJDBC {

    Connection conn = ConnectJDBC.getConnection();

    ResultSet rs = null;
    PreparedStatement ps = null;

    public TeamEvaluation SearchTeamEvalID(int s) {
        List<TeamEvaluation> list = new ArrayList<>();
        String sql = "SELECT * FROM `team-evaluation` where team_eval_id = " + s;
        ResultSet rs = getData(sql);
        try {
            while (rs.next()) {
                return new TeamEvaluation(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getString(6));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public TeamEvaluation SearchTeamID(String s) {
        List<TeamEvaluation> list = new ArrayList<>();
        String sql = "select * from team-evaluation where team_id = " + s;
        ResultSet rs = getData(sql);
        try {
            while (rs.next()) {
                return new TeamEvaluation(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getString(7));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<TeamEvaluation> getTeamBy(String team_id) {
        List<TeamEvaluation> list = new ArrayList<>();
        String sql = "select distinct * from `team-evaluation` where team_id = " + team_id;
        ResultSet rs = getData(sql);
        try {
            while (rs.next()) {
                list.add(new TeamEvaluation(rs.getInt(1)));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public Class_s SearchTrainerID(String s) {
        List<Class_s> list = new ArrayList<>();
        String sql = "select * form class where trainer_id = " + s;
        ResultSet rs = getData(sql);
        try {
            while (rs.next()) {
                return new Class_s(rs.getString(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String getLeaderName(String s) {
        String sql = "select distinct b.fullname from team a inner join user b on a.team_name = b.user_id where a.team_name = " + s;
        ResultSet rs = getData(sql);
        try {
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e) {
            System.out.println("error");
        }
        return "";
    }

    public void editTeamEval(int team_eva_id, int eva_id, int criteria_id, int team_id, int grade, String note) {
        String sql = "update `team-evaluation` set evaluation_id = ?, criteria_id = ?, team_id = ?, grade = ?, note = ? \n"
                + "where team_eva_id = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, team_id);
            ps.setInt(2, grade);
            ps.setString(3, note);
            ps.setInt(4, team_eva_id);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Team viewTeam(String team) {
        Vector<Team> list = new Vector<>();

        String sql = "select * from team where team_id  =  " + team;
        //   System.out.println(sql);
        ResultSet rs = getData(sql);
        try {
            while (rs.next()) {
                int team_id = rs.getInt(1);
                String class_id = rs.getString(2);
                String topic_code = rs.getString(3);
                String topic_name = rs.getString(4);
                String gitlab_url = rs.getString(5);
                int status = rs.getInt(6);
                String team_name = rs.getString(7);
                Team u = new Team(team_id, class_id, null, topic_code, topic_name, gitlab_url, status, team_name);

                return u;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUserByID(String user_id) {
        String sql = "select * from user where user_id = " + user_id + "";
        ResultSet rs = getData(sql);
        try {
            if (rs.next()) {
                return new User(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getInt(4), rs.getString(5), rs.getString(6), rs.getString(7),
                        rs.getString(8), rs.getString(9), rs.getInt(10), rs.getInt(11), rs.getString(12), rs.getString(13), rs.getString(14));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
