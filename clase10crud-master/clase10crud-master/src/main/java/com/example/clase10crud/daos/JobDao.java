package com.example.clase10crud.daos;

import com.example.clase10crud.beans.Employee;

import java.sql.*;
import java.util.ArrayList;

public class EmployeeDao {
    public ArrayList<Employee> listar(){

        ArrayList<Employee> lista = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String url = "jdbc:mysql://localhost:3306/hr";
        String username = "root";
        String password = "123456";

        String sql = "SELECT \n" +
                "    e.employee_id,\n" +
                "    e.first_name,\n" +
                "    e.last_name,\n" +
                "    e.hire_date,\n" +
                "    e.email,\n" +
                "    (SELECT CONCAT(m.first_name, ' ', m.last_name)\n" +
                "     FROM employees m\n" +
                "     WHERE m.employee_id = e.manager_id) AS manager_name\n" +
                "FROM \n" +
                "    employees e;";


        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getInt(1));
                employee.setHire_date(rs.getDate(2));
                employee.setFirst_name(rs.getString(3));
                employee.setLast_name(rs.getString(4));
                employee.setEmail(rs.getString(5));
                employee.setManager(rs.getString(6));
                lista.add(employee);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    public void crear(String fname, String lname, int email, int phnumber){

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String url = "jdbc:mysql://localhost:3306/hr";
        String username = "root";
        String password = "123456";

        String sql = "insert into employees (first_name, last_name, email,phone_number) values (?,?,?,?)";

        try(Connection connection = DriverManager.getConnection(url,username,password);
            PreparedStatement pstmt = connection.prepareStatement(sql)){

            pstmt.setString(1,fname);
            pstmt.setString(2,lname);
            pstmt.setInt(3,email);
            pstmt.setInt(4,phnumber);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
