package agendajava.entity;

import java.io.UnsupportedEncodingException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AppointmentTask {
    private int idCompromisso;
    private String data;
    private String hora;
    private String titulo; // Adicionado o campo de título
    private String descricao;
    private String categoria;
    private String status;

    // Construtor vazio
    public AppointmentTask() {
    }

    // Construtor com parâmetros
    public AppointmentTask(int idCompromisso, String data, String hora, String titulo, String descricao, String categoria, String status) {
        this.idCompromisso = idCompromisso;
        this.data = data;
        this.hora = hora;
        this.titulo = titulo;
        this.descricao = descricao;
        this.categoria = categoria;
        this.status = status;
    }

    // Getters e setters
    public int getIdCompromisso() {
        return idCompromisso;
    }

    public void setIdCompromisso(int idCompromisso) {
        this.idCompromisso = idCompromisso;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        // Verifica se a hora é válida antes de formatar
        try {
            LocalTime time = LocalTime.parse(hora);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            return time.format(formatter);
        } catch (Exception e) {
            // Tratar erros de parsing ou formatação aqui, se necessário
            return hora; // Retorna a hora sem formatação em caso de erro
        }
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
    
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        try {
            this.titulo = new String(titulo.getBytes("UTF-8"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            System.err.println("Erro de codificação UTF-8: " + e.getMessage());
        }
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        try {
            // Certifique-se de que a entrada seja convertida para UTF-8
            this.descricao = new String(descricao.getBytes("UTF-8"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // Lida com a exceção, se ocorrer
            System.err.println("Erro de codificação UTF-8: " + e.getMessage());
        }
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Override do método toString para exibir informações do compromisso
    @Override
    public String toString() {
        return "Compromisso [idCompromisso=" + idCompromisso + ", data=" + data + ", hora=" + hora + ", titulo=" + titulo + ", descricao=" + descricao
                + ", categoria=" + categoria + ", status=" + status + "]";
    }
}
