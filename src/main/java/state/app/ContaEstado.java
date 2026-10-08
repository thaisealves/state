package state.app;

public abstract class ContaEstado {

    public abstract String getEstado();

    public boolean sacar(Conta conta, double valor) {
        return false;
    }

    public boolean depositar(Conta conta, double valor) {
        return false;
    }

    public boolean transferir(Conta conta, Conta destino, double valor) {
        return false;
    }

    public boolean bloquear(Conta conta) {
        return false;
    }

    public boolean encerrar(Conta conta) {
        return false;
    }

    public boolean desbloquear(Conta conta) {
        return false;
    }
}
