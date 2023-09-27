package agendajava.entity;

import java.io.UnsupportedEncodingException;

public class AppointmentTask {
    private int idCompromisso;
    private String data;
    private String hora;
    private String descricao;
    private String categoria;
    private String status;

    // Construtor vazio
    public AppointmentTask() {
    }

    // Construtor com parâmetros
    public AppointmentTask(int idCompromisso, String data, String hora, String descricao, String categoria, String status) {
        this.idCompromisso = idCompromisso;
        this.data = data;
        this.hora = hora;
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
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
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
        return "Compromisso [idCompromisso=" + idCompromisso + ", data=" + data + ", hora=" + hora + ", descricao=" + descricao
                + ", categoria=" + categoria + ", status=" + status + "]";
    }
}