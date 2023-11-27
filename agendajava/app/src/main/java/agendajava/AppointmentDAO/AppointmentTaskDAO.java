package agendajava.AppointmentDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import agendajava.entity.AppointmentTask;
import agendajava.conexaodb.Conexao;

public class AppointmentTaskDAO {

    // Método para inserir uma nova tarefa de compromisso no banco de dados
    public void inserirTarefa(AppointmentTask tarefa) {
        String sql = "INSERT INTO compromissos (data, hora, titulo, descricao, categoria, status) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = null;
        try {
            ps = Conexao.obterConexao().prepareStatement(sql);
            ps.setString(1, tarefa.getData());
            ps.setString(2, tarefa.getHora());
            ps.setString(3, tarefa.getTitulo());
            ps.setString(4, tarefa.getDescricao());
            ps.setString(5, tarefa.getCategoria());
            ps.setString(6, tarefa.getStatus());

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean possuiCompromissosNaData(String data) {
        // Recupere a lista de compromissos para a data especificada
        List<AppointmentTask> compromissos = AppointmentTaskDAO.recuperarTarefasPorDia(data);

        // Verifique se a lista de compromissos não está vazia
        return !compromissos.isEmpty();
    }

    // Método para atualizar uma tarefa de compromisso no banco de dados
    public void atualizarTarefa(AppointmentTask tarefa) {
        String sql = "UPDATE compromissos SET data = ?, hora = ?, titulo = ?, descricao = ?, categoria = ?, status = ? WHERE id_compromisso = ?";
    
        PreparedStatement ps = null;
        try {
            ps = Conexao.obterConexao().prepareStatement(sql);
    
            // Verifique se os valores não estão vazios ou nulos antes de definir os parâmetros
            if (tarefa.getData() != null && !tarefa.getData().isEmpty()) {
                ps.setString(1, tarefa.getData());
            } else {
                ps.setNull(1, Types.VARCHAR); // Trata valor vazio ou nulo como NULL no banco de dados
            }
    
            if (tarefa.getHora() != null && !tarefa.getHora().isEmpty()) {
                ps.setString(2, tarefa.getHora());
            } else {
                ps.setNull(2, Types.VARCHAR);
            }
    
            if (tarefa.getTitulo() != null && !tarefa.getTitulo().isEmpty()) {
                ps.setString(3, tarefa.getTitulo());
            } else {
                ps.setNull(3, Types.VARCHAR);
            }
    
            if (tarefa.getDescricao() != null && !tarefa.getDescricao().isEmpty()) {
                ps.setString(4, tarefa.getDescricao());
            } else {
                ps.setNull(4, Types.VARCHAR);
            }
    
            if (tarefa.getCategoria() != null && !tarefa.getCategoria().isEmpty()) {
                ps.setString(5, tarefa.getCategoria());
            } else {
                ps.setNull(5, Types.VARCHAR);
            }
    
            if (tarefa.getStatus() != null && !tarefa.getStatus().isEmpty()) {
                ps.setString(6, tarefa.getStatus());
            } else {
                ps.setNull(6, Types.VARCHAR);
            }
    
            ps.setInt(7, tarefa.getIdCompromisso());
            ps.executeUpdate();
            ps.close();
    
            // Imprima o comando SQL executado
            System.out.println(ps);
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    

    // Método para excluir uma tarefa de compromisso do banco de dados
    public void excluirTarefa(int idCompromisso) {
        String sql = "DELETE FROM compromissos WHERE id_compromisso = ?";
        PreparedStatement ps = null;
        try {
            ps = Conexao.obterConexao().prepareStatement(sql);
            ps.setInt(1, idCompromisso);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para recuperar todas as tarefas de compromisso do banco de dados
    public static List<AppointmentTask> recuperarTarefas() {
        List<AppointmentTask> tarefas = new ArrayList<>();
        String sql = "SELECT * FROM compromissos";
        PreparedStatement ps = null;

        try {
            ps = Conexao.obterConexao().prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                AppointmentTask tarefa = new AppointmentTask();
                tarefa.setIdCompromisso(resultSet.getInt("id_compromisso"));
                tarefa.setData(resultSet.getString("data"));
                tarefa.setHora(resultSet.getString("hora"));
                tarefa.setTitulo(resultSet.getString("titulo"));
                tarefa.setDescricao(resultSet.getString("descricao"));
                tarefa.setCategoria(resultSet.getString("categoria"));
                tarefa.setStatus(resultSet.getString("status"));

                tarefas.add(tarefa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tarefas;
    }

    // Método para recuperar todas as tarefas de compromisso por dia do banco de dados
    public static List<AppointmentTask> recuperarTarefasPorDia(String data) {
        List<AppointmentTask> tarefas = new ArrayList<>();
        String sql = "SELECT * FROM compromissos WHERE data = ?";
        PreparedStatement ps = null;

        try {
            ps = Conexao.obterConexao().prepareStatement(sql);
            ps.setString(1, data);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                AppointmentTask tarefa = new AppointmentTask();
                tarefa.setIdCompromisso(resultSet.getInt("id_compromisso"));
                tarefa.setData(resultSet.getString("data"));
                tarefa.setHora(resultSet.getString("hora"));
                tarefa.setTitulo(resultSet.getString("titulo"));
                tarefa.setDescricao(resultSet.getString("descricao"));
                tarefa.setCategoria(resultSet.getString("categoria"));
                tarefa.setStatus(resultSet.getString("status"));

                tarefas.add(tarefa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tarefas;
    }

    public AppointmentTask getCompromissoPorID(int id) {
        AppointmentTask compromisso = null;
        String query = "SELECT * FROM compromissos WHERE id = ?";
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        
        try {
            ps = Conexao.obterConexao().prepareStatement(query);
            ps.setString(1, String.valueOf(id));
            resultSet = ps.executeQuery();
    
            if (resultSet.next()) {
                compromisso = new AppointmentTask();
                compromisso.setTitulo(resultSet.getString("titulo"));
                compromisso.setDescricao(resultSet.getString("descricao"));
                compromisso.setHora(resultSet.getString("hora"));
                compromisso.setCategoria(resultSet.getString("categoria"));
                compromisso.setStatus(resultSet.getString("status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Certifique-se de fechar o PreparedStatement e o ResultSet em um bloco finally.
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    
        return compromisso;
    }

    public static List<AppointmentTask> buscarCompromissos(String dia, String mes, String ano, String hora, String titulo, String descricao, String categoria, String status) {
        List<AppointmentTask> compromissos = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM compromissos WHERE 1=1");
    
        // Adicione as condições de consulta com base nos parâmetros fornecidos
        if (dia != null && !dia.isEmpty()) {
            sql.append(" AND DAY(data) = ?");
        }
    
        if (mes != null && !mes.trim().isEmpty()) {
            sql.append(" AND MONTH(data) = ?");
        }
    
        if (ano != null && !ano.trim().isEmpty()) {
            sql.append(" AND YEAR(data) = ?");
        }
    
        if (hora != null && !hora.trim().isEmpty()) {
            sql.append(" AND hora = ?");
        }
    
        if (titulo != null && !titulo.trim().isEmpty()) {
            sql.append(" AND titulo LIKE ?");
        }
    
        if (descricao != null && !descricao.trim().isEmpty()) {
            sql.append(" AND descricao LIKE ?");
        }
    
        if (categoria != null && !categoria.trim().isEmpty()) {
            sql.append(" AND categoria = ?");
        }
    
        if (status != null && !status.trim().isEmpty()) {
            sql.append(" AND status = ?");
        }
    
        // Remova as condições que não têm valores
        if (sql.toString().equals("SELECT * FROM compromissos WHERE 1=1")) {
            // Se a consulta ainda for igual à consulta inicial, não adicione condições WHERE
            sql = new StringBuilder("SELECT * FROM compromissos");
        }
    
        System.out.println("SQL: " + sql.toString()); // Adicione esta linha para imprimir a consulta SQL
    
        PreparedStatement ps = null;
    
        try {
            ps = Conexao.obterConexao().prepareStatement(sql.toString());
    
            int parametroIndex = 1;
    
            if (dia != null && !dia.trim().isEmpty()) {
                ps.setString(parametroIndex++, dia);
            }
    
            if (mes != null && !mes.trim().isEmpty()) {
                ps.setString(parametroIndex++, mes);
            }
    
            if (ano != null && !ano.trim().isEmpty()) {
                ps.setString(parametroIndex++, ano);
            }
    
            if (hora != null && !hora.trim().isEmpty()) {
                ps.setString(parametroIndex++, hora);
            }
    
            if (titulo != null && !titulo.trim().isEmpty()) {
                ps.setString(parametroIndex++, "%" + titulo + "%");
            }
    
            if (descricao != null && !descricao.trim().isEmpty()) {
                ps.setString(parametroIndex++, "%" + descricao + "%");
            }
    
            if (categoria != null && !categoria.trim().isEmpty()) {
                ps.setString(parametroIndex++, categoria);
            }
    
            if (status != null && !status.trim().isEmpty()) {
                ps.setString(parametroIndex++, status);
            }
    
            ResultSet resultSet = ps.executeQuery();
    
            while (resultSet.next()) {
                AppointmentTask tarefa = new AppointmentTask();
                tarefa.setIdCompromisso(resultSet.getInt("id_compromisso"));
                tarefa.setData(resultSet.getString("data")); // Certifique-se de usar o nome correto da coluna
                tarefa.setHora(resultSet.getString("hora"));
                tarefa.setTitulo(resultSet.getString("titulo"));
                tarefa.setDescricao(resultSet.getString("descricao"));
                tarefa.setCategoria(resultSet.getString("categoria"));
                tarefa.setStatus(resultSet.getString("status"));
    
                compromissos.add(tarefa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    
        return compromissos;
    }
    
    
    
    
    
}
