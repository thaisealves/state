package state.app;

public class ContaEstadoNegativa extends ContaEstado {
    private ContaEstadoNegativa() {
    };

    private static ContaEstadoNegativa instance = new ContaEstadoNegativa();

    public static ContaEstadoNegativa getInstance() {
        return instance;
    }

    @Override
    public String getEstado() {
        return "Negativa";
    }

    @Override
    public boolean depositar(Conta conta, double valor) {
        conta.setSaldo(conta.getSaldo() + valor);
        if (conta.getSaldo() >= 0) {
            conta.setEstado(ContaEstadoAtiva.getInstance());
        }
        return true;
    }

    @Override
    public boolean sacar(Conta conta, double valor) {
        if (conta.getSaldo() + conta.getLimite() >= valor) {
            conta.setSaldo(conta.getSaldo() - valor);
            return true;
        }
        return false;
    }

    @Override
    public boolean transferir(Conta conta, Conta destino, double valor) {
        if (conta.getSaldo() + conta.getLimite() >= valor && destino.depositar(valor)) {
            conta.setSaldo(conta.getSaldo() - valor);
            return true;
        }
        return false;
    }

    @Override
    public boolean bloquear(Conta conta) {
        conta.setEstado(ContaEstadoBloqueada.getInstance());
        return true;
    }

}
