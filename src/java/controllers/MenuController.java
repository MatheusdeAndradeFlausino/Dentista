/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import classes.Grupo;
import classes.PermissoesGrupo;
import classes.Usuario;
import enums.ModuloNomes;
import facades.GrupoUsuarioFacade;
import facades.PermissoesGrupoFacade;
import facades.UsuarioFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import utils.ModulosPermissoes;

/**
 *
 * @author vanderlei
 */
@Named(value = "menuController")
@SessionScoped
public class MenuController implements Serializable {

    @EJB
    private PermissoesGrupoFacade permissoesFacade;
    @EJB
    private GrupoUsuarioFacade grupoFacade;
    @EJB
    private UsuarioFacade usuarioFacade;
    private List<PermissoesGrupo> permissoesGrupo;
    private ModulosPermissoes modulosPermitidos;

    /**
     * Creates a new instance of MenuController
     */
    public MenuController() {
    }

    public PermissoesGrupoFacade getPermissoesFacade() {
        return permissoesFacade;
    }

    public GrupoUsuarioFacade getGrupoFacade() {
        return grupoFacade;
    }

    public UsuarioFacade getUsuarioFacade() {
        return usuarioFacade;
    }

    public boolean isMenuPermitido(String nome) {
        if (modulosPermitidos == null) {
            pesquisar();
        }
        for (ModulosPermissoes.Modulo modulosPermitido : modulosPermitidos.getModulosPermitidos()) {
            if (modulosPermitido.getNome().equals(nome)) {
                return modulosPermitido.isPermitir();
            }
        }

        return false;
    }
    
    public boolean isMenuAnamnesePermitido(){
        return isMenuPermitido(ModuloNomes.Anamnese.getNome());
    } 
    
    
    public boolean isMenuBandeiraPermitido(){
        return isMenuPermitido(ModuloNomes.Bandeira.getNome());
    }
    
    public boolean isMenuCentroCustoPermitido(){
        return isMenuPermitido(ModuloNomes.CentroCusto.getNome());
    }
    
    public boolean isMenuChequePermitido(){
        return isMenuPermitido(ModuloNomes.Cheque.getNome());
    }
    
    public boolean isMenuClinicaPermitido(){
        return isMenuPermitido(ModuloNomes.Clinica.getNome());
    }

    public boolean isMenuAgendamentoPermitido(){
        return isMenuPermitido(ModuloNomes.Agendamento.getNome());
    }
    
    public boolean isMenuContaAvulsaPermitido(){
        return isMenuPermitido(ModuloNomes.ContaAvulsa.getNome());
    }
    
    public boolean isMenuContasPagarPermitido(){
        return isMenuPermitido(ModuloNomes.ContasPagar.getNome());
    }
    
    public boolean isMenuDocumentacaoPermitido(){
        return isMenuPermitido(ModuloNomes.Documentacao.getNome());
    }
    
    public boolean isMenuEspecialidadePermitido(){
        return isMenuPermitido(ModuloNomes.Especialidade.getNome());
    }
    
    public boolean isMenuFilaEsperaPermitido(){
        return isMenuPermitido(ModuloNomes.FilaEspera.getNome());
    }
    
    public boolean isMenuFormaPagamentoPermitido(){
        return isMenuPermitido(ModuloNomes.FormaPagamento.getNome());
    }
    
    public boolean isMenuFornecedorPermitido(){
        return isMenuPermitido(ModuloNomes.Fornecedor.getNome());
    }
    
    public boolean isMenuGrupoPermitido(){
        return isMenuPermitido(ModuloNomes.Grupo.getNome());
    }
    
    public boolean isMenuHistoricoLaboratorioPermitido(){
        return isMenuPermitido(ModuloNomes.HistoricoLaboratorio.getNome());
    }
    
    public boolean isMenuIndicacaoPermitido(){
        return isMenuPermitido(ModuloNomes.Indicacao.getNome());
    }
    
    public boolean isMenuMaterialPermitido(){
        return isMenuPermitido(ModuloNomes.Material.getNome());
    }
    
    public boolean isMenuMensalidadePermitido(){
        return isMenuPermitido(ModuloNomes.Mensalidade.getNome());
    }
    
    public boolean isMenuMotivoPermitido(){
        return isMenuPermitido(ModuloNomes.Motivo.getNome());
    }
    
    public boolean isMenuMovimentacaoPermitido(){
        return isMenuPermitido(ModuloNomes.Movimentacao.getNome());
    }
    
    public boolean isMenuOrcamentoPermitido(){
        return isMenuPermitido(ModuloNomes.Orcamento.getNome());
    }
    
    public boolean isMenuOrcamentoProcedimentoPermitido(){
        return isMenuPermitido(ModuloNomes.OrcamentoProcedimento.getNome());
    }
    
    public boolean isMenuOrtoPermitido(){
        return isMenuPermitido(ModuloNomes.Orto.getNome());
    }
    
    public boolean isMenuPacientePermitido(){
        return isMenuPermitido(ModuloNomes.Paciente.getNome());
    }
    
    public boolean isMenuPermissoesGrupoPermitido(){
        return isMenuPermitido(ModuloNomes.PermissoesGrupo.getNome());
    }
    
    public boolean isMenuProcedimentoPermitido(){
        return isMenuPermitido(ModuloNomes.Procedimento.getNome());
    }
    
    public boolean isMenuProcedimentoOrtoPermitido(){
        return isMenuPermitido(ModuloNomes.ProcedimentoOrto.getNome());
    }
    
    public boolean isMenuProfissionalPermitido(){
        return isMenuPermitido(ModuloNomes.Profissional.getNome());
    }
    
    public boolean isMenuRegraPermitido(){
        return isMenuPermitido(ModuloNomes.Regra.getNome());
    }
    
    public boolean isMenuStatusLaboratorioPermitido(){
        return isMenuPermitido(ModuloNomes.StatusLaboratorio.getNome());
    }
    
    public boolean isMenuTabelaProcedimentoPermitido(){
        return isMenuPermitido(ModuloNomes.TabelaProcedimento.getNome());
    }
    
    public boolean isMenuUsuarioPermitido(){
        return isMenuPermitido(ModuloNomes.Usuario.getNome());
    }
    
    public boolean isSuperMenuEstoquePermitido() {
        return isMenuPermitido(ModuloNomes.Movimentacao.getNome())
                || isMenuPermitido(ModuloNomes.Material.getNome());
    }

    public boolean isSuperMenuLaboratorioPermitido() {
        return isMenuPermitido(ModuloNomes.HistoricoLaboratorio.getNome())
                || isMenuPermitido(ModuloNomes.StatusLaboratorio.getNome());
    }

    public boolean isSuperMenuCadastroPermitido() {
        return (isMenuPermitido(ModuloNomes.Anamnese.getNome())
                || isMenuPermitido(ModuloNomes.Bandeira.getNome())
                || isMenuPermitido(ModuloNomes.CentroCusto.getNome())
                || isMenuPermitido(ModuloNomes.Clinica.getNome())
                || isMenuPermitido(ModuloNomes.Especialidade.getNome())
                || isMenuPermitido(ModuloNomes.FormaPagamento.getNome())
                || isMenuPermitido(ModuloNomes.Fornecedor.getNome())
                || isMenuPermitido(ModuloNomes.Grupo.getNome())
                || isMenuPermitido(ModuloNomes.Motivo.getNome())
                || isMenuPermitido(ModuloNomes.Paciente.getNome())
                || isMenuPermitido(ModuloNomes.Procedimento.getNome())
                || isMenuPermitido(ModuloNomes.Profissional.getNome())
                || isMenuPermitido(ModuloNomes.TabelaProcedimento.getNome())
                || isMenuPermitido(ModuloNomes.Usuario.getNome())
                || isSuperMenuEstoquePermitido()
                || isSuperMenuLaboratorioPermitido());
    }

    public boolean isSuperMenuAtendimentoPermitido() {
        return (isMenuPermitido(ModuloNomes.Agendamento.getNome())
                || isMenuPermitido(ModuloNomes.FilaEspera.getNome())
                || isMenuPermitido(ModuloNomes.Orcamento.getNome())
                || isMenuPermitido(ModuloNomes.OrcamentoProcedimento.getNome())
                || isMenuPermitido(ModuloNomes.Orto.getNome())
                || isMenuPermitido(ModuloNomes.ProcedimentoOrto.getNome()));
    }

    public boolean isSuperMenuRegrasPermitido() {
        return (isMenuPermitido(ModuloNomes.Regra.getNome())
                || isMenuPermitido(ModuloNomes.PermissoesGrupo.getNome()));
    }

    public boolean isSuperMenuFinanceiroPermitido() {
        return (isMenuPermitido(ModuloNomes.Cheque.getNome())
                || isMenuPermitido(ModuloNomes.ContaAvulsa.getNome())
                || isMenuPermitido(ModuloNomes.ContasPagar.getNome())
                || isMenuPermitido(ModuloNomes.Documentacao.getNome())
                || isMenuPermitido(ModuloNomes.Indicacao.getNome())
                || isMenuPermitido(ModuloNomes.Mensalidade.getNome()));
    }

    public List<PermissoesGrupo> getPermissoesGrupo() {
        if (permissoesGrupo == null) {
            pesquisar();
        }
        return permissoesGrupo;
    }

    public void pesquisar() {
        permissoesGrupo = new LinkedList<>();
        modulosPermitidos = new ModulosPermissoes();
        Usuario usuarioLogado = getUsuarioFacade().findByLogin(usuarioLogado());
        List<Grupo> gruposUsuario = getGrupoFacade().findAllGruposDeUmUsuario(usuarioLogado);
        for (Grupo gruposUsuario1 : gruposUsuario) {
            permissoesGrupo.addAll(getPermissoesFacade().findByGrupo(gruposUsuario1));
        }
        modulosPermitidos.popularModulosPermissoes(permissoesGrupo);
    }

    public String usuarioLogado() {
        return ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getUserPrincipal().getName();
    }

    public void setPermissoesGrupo(List<PermissoesGrupo> permissoesGrupo) {
        this.permissoesGrupo = permissoesGrupo;
    }

}
