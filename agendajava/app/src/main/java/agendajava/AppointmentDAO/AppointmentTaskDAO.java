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

    // Método para atualizar uma tarefa de compromisso no banco de dados
    public void atualizarTarefa(AppointmentTask tarefa) {
        String sql = "UPDATE compromissos SET data = ?, hora = ?, titulo = ?, descricao = ?, categoria = ?, status = ? WHERE id_compromisso = ?";

        PreparedStatement ps = null;
        try {
            ps = Conexao.obterConexao().prepareStatement(sql);
            ps.setString(1, tarefa.getData());
            ps.setString(2, tarefa.getHora());
            ps.setString(3, tarefa.getTitulo());
            ps.setString(4, tarefa.getDescricao());
            ps.setString(5, tarefa.getCategoria());
            ps.setString(6, tarefa.getStatus());
            ps.setInt(7, tarefa.getIdCompromisso());

            ps.executeUpdate();
            ps.close();
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
    public List<AppointmentTask> recuperarTarefas() {
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
    public List<AppointmentTask> recuperarTarefasPorDia(String data) {
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
}
