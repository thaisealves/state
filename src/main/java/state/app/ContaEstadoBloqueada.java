package state.app;

public class ContaEstadoBloqueada extends ContaEstado {
    private ContaEstadoBloqueada() {
    };

    private static ContaEstadoBloqueada instance = new ContaEstadoBloqueada();

    public static ContaEstadoBloqueada getInstance() {
        return instance;
    }

    @Override
    public String getEstado() {
        return "Bloqueada";
    }

    @Override
    public boolean desbloquear(Conta conta) {
        if (conta.getSaldo() < 0) {
            conta.setEstado(ContaEstadoNegativa.getInstance());
        } else {
            conta.setEstado(ContaEstadoAtiva.getInstance());
        }
        return true;
    }

    @Override
    public boolean encerrar(Conta conta) {
        if (conta.getSaldo() < 0) {
            return false;
        }
        conta.setSaldo(0);
        conta.setEstado(ContaEstadoEncerrada.getInstance());
        return true;
    }
}
