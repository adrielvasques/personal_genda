package agendajava.conexaodb;
import java.sql.*;

public class Conexao {
    // Configuração da conexão com o banco de dados
    private static final String url = "jdbc:mysql://localhost:3306/db_agenda";
    private static final String user = "root";
    private static final String password = "root";

    private static Connection conexao = null;

    // Método para obter a conexão
    public static Connection obterConexao() {
        try {
            if (conexao == null || conexao.isClosed()) {
                conexao = DriverManager.getConnection(url, user, password);
            }
            return conexao;
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            e.printStackTrace();
        }finally{
            System.out.println("Conexão obtida com sucesso!");
        }
        return null;
    }
}
