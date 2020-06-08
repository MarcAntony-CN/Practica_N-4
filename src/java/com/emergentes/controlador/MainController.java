package com.emergentes.controlador;



import com.emergentes.modelo.Libro;
import com.emergentes.utiles.ConexionDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class MainController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String op;
        op=(request.getParameter("op")!=null) ? request.getParameter("op") : "list";
        ArrayList<Libro> lista=new ArrayList<Libro>();
        ConexionDB canal=new ConexionDB();
        Connection conn=canal.conectar();
        PreparedStatement ps;
        ResultSet rs;
        
        if(op.equals("list")){
            try{
                String sql="SELECT * FROM libros";
                ps=conn.prepareStatement(sql);
                rs=ps.executeQuery();
                
                while(rs.next()){
                    Libro lib=new Libro();
                    lib.setId(rs.getInt("id"));
                    lib.setFecha(rs.getString("fecha"));
                    lib.setTitulo(rs.getString("titulo"));
                    lib.setContenido(rs.getString("contenido"));
                    
                    lista.add(lib);
                }
                
                request.setAttribute("lista", lista);
                request.getRequestDispatcher("panel.jsp").forward(request, response);
                
            }catch(SQLException ex){
                System.out.println("Error en SQL: "+ex.getMessage());
            }finally{
                canal.desconectar();
            }
        }
        
        if(op.equals("nuevo")){
            Libro l=new Libro();
            request.setAttribute("libro", l);
            request.getRequestDispatcher("editar.jsp").forward(request, response);
        }
       
        if(op.equals("eliminar")){
            int id=Integer.parseInt(request.getParameter("id"));
            
            try{
                String sql="delete from libros where id=?";
                ps=conn.prepareStatement(sql);
                ps.setInt(1, id);
                ps.executeUpdate();
            }catch(SQLException ex){
                System.out.println("Error en SQL: "+ex.getMessage());
            }finally{
                canal.desconectar();
            }
            response.sendRedirect("MainController");
        }
        if(op.equals("editar")){      
            try{
                int id=Integer.parseInt(request.getParameter("id"));
                String sql="select * from libros where id=?";
                ps=conn.prepareStatement(sql);
                ps.setInt(1, id);
                
                rs = ps.executeQuery();
                
                Libro li = new Libro();
                
                while(rs.next()){
                li.setId(rs.getInt("id"));
                li.setFecha(rs.getString("fecha"));
                li.setTitulo(rs.getString("titulo"));
                li.setContenido(rs.getString("contenido"));                
            }
                request.setAttribute("libro", li);
                request.getRequestDispatcher("editar.jsp").forward(request, response);
            }catch(SQLException ex){
                System.out.println("Error en SQL: "+ex.getMessage());
            }finally{
                canal.conectar();
            }
            response.sendRedirect("MainController");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int id=Integer.parseInt(request.getParameter("id"));
        String fecha=request.getParameter("fecha");
        String titulo=request.getParameter("titulo");
        String contenido=request.getParameter("contenido");
        
        Libro l=new Libro();
        
        l.setId(id);
        l.setFecha(fecha);
        l.setTitulo(titulo);
        l.setContenido(contenido);
        
        ConexionDB canal=new ConexionDB();
        Connection conn=canal.conectar();
        PreparedStatement ps;
        ResultSet rs;
        
        if(id==0){
           String sql="insert into libros (fecha, titulo, contenido) values (?,?,?);";
            try{
                
                ps=conn.prepareStatement(sql);
                ps.setString(1, l.getFecha());
                ps.setString(2, l.getTitulo());
                ps.setString(3, l.getContenido());
                ps.executeUpdate();
            }catch(SQLException ex){
                System.out.println("Error de SQL: "+ex.getMessage());
            }finally{
                canal.desconectar();
            }
        
        response.sendRedirect("MainController");
    }
        else{
            try{
    String slq = "update libro set fecha=?,titulo=?,contenido=? where id =?";
     ps=conn.prepareStatement(slq);
     ps.setString(1, l.getFecha());
     ps.setString(2, l.getTitulo());
     ps.setString(3, l.getContenido());
     ps.setInt(4, l.getId());
     ps.executeUpdate();
     
            }catch(SQLException ex){
                System.out.println("Error al actualisar"+ex.getMessage());
            }
          response.sendRedirect("MainController");  
   }    
}
}
