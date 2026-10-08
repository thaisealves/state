package state.app;

public class Conta {
    private ContaEstado estado;
    private double saldo;
    private double limite;

    public Conta() {
        this(0);
    }

    public Conta(double limite) {
        this.estado = ContaEstadoAtiva.getInstance();
        this.limite = limite;
    }

    public void setEstado(ContaEstado estado) {
        this.estado = estado;
    }

    public ContaEstado getEstado() {
        return estado;
    }

    public String getNomeEstado() {
        return estado.getEstado();
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }

    public boolean sacar(double valor) {
        return estado.sacar(this, valor);
    }

    public boolean depositar(double valor) {
        return estado.depositar(this, valor);
    }

    public boolean transferir(Conta destino, double valor) {
        return estado.transferir(this, destino, valor);
    }

    public boolean bloquear() {
        return estado.bloquear(this);
    }

    public boolean encerrar() {
        return estado.encerrar(this);
    }

    public boolean desbloquear() {
        return estado.desbloquear(this);
    }
}
