package state.app;

public class ContaEstadoEncerrada extends ContaEstado {
    private ContaEstadoEncerrada() {
    };

    private static ContaEstadoEncerrada instance = new ContaEstadoEncerrada();

    public static ContaEstadoEncerrada getInstance() {
        return instance;
    }

    @Override
    public String getEstado() {
        return "Encerrada";
    }
}
