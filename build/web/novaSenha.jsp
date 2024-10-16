<%@ page import="controlefuncionario.Usuario" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.io.*" %>
<%@ page import="javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="utils.Conexao" %>

<%
    // Obtendo os parâmetros do formulário
    String email = request.getParameter("email");
    String novaSenha = request.getParameter("senha");
    String confirmarSenha = request.getParameter("senhaConfirm");

    // Verifica se os campos foram preenchidos
    if (email == null || email.isEmpty() || novaSenha == null || novaSenha.isEmpty() || confirmarSenha == null || confirmarSenha.isEmpty()) {
        out.println("<script>alert('Por favor, preencha todos os campos.'); history.back();</script>");
        return;
    }

    // Verifica se as senhas coincidem
    if (!novaSenha.equals(confirmarSenha)) {
        out.println("<script>alert('As senhas não coincidem. Tente novamente.'); history.back();</script>");
        return;
    }

    // Tenta alterar a senha do usuário
    try {
        Usuario usuario = new Usuario();
        usuario.setEmail(email);

        // Autentica o usuário para verificar se ele existe
        Usuario usuarioAutenticado = usuario.autenticarUsuario();

        if (usuarioAutenticado != null) {
            // Se o usuário foi autenticado, altera a senha
            boolean senhaAlterada = usuarioAutenticado.alterarSenha(novaSenha);

            if (senhaAlterada) {
                out.println("<script>alert('Senha alterada com sucesso!'); window.location.href='login.jsp';</script>");
            } else {
                out.println("<script>alert('Erro ao alterar a senha. Tente novamente.'); history.back();</script>");
            }
        } else {
            out.println("<script>alert('Usuário não encontrado.'); history.back();</script>");
        }
    } catch (Exception e) {
        e.printStackTrace();
        out.println("<script>alert('Erro ao processar a solicitação.'); history.back();</script>");
    }
%>
