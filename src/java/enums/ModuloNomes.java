/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enums;

/**
 *
 * @author vanderlei
 */
public enum ModuloNomes {
    Agendamento("Agendamento"),
    Anamnese("Anamnese"),
    Bandeira("Bandeira"),
    CentroCusto("Centro de Custo"),
    Cheque("Cheque"),
    Clinica("Clínica"),
    ContaAvulsa("Contas Avulsas"),
    ContasPagar("Contas a Pagar"),
    Documentacao("Documentação"),
    Especialidade("Especialidade"),
    FilaEspera("Fila de Espera"),
    FormaPagamento("Forma de Pagamento"),
    Fornecedor("Fornecedor"),
    Grupo("Grupo"),
    HistoricoLaboratorio("Histórico do Laboratório"),
    Indicacao("Indicação"),
    Material("Material"),
    Mensalidade("Mensalidade"),
    Motivo("Motivo"),
    Movimentacao("Movimentação"),
    Orcamento("Orçamento"),
    OrcamentoProcedimento("Orçamento-Procedimento"),
    Orto("Orto"),
    Paciente("Paciente"),
    PermissoesGrupo("Permissões dos Grupos"),
    Procedimento("Procedimento"),
    ProcedimentoOrto("Procedimento-Orto"),
    Profissional("Profissional"),
    Regra("Regra"),
    StatusLaboratorio("Status do Laboratório"),
    TabelaProcedimento("Tabela de Procedimentos"),
    Usuario("Usuário");
    
    private final String nome;
    
    ModuloNomes(String nome){
        this.nome = nome;
    }
    
    public String getNome(){
        return nome;
    }
}
