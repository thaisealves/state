package state.app;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StateTest {
    Conta conta;
    Conta destino;

    @BeforeEach
    void setUp() {
        conta = new Conta(100);
        destino = new Conta();
    }

    // Estado Ativa

    @Test
    void deveIniciarContaAtiva() {
        assertEquals(ContaEstadoAtiva.getInstance(), conta.getEstado());
    }

    @Test
    void devePermanecerAtivaComSaldoZero() {
        conta.depositar(100);
        assertTrue(conta.sacar(100));
        assertEquals(0, conta.getSaldo());
        assertEquals(ContaEstadoAtiva.getInstance(), conta.getEstado());
    }

    @Test
    void deveNegativarAoSacarAteOLimite() {
        assertTrue(conta.sacar(100));
        assertEquals(-100, conta.getSaldo());
        assertEquals(ContaEstadoNegativa.getInstance(), conta.getEstado());
    }

    @Test
    void naoDeveSacarAlemDoLimite() {
        assertFalse(conta.sacar(100.01));
        assertEquals(0, conta.getSaldo());
        assertEquals(ContaEstadoAtiva.getInstance(), conta.getEstado());
    }

    @Test
    void deveNegativarAoTransferirAteOLimite() {
        assertTrue(conta.transferir(destino, 100));
        assertEquals(-100, conta.getSaldo());
        assertEquals(100, destino.getSaldo());
        assertEquals(ContaEstadoNegativa.getInstance(), conta.getEstado());
    }

    @Test
    void naoDeveTransferirAlemDoLimite() {
        assertFalse(conta.transferir(destino, 100.01));
        assertEquals(0, conta.getSaldo());
        assertEquals(0, destino.getSaldo());
    }

    @Test
    void naoDeveTransferirParaContaEncerrada() {
        destino.encerrar();
        conta.depositar(100);
        assertFalse(conta.transferir(destino, 100));
        assertEquals(100, conta.getSaldo());
    }

    @Test
    void deveBloquearContaAtiva() {
        assertTrue(conta.bloquear());
        assertEquals(ContaEstadoBloqueada.getInstance(), conta.getEstado());
    }

    @Test
    void deveEncerrarContaAtivaZerandoSaldo() {
        conta.depositar(100);
        assertTrue(conta.encerrar());
        assertEquals(0, conta.getSaldo());
        assertEquals(ContaEstadoEncerrada.getInstance(), conta.getEstado());
    }

    // Estado Negativa

    @Test
    void deveSacarAteOLimiteEmContaNegativa() {
        conta.sacar(50);
        assertTrue(conta.sacar(50));
        assertEquals(-100, conta.getSaldo());
    }

    @Test
    void naoDeveSacarAlemDoLimiteEmContaNegativa() {
        conta.sacar(50);
        assertFalse(conta.sacar(50.01));
        assertEquals(-50, conta.getSaldo());
    }

    @Test
    void deveTransferirAteOLimiteEmContaNegativa() {
        conta.sacar(50);
        assertTrue(conta.transferir(destino, 50));
        assertEquals(-100, conta.getSaldo());
        assertEquals(50, destino.getSaldo());
    }

    @Test
    void naoDeveTransferirAlemDoLimiteEmContaNegativa() {
        conta.sacar(50);
        assertFalse(conta.transferir(destino, 50.01));
        assertEquals(-50, conta.getSaldo());
        assertEquals(0, destino.getSaldo());
    }

    @Test
    void devePermanecerNegativaAoDepositarMenosQueADivida() {
        conta.sacar(100);
        assertTrue(conta.depositar(99.99));
        assertEquals(ContaEstadoNegativa.getInstance(), conta.getEstado());
    }

    @Test
    void deveAtivarAoDepositarODevido() {
        conta.sacar(100);
        assertTrue(conta.depositar(100));
        assertEquals(0, conta.getSaldo());
        assertEquals(ContaEstadoAtiva.getInstance(), conta.getEstado());
    }

    @Test
    void deveBloquearContaNegativa() {
        conta.sacar(100);
        assertTrue(conta.bloquear());
        assertEquals(ContaEstadoBloqueada.getInstance(), conta.getEstado());
    }

    @Test
    void naoDeveEncerrarContaNegativa() {
        conta.sacar(100);
        assertFalse(conta.encerrar());
        assertEquals(ContaEstadoNegativa.getInstance(), conta.getEstado());
    }

    // Estado Bloqueada

    @Test
    void contaBloqueadaNaoDeveMovimentar() {
        conta.depositar(100);
        conta.bloquear();
        assertFalse(conta.sacar(1));
        assertFalse(conta.depositar(1));
        assertFalse(conta.transferir(destino, 1));
        assertFalse(conta.bloquear());
        assertEquals(100, conta.getSaldo());
    }

    @Test
    void deveDesbloquearParaAtivaComSaldoZero() {
        conta.bloquear();
        assertTrue(conta.desbloquear());
        assertEquals(ContaEstadoAtiva.getInstance(), conta.getEstado());
    }

    @Test
    void deveDesbloquearParaNegativaComSaldoNegativo() {
        conta.sacar(0.01);
        conta.bloquear();
        assertTrue(conta.desbloquear());
        assertEquals(ContaEstadoNegativa.getInstance(), conta.getEstado());
    }

    @Test
    void deveEncerrarContaBloqueadaZerandoSaldo() {
        conta.depositar(100);
        conta.bloquear();
        assertTrue(conta.encerrar());
        assertEquals(0, conta.getSaldo());
        assertEquals(ContaEstadoEncerrada.getInstance(), conta.getEstado());
    }

    @Test
    void naoDeveEncerrarContaBloqueadaComSaldoNegativo() {
        conta.sacar(0.01);
        conta.bloquear();
        assertFalse(conta.encerrar());
        assertEquals(ContaEstadoBloqueada.getInstance(), conta.getEstado());
    }

    // Estado Encerrada

    @Test
    void contaEncerradaNaoDeveRealizarOperacoes() {
        conta.encerrar();
        assertFalse(conta.sacar(1));
        assertFalse(conta.depositar(1));
        assertFalse(conta.transferir(destino, 1));
        assertFalse(conta.bloquear());
        assertFalse(conta.desbloquear());
        assertFalse(conta.encerrar());
        assertEquals(ContaEstadoEncerrada.getInstance(), conta.getEstado());
    }
}
