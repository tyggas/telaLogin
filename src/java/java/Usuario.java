package java;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utils.Conexao;

public class Usuario {

    private String email;
    private String senha;

    // Inclusão de Usuario
    public boolean incluirUsuario() throws ClassNotFoundException {
        String sql = "INSERT INTO usuario values (?,?)";
        Connection con = Conexao.conectar();
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, this.getEmail());
            stm.setString(2, this.getSenha());
            stm.execute();
        } catch (SQLException e) {
            System.out.println("Erro na inclusão do usuário");
            return false;
        }
        return true;
    }

    // autenticacao do usuario
    public Usuario autenticarUsuario() throws ClassNotFoundException {
        Connection con = Conexao.conectar();
        Usuario usu = null;
        String sql = "SELECT email FROM usuario WHERE email = ?";
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, this.getEmail());
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                usu = new Usuario();
                usu.setEmail(rs.getString("email"));
            }
        } catch (SQLException e) {
            System.out.println("Erro na consulta do usuario");
            return null;
        }
        return usu;
    }

    public boolean alterarSenha(String novaSenha) throws ClassNotFoundException {
        Connection con = Conexao.conectar();
        String sql = "UPDATE usuario SET senha = ? WHERE email = ?";
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, novaSenha); // Define a nova senha
            stm.setString(2, this.getEmail()); // Usa o email para identificar o usuário
            int rowsUpdated = stm.executeUpdate();

            // Verifica se a atualização foi bem-sucedida
            if (rowsUpdated > 0) {
                this.setSenha(novaSenha); // Atualiza o campo senha no objeto
                return true;
            } else {
                System.out.println("Usuário não encontrado.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao alterar a senha: " + e.getMessage());
            return false;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
