package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import conexao.Conexao;
import model.Categoria;
import model.Residuo;

public class ResiduoDAO {

     public static void cadastrarResiduo(String nome, int codCategoria){
        String sql = "INSERT INTO residuo (nome, codCategoria, qtd) VALUES (?,?,?)";
        PreparedStatement ps = null;

        try {
            Connection conn = Conexao.getConexao();
            ps = conn.prepareStatement(sql);
            ps.setString(1, nome);
            ps.setInt(2, codCategoria);
            ps.setInt(3, 0);
            ps.execute();
            JOptionPane.showMessageDialog(null, "Residuo cadastrado com sucesso! ✅", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            //Conexao.fecharConn(conn);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            
        }
    }

    public static List<Residuo> getResiduos(){
        List<Residuo> lista = new ArrayList<Residuo>();
        
        
        String sql = "SELECT nome, id, codCategoria, qtd FROM residuo ORDER BY nome";
        PreparedStatement ps = null;

        try {
            Connection conn = Conexao.getConexao();
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs != null) {

                while (rs.next()) {
                    Residuo residuo = new Residuo();
                    residuo.nome = rs.getString(1);
                    residuo.id = rs.getInt(2);
                    residuo.idCategoria = rs.getInt(3);
                    residuo.qtd = rs.getInt(4);
                    lista.add(residuo);

                }
                
            }
            //Conexao.fecharConn(conn);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            
        }
        return lista;
    }

    public static void excluirResiduo(int idResiduo){
        String sql = "DELETE FROM residuo WHERE id = ?";
        PreparedStatement ps = null;

        try {
            Connection conn = Conexao.getConexao();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idResiduo);
            ps.execute();
            JOptionPane.showMessageDialog(null, "Residuo removido com sucesso! ✅", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            
        }
    }

    public static void incrementarEstoque(double qtd, int id){
        String sql = "UPDATE residuo SET qtd = qtd + ? WHERE id = ?";
        PreparedStatement ps = null;

        try {
            Connection conn = Conexao.getConexao();
            ps = conn.prepareStatement(sql);
            ps.setDouble(1, qtd);
            ps.setDouble(2, id);
            ps.execute();
            JOptionPane.showMessageDialog(null, "Estoque alterado com sucesso! ✅", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            //Conexao.fecharConn(conn);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            
        }
    }
    public static void decrementarEstoque(double qtd, int id){
        String sql = "UPDATE residuo SET qtd = qtd - ? WHERE id = ?";
        PreparedStatement ps = null;

        try {
            Connection conn = Conexao.getConexao();
            ps = conn.prepareStatement(sql);
            ps.setDouble(1, qtd);
            ps.setDouble(2, id);
            ps.execute();
            JOptionPane.showMessageDialog(null, "Estoque alterado com sucesso! ✅", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            //Conexao.fecharConn(conn);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            
        }
    }
    public static void atualizarCategoria(String name, int idResiduo) {
        String sql = "UPDATE residuo SET nome = ? WHERE id = ?";

        PreparedStatement ps = null;

        try {
            Connection conn = Conexao.getConexao();
            ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setInt(2, idResiduo);
            ps.execute();
            JOptionPane.showMessageDialog(null, "Resíduo atualizado com sucesso! ✅", "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());

        }
    }




}
