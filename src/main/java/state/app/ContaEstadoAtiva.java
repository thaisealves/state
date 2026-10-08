package state.app;

public class ContaEstadoAtiva extends ContaEstado {
    private ContaEstadoAtiva() {
    };

    private static ContaEstadoAtiva instance = new ContaEstadoAtiva();

    public static ContaEstadoAtiva getInstance() {
        return instance;
    }

    @Override
    public String getEstado() {
        return "Ativa";
    }

    @Override
    public boolean sacar(Conta conta, double valor) {
        if (conta.getSaldo() + conta.getLimite() >= valor) {
            conta.setSaldo(conta.getSaldo() - valor);
            if (conta.getSaldo() < 0) {
                conta.setEstado(ContaEstadoNegativa.getInstance());
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean depositar(Conta conta, double valor) {
        conta.setSaldo(conta.getSaldo() + valor);
        return true;
    }

    @Override
    public boolean transferir(Conta conta, Conta destino, double valor) {
        if (conta.getSaldo() + conta.getLimite() >= valor && destino.depositar(valor)) {
            conta.setSaldo(conta.getSaldo() - valor);
            if (conta.getSaldo() < 0) {
                conta.setEstado(ContaEstadoNegativa.getInstance());
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean bloquear(Conta conta) {
        conta.setEstado(ContaEstadoBloqueada.getInstance());
        return true;
    }

    @Override
    public boolean encerrar(Conta conta) {
        conta.sacar(conta.getSaldo());
        conta.setEstado(ContaEstadoEncerrada.getInstance());
        return true;
    }
}
