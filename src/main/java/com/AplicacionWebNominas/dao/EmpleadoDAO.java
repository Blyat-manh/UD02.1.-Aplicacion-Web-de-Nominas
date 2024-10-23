package com.AplicacionWebNominas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.AplicacionWebNominas.conexion.Conexion;
import com.AplicacionWebNominas.model.Empleado;
import com.AplicacionWebNominas.model.Nomina;


public class EmpleadoDAO {
	private Connection connection;
	private PreparedStatement statement;
	private boolean estadoOperacion;
	
	
	
		public List<Empleado> obtenerEmpleado() throws SQLException {
			ResultSet resultSet = null;
			List<Empleado> listaEmpleados = new ArrayList<>();

			String sql = null;
			estadoOperacion = false;
			connection = obtenerConexion();

			try {
				sql = "SELECT * FROM empleado";
				statement = connection.prepareStatement(sql);
				resultSet = statement.executeQuery(sql);
				while (resultSet.next()) {
					Empleado e = new Empleado();
					e.setNombre(resultSet.getString("nombre"));
					e.setDni(resultSet.getString("dni"));
					e.setSexo(resultSet.getString("sexo").charAt(0));
					e.setCategoria(resultSet.getInt("categoria"));
					e.setAnyos(resultSet.getInt("anyos"));
					
					listaEmpleados.add(e);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

			return listaEmpleados;
		}
	
		public Empleado obtenerEmpleado(String dni) throws SQLException {
		    ResultSet resultSet = null;
		    Empleado e = null;

		    String sql = "SELECT * FROM empleado WHERE dni = ?";
		    connection = obtenerConexion();
		    statement = connection.prepareStatement(sql);
		    statement.setString(1, dni);

		    resultSet = statement.executeQuery();

		    if (resultSet.next()) {
		        e = new Empleado();
		        e.setNombre(resultSet.getString("nombre"));
		        e.setDni(resultSet.getString("dni"));
		        e.setSexo(resultSet.getString("sexo").charAt(0));
		        e.setCategoria(resultSet.getInt("categoria"));
		        e.setAnyos(resultSet.getInt("anyos"));
		    }

		    return e; 
		}
		public List<Empleado> buscarEmpleadosPorDNI(String dni) throws SQLException {
		    List<Empleado> listaEmpleados = new ArrayList<>();
		    String sql = "SELECT * FROM empleado WHERE dni LIKE ?";
		    connection = obtenerConexion();
		    statement = connection.prepareStatement(sql);
		    statement.setString(1, "%" + dni + "%"); // Busca por coincidencias parciales

		    ResultSet resultSet = statement.executeQuery();
		    while (resultSet.next()) {
		        Empleado e = new Empleado();
		        e.setNombre(resultSet.getString("nombre"));
		        e.setDni(resultSet.getString("dni"));
		        e.setSexo(resultSet.getString("sexo").charAt(0));
		        e.setCategoria(resultSet.getInt("categoria"));
		        e.setAnyos(resultSet.getInt("anyos"));
		        listaEmpleados.add(e);
		    }

		    return listaEmpleados;
		}
		public List<Empleado> buscarEmpleadosPorCriterios(String dni, String nombre, String categoria) throws SQLException {
		    List<Empleado> listaEmpleados = new ArrayList<>();
		    StringBuilder sql = new StringBuilder("SELECT * FROM empleado WHERE 1=1");
		    
		    if (dni != null && !dni.isEmpty()) {
		        sql.append(" AND dni LIKE ?");
		    }
		    if (nombre != null && !nombre.isEmpty()) {
		        sql.append(" AND nombre LIKE ?");
		    }
		    if (categoria != null && !categoria.isEmpty()) {
		        sql.append(" AND categoria LIKE ?");
		    }

		    connection = obtenerConexion();
		    statement = connection.prepareStatement(sql.toString());

		    int index = 1;
		    if (dni != null && !dni.isEmpty()) {
		        statement.setString(index++, "%" + dni + "%");
		    }
		    if (nombre != null && !nombre.isEmpty()) {
		        statement.setString(index++, "%" + nombre + "%");
		    }
		    if (categoria != null && !categoria.isEmpty()) {
		        statement.setString(index++, "%" + categoria + "%");
		    }

		    ResultSet resultSet = statement.executeQuery();
		    while (resultSet.next()) {
		        Empleado e = new Empleado();
		        e.setNombre(resultSet.getString("nombre"));
		        e.setDni(resultSet.getString("dni"));
		        e.setSexo(resultSet.getString("sexo").charAt(0));
		        e.setCategoria(resultSet.getInt("categoria"));
		        e.setAnyos(resultSet.getInt("anyos"));
		        listaEmpleados.add(e);
		    }

		    return listaEmpleados;
		}
		public void actualizarEmpleado(Empleado empleado) throws SQLException {
		    String sql = "UPDATE empleado SET nombre=?, sexo=?, categoria=?, anyos=? WHERE dni=?";
		    connection = obtenerConexion();
		    statement = connection.prepareStatement(sql);
		    statement.setString(1, empleado.getNombre());
		    statement.setString(2, String.valueOf(empleado.getSexo()));
		    statement.setInt(3, empleado.getCategoria());
		    statement.setInt(4, empleado.getAnyos());
		    statement.setString(5, empleado.getDni());
		    statement.executeUpdate();
		}

	
	private Connection obtenerConexion() throws SQLException {
		return Conexion.getConnection();
	}
}
