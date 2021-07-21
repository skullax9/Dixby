package Dixby;

import java.sql.*;
import static Dixby.Project_Datas.*;
import static Dixby.fifaUserSearch.*;
import static Dixby.main.COMMAND;

public class fifaDBConnect {
    static String sql = null;
    static Connection conn = null;
    static ResultSet rs = null;
    static Statement st = null;
    static String nick;
    static String accid;

    static void DBConnect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
            st = conn.createStatement();

            sql = "select * from FifaData where accessid = '"+accessID+"'";
            rs = st.executeQuery(sql);

            if (!(rs.next())) {
                String insertSql = "insert into FifaData values('"+nickname+"','"+accessID+"')";
                st.executeUpdate(insertSql);
            } else {
                nick = rs.getString(1);
                accid = rs.getString(2);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch(SQLException ex) {

                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch(SQLException ex) {

                }
            }
        }
    }

    static void UserHighRank() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
            st = conn.createStatement();

            sql = "select * from FifaData where nickname = '"+COMMAND.substring(6)+"'";
            rs = st.executeQuery(sql);

            if (rs.next()) {
                nick = rs.getString(1);
                accid = rs.getString(2);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch(SQLException ex) {

                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch(SQLException ex) {

                }
            }
        }
    }
}
