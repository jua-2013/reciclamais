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

public class CategoriaDAO {

    public static void cadastrarCategoria(String nome) {

        String sql = "INSERT INTO categorias (nome) VALUES (?)";

        PreparedStatement ps = null;

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1, nome);

            ps.execute();
            ps.close();
            JOptionPane.showMessageDialog(null, "Categoria cadastrada com sucesso! ✅", "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static List<Categoria> getCategorias() {
        List<Categoria> lista = new ArrayList<Categoria>();

        String sql = "SELECT id, nome FROM categorias ORDER BY nome";
        PreparedStatement ps = null;

        try {
            Connection conn = Conexao.getConexao();
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs != null) {

                while (rs.next()) {
                    Categoria categoria = new Categoria();
                    categoria.id = rs.getInt(1);
                    categoria.nome = rs.getString(2);
                    lista.add(categoria);

                }

            }
            // Conexao.fecharConn(conn);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());

        }
        return lista;
    }

    public static void excluirCategoria(int idCategoria) {
        String sql = "DELETE FROM categorias WHERE id = ?";
        PreparedStatement ps = null;

        try {
            Connection conn = Conexao.getConexao();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idCategoria);
            ps.execute();
            JOptionPane.showMessageDialog(null, "Categoria removida com sucesso! ✅", "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());

        }
    }

    public static void atualizarCategoria(String name, int idCategoria) {
        String sql = "UPDATE categorias SET nome = ? WHERE id = ?";

        PreparedStatement ps = null;

        try {
            Connection conn = Conexao.getConexao();
            ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setInt(2, idCategoria);
            ps.execute();
            JOptionPane.showMessageDialog(null, "Categoria atualizada com sucesso! ✅", "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());

        }
    }

}
